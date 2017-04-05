package se.rickylagerkvist.whotune.OLDCODE.models;

import java.util.ArrayList;
import java.util.Collections;

import se.rickylagerkvist.whotune.data.Player;

/**
 * Created by Ricky on 2016-11-20.
 */

public class Round {

    private ArrayList<Player> players = new ArrayList<>();

    public ArrayList<Player> getPlayers() {
        return players;
    }
    public Round() {
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Round(ArrayList<Player> players) {
        this.players = players;
    }

    public void shuffle(){
        Collections.shuffle(players);
    }

    public void addPlayer(Player newPlayer){
        players.add(newPlayer);
    }

    public ArrayList<GuessOrAnswer> getUserSelections(){

        ArrayList<GuessOrAnswer> userSelectionsList = new ArrayList<>();

        for (Player player : players)
        {
            GuessOrAnswer g = new GuessOrAnswer(player.getName(), player.getSelectedTrack());
            userSelectionsList.add(g);
        }
        return  userSelectionsList;
    }
}
