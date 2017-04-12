package se.rickylagerkvist.whotune.presentation.results;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.rickylagerkvist.whotune.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultsFragment extends Fragment implements ResultsPresenter.View {

    private ResultsPresenter presenter;

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

        presenter = new ResultsPresenter(this);

        return rootView;
    }

}
