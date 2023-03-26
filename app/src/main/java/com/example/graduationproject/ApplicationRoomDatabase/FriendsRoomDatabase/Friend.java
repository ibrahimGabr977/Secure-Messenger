package com.example.graduationproject.ApplicationRoomDatabase.FriendsRoomDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "friends_table")
public class Friend {

    @NonNull
    @PrimaryKey
    private final String friend_id;

    @NonNull
    @ColumnInfo
    private final String friend_name;

    @NonNull
    @ColumnInfo
    private final String friend_profile_image_path;



    @NonNull
    @ColumnInfo
    private final String chatCode;


    public Friend(@NonNull String friend_id, @NonNull String friend_name, @NonNull String friend_profile_image_path,
                  @NonNull String chatCode) {
        this.friend_id = friend_id;
        this.friend_name = friend_name;
        this.friend_profile_image_path = friend_profile_image_path;
        this.chatCode = chatCode;
    }

    @NonNull
    public String getFriend_id() {
        return friend_id;
    }

    @NonNull
    public String getFriend_name() {
        return friend_name;
    }

    @NonNull
    public String getFriend_profile_image_path() {
        return friend_profile_image_path;
    }

    @NonNull
    public String getChatCode() {
        return chatCode;
    }
}
