package se.rickylagerkvist.whotune.presentation.results;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import se.rickylagerkvist.whotune.MainActivity;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.data.database.FireBaseRef;
import se.rickylagerkvist.whotune.data.model.Admin;
import se.rickylagerkvist.whotune.data.model.User;
import se.rickylagerkvist.whotune.presentation.menu.MenuFragment;
import se.rickylagerkvist.whotune.presentation.shared.PlayersCardAdapter;
import se.rickylagerkvist.whotune.utils.SharedPrefUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultsFragment extends Fragment {

    private ListView listView;
    private PlayersCardAdapter adapter;
    private DatabaseReference playersRef, adminRef;
    private Button btnEndGame;
    private boolean isAdmin;

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

        listView = (ListView) rootView.findViewById(R.id.lv_results);
        playersRef = FireBaseRef.round(SharedPrefUtils.getGameId(getContext())).child("players");
        adapter = new PlayersCardAdapter(getActivity(), User.class, R.layout.player_card, playersRef, false, true);
        listView.setAdapter(adapter);

        checkIfYouAreAdmin();
        gameListener();

        btnEndGame = (Button) rootView.findViewById(R.id.btn_results_end_game);
        btnEndGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isAdmin){
                    FireBaseRef.round(SharedPrefUtils.getGameId(getContext())).removeValue();
                }
                ((MainActivity)getContext()).changeFragment(MenuFragment.newInstance(), false);
            }
        });

        return rootView;
    }

    private void gameListener() {
        FireBaseRef.round(SharedPrefUtils.getGameId(getContext())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    ((MainActivity)getContext()).changeFragment(MenuFragment.newInstance(), false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkIfYouAreAdmin() {

        adminRef = FireBaseRef.roundAdmin(SharedPrefUtils.getGameId(getContext()));
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
