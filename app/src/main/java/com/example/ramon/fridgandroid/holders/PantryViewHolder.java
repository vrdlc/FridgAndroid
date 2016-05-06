package com.example.ramon.fridgandroid.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.models.Item;
import com.example.ramon.fridgandroid.ui.ItemDetailActivity;
import com.example.ramon.fridgandroid.ui.PantryDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ramon on 5/6/16.
 */
public class PantryViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.nameTextView) TextView mNameTextView;
    @Bind(R.id.quantityTextView) TextView mQuantityTextView;
    @Bind(R.id.notesTextView) TextView mNotesTextView;

    private Context mContext;
    private ArrayList<Item> mItems = new ArrayList<>();

    public PantryViewHolder(View itemView, ArrayList<Item> items) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        mItems = items;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, PantryDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("items", Parcels.wrap(mItems));
                mContext.startActivity(intent);
            }
        });
    }

    public void bindItem(Item item) {
        mNameTextView.setText(item.getItemName());
        mQuantityTextView.setText(item.getItemQuantity());
        mNotesTextView.setText(item.getItemNotes());
    }
}
