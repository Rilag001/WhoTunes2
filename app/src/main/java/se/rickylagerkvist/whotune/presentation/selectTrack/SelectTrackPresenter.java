package se.rickylagerkvist.whotune.presentation.selectTrack;

/**
 * Created by rickylagerkvist on 2017-04-05.
 */

import org.json.JSONException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.rickylagerkvist.whotune.data.database.ApiUtils;
import se.rickylagerkvist.whotune.data.database.SpotifyService;
import se.rickylagerkvist.whotune.data.model.spotify.tracks.Track;
import se.rickylagerkvist.whotune.data.model.spotify.tracks.TrackList;

public class SelectTrackPresenter {

    private View view;
    private SpotifyService spotifyService;

    public SelectTrackPresenter(View view) {
        this.view = view;

        spotifyService = ApiUtils.getSpotifyService();
    }

    void searchTrack(String searchText) throws JSONException {

        if(!view.searchEditText().isEmpty()){

            Call<TrackList> trackList = spotifyService.searchTracks(searchText.replace(" ", "+"), "track", "20");
            trackList.enqueue(new Callback<TrackList>() {
                @Override
                public void onResponse(Call<TrackList> call, Response<TrackList> response) {
                    List<Track> tracks = response.body().getTracks().getItems();
                    view.updateList(tracks);
                    view.toggleNoTrackFoundLayout(tracks.isEmpty());
                }

                @Override
                public void onFailure(Call<TrackList> call, Throwable t) {

                }
            });

        } else {
            view.showSnackBarSearchIsEmpty();
        }
    }


    public interface View {
        void updateList(List<Track> list);
        void toggleNoTrackFoundLayout(boolean isTracksEmpty);
        String searchEditText();
        void showSnackBarSearchIsEmpty();
    }
}
