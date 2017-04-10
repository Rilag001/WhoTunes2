package se.rickylagerkvist.whotune.playSongs;


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

import java.util.ArrayList;

import se.rickylagerkvist.whotune.MainActivity.Main2Activity;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.client.FireBaseRef;
import se.rickylagerkvist.whotune.utils.SharedPrefUtils;

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
        trackName = (TextView) rootView.findViewById(R.id.tv_track);
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

        // player click actions
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO play/pause
            }
        });

        nextTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO next track
            }
        });

        previousTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO previous track
            }
        });

        return rootView;
    }

    public void play(String uri){
        ((Main2Activity)getContext()).playSpotify(uri);
    }

    public void pauseOrResumeSpotify(){
        ((Main2Activity)getContext()).pauseOrResumeSpotify();
    }

    public void skipToPrevious(){
        ((Main2Activity)getContext()).skipToPrevious();
    }

    public void skipToNext(){
        ((Main2Activity)getContext()).skipToNext();
    }

}
