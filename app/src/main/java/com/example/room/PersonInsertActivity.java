package com.example.room;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.room.persistence.AppDatabase;
import com.example.room.persistence.entity.Book;
import com.example.room.persistence.entity.Person;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonInsertActivity extends AppCompatActivity {

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText ageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_insert);

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        ageEditText = findViewById(R.id.ageEditText);
    }

    public void registerPerson(View view) {
        Person person = new Person();
        person.setFirstName(firstNameEditText.getText().toString());
        person.setLastName(lastNameEditText.getText().toString());
        person.setAge(Integer.parseInt(ageEditText.getText().toString()));
        person.setEmail(emailEditText.getText().toString());

        new InsertAsyncTask(this).execute(person);
    }

    private static class InsertAsyncTask extends AsyncTask<Person, Void, Void> {

        private WeakReference<Activity> activity;

        public InsertAsyncTask(Activity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        protected Void doInBackground(Person... param) {
            AppDatabase db = AppDatabase.getAppDatabase(activity.get().getBaseContext());

            db.personDao().insert(param[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            activity.get().finish();
        }
    }
}
