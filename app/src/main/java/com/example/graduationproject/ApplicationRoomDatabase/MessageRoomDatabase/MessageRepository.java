package com.example.graduationproject.ApplicationRoomDatabase.MessageRoomDatabase;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.graduationproject.ApplicationRoomDatabase.MyRoomDatabase;
import java.util.List;

 class MessageRepository {

    private final MessageDao messageDao;
   // private final LiveData<List<Message>> allMessages;


     MessageRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        messageDao = db.messageDao();
        //allMessages = messageDao.getAllCurrentChatMessages(friend_id);
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
     LiveData<List<Message>> getAllMessages(String friend_id) {
        return messageDao.getAllCurrentChatMessages(friend_id);
    }



    // These must called  on a non-UI thread or the app will throw an exception. Room ensures
    // that there're not any long running operations on the main thread, blocking the UI.

    //insert a new message to the database
     void insertMessage(Message message) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            messageDao.insertMessage(message);
        });
    }


    //delete a message or list of messages from the database using its/their id/s
     void deleteMessageById(Long... message_id) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            messageDao.deleteMessage(message_id);
        });
    }

    LiveData<Message> getLastMessage(String friend_id){
         return messageDao.getLastMessage(friend_id);
    }

}
