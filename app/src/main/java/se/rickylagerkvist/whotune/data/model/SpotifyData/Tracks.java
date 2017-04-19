package se.rickylagerkvist.whotune.data.model.SpotifyData;

import java.util.ArrayList;

/**
 * Created by Ricky on 2016-11-20.
 */

public class Tracks {

    private ArrayList<Track> items;

    public Tracks(ArrayList<Track> items) {
        this.items = items;
    }

    public ArrayList<Track> getItems() {
        return items;
    }

    public void setItems(ArrayList<Track> items) {
        this.items = items;
    }
}
