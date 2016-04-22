package com.example.ramon.fridgandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner spinner = (Spinner) findViewById(R.id.listSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.list_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinner_list_value = spinner.getSelectedItem().toString();
                if (spinner_list_value.equals("Check Pantry")) {
                    Intent intent = new Intent(MainActivity.this, PantryActivity.class);
                    startActivity(intent);
                } else if (spinner_list_value.equals("Check Grocery List")) {
                    Intent intent = new Intent(MainActivity.this, GroceryActivity.class);
                    startActivity(intent);
                } else if (spinner_list_value.equals("Check Everything")) {
                    Intent intent = new Intent(MainActivity.this, EverythingActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
