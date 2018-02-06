package com.example.room.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.room.persistence.entity.Book;
import com.example.room.persistence.entity.Person;
import com.example.room.persistence.entity.PersonWithBook;

import java.util.List;

/**
 * Created by felipe on 11/01/18.
 */

@Dao
public interface BookDao {

    @Insert
    long insert(Book book);

    @Update
    int update(Book book);

    @Delete
    void delete(Book book);

    @Query("SELECT * FROM books")
    LiveData<List<Book>> getAll();

    @Query("SELECT * FROM books WHERE id = :idbook")
    Book getById(int idbook);

    @Query("SELECT * FROM books WHERE id = :idbook")
    Book getByIdList(List<Long> idbook);
}
