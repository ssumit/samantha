package samantha.app.location;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StorageHelper
{
    private static SQLiteDatabase _locationReminderDatabase = null;

    public SQLiteDatabase getLocationReminderDatabase(Context context)
    {
        if (_locationReminderDatabase == null)
        {
            LocationStoreHelper databaseHelper = new LocationStoreHelper(context);
            _locationReminderDatabase = getWritableDatabase(databaseHelper);
        }
        return _locationReminderDatabase;
    }

    private SQLiteDatabase getWritableDatabase(SQLiteOpenHelper helper)
    {
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        setDBConfigParams(sqLiteDatabase);
        return sqLiteDatabase;
    }

    private void setDBConfigParams(SQLiteDatabase db)
    {
        db.execSQL("PRAGMA synchronous=0");
        db.enableWriteAheadLogging();
    }

}
