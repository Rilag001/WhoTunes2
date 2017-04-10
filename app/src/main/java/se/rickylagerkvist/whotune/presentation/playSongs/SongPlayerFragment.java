package se.rickylagerkvist.whotune.presentation.playSongs;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import se.rickylagerkvist.whotune.data.database.FireBaseRef;
import se.rickylagerkvist.whotune.data.model.GuessOrAnswer;
import se.rickylagerkvist.whotune.data.model.Player;
import se.rickylagerkvist.whotune.data.model.SpotifyData.Track;
import se.rickylagerkvist.whotune.data.model.WhoTuneGame;
import se.rickylagerkvist.whotune.presentation.main.MainActivity;
import se.rickylagerkvist.whotune.R;
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
    private ProgressBar trackProgressBar;
    private ArrayList<String> tracksUri = new ArrayList<>();
    private ConstraintLayout constraintLayout;
    private WhoTuneGame game;


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

        // progressbar
        trackProgressBar = (ProgressBar) rootView.findViewById(R.id.progressbar_player_track);
        trackProgressBar.setVisibility(ProgressBar.VISIBLE);
        trackProgressBar.setProgress(20);
        trackProgressBar.setMax(100);

        // track UI
        coverArt = (ImageView) rootView.findViewById(R.id.iv_player_coverart);
        trackName = (TextView) rootView.findViewById(R.id.tv_player_trackname);
        artist = (TextView) rootView.findViewById(R.id.tv_player_artist);
        trackProgress = (TextView) rootView.findViewById(R.id.tv_track_progress);
        trackLength = (TextView) rootView.findViewById(R.id.tv_track_length);
        playPause = (ImageView) rootView.findViewById(R.id.iv_player_play_pause);
        nextTrack = (ImageView) rootView.findViewById(R.id.iv_player_skip_next);
        previousTrack = (ImageView) rootView.findViewById(R.id.iv_player_skip_previous);

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

                for (Player player : game.getPlayers().values()) {
                    tracksUri.add(player.getSelectedTrack().getUri());
                    Collections.shuffle(tracksUri);

                    // temp
                    upDateTrackUI(player.getSelectedTrack());
                }
                ((MainActivity)getContext()).playSpotify(tracksUri);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // TODO update UI on track changes

        // TODO view results on track end

        // player click actions
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).pauseOrResumeSpotify();
            }
        });

        nextTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).skipToPrevious();
            }
        });

        previousTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).skipToNext();
            }
        });

        return rootView;
    }

    public void upDateTrackUI(Track track){

        track.getAlbum().getImages().get(0);

        Glide.with(getActivity()).load(track.getBigCoverArt()).into(coverArt);
        trackName.setText(track.getName());
        artist.setText(track.getAllArtistAsJoinedString());

        //
        trackLength.setText(Utils.convertMsToMinSecString(track.getDuration_ms()));
    }

}
