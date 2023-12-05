package br.com.eventhorizon.mywallet.ms.assets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    try {
      int startDelay = Integer.parseInt(System.getProperty(ApplicationProperties.START_DELAY_PROP_NAME, "0"));
      log.info("Application start delay {} ...", startDelay);
      Thread.sleep(startDelay);
      log.info("Application starting ...");
      SpringApplication app = new SpringApplication(Application.class);
      app.setWebApplicationType(WebApplicationType.SERVLET);
      app.run(args);
    } catch (Exception e) {
      log.error("Application startup error: " + e);
    }
  }
}
