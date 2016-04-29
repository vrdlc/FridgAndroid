package com.example.ramon.fridgandroid.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ramon on 4/29/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //TABLE NAME
    public static final String TABLE_NAME = "PANTRY";

    //TABLE COLUMNS
    public static final String _ID = "_id";
    public static final String ITEM = "item";
    public static final String QUANTITY = "quantity";
    public static final String NOTES = "notes";

    //DATABASE INFO
    static final String DB_NAME = "FRIDGANDROID.DB";

    //DATABASE VERSION
    static final int DB_VERSION = 1;

    //CREATING A TABLE QUERY
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM + "TEXT " + QUANTITY + "TEXT NOT NULL, " + NOTES + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
