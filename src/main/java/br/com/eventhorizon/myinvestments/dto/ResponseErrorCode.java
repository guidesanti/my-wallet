package br.com.eventhorizon.myinvestments.dto;

public enum ResponseErrorCode {

  UNKNOWN("UNKNOWN"),
  RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND");

  private final String value;

  ResponseErrorCode(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
