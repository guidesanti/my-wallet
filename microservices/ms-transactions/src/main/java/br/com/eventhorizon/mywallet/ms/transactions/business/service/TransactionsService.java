package br.com.eventhorizon.mywallet.ms.transactions.business.service;

import br.com.eventhorizon.common.exception.BusinessErrorException;
import br.com.eventhorizon.mywallet.common.proto.AssetsProto;
import br.com.eventhorizon.mywallet.common.proto.TransactionsProto;
import br.com.eventhorizon.common.repository.DuplicateKeyException;
import br.com.eventhorizon.saga.*;
import br.com.eventhorizon.saga.content.SagaContent;
import br.com.eventhorizon.saga.content.serializer.impl.DefaultSagaContentSerializer;
import br.com.eventhorizon.saga.handler.SagaSingleHandler;
import br.com.eventhorizon.saga.messaging.SagaPublisher;
import br.com.eventhorizon.saga.repository.SagaRepository;
import br.com.eventhorizon.saga.transaction.SagaTransaction;
import br.com.eventhorizon.saga.transaction.SagaTransactionManager;
import br.com.eventhorizon.common.validation.ValidationError;
import br.com.eventhorizon.mywallet.ms.transactions.ApplicationProperties;
import br.com.eventhorizon.mywallet.ms.transactions.business.Errors;
import br.com.eventhorizon.mywallet.ms.transactions.business.model.validation.CreateTransactionCommandRequestValidator;
import br.com.eventhorizon.mywallet.ms.transactions.api.messaging.model.mapper.TransactionMessageMapper;
import br.com.eventhorizon.mywallet.ms.transactions.business.model.Transaction;
import br.com.eventhorizon.mywallet.ms.transactions.persistence.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.StringJoiner;

@Service
@Slf4j
public class TransactionsService {

    private final SagaTransactionManager sagaTransactionManager;

    private final SagaTransaction createTransactionSagaTransaction;

    private final CreateTransactionCommandRequestValidator createTransactionRequestValidator;

    @Autowired
    public TransactionsService(ApplicationProperties applicationProperties,
                               TransactionRepository transactionRepository,
                               SagaRepository sagaRepository,
                               SagaPublisher sagaPublisher,
                               CreateTransactionCommandRequestValidator createTransactionRequestValidator) {
        this.sagaTransactionManager = new SagaTransactionManager();
        this.createTransactionSagaTransaction = SagaTransaction.builder()
                .handler(new CreateTransactionRequestHandler(applicationProperties, transactionRepository))
                .repository(sagaRepository)
                .publisher(sagaPublisher)
                .serializer(new DefaultSagaContentSerializer(TransactionsProto.Transaction.class))
                .serializer(new DefaultSagaContentSerializer(TransactionsProto.CreateTransactionCommandRequest.class))
                .serializer(new DefaultSagaContentSerializer(TransactionsProto.TransactionCreatedEvent.class))
                .serializer(new DefaultSagaContentSerializer(AssetsProto.GetAssetCommandRequest.class))
                .build();
        this.createTransactionRequestValidator  = createTransactionRequestValidator;
    }

    public TransactionsProto.Transaction createTransaction(
            SagaIdempotenceId idempotenceId,
            String traceId,
            TransactionsProto.CreateTransactionCommandRequest createTransactionCommandRequest) {
        handleCreateTransactionRequestValidationErrors(createTransactionRequestValidator.validate(createTransactionCommandRequest));
        SagaResponse sagaResponse = sagaTransactionManager.execute(
                createTransactionSagaTransaction,
                SagaMessage.builder()
                        .idempotenceId(idempotenceId)
                        .traceId(traceId)
                        .content(SagaContent.of(createTransactionCommandRequest))
                        .build());
        return (TransactionsProto.Transaction) sagaResponse.content().getContent();
    }

    private void handleCreateTransactionRequestValidationErrors(List<ValidationError> errors) {
        if (!errors.isEmpty()) {
            var details = new StringJoiner(", ", "{", "}");
            errors.forEach(error -> details.add(
                    new StringBuilder().append("{")
                            .append("field: ").append(error.getField())
                            .append(", code: ").append(error.getCode())
                            .append(", message: ").append(error.getMessage())
                            .append("}")
            ));
            throw new BusinessErrorException(
                    Errors.INVALID_CREATE_TRANSACTION_REQUEST.getCode(),
                    String.format(Errors.INVALID_CREATE_TRANSACTION_REQUEST.getMessageTemplate()),
                    details.toString());
        }
    }

    @RequiredArgsConstructor
    private static class CreateTransactionRequestHandler implements SagaSingleHandler {

        private final ApplicationProperties applicationProperties;

        private final TransactionRepository transactionRepository;

        @Override
        public SagaOutput handle(SagaMessage message) {
            var createTransactionRequest = (TransactionsProto.CreateTransactionCommandRequest) message.content().getContent();
            var transaction = TransactionMessageMapper.toBusinessModel(createTransactionRequest);
            try {
                var createdTransaction = transactionRepository.create(transaction);
                return SagaOutput.builder()
                        .response(SagaResponse.builder()
                                .idempotenceId(message.idempotenceId())
                                .content(SagaContent.of(TransactionMessageMapper.toMessageModel(createdTransaction)))
                                .build())
                        .event(SagaEvent.builder()
                                .originalIdempotenceId(message.idempotenceId())
                                .idempotenceId(message.idempotenceId().incrementIdempotenceStep(1))
                                .traceId(message.traceId())
                                .destination(applicationProperties.getKafka().getTopics().get("transactions-transaction-created"))
                                .headers(SagaHeaders.builder()
                                        .header("transaction-id", createdTransaction.getId())
                                        .build())
                                .content(SagaContent.of(getCreatedTransactionEvent(createdTransaction)))
                                .build())
                        .event(SagaEvent.builder()
                                .originalIdempotenceId(message.idempotenceId())
                                .idempotenceId(message.idempotenceId().incrementIdempotenceStep(1))
                                .traceId(message.traceId())
                                .destination(applicationProperties.getKafka().getTopics().get("assets-get-asset"))
                                .headers(SagaHeaders.builder()
                                        .header("transaction-id", createdTransaction.getId())
                                        .build())
                                .content(SagaContent.of(AssetsProto.GetAssetCommandRequest.newBuilder()
                                                .setAssetId(transaction.getAssetId())
                                        .build()))
                                .build())
                        .build();
            } catch (DuplicateKeyException ex) {
                throw new BusinessErrorException(
                        "TRANSACTION_ALREADY_EXIST", "Transaction already exist with ID '" +
                        transaction.getId() + "'",
                        ex);
            }
        }

        private TransactionsProto.TransactionCreatedEvent getCreatedTransactionEvent(Transaction transaction) {
            return TransactionsProto.TransactionCreatedEvent.newBuilder()
                    .setTransaction(TransactionMessageMapper.toMessageModel(transaction))
                    .build();
        }
    }
}
