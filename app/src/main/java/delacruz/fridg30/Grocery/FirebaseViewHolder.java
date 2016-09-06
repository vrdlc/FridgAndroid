package delacruz.fridg30.Grocery;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import delacruz.fridg30.Constants;
import delacruz.fridg30.Models.Item;
import delacruz.fridg30.R;

/**
TODO
 Try moving DatabaseREference into its own method in the constructor
 */
public class FirebaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;
    public TextView mItemNameView;
    public Button mDeleteButton;

    private SharedPreferences mSharedPreferences;
    private static DatabaseReference mDatabase;
    private Item mItem;



    View mView;
    Context mContext;

    public FirebaseViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);

    }

    public void bindItem(Item item) {
        mItemNameView = (TextView) mView.findViewById(R.id.nameTextView);
        TextView quantityTextView = (TextView) mView.findViewById(R.id.quantityTextView);
        TextView notesTextView = (TextView) mView.findViewById(R.id.notesTextView);
        mDeleteButton = (Button) mView.findViewById(R.id.delete);
        mDeleteButton.setOnClickListener(this);

        mItem = item;

        mItemNameView.setText(item.getItemName());
        quantityTextView.setText("x " + item.getItemQuantity());
        notesTextView.setText("Notes: " + item.getItemNotes());

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.delete) {
            openDeleteDialog();
        } else {
            final ArrayList<Item> items = new ArrayList<>();
            DatabaseReference ref;
            if (mContext.getClass().getSimpleName().equals("GroceryActivity")) {
                Log.d("VH If Statement", "I'm working!");
                ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_LOCATION_GROCERY);
            } else {
                ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_LOCATION_PANTRY);
            }
            ref.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        items.add(snapshot.getValue(Item.class));
                    }

                    int itemPosition = getLayoutPosition();

                    Intent intent = new Intent(mContext, DetailActivity.class);
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

    private void openDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Remove Item From List");
        builder.setMessage("Are you sure you want to delete this item FOREVER?");
//        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteItemFromFirebase();
                Toast.makeText(mContext.getApplicationContext(), "Deleted forEVER", Toast.LENGTH_SHORT).show();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext.getApplicationContext(), "Phew! That was close!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    public void deleteItemFromFirebase() {
//        String userUid = mSharedPreferences.getString(Constants.KEY_UID, null);
        String id = mItem.getId();
        if (mContext.getClass().getSimpleName().equals("GroceryActivity")) {

            mDatabase = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_LOCATION_GROCERY);
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_LOCATION_PANTRY);
        }
        DatabaseReference itemRef = mDatabase.getRef();
        DatabaseReference finalItem = itemRef.child(id);
        finalItem.removeValue();
    }


}
