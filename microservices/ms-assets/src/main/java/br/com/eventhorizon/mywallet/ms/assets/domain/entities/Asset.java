package br.com.eventhorizon.mywallet.ms.assets.domain.entities;

import br.com.eventhorizon.common.properties.Properties;

import br.com.eventhorizon.mywallet.ms.assets.domain.exception.AssetValidationException;
import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;
import br.com.fluentvalidator.predicate.LogicalPredicate;
import br.com.fluentvalidator.predicate.StringPredicate;
import lombok.*;

import java.util.ArrayList;
import java.util.function.Predicate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Asset {

    private String id;

    private String shortName;

    private String longName;

    private AssetType type;

    private String description;

    private Properties properties;

    public static ValidationResult canCreate(String id, String shortName, String longName, AssetType type, String description, Properties properties) {
        var errors = new ArrayList<Error>();
        errors.addAll(new IdValidator().validate(id).getErrors());
        errors.addAll(new ShortNameValidator().validate(shortName).getErrors());
        return errors.isEmpty() ? ValidationResult.ok() : ValidationResult.fail(errors);
    }

    public static Asset create(String id, String shortName, String longName, AssetType type, String description, Properties properties) {
        canCreate(id, shortName, longName, type, description, properties).isInvalidThrow(AssetValidationException.class);
        return new Asset(id, shortName, longName,type, description, properties);
    }

    public ValidationResult canUpdate(AssetType type, String description, Properties properties) {
        // TODO
        return null;
    }

    public void update(AssetType type, String description, Properties properties) {
        canUpdate(type, description, properties).isInvalidThrow(AssetValidationException.class);
        this.type = type;
        this.description = description;
        this.properties = properties;
    }

    private static class IdValidator extends AbstractValidator<String> {

        @Override
        public void rules() {
            ruleFor(s -> s)
                    .must(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                    .withFieldName("id")
                    .withCode("INVALID_ID")
                    .withMessage("Invalid ID");
        }
    }

    private static class ShortNameValidator extends AbstractValidator<String> {

        @Override
        public void rules() {
            ruleFor(s -> s)
                    .must(LogicalPredicate.not(StringPredicate.stringEmptyOrNull()))
                    .withFieldName("shortName")
                    .withCode("INVALID_SHORT_NAME")
                    .withMessage("Invalid short name")
                    .must(Predicate.not(StringPredicate.stringEmptyOrNull()))
                    .withFieldName("");
            ruleFor("bla", s -> s)
        }
    }
}
