package com.example.ramon.fridgandroid.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ramon on 4/29/16.
 */
public class ExampleDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "PantryList.db";
    public static final int DATABASE_VERSION = 1;
    public static final String PANTRY_TABLE_NAME = "pantry"
    public static final String PANTRY_COLUMN_ID = "_id";
    public static final String PANTRY_COLUMN_ITEM = "item";
    public static final int PANTRY_COLUMN_QUANTITY = "quantity";
    public static final String PANTRY_COLUMN_NOTES = "notes";

    public ExampleDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE" + PANTRY_TABLE_NAME + "(" +
        PANTRY_COLUMN_ID + " INTEGER PRIMARY KEY, " +
        PANTRY_COLUMN_ITEM + " TEXT, " +
        PANTRY_COLUMN_QUANTITY + " INTEGER, " +
        PANTRY_COLUMN_NOTES + " TEXT,):
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + PANTRY_TABLE_NAME);
        onCreate(db);
    }

    public boolean updatePantry(Integer id, String item, int quantity, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PANTRY_COLUMN_ITEM, item);
        contentValues.put(PANTRY_COLUMN_QUANTITY, quantity);
        contentValues.put(PANTRY_COLUMN_NOTES, notes);
        db.update(PANTRY_TABLE_NAME, contentValues, PANTRY_COLUMN_ID + " = ? ", new String[] {Integer.toString(id)});
        return true;
    }

    public Cursor getPantry(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + PANTRY_TABLE_NAME + " WHERE " + PANTRY_COLUMN_ID + "=?", new String [] { Integer.toString(id) } );
        return res;
    }

    public Cursor getAllPantry() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + PANTRY_TABLE_NAME, null);
        return res;
    }

    public Integer deletePantry(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PANTRY_TABLE_NAME, PANTRY_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) } );
    }


}