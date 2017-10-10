package com.fox.imok;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

/**
 * Created by NestorSo on 28/09/2017.
 */

public class Aplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
