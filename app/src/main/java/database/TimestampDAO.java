package database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

@Dao
 public interface TimestampDAO {
    @Insert
    void insert(Long timestamp);

    @Update
    void update(TimestampDbObject timestamp);

    @Delete
    void delete(TimestampDbObject timestamp);
}
