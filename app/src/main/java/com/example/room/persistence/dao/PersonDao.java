package com.example.room.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.room.persistence.entity.Person;
import com.example.room.persistence.entity.PersonWithBook;

import java.util.List;

/**
 * Created by felipe on 11/01/18.
 */

@Dao
public interface PersonDao {

    @Insert
    long insert(Person person);

    @Insert
    void insertAll(List<Person> personList);

    @Update
    int update(Person person);

    @Delete
    void delete(Person person);

    @Query("SELECT * FROM person")
    LiveData<List<Person>> getAll();

    @Query("SELECT * FROM person WHERE id = :idperson")
    Person getById(int idperson);

    @Query("SELECT * FROM person WHERE id = :idperson")
    Person getByIdList(List<Long> idperson);

    @Query("DELETE FROM person")
    void deleteAllPerson();
}
