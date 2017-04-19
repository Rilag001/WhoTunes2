package se.rickylagerkvist.whotune.data.model.spotify.playList;


/**
 * Created by rickylagerkvist on 2017-04-19.
 */

public class PlayList {

    private String name;

    public PlayList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
