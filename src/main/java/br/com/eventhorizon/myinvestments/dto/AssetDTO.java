package br.com.eventhorizon.myinvestments.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

@Data
public class AssetDTO {

    private String id;

    @NotBlank(groups = CreateValidation.class)
    private String name;

    @NotBlank(groups = CreateValidation.class)
    private String strategy;

    @NotBlank(groups = CreateValidation.class)
    private String type;

    private Map<String, String> properties;

    public interface CreateValidation { }

    public interface UpdateValidation { }
}
