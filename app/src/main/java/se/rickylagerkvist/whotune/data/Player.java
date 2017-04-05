package se.rickylagerkvist.whotune.data;

import java.util.ArrayList;

import se.rickylagerkvist.whotune.OLDCODE.models.GuessOrAnswer;
import se.rickylagerkvist.whotune.data.SpotifyData.TrackObject;

/**
 * Created by Ricky on 2016-11-20.
 */

public class Player {

    private String Name;
    private String profilePicUrl;
    private String Uri;
    private TrackObject selectedTrack;
    private ArrayList<GuessOrAnswer> GuessesList = new ArrayList<>();
    private int PointsForRightAnswer;

    public Player() {
    }

    public Player(String name, String profilePicUrl, String uri) {
        Name = name;
        this.profilePicUrl = profilePicUrl;
        Uri = uri;
    }

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

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getUri() {
        return Uri;
    }

    public void setUri(String uri) {
        Uri = uri;
    }
}
