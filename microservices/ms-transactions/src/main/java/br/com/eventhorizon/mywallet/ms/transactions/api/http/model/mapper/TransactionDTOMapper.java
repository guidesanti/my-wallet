package br.com.eventhorizon.mywallet.ms.transactions.api.http.model.mapper;

import br.com.eventhorizon.mywallet.common.proto.TransactionsProto;
import br.com.eventhorizon.mywallet.ms.transactions.api.http.model.CreateTransactionRequestDTO;
import br.com.eventhorizon.mywallet.ms.transactions.api.http.model.TransactionDTO;

import java.math.BigDecimal;

public final class TransactionDTOMapper {

    public static TransactionsProto.CreateTransactionCommandRequest toMessagingModel(CreateTransactionRequestDTO createTransactionRequestDTO) {
        var builder = TransactionsProto.CreateTransactionCommandRequest.newBuilder()
                .setType(TransactionsProto.TransactionType.valueOf(createTransactionRequestDTO.getType().name()))
                .setUnits(createTransactionRequestDTO.getUnits().toString())
                .setPrice(createTransactionRequestDTO.getPrice().toString());
        var sourceAccountId = createTransactionRequestDTO.getSourceAccountId();
        if (sourceAccountId != null) {
            builder.setSourceAccountId(sourceAccountId);
        }
        var destinationAccountId = createTransactionRequestDTO.getDestinationAccountId();
        if (destinationAccountId != null) {
            builder.setDestinationAccountId(destinationAccountId);
        }
        var assetId = createTransactionRequestDTO.getAssetId();
        if (assetId != null) {
            builder.setAssetId(assetId);
        }
        var description = createTransactionRequestDTO.getDescription();
        if (description != null) {
            builder.setDescription(description);
        }

        return builder.build();
    }

    public static TransactionDTO toApiModel(TransactionsProto.Transaction transaction) {
        var transactionDTO = new TransactionDTO(transaction.getId(), transaction.getState().name(), transaction.getType().name(), new BigDecimal(transaction.getUnits()), new BigDecimal(transaction.getPrice()));
        transactionDTO.setCreatedAt(transaction.getCreatedAt());
        transactionDTO.setSettledAt(transaction.getSettledAt());
        transactionDTO.setSourceAccountId(transaction.getSourceAccountId());
        transactionDTO.setDestinationAccountId(transaction.getDestinationAccountId());
        transactionDTO.setAssetId(transaction.getAssetId());
        transactionDTO.setDescription(transaction.getDescription());
        return transactionDTO;
    }

    public static TransactionDTO toApiModel(TransactionsProto.CreateTransactionCommandReply createTransactionCommandReply) {
        TransactionsProto.Transaction transactionMessage = createTransactionCommandReply.getTransaction();
        var transactionDTO = new TransactionDTO(transactionMessage.getId(),
                transactionMessage.getState().name(),
                transactionMessage.getType().name(),
                new BigDecimal(transactionMessage.getUnits()),
                new BigDecimal(transactionMessage.getPrice()));
        transactionDTO.setCreatedAt(transactionMessage.getCreatedAt());
        transactionDTO.setSettledAt(transactionMessage.getSettledAt());
        transactionDTO.setSourceAccountId(transactionMessage.getSourceAccountId());
        transactionDTO.setDestinationAccountId(transactionMessage.getDestinationAccountId());
        transactionDTO.setAssetId(transactionMessage.getAssetId());
        transactionDTO.setDescription(transactionMessage.getDescription());
        return transactionDTO;
    }
}
