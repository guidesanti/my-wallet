package br.com.eventhorizon.common.concurrent.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class SimpleThreadFactory implements ThreadFactory {

    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final String threadNamePrefix;

    @Override
    public Thread newThread(@NonNull Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName("eh-" + threadNamePrefix + "-thread-" + threadNumber.getAndIncrement());
        thread.setDaemon(false);
        thread.setPriority(Thread.NORM_PRIORITY);
        return thread;
    }
}
