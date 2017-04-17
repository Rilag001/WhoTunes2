package se.rickylagerkvist.whotune.presentation.showRounds;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.Query;

import de.hdodenhof.circleimageview.CircleImageView;
import se.rickylagerkvist.whotune.MainActivity;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.data.database.FireBaseRef;
import se.rickylagerkvist.whotune.data.model.GameState;
import se.rickylagerkvist.whotune.data.model.User;
import se.rickylagerkvist.whotune.data.model.WhoTuneRound;
import se.rickylagerkvist.whotune.presentation.showPlayersInRound.PlayersInGameFragment;
import se.rickylagerkvist.whotune.utils.SharedPrefUtils;
import se.rickylagerkvist.whotune.utils.ConvertAndFormatHelpers;

/**
 * Created by rickylagerkvist on 2017-04-04.
 */

public class GamesCardAdapter extends FirebaseListAdapter<WhoTuneRound> {

    public GamesCardAdapter(Activity activity, Class<WhoTuneRound> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    @Override
    protected void populateView(View v, final WhoTuneRound model, final int position) {

        TextView name = (TextView) v.findViewById(R.id.tv_name);
        CircleImageView profilePic = (CircleImageView) v.findViewById(R.id.circleIV);
        TextView createdBy = (TextView) v.findViewById(R.id.tv_created_by);
        TextView createdDate = (TextView) v.findViewById(R.id.tv_created_date);
        TextView join =(TextView) v.findViewById(R.id.tv_join_game);

        if(model.getGameState().equals(GameState.OPEN)){
            name.setText(model.getName());
            Glide.with(v.getContext()).load(model.getAdmin().getCreatorProfilePicUrl()).into(profilePic);
            createdBy.setText(v.getResources().getString(R.string.game_created_by, model.getAdmin().getCreatorName()));

            createdDate.setText(ConvertAndFormatHelpers.SIMPLE_DATE_FORMAT.format(model.getCreatedDate()));

            join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String gameId = GamesCardAdapter.this.getRef(position).getKey();
                    SharedPrefUtils.saveGameId(gameId, v.getContext());

                    // create player and save in this round
                    String photoUrl = SharedPrefUtils.getPhotoUrl(v.getContext());
                    String displayName = SharedPrefUtils.getDisplayName(v.getContext());
                    String userUid = SharedPrefUtils.getUid(v.getContext());

                    User player = new User(displayName, photoUrl, userUid);

                    model.getPlayers().put(SharedPrefUtils.getUid(v.getContext()), player);

                    // save to FireBase
                    FireBaseRef.round(gameId).setValue(model);

                    // save key, bundle to PlayersInGameFragment
                    Bundle bundle = new Bundle();
                    bundle.putString("GAME_ID", gameId);

                    ((MainActivity) v.getContext()).changeFragment(PlayersInGameFragment.newInstance(), true);
                }
            });

        } else {
            v.setVisibility(View.GONE);
        }


    }
}
