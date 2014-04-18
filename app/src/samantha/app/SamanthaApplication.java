package samantha.app;

import android.app.Application;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SamanthaApplication extends Application {
    private static SamLogger _logger = new SamLogger();
    private final static Set<Callback> _callbacks = new CopyOnWriteArraySet<Callback>();
    private static SamanthaApplication _this;
    private static boolean _isReady = false;

    public void onCreate() {
        super.onCreate();
        _this = SamanthaApplication.this;
        _logger.logE("XXXXX", " this is initialized");
        handleDeviceCrashGraceFully();
        init();
        int hour = Calendar.getInstance().get(Calendar.HOUR);
        int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        _logger.logD("SAMXXXXX", "hour: " + hour + " minute: " + minute + " hour of day: " + hourOfDay);
    }

    public static SamanthaApplication getInstance() {
        _logger.logE("XXXXX", " getinstance called (): " + _this);
        return _this;
    }

    private void handleDeviceCrashGraceFully() {
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

    private void killApplication() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

    private void init() {
        Runnable initRunnable = new Runnable() {
            @Override
            public void run() {
                InitAppHelper initAppHelper = new InitAppHelper();
                initAppHelper.initApp(_this.getApplicationContext());
                fireCallbacks();
            }
        };
        ExecutorUtil.scheduleNowOnAppThread(initRunnable);
    }

    public boolean isReady() {
        synchronized (_callbacks)
        {
            return _isReady;
        }
    }

    private void fireCallbacks() {
        synchronized (_callbacks) {
            for (Callback callback : _callbacks) {
                callback.onReady();
            }
            _isReady = true;
            _callbacks.clear();
        }
    }

    public void attachCallback(Callback callback)
    {
        synchronized (_callbacks)
        {
            if (_isReady)
            {
                callback.onReady();
            } else
            {
                _callbacks.add(callback);
            }
        }
    }

    public interface Callback {
        public void onReady();

        public void onFailure();
    }

}
