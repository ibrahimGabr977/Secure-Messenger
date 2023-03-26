package com.example.graduationproject.SettingsActivityComponents;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.SwitchCompat;

import com.example.graduationproject.ApplicationAIComponents.KeyStrokeDynamics;
import com.example.graduationproject.ApplicationAIComponents.ManualActivityMonitor;
import com.example.graduationproject.ApplicationSystem.SharedData;
import com.example.graduationproject.R;

public class SettingsViewModel {

    private final Activity activity;
    private final SwitchCompat switch_activity_monitor, switch_key_strokes;
    private final TextView start_activity_record;
    private final EditText edit_key_strokes;

    public SettingsViewModel(Activity activity) {
        this.activity = activity;


        ImageView image_profile = activity.findViewById(R.id.settings_image_profile);
        EditText edit_name = activity.findViewById(R.id.settings_edit_name);
        EditText edit_security_code = activity.findViewById(R.id.settings_edit_securityCode);
        TextView edit_userId = activity.findViewById(R.id.settings_text_searchId);





        image_profile.setImageResource(Integer.parseInt(SharedData.getProfileImageResource(activity)));
        edit_name.setText(SharedData.getUserName(activity));
        edit_userId.setText("#"+SharedData.getCurrentUserId(activity));
        edit_security_code.setText(SharedData.getSecurityCode(activity));



        switch_activity_monitor = activity.findViewById(R.id.settings_switch_activityMonitor);
        start_activity_record = activity.findViewById(R.id.settings_textB_recordActivity);
        switch_key_strokes= activity.findViewById(R.id.settings_switch_keyStrokes);
        edit_key_strokes = activity.findViewById(R.id.settings_edit_keyStrokes);




        TextView clickBack = activity.findViewById(R.id.settings_textB_back);

        clickBack.setOnClickListener(v -> activity.finish());



    }


    public void switchActivityMonitor(){



        if (!ManualActivityMonitor.getSavedMonitorCriteria(activity).equals("Auto"))
            switch_activity_monitor.setChecked(true);






        switch_activity_monitor.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked){

                ManualActivityMonitor.getInstance().setCurrentActivity(null);

             start_activity_record.setVisibility(View.VISIBLE);

             startActivityRecording();


            }else {
                ManualActivityMonitor.getInstance().setCurrentActivity(null);
                start_activity_record.setVisibility(View.GONE);
                ManualActivityMonitor.setNewMonitorCriteria(activity, "Auto");

            }





        });

    }


    private void startActivityRecording(){

        start_activity_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Start activity recording....", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ManualActivityMonitor.getInstance().setAlreadyRecord(true);
                        activity.finish();
                    }
                },1000L);
            }
        });

    }







    public void switchKeyStrokes(){



        if (!KeyStrokeDynamics.getSavedKeyStorkesCriteria(activity).equals("Empty"))
            switch_key_strokes.setChecked(true);






        switch_key_strokes.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked){



                edit_key_strokes.setVisibility(View.VISIBLE);




            }else {

                edit_key_strokes.setVisibility(View.GONE);


            }





        });

    }








}
