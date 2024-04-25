package br.com.eventhorizon.common.concurrent.impl;

import br.com.eventhorizon.common.concurrent.ExecutorServiceFactory;
import br.com.eventhorizon.common.concurrent.Runner;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class DefaultRunnerTest {

    @Test
    void test() throws InterruptedException {
        var runner = new CustomRunner(false, ExecutorServiceFactory.getDefaultExecutorServiceFactory()
                .createThreadPoolExecutorService(
                                "custom-runner", 1, 1, Long.MAX_VALUE, TimeUnit.SECONDS));
        assertEquals(Runner.State.CREATED, runner.getState());
        runner.start();
        assertTrue(Runner.State.STARTING== runner.getState() || Runner.State.RUNNING == runner.getState());
        Thread.sleep(1000);
        assertEquals(Runner.State.RUNNING, runner.getState());
        runner.stop();
        assertTrue(Runner.State.STOPPING == runner.getState() || Runner.State.STOPPED == runner.getState());
        assertTrue(runner.getCrashCause().isEmpty());
    }

    @Test
    void testCrash() throws InterruptedException {
        var runner = new CustomRunner(true, ExecutorServiceFactory.getDefaultExecutorServiceFactory()
                .createThreadPoolExecutorService(
                                "custom-runner", 1, 1, Long.MAX_VALUE, TimeUnit.SECONDS));
        assertEquals(Runner.State.CREATED, runner.getState());
        runner.start();
        assertTrue(Runner.State.STARTING== runner.getState() || Runner.State.RUNNING == runner.getState());
        Thread.sleep(1000);
        assertEquals(Runner.State.RUNNING, runner.getState());
        Thread.sleep(2000);
        assertSame(Runner.State.CRASHED, runner.getState());
        assertTrue(runner.getCrashCause().isPresent());
        assertEquals(CrashException.class, runner.getCrashCause().get().getClass());
    }

    @Test
    void testCrashAndRestart() throws InterruptedException {
        var runner = new CustomRunner(true, ExecutorServiceFactory.getDefaultExecutorServiceFactory()
                .createThreadPoolExecutorService(
                        "custom-runner", 1, 1, Long.MAX_VALUE, TimeUnit.SECONDS));
        assertEquals(Runner.State.CREATED, runner.getState());
        runner.start();
        assertTrue(Runner.State.STARTING== runner.getState() || Runner.State.RUNNING == runner.getState());
        Thread.sleep(1000);
        assertEquals(Runner.State.RUNNING, runner.getState());
        Thread.sleep(2000);
        assertSame(Runner.State.CRASHED, runner.getState());
        assertTrue(runner.getCrashCause().isPresent());
        assertEquals(CrashException.class, runner.getCrashCause().get().getClass());

        runner.start();
    }

    private static class CrashException extends RuntimeException {
    }

    @Slf4j
    private static class CustomRunner extends DefaultRunner {

        private final boolean shouldCrash;

        public CustomRunner(boolean shouldCrash, ExecutorService executorService) {
            super("custom-runner", executorService);
            this.shouldCrash = shouldCrash;
        }

        @Override
        public void run() {
            int count = 10;
            while (state == State.RUNNING) {
                try {
                    log.info("Running ...");
                    Thread.sleep(100);
                    count--;
                    if (count == 0 && shouldCrash) {
                        break;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            log.info("Stopped");
            if (shouldCrash) {
                throw new CrashException();
            }
        }
    }
}
