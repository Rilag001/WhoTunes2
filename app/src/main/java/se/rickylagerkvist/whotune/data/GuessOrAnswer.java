package se.rickylagerkvist.whotune.data;

import se.rickylagerkvist.whotune.data.SpotifyData.Track;

/**
 * Created by Ricky on 2016-12-04.
 */

public class GuessOrAnswer {

    private String UserName;
    private Track SelectedTrack;

    public GuessOrAnswer(String userName, Track selectedTrack){
        UserName = userName;
        SelectedTrack = selectedTrack;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Track getSelectedTrack() {
        return SelectedTrack;
    }

    public void setSelectedTrack(Track selectedTrack) {
        SelectedTrack = selectedTrack;
    }
}
