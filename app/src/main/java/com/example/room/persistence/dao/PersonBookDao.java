package com.example.room.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.room.persistence.entity.Book;
import com.example.room.persistence.entity.PersonWithBook;

import java.util.List;

/**
 * Created by felipe on 18/01/18.
 */

@Dao
public interface PersonBookDao {

    @Query("SELECT * FROM person")
    LiveData<List<PersonWithBook>> loadPersonsAndBooks();

}
