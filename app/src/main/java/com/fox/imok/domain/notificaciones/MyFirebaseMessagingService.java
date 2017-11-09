package com.fox.imok.domain.notificaciones;

import android.util.Log;

import com.fox.imok.domain.models.ContactosOperaciones;
import com.fox.imok.domain.models.ContactosOperacionesContrato;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        JSONObject notificacion = new JSONObject(remoteMessage.getData());
        Log.e(getClass().getName(),"PUSH! "+notificacion.toString());
        if(notificacion.optString("pinpoint.notification.title").toLowerCase().equalsIgnoreCase("alert")) {
            ContactosOperacionesContrato contrato = new ContactosOperaciones();
            contrato.enviarSMS(notificacion.optString("pinpoint.notification.body"));
        }
    }
}
