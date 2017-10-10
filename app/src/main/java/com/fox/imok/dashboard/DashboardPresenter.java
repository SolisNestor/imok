package com.fox.imok.dashboard;
import com.activeandroid.query.Select;
import com.fox.imok.dashboard.eventbus.ContactoEventBus;
import com.fox.imok.domain.bd.TBContactos;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by NestorSo on 28/09/2017.
 */

public class DashboardPresenter implements DashboardContract.Presenter, DashboardInteractor.Callback {

    private DashboardContract.View mView;
    private DashboardInteractor mInteractor;
    private List<TBContactos> listaContactos;

    public DashboardPresenter(DashboardContract.View mView, DashboardInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
        mView.setPresenter(this);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void enviarSms() {
        mView.setProgress(true);
        mInteractor.enviarSms(listaContactos, 0, this);

    }

    @Override
    public void inicializarContactos() {
        mView.setProgress(true);
        mInteractor.cargarContactos(this);
    }

    @Override
    public void obtenerContactos() {
        mInteractor.obtenerContactos(this);
    }

    @Override
    public void errorSms(String mensaje) {
        mView.setProgress(false);
        mView.message(mensaje);
    }

    @Override
    public void cargaContactosOk() {
        mView.cargaContactosOk();
        mInteractor.guardarInicializacion(true);
    }

    @Override
    public void envioSmsOk() {
        mView.smsOk();
    }

    @Override
    public void setContactos(List<TBContactos> tbContactos) {
        mInteractor.guardarInicializacion(true);
        this.listaContactos = tbContactos;
        mView.setContactos(tbContactos);
    }

    @Override
    public void actualizarContactos() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ContactoEventBus event) {
        mView.actualizarContactos(new Select().from(TBContactos.class).<TBContactos>execute());
    }

}
