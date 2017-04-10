package se.rickylagerkvist.whotune.presentation.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;

import java.util.List;

import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.presentation.menu.MenuFragment;
import se.rickylagerkvist.whotune.presentation.results.ResultsFragment;
import se.rickylagerkvist.whotune.utils.Utils;

public class MainActivity extends AppCompatActivity implements
        PlayerNotificationCallback, ConnectionStateCallback, MainActivityPresenter.View {

    private static final String CLIENT_ID = "d2765d66afd94102adb14e41de533df0";
    private static final String REDIRECT_URI = "the-music-game-login://callback";
    // Request code that will be used to verify if the result comes from correct activity
    // Can be any integer
    private static final int REQUEST_CODE = 1225;
    private Player spotifyPlayer;
    private static String SPOTIFY_PACKAGE_NAME = "com.spotify.music";
    private MainActivityPresenter presenter;
    private Boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        presenter = new MainActivityPresenter(this);

        // init MenuFragment
        changeFragment(MenuFragment.newInstance(), false);

        // check is spotify is installed
        boolean isSpotifyInstalled = Utils.isPackageInstalled(SPOTIFY_PACKAGE_NAME, this.getPackageManager());
        if(!isSpotifyInstalled){
            new AlertDialog.Builder(getApplicationContext())
                    .setTitle("")
                    .setMessage("")
                    .setPositiveButton("Install Spotify", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + SPOTIFY_PACKAGE_NAME)));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + SPOTIFY_PACKAGE_NAME)));
                            }
                        }
                    })
                    .setNegativeButton("Exit app", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();
                            System.exit(0);
                        }
                    })
                    .show();
        } else {
            Toast.makeText(this, "Spotify installed", Toast.LENGTH_SHORT).show();
        }

        // Authentication to Spotify
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    public void changeFragment(Fragment fragment, boolean addToBackstack) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    // Spotify methods
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {

                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new Player.InitializationObserver() {
                    @Override
                    public void onInitialized(Player player) {
                        spotifyPlayer = player;
                        spotifyPlayer.addConnectionStateCallback(MainActivity.this);
                        spotifyPlayer.addPlayerNotificationCallback(MainActivity.this);
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
    public void onLoginFailed(Throwable throwable) {
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
    public void onPlaybackError(ErrorType errorType, String s) {
        Log.d("MainActivity", "Playback error received: " + errorType.name());
    }
    //end region

    @Override
    protected void onDestroy() {
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    // get Spotify player
    public Player getPlayer(){
        return spotifyPlayer;
    }



}
