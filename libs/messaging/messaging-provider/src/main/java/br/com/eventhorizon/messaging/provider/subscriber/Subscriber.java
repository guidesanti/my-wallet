package br.com.eventhorizon.messaging.provider.subscriber;

import br.com.eventhorizon.messaging.provider.subscriber.processor.*;
import br.com.eventhorizon.messaging.provider.subscription.SingleSubscription;
import br.com.eventhorizon.messaging.provider.subscription.Subscription;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Slf4j
@RequiredArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Subscriber<T> {

    @Getter
    @ToString.Include
    private final String name;

    private final ExecutorService executorService;

    private final Runnable runner = this::run;

    @Getter
    private State state = State.CREATED;

    @Getter
    private final Subscription<T> subscription;

    private final SubscriberMessagePoller<T> poller;

    private final SubscriberMessageProcessorListener<T> listener;

    public synchronized boolean start() {
        log.info("[{}] Starting subscriber", this);

        if (state == State.RUNNING) {
            return true;
        }

        if (state == State.CREATED) {
            state = State.STARTING;
            executorService.execute(this.runner);
            log.info("[{}] Subscriber started", this);
            return true;
        }

        log.warn("[{}] Invalid subscriber state transition, cannot transition from {} to {}", this, state, State.RUNNING);

        return false;
    }

    public synchronized boolean stop() {
        log.warn("[{}] Stopping subscriber", this);

        if (state == State.STOPPED) {
            log.warn("[{}] Subscriber already stopped", this);
            return true;
        }

        try {
            state = State.STOPPING;
            log.info("[{}] Waiting for subscriber to stop", this);
            this.wait();
        } catch (InterruptedException ex) {
            log.error(String.format("[%s] Exception occurred while waiting for subscriber to stop", this), ex);
        }

        log.warn("[{}] Subscriber stopped", this);

        return true;
    }

    private void run() {
        try {
            synchronized (this) {
                state = State.RUNNING;
            }
            while (state == State.RUNNING) {
                var polledMessages = poll();
                if (polledMessages != null && !polledMessages.isEmpty()) {
                    process(polledMessages);
                }
            }
        } catch (Exception ex) {
            log.error(String.format("[%s] Unexpected exception occurred while subscriber was running", this), ex);
        } finally {
            synchronized (this) {
                state = State.STOPPED;
                this.notifyAll();
            }
        }
    }

    private List<SubscriberPolledMessageBatch<T>> poll() {
        try {
            return poller.poll();
        } catch (Exception ex) {
            log.error(String.format("[%s] Exception not handled by message poller", this), ex);
        }

        return Collections.emptyList();
    }

    private void process(List<SubscriberPolledMessageBatch<T>> messages) {
        try {
            messages.forEach(polledMessages -> {
                if (subscription instanceof SingleSubscription<T>) {
                    executorService.submit(new SubscriberSingleMessageProcessor<>(subscription, polledMessages, listener));
                } else {
                    executorService.submit(new SubscriberBulkMessageProcessor<>(subscription, polledMessages, listener));
                }
            });
        } catch (Exception ex) {
            log.error(String.format("[%s] Exception not handled by filters/handler", this), ex);
        }
    }

    private enum State {
        CREATED,
        STARTING,
        RUNNING,
        STOPPING,
        STOPPED
    }
}
