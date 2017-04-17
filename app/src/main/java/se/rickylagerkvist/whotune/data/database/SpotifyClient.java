package se.rickylagerkvist.whotune.data.database;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Ricky on 2016-11-18.
 */

public class SpotifyClient {

    private static final String BASE_URL = "https://api.spotify.com/v1";

    public static final String SEARCH_TRACK_URL = "/search?q={search}&type=track&limit=20";
    public static String CREATE_PLAYLIST_URL = "/users/{user_id}/playlists";
    public static String GET_USER = "/me";

    public static String AUTH_TOKEN;

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

//    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        client.addHeader("Authorization", AUTH_TOKEN);
//        client.post(getAbsoluteUrl(url), params, responseHandler);
//    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
