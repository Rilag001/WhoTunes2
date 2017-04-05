package se.rickylagerkvist.whotune.selectTrack;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.rickylagerkvist.whotune.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectTrackFragment extends Fragment {


    public SelectTrackFragment() {
        // Required empty public constructor
    }

    public static SelectTrackFragment newInstance() {
        SelectTrackFragment fragment = new SelectTrackFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_select_track_fragment, container, false);
        return rootView;
    }

}
