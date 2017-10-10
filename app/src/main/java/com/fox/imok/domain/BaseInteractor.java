package com.fox.imok.domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by NestorSo on 28/09/2017.
 */

public class BaseInteractor {
    public static final String SHARED = "shared_iamok";
    private Context mContext;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public BaseInteractor(Context mContext) {
        this.mContext = mContext;
    }
    public SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null)
            sharedPreferences = mContext.getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public SharedPreferences.Editor getEditor() {
        if(editor == null)
            editor = getSharedPreferences().edit();
        return editor;
    }



    public boolean isNetworkAvailable() {
        ConnectivityManager connMgr =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public Context getContext() {
        return mContext;
    }
}
