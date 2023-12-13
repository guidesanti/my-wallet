package br.com.eventhorizon.common.config;

public interface ConfigProvider {

    String getName();

    String getValue(String name);
}
