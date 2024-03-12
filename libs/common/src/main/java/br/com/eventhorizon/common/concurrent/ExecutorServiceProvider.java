package br.com.eventhorizon.common.concurrent;

import br.com.eventhorizon.common.concurrent.impl.ExecutorServiceProviderImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public interface ExecutorServiceProvider {

    static ExecutorServiceProvider getDefaultExecutorServiceProvider() {
        return ExecutorServiceProviderImpl.getInstance();
    }

    ExecutorService getIoExecutorService();

    ScheduledExecutorService getIdleExecutorService();
}
