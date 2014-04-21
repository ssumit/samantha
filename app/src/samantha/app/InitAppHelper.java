package samantha.app;

import android.content.Context;
import samantha.app.Call.PhoneBookLookUp;
import samantha.app.location.LocationListenerImpl;

public class InitAppHelper {
    public void initApp(Context context) {
        PhoneBookLookUp.init(context.getContentResolver());
        LocationListenerImpl locationListenerImpl = new LocationListenerImpl(context);
    }
}
