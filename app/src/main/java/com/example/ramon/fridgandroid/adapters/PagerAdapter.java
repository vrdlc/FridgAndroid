package com.example.ramon.fridgandroid.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ramon.fridgandroid.models.Item;
import com.example.ramon.fridgandroid.ui.ItemDetailFragment;

import java.util.ArrayList;

/**
 * Created by Ramon on 5/2/16.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Item> mItems;

    public PagerAdapter(FragmentManager fm, ArrayList<Item> items) {
        super(fm);
        mItems = items;
    }

    @Override
    public Fragment getItem(int position) {
        return ItemDetailFragment.newInstance(mItems.get(position));
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
