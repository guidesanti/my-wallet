package br.com.eventhorizon.mywallet.ms.transactions.business.model.validation;

import br.com.eventhorizon.common.validation.ValidationResult;
import br.com.eventhorizon.mywallet.common.proto.TransactionsProto;
import br.com.eventhorizon.common.validation.ValidationError;
import br.com.eventhorizon.common.validation.ValidationErrorCode;
import br.com.eventhorizon.common.validation.Validator;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateTransactionCommandRequestValidator implements Validator<TransactionsProto.CreateTransactionCommandRequest> {

    @Override
    public ValidationResult validate(@NonNull TransactionsProto.CreateTransactionCommandRequest target) {
        var errors = new ArrayList<ValidationError>();
        var type = target.getType();

        if (type == TransactionsProto.TransactionType.ASSET_BUY) {
            if (!target.hasSourceAccountId() || target.getSourceAccountId().isBlank()) {
                errors.add(ValidationError.of("sourceAccountId", ValidationErrorCode.CANNOT_BE_NULL, getErrorMessage("sourceAccountId", type)));
            }
            if (!target.hasAssetId() || target.getAssetId().isBlank()) {
                errors.add(ValidationError.of("assetId", ValidationErrorCode.CANNOT_BE_NULL, getErrorMessage("assetId", type)));
            }
        } else if (type == TransactionsProto.TransactionType.ASSET_SELL) {
            if (!target.hasDestinationAccountId() || target.getDestinationAccountId().isBlank()) {
                errors.add(ValidationError.of("destinationAccountId", ValidationErrorCode.CANNOT_BE_NULL, getErrorMessage("destinationAccountId", type)));
            }
            if (!target.hasAssetId() || target.getAssetId().isBlank()) {
                errors.add(ValidationError.of("assetId", ValidationErrorCode.CANNOT_BE_NULL, getErrorMessage("assetId", type)));
            }
        } else if (type == TransactionsProto.TransactionType.TRANSFER) {
            if (!target.hasSourceAccountId() || target.getSourceAccountId().isBlank()) {
                errors.add(ValidationError.of("sourceAccountId", ValidationErrorCode.CANNOT_BE_NULL, getErrorMessage("sourceAccountId", type)));
            }
            if (!target.hasDestinationAccountId() || target.getDestinationAccountId().isBlank()) {
                errors.add(ValidationError.of("destinationAccountId", ValidationErrorCode.CANNOT_BE_NULL, getErrorMessage("destinationAccountId", type)));
            }
        } else if (type == TransactionsProto.TransactionType.DEPOSIT) {
            if (!target.hasDestinationAccountId() || target.getDestinationAccountId().isBlank()) {
                errors.add(ValidationError.of("destinationAccountId", ValidationErrorCode.CANNOT_BE_NULL, getErrorMessage("destinationAccountId", type)));
            }
        } else if (type == TransactionsProto.TransactionType.WITHDRAW) {
            if (!target.hasSourceAccountId() || target.getSourceAccountId().isBlank()) {
                errors.add(ValidationError.of("sourceAccountId", ValidationErrorCode.CANNOT_BE_NULL, getErrorMessage("sourceAccountId", type)));
            }
        } else if (type == TransactionsProto.TransactionType.DIVIDEND) {
            if (!target.hasAssetId() || target.getAssetId().isBlank()) {
                errors.add(ValidationError.of("assetId", ValidationErrorCode.CANNOT_BE_NULL, getErrorMessage("assetId", type)));
            }
            if (!target.hasDestinationAccountId() || target.getDestinationAccountId().isBlank()) {
                errors.add(ValidationError.of("destinationAccountId", ValidationErrorCode.CANNOT_BE_NULL, getErrorMessage("destinationAccountId", type)));
            }
        } else if (type == TransactionsProto.TransactionType.INTEREST) {
            if (!target.hasAssetId() || target.getAssetId().isBlank()) {
                errors.add(ValidationError.of("assetId", ValidationErrorCode.CANNOT_BE_NULL, getErrorMessage("assetId", type)));
            }
            if (!target.hasDestinationAccountId() || target.getDestinationAccountId().isBlank()) {
                errors.add(ValidationError.of("destinationAccountId", ValidationErrorCode.CANNOT_BE_NULL, getErrorMessage("destinationAccountId", type)));
            }
        } else if (type == TransactionsProto.TransactionType.CASHBACK) {
            if (!target.hasDestinationAccountId() || target.getDestinationAccountId().isBlank()) {
                errors.add(ValidationError.of("destinationAccountId", ValidationErrorCode.CANNOT_BE_NULL, getErrorMessage("destinationAccountId", type)));
            }
        } else {
            errors.add(ValidationError.of("type", ValidationErrorCode.INVALID_VALUE, "Invalid transaction type '" + type + "'"));
        }

        return ValidationResult.builder().errors(errors).build();
    }

    private String getErrorMessage(String field, TransactionsProto.TransactionType type) {
        return field + " cannot be null for " + type + " transactions";
    }
}
