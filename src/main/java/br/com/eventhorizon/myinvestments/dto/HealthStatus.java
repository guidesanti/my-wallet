package br.com.eventhorizon.myinvestments.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthStatus {

  private HealthStatus.Status status;

  private String version;

  private String gitHash;

  private String build;

  public enum Status {

    HEALTHY,
    UNHEALTHY,
    UNKNOWN
  }
}
