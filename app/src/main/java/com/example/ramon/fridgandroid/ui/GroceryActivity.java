package com.example.ramon.fridgandroid.ui;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.ramon.fridgandroid.holders.OnStartDragListener;
import com.example.ramon.fridgandroid.util.Constants;
import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.adapters.FirebaseGroceryListAdapter;
import com.example.ramon.fridgandroid.models.Item;
import com.example.ramon.fridgandroid.util.SimpleItemTouchHelperCallback;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GroceryActivity extends AppCompatActivity implements OnStartDragListener {
    private Query mQuery;
    private Firebase mFirebaseGroceryRef;
    private FirebaseGroceryListAdapter mAdapter;
    private SharedPreferences mSharedPreferences;
    private ItemTouchHelper mItemTouchHelper;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        ButterKnife.bind(this);

        mFirebaseGroceryRef = new Firebase(Constants.FIREBASE_SAVED_ITEM_URL);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        setUpFirebaseQuery();
        setUpRecyclerView();
    }

    private void setUpFirebaseQuery() {
        String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
        String item = mFirebaseGroceryRef.child(userUid).toString();
        mQuery = new Firebase(item).orderByChild("chooseList").equalTo("grocery");
    }

    private void setUpRecyclerView() {
        mAdapter = new FirebaseGroceryListAdapter(mQuery, Item.class, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    }

