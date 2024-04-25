package br.com.eventhorizon.common.concurrent;

import br.com.eventhorizon.common.concurrent.impl.SleeperImpl;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

public interface Sleeper {

    static Sleeper getDefaultSleeper() {
        return SleeperImpl.getInstance();
    }

    void sleep(Duration duration) throws InterruptedException;

    void sleep(Duration duration, AtomicBoolean wakeUp) throws InterruptedException;
}
