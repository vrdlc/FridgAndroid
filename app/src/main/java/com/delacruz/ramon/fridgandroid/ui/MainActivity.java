package com.delacruz.ramon.fridgandroid.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.delacruz.ramon.fridgandroid.models.User;
import com.delacruz.ramon.fridgandroid.util.Constants;
import com.delacruz.ramon.fridgandroid.R;
import com.delacruz.ramon.fridgandroid.models.Item;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ValueEventListener mUserRefListener;
    private Firebase mSavedItemRef;
    private Firebase mUserRef;
    private String mUid;
    private Firebase mFirebaseRef;
//    private ValueEventListener mSavedItemRefListener;
    private Item mItem;
    private SharedPreferences mSharedPreferences;

    @Bind (R.id.pantryButton) Button mPantryButton;
    @Bind (R.id.everythingButton) Button mEverythingButton;
    @Bind (R.id.groceryButton) Button mGroceryButton;
    @Bind (R.id.addToList) Button mAddToList;
    @Bind (R.id.nameEditText) EditText mNameEditText;
    @Bind (R.id.quantityEditText) EditText mQuantityEditText;
    @Bind (R.id.notesEditText) EditText mNotesEditText;
    @Bind (R.id.welcomeTextView) TextView mWelcomeTextView;
    @Bind (R.id.spinner) Spinner mSpinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUid = mSharedPreferences.getString(Constants.KEY_UID, null);
        mUserRef = new Firebase(Constants.FIREBASE_URL_USERS).child(mUid);

        mFirebaseRef = new Firebase(Constants.FIREBASE_URL);
        ButterKnife.bind(this);


        mPantryButton.setOnClickListener(this);
        mEverythingButton.setOnClickListener(this);
        mGroceryButton.setOnClickListener(this);
        mAddToList.setOnClickListener(this);

        mSavedItemRef = new Firebase(Constants.FIREBASE_SAVED_ITEM_URL);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mUserRefListener = mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                mWelcomeTextView.setText("Welcome, " + user.getName() + "!");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d(TAG, "Read failed");
            }
        });
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
//        if (id == R.id.action_pantry) {
//            Intent intent = new Intent)
//        }
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
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


                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();

                saveItemToFirebase(name, quantity, notes, list);
                mNameEditText.setText("");
                mQuantityEditText.setText("");
                mNotesEditText.setText("");


            default:
                break;
        }
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
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}