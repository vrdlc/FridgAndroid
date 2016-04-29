package com.example.ramon.fridgandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.ramon.fridgandroid.helpers.DatabaseHelper;

/**
 * Created by Ramon on 4/29/16.
 */
public class DBManager { //HERE IS WHERE ALL CRUD IS DEFINED

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String item, String quantity, String notes) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.ITEM, item);
        contentValue.put(DatabaseHelper.QUANTITY, quantity);
        contentValue.put(DatabaseHelper.NOTES, notes);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.ITEM, DatabaseHelper.QUANTITY, DatabaseHelper.NOTES };
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String item, String quantity, String notes) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.ITEM, item);
        contentValues.put(DatabaseHelper.QUANTITY, quantity);
        contentValues.put(DatabaseHelper.NOTES, notes);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }


}
