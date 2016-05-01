package com.example.ramon.fridgandroid.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.database.DatabaseAdapter;
import com.example.ramon.fridgandroid.database.DatabaseHelper;
import com.example.ramon.fridgandroid.models.Item;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    EditText nameEditText, quantityEditText, notesEditText;
    Button addToPantry;
    Button pantryButton;
    DatabaseAdapter dbadapter;
    List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        items = new ArrayList<Item>();
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        quantityEditText = (EditText) findViewById(R.id.quantityEditText);
        notesEditText = (EditText) findViewById(R.id.notesEditText);
        addToPantry = (Button) findViewById(R.id.addToPantry);
        pantryButton = (Button) findViewById(R.id.pantryButton);

        pantryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PantryActivity.class);
                startActivity(intent);
            }
        });

        addToPantry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEditText.getText().toString();
                String quantity = quantityEditText.getText().toString();
                String notes = notesEditText.getText().toString();

                if (name.equals("")) {
                    Toast.makeText(MainActivity.this, "Please fill the item name field", Toast.LENGTH_LONG).show();
                } else {
                    dbadapter = new DatabaseAdapter(MainActivity.this);
                    dbadapter.add(name, quantity, notes);
                }
                nameEditText.setText("");
                quantityEditText.setText("");
                notesEditText.setText("");

                Toast.makeText(MainActivity.this, "Saved to Pantry", Toast.LENGTH_LONG);
            }

        });
    }
}
//
//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    @Bind(R.id.pantryButton) Button mPantryButton;
//    @Bind(R.id.everythingButton) Button mEverythingButton;
//    @Bind (R.id.groceryButton) Button mGroceryButton;
//    @Bind (R.id.addToPantry) Button mAddToPantry;
//    @Bind (R.id.addToGrocery) Button mAddToGrocery;
//
//    @Bind (R.id.nameEditText) EditText mNameEditText;
//    @Bind (R.id.quantityEditText) EditText mQuantityEditText;
//    @Bind (R.id.notesEditText) EditText mNotesEditText;
//
//    final DatabaseAdapter db = new DatabaseAdapter(this);
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
//
//
//        mPantryButton.setOnClickListener(this);
//        mEverythingButton.setOnClickListener(this);
//        mGroceryButton.setOnClickListener(this);
//        mAddToPantry.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.pantryButton:
//                Intent intentPantry = new Intent(MainActivity.this, PantryActivity.class);
//                startActivity(intentPantry);
//                Log.v("I WANT TO SEE THE DB",  "please");
//                break;
//            case R.id.everythingButton:
//                Intent intentEverything = new Intent(MainActivity.this, EverythingActivity.class);
//                startActivity(intentEverything);
//                break;
//            case R.id.groceryButton:
//                Intent intentGrocery = new Intent(MainActivity.this, GroceryActivity.class);
//                startActivity(intentGrocery);
//                break;
//            case R.id.addToPantry:
//                String name = mNameEditText.getText().toString();
//                String quantity = mQuantityEditText.getText().toString();
//                String notes = mNotesEditText.getText().toString();
//
//                Log.v("this", "sucks");
//
//                //DO save AND retrieve GO HERE? I NEED THIS BUTTON TO SAVE DATA
//                db.openDB();
//
//                db.add(name, quantity, notes);
//                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
//
//                //HERE WE CAN PUT
//                //name.setText(""); ETC IF IT ALL WORKS
//
//                db.close();
//
//
//
//            default:
//                break;
//        }
//    }
//
//
//}
