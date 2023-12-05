package br.com.eventhorizon.mywallet.ms.transactions.persistence.model.mapper;

import br.com.eventhorizon.mywallet.common.util.DateTimeUtils;
import br.com.eventhorizon.mywallet.ms.transactions.business.model.Transaction;
import br.com.eventhorizon.mywallet.ms.transactions.persistence.model.TransactionDocument;

import java.math.BigDecimal;

public final class TransactionDocumentMapper {

    public static TransactionDocument toPersistenceModel(Transaction transaction) {
        return TransactionDocument.builder()
                .id(transaction.getId())
                .createdAt(DateTimeUtils.offsetDateTimeToString(transaction.getCreatedAt()))
                .settledAt(DateTimeUtils.offsetDateTimeToString(transaction.getSettledAt()))
                .state(transaction.getState().name())
                .type(transaction.getType().name())
                .units(transaction.getUnits().toString())
                .price(transaction.getPrice().toString())
                .sourceAccountId(transaction.getSourceAccountId())
                .destinationAccountId(transaction.getDestinationAccountId())
                .assetId(transaction.getAssetId())
                .description(transaction.getDescription())
                .build();
    }

    public static Transaction toBusinessModel(TransactionDocument transaction) {
        return Transaction.builder()
                .id(transaction.getId())
                .createdAt(DateTimeUtils.stringToOffsetDateTime(transaction.getCreatedAt()))
                .settledAt(DateTimeUtils.stringToOffsetDateTime(transaction.getSettledAt()))
                .state(Transaction.State.valueOf(transaction.getState()))
                .type(Transaction.Type.valueOf(transaction.getType()))
                .units(new BigDecimal(transaction.getUnits()))
                .price(new BigDecimal(transaction.getPrice()))
                .sourceAccountId(transaction.getSourceAccountId())
                .destinationAccountId(transaction.getDestinationAccountId())
                .assetId(transaction.getAssetId())
                .description(transaction.getDescription())
                .build();
    }
}
