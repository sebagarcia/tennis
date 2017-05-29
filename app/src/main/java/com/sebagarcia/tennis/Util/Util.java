package com.sebagarcia.tennis.Util;

import android.content.SharedPreferences;

/**
 * Created by cristian on 5/29/17.
 */

public class Util {

    public static String getUserMailPref(SharedPreferences preferences){
        return preferences.getString("Email","");
    }
    public static String getUserPassPref(SharedPreferences preferences){
        return preferences.getString("Password","");
    }
}
