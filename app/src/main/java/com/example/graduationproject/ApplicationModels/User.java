package com.example.graduationproject.ApplicationModels;



public class User {

    private String userId, name, profileImageResource;

    public User(String userId, String name, String profileImageResource) {
        this.userId = userId;
        this.name = name;
        this.profileImageResource = profileImageResource;
    }

    public User() {}

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageResource() {
        return profileImageResource;
    }


}
