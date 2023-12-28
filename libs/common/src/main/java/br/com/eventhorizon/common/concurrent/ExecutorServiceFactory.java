package br.com.eventhorizon.common.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public interface ExecutorServiceFactory {

    ExecutorService createThreadPoolExecutorService(String threadNamePrefix);

    ScheduledExecutorService createScheduledExecutorService(String threadNamePrefix);
}
