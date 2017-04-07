package se.rickylagerkvist.whotune.gamesList;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.Query;

import de.hdodenhof.circleimageview.CircleImageView;
import se.rickylagerkvist.whotune.MainActivity.Main2Activity;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.client.FireBaseRef;
import se.rickylagerkvist.whotune.data.GameState;
import se.rickylagerkvist.whotune.data.Player;
import se.rickylagerkvist.whotune.data.WhoTuneGame;
import se.rickylagerkvist.whotune.playersInGame.PlayersInGameFragment;
import se.rickylagerkvist.whotune.utils.SharedPrefUtils;
import se.rickylagerkvist.whotune.utils.Utils;

/**
 * Created by rickylagerkvist on 2017-04-04.
 */

public class GamesCardAdapter extends FirebaseListAdapter<WhoTuneGame> {

    public GamesCardAdapter(Activity activity, Class<WhoTuneGame> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    @Override
    protected void populateView(View v, final WhoTuneGame model, final int position) {

        TextView name = (TextView) v.findViewById(R.id.tv_name);
        CircleImageView profilePic = (CircleImageView) v.findViewById(R.id.circleIV);
        TextView createdBy = (TextView) v.findViewById(R.id.tv_created_by);
        TextView createdDate = (TextView) v.findViewById(R.id.tv_created_date);
        TextView join =(TextView) v.findViewById(R.id.tv_join_game);

        if(model.getGameState().equals(GameState.OPEN)){
            name.setText(model.getName());
            Glide.with(v.getContext()).load(model.getAdmin().getCreatorProfilePicUrl()).into(profilePic);
            createdBy.setText(v.getResources().getString(R.string.game_created_by, model.getAdmin().getCreatorName()));

            createdDate.setText(Utils.SIMPLE_DATE_FORMAT.format(model.getCreatedDate()));

            join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String gameId = GamesCardAdapter.this.getRef(position).getKey();

                    // create player and save in this game
                    String photoUrl = SharedPrefUtils.getPhotoUrl(v.getContext());
                    String displayName = SharedPrefUtils.getDisplayName(v.getContext());
                    String userUid = SharedPrefUtils.getUid(v.getContext());

                    // TODO check that you are not added as a player, before adding
                    // add player to list
                    Player player = new Player(displayName, photoUrl, userUid);
                    model.addPlayerToPlayers(player);

                    // save to Firebase
                    FireBaseRef.game(gameId).setValue(model);

                    // save key, bundle to PlayersInGameFragment
                    Bundle bundle = new Bundle();
                    bundle.putString("GAME_ID", gameId);

                    ((Main2Activity) v.getContext()).changeFragment(PlayersInGameFragment.newInstance(), true, bundle);
                }
            });

        } else {
            v.setVisibility(View.GONE);
        }


    }
}
