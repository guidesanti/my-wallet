package br.com.eventhorizon.mywallet.ms.assets.business.model;

import lombok.*;

import java.util.Map;

@Getter
@Builder
public class Asset {

    private String id;

    @NonNull
    private String shortName;

    @NonNull
    private String longName;

    @NonNull
    private String strategy;

    @NonNull
    private String type;

    @Singular
    private Map<String, String> properties;
}
