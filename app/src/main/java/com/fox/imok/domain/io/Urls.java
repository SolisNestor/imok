package com.fox.imok.domain.io;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by nestorso on 8/11/2017.
 */

public interface Urls {
    @POST(ConstantsUrls.ENDPOINT_LIST_USERS)
    Call<JsonElement> listusers(@Body JsonObject info);

    @POST(ConstantsUrls.ENDPOINT_SAVE_FCM)
    Call<JsonElement> saveFCM(@Body JsonObject info);

    @POST(ConstantsUrls.ENDPOINT_SAVE_SMS)
    Call<JsonElement> saveSMS(@Body JsonObject info);
}
