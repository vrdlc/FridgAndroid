package com.delacruz.ramon.fridgandroid.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.delacruz.ramon.fridgandroid.R;
import com.delacruz.ramon.fridgandroid.holders.ItemViewHolder;
import com.delacruz.ramon.fridgandroid.models.Item;
import com.delacruz.ramon.fridgandroid.util.Constants;
import com.firebase.client.Firebase;

import java.util.ArrayList;

import butterknife.Bind;


//TODO
    //Add Delete and Edit functions
    //Rewrite Firebase code to use new Firebase
    //Change app bar text to tell user which list they are on
    //Redirect launch page to Everything List and add FAB to create dialog box with input options
    //Fix my ugly ass design
/**
 * Created by Ramon on 5/2/16.
 */
public class ItemListAdapter extends RecyclerView.Adapter<ItemViewHolder> implements View.OnClickListener {
    private ArrayList<Item> mItems = new ArrayList<>();
    private Context mContext;
    private SharedPreferences mSharedPreferences;

    @Bind(R.id.deleteButton) Button mDeleteButton;


    public ItemListAdapter(Context context, ArrayList<Item> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mDeleteButton.setOnClickListener(this);


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_item, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(view, mItems);
        return viewHolder;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteButton:
             deleteItemFromFirebase();
                Log.d("ItemListAdapter", "DId it work?");
                Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public void deleteItemFromFirebase() {

        String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
        Firebase savedItemRef = new Firebase(Constants.FIREBASE_SAVED_ITEM_URL).child(userUid);

        Item item = new Item();
        Firebase itemRef = savedItemRef.push();
        String keyId = itemRef.getKey();
        item.setId(keyId);
        itemRef.setValue("");
        Log.v("WHEEEE", item.getId());

    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bindItem(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
