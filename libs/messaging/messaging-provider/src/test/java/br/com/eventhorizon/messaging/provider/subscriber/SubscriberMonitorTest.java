package br.com.eventhorizon.messaging.provider.subscriber;

import br.com.eventhorizon.common.concurrent.Runner;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class SubscriberMonitorTest {

    @Test
    void test() throws InterruptedException {
        // Given
        var subscriber = mock(Subscriber.class);
        doReturn(Runner.State.CRASHED).when(subscriber).getState();
        var monitor = new SubscriberMonitor(List.of(subscriber));

        // When
        monitor.startMonitoring();

        // Then
        Thread.sleep(2000);
        monitor.stopMonitoring();

        Thread.sleep(5000);

        monitor.startMonitoring();
        Thread.sleep(3100);
        monitor.stopMonitoring();
    }
}
