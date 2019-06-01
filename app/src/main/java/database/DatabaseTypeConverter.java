package database;

import android.arch.persistence.room.TypeConverter;

import java.sql.Date;

/**
 * The class that handles type conversions, that the Room architecture and SQLite can't support
 * natively. Currently holds the date converter, so the database can store dates as longs.
 */
public class DatabaseTypeConverter {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
