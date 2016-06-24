package com.delacruz.ramon.fridg.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.delacruz.ramon.fridg.R;
import com.delacruz.ramon.fridg.adapters.GroceryPagerAdapter;
import com.delacruz.ramon.fridg.models.Item;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GroceryDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private GroceryPagerAdapter adapterViewPager;
    ArrayList<Item> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_detail);
        ButterKnife.bind(this);

        mItems = Parcels.unwrap(getIntent().getParcelableExtra("items"));
        int startingPosition = Integer.parseInt(getIntent().getStringExtra("position"));
        adapterViewPager = new GroceryPagerAdapter(this, getSupportFragmentManager(), mItems);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}
