package com.shakil.stop;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.util.Log;

/**
 * Created by Shakil Karim on 11/19/16.
 */

public class MyApp extends Application {

    private static MyApp instance;


    private boolean mIsDeviceLocked;

    public static MyApp getInstance() {
        return instance;
    }


    public  boolean isDeviceLocked() {
        return mIsDeviceLocked;
    }

    public void setIsDeviceLocked(boolean isDeviceLocked) {
        mIsDeviceLocked = isDeviceLocked;
    }



    private static final String TAG = "MyApp";
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        Log.d(TAG, "onCreate: ");
    }


    @Override
    public void onTerminate() {
        super.onTerminate();

        Log.d(TAG, "onTerminate: ");
    }


}
