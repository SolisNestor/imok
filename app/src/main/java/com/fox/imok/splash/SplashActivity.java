package com.fox.imok.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fox.imok.R;
import com.fox.imok.dashboard.DashboardActivity;

public class SplashActivity extends AppCompatActivity {

    public static final int SECONDS_NEXT_SCREEN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        nextScreen();
    }

    private void nextScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, DashboardActivity.class);
                startActivity(i);
                SplashActivity.this.finish();
            }
        }, (SECONDS_NEXT_SCREEN * 1000));
    }
}
