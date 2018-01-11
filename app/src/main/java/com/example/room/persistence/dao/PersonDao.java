package com.example.room.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.room.persistence.entity.Person;

import java.util.List;

/**
 * Created by felipe on 11/01/18.
 */

@Dao
public interface PersonDao {

    @Insert
    void insert(Person person);

    @Update
    void update(Person person);

    @Delete
    void delete(Person person);

    @Query("SELECT * FROM person")
    List<Person> getAll();

    @Query("SELECT * FROM person WHERE id = :idperson")
    Person getById(int idperson);

    @Query("SELECT * FROM person WHERE id = :idperson")
    Person getByIdList(List<Long> idperson);
}
