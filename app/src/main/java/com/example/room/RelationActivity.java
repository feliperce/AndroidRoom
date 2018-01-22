package com.example.room;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import com.example.room.persistence.AppDatabase;
import com.example.room.persistence.entity.Book;
import com.example.room.persistence.entity.Person;
import com.example.room.persistence.entity.PersonWithBook;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RelationActivity extends AppCompatActivity {

    private RecyclerView personRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);

        personRecyclerView = findViewById(R.id.personRecyclerView);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);

        /*AppDatabase db = AppDatabase.getAppDatabase(this);

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

        db.bookDao().insert(book);*/
    }

    public void fabClick(View view) {
        Intent it = new Intent(this, PersonInsertActivity.class);
        startActivity(it);
    }

    private static class PersonAsyncTask extends AsyncTask<Person, Void, PersonWithBook> {

        private WeakReference<Activity> activity;

        public PersonAsyncTask(Activity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        protected PersonWithBook doInBackground(Person... param) {
            AppDatabase db = AppDatabase.getAppDatabase(activity.get().getBaseContext());

            db.personDao().insert(param[0]);
            return null;
        }

        @Override
        protected void onPostExecute(PersonWithBook personWithBook) {
            super.onPostExecute(personWithBook);
            activity.get().finish();
        }
    }
    
}
