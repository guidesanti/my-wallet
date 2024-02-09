package br.com.eventhorizon.mywallet.ms.assets.business.model;

import br.com.eventhorizon.common.properties.PropertyDefinitions;
import br.com.eventhorizon.mywallet.ms.assets.business.model.enums.Income;
import br.com.eventhorizon.mywallet.ms.assets.business.model.enums.Strategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetType implements Serializable {

    @Serial
    private static final long serialVersionUID = -6991022833013191610L;

    private String id;

    private String name;

    private Strategy strategy;

    private Income income;

    private boolean tradeableOnStockExchange;

    private String description;

    private PropertyDefinitions propertyDefinitions;
}
