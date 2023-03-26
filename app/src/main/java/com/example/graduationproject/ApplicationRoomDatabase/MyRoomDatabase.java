package com.example.graduationproject.ApplicationRoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.graduationproject.ApplicationRoomDatabase.FriendsRoomDatabase.Friend;
import com.example.graduationproject.ApplicationRoomDatabase.FriendsRoomDatabase.FriendDao;
import com.example.graduationproject.ApplicationRoomDatabase.MessageRoomDatabase.Message;
import com.example.graduationproject.ApplicationRoomDatabase.MessageRoomDatabase.MessageDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Message.class, Friend.class}, version = 1, exportSchema = false)

public abstract class MyRoomDatabase extends RoomDatabase {

    public abstract FriendDao friendDao();

    public abstract MessageDao messageDao();

    private static volatile MyRoomDatabase INSTANCE; //ensure that there will be only one instance of this class
    private static final int NUMBER_OF_THREADS = 5;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);



    public static MyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyRoomDatabase.class, "whole_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}