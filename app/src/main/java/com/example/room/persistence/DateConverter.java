package com.example.room.persistence;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;

/**
 * Created by felipe on 18/01/18.
 */

public class Converter {

    @TypeConverter
    public Long getTodayTimeInMillis() {
        return Calendar.getInstance().getTimeInMillis();
    }
}
