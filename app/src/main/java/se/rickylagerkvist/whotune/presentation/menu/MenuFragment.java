package se.rickylagerkvist.whotune.presentation.menu;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import se.rickylagerkvist.whotune.presentation.showRounds.GamesFragment;
import se.rickylagerkvist.whotune.MainActivity;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.presentation.login.FirebaseLogInActivity;
import se.rickylagerkvist.whotune.utils.DialogHelpers;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    public MenuFragment() {
        // Required empty public constructor
    }

    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        Button btnCreateGame = (Button) rootView.findViewById(R.id.btn_create_game);
        Button btnJoinGame = (Button) rootView.findViewById(R.id.btn_join_game);
        Button btnLogOut = (Button) rootView.findViewById(R.id.btn_logout);
        ImageView mInfoImage = (ImageView) rootView.findViewById(R.id.iv_infoImage);

        // create round
        btnCreateGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = CreateGameDialog.newInstance();
                dialog.show(getActivity().getFragmentManager(), "CreateGameDialog");
            }
        });

        // join round
        btnJoinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).changeFragment(GamesFragment.newInstance(), true);
            }
        });

        // get info
        mInfoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogHelpers.showInfoDialog(getContext());
            }
        });

        // logout
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), FirebaseLogInActivity.class));
            }
        });


        return rootView;
    }

}
