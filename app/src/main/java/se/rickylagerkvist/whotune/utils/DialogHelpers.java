package se.rickylagerkvist.whotune.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import se.rickylagerkvist.whotune.R;

/**
 * Created by rickylagerkvist on 2017-04-07.
 */

public class DialogHelpers {

    public static void infoDialog(Context context){
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
}
