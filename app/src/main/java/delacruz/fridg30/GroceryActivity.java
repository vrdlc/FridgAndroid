//TODO
//While rewriting this code, make sure bare minimum files are carried over. Lots of redundencies in old code
//Use new Firebase code
//Add Categorie field to DB
//Can I make modular categorie view holders? ie one view holder that can repeat dynamically for every category on a page, but only when category is being used?

package delacruz.fridg30;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import butterknife.Bind;
import butterknife.ButterKnife;

public class GroceryActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mGroceryDatabase;


    private static final String TAG = GroceryActivity.class.getSimpleName();
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.groceryFab) FloatingActionButton mGroceryFab;
    @Bind(R.id.saveFab) FloatingActionButton mSaveFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        ButterKnife.bind(this);
        mGroceryFab.setOnClickListener(this);
        mSaveFab.setOnClickListener(this);



        // Write a message to the database
        mGroceryDatabase= FirebaseDatabase.getInstance().getReference().child("items");

//        mGroceryDatabase.setValue("Hello, World!");

        mGroceryDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Item item = dataSnapshot.getValue(Item.class);
                Log.d(TAG, "Value is: " + item);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.groceryFab:
                Intent intentGrocery = new Intent(GroceryActivity.this, GroceryActivity.class);
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
        AlertDialog alertDialog = builder.create(); //Do I need this?

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
//        String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
        Item item = new Item(name, quantity, notes, list);

        DatabaseReference itemRef= mGroceryDatabase.push();
        String keyId = itemRef.getKey();
        item.setId(keyId);
        itemRef.setValue(item);
    }

//    private Query mQuery;
//    private Firebase mFirebaseGroceryRef;
//    private FirebaseGroceryListAdapter mAdapter;
//    private SharedPreferences mSharedPreferences;
//    private ItemTouchHelper mItemTouchHelper;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_grocery);
//        ButterKnife.bind(this);
//
//        Firebase.setAndroidContext(this);
//        mFirebaseGroceryRef = new Firebase(Constants.FIREBASE_SAVED_ITEM_URL);
//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//
//        mPantryFab.setOnClickListener(this);
//        mSaveFab.setOnClickListener(this);
//
//        setUpFirebaseQuery();
//        setUpRecyclerView();
//    }
//
//    private void setUpFirebaseQuery() {
//        String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
//        String item = mFirebaseGroceryRef.child(userUid).toString();
//        mQuery = new Firebase(item).orderByChild("chooseList").equalTo("grocery");
//    }
//
//    private void setUpRecyclerView() {
//        mAdapter = new FirebaseGroceryListAdapter(mQuery, Item.class, this);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setAdapter(mAdapter);
//
//        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mAdapter);
//        mItemTouchHelper = new ItemTouchHelper(callback);
//        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
//    }
//
//    @Override
//    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
//        mItemTouchHelper.startDrag(viewHolder);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.pantryFab:
//                Intent intentGrocery = new Intent(GroceryActivity.this, PantryActivity.class);
//                startActivity(intentGrocery);
//                break;
//            case R.id.saveFab:
//                openDialog();
//                break;
//        }
//    }
//
//    private void openDialog() {
//        LayoutInflater inflater = LayoutInflater.from(GroceryActivity.this);
//        View subView = inflater.inflate(R.layout.fragment_save_item, null);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Add Item To List");
//        builder.setMessage("Enter Item Info, Select List, and Click 'Okay'");
//        builder.setView(subView);
//        AlertDialog alertDialog = builder.create();
//
//        final EditText subEditText = (EditText) subView.findViewById(R.id.nameEditText);
//        final EditText subEditQuantity = (EditText) subView.findViewById(R.id.quantityEditText);
//        final EditText subEditNotes = (EditText) subView.findViewById(R.id.notesEditText);
//        final Spinner mSpinner = (Spinner) subView.findViewById(R.id.spinner);
//
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.spinner_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mSpinner.setAdapter(adapter);
//
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                String name = subEditText.getText().toString();
//                String quantity = subEditQuantity.getText().toString();
//                String notes = subEditNotes.getText().toString();
//                int listId = mSpinner.getSelectedItemPosition();
//
//                String list;
//                if (listId == 0) {
//                    list = "pantry";
//                } else {
//                    list = "grocery";
//                }
//                saveItemToFirebase(name, quantity, notes, list);
//
//                Toast.makeText(getApplicationContext(), "Saved to " + list, Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(GroceryActivity.this, "Cancel", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        builder.show();
//    }
//
//
//    public void saveItemToFirebase(String name, String quantity, String notes, String list) {
//        String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
//        Firebase savedItemRef = new Firebase(Constants.FIREBASE_SAVED_ITEM_URL).child(userUid);
//
//        Item item = new Item(name, quantity, notes, list);
//
//        Firebase itemRef = savedItemRef.push();
//        String keyId = itemRef.getKey();
//        item.setId(keyId);
//        itemRef.setValue(item);
//    }


}
