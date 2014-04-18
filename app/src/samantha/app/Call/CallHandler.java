package samantha.app.Call;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import org.json.JSONException;
import org.json.JSONObject;
import samantha.app.SamanthaApplication;
import samantha.app.ToastMaker;
import samantha.app.UserIntent;
import samantha.app.Utils;

public class CallHandler {
    private static final String CONTACT_NAME = "contact";
    private static final String VALUE = "value";
    private PhoneBookLookUp _phoneBookLookUp;
    private ToastMaker _toastMaker; //todo: it really does not belong here

    public CallHandler() {
        _phoneBookLookUp = new PhoneBookLookUp();
        _toastMaker = new ToastMaker(getContext());
    }

    public void call(UserIntent userIntent) {
        String userNameOrPhone = getUserOrPhone(userIntent.getEntities());
        if (userNameOrPhone == null)
        {
            _toastMaker.showToast("Server failed to identify name?");
            openDialler();
            return;
        }
        if (!isNumeric(userNameOrPhone))
        {
            userNameOrPhone = getPhoneNumberFromUserName(userNameOrPhone);
            if (userNameOrPhone == null)
            {
                _toastMaker.showToast("We could not find any number for this contact :(");
                openDialler();
                return;
            }
        }
        dialPhone(userNameOrPhone);
    }

    private void openDialler() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    private String getPhoneNumberFromUserName(String userName) {
        return _phoneBookLookUp.getPhoneNumber(userName);
    }

    private void dialPhone(String phone) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phone));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(callIntent);
    }

    private Context getContext() {
        return SamanthaApplication.getInstance().getApplicationContext();
    }

    private boolean isNumeric(String userNameOrPhone) {
        return Utils.isNumeric(userNameOrPhone);
    }

    private String getUserOrPhone(JSONObject entities) {
        try {
            return entities.getJSONObject(CONTACT_NAME).getString(VALUE);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
