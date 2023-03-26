package com.example.graduationproject.ApplicationRoomDatabase.FriendsRoomDatabase;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FriendViewModel extends AndroidViewModel {


    private final FriendRepository friendRepository;

    private  final LiveData<List<Friend>> allFriends;

    public FriendViewModel (Application application) {
        super(application);
        friendRepository = new FriendRepository(application);
        allFriends = friendRepository.getAllFriends();
    }



    public LiveData<List<Friend>> getAllFriends() { return allFriends; }

    public void insert(Friend friend) { friendRepository.insertFriend(friend); }

    public void deleteFriendById(String friend_id) {friendRepository.deleteFriendById(friend_id);}



    public LiveData<Friend> getASpecificFriend(String friend_id){
        return friendRepository.getASpecificFriend(friend_id);
    }




}
