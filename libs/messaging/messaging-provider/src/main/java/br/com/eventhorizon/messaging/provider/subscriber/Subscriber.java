package br.com.eventhorizon.messaging.provider.subscriber;

import br.com.eventhorizon.common.concurrent.impl.DefaultRunner;
import br.com.eventhorizon.messaging.provider.subscriber.processor.*;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.SingleSubscription;
import br.com.eventhorizon.messaging.provider.subscriber.subscription.Subscription;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Slf4j
public class Subscriber<T> extends DefaultRunner {

    @Getter
    private final Subscription<T> subscription;

    private final SubscriberMessagePoller<T> poller;

    private final SubscriberMessageProcessorListener<T> listener;

    public Subscriber(String name, ExecutorService executorService, Subscription<T> subscription, SubscriberMessagePoller<T> poller, SubscriberMessageProcessorListener<T> listener) {
        super(name, executorService);
        this.subscription = subscription;
        this.poller = poller;
        this.listener = listener;
    }

    @Override
    public void run() {
//        int count = 6;
        try {
            initPoller();
            while (state == State.RUNNING) {
                var polledMessages = poll();
                if (polledMessages != null && !polledMessages.isEmpty()) {
                    process(polledMessages);
                }
//                count--;
//                if (count == 0) {
//                    throw new RuntimeException("STOPPING");
//                }
            }
        } catch (Exception ex) {
            log.error(String.format("[%s] Unexpected exception occurred while subscriber was running", this), ex);
            throw ex;
        } finally {
            closePoller();
        }
    }

    private void initPoller() {
        try {
            poller.init();
        } catch (Exception ex) {
            log.error(String.format("[%s] Failed to initialize message poller", this), ex);
            throw ex;
        }
    }

    private List<SubscriberPolledMessageBatch<T>> poll() {
        try {
            return poller.poll();
        } catch (Exception ex) {
            log.error(String.format("[%s] Exception on message poller", this), ex);
            // We should stop polling now, since a problem occurred on polling
            throw ex;
        }
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
            log.error(String.format("[%s] Exception on message processor", this), ex);
            // If exception occurs here we won't stop polling since it was a processing issue
        }
    }

    private void closePoller() {
        try {
            poller.close();
        } catch (Exception ex) {
            log.error(String.format("[%s] Failed to close message poller", this), ex);
        }
    }
}
