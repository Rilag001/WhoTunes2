package se.rickylagerkvist.whotune.data.model.whoTune;

/**
 * Created by Ricky on 2016-12-04.
 */

public class UsersTrack {

    private String userUid;
    private String trackUri;

    public UsersTrack(String userUid, String trackUri) {
        this.userUid = userUid;
        this.trackUri = trackUri;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getTrackUri() {
        return trackUri;
    }

    public void setTrackUri(String trackUri) {
        this.trackUri = trackUri;
    }
}
