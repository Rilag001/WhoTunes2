package se.rickylagerkvist.whotune.OLDCODE.models;

import se.rickylagerkvist.whotune.data.SpotifyData.TrackObject;

/**
 * Created by Ricky on 2016-12-04.
 */

public class GuessOrAnswer {

    private String UserName;
    private TrackObject SelectedTrack;

    public GuessOrAnswer(String userName, TrackObject selectedTrack){
        UserName = userName;
        SelectedTrack = selectedTrack;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public TrackObject getSelectedTrack() {
        return SelectedTrack;
    }

    public void setSelectedTrack(TrackObject selectedTrack) {
        SelectedTrack = selectedTrack;
    }
}
