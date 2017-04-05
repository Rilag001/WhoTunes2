package se.rickylagerkvist.whotune.playSongs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.rickylagerkvist.whotune.Main2Activity;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.menu.MenuFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongPlayerFragment extends Fragment {


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
        return inflater.inflate(R.layout.fragment_song_player, container, false);
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
