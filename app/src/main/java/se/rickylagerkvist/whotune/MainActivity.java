package se.rickylagerkvist.whotune;

import android.content.DialogInterface;
import android.content.Intent;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.rickylagerkvist.whotune.data.database.ApiUtils;
import se.rickylagerkvist.whotune.data.database.SpotifyService;
import se.rickylagerkvist.whotune.data.model.spotify.profile.SpotifyProfile;
import se.rickylagerkvist.whotune.presentation.menu.MenuFragment;
import se.rickylagerkvist.whotune.utils.ConvertAndFormatHelpers;
import se.rickylagerkvist.whotune.utils.DialogHelpers;
import se.rickylagerkvist.whotune.utils.SharedPrefUtils;

public class MainActivity extends AppCompatActivity implements
        PlayerNotificationCallback, ConnectionStateCallback, MainActivityNavigationInterFace {

    private static final String CLIENT_ID = "d2765d66afd94102adb14e41de533df0";
    private static final String REDIRECT_URI = "the-music-game-login://callback";
    // Request code that will be used to verify if the result comes from correct activity
    // Can be any integer
    private static final int REQUEST_CODE = 1225;
    private Player spotifyPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init MenuFragment
        changeFragment(MenuFragment.newInstance(), false);
        isSpotifyInstalledHandler();
        spotifyAuth();
    }


    private void isSpotifyInstalledHandler() {
        boolean isSpotifyInstalled = ConvertAndFormatHelpers.isPackageInstalled(getString(R.string.spotify_package_name), this.getPackageManager());
        if(!isSpotifyInstalled){
            DialogHelpers.showSpotifyNotInstalledDialog(this);
        } else {
            Log.d("MainActivity", "Spotify installed");
        }
    }


    private void spotifyAuth() {
        // Authentication to Spotify
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming", "playlist-modify-public", "playlist-modify-private"});
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {

                // save auth token
                SharedPrefUtils.saveAuthToken(response.getAccessToken(), getApplicationContext());
                // get and save profile id
                getSpotifyProfile(response.getAccessToken());

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
            } else {
                new AlertDialog.Builder(MainActivity.this)
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
        }
    }


    private void getSpotifyProfile(String auth) {

        SpotifyService spotifyService = ApiUtils.getSpotifyService();

        Call<SpotifyProfile> meCall = spotifyService.getMyProfile("Bearer " + auth);
        meCall.enqueue(new Callback<SpotifyProfile>() {
            @Override
            public void onResponse(Call<SpotifyProfile> call, Response<SpotifyProfile> response) {
                SharedPrefUtils.saveSpotifyProfileId(response.body().getId(), getApplicationContext());

                Toast.makeText(MainActivity.this, "Welcome " + response.body().getDisplay_name(), Toast.LENGTH_SHORT).show();
                SharedPrefUtils.saveSpotifyProfilePic(response.body().getImages().get(0).url, getApplicationContext());
            }

            @Override
            public void onFailure(Call<SpotifyProfile> call, Throwable t) {
            }
        });
    }


    // Spotify callbacks
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
