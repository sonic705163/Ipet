package com.example.java.mybottomnav;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context gc() {
        return context;
    }
}