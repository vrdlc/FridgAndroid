package com.example.ramon.fridgandroid.ui;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.ramon.fridgandroid.util.Constants;
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
    private SharedPreferences mSharedPreferences;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
//    @Bind(R.id.updateButton) Button mUpdateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
        ButterKnife.bind(this);

        mFirebasePantryRef = new Firebase(Constants.FIREBASE_SAVED_ITEM_URL);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        setUpFirebaseQuery();
        setUpRecyclerView();
    }

    private void setUpFirebaseQuery() {
        String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
        String item = mFirebasePantryRef.child(userUid).toString();
        mQuery = new Firebase(item).orderByChild("chooseList").equalTo("pantry");
    }

    private void setUpRecyclerView() {
        mAdapter = new FirebasePantryListAdapter(mQuery, Item.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }
}