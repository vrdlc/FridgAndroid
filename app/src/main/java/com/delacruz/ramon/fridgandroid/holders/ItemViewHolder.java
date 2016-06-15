package com.delacruz.ramon.fridgandroid.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.delacruz.ramon.fridgandroid.R;
import com.delacruz.ramon.fridgandroid.models.Item;
import com.delacruz.ramon.fridgandroid.ui.ItemDetailActivity;
import com.delacruz.ramon.fridgandroid.util.Utils;

import org.parceler.Parcels;

import java.sql.Date;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Ramon on 5/2/16.
 */
public class ItemViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.nameTextView) TextView mNameTextView;
    @Bind(R.id.quantityTextView) TextView mQuantityTextView;
    @Bind(R.id.notesTextView) TextView mNotesTextView;
    @Bind(R.id.timestampTextView) TextView mTimestampTextView;
//    @Bind(R.id.imageView) ImageView mImageView;


    private Context mContext;
    private ArrayList<Item> mItems = new ArrayList<>();

    public ItemViewHolder(View itemView, ArrayList<Item> items) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mContext = itemView.getContext();
        mItems = items;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = getLayoutPosition();
                Intent intent = new Intent(mContext, ItemDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("items", Parcels.wrap(mItems));
                mContext.startActivity(intent);
            }
        });
    }

    public void bindItem(Item item) {
        mNameTextView.setText(item.getItemName());
        mQuantityTextView.setText("x " + item.getItemQuantity());
        mNotesTextView.setText(item.getItemNotes());
        if (item.getTimestampLastChanged() != null) {
            Utils.SIMPLE_DATE_FORMAT.format(
                    new Date(item.getTimestampLastChangedLong()));
        } else {
            mTimestampTextView.setText("");
        }
    }
}
