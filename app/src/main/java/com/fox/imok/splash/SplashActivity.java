package com.fox.imok.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fox.imok.R;
import com.fox.imok.dashboard.DashboardActivity;

public class SplashActivity extends AppCompatActivity implements SplashContrato.Vista {
    private SplashContrato.Presentador presentador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        presentador = new SplashPresentador(this);
        presentador.iniciarTiempo();
    }

    @Override
    public void cambiarPantalla() {
        Intent i = new Intent(SplashActivity.this, DashboardActivity.class);
        startActivity(i);
        SplashActivity.this.finish();
    }

}
