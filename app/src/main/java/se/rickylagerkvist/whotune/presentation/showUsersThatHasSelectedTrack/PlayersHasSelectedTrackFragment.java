package se.rickylagerkvist.whotune.presentation.showUsersThatHasSelectedTrack;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.rickylagerkvist.whotune.MainActivity;
import se.rickylagerkvist.whotune.MainActivityNavigationInterFace;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.data.database.ApiUtils;
import se.rickylagerkvist.whotune.data.database.FireBaseRef;
import se.rickylagerkvist.whotune.data.database.SpotifyService;
import se.rickylagerkvist.whotune.data.model.spotify.profile.SpotifyProfile;
import se.rickylagerkvist.whotune.data.model.whoTune.User;
import se.rickylagerkvist.whotune.presentation.playSongs.SongPlayerFragment;
import se.rickylagerkvist.whotune.presentation.shared.PlayersCardAdapter;
import se.rickylagerkvist.whotune.utils.SharedPrefUtils;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayersHasSelectedTrackFragment extends Fragment{

    MainActivityNavigationInterFace navigationInterFace;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            navigationInterFace = (MainActivityNavigationInterFace) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement MainActivityNavigationInterFace");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_players_has_selected_track, container, false);

        DatabaseReference playersRef = FireBaseRef.round(SharedPrefUtils.getGameId(getActivity())).child("players");

        ListView listView = (ListView) rootView.findViewById(R.id.lv_players_that_has_selected_track);
        PlayersCardAdapter adapter = new PlayersCardAdapter(getActivity(), User.class, R.layout.player_card, playersRef, true, false);
        listView.setAdapter(adapter);

        Button btnPlayTracks = (Button) rootView.findViewById(R.id.btn_play_tracks);
        btnPlayTracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationInterFace.changeFragment(SongPlayerFragment.newInstance(), false);
            }
        });

        return rootView;
    }

}
