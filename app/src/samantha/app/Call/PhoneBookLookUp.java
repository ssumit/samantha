package samantha.app.Call;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class PhoneBookLookUp
{
    private static ContentResolver _contentResolver;

    public static void init(ContentResolver contentResolver)
    {
        _contentResolver = contentResolver;
    }

    public String getPhoneNumber(String name) {
        String ret = null;
        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" like'%" + name +"%'";
        String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = _contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, selection, null, null);
        if (cursor != null)
        {
            if (cursor.moveToFirst()) {
                ret = cursor.getString(0);
            }
            cursor.close();
        }
        return ret;
    }

    public String getContactName(String phoneNumber)
    {
        Cursor cursor = getCursorInContentFilterURI(phoneNumber, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME});
        if (cursor == null)
        {
            return null;
        }
        String contactName = null;
        if(cursor.moveToFirst())
        {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        cursor.close();
        return contactName;
    }

    private Cursor getCursorInContentFilterURI(String phoneNumber, String[] projection)
    {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        return _contentResolver.query(uri, projection, null, null, null);
    }
}
