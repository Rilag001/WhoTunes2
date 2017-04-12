package se.rickylagerkvist.whotune.presentation.playSongs;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.PlayerStateCallback;

import java.util.ArrayList;
import java.util.Collections;

import se.rickylagerkvist.whotune.data.database.FireBaseRef;
import se.rickylagerkvist.whotune.data.model.Player;
import se.rickylagerkvist.whotune.data.model.SpotifyData.Track;
import se.rickylagerkvist.whotune.data.model.WhoTuneGame;
import se.rickylagerkvist.whotune.presentation.main.MainActivity;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.presentation.results.ResultsFragment;
import se.rickylagerkvist.whotune.utils.SharedPrefUtils;
import se.rickylagerkvist.whotune.utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongPlayerFragment extends Fragment implements SongPlayerPresenter.View {

    private SongPlayerPresenter presenter;
    private ImageView coverArt;
    private TextView trackName, artist, trackProgress, trackLength;
    private ImageView playPause, nextTrack, previousTrack;
    private SeekBar seekBar;
    private ArrayList<String> tracksUri = new ArrayList<>();
    private ConstraintLayout constraintLayout;
    private WhoTuneGame game;
    private boolean isPlaying;
    private Handler spotifyProgressHandler;

    private com.spotify.sdk.android.player.Player spotifyPlayer;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_song_player, container, false);

        presenter = new SongPlayerPresenter(this);

        constraintLayout = (ConstraintLayout) rootView.findViewById(R.id.cl_player);

        // track UI
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

        // animate background
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        // get game
        FireBaseRef.game(SharedPrefUtils.getGameId(getActivity())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                game = dataSnapshot.getValue(WhoTuneGame.class);

                // get tracks and play
                for (Player player : game.getPlayers().values()) {
                    tracksUri.add(player.getSelectedTrack().getUri());
                    Collections.shuffle(tracksUri);
                }
                spotifyPlayer.play(tracksUri);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // get SpotifyPlayer
        spotifyPlayer = ((MainActivity)getContext()).getPlayer();
        spotifyPlayer.addPlayerNotificationCallback(new PlayerNotificationCallback() {
            @Override
            public void onPlaybackEvent(EventType eventType, PlayerState playerState) {

                isPlaying = playerState.playing;

                //TODO view results on track end
                if(eventType.equals(EventType.END_OF_CONTEXT)){
                    ((MainActivity)getContext()).changeFragment(ResultsFragment.newInstance(), false);
                } else {

                    try{
                        // update track UI
                        for (Player player : game.getPlayers().values()) {
                            if(player.getSelectedTrack().getUri().equals(playerState.trackUri)){
                                updateTrackUI(player.getSelectedTrack(), playerState.durationInMs);
                            }
                        }
                    } catch (Exception e){
                        Log.e("SongPlayerFragment", e.toString());
                    }
                }
            }

            @Override
            public void onPlaybackError(ErrorType errorType, String errorDetails) {
                // Handle errors
            }
        });

        // user action - change track progress
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    spotifyPlayer.seekToPosition(progress);
                    trackProgress.setText(Utils.convertMsToMinSecString(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // task to UI update track progress
        spotifyProgressHandler = new Handler();
        startTrackProgressTask();

        // player click actions
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying){
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
                spotifyPlayer.skipToNext();
            }
        });

        previousTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spotifyPlayer.skipToPrevious();
            }
        });

        return rootView;
    }

    public void updateTrackUI(Track track, int durationInMs){

        track.getAlbum().getImages().get(0);

        Glide.with(getActivity()).load(track.getBigCoverArt()).into(coverArt);
        trackName.setText(track.getName());
        artist.setText(track.getAllArtistAsJoinedString());

        seekBar.setMax(durationInMs);
        trackLength.setText(Utils.convertMsToMinSecString(durationInMs));
    }

    // update track UI
    Runnable trackProg = new Runnable() {
        @Override
        public void run() {
            try{
                spotifyPlayer.getPlayerState(new PlayerStateCallback() {
                    @Override
                    public void onPlayerState(PlayerState playerState) {
                        trackProgress.setText(Utils.convertMsToMinSecString(playerState.positionInMs));
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
