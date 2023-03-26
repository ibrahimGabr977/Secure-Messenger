package com.example.graduationproject.ApplicationRoomDatabase.MessageRoomDatabase;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

public class MessageViewModel extends AndroidViewModel {


    private final MessageRepository messageRepository;

    //private final LiveData<List<Message>> allCurrentMessages;



    public MessageViewModel (Application application) {
        super(application);
        messageRepository = new MessageRepository(application);

    }

    public LiveData<List<Message>> getAllCurrentMessages(String friend_id) {
        return messageRepository.getAllMessages(friend_id);
    }

    public void insertMessage(Message message) { messageRepository.insertMessage(message); }

    public void deleteMessageById(Long... message_id) {messageRepository.deleteMessageById(message_id);}

    public LiveData<Message> getLastMessage(String friend_id){
        return messageRepository.getLastMessage(friend_id);
    }

}
