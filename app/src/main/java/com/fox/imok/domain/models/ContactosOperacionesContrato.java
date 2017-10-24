package com.fox.imok.domain.models;

import com.fox.imok.domain.bd.TBContactos;

import java.util.List;

/**
 * Created by nestorso on 21/10/2017.
 */

public interface ContactosOperacionesContrato {
    void initContactos(Callback callback);
    List<TBContactos> obtenerContactos();
    void actualizarContacto(TBContactos contacto);
    void enviarSMS();
    void enviarSMS(TBContactos contacto);
    void llamar(TBContactos contacto);
    interface Callback{
        boolean contactosCargados(String mensaje);
    }
}
