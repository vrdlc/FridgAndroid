package com.delacruz.ramon.fridg.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.delacruz.ramon.fridg.R;
import com.delacruz.ramon.fridg.models.Item;
import com.delacruz.ramon.fridg.ui.GroceryDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ramon on 5/6/16.
 */
public class GroceryViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

    @Bind(R.id.nameTextView) public TextView mNameTextView;
    @Bind(R.id.quantityTextView) TextView mQuantityTextView;
    @Bind(R.id.notesTextView) TextView mNotesTextView;

    private Context mContext;
    private ArrayList<Item> mItems = new ArrayList<>();

    public GroceryViewHolder(View itemView, ArrayList<Item> items) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        mItems = items;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, GroceryDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("items", Parcels.wrap(mItems));
                mContext.startActivity(intent);
            }
        });
    }

    public void bindItem(Item item) {
        mNameTextView.setText(item.getItemName());
        mQuantityTextView.setText("x " + item.getItemQuantity());
        mNotesTextView.setText("Notes: " + item.getItemNotes());
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }
}
