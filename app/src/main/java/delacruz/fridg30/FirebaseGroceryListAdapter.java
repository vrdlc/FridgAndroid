package delacruz.fridg30;


import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

import delacruz.fridg30.Models.Item;

/**
 * Created by Ramon on 7/18/16.
 */
public class FirebaseGroceryListAdapter extends FirebaseRecyclerAdapter<Item, FirebaseGroceryViewHolder> implements ItemTouchHelperAdapter {

    private DatabaseReference mDatabaseReference;
    private OnStartDragListener mDragStartListener;
    private Context mContext;
    private ArrayList<Item> mItems;
    private ChildEventListener mChildEventListener;

    public FirebaseGroceryListAdapter(Class<Item> modelClass, int modelLayout,
                                         Class<FirebaseGroceryViewHolder> viewHolderClass,
                                         Query query, OnStartDragListener onStartDragListener, Context context) {

        super(modelClass, modelLayout, viewHolderClass, query);
        mDatabaseReference = query.getRef();
        mDragStartListener = onStartDragListener;
        mContext = context;
        mItems = new ArrayList<Item>();

        mChildEventListener = mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mItems.add(dataSnapshot.getValue(Item.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void populateViewHolder(final FirebaseGroceryViewHolder viewHolder, Item model, int position) {
        viewHolder.bindItem(model);

        viewHolder.mItemNameView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(viewHolder);
                }
                return false;
            }

        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GroceryDetailActivity.class);
                intent.putExtra("position", viewHolder.getAdapterPosition());
                intent.putExtra("restaurants", Parcels.wrap(mItems));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        getRef(position).removeValue();
    }

    private void setIndexInFirebase() {
        for (Item item : mItems) {
            int index = mItems.indexOf(item);
            DatabaseReference ref = getRef(index);
            item.setIndex(Integer.toString(index));
            ref.setValue(item);
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
        setIndexInFirebase();
        mItems.clear();
        mDatabaseReference.removeEventListener(mChildEventListener);
    }
 }