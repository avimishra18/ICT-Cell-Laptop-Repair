package com.example.ictcelllaptoprepair;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    //Setter and getter for Rollnumber
    public static void setUserRollnumber(Context ctx, String rollnumber)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("rollnumber", rollnumber);
        editor.apply();
    }

    public static String getUserRollnumber(Context ctx)
    {
        return getSharedPreferences(ctx).getString("rollnumber", "");
    }


    //Setter and getter for Username
    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("username", userName);
        editor.apply();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString("username", "");
    }

    //Setter and getter for Number
    public static void setUserNumber(Context ctx, String number)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("number",number);
        editor.apply();
    }

    public static String getUserNumber(Context ctx)
    {
        return getSharedPreferences(ctx).getString("number", "");
    }

    //Setter and getter for Admin
    public static void setUserRole(Context ctx, String admin)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("admin", admin);
        editor.apply();
    }

    public static String getUserAdmin(Context ctx)
    {
        return getSharedPreferences(ctx).getString("username", "");
    }

    //Setter and getter for Password
    public static void setUserPassword(Context ctx, String pasword)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString("password", pasword);
        editor.apply();
    }

    public static String getUserPassword(Context ctx)
    {
        return getSharedPreferences(ctx).getString("password", "");
    }

}
