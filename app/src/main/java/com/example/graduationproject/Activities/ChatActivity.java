package com.example.graduationproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.graduationproject.ChatActivityComponents.ChatViewModel;
import com.example.graduationproject.R;

public class ChatActivity extends AppCompatActivity {

    private ChatViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chat);


        viewModel = new ChatViewModel(this);

        viewModel.searchForFriends();



    }


    @Override
    protected void onResume() {
        super.onResume();
        viewModel.fetchFriends();
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.fetchFriends();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewModel.backPressed();
    }
}