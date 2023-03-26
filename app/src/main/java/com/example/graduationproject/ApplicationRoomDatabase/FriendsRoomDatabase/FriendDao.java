package com.example.graduationproject.ApplicationRoomDatabase.FriendsRoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface FriendDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFriend(Friend friend);


    @Query("DELETE FROM friends_table WHERE friend_id = :friend_id")
    void deleteFriend(String friend_id);


    @Query("SELECT * FROM friends_table")
    LiveData<List<Friend>> getAllFriends();


    @Query("SELECT * FROM friends_table WHERE friend_id = :friend_id")
    LiveData<Friend> getASpecificFriend(String friend_id);



}
