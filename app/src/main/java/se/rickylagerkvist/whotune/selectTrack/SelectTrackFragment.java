package se.rickylagerkvist.whotune.selectTrack;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import se.rickylagerkvist.whotune.MainActivity.Main2Activity;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.data.SpotifyData.Track;
import se.rickylagerkvist.whotune.playersHasSelectedTrack.PlayersHasSelectedTrackFragment;
import se.rickylagerkvist.whotune.playersInGame.PlayersInGameFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectTrackFragment extends Fragment implements SelectTrackPresenter.View, SearchTrackAdapter.SearchTrackAdapterInterFace{

    //member variables
    private SelectTrackPresenter presenter;
    private List<Track> tracks = new ArrayList<>();
    private SearchTrackAdapter adapter;
    private RecyclerView recyclerView;
    private EditText edtSearch;
    private RelativeLayout mainLayout;
    private ImageButton searchButton;
    private TextView tvTrack, tvAlbum;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout selectedTrackStack;
    private ImageView ivTrackCard;
    private Button btnSelectTrack;
    private Track selectedTrack;
    //end region


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

        // set presenter
        presenter = new SelectTrackPresenter(this);

        // set adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_trackList);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new SearchTrackAdapter(tracks, this);
        recyclerView.setAdapter(adapter);

        // layouts
        mainLayout = (RelativeLayout) rootView.findViewById(R.id.rl_select_track);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        searchButton = (ImageButton) rootView.findViewById(R.id.searchButton);
        edtSearch = (EditText) rootView.findViewById(R.id.searchEditText);
        selectedTrackStack = (RelativeLayout) rootView.findViewById(R.id.selectedTrackStack);
        ivTrackCard = (ImageView) rootView.findViewById(R.id.iv_track_card);
        tvTrack = (TextView) rootView.findViewById(R.id.tv_track);
        tvAlbum = (TextView) rootView.findViewById(R.id.tv_album);
        btnSelectTrack = (Button) rootView.findViewById(R.id.btn_selectTrack);

        // search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchForTracks();
            }
        });

        // search on editorAction
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    SearchForTracks();
                    return true;
                }
                return false;
            }
        });

        // SwipeRefresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SearchForTracks();
            }
        });

        //
        btnSelectTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedTrack != null){
                    Bundle bundle = new Bundle();
                    ((Main2Activity) v.getContext()).changeFragment(PlayersHasSelectedTrackFragment.newInstance(), true, bundle);
                }
            }
        });

        return rootView;
    }

    private void SearchForTracks(){

        try{
            presenter.searchTrack(searchEditText());
        } catch (JSONException e){
            Log.e(getActivity().getClass().getSimpleName(), e.toString());
        }

        swipeRefreshLayout.setRefreshing(false);

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(
                Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.RESULT_HIDDEN, 0);
    }

    @Override
    public void updateList(List<Track> list) {
        adapter.update(list);
    }

    @Override
    public void toggleNoTrackFoundLayout(boolean isTracksEmpty) {
        if(isTracksEmpty){
            Snackbar snackbar = Snackbar.make(mainLayout, "No Tracks found", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    public String searchEditText() {
        return edtSearch.getText().toString();
    }

    @Override
    public void showSnackBarSearchIsEmpty() {
        Snackbar snackbar = Snackbar.make(mainLayout, "Search is empty!", Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    // set selected track UI
    public void setSelectedTrackUI(Track track) {

        Glide.with(this).load(track.getAlbum().getImages().get(0).url).into(ivTrackCard);
        tvTrack.setText(track.getName());
        tvAlbum.setText(track.getAlbum().getName());

        if(selectedTrackStack.getVisibility() == View.INVISIBLE){
            animateUp();
        }

        selectedTrack = track;
    }
    private void animateUp(){
        Animation bottomUp = AnimationUtils.loadAnimation(getContext(),
                R.anim.bottom_up);
        selectedTrackStack.startAnimation(bottomUp);
        selectedTrackStack.setVisibility(View.VISIBLE);
    }

}
