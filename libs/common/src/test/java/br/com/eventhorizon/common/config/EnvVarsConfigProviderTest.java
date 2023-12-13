package br.com.eventhorizon.common.config;

import br.com.eventhorizon.common.config.impl.EnvVarsConfigProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnvVarsConfigProviderTest {

    private static final String PROVIDER_NAME = "default-env-vars-config-provider";

    private final ConfigProvider provider = new EnvVarsConfigProvider();

    @Test
    public void testGetName() {
        assertEquals(PROVIDER_NAME, provider.getName());
    }

//    @Test
//    public void testGetValue() {
//        assertEquals(PROVIDER_NAME, provider.getValue("some"));
//    }
}
