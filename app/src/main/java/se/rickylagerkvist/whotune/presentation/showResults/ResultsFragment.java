package se.rickylagerkvist.whotune.presentation.showResults;


import android.app.DialogFragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import se.rickylagerkvist.whotune.MainActivity;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.data.database.FireBaseRef;
import se.rickylagerkvist.whotune.data.model.whoTune.Admin;
import se.rickylagerkvist.whotune.data.model.whoTune.User;
import se.rickylagerkvist.whotune.data.model.whoTune.WhoTuneRound;
import se.rickylagerkvist.whotune.presentation.menu.MenuFragment;
import se.rickylagerkvist.whotune.presentation.shared.PlayersCardAdapter;
import se.rickylagerkvist.whotune.utils.SharedPrefUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultsFragment extends Fragment {

    private boolean isAdmin;
    private String roundName;

    public ResultsFragment() {
        // Required empty public constructor
    }

    public static ResultsFragment newInstance(){
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_results, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.lv_results);
        DatabaseReference playersRef = FireBaseRef.round(SharedPrefUtils.getGameId(getContext())).child("players");
        PlayersCardAdapter adapter = new PlayersCardAdapter(getActivity(), User.class, R.layout.player_card, playersRef, false, true);
        listView.setAdapter(adapter);

        checkIfYouAreAdmin();
        gameListener();

        // end game
        Button btnEndGame = (Button) rootView.findViewById(R.id.btn_results_end_game);
        btnEndGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isAdmin){
                    FireBaseRef.round(SharedPrefUtils.getGameId(getContext())).removeValue();
                }
                ((MainActivity)getContext()).changeFragment(MenuFragment.newInstance(), false);
            }
        });

        // post playlist
        ImageButton btnPostPlayList = (ImageButton) rootView.findViewById(R.id.btn_post_playlist);
        btnPostPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //postPlaylist();
                DialogFragment dialog = PostPlayListDialog.newInstance(roundName);
                dialog.show(getActivity().getFragmentManager(), "PostPlayListDialog");
            }
        });

        return rootView;
    }

    private void gameListener() {
        FireBaseRef.round(SharedPrefUtils.getGameId(getContext())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // end game
                if(!dataSnapshot.exists()){
                    try{
                        ((MainActivity)getContext()).changeFragment(MenuFragment.newInstance(), false);
                    } catch (Exception e){
                        Log.e("ResultsFragment", e.toString());
                    }
                } else {
                    WhoTuneRound round = dataSnapshot.getValue(WhoTuneRound.class);

                    // get name
                    roundName = round.getName();

//                    // get tracks
//                    ArrayList<Track> trackList = new ArrayList<>();
//
//                    HashMap<String,User> players = round.getPlayers();
//                    for (User value : players.values()){
//                        trackList.add(value.getSelectedTrack());
//                    }
//                    tracks = new Tracks(trackList);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkIfYouAreAdmin() {

        DatabaseReference adminRef = FireBaseRef.roundAdmin(SharedPrefUtils.getGameId(getContext()));
        adminRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Admin admin = dataSnapshot.getValue(Admin.class);
                isAdmin = admin.getUid().equals(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("USERUID", ""));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
