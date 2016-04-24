package com.example.ramon.fridgandroid;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PantryActivity extends AppCompatActivity { //tutorial had this as "Activity". Does this matter?
    //THESE ARE FOR THE EXPANDABLE LIST VIEW
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    private TextView mNameTextView;
    private TextView mQuantityTextView;
    private TextView mNotesTextView;

//    Intent intent = getIntent();
//    ArrayList<String> newPantryName = intent.getStringArrayListExtra("newPantryName");
//    ArrayList<String> newPantryQuantity = intent.getStringArrayListExtra("newPantryQuantity");
//    ArrayList<String> newPantryNotes = intent.getStringArrayListExtra("newPantryNotes");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

//------------- DEMOING ABILITY TO PASS DATA BETWEEN ACTIVITIES HERE -------------
        mNameTextView = (TextView) findViewById(R.id.nameTextView);
        mQuantityTextView = (TextView) findViewById(R.id.quantityTextView);
        mNotesTextView = (TextView) findViewById(R.id.notesTextView);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String quantity = intent.getStringExtra("quantity");
        String notes = intent.getStringExtra("notes");
        mNameTextView.setText(name);
        mQuantityTextView.setText("x " + quantity);
        mNotesTextView.setText(notes);


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

//    Intent intent = getIntent();
//
//    ArrayList<String> getNewPantryName = intent.getStringArrayListExtra("newPantryName");
//    ArrayList<String> getNewPantryQuantity = intent.getStringArrayListExtra("newPantryQuantity");
//    ArrayList<String> getNewPantryNotes = intent.getStringArrayListExtra("newPantryNotes");

//    ArrayAdapter nameAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, newPantryName);
//    ArrayAdapter quantityAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, newPantryQuantity);
//    ArrayAdapter notesAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, newPantryNotes);
//    listDataHeader.setAdapter(nameAdapter);
//    listDataChild.setAdapter(quantityAdapter);
//    listDataChild.setAdapter(notesAdapter);


}
