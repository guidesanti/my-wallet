package br.com.eventhorizon.mywallet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.REACTIVE;

@Component
@ConditionalOnWebApplication(type = REACTIVE)
@Slf4j
public class MyWalletRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("Running as REACTIVE");
    }
}
