package com.example.graduationproject.ApplicationRoomDatabase.MessageRoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public  interface MessageDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMessage(Message message);



    @Query("DELETE FROM messages_table WHERE message_timeMillis IN (:message_id)")
    void deleteMessage(Long... message_id); //able to delete single or multiple message/s


    //get messages from database that sent or received by the user and the specified friend
    @Query("SELECT * FROM messages_table WHERE from_id = :friend_id OR to_id = :friend_id " +
            "ORDER BY message_timeMillis ASC")
    LiveData<List<Message>> getAllCurrentChatMessages(String friend_id);


     @Query("SELECT * FROM messages_table WHERE from_id = :friend_id OR to_id = :friend_id" +
             " ORDER BY message_timeMillis DESC LIMIT 1")
     LiveData<Message> getLastMessage(String friend_id);



}
