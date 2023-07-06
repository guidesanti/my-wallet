package br.com.eventhorizon.myinvestments.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document(collection = "assetTypes")
public class AssetType extends BaseModel {

    private String name;

    private String description;
}
