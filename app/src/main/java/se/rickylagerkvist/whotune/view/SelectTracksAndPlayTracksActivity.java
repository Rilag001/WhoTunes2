package se.rickylagerkvist.whotune.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.models.Round;
import se.rickylagerkvist.whotune.models.spotifyModels.TrackList;
import se.rickylagerkvist.whotune.models.spotifyModels.TrackObject;
import se.rickylagerkvist.whotune.utils.APIUrlPaths;
import se.rickylagerkvist.whotune.utils.HttpUtils;

import static se.rickylagerkvist.whotune.R.id.searchEditText;

public class SelectTracksAndPlayTracksActivity extends Activity implements
        PlayerNotificationCallback, ConnectionStateCallback {

    // spotify
    private static final String CLIENT_ID = "d2765d66afd94102adb14e41de533df0";
    private static final String REDIRECT_URI = "the-music-game-login://callback";
    // Request code that will be used to verify if the result comes from correct activity
    // Can be any integer
    private static final int REQUEST_CODE = 1225;
    private Player mPlayer;

    // views
    EditText mSearchEditText, mPlayerNameEditText;
    ImageButton mSearchButton, mNextTrackButton, mPauseTrackButton;
    ListView mTracksListView;
    ImageView mTrackCardImageView;
    TextView mTrack, mAlbum;
    Button mNextButton;
    RelativeLayout mPlayStack, mMainLayout;
    TextView mCurrentPlayerNrTextView;


    // list
    ArrayList<TrackObject> mTrackObjects = new ArrayList<>();
    TrackCardAdapter mTrackCardAdapter;

    // round
    int nrOfPlayers = 0;
    int createdPlayers = 0;
    Round round = new Round();
    String currentTrackUri;
    ArrayList<String> tracks = new ArrayList<>();
    TrackObject selectedTrack;
    Boolean IsPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Authentication to Spotify
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);

        // views
        mSearchEditText = (EditText) findViewById(searchEditText);
        mSearchButton = (ImageButton) findViewById(R.id.searchButton);
        mTracksListView = (ListView) findViewById(R.id.trackListView);
        mTrackCardImageView = (ImageView) findViewById(R.id.track_card_imageView);
        mTrack = (TextView) findViewById(R.id.trackTextView);
        mAlbum = (TextView) findViewById(R.id.albumTextView);
        mPlayStack = (RelativeLayout) findViewById(R.id.playStack);
        mNextButton = (Button) findViewById(R.id.selectTrackButton);
        mPlayerNameEditText = (EditText) findViewById(R.id.playerNameEditText);
        mNextTrackButton = (ImageButton) findViewById(R.id.nextTrackButton);
        mPauseTrackButton = (ImageButton) findViewById(R.id.pauseTrackButton);
        mMainLayout = (RelativeLayout) findViewById(R.id.activity_main);
        mCurrentPlayerNrTextView = (TextView) findViewById(R.id.playerNrTextView);

        // set adapter for ListView
        mTrackCardAdapter = new TrackCardAdapter(this, mTrackObjects);
        mTracksListView.setAdapter(mTrackCardAdapter);

        // get nr of players
        Intent incomingIntent = this.getIntent();
        nrOfPlayers = incomingIntent.getIntExtra("nrOfPlayers", 0);
        mCurrentPlayerNrTextView.setText(getString(R.string.Player_) + 1 +"/" + nrOfPlayers);

        // search track
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchTracks();

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mMainLayout.getWindowToken(), 0);

                    return true;
                }
                return false;
            }
        });

        // skip to next track or nav to SelectGuessesActivity
        mNextTrackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentTrackUri.equals(tracks.get(nrOfPlayers-1))){

                    Gson gs = new Gson();
                    String roundToJson = gs.toJson(round);

                    mPlayer.pause();

                    new AlertDialog.Builder(SelectTracksAndPlayTracksActivity.this)
                            .setTitle("End of playlist")
                            .setMessage("Who picked what song? Pick you guesses... Ready to see the results?")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Gson gs = new Gson();
                                    String roundToJson = gs.toJson(round);

                                    mPlayer.pause();

                                    Intent intent = new Intent(SelectTracksAndPlayTracksActivity.this, ShowAnswersActivity.class);
                                    intent.putExtra("round", roundToJson);
                                    startActivity(intent);
                                }
                            }).show();

                } else{
                    mPlayer.skipToNext();
                }
            }
        });
        // pause ot play track
        mPauseTrackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(IsPlaying){
                    mPlayer.pause();
                    mPauseTrackButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                } else {
                    mPlayer.resume();
                    mPauseTrackButton.setImageResource(R.drawable.ic_pause_black_24dp);
                }
            }
        });


        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!mPlayerNameEditText.getText().toString().isEmpty() && selectedTrack != null){

                    String name = mPlayerNameEditText.getText().toString();
                    se.rickylagerkvist.whotune.models.Player player = new se.rickylagerkvist.whotune.models.Player(name);
                    player.setSelectedTrack(selectedTrack);
                    round.addPlayer(player);

                    createdPlayers += 1;

                    mPlayerNameEditText.setText("");

                    mTrackCardImageView.setImageResource(android.R.color.transparent);
                    mTrack.setText("");
                    mAlbum.setText("");
                    mSearchEditText.setText("");
                    mTrackCardAdapter.clear();

                    selectedTrack = null;

                    animateDown();

                    if(createdPlayers >= nrOfPlayers){

                        mCurrentPlayerNrTextView.setText(R.string.playing);

                        round.shuffle();

                        for (se.rickylagerkvist.whotune.models.Player item : round.getPlayers()) {
                            tracks.add(item.getSelectedTrack().uri);
                        }

                        mNextButton.setVisibility(View.GONE);
                        mNextTrackButton.setVisibility(View.VISIBLE);
                        mPauseTrackButton.setVisibility(View.VISIBLE);

                        mPlayer.play(tracks);

                    } else {

                        int i = createdPlayers +1;
                        mCurrentPlayerNrTextView.setText("Player " + i  +"/" + nrOfPlayers);

                        Snackbar snackbar = Snackbar
                                .make(mMainLayout, "Pass the mobile to the next user", Snackbar.LENGTH_LONG);
                        snackbar.show();

                        mPlayerNameEditText.requestFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(mPlayerNameEditText, InputMethodManager.SHOW_IMPLICIT);
                    }

                } else {
                    Snackbar snackbar = Snackbar.make(mMainLayout, "Select name and track", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTracks();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mMainLayout.getWindowToken(), 0);
            }
        });


        mTracksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                selectedTrack = mTrackCardAdapter.getItem(position);

                setPlayUI(selectedTrack);
            }
        });

        mTracksListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

                if(i == 0){
                    animateUp();
                    mPlayStack.setVisibility(View.VISIBLE);
                } else {
                    if(mPlayStack.getVisibility() == View.VISIBLE){
                        animateDown();
                        mPlayStack.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {


            }
        });
    }

    private void setPlayUI(TrackObject track) {

        Glide.with(this).load(track.album.images.get(0).url).into(mTrackCardImageView);
        mTrack.setText(track.name);
        mAlbum.setText(track.album.name);

        if(mPlayStack.getVisibility() == View.INVISIBLE){
            animateUp();
        }
    }


    private void animateUp(){
        Animation bottomUp = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bottom_up);
        mPlayStack.startAnimation(bottomUp);
        mPlayStack.setVisibility(View.VISIBLE);
    }
    private void animateDown(){
        Animation bottomDown = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bottom_down);
        mPlayStack.startAnimation(bottomDown);
        mPlayStack.setVisibility(View.INVISIBLE);
    }

    public void searchTracks()  {

        String searchString = mSearchEditText.getText().toString();

        if(!searchString.isEmpty()){
            try {
                searchTrack(searchString);
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        } else {

            Snackbar snackbar = Snackbar.make(mMainLayout, "Search is empty!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    public void searchTrack(String searchText) throws JSONException {

        String newSearchText = searchText.replaceAll(" ", "+");

        HttpUtils.get(APIUrlPaths.searchTrack.replace("{search}", newSearchText), null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
                TrackList tacksList = JSON.parseObject(response.toString(), TrackList.class);

                mTrackObjects.clear();
                mTrackCardAdapter.clear();
                mTrackObjects = tacksList.tracks.items;
                mTrackCardAdapter.addAll(mTrackObjects);
                mTrackCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {

                String accessToken = response.getAccessToken();

                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
                    @Override
                    public void onInitialized(Player player) {
                        mPlayer = player;
                        mPlayer.addConnectionStateCallback(SelectTracksAndPlayTracksActivity.this);
                        mPlayer.addPlayerNotificationCallback(SelectTracksAndPlayTracksActivity.this);

                        mPlayer.addPlayerNotificationCallback(new PlayerNotificationCallback() {
                            @Override
                            public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
                                currentTrackUri = playerState.trackUri;

                                IsPlaying = playerState.playing;

                                // if end of playlist hit
                                if(eventType.equals(EventType.END_OF_CONTEXT)){
                                    new AlertDialog.Builder(SelectTracksAndPlayTracksActivity.this)
                                            .setTitle("End of playlist")
                                            .setMessage("Who picked what song? Pick you guesses... Ready to see the results?")
                                            .setCancelable(false)
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Gson gs = new Gson();
                                                    String roundToJson = gs.toJson(round);

                                                    mPlayer.pause();

                                                    Intent intent = new Intent(SelectTracksAndPlayTracksActivity.this, ShowAnswersActivity.class);
                                                    intent.putExtra("round", roundToJson);
                                                    startActivity(intent);
                                                }
                                            }).show();
                                }

                                for (se.rickylagerkvist.whotune.models.Player item : round.getPlayers()) {

                                    if(item.getSelectedTrack().uri.equals(currentTrackUri)){
                                        setPlayUI(item.getSelectedTrack());
                                    }
                                }
                            }

                            @Override
                            public void onPlaybackError(ErrorType errorType, String errorDetails) {
                                // Handle errors
                            }
                        });

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Throwable error) {
        Log.d("MainActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d("MainActivity", "Playback event received: " + eventType.name());
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String errorDetails) {
        Log.d("MainActivity", "Playback error received: " + errorType.name());
    }

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        mTrackCardAdapter.clear();
        super.onDestroy();
    }
}
