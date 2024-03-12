package br.com.eventhorizon.common.concurrent;

import br.com.eventhorizon.common.concurrent.impl.ExecutorServiceFactoryImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public interface ExecutorServiceFactory {

    static ExecutorServiceFactory getDefaultExecutorServiceFactory() {
        return ExecutorServiceFactoryImpl.getInstance();
    }

    ExecutorService createThreadPoolExecutorService(String threadNamePrefix);

    ScheduledExecutorService createScheduledExecutorService(String threadNamePrefix);
}
