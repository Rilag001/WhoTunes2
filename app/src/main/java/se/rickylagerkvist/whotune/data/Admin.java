package se.rickylagerkvist.whotune.data;

/**
 * Created by rickylagerkvist on 2017-04-05.
 */

public class Admin {

    private String creatorProfilePicUrl;
    private String creatorName;
    private String Uid;

    public Admin() {
    }

    public Admin(String creatorProfilePicUrl, String creatorName, String uid) {
        this.creatorProfilePicUrl = creatorProfilePicUrl;
        this.creatorName = creatorName;
        Uid = uid;
    }

    public String getCreatorProfilePicUrl() {
        return creatorProfilePicUrl;
    }

    public void setCreatorProfilePicUrl(String creatorProfilePicUrl) {
        this.creatorProfilePicUrl = creatorProfilePicUrl;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
