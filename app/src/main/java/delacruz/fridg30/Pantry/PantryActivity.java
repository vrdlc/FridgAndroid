package delacruz.fridg30.Pantry;

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
import delacruz.fridg30.Grocery.FirebaseListAdapter;
import delacruz.fridg30.Grocery.FirebaseViewHolder;
import delacruz.fridg30.Grocery.GroceryActivity;
import delacruz.fridg30.Models.Item;
import delacruz.fridg30.OnStartDragListener;
import delacruz.fridg30.R;
import delacruz.fridg30.SimpleItemTouchHelperCallback;

public class PantryActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference mPantryDatabase;
    private ValueEventListener mValueEventListener;
//    private FirebaseRecyclerAdapter mFirebaseRecyclerAdapter;
    private PantryFirebaseListAdapter mFirebaseListAdapter;
    private OnStartDragListener mOnDragListener;
    private String uId;
    private SharedPreferences mSharedPreferences;
    private ItemTouchHelper mItemTouchHelper;
    private Context mContext;


    private static final String TAG = PantryActivity.class.getSimpleName();
    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;
    @Bind(R.id.pantryFab) FloatingActionButton mPantryFab;
    @Bind(R.id.saveFab) FloatingActionButton mSaveFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);
        ButterKnife.bind(this);
        mPantryFab.setOnClickListener(this);
        mSaveFab.setOnClickListener(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mContext = this;


        // Write a message to the database
        mPantryDatabase = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_LOCATION_PANTRY);
        setUpFirebaseAdapter();

        mPantryDatabase.addValueEventListener(new ValueEventListener() {
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
            case R.id.pantryFab:
                Intent intentGrocery = new Intent(PantryActivity.this, GroceryActivity.class);
                startActivity(intentGrocery);
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
                .getReference(Constants.FIREBASE_LOCATION_PANTRY)
//                .child(uId)
                .orderByChild("category");

        mFirebaseListAdapter = new PantryFirebaseListAdapter
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
        LayoutInflater inflater = LayoutInflater.from(PantryActivity.this);
        View subView = inflater.inflate(R.layout.fragment_save_item, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Item To List");
        builder.setMessage("Enter Item Info, Select List, and Click 'Okay'");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create(); //Do I need this?

        final EditText subEditText = (EditText) subView.findViewById(R.id.nameEditText);
        final EditText subEditQuantity = (EditText) subView.findViewById(R.id.quantityEditText);
        final EditText subEditNotes = (EditText) subView.findViewById(R.id.notesEditText);
        final Spinner mCategory = (Spinner) subView.findViewById(R.id.categorySpinner);
        final Spinner mLocation = (Spinner) subView.findViewById(R.id.locationSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.location_spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLocation.setAdapter(adapter);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String name = subEditText.getText().toString();
                String quantity = subEditQuantity.getText().toString();
                String notes = subEditNotes.getText().toString();
                int categoryId = mCategory.getSelectedItemPosition();
                int listId = mLocation.getSelectedItemPosition();

                String category;
                if (categoryId == 0) {
                    category = "dairy";
                } else if (categoryId == 1) {
                    category = "meat";
                } else if (categoryId == 2) {
                    category = "produce";
                } else if (categoryId == 3) {
                    category = "bread";
                } else if (categoryId == 4) {
                    category = "frozen";
                } else if (categoryId == 5) {
                    category = "meat";
                } else if (categoryId == 6) {
                    category = "staples";
                } else if (categoryId == 7) {
                    category = "household";
                } else if (categoryId == 8) {
                    category = "personal_care";
                } else if (categoryId == 9) {
                    category = "canned";
                } else {
                    category = "other";
                }

                String list;
                if (listId == 0) {
                    list = "pantry";
                } else {
                    list = "grocery";
                }
                saveItemToFirebase(name, quantity, notes, category, list);

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

    public void saveItemToFirebase(String name, String quantity, String notes, String category, String list) {
//        String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
        Item item = new Item(name, quantity, notes, category, list);
        DatabaseReference itemRef = mPantryDatabase.push();
        String keyId = itemRef.getKey();
        item.setId(keyId);
        itemRef.setValue(item);
    }
}
