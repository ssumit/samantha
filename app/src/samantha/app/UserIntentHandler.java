package samantha.app;

import org.json.JSONException;
import org.json.JSONObject;

public class UserIntentHandler {

    public UserIntentHandler() {
    }

    public void handleUserIntent(String intent, String json) {
        try {
            UserIntent userIntent = new UserIntent(intent, new JSONObject(json));
            if (intent.equals(getIntentName(HandledIntent.ALARM)))
            {
                AlarmHandler alarmHandler = new AlarmHandler();
                alarmHandler.setAlarm(userIntent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getIntentName(HandledIntent alarm) {
        return alarm.name().toLowerCase();
    }
}
