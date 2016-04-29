package com.example.ramon.fridgandroid.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ramon.fridgandroid.PantryData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramon on 4/29/16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";
    // Database Info
    private static final String DATABASE_PANTRY = "PantryDatabase";
    private static final int DATABASE_VERSION = 1;

    //Table Names
    private static final String TABLE_PANTRYDETAIL = "pantrydetail";


    //userdetail Table Columns
    private static final String _ID = "_id";
    private static final String ITEM = "item";
    private static final String QUANTITY = "quantity";
    private static final String NOTES = "notes";

    private static DBHelper mDBHelper;

//    public DBHelper(Context context, String item, SQLiteDatabase.CursorFactory factory, int version {
//        super(context, item, factory, version);
//    }//IS THIS THE CONSTRUCTOR TO UPDATE?

    public static synchronized DBHelper getInstance (Context context) {
        if (mDBHelper == null) {
            mDBHelper = new DBHelper(context.getApplicationContext());
        }
        return mDBHelper;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_PANTRY, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_PANTRYDETAIL_TABLE = "CREATE TABLE " + TABLE_PANTRYDETAIL + "(" +
                _ID + " TEXT, " + //SHOULD THIS BE INTEGER?
                ITEM + " TEXT, " +
                QUANTITY + " TEXT, " +
                NOTES + " TEXT" +
                ")";
        db.execSQL(CREATE_PANTRYDETAIL_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRYDETAIL);

            onCreate(db);
        }

    }

    public void insertPantryDetail(PantryData pantryData) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();
            values.put(ITEM, pantryData.item);
            values.put(QUANTITY, pantryData.quantity);
            values.put(NOTES, pantryData.notes);

            db.insertOrThrow(TABLE_PANTRYDETAIL, null, values);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("THIS", "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public List<PantryData> getAllUser() {
        List<PantryData> pantrydetails = new ArrayList<>();

        String PANTRY_DETAIL_SELECT_QUERY = "SELECT * FROM " + TABLE_PANTRYDETAIL;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(PANTRY_DETAIL_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    PantryData pantryData = new PantryData();
                    pantryData.item = cursor.getString(cursor.getColumnIndex(ITEM));
                    pantryData.quantity = cursor.getString(cursor.getColumnIndex(QUANTITY));
                    pantryData.notes = cursor.getString(cursor.getColumnIndex(NOTES));

                    pantrydetails.add(pantryData);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("THAT", "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return pantrydetails;
    }

    void deleteRow(String item) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.beginTransaction();;
            db.execSQL("delete from " + TABLE_PANTRYDETAIL + " where name = '" + "'");
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.d("DELETE", "Error while trying to delete items from database");
        } finally {
            db.endTransaction();
        }
    }

}
