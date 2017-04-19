package se.rickylagerkvist.whotune.data.database;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import se.rickylagerkvist.whotune.data.model.spotify.playList.PlayList;
import se.rickylagerkvist.whotune.data.model.spotify.profile.SpotifyProfile;
import se.rickylagerkvist.whotune.data.model.spotify.tracks.TrackList;

/**
 * Created by rickylagerkvist on 2017-04-18.
 */

public interface SpotifyService {

    @GET("search")
    Call<TrackList> getTracks(@Query("q") String search, @Query("type") String type, @Query("limit") String limit);

    @GET("me")
    Call<SpotifyProfile> getSpotifyProfile(@Header("Authorization") String authorization);

    @POST("users/{user_id}/playlists")
    Call<PlayList> postPlayList(
            @Path("user_id") String userId,
            @Body PlayList playlist,
            @Header("Authorization") String authorization,
            @Header("Content-Type") String contentType);

}
