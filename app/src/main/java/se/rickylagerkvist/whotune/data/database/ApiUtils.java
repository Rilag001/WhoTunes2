package se.rickylagerkvist.whotune.data.database;

import se.rickylagerkvist.whotune.WhoTuneApplication;
import se.rickylagerkvist.whotune.utils.SharedPrefUtils;

/**
 * Created by rickylagerkvist on 2017-04-19.
 */

public class ApiUtils {

    private ApiUtils() {}

    private static final String SPOTIFY_BASE_URL = "https://api.spotify.com/v1/";

    public static SpotifyService getSpotifyService(){
        return RetrofitClient.getClient(SPOTIFY_BASE_URL).create(SpotifyService.class);
    }
}


