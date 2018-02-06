package com.example.room;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.room.persistence.entity.Book;
import com.example.room.persistence.entity.PersonWithBook;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by felipe on 23/01/18.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private List<Book> bookList;
    private Context context;

    public BookAdapter(List<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        holder.titleTextView.setText(bookList.get(position).getName());
        holder.authorTextView.setText(bookList.get(position).getAuthor());
        holder.releaseDateTextView.setText(dateFormat.format(bookList.get(position).getReleaseDate()));
    }

    @Override
    public int getItemCount() {
        return bookList ==null ? 0 : bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView authorTextView;
        TextView releaseDateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.bookTitleTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
            releaseDateTextView = itemView.findViewById(R.id.releaseDateTextView);
        }
    }
}
