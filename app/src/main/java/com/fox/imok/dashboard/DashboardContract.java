package com.fox.imok.dashboard;

import com.fox.imok.domain.BaseView;
import com.fox.imok.domain.bd.TBContactos;

import java.util.List;

/**
 * Created by NestorSo on 28/09/2017.
 */

public interface DashboardContract {

    interface View extends BaseView<Presenter> {
        void smsOk();
        void cargaContactosOk();
        void errorSMS(String mensaje);
        void actualizarContactos(List<TBContactos> tbContactos);
    }

    interface Presenter{
        void onStart();
        void onStop();
        void enviarSms();
        void inicializarContactos();
        void obtenerContactos();
        void actualizarContactos();
    }
}
