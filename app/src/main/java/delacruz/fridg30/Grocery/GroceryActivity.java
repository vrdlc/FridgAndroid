//TODO
//While rewriting this code, make sure bare minimum files are carried over. Lots of redundencies in old code
//Use new Firebase code
//Add Categorie field to DB
//Can I make modular categorie view holders? ie one view holder that can repeat dynamically for every category on a page, but only when category is being used?

package delacruz.fridg30.Grocery;

import android.content.Context;
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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import butterknife.Bind;
import butterknife.ButterKnife;
import delacruz.fridg30.Constants;
import delacruz.fridg30.Models.Item;
import delacruz.fridg30.OnStartDragListener;
import delacruz.fridg30.Pantry.PantryActivity;
import delacruz.fridg30.R;
import delacruz.fridg30.SimpleItemTouchHelperCallback;

public class GroceryActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mGroceryDatabase;
    private ValueEventListener mValueEventListener;
//    private FirebaseRecyclerAdapter mFirebaseRecyclerAdapter;
    private FirebaseListAdapter mFirebaseListAdapter;
    private OnStartDragListener mOnDragListener;
    private String uId;
    private SharedPreferences mSharedPreferences;
    private ItemTouchHelper mItemTouchHelper;
    private Context mContext;


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

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mContext = this;


        // Write a message to the database
        mGroceryDatabase = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_LOCATION_ITEM);
        setUpFirebaseAdapter();

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
                Intent intentPantry = new Intent(GroceryActivity.this, PantryActivity.class);
                startActivity(intentPantry);
                finish();
                break;
            case R.id.saveFab:
                openDialog();
                break;

        }
    }

    @Override
    public void onDestroy() {
//        mGroceryDatabase.removeEventListener(mValueEventListener);
        super.onDestroy();
//        mFirebaseListAdapter.cleanup();

    }

    private void setUpFirebaseAdapter() {

        Query query = FirebaseDatabase.getInstance()
                .getReference(Constants.FIREBASE_LOCATION_ITEM)
//                .child(uId)
                .orderByChild("chooseList").equalTo("grocery");

        mFirebaseListAdapter = new FirebaseListAdapter
                (Item.class, R.layout.universal_list_item, FirebaseViewHolder.class,
                        query, mOnDragListener, mContext) {

            @Override
            protected void populateViewHolder(FirebaseViewHolder viewHolder,
                                              Item model, int position) {
                viewHolder.bindItem(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseListAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mFirebaseListAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
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

        DatabaseReference itemRef = mGroceryDatabase.push();
        String keyId = itemRef.getKey();
        item.setId(keyId);
        itemRef.setValue(item);
    }
}