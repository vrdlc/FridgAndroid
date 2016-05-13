package com.example.ramon.fridgandroid.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ramon.fridgandroid.util.Constants;
import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.models.Item;
import com.firebase.client.Firebase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Firebase mSavedItemRef;
//    private ValueEventListener mSavedItemRefListener;
    private Item mItem;

    @Bind (R.id.pantryButton) Button mPantryButton;
    @Bind (R.id.everythingButton) Button mEverythingButton;
    @Bind (R.id.groceryButton) Button mGroceryButton;
    @Bind (R.id.addToList) Button mAddToList;
    @Bind (R.id.nameEditText) EditText mNameEditText;
    @Bind (R.id.quantityEditText) EditText mQuantityEditText;
    @Bind (R.id.notesEditText) EditText mNotesEditText;
    @Bind (R.id.spinner) Spinner mSpinner;



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

        Spinner mSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mSavedItemRef.removeEventListener(mSavedItemRefListener);
//    } DO I NEED THIS? I AM CURRENTLY NOT USING A VALUE EVENT LISTENER, BUT DO I NEED TO?

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pantryButton:
                Intent intentPantry = new Intent(MainActivity.this, PantryActivity.class);
                startActivity(intentPantry);
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
                int listId = mSpinner.getSelectedItemPosition();
                String list;
                if (listId == 0) {
                    list = "pantry";
                } else {
                    list = "grocery";
                }

//                Firebase ref = new Firebase(Constants.FIREBASE_URL_SAVED_ITEM);
//                ref.push().setValue(mItem);
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();

                saveItemToFirebase(name, quantity, notes, list);
                mNameEditText.setText("");
                mQuantityEditText.setText("");
                mNotesEditText.setText("");


                //SHARED PREFERENCES
                //if(!(name).equals("")) {
                //  addToSharedPreferences(name);
                //}


            default:
                break;
        }
    }

    public void saveItemToFirebase(String name, String quantity, String notes, String list) {
        Firebase savedItemRef = new Firebase(Constants.FIREBASE_URL_SAVED_ITEM);

        Item item = new Item(name, quantity, notes, list);
        Firebase itemRef = savedItemRef.push();
        String keyId = itemRef.getKey();
        item.setId(keyId);
        itemRef.setValue(item);
        Log.v("WHEEEE", item.getId());

    }


}