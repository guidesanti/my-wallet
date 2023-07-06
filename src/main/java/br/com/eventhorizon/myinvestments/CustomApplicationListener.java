package br.com.eventhorizon.myinvestments;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class CustomApplicationListener implements ApplicationListener<ApplicationEvent>  {

  @Override
  public void onApplicationEvent(ApplicationEvent event) {
    System.out.println("Application event: " + event);

//    if (event instanceof ApplicationEnvironmentPreparedEvent) {
//      PropertyManager.init(((ApplicationEnvironmentPreparedEvent) event).getEnvironment());
//    } else if (event instanceof EnvironmentChangeEvent) {
//      // TODO
//      EnvironmentChangeEvent evt = (EnvironmentChangeEvent) event;
//      int a = 10;
//    }
  }
}
