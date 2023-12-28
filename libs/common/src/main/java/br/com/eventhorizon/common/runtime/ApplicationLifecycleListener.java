package br.com.eventhorizon.common.runtime;

public interface ApplicationLifecycleListener {

    void onStarting();

    void onStarted();

    void onReady();

    void onShuttingDown();
}
