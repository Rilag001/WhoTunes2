package se.rickylagerkvist.whotune.data.model;

import java.util.ArrayList;

import se.rickylagerkvist.whotune.data.model.SpotifyData.Track;

/**
 * Created by Ricky on 2016-11-20.
 */

public class User {

    private String Name;
    private String profilePicUrl;
    private String Uri;
    private Track selectedTrack;

    public User() {
    }

    public User(String name, String profilePicUrl, String uri) {
        Name = name;
        this.profilePicUrl = profilePicUrl;
        Uri = uri;
    }


    public User(String name) {
        Name = name;
    }

    // getters and setters
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Track getSelectedTrack() {
        return selectedTrack;
    }

    public void setSelectedTrack(Track selectedTrack) {
        this.selectedTrack = selectedTrack;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getUri() {
        return Uri;
    }

    public void setUri(String uri) {
        Uri = uri;
    }
}
