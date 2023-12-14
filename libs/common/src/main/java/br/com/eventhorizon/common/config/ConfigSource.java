package br.com.eventhorizon.common.config;

public interface ConfigSource {

    String getName();

    String getValue(String name);
}
