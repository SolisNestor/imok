package com.fox.imok.domain.models;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;

import com.activeandroid.query.Select;
import com.fox.imok.Aplication;
import com.fox.imok.R;
import com.fox.imok.domain.bd.TBContactos;
import com.fox.imok.domain.permisos.PermisoObj;
import com.fox.imok.domain.permisos.Permisos;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by nestorso on 21/10/2017.
 */

public class ContactosOperaciones implements ContactosOperacionesContrato {
    public static final String KEY_INIT = "init";

    private final SmsManager smsManager;
    private final Context mContext;
    private final SharedPreferences sharedPreferences;
    private CallbackContactosOperaciones listener;


    public ContactosOperaciones() {
        this.mContext = Aplication.getContext();
        sharedPreferences = mContext.getSharedPreferences(mContext.getPackageName(), Context.MODE_PRIVATE);
        smsManager = SmsManager.getDefault();
    }
    @Override
    public void initContactos(AgendaCallback callback) {
        if (!Permisos.permisoUtilCheck(getContext(), Manifest.permission.READ_CONTACTS)) {
            sendCallBack(Manifest.permission.READ_CONTACTS);
            callback.onResult(false, "");
            return;
        }
        boolean isInit = sharedPreferences.getBoolean(KEY_INIT, false);
        if (!isInit)
            new AgendaContactosAWS(mContext, callback);
            //new AgendaContactos(mContext, callback).execute("");
        else
            callback.onResult(true, mContext.getString(R.string.contactoscargados));

    }

    @Override
    public void obtenerContactos(final CallbackContactos callback) {
        initContactos(new AgendaCallback() {
            @Override
            public void onResult(boolean result, String message) {
                if (result) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(KEY_INIT, true);
                    editor.apply();
                    editor.commit();
                    callback.onResult(result, new Select().from(TBContactos.class).<TBContactos>execute(), message);
                }
            }
        });
    }

    @Override
    public void actualizarContacto(String telefono, boolean envioMensaje, String mensaje) {
        TBContactos contacto = new Select().from(TBContactos.class).where("telefono=?", telefono.replaceAll(" ", "")).executeSingle();
        if (contacto == null)
            sendCallBack(false,
                    String.format(getContext().getString(R.string.contactosoperaciones_contactonoexiste),
                            telefono));
        else
            actualizarContacto(contacto, envioMensaje, mensaje);
    }

    @Override
    public void actualizarContacto(TBContactos contacto, boolean envioMensaje, String mensaje) {
        contacto.actualizar(envioMensaje, mensaje);
    }

    @Override
    public void enviarSMS() {
        obtenerContactos(new CallbackContactos() {
            @Override
            public void onResult(boolean result, List<TBContactos> contactos, String message) {
                if (result)
                    processSendSms(contactos, 0);
            }
        });
    }

    @Override
    public void enviarSMS(final String mensaje) {
        obtenerContactos(new CallbackContactos() {
            @Override
            public void onResult(boolean result, List<TBContactos> contactos, String message) {
                if (result)
                    processSendSms(contactos, 0, mensaje);
            }
        });
    }

    private void processSendSms(List<TBContactos> contactos, int indice) {
        if (indice < contactos.size()) {
            enviarSMS(contactos.get(indice));
            processSendSms(contactos, indice + 1);
        } else
            sendCallBack(true, getContext().getString(R.string.mensajes_entregados));
    }

    private void processSendSms(List<TBContactos> contactos, int indice, String mensaje) {
        if (indice < contactos.size()) {
            enviarSMS(contactos.get(indice), mensaje);
            processSendSms(contactos, indice + 1, mensaje);
        } else
            sendCallBack(true, getContext().getString(R.string.mensajes_entregados));
    }

    @Override
    public void enviarSMS(TBContactos contacto, String mensaje) {
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS)) {
            smsManager.sendTextMessage(contacto.getTelefono(), null, mensaje, null, null);
            actualizarContacto(contacto, true, mensaje);
        } else
            sendCallBack(Manifest.permission.SEND_SMS);
    }

    @Override
    public void enviarSMS(TBContactos contacto) {
        final String mensaje = getContext().getString(R.string.mensajeayuda);
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS)) {
            smsManager.sendTextMessage(contacto.getTelefono(), null, mensaje, null, null);
            actualizarContacto(contacto, true, mensaje);
        } else
            sendCallBack(Manifest.permission.SEND_SMS);
    }

    @Override
    public void llamar(TBContactos contacto) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + contacto.getTelefono()));
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE))
            getContext().startActivity(intent);
        else
            sendCallBack(Manifest.permission.CALL_PHONE);
    }

    @Override
    public void setCallbackContactosOperaciones(CallbackContactosOperaciones callbackContactosOperaciones) {
        this.listener = callbackContactosOperaciones;
    }

    private void sendCallBack(boolean result, String message) {
        if (listener != null)
            listener.onResult(result, message);
    }

    private void sendCallBack(String permission) {
        if (listener != null)
            listener.requestPermission(permission);
    }

    public Context getContext() {
        return mContext;
    }
}
