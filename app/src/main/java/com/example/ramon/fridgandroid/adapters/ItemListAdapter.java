package com.example.ramon.fridgandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ramon.fridgandroid.ItemViewHolder;
import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.models.Item;

import java.util.ArrayList;

/**
 * Created by Ramon on 5/2/16.
 */
public class ItemListAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private ArrayList<Item> mItems = new ArrayList<>();
    private Context mContext;

    public ItemListAdapter(Context context, ArrayList<Item> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_item, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view, mItems);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bindItem(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
