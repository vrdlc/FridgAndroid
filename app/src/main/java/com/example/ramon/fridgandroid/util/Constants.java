package com.example.ramon.fridgandroid.util;

import com.example.ramon.fridgandroid.BuildConfig;

/**
 * Created by Ramon on 4/30/16.
 */
public class Constants {

    public static final String FIREBASE_URL = BuildConfig.FIREBASE_ROOT_URL;
    public static final String FIREBASE_LOCATION_ITEM = "savedItem";

    public static final String FIREBASE_SAVED_ITEM_URL = FIREBASE_URL + "/" + FIREBASE_LOCATION_ITEM;

    public static final String FIREBASE_ITEM_NAME = "item_name";
    public static final String FIREBASE_ITEM_QUANTITY = "item_quantity";
    public static final String FIREBASE_ITEM_NOTES = "item_notes";
    public static final String FIREBASE_URL_SAVED_ITEM = FIREBASE_URL + "/" + FIREBASE_LOCATION_ITEM;

    public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";

    public static final String FIREBASE_LOCATION_USERS = "users";
    public static final String FIREBASE_PROPERTY_EMAIL = "email";

    public static final String KEY_UID = "UID";
    public static final String KEY_USER_EMAIL = "email";
    public static final String FIREBASE_URL_USERS = FIREBASE_URL +"/" + FIREBASE_LOCATION_USERS;
}
