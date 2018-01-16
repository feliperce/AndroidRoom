package com.example.room;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.room.persistence.AppDatabase;
import com.example.room.persistence.entity.Book;
import com.example.room.persistence.entity.Person;
import com.example.room.persistence.entity.PersonWithBook;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Person person = new Person();
        person.setFirstName("Aaaaaa");
        person.setLastName("Bbbbbbb");
        person.setAge(26);
        person.setEmail("fdsfsd@gmail.com");

        List<Book> bookList = new ArrayList<>();
        Book book = new Book();
        book.setAuthor("AAAAAAA");
        book.setName("Nnnnnnn");
        bookList.add(book);

        AppDatabase db = AppDatabase.getAppDatabase(this);
        db.personDao().insert(person);
        db.bookDao().insert(book);
    }
}
