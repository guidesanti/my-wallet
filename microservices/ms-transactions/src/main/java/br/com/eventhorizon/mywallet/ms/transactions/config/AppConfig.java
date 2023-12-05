package br.com.eventhorizon.mywallet.ms.transactions.config;

import br.com.eventhorizon.mywallet.common.http.controller.BaseControllerAdvice;
import br.com.eventhorizon.mywallet.common.http.filter.TracingFilter;
import br.com.eventhorizon.mywallet.common.saga.message.DefaultSagaPublisher;
import br.com.eventhorizon.mywallet.ms.transactions.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@Import({
        BaseControllerAdvice.class,
        TracingFilter.class,
        DefaultSagaPublisher.class
})
public class AppConfig {

    @Autowired
    public AppConfig(ApplicationProperties props) {
        log.info("===================================================================================================");
        log.info("Application properties: {}", props);
        log.info("===================================================================================================");
    }
}
