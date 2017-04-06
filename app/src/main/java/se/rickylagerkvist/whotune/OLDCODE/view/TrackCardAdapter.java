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
import se.rickylagerkvist.whotune.data.SpotifyData.Track;

/**
 * Created by Ricky on 2016-11-20.
 */

public class TrackCardAdapter extends ArrayAdapter<Track> {

    public TrackCardAdapter(Context context, ArrayList<Track> tracks) {
        super(context, 0, tracks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Track track = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.track_card, parent, false);
        }
        // Lookup view for data population
        ImageView coverArt = (ImageView) convertView.findViewById(R.id.iv_track_card);
        TextView trackText = (TextView) convertView.findViewById(R.id.trackTextView);
        TextView albumText = (TextView) convertView.findViewById(R.id.albumTextView);
        // Populate the data into the template view using the data object
        Glide.with(getContext()).load(track.getAlbum().getImages().get(0).url).into(coverArt);
        trackText.setText(track.getName());
        albumText.setText(track.getAlbum().getName());
        // Return the completed view to render on screen
        return convertView;
    }

}
