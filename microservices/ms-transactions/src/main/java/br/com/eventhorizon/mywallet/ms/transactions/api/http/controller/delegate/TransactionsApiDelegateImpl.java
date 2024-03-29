package br.com.eventhorizon.mywallet.ms.transactions.api.http.controller.delegate;

import br.com.eventhorizon.saga.SagaIdempotenceId;
import br.com.eventhorizon.mywallet.ms.transactions.api.http.TransactionsApiDelegate;
import br.com.eventhorizon.mywallet.ms.transactions.api.http.model.CreateTransactionRequestDTO;
import br.com.eventhorizon.mywallet.ms.transactions.api.http.model.GetAllTransactions200Response;
import br.com.eventhorizon.mywallet.ms.transactions.api.http.model.ResponseStatus;
import br.com.eventhorizon.mywallet.ms.transactions.api.http.model.mapper.TransactionDTOMapper;
import br.com.eventhorizon.mywallet.ms.transactions.business.service.TransactionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class TransactionsApiDelegateImpl implements TransactionsApiDelegate {

    private final TransactionsService transactionsService;

    @Override
    public ResponseEntity<GetAllTransactions200Response> createTransaction(
            String traceId, String idempotenceId, CreateTransactionRequestDTO createTransactionRequestDTO) {
        try {
            throw new RuntimeException("Blablabla");
        } catch (Exception e) {
            log.info("Error1: {} / {}", e.getMessage(), e);
            log.info("Error2: " + e.getMessage(), e);
        }
        var createdTransaction = TransactionDTOMapper.toApiModel(
                transactionsService.createTransaction(
                        SagaIdempotenceId.of(idempotenceId),
                        traceId,
                        TransactionDTOMapper.toMessagingModel(createTransactionRequestDTO)));
        var response = new GetAllTransactions200Response(ResponseStatus.SUCCESS);
        response.setData(List.of(createdTransaction));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetAllTransactions200Response> getAllTransactions(String xTraceId) {
        return TransactionsApiDelegate.super.getAllTransactions(xTraceId);
    }
}
