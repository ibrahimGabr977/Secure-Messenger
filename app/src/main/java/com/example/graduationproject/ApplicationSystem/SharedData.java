package com.example.graduationproject.ApplicationSystem;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.graduationproject.R;

public class SharedData {

    private static SharedPreferences currentUserInfo;

   public static void saveNewUserData(Context context, String currentUserId,
                             String userName, String securityCode, String profileImageResource){

        //shared preferences put data
        currentUserInfo =context.getApplicationContext().getSharedPreferences("userPref",0);
        SharedPreferences.Editor editor=currentUserInfo.edit();
        editor.putString("currentUserId",currentUserId);
        editor.putString("userName",userName);
       editor.putString("securityCode",securityCode);
        editor.putString("profileImageResource",profileImageResource);
        editor.apply();

    }


     public static String getCurrentUserId(Context context){

        //get current user id return "newUser" if not exist (defValue)
        currentUserInfo =context.getApplicationContext().getSharedPreferences("userPref",0);
        return currentUserInfo.getString("currentUserId","NewUser");

    }

    public static String getSecurityCode(Context context){

        //get current user id return "newUser" if not exist (defValue)
        currentUserInfo =context.getApplicationContext().getSharedPreferences("userPref",0);
        return currentUserInfo.getString("securityCode","NewUser");

    }


    public static String getProfileImageResource(Context context){

       // return current user profile image
        currentUserInfo =context.getApplicationContext().getSharedPreferences("userPref",0);
        return currentUserInfo.getString("profileImageResource",
                String.valueOf(R.drawable.chat_default_profile_imaged));
    }


    public static String getUserName(Context context){

        // return current user name
        currentUserInfo =context.getApplicationContext().getSharedPreferences("userPref",0);
        return currentUserInfo.getString("userName","NewUser");

    }



}
