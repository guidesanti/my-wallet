package br.com.eventhorizon.mywallet.ms.assets.business.model;

import br.com.eventhorizon.common.properties.Properties;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Asset implements Serializable {

    // TODO: Add serial

    private String id;

    private String shortName;

    private String longName;

    private AssetType type;

    private String description;

    private Properties properties;

//    @Singular
//    private Map<String, String> properties;
}
