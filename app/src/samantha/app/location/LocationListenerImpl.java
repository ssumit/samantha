package samantha.app.location;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import samantha.app.SamLogger;
import samantha.app.ToastMaker;

import java.util.List;

public class LocationListenerImpl implements LocationListener {

    private static final String LOG_TAG = LocationListenerImpl.class.getSimpleName();
    private final Context _context;
    private SamLogger _logger;
    private boolean _isGPSEnabled = false;
    private boolean _isNetworkEnabled = false;
    private Location _location;
    private double _latitude;
    private double _longitude;
    private LocationManager _locationManager;
    private ToastMaker _toastMaker;
    private LocationHandler _locationHandler;

    private static final long MIN_DISTANCE_IN_METRES_CHANGE_FOR_UPDATES = 200;
    private static final long MIN_TIME_MILLISEC_BW_UPDATES = 1000 * 60 * 1;


    public LocationListenerImpl(Context context) {
        this._context = context;
        _logger = new SamLogger();
        _toastMaker = new ToastMaker(context);
        _locationHandler = LocationHandler.getInstance();
        _locationManager = (LocationManager) _context.getSystemService(Context.LOCATION_SERVICE);
        _isGPSEnabled = _locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        _isNetworkEnabled = _locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        String provider = _locationManager.getBestProvider(new Criteria(), true);
        new LocationHelper().getLocationList(1);
        getLocation();
    }

    public Location getLocation() {
        Looper.prepare();
        try {
            if (!canGetLocation()) {
                _toastMaker.showToast("No GPS and network :(");
            } else {
                if (_isNetworkEnabled) {
                    UpdateLocationViaNetwork();
                }
                if (_isGPSEnabled && _location == null) {
                    updateLocationViaGPS();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        _logger.logD(LOG_TAG, " location : " + _location + " isgps enabled : " + _isGPSEnabled + " isnetEnale : " + _isNetworkEnabled);
        return _location;
    }

    private void updateLocationViaGPS() {
        updateLocation(LocationManager.GPS_PROVIDER);
    }

    private void UpdateLocationViaNetwork() {
        updateLocation(LocationManager.NETWORK_PROVIDER);
    }

    private void updateLocation(String provider) {
        _locationManager.requestLocationUpdates(provider, MIN_TIME_MILLISEC_BW_UPDATES,
                MIN_DISTANCE_IN_METRES_CHANGE_FOR_UPDATES, this);
        _location = _locationManager.getLastKnownLocation(provider);
        _logger.logD(LOG_TAG, " location based on last known location : " + _location);
        updateLatitudeAndLongitude();
    }

    private void updateLatitudeAndLongitude() {
        if (_location != null) {
            _latitude = _location.getLatitude();
            _longitude = _location.getLongitude();
        }
    }

    public void stopUsingGPS() {
        if(_locationManager != null){
            _locationManager.removeUpdates(LocationListenerImpl.this);
        }
    }

    public double getLatitude() {
        if(_location != null){
            _latitude = _location.getLatitude();
        }

        return _latitude;
    }

    public double getLongitude() {
        if(_location != null) {
            _longitude = _location.getLongitude();
        }

        return _longitude;
    }

    public boolean canGetLocation() {
        return _isGPSEnabled || _isNetworkEnabled;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(_context);
        alertDialog.setTitle("Location Tracker");
        alertDialog.setMessage("Change GPS settings?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                _context.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        _logger.logD(LOG_TAG, " on location change : " + location);
        _location = location;
        updateLatitudeAndLongitude();
        List<LocationReminder> reminders = _locationHandler.getReminders(location);
        showReminderAlert(reminders);
    }

    private void showReminderAlert(final List<LocationReminder> reminders) {
        if (reminders.size()>0) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(_context);
            alertDialog.setTitle("Reminder");
            final String reminderMessage = getCombinedReminder(reminders);
            alertDialog.setMessage(reminderMessage);
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    _locationHandler.removeReminders(reminders);
                    dialog.cancel();
                }
            });
            alertDialog.setNegativeButton("Remind Later", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }
    }

    private String getCombinedReminder(List<LocationReminder> reminders) {
        StringBuilder stringBuilder = new StringBuilder();
        for (LocationReminder reminder : reminders) {
            stringBuilder.append("\n").append(reminder.getAction()).append(" ").append(reminder.getEntity());
        }

        return stringBuilder.toString();
    }

    @Override
    public void onProviderDisabled(String provider) {
        setProvideEnable(provider, false);
    }

    @Override
    public void onProviderEnabled(String provider) {
        setProvideEnable(provider, true);
        getLocation();
    }

    private void setProvideEnable(String provider, boolean isEnabled) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            _isGPSEnabled = isEnabled;
        } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
            _isNetworkEnabled = isEnabled;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

}
