package se.rickylagerkvist.whotune.utils;

import android.content.pm.PackageManager;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by rickylagerkvist on 2017-04-04.
 */

public class ConvertAndFormatHelpers {

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    public static boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            return packageManager.getApplicationInfo(packagename, 0).enabled;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static String convertMsToMinSecString(int ms){
        int totalSecs = ms / 1000;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }
}

