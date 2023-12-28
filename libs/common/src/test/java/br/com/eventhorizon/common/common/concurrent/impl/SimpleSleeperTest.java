package br.com.eventhorizon.common.common.concurrent.impl;

import br.com.eventhorizon.common.concurrent.Sleeper;
import br.com.eventhorizon.common.concurrent.impl.SimpleSleeper;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleSleeperTest {

    private final Sleeper sleeper = SimpleSleeper.getInstance();

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

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
        var future1 = executorService.submit(() -> {
            var ini1 = System.currentTimeMillis();
            sleeper.sleep(duration, wakeup1);
            var end = System.currentTimeMillis();
            return end - ini1;
        });

        var wakeup2 = new AtomicBoolean(false);
        var future2 = executorService.submit(() -> {
            var ini1 = System.currentTimeMillis();
            sleeper.sleep(duration, wakeup2);
            var end = System.currentTimeMillis();
            return end - ini1;
        });

        sleeper.sleep(Duration.ofMillis(100));
        wakeup1.set(true);
        var elapsed1 = future1.get();

        sleeper.sleep(Duration.ofMillis(100));
        wakeup2.set(true);
        var elapsed2 = future2.get();

        assertTrue(elapsed1 <= 200);
        assertTrue(elapsed2 >= 100 && elapsed2 <= 300);
    }
}
