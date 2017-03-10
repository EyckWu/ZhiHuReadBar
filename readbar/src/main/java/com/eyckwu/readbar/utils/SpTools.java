package com.eyckwu.readbar.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Eyck on 2017/2/27.
 */

public class SpTools {

    public static void putString(Context context,String key,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("READBAR",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key,value).commit();
    }

    public static String getString(Context context,String key,String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences("READBAR",Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,defaultValue);
    }

}
