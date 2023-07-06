package br.com.eventhorizon.myinvestments.messaging;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuyAsset {

    private String name;

    private Integer amount;

    private Double price;
}
