package com.fox.imok.splash;

import android.os.Handler;

/**
 * Created by nestorso on 21/10/2017.
 */

public class SplashPresentador implements SplashContrato.Presentador {
    private SplashContrato.Vista vista;
    public static final int SECONDS_NEXT_SCREEN = 1;
    public SplashPresentador(SplashContrato.Vista vista) {
        this.vista = vista;
    }

    @Override
    public void iniciarTiempo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getVista().cambiarPantalla();
            }
        }, SECONDS_NEXT_SCREEN * 1000);
    }

    public SplashContrato.Vista getVista() {
        return vista;
    }
}
