package samantha.app;


import android.util.Log;

public class SamLogger {

    public void logV(String tag, String message) {
        Log.v(tag, message);
    }

    public void logD(String tag, String message) {
        Log.d(tag, message);
    }

    public void logE(String tag, String message) {
        Log.e(tag, message);
    }

    public void logE(String tag, String message, Throwable throwable) {
        Log.e(tag, message, throwable);
    }
}
