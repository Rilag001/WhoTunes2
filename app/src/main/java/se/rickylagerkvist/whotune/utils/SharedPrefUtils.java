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

    public static String getGameId(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString("GAME_ID", "");
    }

    public static String getAuthToken(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString("AUTH_TOKEN", "");
    }

    public static String getSpotifyProfileId(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString("PROFILE_ID", "");
    }

    public static String getSpotifyProfilePic(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString("PROFILE_PIC_URL", "");
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

    public static void saveGameId(String gameId, Context context){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("GAME_ID", gameId).apply();
    }

    public static void saveAuthToken(String authToken, Context context){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("AUTH_TOKEN", authToken).apply();
    }

    public static void saveSpotifyProfileId(String profileId, Context context){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("PROFILE_ID", profileId).apply();
    }

    public static void saveSpotifyProfilePic(String profilePicUrl, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString("PROFILE_PIC_URL", profilePicUrl).apply();
    }
}
