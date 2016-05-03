package com.example.ramon.fridgandroid.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.models.Item;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 5/3/16.
 */
public class PantryDetailFragment extends Fragment {

    @Bind(R.id.pantryDetailNameTextView) TextView mPantryDetailNameTextView;
    @Bind(R.id.pantryDetailQuantityTextView) TextView mPantryDetailQuantityTextView;
    @Bind(R.id.pantryDetailNotesTextView) TextView mPantryDetailNotesTextView;
    @Bind(R.id.pantryUpdateButton) Button mPantryUpdateButton;

    private Item mItem;

    public PantryDetailFragment() {
        //Required Empty public constructor
    }

    public static PantryDetailFragment newInstance(Item item) {
        PantryDetailFragment pantryDetailFragment = new PantryDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("item", Parcels.wrap(item));
        pantryDetailFragment.setArguments(args);
        return pantryDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItem = Parcels.unwrap(getArguments().getParcelable("item"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pantry_detail, container, false);
        ButterKnife.bind(this, view);

        mPantryDetailNameTextView.setText(mItem.getItemName());
        mPantryDetailQuantityTextView.setText(mItem.getItemQuantity());
        mPantryDetailNotesTextView.setText(mItem.getItemNotes());

        return view;
    }

}
