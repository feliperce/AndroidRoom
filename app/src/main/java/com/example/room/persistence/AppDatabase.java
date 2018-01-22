package com.example.room.persistence;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.room.persistence.dao.BookDao;
import com.example.room.persistence.dao.PersonBookDao;
import com.example.room.persistence.dao.PersonDao;
import com.example.room.persistence.entity.Book;
import com.example.room.persistence.entity.Person;

import java.util.Random;

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
                    .addMigrations(MIGRATION_1_2)
                    //.fallbackToDestructiveMigration() //Remove DB when migration are called
                    //.allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            for(int i =0; i<10; i++) {
                ContentValues cv = new ContentValues();
                cv.put("first_name", "Person"+i);
                cv.put("last_name", "LastName"+i);
                cv.put("age", i*5);
                cv.put("email", "email@email.com");
                database.insert("person", 0, cv);
            }
        }
    };

}
