package se.rickylagerkvist.whotune.presentation.playersInGame;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.Query;

import de.hdodenhof.circleimageview.CircleImageView;
import se.rickylagerkvist.whotune.data.model.Player;
import se.rickylagerkvist.whotune.R;

/**
 * Created by rickylagerkvist on 2017-04-04.
 */

public class PlayersCardAdapter extends FirebaseListAdapter<Player> {

    public PlayersCardAdapter(Activity activity, Class<Player> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    @Override
    protected void populateView(View v, Player model, int position) {

        TextView name = (TextView) v.findViewById(R.id.tv_player_name);
        CircleImageView profilePic = (CircleImageView) v.findViewById(R.id.player_circleIV);

        name.setText(model.getName());
        Glide.with(v.getContext()).load(model.getProfilePicUrl()).into(profilePic);

    }
}
