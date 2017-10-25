package com.fox.imok;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

/**
 * Created by NestorSo on 28/09/2017.
 */

public class Aplication extends Application {

    private static  Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
