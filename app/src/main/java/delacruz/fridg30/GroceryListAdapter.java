//package delacruz.fridg30;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.ArrayList;
//
//import delacruz.fridg30.Models.Item;
//
///**
// * Created by Ramon on 7/16/16.
// */
//public class GroceryListAdapter extends RecyclerView.Adapter<GroceryViewHolder> {
//    private ArrayList<Item> mItems = new ArrayList<>();
//    private Context mContext;
//
//    public GroceryListAdapter(Context context, ArrayList<Item> items) {
//        mContext = context;
//        mItems = items;
//    }
//
//    @Override
//    public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.universal_list_item, parent, false);
//        GroceryViewHolder viewHolder = new GroceryViewHolder(view, mItems);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(GroceryViewHolder holder, int position) {
//        holder.bindItem(mItems.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return mItems.size();
//    }
//}
//
