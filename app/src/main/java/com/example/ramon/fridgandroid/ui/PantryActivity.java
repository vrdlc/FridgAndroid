package com.example.ramon.fridgandroid.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.ramon.fridgandroid.Constants;
import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.adapters.FirebasePantryListAdapter;
import com.example.ramon.fridgandroid.models.Item;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import butterknife.Bind;
import butterknife.ButterKnife;


public class PantryActivity extends AppCompatActivity {
    private Query mQuery;
    private Firebase mFirebasePantryRef;
    private FirebasePantryListAdapter mAdapter;

    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
        ButterKnife.bind(this);

        mFirebasePantryRef = new Firebase(Constants.FIREBASE_URL_SAVED_ITEM);

        setUpFirebaseQuery();
        setUpRecyclerView();
    }

    private void setUpFirebaseQuery() {
        String item = mFirebasePantryRef.toString();
        mQuery = new Firebase(item);
    }

    private void setUpRecyclerView() {
        mAdapter = new FirebasePantryListAdapter(mQuery, Item.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }
}