package com.fox.imok.domain.models;

import android.content.Context;
import android.telephony.SmsManager;

import com.fox.imok.domain.bd.TBContactos;

import java.util.List;

/**
 * Created by nestorso on 21/10/2017.
 */

public class ContactosOperaciones implements ContactosOperacionesContrato {

    private SmsManager smsManager;
    private Context context;

    @Override
    public void initContactos(Callback callback) {

    }

    @Override
    public List<TBContactos> obtenerContactos() {
        return null;
    }

    @Override
    public void actualizarContacto(TBContactos contacto) {

    }

    @Override
    public void enviarSMS() {

    }

    @Override
    public void enviarSMS(TBContactos contacto) {

    }

    @Override
    public void llamar(TBContactos contacto) {

    }

}
