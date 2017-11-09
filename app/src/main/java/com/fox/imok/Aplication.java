package com.fox.imok;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;
import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.config.AWSConfiguration;

/**
 * Created by NestorSo on 28/09/2017.
 */

public class Aplication extends MultiDexApplication {

    private static  Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        initializeApplication();
        ActiveAndroid.initialize(this);
        context = this;
    }

    private void initializeApplication() {

        AWSConfiguration awsConfiguration = new AWSConfiguration(getApplicationContext());

        // If IdentityManager is not created, create it
        if (IdentityManager.getDefaultIdentityManager() == null) {
            IdentityManager identityManager =
                    new IdentityManager(getApplicationContext(), awsConfiguration);
            IdentityManager.setDefaultIdentityManager(identityManager);
        }

    }
    public static Context getContext() {
        return context;
    }
}
