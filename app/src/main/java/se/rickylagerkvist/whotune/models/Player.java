package se.rickylagerkvist.whotune.models;

import java.util.ArrayList;

import se.rickylagerkvist.whotune.models.spotifyModels.TrackObject;

/**
 * Created by Ricky on 2016-11-20.
 */

public class Player {

    private String Name;
    private TrackObject selectedTrack;
    private ArrayList<GuessOrAnswer> GuessesList = new ArrayList<>();
    private int PointsForRightAnswer;

    public int getPointsForRightAnswer() {
        return PointsForRightAnswer;
    }

    public void setPointsForRightAnswer(int pointsForRightAnswer) {
        PointsForRightAnswer = pointsForRightAnswer;
    }

    public Player(String name) {
        Name = name;
    }

    // getters and setters
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public TrackObject getSelectedTrack() {
        return selectedTrack;
    }

    public void setSelectedTrack(TrackObject selectedTrack) {
        this.selectedTrack = selectedTrack;
    }

    public ArrayList<GuessOrAnswer> getGuessesList() {
        return GuessesList;
    }

    public void setGuessesList(ArrayList<GuessOrAnswer> guessesList) {
        GuessesList = guessesList;
    }

    // add/remove guess
    public void addGuess(GuessOrAnswer newGuess){
        GuessesList.add(newGuess);
    }

    public void removeGuess(GuessOrAnswer removeGuess){
        GuessesList.remove(removeGuess);
    }


}
