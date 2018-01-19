package com.example.room.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.room.persistence.dao.BookDao;
import com.example.room.persistence.dao.PersonBookDao;
import com.example.room.persistence.dao.PersonDao;
import com.example.room.persistence.entity.Book;
import com.example.room.persistence.entity.Person;

/**
 * Created by felipe on 11/01/18.
 */

@Database(entities = {Person.class, Book.class}, version = 2)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract PersonDao personDao();
    public abstract BookDao bookDao();
    public abstract PersonBookDao personBookDao();

    public static AppDatabase getAppDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "mydb")
                    .fallbackToDestructiveMigration() //Remove DB when migration are called
                    //.allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
