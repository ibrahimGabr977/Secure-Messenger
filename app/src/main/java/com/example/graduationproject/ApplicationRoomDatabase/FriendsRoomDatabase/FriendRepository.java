package com.example.graduationproject.ApplicationRoomDatabase.FriendsRoomDatabase;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.graduationproject.ApplicationRoomDatabase.MyRoomDatabase;
import java.util.List;


 class FriendRepository {

        private final FriendDao friendDao;
        private final LiveData<List<Friend>> allFriends;



         FriendRepository(Application application) {
            MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
            friendDao = db.friendDao();
            allFriends = friendDao.getAllFriends();
        }

        // Room executes all queries on a separate thread.
        // Observed LiveData will notify the observer when the data has changed.
         LiveData<List<Friend>> getAllFriends() {
            return allFriends;
        }



        // These must called  on a non-UI thread or the app will throw an exception. Room ensures
        // that there're not any long running operations on the main thread, blocking the UI.

       //insert a new chat friend to the database
        void insertFriend(Friend friend) {
            MyRoomDatabase.databaseWriteExecutor.execute(() -> {
                friendDao.insertFriend(friend);
            });
        }

       //delete a chat friend from the database using his id
        void deleteFriendById(String friend_id) {
           MyRoomDatabase.databaseWriteExecutor.execute(() -> {
              friendDao.deleteFriend(friend_id);
          });
        }


        LiveData<Friend> getASpecificFriend(String friend_id){
             return friendDao.getASpecificFriend(friend_id);
        }


    }


