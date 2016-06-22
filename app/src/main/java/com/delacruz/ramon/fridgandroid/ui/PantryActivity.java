package com.delacruz.ramon.fridgandroid.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.delacruz.ramon.fridgandroid.holders.OnStartDragListener;
import com.delacruz.ramon.fridgandroid.util.Constants;
import com.delacruz.ramon.fridgandroid.R;
import com.delacruz.ramon.fridgandroid.adapters.FirebasePantryListAdapter;
import com.delacruz.ramon.fridgandroid.models.Item;
import com.delacruz.ramon.fridgandroid.util.SimpleItemTouchHelperCallback;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import butterknife.Bind;
import butterknife.ButterKnife;


public class PantryActivity extends AppCompatActivity implements OnStartDragListener, View.OnClickListener {
    private Query mQuery;
    private Firebase mFirebasePantryRef;
    private FirebasePantryListAdapter mAdapter;
    private SharedPreferences mSharedPreferences;
    private ItemTouchHelper mItemTouchHelper;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.groceryFab) FloatingActionButton mGroceryFab;
    @Bind(R.id.saveFab) FloatingActionButton mSaveFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
        ButterKnife.bind(this);

        Firebase.setAndroidContext(this);
        mFirebasePantryRef = new Firebase(Constants.FIREBASE_SAVED_ITEM_URL);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        mGroceryFab.setOnClickListener(this);
        mSaveFab.setOnClickListener(this);


        setUpFirebaseQuery();
        setUpRecyclerView();
    }

    private void setUpFirebaseQuery() {
        String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
        String item = mFirebasePantryRef.child(userUid).toString();
        mQuery = new Firebase(item).orderByChild("chooseList").equalTo("pantry");
    }

    private void setUpRecyclerView() {
        mAdapter = new FirebasePantryListAdapter(mQuery, Item.class, this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.groceryFab:
                Intent intentGrocery = new Intent(PantryActivity.this, GroceryActivity.class);
                startActivity(intentGrocery);
                break;
            case R.id.saveFab:
                openDialog();
                break;
        }
    }

    private void openDialog() {
        LayoutInflater inflater = LayoutInflater.from(PantryActivity.this);
        View subView = inflater.inflate(R.layout.fragment_save_item, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Item To List");
        builder.setMessage("Enter Item Info, Select List, and Click 'Okay'");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        final EditText subEditText = (EditText) subView.findViewById(R.id.nameEditText);
        final EditText subEditQuantity = (EditText) subView.findViewById(R.id.quantityEditText);
        final EditText subEditNotes = (EditText) subView.findViewById(R.id.notesEditText);
        final Spinner mSpinner = (Spinner) subView.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = subEditText.getText().toString();
                String quantity = subEditQuantity.getText().toString();
                String notes = subEditNotes.getText().toString();
                int listId = mSpinner.getSelectedItemPosition();


                String list;
                if (listId == 0) {
                    list = "pantry";
                } else {
                    list = "grocery";
                }
                saveItemToFirebase(name, quantity, notes, list);

                Toast.makeText(getApplicationContext(), "Saved to " + list, Toast.LENGTH_SHORT).show();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(PantryActivity.this, "Cancel", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }


    public void saveItemToFirebase(String name, String quantity, String notes, String list) {
        String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
        Firebase savedItemRef = new Firebase(Constants.FIREBASE_SAVED_ITEM_URL).child(userUid);

        Item item = new Item(name, quantity, notes, list);
        Firebase itemRef = savedItemRef.push();
        String keyId = itemRef.getKey();
        item.setId(keyId);
        itemRef.setValue(item);
    }
}