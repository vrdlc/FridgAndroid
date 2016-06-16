package com.delacruz.ramon.fridgandroid.ui;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.delacruz.ramon.fridgandroid.R;
import com.delacruz.ramon.fridgandroid.models.Item;
import com.delacruz.ramon.fridgandroid.util.Constants;
import com.delacruz.ramon.fridgandroid.util.Utils;
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
public class ItemDetailFragment extends Fragment {

    private SharedPreferences mSharedPreferences;

    @Bind(R.id.detailItemNameTextView) TextView mNameTextView;
    @Bind(R.id.detailQuantityTextView) TextView mQuantityTextView;
    @Bind(R.id.detailNotesTextView) TextView mNotesTextView;
    @Bind(R.id.detailTimestampTextView) TextView mTimestampTextView;
    @Bind(R.id.deleteButton) Button mDeleteButton;
    //@Bind(R.id.updateButton) Button mUpdateButton;

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
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);
        ButterKnife.bind(this, view);

        Firebase refListName = new Firebase(Constants.FIREBASE_SAVED_ITEM_URL).child(mItem.getId());
        refListName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    Item item = dataSnapshot.getValue(Item.class);

                if (item != null) {
                    mNameTextView.setText(mItem.getItemName());
                    mQuantityTextView.setText("x " + mItem.getItemQuantity());
                    mNotesTextView.setText(mItem.getItemNotes());
                    if (item.getTimestampLastChanged() != null) {
                        String time = Utils.SIMPLE_DATE_FORMAT.format(
                                new Date(item.getTimestampLastChangedLong()));
                        mTimestampTextView.setText(time);
                    } else {
                        mTimestampTextView.setText("N/A");
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return view;
    }

}
