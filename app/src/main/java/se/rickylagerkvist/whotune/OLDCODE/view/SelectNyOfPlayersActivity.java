package se.rickylagerkvist.whotune.OLDCODE.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import se.rickylagerkvist.whotune.R;

public class SelectNyOfPlayersActivity extends AppCompatActivity {

    FloatingActionButton mPlayButton;
    EditText mSelectNrOgPlayersEditText;
    RelativeLayout mMainLayout;
    ImageView mInfoImage;

    int nrOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ny_of_players_actvivity);

        mPlayButton = (FloatingActionButton) findViewById(R.id.playButton);
        mSelectNrOgPlayersEditText = (EditText) findViewById(R.id.nrOfPlayersEditPext);
        mMainLayout = (RelativeLayout) findViewById(R.id.activity_select_ny_of_players_actvivity);
        mInfoImage = (ImageView) findViewById(R.id.infoImage);

        mSelectNrOgPlayersEditText.setText("3");

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nrOfPlayers = Integer.parseInt(mSelectNrOgPlayersEditText.getText().toString());

                if(nrOfPlayers >= 3){

                    Intent intent = new Intent(SelectNyOfPlayersActivity.this, SelectTracksAndPlayTracksActivity.class);
                    intent.putExtra("nrOfPlayers", nrOfPlayers);
                    startActivity(intent);

                } else {

                    Snackbar snackbar = Snackbar
                            .make(mMainLayout, "Select at least 3 players", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        mInfoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(SelectNyOfPlayersActivity.this)
                        .setTitle("Ho-hooo... How to play")
                        .setMessage("A minimum of 3 players each pick a song. The songs will be played in random order. The players then guess who picked what song. \n\nEasy enough right? Lets play to some sweet tunes!")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
    }
}
