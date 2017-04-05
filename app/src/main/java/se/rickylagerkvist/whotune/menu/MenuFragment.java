package se.rickylagerkvist.whotune.menu;


import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import se.rickylagerkvist.whotune.OLDCODE.view.LoginActivity;
import se.rickylagerkvist.whotune.gamesList.GamesFragment;
import se.rickylagerkvist.whotune.Main2Activity;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.login.FirebaseLogInActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    private Button btnCreateGame, btnJoinGame, btnLogOut;
    private DatabaseReference gamesRef;
    ImageView mInfoImage;

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

        btnCreateGame = (Button) rootView.findViewById(R.id.btn_create_game);
        btnJoinGame = (Button) rootView.findViewById(R.id.btn_join_game);
        btnLogOut = (Button) rootView.findViewById(R.id.btn_logout);
        mInfoImage = (ImageView) rootView.findViewById(R.id.iv_infoImage);

        gamesRef = FirebaseDatabase.getInstance().getReference("games");

        // create game
        btnCreateGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment dialog = CreateGameDialog.newInstance();
                dialog.show(getActivity().getFragmentManager(), "CreateGameDialog");
            }
        });

        // join game
        btnJoinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Main2Activity) getContext()).changeFragment(GamesFragment.newInstance(), true, null);
            }
        });

        // get info
        mInfoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Ho-hooo... How to play")
                        .setMessage("A minimum of 3 players each pick a song. The songs will be played in random order. The players then guess who picked what song. \n\nEasy enough right? Lets play to some sweet tunes!")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
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

        // TODO HighScore?

        return rootView;
    }

}
