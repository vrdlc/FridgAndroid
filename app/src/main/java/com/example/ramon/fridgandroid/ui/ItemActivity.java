package com.example.ramon.fridgandroid.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ramon.fridgandroid.util.Constants;
import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.adapters.FirebaseItemListAdapter;
import com.example.ramon.fridgandroid.models.Item;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemActivity extends AppCompatActivity {
    private Query mQuery;
    private Firebase mFirebaseItemsRef;
    private FirebaseItemListAdapter mAdapter;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        ButterKnife.bind(this);

        mFirebaseItemsRef = new Firebase(Constants.FIREBASE_URL_SAVED_ITEM);

        setUpFirebaseQuery();
        setUpRecyclerView();
    }

    private void setUpFirebaseQuery() {
        String item = mFirebaseItemsRef.toString();
        mQuery = new Firebase(item);
    }

    private void setUpRecyclerView() {
        mAdapter = new FirebaseItemListAdapter(mQuery, Item.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }
}
