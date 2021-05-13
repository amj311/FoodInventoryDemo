package com.example.foodinventorydemo.ui.main.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Settings Fragment:\nNot yet implemented");
    }

    public LiveData<String> getText() {
        return mText;
    }
}