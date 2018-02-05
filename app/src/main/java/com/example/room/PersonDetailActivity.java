package com.example.room;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.room.persistence.entity.Book;
import com.example.room.persistence.entity.Person;
import com.example.room.persistence.entity.PersonWithBook;

import java.util.ArrayList;

public class PersonDetailActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView emailTextView;
    private RecyclerView booksRecyclerView;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        booksRecyclerView = findViewById(R.id.booksRecyclerView);

        Person person = getIntent().getParcelableExtra(Person.BUNDLE);
        ArrayList<Book> bookArrayList = getIntent().getParcelableArrayListExtra(Book.BUNDLE);

        nameTextView.setText(
            String.format("%s %s", person.getFirstName(), person.getLastName())
        );
        emailTextView.setText(person.getEmail());

        bookAdapter = new BookAdapter(bookArrayList, this);
        booksRecyclerView.setAdapter(bookAdapter);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
