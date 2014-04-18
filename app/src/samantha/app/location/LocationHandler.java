package samantha.app.location;

import android.content.Context;
import samantha.app.SamLogger;
import samantha.app.SamanthaApplication;

import java.util.List;

public class LocationHandler {
    private static LocationHandler _this;
    private static final Object _lock = new Object();
    private SamLogger _logger;
    private LocationHelper _locationHelper;
    private LocationStore _store;
    private LocationHandler() {
        _locationHelper = new LocationHelper();
        _store = new LocationStore(new StorageHelper().getLocationReminderDatabase(getContext()));
        _logger = new SamLogger();
    }

    private Context getContext() {
        return SamanthaApplication.getInstance().getApplicationContext();
    }

    public static LocationHandler getInstance() {
        if (_this == null) {
            synchronized (_lock) {
                if (_this == null) {
                    _this = new LocationHandler();
                }
            }
        }
        return _this;
    }

    public void printLocation() {
        List<String> list = _locationHelper.getLocationList(3);
    }

    public void addReminder(String action, String location, String entity) {
        _store.addReminder(action, location, entity);
    }

    public List<LocationReminder> getReminders(String location) {
        return _store.getReminders(location);
    }
}
