package se.rickylagerkvist.whotune.models.spotifyModels;

import java.util.ArrayList;

/**
 * Created by Ricky on 2016-11-20.
 */

public class TracksObject {

    public String href;
    public ArrayList<TrackObject> items;
    public int integer;
    public String next;
    public CursorObject cursors;
    public int total;
}
