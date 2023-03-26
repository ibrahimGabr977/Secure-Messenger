package com.example.graduationproject.StartUpActivityComponents;

import android.app.Activity;;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.graduationproject.ApplicationSystem.GeneralSystemMethods;
import com.example.graduationproject.ApplicationSystem.MyFirebaseBuilder;
import com.example.graduationproject.R;


public class StartUpViewModel {

    private final Activity activity;
    private final EditText nameEditText, securityCodeEditText;



    public StartUpViewModel(Activity activity) {
        this.activity = activity;

        nameEditText = activity.findViewById(R.id.startup_edit_name);
        securityCodeEditText = activity.findViewById(R.id.startup_edit_securityCode);
        TextView createAccountTextView = activity.findViewById(R.id.startup_create_account_button);


        createAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameEditText.getText().toString().trim().length() != 0
                        && securityCodeEditText.getText().toString().trim().length() != 0)

                createAccount();

                else
                    Toast.makeText(activity, "Please fill the required fields", Toast.LENGTH_SHORT).show();
            }
        });
    }





    private void createAccount () {

        if (GeneralSystemMethods.isInternetAvailable(activity)){

            MyFirebaseBuilder.createNewUserAccount(activity, nameEditText.getText().toString(),
                    securityCodeEditText.getText().toString());

//            if (!SharedData.getCurrentUserId(activity).equals("NewUser")){
//
//               GeneralSystemMethods.startChat(activity);
//               // Toast.makeText(activity, "done", Toast.LENGTH_SHORT).show();
//
//            }else {
//
//                Toast.makeText(activity, "Failed to create account, please try again", Toast.LENGTH_SHORT).show();
//
//            }

        }else {

            Toast.makeText(activity, "No internet connection",
                    Toast.LENGTH_SHORT).show();
        }





    }






}
