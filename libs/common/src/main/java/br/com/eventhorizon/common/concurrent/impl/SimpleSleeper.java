package br.com.eventhorizon.common.concurrent.impl;

import br.com.eventhorizon.common.concurrent.Sleeper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleSleeper implements Sleeper {

    private static final class InstanceHolder {
        private static final SimpleSleeper instance = new SimpleSleeper();
    }

    public static SimpleSleeper getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public void sleep(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            // Do nothing
        }
    }

    @Override
    public void sleep(Duration duration, AtomicBoolean wakeUp) {
        var start = System.currentTimeMillis();
        var remaining = duration.toMillis();
        while (!wakeUp.get() && remaining > 0) {
            sleep(Duration.ofMillis(Math.min(100, duration.toMillis())));
            remaining = start + duration.toMillis() - System.currentTimeMillis();
        }
    }
}
