package samantha.app;

import android.app.Application;
import android.widget.Toast;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SamanthaApplication extends Application {
    private SamLogger _logger = new SamLogger();

    public void onCreate() {
        super.onCreate();
        handleDeviceCrashGraceFully();
        init();
    }

    private void handleDeviceCrashGraceFully()
    {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
        {
            private boolean _CurrentlyCrashing = false;

            @Override
            public void uncaughtException(Thread thread, Throwable throwable)
            {
                _logger.logE(SamanthaApplication.class.getName(), "UncaughtException: ", throwable);
                if (!_CurrentlyCrashing)
                {
                    _CurrentlyCrashing = true;
                    ToastMaker toastMaker = new ToastMaker(getApplicationContext());
                    toastMaker.showToast(R.string.crash_toast_text, Toast.LENGTH_SHORT);
                    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                    executorService.schedule(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            killApplication();
                        }
                    }, 3000, TimeUnit.MILLISECONDS);
                }
            }
        });
    }

    private void killApplication()
    {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

    private void init() {
        Runnable initRunnable = new Runnable() {
            @Override
            public void run() {
                InitAppHelper initAppHelper = new InitAppHelper();
                initAppHelper.initApp();
            }
        };
        ExecutorUtil.scheduleNowOnAppThread(initRunnable);
    }
}
