package com.fox.imok.alerta;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;

import com.fox.imok.Aplication;
import com.fox.imok.domain.models.ConstantsContactosOperaciones;
import com.fox.imok.domain.models.ContactosOperaciones;
import com.fox.imok.domain.models.ContactosOperacionesContrato;
import com.fox.imok.domain.models.Error;

/**
 * Created by nestorso on 21/10/2017.
 */

public class AlertaPresentador implements AlertaContrato.Presentador, ContactosOperacionesContrato.CallbackContactosOperaciones {


    private AlertaContrato.Vista vista;
    private ContactosOperacionesContrato contactosOperaciones;

    public AlertaPresentador(AlertaContrato.Vista vista) {
        this.vista = vista;
        contactosOperaciones = new ContactosOperaciones();
        contactosOperaciones.setCallbackContactosOperaciones(this);
    }

    @Override
    public void enviarAlerta() {
        contactosOperaciones.enviarSMS();
    }

    @Override
    public void onResult(boolean result, String message) {
        if(result) {
            vista.ocultarBotonPrincipal(true);
            vista.mostrarAlertaEnProceso();
        }else {
            Error error = new Error();
            error.setMensaje(message);
            vista.mostrarError(error);
        }
    }

    @Override
    public void requestPermission(String permiso) {
        vista.solicitarPermiso(permiso);
    }

}
