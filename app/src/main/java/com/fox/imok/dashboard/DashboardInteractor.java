package com.fox.imok.dashboard;

import android.content.Context;
import android.telephony.SmsManager;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.fox.imok.R;
import com.fox.imok.dashboard.tareas.GuardarContactos;
import com.fox.imok.domain.BaseInteractor;
import com.fox.imok.domain.ConstantsShared;
import com.fox.imok.domain.bd.TBContactos;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by NestorSo on 28/09/2017.
 */

public class DashboardInteractor extends BaseInteractor {

    public DashboardInteractor(Context mContext) {
        super(mContext);
    }

    public void obtenerContactos(Callback callback){
        callback.setContactos(new Select().from(TBContactos.class).<TBContactos>execute());
    }
    public void cargarContactos(Callback callback){
        guardarInicializacion(false);
        eliminarContactos();
        new GuardarContactos(getContext(),callback).execute("");
    }
    public void guardarInicializacion(boolean value){
        getEditor().putBoolean(ConstantsShared.INICIALIZACION.name(), value);
        getEditor().apply();
    }

    public void enviarSms(List<TBContactos> contactos, int indice, Callback callback){
        if(getSharedPreferences().getBoolean(ConstantsShared.INICIALIZACION.name(), false))
            if(indice<contactos.size()){
                TBContactos contacto = contactos.get(indice);
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(contacto.getTelefono(), null, getContext().getString(R.string.mensajeayuda), null, null);
                contacto.setMensajeEnvio(getContext().getString(R.string.mensajeayuda));
                Date currentTime = Calendar.getInstance().getTime();
                contacto.setFechaHoraEnvio(currentTime.getTime());
                contacto.save();
                enviarSms(contactos, indice+1, callback);
            }else
                callback.envioSmsOk();
        else
            callback.errorSms(getContext().getString(R.string.inicializar));

    }

    private void eliminarContactos(){
        new Delete().from(TBContactos.class).execute();
    }



    public interface Callback {
        void errorSms(String mensaje);
        void setContactos(List<TBContactos> tbContactos);
        void cargaContactosOk();
        void envioSmsOk();
    }

}
