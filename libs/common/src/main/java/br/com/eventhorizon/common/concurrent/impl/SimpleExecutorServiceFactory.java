package br.com.eventhorizon.common.concurrent.impl;

import br.com.eventhorizon.common.concurrent.ExecutorServiceFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleExecutorServiceFactory implements ExecutorServiceFactory {

    private static final class InstanceHolder {
        private static final SimpleExecutorServiceFactory instance = new SimpleExecutorServiceFactory();
    }

    public static SimpleExecutorServiceFactory getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public ExecutorService createThreadPoolExecutorService(String threadNamePrefix) {
        return new ThreadPoolExecutor(1, Integer.MAX_VALUE, 10L, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new SimpleThreadFactory(threadNamePrefix));
    }

    @Override
    public ScheduledExecutorService createScheduledExecutorService(String threadNamePrefix) {
        return Executors.newSingleThreadScheduledExecutor(new SimpleThreadFactory(threadNamePrefix));
    }
}
