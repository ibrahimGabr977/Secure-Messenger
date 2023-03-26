package com.example.graduationproject.ApplicationRoomDatabase.MessageRoomDatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "messages_table")
public class Message {

    @PrimaryKey
    private  long message_timeMillis;//message time on milliseconds will also considered as its unique number or id


    @NonNull
    @ColumnInfo
    private  String message_body;


    @NonNull
    @ColumnInfo
    private  String from_id;

    @NonNull
    @ColumnInfo
    private  String to_id;

    @NonNull
    @ColumnInfo
    private  String message_type;


    public Message(long message_timeMillis,
                   @NonNull String message_body, @NonNull String from_id, @NonNull String to_id, @NonNull String message_type) {

        this.message_timeMillis = message_timeMillis;
        this.message_body = message_body;
        this.from_id = from_id;
        this.to_id = to_id;
        this.message_type = message_type;
    }




    public long getMessage_timeMillis() {
        return message_timeMillis;
    }



    @NonNull
    public String getMessage_body() {
        return message_body;
    }

    @NonNull
    public String getFrom_id() {
        return from_id;
    }

    @NonNull
    public String getTo_id() {
        return to_id;
    }

    @NonNull
    public String getMessage_type() {
        return message_type;
    }






    public void setMessage_timeMillis(long message_id) {
        this.message_timeMillis = message_id;
    }

    public void setMessage_body(@NonNull String message_body) {
        this.message_body = message_body;
    }

    public void setFrom_id(@NonNull String from_id) {
        this.from_id = from_id;
    }

    public void setMessage_type(@NonNull String message_type) {
        this.message_type = message_type;
    }

    public void setTo_id(@NonNull String to_id) {
        this.to_id = to_id;
    }
}
