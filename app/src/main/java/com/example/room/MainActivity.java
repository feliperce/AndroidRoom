package com.example.room;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.room.persistence.AppDatabase;
import com.example.room.persistence.entity.Book;
import com.example.room.persistence.entity.Person;
import com.example.room.persistence.entity.PersonWithBook;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PersonAdapter.OnItemClickListener {

    private RecyclerView personRecyclerView;
    private ProgressBar progressBar;
    private List<Person> personList;
    private PersonAdapter arrayAdapter;
    private AppDatabase db;
    private Faker faker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);

        faker = new Faker();

        personRecyclerView = findViewById(R.id.personRecyclerView);
        progressBar = findViewById(R.id.progressBar);

        personList = new ArrayList<>(0);

        arrayAdapter = new PersonAdapter(personList, this, this);

        personRecyclerView.setLayoutManager(
                new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL, false));
        personRecyclerView.setAdapter(arrayAdapter);

        registerForContextMenu(personRecyclerView);

        db = AppDatabase.getAppDatabase(this);
        db.personDao().getAll().observe(this, new Observer<List<Person>>() {
            @Override
            public void onChanged(@Nullable List<Person> newPersonList) {
                personList.clear();
                personList.addAll(newPersonList);
                arrayAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
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
        Intent it = new Intent(this, PersonDetailActivity.class);
        it.putExtra(Person.BUNDLE, personList.get(position).getId());
        startActivity(it);
    }

    @Override
    public void onItemDeleteClick(final int position) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.personDao().delete(personList.get(position));
            }
        }).start();
    }

    @Override
    public void onItemUpdateClick(int position) {
        createUpdateDialog(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addNItem:
                createRandomInsertDialog();
                return true;
            case R.id.removeAllPersonData:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        db.personDao().deleteAllPerson();
                    }
                }).start();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createUpdateDialog(int position) {
        final Person person = personList.get(position);

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

    private void createRandomInsertDialog() {
        final EditText editText = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert N random data");
        builder.setMessage("choose random number amount");
        builder.setView(editText);
        builder.setPositiveButton(getString(R.string.register), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int amount = Integer.parseInt(editText.getText().toString());

                if(amount>0) {
                    final List<Person> personList = new ArrayList<>();

                    for(int i = 0; i < amount; i++) {
                        Person person = new Person();
                        person.setFirstName(faker.gameOfThrones().character());
                        person.setLastName(faker.pokemon().name());
                        person.setAge(faker.number().numberBetween(10, 100));
                        person.setEmail(faker.internet().emailAddress());
                        personList.add(person);
                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            db.personDao().insertAll(personList);
                        }
                    }).start();

                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        builder.create().show();
    }
}
