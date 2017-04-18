package se.rickylagerkvist.whotune.presentation.showRounds;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.data.database.FireBaseRef;
import se.rickylagerkvist.whotune.data.model.WhoTuneRound;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoundsFragment extends Fragment {

    ImageView ivExit;

    public RoundsFragment() {
        // Required empty public constructor
    }

    public static RoundsFragment newInstance() {
        RoundsFragment fragment = new RoundsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_games, container, false);

        ListView list = (ListView) rootView.findViewById(R.id.lw_games);
        final RoundsCardAdapter gamesCardAdapter = new RoundsCardAdapter(getActivity(), WhoTuneRound.class, R.layout.games_card, FireBaseRef.rounds);
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
