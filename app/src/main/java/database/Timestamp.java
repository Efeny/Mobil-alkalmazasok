package database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

/**
 * The abstract class representing the SQLite ("Room") database.
 */
@Database(entities = { LocationDbObject.class, TimestampDbObject.class }, version = 1)
@TypeConverters({DatabaseTypeConverter.class})
public abstract class Timestamp extends RoomDatabase {
    public abstract LocationDAO locationDAO();
    public abstract TimestampDAO timestampDAO();
}
