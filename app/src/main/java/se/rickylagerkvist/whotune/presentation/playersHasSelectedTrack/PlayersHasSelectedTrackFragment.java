package se.rickylagerkvist.whotune.presentation.playersHasSelectedTrack;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

import se.rickylagerkvist.whotune.presentation.main.MainActivity;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.data.model.SpotifyData.Image;
import se.rickylagerkvist.whotune.presentation.playSongs.SongPlayerFragment;
import se.rickylagerkvist.whotune.presentation.playersInGame.PlayersCardAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayersHasSelectedTrackFragment extends Fragment implements PlayersHasSelecterTrackPresenter.View {

    private PlayersHasSelecterTrackPresenter presenter;
    private PlayersCardAdapter adapter;
    private ListView listView;
    private Button btnPlayTracks;
    private DatabaseReference gameRef;
    private Image exitImage;

    public PlayersHasSelectedTrackFragment() {
        // Required empty public constructor
    }

    public static PlayersHasSelectedTrackFragment newInstance() {
        PlayersHasSelectedTrackFragment fragment = new PlayersHasSelectedTrackFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_playershas_selected_track, container, false);

        presenter = new PlayersHasSelecterTrackPresenter(this);
        listView = (ListView) rootView.findViewById(R.id.lv_players_that_has_selected_track);

        btnPlayTracks = (Button) rootView.findViewById(R.id.btn_play_tracks);
        btnPlayTracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                ((MainActivity)v.getContext()).changeFragment(SongPlayerFragment.newInstance(), false, bundle);
            }
        });

        return rootView;
    }

}
