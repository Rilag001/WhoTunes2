package se.rickylagerkvist.whotune.selectTrack;

/**
 * Created by rickylagerkvist on 2017-04-05.
 */

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import se.rickylagerkvist.whotune.client.SpotifyClient;
import se.rickylagerkvist.whotune.data.SpotifyData.Track;
import se.rickylagerkvist.whotune.data.SpotifyData.TrackList;

public class SelectTrackPresenter {

    View view;

    public SelectTrackPresenter(View view) {
        this.view = view;
    }

    void searchTrack(String searchText) throws JSONException {

        if(!view.searchEditText().isEmpty()){

            String newSearchText = SpotifyClient.SEARCH_TRACK_URL.replace("{search}", searchText.replace(" ", "+"));

            SpotifyClient.get(newSearchText, null, new JsonHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Log.d("asd", "---------------- this is response : " + response);

                    TrackList tacksList = JSON.parseObject(response.toString(), TrackList.class);

                    List<Track> tracks = tacksList.getTracks().getItems();
                    view.updateList(tracks);
                    view.toggleNoTrackFoundLayout(tracks.isEmpty());
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {

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
