package br.com.eventhorizon.myinvestments.messaging;

import br.com.eventhorizon.myinvestments.model.Asset;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AssetManagementMessage {

    private Operation operation;

    private Asset asset;

    public enum Operation {
        CREATE,
        UPDATE,
        DELETE;
    }
}
