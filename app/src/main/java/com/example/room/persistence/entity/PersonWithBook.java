package com.example.room.persistence.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by felipe on 12/01/18.
 */

public class PersonWithBook {

    @Embedded
    public Person person;
    @Relation(parentColumn = "id", entityColumn = "person_id")
    public List<Book> bookList;

}
