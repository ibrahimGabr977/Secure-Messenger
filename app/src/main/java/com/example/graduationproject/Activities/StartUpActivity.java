package com.example.graduationproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.example.graduationproject.ApplicationSystem.GeneralSystemMethods;
import com.example.graduationproject.ApplicationSystem.SharedData;
import com.example.graduationproject.R;
import com.example.graduationproject.StartUpActivityComponents.StartUpViewModel;



public class StartUpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_startup);

        if (!SharedData.getCurrentUserId(this).equals("NewUser")){

            GeneralSystemMethods.startChat(this);
            //Toast.makeText(this, "Not new", Toast.LENGTH_SHORT).show();


        }else {

              new StartUpViewModel(this);

        }





//        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Users");
//
//        reference.child("12345").setValue(new User("12345", "Ibrahim",""));

    }




}