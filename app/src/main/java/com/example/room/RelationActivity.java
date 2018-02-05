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
import com.example.room.persistence.entity.PersonWithBook;

import java.util.ArrayList;
import java.util.List;

public class RelationActivity extends AppCompatActivity {

    public static final String DETAIL_BUNDLE = "detail";

    private RecyclerView personRecyclerView;
    private List<PersonWithBook> personWithBookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);

        personRecyclerView = findViewById(R.id.personRecyclerView);

        personWithBookList = new ArrayList<>(0);

        final PersonWithBookAdapter arrayAdapter = new PersonWithBookAdapter(personWithBookList, this);

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
    
}
