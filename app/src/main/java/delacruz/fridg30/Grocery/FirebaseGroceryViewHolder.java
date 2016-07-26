package delacruz.fridg30.Grocery;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

import delacruz.fridg30.Constants;
import delacruz.fridg30.Grocery.GroceryDetailActivity;
import delacruz.fridg30.Models.Item;
import delacruz.fridg30.R;

/**
 * Created by Ramon on 7/18/16.
 */
public class FirebaseGroceryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;
    public TextView mItemNameView;

    View mView;
    Context mContext;

    public FirebaseGroceryViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindItem(Item item) {
        mItemNameView= (TextView) mView.findViewById(R.id.nameTextView);
        TextView quantityTextView = (TextView) mView.findViewById(R.id.quantityTextView);
        TextView notesTextView = (TextView) mView.findViewById(R.id.notesTextView);

        mItemNameView.setText(item.getItemName());
        quantityTextView.setText("x " + item.getItemQuantity());
        notesTextView.setText("Notes: " + item.getItemNotes());
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Item> items = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_LOCATION_ITEM);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    items.add(snapshot.getValue(Item.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, GroceryDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("items", Parcels.wrap(items));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


}
