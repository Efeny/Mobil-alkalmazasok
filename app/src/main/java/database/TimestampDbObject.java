package database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;


import java.sql.Date;
import java.sql.Timestamp;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Class that represents the TIMESTAMP table in the database.
 * The class has a foreign key from the LOCATION table, to connect junctions to their
 * recorded measurements.
 */
@Entity(foreignKeys = @ForeignKey(entity = LocationDbObject.class,
        parentColumns = "id",
        childColumns = "locationId",
        onUpdate = CASCADE,
        onDelete = CASCADE))

public class TimestampDbObject {
    @PrimaryKey(autoGenerate = true)
    public int timestampId;

    @ColumnInfo(name = "locationId")
    public int locationId;

    @ColumnInfo(name = "timestamp")
    public Date timestamp;

    @ColumnInfo(name = "vehicletype")
    public String vehicleType;


    public TimestampDbObject(int locationId, Date timestamp, String vehicleType) {
        this.locationId = locationId;
        this.timestamp = timestamp;
        this.vehicleType = vehicleType;
    }
}
