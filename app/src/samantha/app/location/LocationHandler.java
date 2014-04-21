package samantha.app.location;

import android.content.Context;
import android.location.Location;
import samantha.app.SamLogger;
import samantha.app.SamanthaApplication;

import java.util.ArrayList;
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

    public void addReminder(LocationReminder locationReminder) {
        addReminder(locationReminder.getAction(), locationReminder.getLocation(), locationReminder.getEntity());
    }

    public void addReminder(String action, String location, String entity) {
        _store.addReminder(action, location, entity);
    }

    public List<LocationReminder> getReminders(String location) {
        return _store.getReminders(location);
    }

    public List<LocationReminder> getReminders(Location location) {
        List<String> locationList = _locationHelper.getLocationList(2, location);
        List<LocationReminder> locationReminders = new ArrayList<LocationReminder>();
        for (String loc : locationList) {
            locationReminders.addAll(getReminders(loc));
        }
       return locationReminders;
    }

    public void removeReminders(List<LocationReminder> reminders) {
        _store.removeReminders(reminders);
    }
}
