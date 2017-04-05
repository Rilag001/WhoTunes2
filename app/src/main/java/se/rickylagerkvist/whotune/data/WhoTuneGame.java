package se.rickylagerkvist.whotune.data;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by rickylagerkvist on 2017-04-04.
 */

public class WhoTuneGame {

    private String name;
    private Admin admin;
    private Date createdDate;
    private ArrayList<Player> players;
    private ArrayList<String> playList;
    private GameState gameState;

    public WhoTuneGame() {
    }

    public WhoTuneGame(String name, Admin admin, Date createdDate, ArrayList<Player> players, ArrayList<String> playList, GameState gameState) {
        this.name = name;
        this.admin = admin;
        this.createdDate = createdDate;
        this.players = players;
        this.playList = playList;
        this.gameState = gameState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<String> getPlayList() {
        return playList;
    }

    public void setPlayList(ArrayList<String> playList) {
        this.playList = playList;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
