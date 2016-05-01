package com.example.ramon.fridgandroid.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.database.DatabaseAdapter;



public class PantryActivity extends AppCompatActivity {

    private TextView mNameTextView;
    private TextView mQuantityTextView;
    private TextView mNotesTextView;
    private Button mUpdateButton;
    private Button mDeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

//        rv = (RecyclerView) findViewById(R.id.pantryRecycler);//DO I NEED TO DO THIS HERE?

        //PASS DATA BETWEEN ACTIVITIES
        Intent intent = getIntent();

        final String itemName = intent.getExtras().getString("ITEM_NAME");
        final String itemQuantity = intent.getExtras().getString("ITEM_QUANTITY");
        final String itemNotes = intent.getExtras().getString("ITEM_NOTES");
        final int id = intent.getExtras().getInt("ID");

        mUpdateButton = (Button) findViewById(R.id.updateButton);
        mDeleteButton = (Button) findViewById(R.id.deleteButton);
        mNameTextView = (TextView) findViewById(R.id.nameTextView);
        mQuantityTextView = (TextView) findViewById(R.id.quantityTextView);
        mNotesTextView = (TextView) findViewById(R.id.notesTextView);

        String name = intent.getStringExtra("name");
        String quantity = intent.getStringExtra("quantity");
        String notes = intent.getStringExtra("notes");
        //ASSIGN DATA TO THOSE VIEWS
        mNameTextView.setText(name);
        mQuantityTextView.setText("x " + quantity);
        mNotesTextView.setText(notes);

        //UPDATE ITEM
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(id, mNameTextView.getText().toString(), mQuantityTextView.getText().toString(), mNotesTextView.getText().toString());
            }
        });

        //DELETE
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(id);
            }
        });
    }

    private void update(int id, String newName, String newQuantity, String newNotes) {
        DatabaseAdapter db = new DatabaseAdapter(this);
        db.openDB();
        long result = db.UPDATE(id, newName, newQuantity, newNotes);

        if (result > 0) {
            mNameTextView.setText(newName);
            mQuantityTextView.setText(newQuantity);
            mNotesTextView.setText(newNotes);
            Snackbar.make(mNameTextView, "Updated Successfully", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(mNameTextView, "Unable to Update", Snackbar.LENGTH_SHORT).show();
        }
        db.close();
    }

    //DELETE
    private void delete(int id) {
        DatabaseAdapter db = new DatabaseAdapter(this);
        db.openDB();
        long result = db.Delete(id);

        if (result > 0) {
            this.finish();
        } else {
            Snackbar.make(mNameTextView, "Unable to Update", Snackbar.LENGTH_SHORT).show();
        }
        db.close();

    }
}

