package br.com.eventhorizon.messaging.provider.subscriber;

import br.com.eventhorizon.common.concurrent.ExecutorServiceFactory;
import br.com.eventhorizon.common.concurrent.Runner;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SubscriberMonitor {

    private static final String MONITOR_THREAD_PREFIX = "subscriber-monitor";

    private static final long MONITOR_LOOP_PERIOD_MILLIS = 1000;

    private final ScheduledExecutorService scheduledExecutorService;

    private final List<Subscriber<?>> subscribers;

    private ScheduledFuture<?> monitorTask;

    public SubscriberMonitor(List<Subscriber<?>> subscribers) {
        if (Objects.isNull(subscribers)) {
            throw new IllegalArgumentException("Subscribers list cannot be null");
        }
        this.scheduledExecutorService = ExecutorServiceFactory.getDefaultExecutorServiceFactory()
                .createScheduledExecutorService(MONITOR_THREAD_PREFIX);
        this.subscribers = subscribers;
    }

    public synchronized void startMonitoring() {
        log.info("Starting subscriber monitor");
        if (monitorTask != null && !monitorTask.isDone()) {
            throw new IllegalStateException("Subscriber monitor is already running");
        }
        monitorTask = scheduledExecutorService.scheduleAtFixedRate(this::run, 0L, MONITOR_LOOP_PERIOD_MILLIS, TimeUnit.MILLISECONDS);
    }

    public synchronized void stopMonitoring() {
        log.info("Stopping subscriber monitor");
        if (monitorTask != null) {
            monitorTask.cancel(true);
            monitorTask = null;
        }
    }

    private void run() {
        try {
            log.debug("Subscriber monitor: monitoring started");
            for (Subscriber<?> subscriber : subscribers) {
                if (Thread.currentThread().isInterrupted()) {
                    log.warn("Subscriber monitor: interrupted");
                    break;
                }
                checkSubscriber(subscriber);
            }
        } catch (Exception ex) {
            log.error("Error while monitoring subscribers", ex);
        } finally {
            log.debug("Subscriber monitor: monitoring finished");
        }
    }

    private void checkSubscriber(Subscriber<?> subscriber) {
        if (Objects.isNull(subscriber)) {
            return;
        }
        try {
            var subscriberState = subscriber.getState();
            log.debug("Subscriber '{}' is '{}'", subscriber, subscriberState);
            if (subscriberState == Runner.State.STOPPED || subscriberState == Runner.State.CRASHED) {
                log.info("Restarting subscriber {} ...", subscriber);
                subscriber.start();
            }
        } catch (Exception ex) {
            log.error(String.format("Error while checking subscriber '%s'", subscriber), ex);
        }
    }
}
