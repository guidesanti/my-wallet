package br.com.eventhorizon.mywallet.ms.kafka.producer.init;

import br.com.eventhorizon.common.http.controller.BaseControllerAdvice;
import br.com.eventhorizon.common.http.filter.TracingFilter;
import br.com.eventhorizon.mywallet.ms.kafka.producer.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
@Import({
        BaseControllerAdvice.class,
        TracingFilter.class
})
public class AppConfig {

    @Autowired
    public AppConfig(ApplicationProperties props) {
        log.info("===================================================================================================");
        log.info("Application properties: {}", props);
        log.info("===================================================================================================");
    }
}
