package com.example.ramon.fridgandroid.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ramon.fridgandroid.Constants;

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

    public Cursor get() {
        SQLiteDatabase db = this.getReadableDatabase();

        String str ="SELECT * FROM TABLE_NAME";
        Cursor cursor = db.rawQuery(str, null);
        cursor.moveToFirst();
        return cursor;
    }
}
