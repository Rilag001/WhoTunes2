package se.rickylagerkvist.whotune.playersInGame;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import se.rickylagerkvist.whotune.Main2Activity;
import se.rickylagerkvist.whotune.data.Player;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.selectTrack.SelectTrackFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayersInGameFragment extends Fragment {

    private ListView listView;
    private Button btnStartGame;
    private PlayersCardAdapter adapter;
    private DatabaseReference playerRef;
    private String GAME_NODE;

    public PlayersInGameFragment() {
        // Required empty public constructor
    }

    public static PlayersInGameFragment newInstance() {
        PlayersInGameFragment fragment = new PlayersInGameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_players_in_game, container, false);

        btnStartGame = (Button) rootView.findViewById(R.id.btn_start_game);
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Main2Activity)v.getContext()).changeFragment(SelectTrackFragment.newInstance(),false, null);
            }
        });

        GAME_NODE = getArguments().getString("GAME_ID");

        playerRef = FirebaseDatabase.getInstance()
                .getReference("games")
                .child(GAME_NODE)
                .child("players");

        listView = (ListView) rootView.findViewById(R.id.lv_players);
        adapter = new PlayersCardAdapter(
                getActivity(),
                Player.class,
                R.layout.games_list_layout,
                playerRef);
        listView.setAdapter(adapter);


        return rootView;
    }

}
