package com.example.ramon.fridgandroid.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.holders.GroceryViewHolder;
import com.example.ramon.fridgandroid.holders.OnStartDragListener;
import com.example.ramon.fridgandroid.models.Item;
import com.example.ramon.fridgandroid.util.Constants;
import com.example.ramon.fridgandroid.util.FirebaseRecyclerAdapter;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import java.util.Collections;

/**
 * Created by Ramon on 5/6/16.
 */
public class FirebaseGroceryListAdapter extends FirebaseRecyclerAdapter<GroceryViewHolder, Item>{

//    private final OnStartDragListener mDragStartListener;
    private Context mContext;

    public FirebaseGroceryListAdapter(Query query, Class<Item> itemClass) {
        super(query, itemClass);
//        mDragStartListener = dragStartListener;
    }

    @Override
    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.grocery_list_item, parent, false);
        return new GroceryViewHolder(view, getItems());
    }

    @Override
    public void onBindViewHolder(final GroceryViewHolder holder, int position) {
        holder.bindItem(getItem(position));
        holder.mNameTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
//                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }
//
//    @Override
//    public boolean onItemMove(int fromPosition, int toPosition) {
//        Collections.swap(getItems(), fromPosition, toPosition);
//        notifyItemMoved(fromPosition, toPosition);
//        return true;
//    }
//
//    @Override
//    public void onItemValueChange(int position) {
////        mContext = parent.getContext();
////        Firebase ref = new Firebase(Constants.FIREBASE_URL_SAVED_ITEM)
////.child(uid);
////        String itemKey = getItem(position).getPushId();
////        ref.child(itemKey).();
//    }

    @Override
    public int getItemCount() {
        return getItems().size();
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
