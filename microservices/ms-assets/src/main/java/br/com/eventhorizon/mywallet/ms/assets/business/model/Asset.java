package br.com.eventhorizon.mywallet.ms.assets.business.model;

import lombok.*;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Asset {

    private String id;

    private String shortName;

    private String longName;

    private String strategy;

    private String type;

    @Singular
    private Map<String, String> properties;
}
