package com.example.ramon.fridgandroid;

/**
 * Created by Ramon on 4/30/16.
 */
public class Constants {

    //COLUMNS
    public static final String ROW_ID = "row_id";
    public static final String ITEM_NAME = "item_name";
    public static final String ITEM_QUANTITY = "item_quantity";
    public static final String ITEM_NOTES = "item_notes";

    //DATABASE PROPERTIES
    public static final String DATABASE_NAME = "database_name";
    public static final String TABLE_NAME = "table_name";
    public static final int DATABASE_VERSION = '1';

    //CREATE TABLE

    public static final String CREATE_TABLE = "CREATE TABLE table_name(id INTEGER PRIMARY KEY AUTOINCREMENT," + "item_name TEXT NOT NULL, item_quantity TEXT NOT NULL, item_notes TEXT NOT NULL);";

    public static final String FIREBASE_URL = BuildConfig.FIREBASE_ROOT_URL;
    public static final String FIREBASE_LOCATION_ITEM = "savedItem";
    public static final String FIREBASE_ITEM_NAME = "item_name";
    public static final String FIREBASE_ITEM_QUANTITY = "item_quantity";
    public static final String FIREBASE_ITEM_NOTES = "item_notes";
    public static final String FIREBASE_URL_SAVED_ITEM = FIREBASE_URL + "/" + FIREBASE_LOCATION_ITEM;
}
