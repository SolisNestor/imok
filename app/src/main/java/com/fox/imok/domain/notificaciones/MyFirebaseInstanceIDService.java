package com.fox.imok.domain.notificaciones;

import android.util.Log;

import com.fox.imok.domain.io.ConstantsUrls;
import com.fox.imok.domain.io.RetroFitHelper;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nestorso on 8/11/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    public static final String REFRESHEDTOKEN = "refreshedToken";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        final String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sendToken(refreshedToken);
    }

    private void sendToken(String refreshToken){
        JsonObject json = new JsonObject();
        json.addProperty(ConstantsUrls.Params.ARN, refreshToken);
        Call<JsonElement> call = RetroFitHelper.getApiServices().saveFCM(json);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.body()!=null){
                    //Log.e(getClass().getName(), "JSON "+response.body().getAsJsonObject().toString());
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
