package com.example.room;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.room.persistence.AppDatabase;
import com.example.room.persistence.entity.Book;
import com.example.room.persistence.entity.Person;
import com.example.room.persistence.entity.PersonWithBook;

import java.util.ArrayList;
import java.util.List;

public class RelationActivity extends AppCompatActivity implements PersonWithBookAdapter.OnItemClickListener {

    public static final String DETAIL_BUNDLE = "detail";

    private RecyclerView personRecyclerView;
    private List<PersonWithBook> personWithBookList;
    private PersonWithBookAdapter arrayAdapter;

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

        AppDatabase db = AppDatabase.getAppDatabase(this);
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
    public void onItemLongClick(int position) {

    }
}
