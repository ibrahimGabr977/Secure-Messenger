package com.example.graduationproject.ApplicationRoomDatabase.FriendsRoomDatabase;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class FriendViewModelFactory implements ViewModelProvider.Factory{
    private final Application mApplication;



    public FriendViewModelFactory(Application application) {
        mApplication = application;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new FriendViewModel(mApplication);
    }
}
