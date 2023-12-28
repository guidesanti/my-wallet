package br.com.eventhorizon.common.runtime.impl;

import br.com.eventhorizon.common.runtime.ApplicationLifecycleListener;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ApplicationLifecycleDispatcher implements ApplicationListener<ApplicationEvent> {

    private final List<ApplicationLifecycleListener> listeners = new ArrayList<>();

    public ApplicationLifecycleDispatcher addListener(ApplicationLifecycleListener listener) {
        listeners.add(listener);
        return this;
    }

    public ApplicationLifecycleDispatcher addListeners(List<ApplicationLifecycleListener> listeners) {
        this.listeners.addAll(listeners);
        return this;
    }

    @Override
    public void onApplicationEvent(@NonNull ApplicationEvent event) {
        if (event instanceof ApplicationStartingEvent) {
            dispatchOnStarting();
        } else if (event instanceof ApplicationStartedEvent) {
            dispatchOnStarted();
        } else if (event instanceof ApplicationReadyEvent) {
            dispatchOnReady();
        } else if (event instanceof ContextClosedEvent) {
            dispatchOnShuttingDown();
        }
    }

    private void dispatchOnStarting() {
        listeners.forEach(ApplicationLifecycleListener::onStarting);
    }

    private void dispatchOnStarted() {
        listeners.forEach(ApplicationLifecycleListener::onStarted);
    }

    private void dispatchOnReady() {
        listeners.forEach(ApplicationLifecycleListener::onReady);
    }

    private void dispatchOnShuttingDown() {
        listeners.forEach(ApplicationLifecycleListener::onShuttingDown);
    }
}
