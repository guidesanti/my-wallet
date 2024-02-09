package br.com.eventhorizon.mywallet.ms.transactions.api.messaging.model.mapper;

import br.com.eventhorizon.mywallet.common.proto.TransactionsProto;
import br.com.eventhorizon.common.utils.DateTimeUtils;
import br.com.eventhorizon.mywallet.ms.transactions.business.model.Transaction;

import java.math.BigDecimal;

public final class TransactionMessageMapper {

    public static Transaction toBusinessModel(TransactionsProto.Transaction transaction) {
        return Transaction.builder()
                .id(transaction.getId())
                .createdAt(DateTimeUtils.stringToOffsetDateTime(transaction.getCreatedAt()))
                .settledAt(DateTimeUtils.stringToOffsetDateTime(transaction.getSettledAt()))
                .state(Transaction.State.valueOf(transaction.getState().name()))
                .type(Transaction.Type.valueOf(transaction.getType().name()))
                .units(new BigDecimal(transaction.getUnits()))
                .price(new BigDecimal(transaction.getPrice()))
                .sourceAccountId(transaction.getSourceAccountId())
                .destinationAccountId(transaction.getDestinationAccountId())
                .assetId(transaction.getAssetId())
                .description(transaction.getDescription())
                .build();
    }

    public static Transaction toBusinessModel(TransactionsProto.CreateTransactionCommandRequest createTransactionCommandRequest) {
        return Transaction.builder()
                .type(Transaction.Type.valueOf(createTransactionCommandRequest.getType().name()))
                .units(new BigDecimal(createTransactionCommandRequest.getUnits()))
                .price(new BigDecimal(createTransactionCommandRequest.getPrice()))
                .sourceAccountId(createTransactionCommandRequest.getSourceAccountId())
                .destinationAccountId(createTransactionCommandRequest.getDestinationAccountId())
                .assetId(createTransactionCommandRequest.getAssetId())
                .description(createTransactionCommandRequest.getDescription())
                .build();
    }

    public static TransactionsProto.Transaction toMessageModel(Transaction transaction) {
        var builder = TransactionsProto.Transaction.newBuilder()
                .setId(transaction.getId())
                .setCreatedAt(DateTimeUtils.offsetDateTimeToString(transaction.getCreatedAt()))
                .setState(TransactionsProto.TransactionState.valueOf(transaction.getState().name()))
                .setType(TransactionsProto.TransactionType.valueOf(transaction.getType().name()))
                .setUnits(transaction.getUnits().toString())
                .setPrice(transaction.getPrice().toString());
        var settledAt = DateTimeUtils.offsetDateTimeToString(transaction.getSettledAt());
        if (settledAt != null) {
            builder.setSettledAt(settledAt);
        }
        var sourceAccountId = transaction.getSourceAccountId();
        if (sourceAccountId != null) {
            builder.setSourceAccountId(sourceAccountId);
        }
        var destinationAccountId = transaction.getDestinationAccountId();
        if (destinationAccountId != null) {
            builder.setDestinationAccountId(destinationAccountId);
        }
        var assetId = transaction.getAssetId();
        if (assetId != null) {
            builder.setAssetId(assetId);
        }
        var description = transaction.getDescription();
        if (description != null) {
            builder.setDescription(description);
        }

        return builder.build();
    }
}
