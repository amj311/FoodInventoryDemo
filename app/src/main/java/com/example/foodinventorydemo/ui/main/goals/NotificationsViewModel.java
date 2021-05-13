package com.example.foodinventorydemo.ui.main.goals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Goals Fragment:\nNot yet implemented");
    }

    public LiveData<String> getText() {
        return mText;
    }
}