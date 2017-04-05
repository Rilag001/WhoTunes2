package se.rickylagerkvist.whotune.OLDCODE.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.data.GuessOrAnswer;

/**
 * Created by Ricky on 2016-12-04.
 */

public class AnswerCardAdapter extends ArrayAdapter<GuessOrAnswer> {

    public AnswerCardAdapter(Context context, ArrayList<GuessOrAnswer> tracks) {
        super(context, 0, tracks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        GuessOrAnswer model = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.track_card, parent, false);
        }

        // Lookup view for data population
        TextView playerName = (TextView) convertView.findViewById(R.id.name_of_player_textView);
        ImageView coverArt = (ImageView) convertView.findViewById(R.id.track_card_imageView);
        TextView trackText = (TextView) convertView.findViewById(R.id.trackTextView);
        TextView albumText = (TextView) convertView.findViewById(R.id.albumTextView);
        // Populate the data into the template view using the data object
        playerName.setVisibility(View.VISIBLE);
        playerName.setText(model.getUserName() + " picked");
        Glide.with(getContext()).load(model.getSelectedTrack().getAlbum().getImages().get(0).url).into(coverArt);
        trackText.setText(model.getSelectedTrack().getName());
        albumText.setText(model.getSelectedTrack().getAlbum().getName());
        // Return the completed view to render on screen
        return convertView;
    }
}
