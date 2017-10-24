package com.fox.imok.domain.models;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by nestorso on 21/10/2017.
 */

public class AgendaContactos extends AsyncTask<String, Integer, String> {
    private Context context;
    private ContactosOperacionesContrato listener;

    public AgendaContactos(Context context, ContactosOperacionesContrato listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
