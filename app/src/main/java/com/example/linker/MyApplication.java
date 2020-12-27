package com.example.linker;

import android.app.Application;
import android.content.Context;

/**
 * Author: Adam Inc.
 * <br/>
 * Created at: 2020/10/29 17:29
 * <br/>
 *
 * @since
 */
public class MyApplication extends Application {
    public static MyApplication baseApplication;

    public static Context context;

    public static MyApplication getInstance() {
        if (baseApplication == null) {
            baseApplication = new MyApplication();
        }
        return baseApplication;
    }
    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }



}
