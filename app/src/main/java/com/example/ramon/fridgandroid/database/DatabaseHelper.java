package com.example.ramon.fridgandroid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ramon.fridgandroid.Constants;
import com.example.ramon.fridgandroid.models.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramon on 4/30/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    //WHEN TABLE IS CREATED
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Constants.CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //UPGRADE TABLE
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + Constants.TABLE_NAME);
        onCreate(db);
    }

    public List<Item> get() {
        List<Item> items = new ArrayList<Item>();
        String query = "SELECT * FROM " + "table_name"; //MAYBE SOMETHING ELSE...

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setItemName(cursor.getString(0));
                item.setItemQuantity(cursor.getString(1));
                item.setItemNotes(cursor.getString(2));

                items.add(item);
            } while (cursor.moveToNext());
        }

        Log.v("Data", items.toString());

        return items;

    }
//
//    public Cursor get() {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        String str ="SELECT * FROM TABLE_NAME";
//        Cursor cursor = db.rawQuery(str, null);
//        cursor.moveToFirst();
//        return cursor;
//    }
}
