package com.example.graduationproject.ApplicationAIComponents;

import android.app.Activity;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.graduationproject.ApplicationSystem.ApplicationDefender;
import com.example.graduationproject.ApplicationSystem.SharedData;


public  class ManualActivityMonitor extends AutoActivityMonitor {


    private static ManualActivityMonitor instance;

    public static ManualActivityMonitor getInstance() {
        if (instance == null)
            instance = new ManualActivityMonitor();
        return instance;
    }

    private ManualActivityMonitor() {
    }



    private static SharedPreferences currentUserActivity;
   // private static String savedMonitorCriteria;

    private  boolean alreadyRecord;
    private  String currentActivity;
    private  boolean alreadyChecked;



    public  boolean isAlreadyRecord() {
        return alreadyRecord;
    }

    public  void setAlreadyRecord(boolean alreadyRecord) {
        this.alreadyRecord = alreadyRecord;
    }


    public  boolean isAlreadyChecked() {
        return alreadyChecked;
    }

    public  void setAlreadyChecked(boolean alreadyChecked) {
        this.alreadyChecked = alreadyChecked;
    }




    public  String getCurrentActivity() { return currentActivity; }

    public  void setCurrentActivity(String currentActivity) { this.currentActivity = currentActivity; }




    public static void setNewMonitorCriteria(Context context, String monitorCriteria){

        currentUserActivity =context.getApplicationContext().getSharedPreferences("activityMonitorData",0);
        SharedPreferences.Editor editor=currentUserActivity.edit();
        editor.putString("monitorCriteria",monitorCriteria);
        editor.apply();
    }


    public static String getSavedMonitorCriteria(Context context) {
        //get current user id return "newUser" if not exist (defValue)
        currentUserActivity =context.getApplicationContext().getSharedPreferences("activityMonitorData",0);
        return currentUserActivity.getString("monitorCriteria","Auto");
    }




    public static void ActivityMonitorChecker(Activity activity, String newActivityPart){


        if (ManualActivityMonitor.getInstance().isAlreadyRecord() ){

            if (ManualActivityMonitor.getInstance().getCurrentActivity()!= null)
            ManualActivityMonitor.getInstance().setCurrentActivity(ManualActivityMonitor.getInstance().getCurrentActivity() + newActivityPart);
            else
                ManualActivityMonitor.getInstance().setCurrentActivity(newActivityPart);


        }else {

            if (ManualActivityMonitor.getInstance().getCurrentActivity()!= null) {

                if (ManualActivityMonitor.getInstance().getCurrentActivity().equals(getSavedMonitorCriteria(activity))) {
                    ManualActivityMonitor.getInstance().setAlreadyChecked(true);
                    ManualActivityMonitor.getInstance().setCurrentActivity(null);


                } else if (ManualActivityMonitor.getInstance().getCurrentActivity().length() >= getSavedMonitorCriteria(activity).length())
                    ApplicationDefender.intruderDetected(activity, SharedData.getUserName(activity));
                else
                    ManualActivityMonitor.getInstance().setCurrentActivity(ManualActivityMonitor.getInstance().getCurrentActivity() + newActivityPart);



            }else
                ManualActivityMonitor.getInstance().setCurrentActivity(newActivityPart);

        }





    }



}
