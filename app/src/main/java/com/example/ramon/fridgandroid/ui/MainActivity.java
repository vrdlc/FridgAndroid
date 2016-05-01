package com.example.ramon.fridgandroid.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ramon.fridgandroid.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.pantryButton) Button mPantryButton;
    @Bind(R.id.everythingButton) Button mEverythingButton;
    @Bind (R.id.groceryButton) Button mGroceryButton;
    @Bind (R.id.addToPantry) Button mAddToPantry;
    @Bind (R.id.addToGrocery) Button mAddToGrocery;

    @Bind (R.id.nameEditText) EditText mNameEditText;
    @Bind (R.id.quantityEditText) EditText mQuantityEditText;
    @Bind (R.id.notesEditText) EditText mNotesEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPantryButton.setOnClickListener(this);
        mEverythingButton.setOnClickListener(this);
        mGroceryButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pantryButton:
                Intent intentPantry = new Intent(MainActivity.this, PantryActivity.class);
                startActivity(intentPantry);
                break;
            case R.id.everythingButton:
                Intent intentEverything = new Intent(MainActivity.this, EverythingActivity.class);
                startActivity(intentEverything);
                break;
            case R.id.groceryButton:
                Intent intentGrocery = new Intent(MainActivity.this, GroceryActivity.class);
                startActivity(intentGrocery);
                break;
            case R.id.addToPantry:
                String name = mNameEditText.getText().toString();
                String quantity = mQuantityEditText.getText().toString();
                String notes = mNotesEditText.getText().toString();

                //DO save AND retrieve GO HERE? I NEED THIS BUTTON TO SAVE DATA

            default:
                break;
        }
    }


}
