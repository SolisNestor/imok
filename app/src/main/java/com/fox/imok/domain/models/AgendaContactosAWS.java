package com.fox.imok.domain.models;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.fox.imok.R;
import com.fox.imok.domain.bd.TBContactos;
import com.fox.imok.domain.io.ConstantsUrls;
import com.fox.imok.domain.io.JsonKeys;
import com.fox.imok.domain.io.RetroFitHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nestorso on 8/11/2017.
 */

public class AgendaContactosAWS {
    private Context context;
    private ContactosOperacionesContrato.AgendaCallback listener;

    public AgendaContactosAWS(Context context, ContactosOperacionesContrato.AgendaCallback listener) {
        this.context = context;
        this.listener = listener;
        getListUsers();
    }

    private void getListUsers(){
        try {
            JsonObject json = new JsonObject();
            json.add(ConstantsUrls.Params.COUNTRY, new JsonParser().parse("GT"));
            RetroFitHelper.getApiServices().listusers(json).enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    if(response.body()!=null){
                        JsonArray json = response.body().getAsJsonArray();
                        processContacts(json);
                    }

                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    listener.onResult(false, t.getLocalizedMessage());
                }


            });
        }catch (Exception e){
            e.printStackTrace();
            Log.e(getClass().getName(), e.getLocalizedMessage());
        }
    }

    private void processContacts (JsonArray list){
        try {
            ActiveAndroid.beginTransaction();
            for (int x = 0; x < list.size(); x++) {
                TBContactos contacto = new TBContactos();
                JsonObject json = list.get(x).getAsJsonObject();
                contacto.setNombre(json.get(JsonKeys.NAME).getAsString() + " " + json.get(JsonKeys.LAST_NAME).getAsString());
                contacto.setTelefono(json.get(JsonKeys.PHONE_NUMBER).getAsString());
                contacto.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        }
        catch (Exception e){
            e.getLocalizedMessage();
        }finally {
            ActiveAndroid.endTransaction();
            listener.onResult(true, context.getString(R.string.contactoscargados));
        }


    }
}
