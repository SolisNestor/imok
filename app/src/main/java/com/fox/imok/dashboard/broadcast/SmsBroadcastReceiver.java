package com.fox.imok.dashboard.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.activeandroid.query.Select;
import com.fox.imok.alerta.AlertaActivity;
import com.fox.imok.dashboard.eventbus.ContactoEventBus;
import com.fox.imok.domain.bd.TBContactos;
import com.fox.imok.domain.io.ConstantsUrls;
import com.fox.imok.domain.io.RetroFitHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                            saveSMS(context, contactos, message );
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

    private void saveSMS(Context context,TBContactos contacto, String mensaje){
        JsonObject res = new JsonObject();
        res.addProperty(ConstantsUrls.Params.ID, contacto.getTelefono().replace("+","%2B"));
        res.addProperty(ConstantsUrls.Params.MESSAGE, mensaje);
        res.addProperty(ConstantsUrls.Params.STATUS, contacto.isOk()?"1":"0");
        res.addProperty(ConstantsUrls.Params.EVENT, "20171115212527");
        RetroFitHelper.getApiServices().saveSMS(res).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.e(getClass().getName(), "JSON "+response.body().toString());
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
