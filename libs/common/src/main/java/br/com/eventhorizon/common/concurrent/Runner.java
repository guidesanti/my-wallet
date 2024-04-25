package br.com.eventhorizon.common.concurrent;

import lombok.Getter;

import java.time.Duration;
import java.util.Optional;

/**
 * The Runner interface should be implemented by any class whose instances are intended to be executed by a separated
 * thread.
 * The Runner interface offers control methods like start and stop so the creater can control when the runner should
 * start/stop.
 * The stop method also provides watting feature so the caller can decide to wait for a maximum time for the runner
 * to gracefully stop.
 * <p>
 * Just after creating the Runner its state should be CREATED.
 * The allowed state changes of a Runner are:
 * - CREATED -> STARTING: After calling start() method
 * - CREATING -> RUNNING: After starting code has already finished and the method run() is called from a new thread
 * - RUNNING -> STOPPING: After calling stop() method
 * - STOPPING -> STOPPED: After the run() method has gracefully finished
 * - RUNNING -> CRASHED: If an exception occurs in run() method
 */
public interface Runner {

    /**
     * Starts the runner.
     * <p>
     * Starts the runner if not already running.
     * If Runner is STARTING or RUNNING, ignore this call.
     * If runner Is STOPPING or STOPPED <code>IllegalStateException</code> is thrown.
     */
    void start();

    /**
     * Stops the runner.
     * <p>
     * Stops the runner if not already stopped.
     * If Runner is STOPPING or STOPPED, ignore this call.
     * Otherwise, will block until the runner stops.
     */
    void stop();

    /**
     * Stops the runner.
     * <p>
     * Stops the runner if not already stopped.
     * If Runner is STOPPING or STOPPED, ignore this call.
     * Otherwise, will block until the runner stops or the <code>timeout</code> has been reached.
     */
    void stop(Duration timeout);

    /**
     * After starting the runner, this method will be called from a new thread.
     */
    void run();

    /**
     * Get the current runner state.
     *
     * @return the runner state
     */
    State getState();

    /**
     * Get crash cause.
     *
     * @return an optional with Runner crash cause if crashed, otherwise returns an empty Optional
     */
    Optional<Throwable> getCrashCause();

    /**
     * The runner states.
     */
    @Getter
    enum State {
        CREATED,
        STARTING,
        RUNNING,
        STOPPING,
        STOPPED,
        CRASHED;
    }
}
