package se.rickylagerkvist.whotune.presentation.showPlayersInRound;


import android.content.Context;
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
import com.google.firebase.database.ValueEventListener;

import se.rickylagerkvist.whotune.MainActivityNavigationInterFace;
import se.rickylagerkvist.whotune.data.database.FireBaseRef;
import se.rickylagerkvist.whotune.MainActivity;
import se.rickylagerkvist.whotune.data.model.whoTune.Admin;
import se.rickylagerkvist.whotune.data.model.whoTune.RoundState;
import se.rickylagerkvist.whotune.data.model.whoTune.User;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.presentation.selectTrack.SelectTrackFragment;
import se.rickylagerkvist.whotune.presentation.shared.PlayersCardAdapter;
import se.rickylagerkvist.whotune.utils.SharedPrefUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayersInRoundFragment extends Fragment {

    // member variables
    private MainActivityNavigationInterFace navigationInterFace;
    private ListView listView;
    private Button btnStartGame;
    private boolean isAdmin;
    // end region

    public PlayersInRoundFragment() {
        // Required empty public constructor
    }

    public static PlayersInRoundFragment newInstance() {
        PlayersInRoundFragment fragment = new PlayersInRoundFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_players_in_game, container, false);

        listView = (ListView) rootView.findViewById(R.id.lv_players);
        ImageView ivExit = (ImageView) rootView.findViewById(R.id.iv_close_players_fragment);
        btnStartGame = (Button) rootView.findViewById(R.id.btn_start_game);

        setUpFirebaseRefs();
        setUpListView();
        checkIfYouAreAdmin();

        // Exit round
        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO if admin remove round, else remove your player from round
            }
        });
        // TODO change to menu fragment if round is gone/admin exit

        // Start round
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAdmin){
                    FireBaseRef.roundGameState(SharedPrefUtils.getGameId(getContext())).setValue(RoundState.SELECTING_TRACK);
                    navigationInterFace.changeFragment(SelectTrackFragment.newInstance(), false);
                } else {
                    Toast.makeText(getContext(), R.string.only_admin_can_start_game, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    private void checkIfYouAreAdmin() {
        FireBaseRef.roundAdmin(SharedPrefUtils.getGameId(getContext())).addListenerForSingleValueEvent(new ValueEventListener() {
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

        // if round state changes (ie admin has changed state, nav to SelectTrackFragment)
        FireBaseRef.roundGameState(SharedPrefUtils.getGameId(getActivity())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RoundState state = dataSnapshot.getValue(RoundState.class);
                if(state == RoundState.SELECTING_TRACK){
                    navigationInterFace.changeFragment(SelectTrackFragment.newInstance(), false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setUpListView() {

        PlayersCardAdapter adapter = new PlayersCardAdapter(
                getActivity(),
                User.class,
                R.layout.player_card,
                FireBaseRef.roundUsers(SharedPrefUtils.getGameId(getContext())), false, false);
        listView.setAdapter(adapter);
    }
}
