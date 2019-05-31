package database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(foreignKeys = @ForeignKey(entity = LocationDbObject.class,
        parentColumns = "id",
        childColumns = "timestampId",
        onUpdate = CASCADE,
        onDelete = CASCADE))

public class TimestampDbObject {
    @PrimaryKey(autoGenerate = true)
    public int timestampId;

    @ColumnInfo(name = "car")
    public long car;

    @ColumnInfo(name = "truck")
    public long truck;

    @ColumnInfo(name = "bus")
    public long bus;


    public TimestampDbObject( long car, long truck, long bus) {
        this.car = car;
        this.truck = truck;
        this.bus = bus;
    }
}
