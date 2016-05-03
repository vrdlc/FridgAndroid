package com.example.ramon.fridgandroid.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ramon.fridgandroid.ItemClickListener;
import com.example.ramon.fridgandroid.R;

/**
 * Created by Ramon on 4/30/16.
 */
public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView nameTextView, quantityTextView, notesTextView;
    ItemClickListener itemClickListener;

    public MyHolder(View itemView) {
        super(itemView);

        //ASSIGN

        nameTextView = (TextView) itemView.findViewById(R.id.itemNameTextView);
        quantityTextView = (TextView) itemView.findViewById(R.id.itemQuantityTextView);
        notesTextView = (TextView) itemView.findViewById(R.id.itemNotesTextView);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic) {
        this.itemClickListener = ic;
    }
}
