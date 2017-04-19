package se.rickylagerkvist.whotune.data.model.whoTune;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by rickylagerkvist on 2017-04-04.
 */

public class WhoTuneRound {

    private String name;
    private Admin admin;
    private Date createdDate;
    private HashMap<String, User> players;
    private ArrayList<UsersTrack> playList;
    private RoundState gameState;

    public WhoTuneRound() {
    }

    public WhoTuneRound(String name, Admin admin, Date createdDate, HashMap<String, User> players, ArrayList<UsersTrack> playList, RoundState gameState) {
        this.name = name;
        this.admin = admin;
        this.createdDate = createdDate;
        this.players = players;
        this.playList = playList;
        this.gameState = gameState;
    }

    // getters
    public String getName() {
        return name;
    }

    public Admin getAdmin() {
        return admin;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public HashMap<String, User> getPlayers() {
        return players;
    }

    public ArrayList<UsersTrack> getPlayList() {
        return playList;
    }

    public RoundState getGameState() {
        return gameState;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setPlayers(HashMap<String, User> players) {
        this.players = players;
    }

    public void setPlayList(ArrayList<UsersTrack> playList) {
        this.playList = playList;
    }

    public void setGameState(RoundState gameState) {
        this.gameState = gameState;
    }
}
