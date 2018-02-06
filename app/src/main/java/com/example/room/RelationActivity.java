package com.example.room;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.room.persistence.AppDatabase;
import com.example.room.persistence.entity.Book;
import com.example.room.persistence.entity.Person;
import com.example.room.persistence.entity.PersonWithBook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RelationActivity extends AppCompatActivity implements PersonWithBookAdapter.OnItemClickListener {

    public static final String DETAIL_BUNDLE = "detail";

    private RecyclerView personRecyclerView;
    private List<PersonWithBook> personWithBookList;
    private PersonWithBookAdapter arrayAdapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);

        personRecyclerView = findViewById(R.id.personRecyclerView);

        personWithBookList = new ArrayList<>(0);

        arrayAdapter = new PersonWithBookAdapter(personWithBookList, this, this);

        personRecyclerView.setLayoutManager(
                new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL, false));
        personRecyclerView.setAdapter(arrayAdapter);

        registerForContextMenu(personRecyclerView);

        db = AppDatabase.getAppDatabase(this);
        db.personBookDao().loadPersonsAndBooks().observe(
                this, new Observer<List<PersonWithBook>>() {
            @Override
            public void onChanged(@Nullable List<PersonWithBook> personWithBooks) {
                Log.d("saasd", "Events Changed:");
                personWithBookList.clear();
                personWithBookList.addAll(personWithBooks);
                arrayAdapter.notifyDataSetChanged();
            }
        });

        registerForContextMenu(personRecyclerView);


    }

    public void fabClick(View view) {
        Intent it = new Intent(this, PersonInsertActivity.class);
        startActivity(it);
    }

    @Override
    public void onItemClick(int position) {
        ArrayList<Book> bookArrayList = new ArrayList<>();
        bookArrayList.addAll(arrayAdapter.getPersonWithBookList().get(position).bookList);

        Intent it = new Intent(this, PersonDetailActivity.class);
        it.putExtra(Person.BUNDLE, personWithBookList.get(position).person);
        it.putParcelableArrayListExtra(Book.BUNDLE, bookArrayList);
        startActivity(it);
    }

    @Override
    public void onItemDeleteClick(final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.personDao().delete(personWithBookList.get(position).person);
            }
        }).start();
    }

    @Override
    public void onItemUpdateClick(int position) {
        createUpdateDialog(position);
    }

    private void createUpdateDialog(int position) {
        final Person person = personWithBookList.get(position).person;

        final Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        dialog.setContentView(R.layout.update_person_dialog);

        final EditText firstNameEditText = dialog.findViewById(R.id.firstNameEditText);
        final EditText lastNameEditText = dialog.findViewById(R.id.lastNameEditText);
        final EditText emailEditText = dialog.findViewById(R.id.emailEditText);
        final EditText ageEditText = dialog.findViewById(R.id.ageEditText);

        firstNameEditText.setText(person.getFirstName());
        lastNameEditText.setText(person.getLastName());
        emailEditText.setText(person.getEmail());
        ageEditText.setText(Integer.toString(person.getAge()));

        Button updatePersonButton = dialog.findViewById(R.id.updatePersonButton);

        updatePersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        person.setFirstName(firstNameEditText.getText().toString());
                        person.setLastName(lastNameEditText.getText().toString());
                        person.setEmail(emailEditText.getText().toString());
                        person.setAge(Integer.parseInt(ageEditText.getText().toString()));

                        db.personDao().update(person);
                        dialog.dismiss();
                    }
                }).start();

            }
        });

        dialog.show();

    }
}
