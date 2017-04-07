package se.rickylagerkvist.whotune.playersInGame;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import se.rickylagerkvist.whotune.MainActivity.Main2Activity;
import se.rickylagerkvist.whotune.data.Admin;
import se.rickylagerkvist.whotune.data.GameState;
import se.rickylagerkvist.whotune.data.Player;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.selectTrack.SelectTrackFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayersInGameFragment extends Fragment implements PlayersInGamePresenter.View {

    // member variables
    private ListView listView;
    private Button btnStartGame;
    private PlayersCardAdapter adapter;
    private DatabaseReference gameRef, adminRef, playersRef;
    private String GAME_NODE;
    private ImageView ivExit;
    private boolean isAdmin;
    private PlayersInGamePresenter presenter;
    // end region

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

        presenter = new PlayersInGamePresenter(this);

        listView = (ListView) rootView.findViewById(R.id.lv_players);
        ivExit = (ImageView) rootView.findViewById(R.id.iv_close_players_fragment);
        btnStartGame = (Button) rootView.findViewById(R.id.btn_start_game);

        setUpFirebaseRefs();
        setUpListView();
        checkIfYouAreAdmin();

        // Exit game
        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO if admin remove game, else remove your player from game
            }
        });
        // TODO change to menu fragment if game is gone/admin exit

        // Start game
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAdmin){
                    gameRef.child("gameState").setValue(GameState.SELECTING_TRACK);
                    ((Main2Activity)v.getContext()).changeFragment(SelectTrackFragment.newInstance(),false, null);
                } else {
                    Toast.makeText(getContext(), R.string.only_admin_can_start_game, Toast.LENGTH_SHORT).show();
                }

            }
        });

        return rootView;
    }

    private void checkIfYouAreAdmin() {
        adminRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Admin admin = dataSnapshot.getValue(Admin.class);
                if(admin.getUid().equals(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("USERUID",""))){
                    isAdmin = true;
                } else {
                    isAdmin = false;
                    btnStartGame.setText(R.string.only_admin_can_start_game);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setUpFirebaseRefs() {

        GAME_NODE = getArguments().getString("GAME_ID");

        gameRef = FirebaseDatabase.getInstance()
                .getReference("games")
                .child(GAME_NODE);

        adminRef = gameRef.child("admin");

        playersRef = gameRef.child("players");
    }

    private void setUpListView() {

        adapter = new PlayersCardAdapter(
                getActivity(),
                Player.class,
                R.layout.player_list_layout,
                playersRef);
        listView.setAdapter(adapter);
    }

}
