package com.fox.imok.domain.models;

import com.fox.imok.domain.bd.TBContactos;

import java.util.List;

/**
 * Created by nestorso on 21/10/2017.
 */

public interface ContactosOperacionesContrato {
    void initContactos(AgendaCallback callback);
    void obtenerContactos(CallbackContactos callback);
    void actualizarContacto(String telefono, boolean envioMensaje, String mensaje);
    void actualizarContacto(TBContactos contacto, boolean envioMensaje, String mensaje);
    void enviarSMS();
    void enviarSMS(TBContactos contacto);
    void llamar(TBContactos contacto);
    void setCallbackContactosOperaciones(CallbackContactosOperaciones callbackContactosOperaciones);
    interface CallbackContactosOperaciones{
        void onResult(boolean result, String message);
        void requestPermission(String permiso);
    }
    interface CallbackContactos{
        void onResult(boolean result, List<TBContactos> contactos, String message);
    }
    interface AgendaCallback{
        void onResult(boolean result, String message);
    }
}
