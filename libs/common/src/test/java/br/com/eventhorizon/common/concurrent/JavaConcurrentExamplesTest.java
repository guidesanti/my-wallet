package br.com.eventhorizon.common.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
public class JavaConcurrentExamplesTest {

    private static final int LOOP_COUNT = 5;

    private static final ExecutorService executorService = ExecutorServiceProvider.getDefaultExecutorServiceProvider().getIoExecutorService();

    @Test
    void testRunnableFinishing() {
        var future = executorService.submit(() -> run(LOOP_COUNT, false));
        checkFutureResult(future);
    }

    @Test
    void testRunnableCancelling() throws InterruptedException {
        var future = executorService.submit(() -> run(2 * LOOP_COUNT, false));
        Sleeper.getDefaultSleeper().sleep(Duration.ofSeconds(LOOP_COUNT - 2));
        future.cancel(false);
        checkFutureResult(future);
        Sleeper.getDefaultSleeper().sleep(Duration.ofSeconds(2));
    }

    @Test
    void testRunnableCancellingWithInterruption() throws InterruptedException {
        var future = executorService.submit(() -> run(2 * LOOP_COUNT, false));
        Sleeper.getDefaultSleeper().sleep(Duration.ofSeconds(LOOP_COUNT - 2));
        future.cancel(true);
        checkFutureResult(future);
        Sleeper.getDefaultSleeper().sleep(Duration.ofSeconds(2));
    }

    @Test
    void testRunnableCancellingWithInterruptionIgnored() throws InterruptedException {
        var future = executorService.submit(() -> runIgnoreInterrupted(2 * LOOP_COUNT, false));
        Sleeper.getDefaultSleeper().sleep(Duration.ofSeconds(LOOP_COUNT - 2));
        future.cancel(true);
        checkFutureResult(future);
        Sleeper.getDefaultSleeper().sleep(Duration.ofSeconds(2));
    }

    @Test
    void testRunnableThrowingException() {
        var future = executorService.submit(() -> run(LOOP_COUNT,true));
        checkFutureResult(future);
    }

    private void run(int loopCount, boolean throwException) {
        while (loopCount-- > 0) {
            try {
                log.info("[{}] Running [{}] ...", Thread.currentThread().getName(), loopCount);
                Sleeper.getDefaultSleeper().sleep(Duration.ofSeconds(1));
            } catch (Exception ex) {
                log.error(String.format("[%s] Exception while running", Thread.currentThread().getName()), ex);
                break;
            }
        }
        if (throwException) {
            throw new CustomRuntimeException("Exception from runnable");
        }
    }

    private void runIgnoreInterrupted(int loopCount, boolean throwException) {
        while (loopCount-- > 0) {
            try {
                log.info("[{}] Running [{}] ...", Thread.currentThread().getName(), loopCount);
                Sleeper.getDefaultSleeper().sleep(Duration.ofSeconds(1));
            } catch (InterruptedException ex) {
                log.info("Ignoring interrupted exception ...");
                Thread.currentThread().interrupt();
            } catch (Exception ex) {
                log.error(String.format("[%s] Exception while running", Thread.currentThread().getName()), ex);
                break;
            }
        }
        if (throwException) {
            throw new CustomRuntimeException("Exception from runnable");
        }
    }

    private void checkFutureResult(Future<?> future) {
        try {
            var result = future.get();
            log.info("[{}] Future result: {}", Thread.currentThread().getName(), result);
        } catch (Exception ex) {
            log.error("[{}] Future exception: {} -> {}", Thread.currentThread().getName(),
                    ex.getClass().getName(), ex.getMessage());
            if (ex.getCause() == null) {
                log.error("[{}] Future exception cause: none", Thread.currentThread().getName());
            } else {
                log.error("[{}] Future exception cause: {} -> {}", Thread.currentThread().getName(),
                        ex.getCause().getClass().getName(), ex.getCause().getMessage());
            }
        }
        log.info("[{}] Future is cancelled: {}", Thread.currentThread().getName(), future.isCancelled());
        log.info("[{}] Future is done: {}", Thread.currentThread().getName(), future.isDone());
    }

    private static class CustomRuntimeException extends RuntimeException {

        public CustomRuntimeException(String message) {
            super(message);
        }

        public CustomRuntimeException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
