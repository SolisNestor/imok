package com.fox.imok.domain.models;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.fox.imok.R;
import com.fox.imok.domain.bd.TBContactos;

/**
 * Created by nestorso on 21/10/2017.
 */

public class AgendaContactos extends AsyncTask<String, Integer, Boolean> {
    private Context context;
    private ContactosOperacionesContrato.AgendaCallback listener;

    public AgendaContactos(Context context, ContactosOperacionesContrato.AgendaCallback listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        return obtenerContactos();
    }

    @Override
    protected void onPostExecute(Boolean s) {
        super.onPostExecute(s);
        listener.onResult(s, context.getString(R.string.contactoscargados));
    }

    public boolean obtenerContactos() {

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

                    String nicknameName = "";
                    String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] params = new String[]{String.valueOf(id), ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE};
                    Cursor nickname = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, where, params, null);
                    while (nickname.moveToNext()) {
                        nicknameName = nickname.getString(nickname.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));
                    }
                    nickname.close();
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
                            if (nicknameName!=null && nicknameName.equalsIgnoreCase("admin"))
                                tbContactos.setAdmin(true);
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
            return false;
        } finally {
            ActiveAndroid.endTransaction();
        }
        return true;
    }

    public Context getContext() {
        return context;
    }
}
