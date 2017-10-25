package com.fox.imok.reporte;

import com.activeandroid.query.Select;
import com.fox.imok.domain.bd.TBContactos;
import com.fox.imok.domain.models.ContactosOperaciones;
import com.fox.imok.domain.models.ContactosOperacionesContrato;
import com.fox.imok.domain.models.Progreso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NestorSo on 25/10/2017.
 */

public class ListadoPresenter implements ContratoReporte.Presenter, AdapterLista.OnClickContacto, ContactosOperacionesContrato.CallbackContactosOperaciones {


    private ContratoReporte.View view;
    private ContactosOperacionesContrato contactosOperaciones;

    public ListadoPresenter(ContratoReporte.View view) {
        this.view = view;
        contactosOperaciones = new ContactosOperaciones();
        contactosOperaciones.setCallbackContactosOperaciones(this);
    }

    @Override
    public void buscarContactos(int tab) {
        List<TBContactos> contactos = new ArrayList<>();
        if(tab==0)
            contactos = new Select().from(TBContactos.class).execute();
        else if(tab==1)
            contactos = new Select().from(TBContactos.class).where("ok=?", true).execute();
        else if(tab==2)
            contactos = new Select().from(TBContactos.class).where("ok=? and fechahorarespuesta > ?", false, 0).execute();
        else if(tab==3)
            contactos = new Select().from(TBContactos.class).where("ok=? and fechahorarespuesta = ?", false, 0).execute();
        view.setContactos(contactos);
    }

    @Override
    public void reenviarSMS() {
        view.showProgress(new Progreso(0));
        contactosOperaciones.enviarSMS();
    }

    @Override
    public AdapterLista.OnClickContacto getListener() {
        return this;
    }

    @Override
    public ContactosOperacionesContrato getContactosOperaciones() {
        return contactosOperaciones;
    }

    @Override
    public void call(TBContactos contacto) {
        contactosOperaciones.llamar(contacto);
    }

    @Override
    public void onResult(boolean result, String message) {
        view.showMessage(message);
        view.showProgress(new Progreso(-1));
    }

    @Override
    public void requestPermission(String permiso) {
        view.showPermission(permiso);
    }
}
