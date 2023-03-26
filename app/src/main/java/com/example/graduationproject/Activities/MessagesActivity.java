package com.example.graduationproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.graduationproject.MessagesActivityComponents.MessagesViewModel;
import com.example.graduationproject.R;

public class MessagesActivity extends AppCompatActivity {

    private MessagesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_messages);

        viewModel=new MessagesViewModel(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.fetchMessages();
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.fetchMessages();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewModel.onBackClicked();
    }
}