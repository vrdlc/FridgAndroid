package com.example.ramon.fridgandroid.ui;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ramon.fridgandroid.R;
import com.example.ramon.fridgandroid.database.DatabaseAdapter;
import com.example.ramon.fridgandroid.database.DatabaseHelper;


public class PantryActivity extends AppCompatActivity {

    private TextView mNameTextView;
    private TextView mQuantityTextView;
    private TextView mNotesTextView;
//    private Button mUpdateButton;
//    private Button mDeleteButton;

    String name, quantity, notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        //PASS DATA BETWEEN ACTIVITIES
//        Intent intent = getIntent();
//
//        final String itemName = intent.getExtras().getString("ITEM_NAME");
//        final String itemQuantity = intent.getExtras().getString("ITEM_QUANTITY");
//        final String itemNotes = intent.getExtras().getString("ITEM_NOTES");
//        final int id = intent.getExtras().getInt("ID");

//        mUpdateButton = (Button) findViewById(R.id.updateButton);
//        mDeleteButton = (Button) findViewById(R.id.deleteButton);
        mNameTextView = (TextView) findViewById(R.id.nameTextView);
        mQuantityTextView = (TextView) findViewById(R.id.quantityTextView);
        mNotesTextView = (TextView) findViewById(R.id.notesTextView);

        DatabaseHelper db = new DatabaseHelper(this);
        Cursor values = db.get();
        values.moveToFirst();
        while (values.isAfterLast() == false) {
            name = values.getString(1);
            quantity = values.getString(2);
            notes = values.getString(3);

            values.moveToNext();
        }

        mNameTextView.setText("" + name);
        mQuantityTextView.setText("" + quantity);
        mNotesTextView.setText("" + notes);


//        String name = intent.getStringExtra("name");
//        String quantity = intent.getStringExtra("quantity");
//        String notes = intent.getStringExtra("notes");
        //ASSIGN DATA TO THOSE VIEWS
//        mNameTextView.setText(itemName);
//        mQuantityTextView.setText("x " + itemQuantity);
//        mNotesTextView.setText(itemNotes);

//        //UPDATE ITEM
//        mUpdateButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                update(id, mNameTextView.getText().toString(), mQuantityTextView.getText().toString(), mNotesTextView.getText().toString());
//            }
//        });
//
//        //DELETE
//        mDeleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                delete(id);
//            }
//        });
//    }
//
//    private void update(int id, String newName, String newQuantity, String newNotes) {
//        DatabaseAdapter db = new DatabaseAdapter(this);
//        db.openDB();
//        long result = db.UPDATE(id, newName, newQuantity, newNotes);
//
//        if (result > 0) {
//            mNameTextView.setText(newName);
//            mQuantityTextView.setText(newQuantity);
//            mNotesTextView.setText(newNotes);
//            Snackbar.make(mNameTextView, "Updated Successfully", Snackbar.LENGTH_SHORT).show();
//        } else {
//            Snackbar.make(mNameTextView, "Unable to Update", Snackbar.LENGTH_SHORT).show();
//        }
//        db.close();
//    }
//
//    //DELETE
//    private void delete(int id) {
//        DatabaseAdapter db = new DatabaseAdapter(this);
//        db.openDB();
//        long result = db.Delete(id);
//
//        if (result > 0) {
//            this.finish();
//        } else {
//            Snackbar.make(mNameTextView, "Unable to Update", Snackbar.LENGTH_SHORT).show();
//        }
//        db.close();
//
//    }


    }
}