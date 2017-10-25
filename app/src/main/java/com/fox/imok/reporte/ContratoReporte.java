package com.fox.imok.reporte;

import com.fox.imok.domain.bd.TBContactos;
import com.fox.imok.domain.models.ContactosOperacionesContrato;
import com.fox.imok.domain.models.Error;
import com.fox.imok.domain.models.Progreso;

import java.util.List;

/**
 * Created by NestorSo on 25/10/2017.
 */

public interface ContratoReporte {
    interface View{
        void setContactos(List<TBContactos> tbContactos);
        void showError(Error error);
        void showMessage(String menssage);
        void showProgress(Progreso progreso);
        void showPermission(String permiso);
    }
    interface Presenter{
        void buscarContactos(int tab);
        void reenviarSMS();
        AdapterLista.OnClickContacto getListener();
        ContactosOperacionesContrato getContactosOperaciones();
    }
}
