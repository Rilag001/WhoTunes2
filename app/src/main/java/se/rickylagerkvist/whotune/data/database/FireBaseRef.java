package se.rickylagerkvist.whotune.data.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by rickylagerkvist on 2017-04-07.
 */

public class FireBaseRef {

    public static DatabaseReference users = FirebaseDatabase.getInstance().getReference("users");
    public static DatabaseReference rounds = FirebaseDatabase.getInstance().getReference("rounds");

    // round
    public static DatabaseReference round(String id){
        return rounds.child(id);
    }

    public static DatabaseReference roundAdmin(String id){
        return rounds.child(id).child("admin");
    }

    public static DatabaseReference roundCreateDate(String id){
        return rounds.child(id).child("createdDate");
    }

    public static DatabaseReference roundPlayList(String id){
        return rounds.child(id).child("playList");
    }

    public static DatabaseReference roundGameState(String id){
        return rounds.child(id).child("gameState");
    }

    public static DatabaseReference roundName(String id){
        return rounds.child(id).child("name");
    }

    public static DatabaseReference roundUsers(String id){
        return rounds.child(id).child("players");
    }

    // user
    public static DatabaseReference userWithUid(String uid){
        return users.child(uid);
    }
}
