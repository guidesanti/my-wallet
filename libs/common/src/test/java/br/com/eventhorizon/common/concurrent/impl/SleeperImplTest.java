package br.com.eventhorizon.common.concurrent.impl;

import br.com.eventhorizon.common.concurrent.Sleeper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class SleeperImplTest {

    private final Sleeper sleeper = Sleeper.getDefaultSleeper();

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    private final ThreadFactoryImpl threadFactory = new ThreadFactoryImpl("test");

    @Test
    public void testSleep() {
        var duration = Duration.ofMillis(100);
        var ini = System.currentTimeMillis();
        sleeper.sleep(duration);
        var end = System.currentTimeMillis();
        assertTrue(end - ini >= duration.toMillis());
    }

    @Test
    public void testSleepWithWakeUp() throws ExecutionException, InterruptedException {
        var duration = Duration.ofMillis(10000);
        var wakeup = new AtomicBoolean(false);
        var future = executorService.submit(() -> {
            var ini1 = System.currentTimeMillis();
            sleeper.sleep(duration, wakeup);
            var end = System.currentTimeMillis();
            return end - ini1;
        });
        sleeper.sleep(Duration.ofMillis(100));
        wakeup.set(true);
        var elapsed = future.get();
        assertTrue(elapsed <= 200);
    }

    @Test
    public void testSleepReentrance() throws ExecutionException, InterruptedException {
        var duration = Duration.ofMillis(10000);

        var wakeup1 = new AtomicBoolean(false);
        var elapsed1 = new AtomicLong();
        var thread1 = threadFactory.newThread(() -> {
            var ini1 = System.currentTimeMillis();
            sleeper.sleep(duration, wakeup1);
            var end = System.currentTimeMillis();
            elapsed1.set(end - ini1);
        });

        var wakeup2 = new AtomicBoolean(false);
        var elapsed2 = new AtomicLong();
        var thread2 = threadFactory.newThread(() -> {
            var ini1 = System.currentTimeMillis();
            sleeper.sleep(duration, wakeup2);
            var end = System.currentTimeMillis();
            elapsed2.set(end - ini1);
        });

        thread1.start();
        thread2.start();

        Thread.sleep(1000);
        wakeup1.set(true);

        Thread.sleep(1000);
        wakeup2.set(true);

        log.info("Elapsed1: {}", elapsed1);
        log.info("Elapsed2: {}", elapsed2);

        assertTrue(elapsed1.get() <= 1500);
        assertTrue(elapsed2.get() >= 1500 && elapsed2.get() <= 2500);
    }
}
