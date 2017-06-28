package com.bjain.pegasus.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bjain.pegasus.R;
import com.bjain.pegasus.pojo.category.CategoryPOJO;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sunil on 26-05-2017.
 */

public class Pref {

    private static final String PrefDB = "pegasus.txt";


    public static final String FCM_REGISTRATION_TOKEN = "fcm_registration_token";
    public static final String FEATURE_LIST_DATA = "feature_list_data";
    public static final String CURRENCY = "currency";


    public static void SetStringPref(Context context, String KEY, String Value) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY, Value);
        editor.commit();
    }

    public static String GetStringPref(Context context, String KEY, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        return sp.getString(KEY, defValue);
    }

    public static void SetBooleanPref(Context context, String KEY, boolean Value) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY, Value);
        editor.commit();
    }

    public static boolean GetBooleanPref(Context context, String KEY, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        return sp.getBoolean(KEY, defValue);
    }

    public static void clearSharedPreference(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    public static void SetCurrency(Context context, String currency) {
        SharedPreferences sp = context.getSharedPreferences("currency.txt", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("currency", currency);
        editor.commit();
    }


    public static String GetCurrency(Context context) {
        SharedPreferences sp = context.getSharedPreferences("currency.txt", MODE_PRIVATE);
        return sp.getString("currency", context.getResources().getString(R.string.Rs));
    }
    public static String GetCurrencyINR(Context context) {
        SharedPreferences sp = context.getSharedPreferences("currency.txt", MODE_PRIVATE);
        return sp.getString("currency", "INR");
    }

    public static <T> void SavePOJO(Context context, String KEY, T object) {
        SharedPreferences mPrefs = context.getSharedPreferences(Pref.PrefDB, MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(object);
        editor.putString(KEY, json);
        editor.commit();
    }
    public static final String TAG=Pref.class.getSimpleName();
    public static Object GetPOJO(Context context, String KEY, String type) {
        try {
            SharedPreferences mPrefs = context.getSharedPreferences(Pref.PrefDB, MODE_PRIVATE);
            Gson gson = new Gson();
            String json = mPrefs.getString(KEY, "");
            Log.d(TAG,"category response:-"+json);
            switch (type) {
                case StringUtils.CATEGORY_TYPE:
                    return gson.fromJson(json, CategoryPOJO.class);
            }
//            T obj = gson.fromJson(json, T.class);
//            return obj;
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
