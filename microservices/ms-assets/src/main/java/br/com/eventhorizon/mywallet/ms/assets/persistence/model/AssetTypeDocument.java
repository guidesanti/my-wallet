package br.com.eventhorizon.mywallet.ms.assets.persistence.model;

import br.com.eventhorizon.common.repository.BaseModel;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document(collection = "asset-types")
public class AssetTypeDocument extends BaseModel {

    private String name;

    private String strategy;

    private String income;

    private boolean tradeableOnStockExchange;

    private String description;

    @Singular
    private List<PropertyDefinitionDocument<?>> propertyDefinitions;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class PropertyDefinitionDocument<T> {

        private String name;

        private String type;

        private T defaultValue;

        private String description;
    }
}
