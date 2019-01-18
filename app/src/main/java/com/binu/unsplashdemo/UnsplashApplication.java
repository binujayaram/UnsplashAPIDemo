package com.binu.unsplashdemo;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by nmillward on 3/31/17.
 */

public class UnsplashApplication extends Application {

    private static UnsplashApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static UnsplashApplication getApplicationInstance() {
        return instance;
    }

    public static boolean hasNetwork() {
        return instance.isNetworkAvailable();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null) && activeNetworkInfo.isConnectedOrConnecting();
    }
}
