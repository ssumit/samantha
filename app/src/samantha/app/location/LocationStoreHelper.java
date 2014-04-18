package samantha.app.location;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocationStoreHelper extends SQLiteOpenHelper {
    private static final String _name = "location_reminder.sqlite";
    private static final int _version = 1;

    public LocationStoreHelper(Context context)
    {
        super(context, _name, null, _version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String tableCreateQuery = LocationStore.getCreateTableString();
        sqLiteDatabase.execSQL(tableCreateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2)
    {
    }
}
