package database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = { LocationDbObject.class, TimestampDbObject.class }, version = 1)
public abstract class Timestamp extends RoomDatabase {
    public abstract LocationDAO locationDAO();
    public abstract TimestampDAO timestampDAO();
}
