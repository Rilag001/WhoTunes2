package se.rickylagerkvist.whotune.models.spotifyModels;

import java.util.ArrayList;

/**
 * Created by Ricky on 2016-11-20.
 */

public class TrackObject {

    public AlbumObject album;
    public ArrayList<ArtistObjects> artists;
    public ArrayList<String> available_markets;
    public int disc_number;
    public int duration_ms;
    public boolean explicit;
    public ExternalIDobject external_ids;
    public ExternalURLobject external_urls;
    public String href;
    public String id;
    public String name;
    public int popularity;
    public String preview_url;
    public int track_number;
    public String type;
    public String uri;
}
