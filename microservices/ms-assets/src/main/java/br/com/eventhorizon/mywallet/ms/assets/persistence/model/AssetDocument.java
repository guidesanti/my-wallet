package br.com.eventhorizon.mywallet.ms.assets.persistence.model;

import br.com.eventhorizon.mywallet.common.repository.BaseModel;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document(collection = "assets")
public class AssetDocument extends BaseModel {

    private String shortName;

    private String longName;

    private String strategy;

    private String type;

    @Singular
    private Map<String, String> properties;
}
