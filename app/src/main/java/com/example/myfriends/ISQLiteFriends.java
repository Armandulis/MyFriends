package com.example.myfriends;

import java.util.ArrayList;
import java.util.List;

public interface ISQLiteFriends {

    long createFriend(FriendBE newFriend);

    ArrayList<FriendBE> getFriends();

    void updateFriend(FriendBE updateFriend);

    void deleteFriend(long friendsID);

}
