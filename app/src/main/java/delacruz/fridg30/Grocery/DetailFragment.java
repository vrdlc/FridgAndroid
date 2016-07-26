package delacruz.fridg30.Grocery;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import delacruz.fridg30.Constants;
import delacruz.fridg30.Models.Item;
import delacruz.fridg30.R;

/**
 * Created by Ramon on 7/16/16.
 */
public class DetailFragment extends Fragment implements View.OnClickListener {

    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;

    private SharedPreferences mSharedPreferences;
    private static Context mContext;
    private Item mItem;
    private static DatabaseReference mGroceryDatabase;


    @Bind(R.id.detailItemNameTextView) TextView mNameTextView;
    @Bind(R.id.detailQuantityTextView) TextView mQuantityTextView;
    @Bind(R.id.detailNotesTextView) TextView mNotesTextView;
    @Bind(R.id.updateFab) FloatingActionButton mUpdateFab;
    @Bind(R.id.deleteFab) FloatingActionButton mDeleteFab;

    public static DetailFragment newInstance(Context context, Item item) {
        mContext = context;
        DetailFragment groceryDetailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("item", Parcels.wrap(item));
        groceryDetailFragment.setArguments(args);
        return groceryDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItem = Parcels.unwrap(getArguments().getParcelable("item"));
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_universal_detail, container, false);
        ButterKnife.bind(this, view);

        mNameTextView.setText(mItem.getItemName());
        mQuantityTextView.setText("x " + mItem.getItemQuantity());
        mNotesTextView.setText("Notes: " + mItem.getItemNotes());

        mUpdateFab.setOnClickListener(this);
        mDeleteFab.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.updateFab:
//                openUpdateDialog();
                //UPDATE ONLY WORKS ON EACH NEW ITEM ONE OR TWO TIMES, THEN I GET ERROR "java.lang.NullPointerException: Can't pass null for argument 'pathString' in child()
                break;
            case R.id.deleteFab:
                openDeleteDialog();
                break;
        }
    }

    private void openUpdateDialog() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View subView = inflater.inflate(R.layout.fragment_save_item, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Update Item");
        builder.setMessage("Enter Item Info, Select List, and Click 'Okay'");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        Item item = mItem;

        final EditText subEditText = (EditText) subView.findViewById(R.id.nameEditText);
        final EditText subEditQuantity = (EditText) subView.findViewById(R.id.quantityEditText);
        final EditText subEditNotes = (EditText) subView.findViewById(R.id.notesEditText);
        final Spinner mSpinner = (Spinner) subView.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.spinner_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        subEditText.setText(item.getItemName());
        subEditQuantity.setText(item.getItemQuantity());
        subEditNotes.setText(item.getItemNotes());


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
                updateItemInFirebase(name, quantity, notes, list);

                Toast.makeText(mContext.getApplicationContext(), name + " updated", Toast.LENGTH_SHORT).show();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext.getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

    public void updateItemInFirebase(String name, String quantity, String notes, String list) {
        String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
        String id = mItem.getId();
        DatabaseReference savedItemRef = mGroceryDatabase;
        Item item = new Item(name, quantity, notes, list);

//OLD CODE DOES NOT WORK, MAYBE NEEDS VALUE EVENT LISTENER?
        DatabaseReference updatedItem = savedItemRef.child(id); //UPDATE ERROR POINTS HERE <------
        Log.d("saved item", savedItemRef + "");
        Intent intent = new Intent(getActivity(), GroceryActivity.class);
        getActivity().startActivity(intent);
        updatedItem.setValue(item);
    }

    private void openDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Remove Item From List");
        builder.setMessage("Are you sure you want to delete this item FOREVER?");
//        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteItemFromFirebase();
                Toast.makeText(mContext.getApplicationContext(), "Deleted forEVER", Toast.LENGTH_SHORT).show();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext.getApplicationContext(), "Phew! That was close!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    public void deleteItemFromFirebase() {
        String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
        String id = mItem.getId();

        mGroceryDatabase = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_LOCATION_ITEM);

        DatabaseReference itemRef = mGroceryDatabase.getRef();
        Log.d("DetailFragment", "Grocery DB " + mGroceryDatabase);

        DatabaseReference finalItem = itemRef.child(id);
        Intent intent = new Intent(getActivity(), GroceryActivity.class);
        getActivity().startActivity(intent);
        finalItem.removeValue();
    }
}
