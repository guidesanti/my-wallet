package br.com.eventhorizon.myinvestments.controller;

import br.com.eventhorizon.myinvestments.MyWalletRunner;
import br.com.eventhorizon.myinvestments.dto.HealthStatus;
import br.com.eventhorizon.myinvestments.dto.Response;
import br.com.eventhorizon.myinvestments.dto.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//record Clazz (int a, int b) {
//}

@RestController
@RequestMapping("/v1/health")
@Slf4j
public class HealthController {

  @Autowired
  public HealthController(MyWalletRunner runner) throws Exception {
  }

  @GetMapping("")
  public ResponseEntity<Response<HealthStatus>> getHealth() throws InterruptedException {
    log.info("GET health");
    var health = HealthStatus.builder().status(HealthStatus.Status.HEALTHY).build();
    return new ResponseEntity<>(new Response<>(ResponseStatus.SUCCESS, health, null), HttpStatus.OK);
  }

//  @GetMapping("/2")
//  public Flux<Clazz> get2() throws InterruptedException {
//    final Flux<Clazz> brands = Flux.just(new Clazz(1, 2), new Clazz(3, 4));
//    brands.subscribe(System.out::println);
//    System.out.println("Thread id: " + Thread.currentThread().threadId());
//    return brands;
//  }

//  @GetMapping("/2")
//  public Mono<ServerResponse> get2() throws InterruptedException {
//    final Mono<String> brands = Mono.just("Under Armour");
//    brands.subscribe(System.out::println);
//    System.out.println("Thread id: " + Thread.currentThread().threadId());
//    return ServerResponse
//            .ok()
//            .contentType(MediaType.APPLICATION_JSON)
//            .bodyValue(brands.map(s -> s))
//            .switchIfEmpty(ServerResponse.notFound().build());
//  }
}
