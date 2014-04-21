package samantha.app.location;

import android.content.Context;
import android.location.*;
import samantha.app.SamanthaApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationHelper {

    public List<String> getLocationList(int maxNoOfResult) {
        Location locations = getLocation();
        return getLocationList(maxNoOfResult, locations);
    }

    private Location getLocation() {
        LocationManager locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        return locationManager.getLastKnownLocation(provider);
    }

    public List<String> getLocationList(int maxNoOfResult, Location location) {
        LocationManager locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        List<String> locationList = new ArrayList<String>();
        List<String> providerList = locationManager.getAllProviders();
        if(location!= null && providerList!= null && providerList.size()>0) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, maxNoOfResult);
                if(listAddresses != null) {
                    for (Address listAddress : listAddresses) {
                        locationList.add(listAddress.getAddressLine(0));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return locationList;
    }

    private Context getContext() {
        return SamanthaApplication.getInstance().getApplicationContext();
    }

}
