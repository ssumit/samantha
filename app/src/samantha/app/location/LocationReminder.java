package samantha.app.location;

import org.json.JSONException;
import org.json.JSONObject;
import samantha.app.UserIntent;

public class LocationReminder {
    private static final String ACTION = "action";
    private static final String LOCATION = "location";
    private static final String ENTITY = "entity";
    private String _action;
    private String _location;
    private String _entity;

    public LocationReminder(String action, String location, String entity) {
        _action = action;
        _location = location;
        _entity = entity;
    }

    public LocationReminder(UserIntent userIntent) throws JSONException {
        JSONObject jsonObject = userIntent.getEntities();
        _action = jsonObject.getString(ACTION);
        _location = jsonObject.getString(LOCATION);
        _entity = jsonObject.getString(ENTITY);
    }

    public String getAction() {
        return _action;
    }

    public String getEntity() {
        return _entity;
    }

    public String getLocation() {
        return _location;
    }
}
