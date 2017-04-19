package se.rickylagerkvist.whotune.data.model.spotify.tracks;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by Ricky on 2016-11-20.
 */

public class Track {

    private Album album;
    private ArrayList<Artist> artists;
    private int duration_ms;
    private String id;
    private String name;
    private String uri;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public void setArtists(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    public int getDuration_ms() {
        return duration_ms;
    }

    public void setDuration_ms(int duration_ms) {
        this.duration_ms = duration_ms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getAllArtistAsJoinedString(){
        ArrayList<Artist> list = getArtists();
        ArrayList<String> nameList = new ArrayList<>();
        for (Artist model:list) {
            nameList.add(model.getName());
        }
        return TextUtils.join(", ", nameList);
    }

    public String getBigCoverArt(){
        return album.getImages().get(0).getUrl();
    }

    public String getSmallCoverArt(){
        return album.getImages().get(album.getImages().size() -1).getUrl();
    }
}
