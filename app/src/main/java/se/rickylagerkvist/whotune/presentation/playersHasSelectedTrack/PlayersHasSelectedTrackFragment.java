package se.rickylagerkvist.whotune.presentation.playersHasSelectedTrack;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

import se.rickylagerkvist.whotune.MainActivity;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.data.database.FireBaseRef;
import se.rickylagerkvist.whotune.data.model.User;
import se.rickylagerkvist.whotune.presentation.playSongs.SongPlayerFragment;
import se.rickylagerkvist.whotune.presentation.shared.PlayersCardAdapter;
import se.rickylagerkvist.whotune.utils.SharedPrefUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayersHasSelectedTrackFragment extends Fragment{

    private PlayersCardAdapter adapter;
    private ListView listView;
    private Button btnPlayTracks;
    private DatabaseReference playersRef;
    // private Image exitImage;

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
        View rootView = inflater.inflate(R.layout.fragment_players_has_selected_track, container, false);

        playersRef = FireBaseRef.round(SharedPrefUtils.getGameId(getActivity())).child("players");

        listView = (ListView) rootView.findViewById(R.id.lv_players_that_has_selected_track);
        adapter = new PlayersCardAdapter(getActivity(), User.class, R.layout.player_card, playersRef, true, false);
        listView.setAdapter(adapter);

        btnPlayTracks = (Button) rootView.findViewById(R.id.btn_play_tracks);
        btnPlayTracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)v.getContext()).changeFragment(SongPlayerFragment.newInstance(), false);
            }
        });

        return rootView;
    }

}
