package se.rickylagerkvist.whotune.OLDCODE.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;

import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.data.GuessOrAnswer;
import se.rickylagerkvist.whotune.data.Round;

public class ShowAnswersActivity extends AppCompatActivity {

    Round round;
    ArrayList<GuessOrAnswer> mAnswersObjects = new ArrayList<>();

    ListView mAnswersListView;
    AnswerCardAdapter mAnswerCardAdapter;
    ImageView mPlayAgainImageView, mPlayListImageView;
    TextView mPlayAgainTextView, mPlayListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_answers);

        // get intent
        Intent intent = getIntent();
        String string = intent.getStringExtra("round");
        // get round
        Gson gS = new Gson();
        round = gS.fromJson(string, Round.class);

        // set adapter to ListView
        mAnswersObjects = new ArrayList<>(round.getUserSelections());
        mAnswersListView = (ListView) findViewById(R.id.answersListView);
        mAnswerCardAdapter = new AnswerCardAdapter(this, mAnswersObjects);
        mAnswersListView.setAdapter(mAnswerCardAdapter);

        mPlayAgainImageView = (ImageView) findViewById(R.id.playAgainImageView);
        mPlayAgainTextView = (TextView)  findViewById(R.id.playAgainTextView);

        mPlayListImageView = (ImageView) findViewById(R.id.playListImageView);
        mPlayListTextView = (TextView)  findViewById(R.id.playListTextView);

        // play again
        mPlayAgainImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ShowAnswersActivity.this, SelectNyOfPlayersActivity.class);
                startActivity(intent1);
            }
        });
        mPlayAgainTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ShowAnswersActivity.this, SelectNyOfPlayersActivity.class);
                startActivity(intent1);
            }
        });

        // save playlist
        mPlayListImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    postPlaylist("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        mPlayListTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    postPlaylist("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void postPlaylist(String userId) throws JSONException {

    }
}
