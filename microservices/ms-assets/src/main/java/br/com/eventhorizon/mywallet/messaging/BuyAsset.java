package br.com.eventhorizon.mywallet.messaging;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuyAsset {

    private String name;

    private Integer amount;

    private Double price;
}
