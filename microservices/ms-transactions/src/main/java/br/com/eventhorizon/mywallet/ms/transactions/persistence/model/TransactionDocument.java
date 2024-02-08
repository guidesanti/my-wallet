package br.com.eventhorizon.mywallet.ms.transactions.persistence.model;

import br.com.eventhorizon.common.error.Error;
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
@Document(collection = "transactions")
public class TransactionDocument extends BaseModel {

    private String settledAt;

    private String state;

    private String type;

    private String units;

    private String price;

    private String sourceAccountId;

    private String destinationAccountId;

    private String assetId;

    private String description;

    @Singular
    private List<Error> errors;
}
