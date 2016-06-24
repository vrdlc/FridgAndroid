package com.delacruz.ramon.fridg.ui;

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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.delacruz.ramon.fridg.holders.OnStartDragListener;
import com.delacruz.ramon.fridg.util.Constants;
import com.delacruz.ramon.fridg.R;
import com.delacruz.ramon.fridg.adapters.FirebaseGroceryListAdapter;
import com.delacruz.ramon.fridg.models.Item;
import com.delacruz.ramon.fridg.util.SimpleItemTouchHelperCallback;
import com.firebase.client.Firebase;
import com.firebase.client.Query;

import butterknife.Bind;
import butterknife.ButterKnife;
import layout.AboutActivity;

public class GroceryActivity extends AppCompatActivity implements OnStartDragListener, View.OnClickListener {
    private Query mQuery;
    private Firebase mFirebaseGroceryRef;
    private FirebaseGroceryListAdapter mAdapter;
    private SharedPreferences mSharedPreferences;
    private ItemTouchHelper mItemTouchHelper;
    private Firebase mFirebaseRef;


    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.pantryFab) FloatingActionButton mPantryFab;
    @Bind(R.id.saveFab) FloatingActionButton mSaveFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        ButterKnife.bind(this);

        Firebase.setAndroidContext(this);
        mFirebaseRef = new Firebase(Constants.FIREBASE_URL);
        mFirebaseGroceryRef = new Firebase(Constants.FIREBASE_SAVED_ITEM_URL);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mPantryFab.setOnClickListener(this);
        mSaveFab.setOnClickListener(this);

        setUpFirebaseQuery();
        setUpRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        if (id == R.id.action_about) {
            Intent intentAbout = new Intent(GroceryActivity.this, AboutActivity.class);
            startActivity(intentAbout);
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pantryFab:
                Intent intentGrocery = new Intent(GroceryActivity.this, PantryActivity.class);
                startActivity(intentGrocery);
                break;
            case R.id.saveFab:
                openDialog();
                break;
        }
    }

    private void openDialog() {
        LayoutInflater inflater = LayoutInflater.from(GroceryActivity.this);
        View subView = inflater.inflate(R.layout.fragment_save_item, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Item To List");
        builder.setMessage("Enter Item Info, Select List, and Click 'Okay'");
        builder.setView(subView);

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
                Toast.makeText(GroceryActivity.this, "Cancel", Toast.LENGTH_LONG).show();
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


    protected void logout() {
        mFirebaseRef.unauth();
        takeUserToLoginScreenOnUnAuth();
    }

    private void takeUserToLoginScreenOnUnAuth() {
        Intent intent = new Intent(GroceryActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}
