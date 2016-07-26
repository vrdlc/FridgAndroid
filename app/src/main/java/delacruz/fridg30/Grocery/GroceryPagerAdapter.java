package delacruz.fridg30.Grocery;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import delacruz.fridg30.Models.Item;
import delacruz.fridg30.Grocery.GroceryDetailFragment;

public class GroceryPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Item> mItems;
    private Context mContext;

    public GroceryPagerAdapter(Context context, FragmentManager fm, ArrayList<Item> items) {
        super(fm);
        mItems = items;
        mContext = context;

    }

    @Override
    public Fragment getItem(int position) {
        return GroceryDetailFragment.newInstance(mContext, mItems.get(position));
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mItems.get(position).getItemName();
    }
}
