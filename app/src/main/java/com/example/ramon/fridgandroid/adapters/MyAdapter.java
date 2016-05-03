package com.example.ramon.fridgandroid.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.ramon.fridgandroid.ItemClickListener;
import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.holders.MyHolder;
import com.example.ramon.fridgandroid.models.Item;
import com.example.ramon.fridgandroid.ui.PantryListActivity;

import java.util.ArrayList;

/**
 * Created by Ramon on 4/30/16.
 */
public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    Context c;
    ArrayList<Item> items;

    public MyAdapter(Context ctx, ArrayList<Item> items) {

        //ASSIGN THEM LOCALLY
        this.c = ctx;
        this.items = items;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        //VIEW OBJECT FROM XML
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pantry_list, null);// activity_pantry_list WAS model.xml. DO I NEED THAT INSTEAD?

        //HOLDER
        MyHolder holder = new MyHolder(v);

        return holder;
    }

    //BIND DATA TO VIEWS
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.nameTextView.setText(items.get(position).getItemName());
        holder.quantityTextView.setText(items.get(position).getItemQuantity());
        holder.notesTextView.setText(items.get(position).getItemNotes());

        //HANDLE ITEMCLICKS
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //OPEN DETAIL ACTIVITY
                //PASS DATA

                //CREATE INTENT

                Intent intent = new Intent(c, PantryListActivity.class);

                //LOAD DATA
                intent.putExtra("ITEM_NAME", items.get(pos).getItemName());
                intent.putExtra("ITEM_QUANTITY", items.get(pos).getItemQuantity());
                intent.putExtra("ITEM_NOTES", items.get(pos).getItemNotes());

                //START ACTIVITY
                c.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
