package samantha.app.location;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class LocationStore {

    public final static String TABLE_NAME = "LocationReminderStore";
    private SQLiteDatabase _sqLiteDatabase;

    public static enum fields
    {
        action, location, entity
    }

    public LocationStore(SQLiteDatabase sqLiteDatabase) {
        _sqLiteDatabase = sqLiteDatabase;
    }

    public static String getCreateTableString()
    {
        return String.format("create table %s ( %s text, %s text, %s text)",
                TABLE_NAME,
                fields.action.name(),
                fields.location.name(),
                fields.entity.name()
        );
    }

    public void addReminder(String action, String location, String entity) {
        ContentValues contentValues = getContentValues(action, location, entity);
        _sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    private ContentValues getContentValues(String action, String location, String entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(fields.action.name(), action);
        contentValues.put(fields.location.name(), location);
        contentValues.put(fields.entity.name(), entity);
        return contentValues;
    }

    public void removeReminders(List<LocationReminder> reminders) {
        String whereClause = fields.location.name() + " = ?";
        for (LocationReminder reminder : reminders) {
            _sqLiteDatabase.delete(TABLE_NAME, whereClause, new String[]{reminder.getLocation()});
        }
    }

    public List<LocationReminder> getReminders(String location) {
        String whereClause = fields.location.name() + " = ?";
        String[] selectionArgs = new String[]{location};
        Cursor cursor = _sqLiteDatabase.query(TABLE_NAME, null, whereClause, selectionArgs, null, null, null);
        List<LocationReminder> reminders = new ArrayList<LocationReminder>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int actionIndex = cursor.getColumnIndex(fields.action.name());
                int entityIndex = cursor.getColumnIndex(fields.entity.name());
                String action = cursor.getString(actionIndex);
                String entity = cursor.getString(entityIndex);
                reminders.add(new LocationReminder(action, location, entity));
            }
            cursor.close();
        }
        return reminders;
    }
}
