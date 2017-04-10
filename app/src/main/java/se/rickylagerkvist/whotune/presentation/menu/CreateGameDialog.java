package se.rickylagerkvist.whotune.presentation.menu;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import se.rickylagerkvist.whotune.data.database.FireBaseRef;
import se.rickylagerkvist.whotune.data.model.Admin;
import se.rickylagerkvist.whotune.data.model.GameState;
import se.rickylagerkvist.whotune.presentation.main.MainActivity;
import se.rickylagerkvist.whotune.data.model.GuessOrAnswer;
import se.rickylagerkvist.whotune.data.model.Player;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.data.model.WhoTuneGame;
import se.rickylagerkvist.whotune.presentation.playersInGame.PlayersInGameFragment;
import se.rickylagerkvist.whotune.utils.SharedPrefUtils;

/**
 * Created by rickylagerkvist on 2017-04-04.
 */

public class CreateGameDialog extends DialogFragment {

    EditText editTextName;

    public static CreateGameDialog newInstance(){
        CreateGameDialog createGameDialog = new CreateGameDialog();
        Bundle bundle = new Bundle();
        createGameDialog.setArguments(bundle);
        return createGameDialog;
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

        builder.setView(rootView)
                .setTitle(R.string.create_game)
                .setNegativeButton(getActivity().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CreateGameDialog.this.getDialog().cancel();
                    }
                })
                .setPositiveButton(getActivity().getString(R.string.add_game), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addGameToFireBase();
                    }
                });
        return builder.create();
    }

    private void addGameToFireBase() {

        String name =  editTextName.getText().toString();

        if(!name.isEmpty()){

            // create fields for WhoTuneGame object
            String photoUrl = SharedPrefUtils.getPhotoUrl(getActivity());
            String displayName = SharedPrefUtils.getDisplayName(getActivity());
            String userUid = SharedPrefUtils.getUid(getActivity());
            Admin admin = new Admin(photoUrl, displayName, userUid);

            HashMap<String, Player> players = new HashMap<>();
            players.put(userUid, new Player(displayName, photoUrl, userUid));

            ArrayList<GuessOrAnswer> playList = new ArrayList<>();

            // save game to db
            String key = FireBaseRef.games.push().getKey();
            SharedPrefUtils.saveGameId(key, getActivity());
            FireBaseRef.games.child(key).setValue(new WhoTuneGame(
                    name,
                    admin,
                    new Date(),
                    players,
                    playList,
                    GameState.OPEN
            ));

            // bundle key send to PlayersInGameFragment
            Bundle bundle = new Bundle();
            bundle.putString("GAME_ID", key);

            CreateGameDialog.this.getDialog().cancel();
            ((MainActivity) getActivity()).changeFragment(PlayersInGameFragment.newInstance(), false, bundle);

        } else {
            Toast.makeText(getActivity(), "Enter a name", Toast.LENGTH_SHORT).show();
        }

    }
}