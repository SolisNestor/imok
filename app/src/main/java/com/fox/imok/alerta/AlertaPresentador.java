package com.fox.imok.alerta;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by nestorso on 21/10/2017.
 */

public class AlertaPresentador implements AlertaContrato.Presentador {

    public static final String KEY_SHARED_ALERTA= "EN_ALERTA";
    private AlertaContrato.Vista vista;
    private SharedPreferences sharedPreferences;

    public AlertaPresentador(AlertaContrato.Vista vista) {
        this.vista = vista;
    }

    @Override
    public void validarAlertaEnProceso(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        boolean enAlerta = sharedPreferences.getBoolean(KEY_SHARED_ALERTA, false);
        if(enAlerta){
            vista.ocultarBotonPrincipal();
            vista.mostrarAlertaEnProceso();
        }
    }

    @Override
    public void enviarAlerta() {

    }
}
