package se.rickylagerkvist.whotune.data.model.spotify.profile;

import java.util.ArrayList;

import se.rickylagerkvist.whotune.data.model.spotify.tracks.Image;

/**
 * Created by rickylagerkvist on 2017-04-19.
 */

public class SpotifyProfile {

    private String id;
    private String display_name;
    private ArrayList<Image> images;

    // getters
    public String getId() {
        return id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    // setters
    public void setId(String id) {
        this.id = id;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    // full object on https://developer.spotify.com/web-api/get-current-users-profile/
}
