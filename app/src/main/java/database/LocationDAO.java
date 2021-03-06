package database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface, that represents the callable methods, and their SQL counterparts.
 */
@Dao
public interface LocationDAO {
    @Query("SELECT * FROM locationDbObject")
    List<LocationDbObject> getAll();

    @Query("SELECT * FROM locationDbObject WHERE id IN (:userIds)")
    List<LocationDbObject> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM locationDbObject WHERE name LIKE :name LIMIT 1")
    LocationDbObject findByName(String name);

    @Insert
    void insert(LocationDbObject dbObject);

    @Insert
    void insertAll(ArrayList<LocationDbObject> locations);

    @Delete
    void delete(LocationDbObject location);
}

