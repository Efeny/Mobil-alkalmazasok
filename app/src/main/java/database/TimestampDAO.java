package database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * A class representing the callable methods and their sql queries of the TIMESTAMP table.
 */
@Dao
 public interface TimestampDAO {
      @Query("SELECT * FROM TimestampDbObject")
      List<TimestampDbObject> listAll();

      @Insert
      void insertTimestamp(TimestampDbObject timestampDbObject);

      @Update
      void update(TimestampDbObject timestamp);

      @Delete
      void delete(TimestampDbObject timestamp);
}
