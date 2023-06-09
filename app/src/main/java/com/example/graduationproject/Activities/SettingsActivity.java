package com.example.graduationproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.graduationproject.R;
import com.example.graduationproject.SettingsActivityComponents.SettingsViewModel;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        SettingsViewModel viewModel = new SettingsViewModel(this);

        viewModel.switchActivityMonitor();
        viewModel.switchKeyStrokes();


    }
}