package com.example.ramon.fridgandroid.adapters;

import android.view.ViewGroup;

/**
 * Created by Ramon on 5/13/16.
 */
public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemValueChange(int position);
}
