package br.com.eventhorizon.common.concurrent;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

public interface Sleeper {

    void sleep(Duration duration);

    void sleep(Duration duration, AtomicBoolean wakeUp);
}
