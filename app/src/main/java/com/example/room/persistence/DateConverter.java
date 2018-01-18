package com.example.room.persistence;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by felipe on 18/01/18.
 */

public class DateConverter {

    @TypeConverter
    public Date longToDate(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar.getTime();
    }

    @TypeConverter
    public long dateToLong(Date date) {
        return date.getTime();
    }
}
