package se.rickylagerkvist.whotune.presentation.showRounds;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.data.model.WhoTuneRound;

/**
 * A simple {@link Fragment} subclass.
 */
public class GamesFragment extends Fragment {

    ImageView ivExit;

    public GamesFragment() {
        // Required empty public constructor
    }

    public static GamesFragment newInstance() {
        GamesFragment fragment = new GamesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_games, container, false);

        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("rounds");
        ListView list = (ListView) rootView.findViewById(R.id.lw_games);
        final GamesCardAdapter gamesCardAdapter = new GamesCardAdapter(getActivity(), WhoTuneRound.class, R.layout.games_card, gamesRef);
        list.setAdapter(gamesCardAdapter);

        ivExit = (ImageView) rootView.findViewById(R.id.iv_close_games_fragment);
        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return rootView;
    }

}