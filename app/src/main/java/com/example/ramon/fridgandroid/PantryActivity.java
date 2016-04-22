package com.example.ramon.fridgandroid;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PantryActivity extends AppCompatActivity { //tutorial had this as "Activity". Does this matter?

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        //get the listview
        expListView = (ExpandableListView) findViewById(R.id.pantryExpandList);
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

        //adding child data

        List<String> cookies = new ArrayList<String>();
        cookies.add("3");
        cookies.add("One bag oatmeal, two bags choc chip");

        List<String> bread = new ArrayList<String>();
        bread.add("2");
        bread.add("One whole wheat, one sourdough");

        List<String> milk = new ArrayList<String>();
        milk.add("2");
        milk.add("one gallon whole, one gallon unsweetened soy");

        listDataChild.put(listDataHeader.get(0), cookies);
        listDataChild.put(listDataHeader.get(1), bread);
        listDataChild.put(listDataHeader.get(2), milk);

    }
}
