package se.rickylagerkvist.whotune.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by rickylagerkvist on 2017-04-04.
 */

public class WhoTuneGame {

    private String name;
    private Admin admin;
    private Date createdDate;
    private HashMap<String, Player> players;
    private ArrayList<GuessOrAnswer> playList;
    private GameState gameState;

    public WhoTuneGame() {
    }

    public WhoTuneGame(String name, Admin admin, Date createdDate, HashMap<String, Player> players, ArrayList<GuessOrAnswer> playList, GameState gameState) {
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

    public HashMap<String, Player> getPlayers() {
        return players;
    }

    public ArrayList<GuessOrAnswer> getPlayList() {
        return playList;
    }

    public GameState getGameState() {
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

    public void setPlayers(HashMap<String, Player> players) {
        this.players = players;
    }

    public void setPlayList(ArrayList<GuessOrAnswer> playList) {
        this.playList = playList;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    // get trackUri
    public ArrayList<String> getShuffledPlayListUri(){
        ArrayList<String> list = new ArrayList<>();
        if(playList != null){
            for (GuessOrAnswer model : playList) {
                list.add(model.getTrackUri());
            }
            Collections.shuffle(list);
        }
        return list;
    }
}
