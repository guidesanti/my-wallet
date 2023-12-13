package br.com.eventhorizon.mywallet.ms.assets.config;

import br.com.eventhorizon.common.config.Config;
import br.com.eventhorizon.common.http.controller.BaseControllerAdvice;
import br.com.eventhorizon.common.http.filter.TracingFilter;
import br.com.eventhorizon.saga.messaging.DefaultSagaPublisher;
import br.com.eventhorizon.mywallet.ms.assets.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@EnableAutoConfiguration
@Import({
        BaseControllerAdvice.class,
        TracingFilter.class,
        DefaultSagaPublisher.class
})
public class AppConfig {

    @Autowired
    public AppConfig(ApplicationProperties props, Config config) {
        log.info("===================================================================================================");
        log.info("Application properties: {}", props);
        log.info("===================================================================================================");

        log.info("===================================================================================================");
        log.info("Application properties 2:");
        log.info("MY_CUSTOM_ENV: {}", config.getValue("MY_CUSTOM_ENV"));
        log.info("my.custom.property: {}", config.getValue("my.custom.property"));
        log.info("===================================================================================================");
    }
}
