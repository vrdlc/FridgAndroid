package com.delacruz.ramon.fridg.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delacruz.ramon.fridg.R;
import com.delacruz.ramon.fridg.holders.PantryViewHolder;
import com.delacruz.ramon.fridg.models.Item;

import java.util.ArrayList;

/**
 * Created by Ramon on 5/6/16.
 */
public class PantryListAdapter extends RecyclerView.Adapter<PantryViewHolder> {
    private ArrayList<Item> mItems = new ArrayList<>();
    private Context mContext;

    public PantryListAdapter(Context context, ArrayList<Item> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public PantryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pantry_list_item, parent, false);
        PantryViewHolder viewHolder = new PantryViewHolder(view, mItems);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PantryViewHolder holder, int position) {
        holder.bindItem(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}