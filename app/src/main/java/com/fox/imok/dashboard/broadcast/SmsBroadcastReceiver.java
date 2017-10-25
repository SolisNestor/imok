package com.fox.imok.dashboard.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.activeandroid.query.Select;
import com.fox.imok.alerta.AlertaActivity;
import com.fox.imok.dashboard.eventbus.ContactoEventBus;
import com.fox.imok.domain.bd.TBContactos;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdu_Objects = (Object[]) bundle.get("pdus");
                if (pdu_Objects != null) {
                    for (Object aObject : pdu_Objects) {
                        SmsMessage currentSMS = getIncomingMessage(aObject, bundle);
                        String senderNo = currentSMS.getDisplayOriginatingAddress();
                        String message = currentSMS.getDisplayMessageBody();
                        TBContactos contactos = new Select().from(TBContactos.class).where("telefono=?", senderNo.replaceAll(" ", "")).executeSingle();
                        if(message.equalsIgnoreCase("alerta") && contactos!=null && contactos.isAdmin()){
                            Intent i = new Intent(context, AlertaActivity.class);
                            i.putExtra("alerta", true);
                            context.startActivity(i);
                        }else {
                            if (contactos != null) {
                                if (message.toLowerCase().contains("imok"))
                                    contactos.setOk(true);
                                else
                                    contactos.setOk(false);
                                Date currentTime = Calendar.getInstance().getTime();
                                contactos.setFechaHoraRespuesta(currentTime.getTime());
                                contactos.setMensajeRespuesta(message);
                                contactos.save();
                            }
                        }
                    }
                    EventBus.getDefault().post(new ContactoEventBus());
                    this.abortBroadcast();
                    // End of loop
                }
            }
        } // bundle null
    }

    private SmsMessage getIncomingMessage(Object aObject, Bundle bundle) {
        SmsMessage currentSMS;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String format = bundle.getString("format");
            currentSMS = SmsMessage.createFromPdu((byte[]) aObject, format);
        } else {
            currentSMS = SmsMessage.createFromPdu((byte[]) aObject);
        }
        return currentSMS;
    }
}
