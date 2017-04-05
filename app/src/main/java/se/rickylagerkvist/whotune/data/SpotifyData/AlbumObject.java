package se.rickylagerkvist.whotune.data.SpotifyData;

import java.util.List;

/**
 * Created by Ricky on 2016-11-20.
 */

public class AlbumObject {

    public String album_type;
    public List<ArtistObjects> artists;
    public List<String> available_markets;
    public List<CopyrightObjects> copyrights;
    public List<ExternalIDobject> external_ids;
    public List<ExternalURLobject> external_urls;
    public List<String> genres;
    public String href;
    public String id;
    public List<ImageObjects> images;
    public String label;
    public String name;
    public int popularity;
    public String release_date;
    public String release_date_precision;
    public Object tracks; //
    public String type;
    public String uri;
}
