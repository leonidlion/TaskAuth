package com.boost.leodev.socialslogin;


import android.content.Context;
import android.content.SharedPreferences;

import com.boost.leodev.socialslogin.ui.activities.MainActivity;

public class Utils {
    private static SharedPreferences prefs;

    public static SharedPreferences getPrefs(Context context){
        return context.getSharedPreferences(
                MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    public static void setUserToken(Context context, String userToken){
        if (context == null) return;
        prefs = getPrefs(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.KEY_USER_TOKEN, userToken);
        editor.apply();
    }

    public static void setUserName(Context context, String userName){
        if (context == null) return;
        prefs = getPrefs(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.KEY_USER_NAME, userName);
        editor.apply();
    }

    public static String getUserToken(Context context){
        prefs = getPrefs(context);
        return prefs.getString(Constants.KEY_USER_TOKEN, "");
    }

    public static String getUserName(Context context){
        prefs = getPrefs(context);
        return prefs.getString(Constants.KEY_USER_NAME, "");
    }

}
