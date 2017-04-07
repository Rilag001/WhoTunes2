package se.rickylagerkvist.whotune.utils;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by rickylagerkvist on 2017-04-07.
 */

public class SharedPrefUtils {

    // get
    public static String getPhotoUrl(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString("PHOTO_URL", "");
    }

    public static String getDisplayName(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString("DISPLAY_NAME", "");
    }

    public static String getUid(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString("USERUID", "");
    }

    // set
    public static void saveUid(String uid, Context context){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("USERUID", uid).apply();
    }

    public static void saveDisplayName(String name, Context context){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("DISPLAY_NAME", name).apply();
    }

    public static void savePhotoUrl(String photoUrl, Context context){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("PHOTO_URL", photoUrl).apply();
    }
}
