package se.rickylagerkvist.whotune.playSongs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.rickylagerkvist.whotune.MainActivity.Main2Activity;
import se.rickylagerkvist.whotune.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongPlayerFragment extends Fragment implements SongPlayerPresenter.View {

    private SongPlayerPresenter presenter;

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

        return rootView;
    }

    public void play(String uri){
        ((Main2Activity)getContext()).playSpotify(uri);
    }

    public void pause(){
        ((Main2Activity)getContext()).pauseSpotify();
    }

    public void resume(){
        ((Main2Activity)getContext()).resumePlayer();
    }

    public void skipToPrevious(){
        ((Main2Activity)getContext()).skipToPrevious();
    }

    public void skipToNext(){
        ((Main2Activity)getContext()).skipToNext();
    }

}
