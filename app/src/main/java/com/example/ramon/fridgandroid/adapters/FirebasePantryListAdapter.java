package com.example.ramon.fridgandroid.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.holders.OnStartDragListener;
import com.example.ramon.fridgandroid.holders.PantryViewHolder;
import com.example.ramon.fridgandroid.models.Item;
import com.example.ramon.fridgandroid.util.Constants;
import com.example.ramon.fridgandroid.util.FirebaseRecyclerAdapter;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ramon on 5/6/16.
 */
public class FirebasePantryListAdapter extends FirebaseRecyclerAdapter<PantryViewHolder, Item> implements ItemTouchHelperAdapter {

    private final OnStartDragListener mDragStartListener;
    private Context mContext;
    private Item mItem;

    public FirebasePantryListAdapter(Query query, Class<Item> itemClass, OnStartDragListener dragStartListener) {
        super(query, itemClass);
        mDragStartListener = dragStartListener;
    }

    @Override
    public PantryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pantry_list_item, parent, false);
        return new PantryViewHolder(view, getItems());
    }

    @Override
    public void onBindViewHolder(final PantryViewHolder holder, int position) {
        holder.bindItem(getItem(position));
        holder.mNameTextView.setOnTouchListener(new View.OnTouchListener() {
           @Override
            public boolean onTouch(View v, MotionEvent event) {
               if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                   mDragStartListener.onStartDrag(holder);
               }
               return false;
           }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(getItems(), fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemValueChange(int position) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String uid = sharedPreferences.getString(Constants.KEY_UID, null);
        Firebase ref = new Firebase(Constants.FIREBASE_SAVED_ITEM_URL).child(uid);
        String itemKey = getItem(position).getId();
        Map<String, Object> pantry = new HashMap<String, Object>();
        pantry.put("chooseList", "grocery");
        Log.d("pantry", pantry + "");
        Log.d("itemKey", itemKey + "");
        Log.d("ref", ref + "");
        ref.child(itemKey).updateChildren(pantry);

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


