package se.rickylagerkvist.whotune.playersInGame;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.Query;

import de.hdodenhof.circleimageview.CircleImageView;
import se.rickylagerkvist.whotune.data.Player;
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

        TextView name = (TextView) v.findViewById(R.id.tv_name);
        CircleImageView profilePic = (CircleImageView) v.findViewById(R.id.circleIV);
        TextView createdBy = (TextView) v.findViewById(R.id.tv_created_by);
        TextView createdDate = (TextView) v.findViewById(R.id.tv_created_date);

        name.setText(model.getName());
        Glide.with(v.getContext()).load(model.getProfilePicUrl()).into(profilePic);
        createdBy.setText("");
        createdDate.setText("");

    }
}
