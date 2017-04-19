package se.rickylagerkvist.whotune.presentation.showResults;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.data.database.ApiUtils;
import se.rickylagerkvist.whotune.data.database.SpotifyService;
import se.rickylagerkvist.whotune.data.model.SpotifyData.PlayList;
import se.rickylagerkvist.whotune.data.model.SpotifyData.Tracks;
import se.rickylagerkvist.whotune.presentation.menu.CreateGameDialog;
import se.rickylagerkvist.whotune.utils.SharedPrefUtils;

/**
 * Created by rickylagerkvist on 2017-04-19.
 */

public class PostPlayListDialog extends DialogFragment {

    EditText editTextName;
    Tracks tracks;

    public static PostPlayListDialog newInstance(String roundName){
        PostPlayListDialog postPlayListDialog = new PostPlayListDialog();
        Bundle bundle = new Bundle();
        bundle.putString("roundName", roundName);
        postPlayListDialog.setArguments(bundle);
        return postPlayListDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // open the soft keyboard automatically when dialog is opened
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_add_game, null);
        editTextName = (EditText) rootView.findViewById(R.id.et_create_game_name);

        // get bundle
        Bundle bundle = this.getArguments();
        editTextName.setText(bundle.getString("roundName"));
        tracks = new Gson().fromJson(bundle.getString("tracks"), Tracks.class);

        // click actions
        builder.setView(rootView)
                .setNegativeButton(getActivity().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PostPlayListDialog.this.getDialog().cancel();
                    }
                })
                .setPositiveButton(getActivity().getString(R.string.post_playlist), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!editTextName.getText().toString().isEmpty()){
                            postPlayList(getActivity().getApplicationContext(), editTextName.getText().toString(), tracks);
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Enter a name", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        return builder.create();
    }

    private void postPlayList(final Context context, String name, Tracks tracks) {

        SpotifyService spotifyService = ApiUtils.getSpotifyService();

        PlayList playList = new PlayList(name);

        spotifyService.postPlayList(SharedPrefUtils.getSpotifyProfileId(context), playList, "Bearer " + SharedPrefUtils.getAuthToken(context), "application/json").enqueue(new Callback<PlayList>() {
            @Override
            public void onResponse(Call<PlayList> call, Response<PlayList> response) {

                if(response.isSuccessful()){
                    Toast.makeText(context, "Playlist posted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PlayList> call, Throwable t) {
                Toast.makeText(context, "Failed to post playlist", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
