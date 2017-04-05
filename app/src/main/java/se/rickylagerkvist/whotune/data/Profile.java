package se.rickylagerkvist.whotune.data;

/**
 * Created by rickylagerkvist on 2017-04-03.
 */

public class Profile {

    private String mName;
    private String mPhotoUri;
    private String mAboutMe;

    public Profile() {
    }

    public Profile(String mName, String mPhotoUri, String mAboutMe) {
        this.mName = mName;
        this.mPhotoUri = mPhotoUri;
        this.mAboutMe = mAboutMe;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhotoUri() {
        return mPhotoUri;
    }

    public void setmPhotoUri(String mPhotoUri) {
        this.mPhotoUri = mPhotoUri;
    }

    public String getmAboutMe() {
        return mAboutMe;
    }

    public void setmAboutMe(String mAboutMe) {
        this.mAboutMe = mAboutMe;
    }
}
