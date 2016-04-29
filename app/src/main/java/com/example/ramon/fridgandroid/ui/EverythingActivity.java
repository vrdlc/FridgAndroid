package com.example.ramon.fridgandroid.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.ramon.fridgandroid.adapters.ExpandableListAdapter;
import com.example.ramon.fridgandroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EverythingActivity extends AppCompatActivity {


    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_everything);

        expListView = (ExpandableListView) findViewById(R.id.everythingExpandList);
        //preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        //setting list adapater
        expListView.setAdapter(listAdapter);
    }

    //preparing the list data

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        //adding child data
        listDataHeader.add("Cookies");
        listDataHeader.add("Bread");
        listDataHeader.add("Milk");
        listDataHeader.add("Eggs");
        listDataHeader.add("Chicken Thighs");
        listDataHeader.add("Ground Beef");

        //adding child data
        List<String> eggs = new ArrayList<String>();
        eggs.add("18");
        eggs.add("Need at least 12 for week's breakfasts");

        List<String> chickenThighs = new ArrayList<String>();
        chickenThighs.add("2");
        chickenThighs.add("One whole wheat, one sourdough");

        List<String> groundBeef = new ArrayList<String>();
        groundBeef.add("1");
        groundBeef.add("one lb, lean");

        List<String> cookies = new ArrayList<String>();
        cookies.add("3");
        cookies.add("One bag oatmeal, two bags choc chip");

        List<String> bread = new ArrayList<String>();
        bread.add("2");
        bread.add("One whole wheat, one sourdough");

        List<String> milk = new ArrayList<String>();
        milk.add("2");
        milk.add("one gallon whole, one gallon unsweetened soy");

        listDataChild.put(listDataHeader.get(0), eggs);
        listDataChild.put(listDataHeader.get(1), chickenThighs);
        listDataChild.put(listDataHeader.get(2), groundBeef);
        listDataChild.put(listDataHeader.get(3), cookies);
        listDataChild.put(listDataHeader.get(4), bread);
        listDataChild.put(listDataHeader.get(5), milk);
    }
}