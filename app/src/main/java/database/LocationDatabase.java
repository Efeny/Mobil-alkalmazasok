package database;

import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.Database;

@android.arch.persistence.room.Database(entities = {LocationDbObject.class}, version = 1)
public abstract class LocationDatabase extends RoomDatabase {
    public abstract LocationDAO locationDAO();
}

