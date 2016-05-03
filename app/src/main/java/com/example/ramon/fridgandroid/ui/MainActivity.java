package com.example.ramon.fridgandroid.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ramon.fridgandroid.Constants;
import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.database.DatabaseAdapter;
import com.example.ramon.fridgandroid.models.Item;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Firebase mSavedItemRef;
    private ValueEventListener mSavedItemRefListener;
    private Item mItem;

    @Bind(R.id.pantryButton) Button mPantryButton;
    @Bind(R.id.everythingButton) Button mEverythingButton;
    @Bind (R.id.groceryButton) Button mGroceryButton;
    @Bind (R.id.addToList) Button mAddToList;
//    @Bind (R.id.addToGrocery) Button mAddToGrocery;

    @Bind (R.id.nameEditText) EditText mNameEditText;
    @Bind (R.id.quantityEditText) EditText mQuantityEditText;
    @Bind (R.id.notesEditText) EditText mNotesEditText;


    //SQLITE DATABASEADAPTER CALLER
//    final DatabaseAdapter db = new DatabaseAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mPantryButton.setOnClickListener(this);
        mEverythingButton.setOnClickListener(this);
        mGroceryButton.setOnClickListener(this);
        mAddToList.setOnClickListener(this);

        mSavedItemRef = new Firebase(Constants.FIREBASE_URL_SAVED_ITEM);
        mSavedItemRefListener = mSavedItemRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String items = dataSnapshot.getValue().toString();
                Log.d("Item Saved", items);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSavedItemRef.removeEventListener(mSavedItemRefListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pantryButton:
                Intent intentPantry = new Intent(MainActivity.this, PantryActivity.class);
                startActivity(intentPantry);
                Log.v("I WANT TO SEE THE DB",  "please");
                break;
            case R.id.everythingButton:
                Intent intentEverything = new Intent(MainActivity.this, ItemActivity.class);
                startActivity(intentEverything);
                break;
            case R.id.groceryButton:
                Intent intentGrocery = new Intent(MainActivity.this, GroceryActivity.class);
                startActivity(intentGrocery);
                break;
            case R.id.addToList:
                String name = mNameEditText.getText().toString();
                String quantity = mQuantityEditText.getText().toString();
                String notes = mNotesEditText.getText().toString();

                Firebase ref = new Firebase(Constants.FIREBASE_URL_SAVED_ITEM);
                ref.push().setValue(mItem);
//                Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();

                saveItemToFirebase(name);
                mNameEditText.setText("");
                mQuantityEditText.setText("");
                mNotesEditText.setText("");


                //SHARED PREFERENCES
                //if(!(name).equals("")) {
                //  addToSharedPreferences(name);
                //}


                //LOCAL SQLITE DATABASE
//                db.openDB();
//
//                db.add(name, quantity, notes);
//                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
//
//                db.close();

            default:
                break;
        }
    }

    public void saveItemToFirebase(String name) {
        Firebase savedItemRef = new Firebase(Constants.FIREBASE_URL_SAVED_ITEM);

        // DO I NEED THREE REFS HERE?
        savedItemRef.push().setValue(name);

    }


}
