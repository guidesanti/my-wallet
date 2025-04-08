package br.com.eventhorizon.common.concurrent.impl;

import br.com.eventhorizon.common.concurrent.ExecutorServiceFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExecutorServiceFactoryImpl implements ExecutorServiceFactory {

    private static final class InstanceHolder {
        private static final ExecutorServiceFactoryImpl instance = new ExecutorServiceFactoryImpl();
    }

    public static ExecutorServiceFactoryImpl getInstance() {
        return InstanceHolder.instance;
    }

    @Override
    public ExecutorService createThreadPoolExecutorService(String threadNamePrefix,
                                                           int corePoolSize,
                                                           int maximumPoolSize,
                                                           long keepAliveTime,
                                                           TimeUnit unit) {
        return ContextAwareThreadPoolExecutor.newWithInheritedMdc(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                new SynchronousQueue<>(), new ThreadFactoryImpl(threadNamePrefix));
    }

    @Override
    public ScheduledExecutorService createScheduledExecutorService(String threadNamePrefix) {
        return Executors.newSingleThreadScheduledExecutor(new ThreadFactoryImpl(threadNamePrefix));
    }
}
