package com.example.ramon.fridgandroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroceryActivity extends AppCompatActivity {


    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        expListView = (ExpandableListView) findViewById(R.id.groceryExpandList);
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

        listDataChild.put(listDataHeader.get(0), eggs);
        listDataChild.put(listDataHeader.get(1), chickenThighs);
        listDataChild.put(listDataHeader.get(2), groundBeef);

    }

    }

