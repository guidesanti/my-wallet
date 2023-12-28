package br.com.eventhorizon.common.concurrent.impl;

import br.com.eventhorizon.common.concurrent.ExecutorServiceFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExecutorServiceProvider {

    private final ExecutorServiceFactory executorServiceFactory = SimpleExecutorServiceFactory.getInstance();

    private volatile ExecutorService ioExecutorService;

    private volatile ScheduledExecutorService idleExecutorService;

    private static final class InstanceHolder {
        private static final ExecutorServiceProvider instance = new ExecutorServiceProvider();
    }

    public static ExecutorServiceProvider getInstance() {
        return InstanceHolder.instance;
    }

    public ExecutorService getIoExecutorService() {
        if (ioExecutorService == null) {
            synchronized (new Object()) {
                if (ioExecutorService == null) {
                    ioExecutorService = executorServiceFactory.createThreadPoolExecutorService("io");
                }
            }
        }
        return ioExecutorService;
    }

    public ScheduledExecutorService getIdleExecutorService() {
        if (idleExecutorService == null) {
            synchronized (new Object()) {
                if (idleExecutorService == null) {
                    idleExecutorService = executorServiceFactory.createScheduledExecutorService("idle");
                }
            }
        }
        return idleExecutorService;
    }
}
