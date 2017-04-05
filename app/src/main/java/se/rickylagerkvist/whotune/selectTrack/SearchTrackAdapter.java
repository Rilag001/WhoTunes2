package se.rickylagerkvist.whotune.selectTrack;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.data.SpotifyData.Track;

/**
 * Created by rickylagerkvist on 2017-04-05.
 */

public class SearchTrackAdapter extends RecyclerView.Adapter<SearchTrackAdapter.MyViewHolder> {

    private List<Track> tracks;

    public SearchTrackAdapter(List<Track> tracks) {
        this.tracks = tracks;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.track_card_imageView)
        ImageView coverArt;
        @BindView(R.id.trackTextView) TextView trackText;
        @BindView(R.id.albumTextView) TextView albumText;
        @BindView(R.id.artistTextView)
        TextView artistText;
        @BindView(R.id.track_card_layout)
        FrameLayout trackLayout;

        MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public SearchTrackAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View ItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_card, parent, false);
        return new MyViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(SearchTrackAdapter.MyViewHolder holder, int position) {

        final Track track = tracks.get(position);

        // TrackText
        holder.trackText.setText(track.getName());

        // ArtistText
        holder.artistText.setText(track.getAllArtistAsJoinedString());

        // AlbumText
        holder.albumText.setText(track.getAlbum().getName());

        // CoverArt
        Glide.with(holder.coverArt.getContext()).load(track.getSmallCoverArt()).into(holder.coverArt);

        holder.trackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO change fragment
            }
        });
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    void update(List<Track> modelList){
        tracks.clear();
        tracks.addAll(modelList);
        notifyDataSetChanged();
    }
}
