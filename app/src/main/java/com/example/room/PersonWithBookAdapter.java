package com.example.room;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.room.persistence.entity.Book;
import com.example.room.persistence.entity.Person;
import com.example.room.persistence.entity.PersonWithBook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felipe on 23/01/18.
 */

public class PersonWithBookAdapter extends RecyclerView.Adapter<PersonWithBookAdapter.ViewHolder> {

    private List<PersonWithBook> personWithBookList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public PersonWithBookAdapter(List<PersonWithBook> personWithBookList, Context context,
                                 OnItemClickListener onItemClickListener) {
        this.personWithBookList = personWithBookList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_person, parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.personNameTextView.setText(
                String.format("%s %s",
                        personWithBookList.get(position).person.getFirstName(),
                        personWithBookList.get(position).person.getLastName()
                ));

        holder.bookQtTextView.setText(
               context.getString(R.string.books_quantity, personWithBookList.get(position).bookList.size())
        );

        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);

            }
        });

        holder.rootLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClickListener.onItemLongClick(position);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return personWithBookList==null ? 0 : personWithBookList.size();
    }

    public List<PersonWithBook> getPersonWithBookList() {
        return personWithBookList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView personNameTextView;
        TextView bookQtTextView;
        LinearLayout rootLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            personNameTextView = itemView.findViewById(R.id.nameTextView);
            bookQtTextView = itemView.findViewById(R.id.booksQtTextView);
            rootLayout = itemView.findViewById(R.id.rootLayout);
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }
}
