package com.delacruz.ramon.fridgandroid.ui;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.delacruz.ramon.fridgandroid.R;
import com.delacruz.ramon.fridgandroid.adapters.FirebaseItemListAdapter;
import com.delacruz.ramon.fridgandroid.models.Item;
import com.delacruz.ramon.fridgandroid.util.Constants;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemActivity extends AppCompatActivity  implements View.OnClickListener {
    private Query mQuery;
    private Firebase mFirebaseItemsRef;
    private FirebaseItemListAdapter mAdapter;
    private SharedPreferences mSharedPreferences;


    @Bind(R.id.itemRecyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.saveFab) FloatingActionButton mSaveFab;

//    @Bind(R.id.nameEditText) EditText subEditText;
//    @Bind(R.id.quantityEditText) EditText subEditQuantity;
//    @Bind(R.id.notesEditText) EditText subEditNotes;
//    @Bind (R.id.spinner) Spinner mSpinner;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSaveFab.setOnClickListener(this);

        mFirebaseItemsRef = new Firebase(Constants.FIREBASE_SAVED_ITEM_URL);


        setUpFirebaseQuery();
        setUpRecyclerView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.saveFab);
        assert fab != null;
    }

    @Override
    public void onClick(View v) {
        openDialog();
        }

    private void openDialog() {
        LayoutInflater inflater = LayoutInflater.from(ItemActivity.this);
        View subView = inflater.inflate(R.layout.fragment_save_item, null);

        //TODO
        // BIND THESE THINGS W/ BUTTERKNIFE

//        final ImageView subImageView = (ImageView) subView.findViewById(R.id.image);
//        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
//        subImageView.setImageDrawable(drawable);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Item To List");
        builder.setMessage("Enter Item Info, Select List, and Click 'Okay'");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

                final EditText subEditText = (EditText) subView.findViewById(R.id.nameEditText);
                final EditText subEditQuantity = (EditText) subView.findViewById(R.id.quantityEditText);
                final EditText subEditNotes = (EditText) subView.findViewById(R.id.notesEditText);
                final Spinner mSpinner = (Spinner) subView.findViewById(R.id.spinner);
//                final Button addToList = (Button) subView.findViewById(R.id.addToList);

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
                Toast.makeText(ItemActivity.this, "Cancel", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

    private void setUpFirebaseQuery() {
        String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
        String item = mFirebaseItemsRef.child(userUid).toString();
        mQuery = new Firebase(item);
    }

    private void setUpRecyclerView() {
        mAdapter = new FirebaseItemListAdapter(mQuery, Item.class);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
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
