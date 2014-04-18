package samantha.app;

import org.json.JSONException;
import org.json.JSONObject;
import samantha.app.Call.CallHandler;

public class UserIntentHandler {

    public UserIntentHandler() {
    }

    public void handleUserIntent(String intent, String json) {
        try {
            UserIntent userIntent = new UserIntent(intent, new JSONObject(json));
            if (intent.equals(getIntentName(HandledIntent.ALARM))) {
                AlarmHandler alarmHandler = new AlarmHandler();
                alarmHandler.setAlarm(userIntent);
            }
            else if (intent.equals(getIntentName(HandledIntent.CALL))) {
                CallHandler callHandler = new CallHandler();
                callHandler.call(userIntent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getIntentName(HandledIntent alarm) {
        return alarm.name().toLowerCase();
    }
}
