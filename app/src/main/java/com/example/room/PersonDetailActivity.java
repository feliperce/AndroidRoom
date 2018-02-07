package com.example.room;

import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
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
import java.util.List;

public class PersonDetailActivity extends AppCompatActivity {

    private ImageView addBookButton;
    private TextView nameTextView;
    private TextView emailTextView;
    private RecyclerView booksRecyclerView;
    private BookAdapter bookAdapter;
    private AppDatabase db;
    private LiveData<PersonWithBook> personWithBookLiveData;
    private PersonWithBook personWithBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        addBookButton = findViewById(R.id.addBookButton);
        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        booksRecyclerView = findViewById(R.id.booksRecyclerView);

        db = AppDatabase.getAppDatabase(this);

        personWithBook = new PersonWithBook();
        personWithBook.bookList = new ArrayList<>();

        db.personBookDao().getPersonWithBooks(getIntent().getExtras().getLong(Person.BUNDLE)).observe(this, new Observer<PersonWithBook>() {
            @Override
            public void onChanged(@Nullable PersonWithBook newPersonWithBook) {
                personWithBook.person = newPersonWithBook.person;
                personWithBook.bookList.clear();
                personWithBook.bookList.addAll(newPersonWithBook.bookList);
                nameTextView.setText(
                        String.format("%s %s", personWithBook.person.getFirstName(), personWithBook.person.getLastName())
                );
                emailTextView.setText(personWithBook.person.getEmail());
            }
        });

        bookAdapter = new BookAdapter(personWithBook.bookList, this);
        booksRecyclerView.setAdapter(bookAdapter);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                book.setPersonId(personWithBook.person.getId());
                personWithBook.bookList.add(book);
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
