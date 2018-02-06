package com.example.room;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.room.persistence.AppDatabase;
import com.example.room.persistence.entity.Book;
import com.example.room.persistence.entity.Person;
import com.example.room.persistence.entity.PersonWithBook;

import java.util.ArrayList;
import java.util.Date;

public class PersonDetailActivity extends AppCompatActivity {

    private ImageView addBookButton;
    private TextView nameTextView;
    private TextView emailTextView;
    private RecyclerView booksRecyclerView;
    private BookAdapter bookAdapter;
    private AppDatabase db;
    private Person person;
    private ArrayList<Book> bookArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        addBookButton = findViewById(R.id.addBookButton);
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        booksRecyclerView = findViewById(R.id.booksRecyclerView);

        person = getIntent().getParcelableExtra(Person.BUNDLE);
        bookArrayList = getIntent().getParcelableArrayListExtra(Book.BUNDLE);

        nameTextView.setText(
            String.format("%s %s", person.getFirstName(), person.getLastName())
        );
        emailTextView.setText(person.getEmail());

        bookAdapter = new BookAdapter(bookArrayList, this);
        booksRecyclerView.setAdapter(bookAdapter);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getAppDatabase(this);

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });

    }

    private void createDialog() {
        final Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        dialog.setContentView(R.layout.add_book_dialog);

        final EditText titleEditText = dialog.findViewById(R.id.titleEditText);
        final EditText authorEditText = dialog.findViewById(R.id.authorEditText);
        Button registerBookButton = dialog.findViewById(R.id.registerBookButton);

        registerBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Book book = new Book();
                book.setName(titleEditText.getText().toString());
                book.setAuthor(authorEditText.getText().toString());
                book.setReleaseDate(new Date());
                book.setPersonId(person.getId());
                bookArrayList.add(book);
                bookAdapter.notifyDataSetChanged();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        db.bookDao().insert(book);
                        dialog.dismiss();
                    }
                }).start();

            }
        });

        dialog.show();

    }
}
