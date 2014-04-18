package samantha.app;

import android.content.Context;
import samantha.app.Call.PhoneBookLookUp;

public class InitAppHelper {
    public void initApp(Context context) {
        PhoneBookLookUp.init(context.getContentResolver());
    }
}
