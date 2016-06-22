package com.delacruz.ramon.fridgandroid.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.delacruz.ramon.fridgandroid.R;
import com.delacruz.ramon.fridgandroid.models.Item;
import com.delacruz.ramon.fridgandroid.util.Constants;
import com.firebase.client.Firebase;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PantryDetailFragment extends Fragment implements View.OnClickListener {
    private SharedPreferences mSharedPreferences;
    private static Context mContext;
    private Item mItem;

    @Bind(R.id.detailItemNameTextView) TextView mNameTextView;
    @Bind(R.id.detailQuantityTextView) TextView mQuantityTextView;
    @Bind(R.id.detailNotesTextView) TextView mNotesTextView;
    @Bind(R.id.updateFab) FloatingActionButton mUpdateFab;
    @Bind(R.id.deleteFab) FloatingActionButton mDeleteFab;



    public PantryDetailFragment() {
        // Required empty public constructor
    }

    public static PantryDetailFragment newInstance(Context context, Item item) {
        PantryDetailFragment pantryDetailFragment = new PantryDetailFragment();
        Bundle args = new Bundle();
        mContext = context;
        args.putParcelable("item", Parcels.wrap(item));
        pantryDetailFragment.setArguments(args);
        return pantryDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItem = Parcels.unwrap(getArguments().getParcelable("item"));
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pantry_detail, container, false);
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
                break;
            case R.id.deleteFab:
//                openDeleteDialog();
                deleteItemFromFirebase(); // THIS ONLY WORKS WITHOUT DIALOG WINDOW BUT THE EXACT SAME THING WORKS IN GROCERYDETAILFRAGMENT
                //ERROR:  java.lang.NullPointerException: Attempt to invoke virtual method 'android.content.res.Resources$Theme android.content.Context.getTheme()' on a null object reference
                break;
        }
    }

    private void openDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);//<--- ERROR POINTS HERE
        builder.setTitle("Remove Item From List");
        builder.setMessage("Are you sure you want to delete this item FOREVER?");
        AlertDialog alertDialog = builder.create();

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
        Firebase savedItemRef = new Firebase(Constants.FIREBASE_SAVED_ITEM_URL).child(userUid);
        Firebase finalItem = savedItemRef.child(id);
        Intent intent = new Intent(getActivity(), GroceryActivity.class);
        getActivity().startActivity(intent);
        finalItem.removeValue();
    }
}
