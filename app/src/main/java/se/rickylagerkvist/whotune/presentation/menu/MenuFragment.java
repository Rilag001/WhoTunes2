package se.rickylagerkvist.whotune.presentation.menu;


import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;
import se.rickylagerkvist.whotune.MainActivityNavigationInterFace;
import se.rickylagerkvist.whotune.presentation.showRounds.RoundsFragment;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.presentation.login.FirebaseLogInActivity;
import se.rickylagerkvist.whotune.utils.DialogHelpers;
import se.rickylagerkvist.whotune.utils.SharedPrefUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    MainActivityNavigationInterFace navigationInterFace;

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
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        Button btnCreateGame = (Button) rootView.findViewById(R.id.btn_create_game);
        Button btnJoinGame = (Button) rootView.findViewById(R.id.btn_join_game);
        Button btnLogOut = (Button) rootView.findViewById(R.id.btn_logout);
        ImageView mInfoImage = (ImageView) rootView.findViewById(R.id.iv_infoImage);
        CircleImageView profileIv = (CircleImageView) rootView.findViewById(R.id.profile_pic);

        Glide.with(this).load(SharedPrefUtils.getSpotifyProfilePic(getContext())).into(profileIv);

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
                navigationInterFace.changeFragment(RoundsFragment.newInstance(),true);
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
