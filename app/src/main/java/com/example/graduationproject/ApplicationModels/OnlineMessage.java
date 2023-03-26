package com.example.graduationproject.ApplicationModels;

public class OnlineMessage {
    private String message_id, message_body, message_type, from_id, to_id;
    private Object message_date;

    public OnlineMessage(String message_id, Object message_date, String message_body, String from_id, String to_id,
                         String message_type) {
        this.message_id = message_id;
        this.message_date = message_date;
        this.message_body = message_body;
        this.from_id = from_id;
        this.to_id = to_id;
        this.message_type = message_type;

    }

    public OnlineMessage() {
    }

    public String getMessage_id() {
        return message_id;
    }

    public String getMessage_body() {
        return message_body;
    }

    public String getMessage_type() {
        return message_type;
    }

    public String getFrom_id() {
        return from_id;
    }

    public String getTo_id() {
        return to_id;
    }

    public Object getMessage_date() {
        return message_date;
    }
}
