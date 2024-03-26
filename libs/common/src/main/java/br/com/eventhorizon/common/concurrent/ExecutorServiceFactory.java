package br.com.eventhorizon.common.concurrent;

import br.com.eventhorizon.common.concurrent.impl.ExecutorServiceFactoryImpl;
import br.com.eventhorizon.common.concurrent.impl.ThreadFactoryImpl;

import java.util.concurrent.*;

public interface ExecutorServiceFactory {

    static ExecutorServiceFactory getDefaultExecutorServiceFactory() {
        return ExecutorServiceFactoryImpl.getInstance();
    }

    default ExecutorService createThreadPoolExecutorService(String threadNamePrefix) {
        return createThreadPoolExecutorService(threadNamePrefix, 1, Integer.MAX_VALUE, 10L, TimeUnit.SECONDS);
    }

    ExecutorService createThreadPoolExecutorService(String threadNamePrefix,
                                                    int corePoolSize,
                                                    int maximumPoolSize,
                                                    long keepAliveTime,
                                                    TimeUnit unit);

    ScheduledExecutorService createScheduledExecutorService(String threadNamePrefix);
}
