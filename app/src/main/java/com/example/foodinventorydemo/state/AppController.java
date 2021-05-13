package com.example.foodinventorydemo.state;

import android.app.Application;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestController requestController;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }


    public RequestController requests() {
        if (requestController == null) {
            requestController = new RequestController(getApplicationContext());
        }
        return requestController;
    }

    
}