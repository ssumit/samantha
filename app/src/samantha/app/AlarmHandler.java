package samantha.app;

import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import org.json.JSONObject;

import java.util.Calendar;

public class AlarmHandler {
    private static final String TIME = "time";
    private Context _context;
    private ToastMaker _toastMaker;
    private SamLogger _logger;

    public AlarmHandler() {
        _context = SamanthaApplication.getInstance().getApplicationContext();
        _toastMaker = new ToastMaker(_context);
        _logger = new SamLogger();
    }

    public void setAlarm(UserIntent userIntent) {
        int hours = getHoursLeft(userIntent);
        int minutes = getMinutesLeft(userIntent);
        //setAlarmIntent(hours, minutes);
        showToast(hours, minutes);
    }

    private int getHoursLeft(UserIntent userIntent) {
        String timeField = getTimeField(userIntent.getEntities());
        return 0;
    }

    private String getTimeField(JSONObject entities) {
        String time = entities.optString(TIME);
        int hour = Calendar.getInstance().get(Calendar.HOUR);
        int hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        _logger.logD("SAMXXXXX", "hour: " + hour + " minute: " + minute + " hour of day: " + hourOfDay);
        return time;
    }

    private int getMinutesLeft(UserIntent userIntent) {
        return 0;
    }

    private void setAlarmIntent(int hours, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_HOUR, hours);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        _context.startActivity(intent);
    }

    private void showToast(int hours, int minutes) {
    }
}
