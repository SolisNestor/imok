package com.fox.imok.dashboard.tareas;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.activeandroid.ActiveAndroid;
import com.fox.imok.dashboard.DashboardInteractor;
import com.fox.imok.domain.bd.TBContactos;

/**
 * Created by nestorso on 30/09/2017.
 */

public class GuardarContactos extends AsyncTask<String, String, String> {

    private Context context;
    private DashboardInteractor.Callback callback;

    public GuardarContactos(Context context, DashboardInteractor.Callback callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        callback.cargaContactosOk();
    }

    @Override
    protected String doInBackground(String... strings) {
        obtenerContactos();
        return null;
    }

    public void obtenerContactos() {

        try {
            ActiveAndroid.beginTransaction();
            ContentResolver cr = getContext().getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);
            if ((cur != null ? cur.getCount() : 0) > 0) {
                while (cur != null && cur.moveToNext()) {
                    String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));

                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            TBContactos tbContactos = new TBContactos();
                            tbContactos.setNombre(name);
                            tbContactos.setTelefono(phoneNo);
                            tbContactos.save();
                        }
                        pCur.close();
                    }
                }
            }
            if (cur != null)
                cur.close();
            ActiveAndroid.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    public Context getContext() {
        return context;
    }

    public DashboardInteractor.Callback getCallback() {
        return callback;
    }
}
