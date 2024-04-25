package br.com.eventhorizon.common.concurrent.impl;

import br.com.eventhorizon.common.concurrent.Runner;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
@ToString(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public abstract class DefaultRunner implements Runner {

    @Getter
    @ToString.Include
    protected final String name;

    protected final ExecutorService executorService;

    @Getter
    protected volatile State state = State.CREATED;

    protected Future<?> future = null;

    protected Throwable crashCause = null;

    @Override
    public Optional<Throwable> getCrashCause() {
        return Optional.ofNullable(crashCause);
    }

    private synchronized void setState(State state) {
        this.state = state;
    }

    @Override
    public synchronized void start() {
        log.info("[{}] Starting runner", this);

        if (state == State.STARTING || state == State.RUNNING) {
            log.warn("[{}] Runner is already {}", this, state);
            return;
        }

        if (state == State.CREATED || state == State.STOPPED || state == State.CRASHED) {
            reset();
            state = State.STARTING;
            logState(false);
            future = executorService.submit(this::doRun);
            return;
        }

        var message = String.format("[%s] Invalid runner state transition, cannot transition from %s to %s", this, state, State.STARTING);
        log.error(message);
        throw new IllegalStateException(message);
    }

    @Override
    public synchronized void stop() {
        stop(null);
    }

    @Override
    public synchronized void stop(Duration timeout) {
        log.warn("[{}] Stopping runner", this);

        if (state == State.STOPPING || state == State.STOPPED || state == State.CRASHED) {
            log.warn("[{}] Runner is already {}", this, state);
            return;
        }

        if (state == State.RUNNING) {
            try {
                state = State.STOPPING;
                logState(true);
                log.info("[{}] Waiting for runner to stop", this);
                if (Objects.isNull(timeout)) {
                    this.wait();
                } else {
                    this.wait(timeout.toMillis());
                    if (!future.isDone()) {
                        future.cancel(true);
                    }
                }
            } catch (InterruptedException ex) {
                log.error(String.format("[%s] Exception occurred while waiting for subscriber to stop", this), ex);
            }
            return;
        }

        var message = String.format("[%s] Invalid runner state transition, cannot transition from %s to %s", this, state, State.STOPPING);
        log.error(message);
        throw new IllegalStateException(message);
    }

    private void reset() {
        future = null;
        crashCause = null;
    }

    private void doRun() {
        try {
            setState(State.RUNNING);
            logState(false);
            run();
            setState(State.STOPPED);
            logState(false);
        } catch (Exception ex) {
            log.error(String.format("[%s] Unexpected exception occurred on runner", this), ex);
            setState(State.CRASHED);
            logState(true);
            crashCause = ex;
            throw ex;
        } finally {
            synchronized (this) {
                this.notifyAll();
            }
        }
    }

    protected void logState(boolean warn) {
        if (warn) {
            log.warn("[{}] Runner is {}", this, state);
        } else {
            log.info("[{}] Runner is {}", this, state);
        }
    }
}
