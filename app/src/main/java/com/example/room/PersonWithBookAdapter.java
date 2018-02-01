package com.example.room;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.room.persistence.entity.PersonWithBook;

import java.util.List;

/**
 * Created by felipe on 23/01/18.
 */

public class PersonWithBookAdapter extends RecyclerView.Adapter<PersonWithBookAdapter.ViewHolder> {

    private List<PersonWithBook> personWithBookList;
    private Context context;

    public PersonWithBookAdapter(List<PersonWithBook> personWithBookList, Context context) {
        this.personWithBookList = personWithBookList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_person, parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.personNameTextView.setText(
                String.format("%s %s",
                        personWithBookList.get(position).person.getFirstName(),
                        personWithBookList.get(position).person.getLastName()
                ));

        holder.bookQtTextView.setText(
               context.getString(R.string.books_quantity, personWithBookList.get(position).bookList.size())
        );
    }

    @Override
    public int getItemCount() {
        return personWithBookList==null ? 0 : personWithBookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView personNameTextView;
        TextView bookQtTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            personNameTextView = itemView.findViewById(R.id.nameTextView);
            bookQtTextView = itemView.findViewById(R.id.booksQtTextView);
        }
    }
}
