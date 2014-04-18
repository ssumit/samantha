package samantha.app;

import org.json.JSONObject;

public class UserIntent {
    private String _intent;
    private JSONObject _entities;

    public UserIntent(String intent, JSONObject entities) {
        _intent = intent;
        _entities = entities;
    }

    public String getIntent() {
        return _intent;
    }

    public JSONObject getEntities() {
        return _entities;
    }
}
