package se.rickylagerkvist.whotune.menu;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

import se.rickylagerkvist.whotune.data.Admin;
import se.rickylagerkvist.whotune.data.GameState;
import se.rickylagerkvist.whotune.MainActivity.Main2Activity;
import se.rickylagerkvist.whotune.data.Player;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.data.WhoTuneGame;
import se.rickylagerkvist.whotune.playersInGame.PlayersInGameFragment;

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
                .setTitle("Create game")
                .setNegativeButton(getActivity().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CreateGameDialog.this.getDialog().cancel();
                    }
                })
                .setPositiveButton(getActivity().getString(R.string.add_game), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addGameToFirebase();
                    }
                });
        return builder.create();
    }

    private void addGameToFirebase() {

        String name =  editTextName.getText().toString();

        if(!name.isEmpty()){

            String photoUrl = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("PHOTO_URL", "");
            String displayName = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("DISPLAY_NAME", "");
            String userUid = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("USERUID","");

            Admin admin = new Admin(photoUrl, displayName, userUid);

            ArrayList<Player> players = new ArrayList<>();
            players.add(new Player(displayName, photoUrl, userUid));

            ArrayList<String> playList = new ArrayList<>();

            DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("games");
            String key = gamesRef.push().getKey();
            gamesRef.child(key).setValue(new WhoTuneGame(
                    name,
                    admin,
                    new Date(),
                    players,
                    playList,
                    GameState.OPEN
            ));

            Bundle bundle = new Bundle();
            bundle.putString("GAME_ID", key);

            CreateGameDialog.this.getDialog().cancel();
            ((Main2Activity) getActivity()).changeFragment(PlayersInGameFragment.newInstance(), false, bundle);

        } else {
            Toast.makeText(getActivity(), "Enter a name", Toast.LENGTH_SHORT).show();
        }

    }
}