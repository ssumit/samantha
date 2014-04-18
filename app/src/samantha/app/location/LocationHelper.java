package samantha.app.location;

import android.content.Context;
import android.location.*;
import samantha.app.SamLogger;
import samantha.app.SamanthaApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationHelper {

    public List<String> getLocationList(int maxNoOfResult) {
        List<String> locationList = new ArrayList<String>();
        LocationManager locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);

        Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        SamLogger logger = new SamLogger();
        if(locations!= null && providerList!= null && providerList.size()>0) {
            double longitude = locations.getLongitude();
            double latitude = locations.getLatitude();
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, maxNoOfResult);
                if(listAddresses != null) {
                    for (Address listAddress : listAddresses) {
                        String location = listAddress.getAddressLine(0);
                        locationList.add(location);
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
