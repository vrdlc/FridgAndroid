package com.example.ramon.fridgandroid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.holders.PantryViewHolder;
import com.example.ramon.fridgandroid.models.Item;
import com.example.ramon.fridgandroid.util.FirebaseRecyclerAdapter;
import com.firebase.client.Query;

/**
 * Created by Ramon on 5/6/16.
 */
public class FirebasePantryListAdapter extends FirebaseRecyclerAdapter<PantryViewHolder, Item> {

    public FirebasePantryListAdapter(Query query, Class<Item> itemClass) {
        super(query, itemClass);
    }

    @Override
    public PantryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pantry_list_item, parent, false);
        return new PantryViewHolder(view, getItems());
    }

    @Override
    public void onBindViewHolder(PantryViewHolder holder, int position) {
        holder.bindItem(getItem(position));
    }

    @Override
    protected void itemAdded(Item item, String key, int position) {

    }

    @Override
    protected void itemChanged(Item oldItem, Item newItem, String key, int position) {

    }

    @Override
    protected void itemRemoved(Item item, String key, int position) {

    }

    @Override
    protected void itemMoved(Item item, String key, int oldPosition, int newPosition) {

    }
}


