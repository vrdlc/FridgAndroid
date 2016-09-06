package delacruz.fridg30.Grocery;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import delacruz.fridg30.Constants;
import delacruz.fridg30.ItemTouchHelperAdapter;
import delacruz.fridg30.Models.Item;
import delacruz.fridg30.OnStartDragListener;

/**
 * Created by Ramon on 7/18/16.
 */
public class FirebaseListAdapter extends FirebaseRecyclerAdapter<Item, FirebaseViewHolder> implements ItemTouchHelperAdapter {

    private DatabaseReference mDatabaseReference;
    private OnStartDragListener mDragStartListener;
    private Context mContext;
    private ArrayList<Item> mItems;
    private ChildEventListener mChildEventListener;

    public FirebaseListAdapter(Class<Item> modelClass, int modelLayout,
                               Class<FirebaseViewHolder> viewHolderClass,
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
    protected void populateViewHolder(final FirebaseViewHolder viewHolder, Item model, int position) {

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
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("position", viewHolder.getAdapterPosition());
                intent.putExtra("items", Parcels.wrap(mItems));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public void onItemValueChange(int position) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String uid = sharedPreferences.getString(Constants.KEY_UID, null);
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_LOCATION_ITEM);         //DON'T FORGET getChild(uid);
////THIS IS CALLED IN THE SimpleItemTouchHelperCallback
//
//        String itemKey = getItem(position).getId();
//        Log.d("ItemKey", itemKey + "");
//        Map<String, Object> item = new HashMap<String, Object>();
//        item.put("chooseList", "pantry");
//        ref.child(itemKey).updateChildren(item);
//        Toast.makeText(mContext.getApplicationContext(), "Moved to Pantry List", Toast.LENGTH_SHORT).show();
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

//    private void setIndexInFirebase() {
//        for (Item item : mItems) {
//            int index = mItems.indexOf(item);
//            DatabaseReference ref = getRef(index);
//            item.setIndex(Integer.toString(index));
//            Log.d("index no", index + "");
//            ref.setValue(item);
//        }
//    }

    @Override
    public void cleanup() {
        super.cleanup();
//        setIndexInFirebase();
        mItems.clear();
        mDatabaseReference.removeEventListener(mChildEventListener);
    }
 }