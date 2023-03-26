package com.example.graduationproject.ApplicationRoomDatabase.MessageRoomDatabase;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class MessageViewModelFactory implements ViewModelProvider.Factory{

    private final Application mApplication;



    public MessageViewModelFactory(Application application) {
        mApplication = application;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MessageViewModel(mApplication);
    }
}
