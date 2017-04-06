package se.rickylagerkvist.whotune.playersHasSelectedTrack;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.data.SpotifyData.Image;
import se.rickylagerkvist.whotune.playersInGame.PlayersCardAdapter;
import se.rickylagerkvist.whotune.playersInGame.PlayersInGameFragment;

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

        return rootView;
    }

}
