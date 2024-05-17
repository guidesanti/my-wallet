package br.com.eventhorizon.common.concurrent.impl;

import br.com.eventhorizon.common.concurrent.Sleeper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SleeperImpl implements Sleeper {

    private static final class InstanceHolder {
        private static final SleeperImpl instance = new SleeperImpl();
    }

    public static SleeperImpl getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public void sleep(Duration duration) throws InterruptedException {
        System.out.println("Sleeping " + duration + " thread " + Thread.currentThread().getName());
        Thread.sleep(duration.toMillis());
    }

    @Override
    public void sleep(Duration duration, AtomicBoolean wakeUp) throws InterruptedException {
        var start = System.currentTimeMillis();
        var remaining = duration.toMillis();
        while (!wakeUp.get() && remaining > 0) {
            sleep(Duration.ofMillis(Math.min(100, duration.toMillis())));
            remaining = start + duration.toMillis() - System.currentTimeMillis();
        }
    }
}
