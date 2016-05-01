package com.example.ramon.fridgandroid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.ramon.fridgandroid.Constants;

/**
 * Created by Ramon on 4/30/16.
 */
public class DatabaseAdapter {

    Context c;
    SQLiteDatabase db;
    DatabaseHelper helper;

    public DatabaseAdapter(Context ctx) {
        this.c = ctx;
        helper = new DatabaseHelper(c);
    }

    //OPEN DB
    public DatabaseAdapter openDB() {
        try {
            db = helper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    //CLOSE
    public void close() {
        try {
            helper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //INSERT DATA TO TB
    public long add(String item_name, String item_quantity, String item_notes) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(Constants.ITEM_NAME, item_name);
            cv.put(Constants.ITEM_QUANTITY, item_quantity);
            cv.put(Constants.ITEM_NOTES, item_notes);

            return db.insert(Constants.TABLE_NAME, Constants.ROW_ID, cv);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }



    //RETRIEVE ALL ITEMS
    public Cursor getAllItems() {
        String[] columns = { Constants.ROW_ID, Constants.ITEM_NAME, Constants.ITEM_QUANTITY, Constants.ITEM_NOTES };
        return db.query(Constants.TABLE_NAME, columns, null, null, null, null, null);
    }

    //UPDATE
    public long UPDATE(int id, String item_name, String item_quantity, String item_notes) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(Constants.ITEM_NAME, item_name);
            cv.put(Constants.ITEM_QUANTITY, item_quantity);
            cv.put(Constants.ITEM_NOTES, item_notes);

            return db.update(Constants.TABLE_NAME, cv, Constants.ROW_ID + "=?", new String[] { String.valueOf(id)});
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    //DELETE
    public long Delete(int id) {
        try {
            return db.delete(Constants.TABLE_NAME, Constants.ROW_ID + "=?", new String[] { String.valueOf(id)});
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
