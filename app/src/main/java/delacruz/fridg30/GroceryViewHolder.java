//package delacruz.fridg30;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//
//import org.parceler.Parcels;
//
//import java.util.ArrayList;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import delacruz.fridg30.Models.Item;
//
///**
// * Created by Ramon on 7/16/16.
// */
//public class GroceryViewHolder extends RecyclerView.ViewHolder {
//
//    @Bind(R.id.nameTextView) public TextView mNameTextView;
//    @Bind(R.id.quantityTextView) TextView mQuantityTextView;
//    @Bind(R.id.notesTextView) TextView mNotesTextView;
//
//    private Context mContext;
//    private ArrayList<Item> mItems = new ArrayList<>();
//
//    public GroceryViewHolder(View itemView, ArrayList<Item> items) {
//        super(itemView);
//        ButterKnife.bind(this, itemView);
//        mContext = itemView.getContext();
//        mItems = items;
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("ViewHolder", mItems + "");
//                int itemPosition = getLayoutPosition();
//                Intent intent = new Intent(mContext, GroceryDetailActivity.class);
//                intent.putExtra("position", itemPosition + "");
//                intent.putExtra("items", Parcels.wrap(mItems));
//                mContext.startActivity(intent);
//            }
//        });
//    }
//
//    public void bindItem(Item item) {
//        mNameTextView.setText(item.getItemName());
//        mQuantityTextView.setText("x " + item.getItemQuantity());
//        mNotesTextView.setText("Notes: " + item.getItemNotes());
//    }
//
//}
