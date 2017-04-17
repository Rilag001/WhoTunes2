package se.rickylagerkvist.whotune.data.model;

/**
 * Created by rickylagerkvist on 2017-04-03.
 */

public class Profile {

    private String name;
    private String photoUri;

    public Profile() {
    }

    public Profile(String name, String photoUri) {
        this.name = name;
        this.photoUri = photoUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
