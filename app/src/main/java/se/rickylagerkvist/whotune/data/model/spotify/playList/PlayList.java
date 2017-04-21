package se.rickylagerkvist.whotune.data.model.spotify.playList;


import se.rickylagerkvist.whotune.data.model.spotify.tracks.Tracks;

/**
 * Created by rickylagerkvist on 2017-04-19.
 */

public class PlayList {

    private String name;
    private Tracks tracks;

    public PlayList(String name, Tracks tracks) {
        this.name = name;
        this.tracks = tracks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }
}
