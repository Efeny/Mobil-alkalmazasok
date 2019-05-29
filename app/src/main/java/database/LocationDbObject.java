package database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class LocationDbObject {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String firstName;

    @ColumnInfo(name = "address")
    public String lastName;

    @ColumnInfo(name = "latitude")
    public long latitude;

    @ColumnInfo(name = "longitude")
    public long longitude;

    public LocationDbObject(String firstName, String lastName, long latitude, long longitude) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
