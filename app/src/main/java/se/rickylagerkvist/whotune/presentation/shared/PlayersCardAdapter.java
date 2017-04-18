package se.rickylagerkvist.whotune.presentation.shared;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.Query;

import de.hdodenhof.circleimageview.CircleImageView;
import se.rickylagerkvist.whotune.data.model.User;
import se.rickylagerkvist.whotune.R;

/**
 * Created by rickylagerkvist on 2017-04-04.
 */

public class PlayersCardAdapter extends FirebaseListAdapter<User> {

    private boolean mSelectedTrackCheck, mShowResults;

    public PlayersCardAdapter(Activity activity, Class<User> modelClass, int modelLayout, Query ref, boolean selectedTrackCheck, boolean showResults) {
        super(activity, modelClass, modelLayout, ref);

        mSelectedTrackCheck = selectedTrackCheck;
        mShowResults = showResults;
    }

    @Override
    protected void populateView(View v, User model, int position) {

        // views
        ConstraintLayout rootLayout = (ConstraintLayout) v.findViewById(R.id.cl_player_view);
        TextView name = (TextView) v.findViewById(R.id.tv_player_name);
        CircleImageView profilePic = (CircleImageView) v.findViewById(R.id.player_circleIV);
        TextView selectedTrack = (TextView) v.findViewById(R.id.tv_pickedTrack);
        ImageView selectedTrackImage = (ImageView) v.findViewById(R.id.im_picked_track_image);

        // mode SelectedTrackFragment
        if(mSelectedTrackCheck){
            if(model.getSelectedTrack() == null){
                rootLayout.setVisibility(View.GONE);
            } else {
                rootLayout.setVisibility(View.VISIBLE);
            }
        }

        // mode ResultsFragment
        if(mShowResults){
            selectedTrack.setVisibility(View.VISIBLE);
            selectedTrackImage.setVisibility(View.VISIBLE);

            selectedTrack.setText("Selected track " + model.getSelectedTrack().getName() + "\nby " + model.getSelectedTrack().getAllArtistAsJoinedString());
            Glide.with(v.getContext()).load(model.getSelectedTrack().getSmallCoverArt()).into(selectedTrackImage);
        }

        name.setText(model.getName());
        Glide.with(v.getContext()).load(model.getProfilePicUrl()).into(profilePic);

    }
}
