package br.com.eventhorizon.mywallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

  private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    try {
//      LOGGER.info("Waiting for debugger to attach ...");
//      Thread.sleep(10000);
      SpringApplication app = new SpringApplication(Application.class);
      app.setWebApplicationType(WebApplicationType.REACTIVE);
      app.run(args);
    } catch (Exception e) {
      LOGGER.error("Application startup error: " + e);
    }
  }

}
