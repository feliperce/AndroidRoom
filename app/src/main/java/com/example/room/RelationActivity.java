package com.example.room;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.room.persistence.AppDatabase;
import com.example.room.persistence.entity.Book;
import com.example.room.persistence.entity.Person;
import com.example.room.persistence.entity.PersonWithBook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RelationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);

        AppDatabase db = AppDatabase.getAppDatabase(this);

        Person person = new Person();
        person.setFirstName("Aaaaaa");
        person.setLastName("Bbbbbbb");
        person.setAge(26);
        person.setEmail("fdsfsd@gmail.com");

        db.personDao().insert(person);

        List<Book> bookList = new ArrayList<>();
        Book book = new Book();
        book.setAuthor("AAAAAAA");
        book.setName("Nnnnnnn");
        book.setPersonId(1);
        book.setReleaseDate(new Date());
        bookList.add(book);

        PersonWithBook personWithBook = new PersonWithBook();
        personWithBook.person = person;
        personWithBook.bookList = bookList;

        db.bookDao().insert(book);
    }
    
}
