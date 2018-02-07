package com.example.room;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.room.persistence.entity.Person;
import com.example.room.persistence.entity.PersonWithBook;

import java.util.List;

/**
 * Created by felipe on 23/01/18.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private List<Person> personList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public PersonAdapter(List<Person> personList, Context context,
                         OnItemClickListener onItemClickListener) {
        this.personList = personList;
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
                        personList.get(position).getFirstName(),
                        personList.get(position).getLastName()
                ));

        holder.personEmailTextView.setText(personList.get(position).getEmail());

        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return personList==null ? 0 : personList.size();
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView personNameTextView;
        TextView personEmailTextView;
        LinearLayout rootLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            personNameTextView = itemView.findViewById(R.id.nameTextView);
            personEmailTextView = itemView.findViewById(R.id.emailTextView);
            rootLayout = itemView.findViewById(R.id.rootLayout);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem deleteMenu = menu.add(Menu.NONE, 0, 0, R.string.delete_menu);
            MenuItem updateMenu = menu.add(Menu.NONE, 1, 1, R.string.update_menu);

            deleteMenu.setOnMenuItemClickListener(itemMenuClick);
            updateMenu.setOnMenuItemClickListener(itemMenuClick);
        }

        private final MenuItem.OnMenuItemClickListener itemMenuClick = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 0:
                        onItemClickListener.onItemDeleteClick(getAdapterPosition());
                        return true;
                    case 1:
                        onItemClickListener.onItemUpdateClick(getAdapterPosition());
                        return true;
                }
                return false;
            }
        };
    }

    interface OnItemClickListener {
        void onItemClick(int position);
        void onItemDeleteClick(int position);
        void onItemUpdateClick(int position);
    }
}
