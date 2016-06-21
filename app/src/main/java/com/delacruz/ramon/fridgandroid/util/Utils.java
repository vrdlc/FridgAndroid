package com.delacruz.ramon.fridgandroid.util;

import android.content.Context;

import java.text.SimpleDateFormat;

/**
 * Created by Ramon on 5/6/16.
 */
public class Utils {

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private Context mContext = null;


    public Utils(Context con) {
        mContext = con;
    }
}
