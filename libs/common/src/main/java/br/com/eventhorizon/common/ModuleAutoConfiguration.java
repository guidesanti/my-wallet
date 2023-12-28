package br.com.eventhorizon.common;

import br.com.eventhorizon.common.concurrent.impl.ExecutorServiceProvider;
import br.com.eventhorizon.common.config.Config;
import br.com.eventhorizon.common.config.ConfigProvider;
import br.com.eventhorizon.common.runtime.impl.ApplicationLifecycleDispatcher;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import java.util.concurrent.ExecutorService;

@AutoConfiguration
public class ModuleAutoConfiguration {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ApplicationLifecycleDispatcher applicationLifecycleDispatcher() {
        return new ApplicationLifecycleDispatcher();
    }

    @Bean
    public ExecutorServiceProvider executorServiceProvider() {
        return ExecutorServiceProvider.getInstance();
    }

    @Bean
    public ExecutorService ioExecutorService(ExecutorServiceProvider executorServiceProvider) {
        return executorServiceProvider.getIoExecutorService();
    }

    @Bean
    public Config config(Environment environment) {
        return ConfigProvider.getConfig(environment);
    }
}
