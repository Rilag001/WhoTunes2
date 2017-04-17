package se.rickylagerkvist.whotune.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import se.rickylagerkvist.whotune.MainActivity;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.presentation.showResults.ResultsFragment;

/**
 * Created by rickylagerkvist on 2017-04-07.
 */

public class DialogHelpers {

    public static void showInfoDialog(Context context){
        new AlertDialog.Builder(context)
                .setTitle(R.string.info_dialog_title)
                .setMessage(R.string.info_dialog_message)
                .setCancelable(false)
                .setPositiveButton(R.string.info_dialog_positive_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    public static void showSpotifyNotInstalledDialog(final Context context){
        new AlertDialog.Builder(context)
                .setTitle(R.string.spotify_needed)
                .setMessage(R.string.need_spotify_to_play)
                .setCancelable(false)
                .setPositiveButton(R.string.install_spotify, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.spotify.music")));
                        } catch (ActivityNotFoundException anfe) {
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.spotify.music")));
                        }
                    }
                })
                .show();
    }

    public static void showPlayerCoundNotBeInitialized(final Context context){
        new AlertDialog.Builder(context)
                .setTitle("Error with player")
                .setMessage("Make sure you got Spotify installed on this device and try again.")
                .setCancelable(false)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .show();
    }

    public static void showNavToResultsDialog(final Context context){
        new AlertDialog.Builder(context)
                .setTitle("End of playlist")
                .setMessage("Who picked what song? Ready to see the results?")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((MainActivity)context).changeFragment(ResultsFragment.newInstance(), false);
                    }
                })
                .show();
    }
}
