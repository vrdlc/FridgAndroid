package com.example.ramon.fridgandroid.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.holders.ItemViewHolder;
import com.example.ramon.fridgandroid.models.Item;
import com.example.ramon.fridgandroid.util.Constants;
import com.firebase.client.Firebase;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by Ramon on 5/2/16.
 */
public class ItemListAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private ArrayList<Item> mItems = new ArrayList<>();
    private Context mContext;
    private SharedPreferences mSharedPreferences;

    @Bind(R.id.deleteButton) Button mDeleteButton;


    public ItemListAdapter(Context context, ArrayList<Item> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);


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
