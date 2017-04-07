package se.rickylagerkvist.whotune.client;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by rickylagerkvist on 2017-04-07.
 */

public class FireBaseRef {

    public static DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");
    public static DatabaseReference games = FirebaseDatabase.getInstance().getReference("games");

    // game
    public static DatabaseReference game(String id){
        return games.child(id);
    }

    public static DatabaseReference gameAdmin(String id){
        return games.child(id).child("admin");
    }

    public static DatabaseReference gameCreateDate(String id){
        return games.child(id).child("createdDate");
    }

    public static DatabaseReference gameGameState(String id){
        return games.child(id).child("state");
    }

    public static DatabaseReference gameName(String id){
        return games.child(id).child("name");
    }

    public static DatabaseReference gamePlayers(String id){
        return games.child(id).child("players");
    }

    // user
    public static DatabaseReference userWithUid(String uid){
        return users.child(uid);
    }
}
