package com.example.ramon.fridgandroid.ui;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.models.Item;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PantryDetailFragment extends Fragment {
    private SharedPreferences mSharedPreferences;

    //WHEN I WANT TO ADD LINK FOR IMPLICIT INTENT, IMPLEMENT View.OnClickListener IN PUBLIC CLASS

    @Bind(R.id.detailItemNameTextView) TextView mNameTextView;
    @Bind(R.id.detailQuantityTextView) TextView mQuantityTextView;
    @Bind(R.id.detailNotesTextView) TextView mNotesTextView;

    private Item mItem;


    public PantryDetailFragment() {
        // Required empty public constructor
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
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pantry_detail, container, false);
        ButterKnife.bind(this, view);

        mNameTextView.setText(mItem.getItemName());
        mQuantityTextView.setText("x " + mItem.getItemQuantity());
        mNotesTextView.setText(mItem.getItemNotes());

        return view;
    }

//    @Override
//    public void onClick(View v) {
//        if (v == mSafeway) {
//            Intent webIntent = new Intent(Intent.ACTION_VIEW, WHAT DO I PUT HERE TO GET LINK???)
//            startActivity(webIntent);
//        }
//    }
}
