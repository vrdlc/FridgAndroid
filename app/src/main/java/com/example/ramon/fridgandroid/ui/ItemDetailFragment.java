package com.example.ramon.fridgandroid.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.models.Item;
import com.example.ramon.fridgandroid.util.Constants;
import com.example.ramon.fridgandroid.util.Utils;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.parceler.Parcels;

import java.sql.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemDetailFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.detailItemNameTextView) TextView mNameTextView;
    @Bind(R.id.detailQuantityTextView) TextView mQuantityTextView;
    @Bind(R.id.detailNotesTextView) TextView mNotesTextView;
    @Bind(R.id.detailTimestampTextView) TextView mTimestampTextView;
    @Bind(R.id.updateButton) Button mUpdateButton;

    private Item mItem;


    public ItemDetailFragment() {
        // Required empty public constructor
    }

    public static ItemDetailFragment newInstance(Item item) {
        ItemDetailFragment itemDetailFragment = new ItemDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("item", Parcels.wrap(item));
        itemDetailFragment.setArguments(args);
        return itemDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItem = Parcels.unwrap(getArguments().getParcelable("item"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);
        ButterKnife.bind(this, view);

        Firebase refListName = new Firebase(Constants.FIREBASE_URL).child("");

        refListName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Item item = dataSnapshot.getValue(Item.class);

                if (item != null) {
                    mNameTextView.setText(mItem.getItemName());
                    mQuantityTextView.setText("x " + mItem.getItemQuantity());
                    mNotesTextView.setText(mItem.getItemNotes());
                    if (item.getTimestampLastChanged() != null) {
                        Utils.SIMPLE_DATE_FORMAT.format(
                                new Date(item.getTimestampLastChangedLong()));
                    } else {
                        mTimestampTextView.setText("");
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    @Override
    public void onClick(View view) {
        if (view == mUpdateButton) {

        }
    }

        return view;
    }
}
