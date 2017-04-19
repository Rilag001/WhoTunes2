package se.rickylagerkvist.whotune.presentation.playSongs;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.PlayerStateCallback;

import java.util.ArrayList;
import java.util.Collections;

import se.rickylagerkvist.whotune.data.database.FireBaseRef;
import se.rickylagerkvist.whotune.data.model.whoTune.Admin;
import se.rickylagerkvist.whotune.data.model.whoTune.RoundState;
import se.rickylagerkvist.whotune.data.model.whoTune.User;
import se.rickylagerkvist.whotune.data.model.spotify.tracks.Track;
import se.rickylagerkvist.whotune.data.model.whoTune.WhoTuneRound;
import se.rickylagerkvist.whotune.MainActivity;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.utils.DialogHelpers;
import se.rickylagerkvist.whotune.utils.SharedPrefUtils;
import se.rickylagerkvist.whotune.utils.ConvertAndFormatHelpers;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongPlayerFragment extends Fragment{

    private ImageView coverArt;
    private TextView trackName, artist, trackProgress, trackLength;
    private ImageView playPause, nextTrack, previousTrack;
    private SeekBar seekBar;
    private ArrayList<String> tracksUri = new ArrayList<>();
    private ConstraintLayout constraintLayout;
    private WhoTuneRound round;
    private boolean isSpotifyPlaying;
    private Handler spotifyProgressHandler;
    private String TAG = "SongPlayerFragment";
    private Player spotifyPlayer;
    private boolean isAdmin = false;

    public SongPlayerFragment() {
        // Required empty public constructor
    }

    public static SongPlayerFragment newInstance() {
        SongPlayerFragment fragment = new SongPlayerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_song_player, container, false);

        // track UI
        constraintLayout = (ConstraintLayout) rootView.findViewById(R.id.cl_player);
        coverArt = (ImageView) rootView.findViewById(R.id.iv_player_coverart);
        trackName = (TextView) rootView.findViewById(R.id.tv_player_trackname);
        artist = (TextView) rootView.findViewById(R.id.tv_player_artist);
        trackProgress = (TextView) rootView.findViewById(R.id.tv_track_progress);
        trackLength = (TextView) rootView.findViewById(R.id.tv_track_length);
        playPause = (ImageView) rootView.findViewById(R.id.iv_player_play_pause);
        nextTrack = (ImageView) rootView.findViewById(R.id.iv_player_skip_next);
        previousTrack = (ImageView) rootView.findViewById(R.id.iv_player_skip_previous);

        // progressbar
        seekBar = (SeekBar) rootView.findViewById(R.id.progressbar_player_track);
        seekBar.setVisibility(ProgressBar.VISIBLE);

        animateBackground();
        getSpotifyPlayerAddNotificationCallback();
        getRound();
        seekBarActionTrackProgress();
        setRoundState();

        // task to UI update track progress
        spotifyProgressHandler = new Handler();
        startTrackProgressTask();

        playerClickActions();

        return rootView;
    }

    private void setRoundState() {
        FireBaseRef.roundAdmin(SharedPrefUtils.getGameId(getContext())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Admin admin = dataSnapshot.getValue(Admin.class);
                if(admin.getUid().equals(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("USERUID",""))){
                    isAdmin = true;
                    FireBaseRef.roundGameState(SharedPrefUtils.getGameId(getContext())).setValue(RoundState.PLAYING);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void playerClickActions() {

        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSpotifyPlaying){
                    spotifyPlayer.pause();
                    playPause.setImageResource(R.drawable.ic_play_arrow_white_48dp);
                } else {
                    spotifyPlayer.resume();
                    playPause.setImageResource(R.drawable.ic_pause_white_48dp);
                }
            }
        });

        nextTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    spotifyPlayer.skipToNext();
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }

            }
        });

        previousTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spotifyPlayer.skipToPrevious();
            }
        });
    }

    private void seekBarActionTrackProgress() {
        // user action - change track progress
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    spotifyPlayer.seekToPosition(progress);
                    trackProgress.setText(ConvertAndFormatHelpers.convertMsToMinSecString(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void getRound() {
        // get round
        FireBaseRef.round(SharedPrefUtils.getGameId(getActivity())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                round = dataSnapshot.getValue(WhoTuneRound.class);

                // get tracks and play
                for (User user : round.getPlayers().values()) {
                    tracksUri.add(user.getSelectedTrack().getUri());
                    Collections.shuffle(tracksUri);
                }
                spotifyPlayer.play(tracksUri);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getSpotifyPlayerAddNotificationCallback() {
        // get SpotifyPlayer
        spotifyPlayer = ((MainActivity)getContext()).getPlayer();
        spotifyPlayer.addPlayerNotificationCallback(new PlayerNotificationCallback() {
            @Override
            public void onPlaybackEvent(EventType eventType, PlayerState playerState) {

                isSpotifyPlaying = playerState.playing;

                if(eventType.equals(EventType.END_OF_CONTEXT)){
                    DialogHelpers.showNavToResultsDialog(getContext());
                    if(isAdmin){
                        FireBaseRef.roundGameState(SharedPrefUtils.getGameId(getContext())).setValue(RoundState.SHOW_RESULTS);
                    }
                } else {

                    try{
                        // update track UI
                        for (User player : round.getPlayers().values()) {
                            if(player.getSelectedTrack().getUri().equals(playerState.trackUri)){
                                updateTrackUI(player.getSelectedTrack(), playerState.durationInMs);
                            }
                        }
                    } catch (Exception e){
                        Log.e(TAG, e.toString());
                    }
                }
            }

            @Override
            public void onPlaybackError(ErrorType errorType, String errorDetails) {
                // Handle errors
            }
        });
    }

    private void animateBackground() {
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }

    public void updateTrackUI(Track track, int durationInMs){

        track.getAlbum().getImages().get(0);

        Glide.with(getActivity()).load(track.getBigCoverArt()).into(coverArt);
        trackName.setText(track.getName());
        artist.setText(track.getAllArtistAsJoinedString());

        seekBar.setMax(durationInMs);
        trackLength.setText(ConvertAndFormatHelpers.convertMsToMinSecString(durationInMs));
    }

    // update seekbar UI
    Runnable trackProg = new Runnable() {
        @Override
        public void run() {
            try{
                spotifyPlayer.getPlayerState(new PlayerStateCallback() {
                    @Override
                    public void onPlayerState(PlayerState playerState) {
                        trackProgress.setText(ConvertAndFormatHelpers.convertMsToMinSecString(playerState.positionInMs));
                        seekBar.setProgress(playerState.positionInMs);
                    }
                });
            } finally {
                spotifyProgressHandler.postDelayed(trackProg, 1000);
            }
        }
    };

    void startTrackProgressTask(){
        trackProg.run();
    }

    void stopTrackProgressTask(){
        spotifyProgressHandler.removeCallbacks(trackProg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTrackProgressTask();
    }
}
