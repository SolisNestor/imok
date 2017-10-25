package com.fox.imok.splash;

/**
 * Created by nestorso on 21/10/2017.
 */

public interface SplashContrato {

    interface Vista{
        void cambiarPantalla();
        void mostrarMensaje(String mensaje);
    }
    interface Presentador{
        void iniciarTiempo();
    }
}
