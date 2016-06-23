package com.delacruz.ramon.fridgandroid.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.delacruz.ramon.fridgandroid.models.Item;
import com.delacruz.ramon.fridgandroid.ui.PantryDetailFragment;

import java.util.ArrayList;

/**
 * Created by Ramon on 6/18/16.
 */
public class PantryPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Item> mItems;
    private Context mContext;

    public PantryPagerAdapter(Context context, FragmentManager fm, ArrayList<Item> items) {
        super(fm);
        mItems = items;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return PantryDetailFragment.newInstance(mContext, mItems.get(position));
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
