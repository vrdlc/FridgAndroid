package com.delacruz.ramon.fridgandroid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delacruz.ramon.fridgandroid.R;
import com.delacruz.ramon.fridgandroid.holders.ItemViewHolder;
import com.delacruz.ramon.fridgandroid.models.Item;
import com.delacruz.ramon.fridgandroid.util.FirebaseRecyclerAdapter;
import com.firebase.client.Query;


/**
 * Created by Ramon on 5/2/16.
 */
public class FirebaseItemListAdapter extends FirebaseRecyclerAdapter<ItemViewHolder, Item> {

    public FirebaseItemListAdapter(Query query, Class<Item> itemClass) {
        super(query, itemClass);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_item, parent, false);
        return new ItemViewHolder(view, getItems());
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
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

