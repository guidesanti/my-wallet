package br.com.eventhorizon.common.concurrent.impl;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ContextAwareThreadPoolExecutor extends ThreadPoolExecutor {

    final private boolean useFixedContext;

    final private Map<String, String> fixedContext;

    public static ContextAwareThreadPoolExecutor newWithInheritedMdc(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                                            TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        return new ContextAwareThreadPoolExecutor(null, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public static ContextAwareThreadPoolExecutor newWithCurrentMdc(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                                          TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        return new ContextAwareThreadPoolExecutor(MDC.getCopyOfContextMap(), corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public static ContextAwareThreadPoolExecutor newWithFixedMdc(Map<String, String> fixedContext, int corePoolSize,
                                                        int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                                        BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        return new ContextAwareThreadPoolExecutor(fixedContext, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    private ContextAwareThreadPoolExecutor(Map<String, String> fixedContext, int corePoolSize, int maximumPoolSize,
                                           long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        this.fixedContext = fixedContext;
        this.useFixedContext = (fixedContext != null);
    }

    private Map<String, String> getContextForTask() {
        return useFixedContext ? fixedContext : MDC.getCopyOfContextMap();
    }

    @Override
    public void execute(Runnable runnable) {
        super.execute(wrap(runnable, getContextForTask()));
    }

    private static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            var previous = MDC.getCopyOfContextMap();
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            try {
                runnable.run();
            } finally {
                if (previous == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(previous);
                }
            }
        };
    }
}
