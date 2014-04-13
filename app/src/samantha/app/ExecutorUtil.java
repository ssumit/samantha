package samantha.app;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ExecutorUtil {
    private static ScheduledExecutorService _appThread = Executors.newSingleThreadScheduledExecutor();

    public static ScheduledFuture<?> scheduleNowOnAppThread(Runnable runnable) {
        ExceptionThrowingFutureTask exceptionThrowingFutureTask = new ExceptionThrowingFutureTask(runnable);
        return _appThread.schedule(exceptionThrowingFutureTask, 0, TimeUnit.MILLISECONDS);
    }
}
