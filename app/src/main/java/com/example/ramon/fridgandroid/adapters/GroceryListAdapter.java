package com.example.ramon.fridgandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.holders.GroceryViewHolder;
import com.example.ramon.fridgandroid.holders.PantryViewHolder;
import com.example.ramon.fridgandroid.models.Item;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Ramon on 5/6/16.
 */
public class GroceryListAdapter extends RecyclerView.Adapter<GroceryViewHolder> {
    private ArrayList<Item> mItems = new ArrayList<>();
    private Context mContext;

    public GroceryListAdapter(Context context, ArrayList<Item> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pantry_list_item, parent, false);
        GroceryViewHolder viewHolder = new GroceryViewHolder(view, mItems);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GroceryViewHolder holder, int position) {
        holder.bindItem(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
