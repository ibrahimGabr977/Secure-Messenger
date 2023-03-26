package com.example.graduationproject.ApplicationSystem;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.graduationproject.Activities.SettingsActivity;
import com.example.graduationproject.ApplicationAIComponents.ManualActivityMonitor;
import com.example.graduationproject.R;

public class ApplicationDefender {


    public static void intruderDetected(Activity activity, String name){

        openSecurityWindow(activity, "Intruder Detected", "Are you Mariam(العبيطة)? Please enter your security code to verify. " +
                "If this message continue annoys you by wrong detection you can disable security methods from settings\"\n",
                "intruder detected");
    }







    public static void openSecurityWindow (Activity activity, String title, String content, String action){

        Dialog securityWindowDialog=new Dialog(activity);
        securityWindowDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        securityWindowDialog.setContentView(R.layout.security_checker_window);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

        securityWindowDialog.getWindow().setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.search_windowbg));

        lp.copyFrom(securityWindowDialog.getWindow().getAttributes());
        lp.width = (int) (activity.getIntent().getIntExtra("screenWidth",0)*0.8);
        lp.height = (int)(activity.getIntent().getIntExtra("screenHeight",0)*0.43);

        TextView security_title=securityWindowDialog.findViewById(R.id.securityWindow_text_title);
        TextView security_content=securityWindowDialog.findViewById(R.id.securityWindow_text_message);



        TextView security_verify=securityWindowDialog.findViewById(R.id.securityWindow_textB_verify);
        EditText security_edit_code=securityWindowDialog.findViewById(R.id.securityWindow_edit_securityCode);


        if (!action.equals("open settings")){
            securityWindowDialog.setCancelable(false);
            securityWindowDialog.setCanceledOnTouchOutside(false);
        }


        security_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (action.equals("open settings")) {
                    if (security_edit_code.getText().toString().equals(SharedData.getSecurityCode(activity))){
                        activity.startActivity(new Intent(activity, SettingsActivity.class));
                        securityWindowDialog.dismiss();
                }else {
                        securityWindowDialog.dismiss();
                        Toast.makeText(activity, "Incorrect security code", Toast.LENGTH_SHORT).show();
                    }

                }else {



                    if (security_edit_code.getText().toString().equals(SharedData.getSecurityCode(activity))){
                        securityWindowDialog.dismiss();
                        ManualActivityMonitor.getInstance().setAlreadyChecked(true);
                    }else {
                        securityWindowDialog.dismiss();
                        ManualActivityMonitor.getInstance().setAlreadyChecked(true);
                        Toast.makeText(activity, "Delete everything....", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


        security_title.setText(title);
        security_content.setText(content);






        securityWindowDialog.show();
        securityWindowDialog.getWindow().setAttributes(lp);



    }



}
