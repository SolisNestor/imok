package com.fox.imok.dashboard;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fox.imok.R;
import com.fox.imok.domain.bd.TBContactos;
import com.fox.imok.domain.permisos.PermisoObj;
import com.fox.imok.domain.permisos.Permisos;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends AppCompatActivity implements DashboardContract.View {

    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.txtContactos)
    TextView txtContactos;
    private DashboardContract.Presenter presenter;
    private Permisos permisos;
    private PermisoObj permisosObj = new PermisoObj(new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS
    }, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        new DashboardPresenter(this, new DashboardInteractor(this));
    }

    /**
     * OnStart()
     * Se utiliza para iniciar EventBus dentro del presentador
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null)
            presenter.onStart();
    }

    /**
     * onStop()
     * Se utiliza para detener Eventbus cuando no se esta viendo el app
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null)
            presenter.onStop();
    }

    /**
     * Recibe el presenter para poder ser utilizado durante toda la actividad
     * @param presenter
     */
    @Override
    public void setPresenter(DashboardContract.Presenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Muesta o oculta el progress
     * @param show
     */
    @Override
    public void setProgress(boolean show) {
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * Recibe mensajes del presenter y los muestra por medio de un toast
     * @param mensaje
     */
    @Override
    public void message(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    /**
     * Se ejecuta cuando los mensajes han sido enviados correctamente.
     */
    @Override
    public void smsOk() {
        setProgress(false);
        message(getString(R.string.mensajes_entregados));
    }

    /**
     * Finaliza la actividad
     */
    @Override
    public void finalizar() {
        finish();
    }

    /**
     * Se produciera un error al enviar un sms, se mostraria el mensaje en pantalla,
     * se hace un método aparte por si se quiere mostrar un dialogo personalizado.
     * @param mensaje
     */
    @Override
    public void errorSMS(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }


    /**
     * Se utiliza para solicitar permisos si el SO es igual o mayor a android 6 y
     * carga los contactos a una base de datos.
     */
    @OnClick(R.id.btn)
    public void inicializar() {
        if (permisos == null)
            permisos = new Permisos(this, permisosObj);
        if (!permisos.comprobarPermisos()) {
            permisos.solicitarPermisos();
            return;
        }
        presenter.inicializarContactos();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permisos.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            message(getString(R.string.continuarahora));
            inicializar();
        } else
            message(getString(R.string.aceptarpermisos));
    }

    /**
     * Se utiliza para cargar los contactos que se les ha enviado SMS.
     * @param tbContactos
     */
    @Override
    public void setContactos(List<TBContactos> tbContactos) {
        txtContactos.setText("");
        String contactos ="";
        for (int i = 0; i < tbContactos.size() ; i++) {
            TBContactos contacto = tbContactos.get(i);
            contactos+=contacto.getNombre()+" "+contacto.getTelefono()+" "+contacto.isOk()+" \n";
        }
        txtContactos.setText(contactos);
    }

    /**
     * Actualiza la lista de los contactos que han enviado SMS.
     * @param tbContactos
     */

    @Override
    public void actualizarContactos(List<TBContactos> tbContactos) {
        txtContactos.setText("");
        String contactos ="";
        for (int i = 0; i < tbContactos.size() ; i++) {
            TBContactos contacto = tbContactos.get(i);
            contactos+=contacto.getNombre()+" "+contacto.getTelefono()+" "+contacto.isOk()+" \n";
        }
        txtContactos.setText(contactos);
    }

    @Override
    public void cargaContactosOk() {
        setProgress(false);
        message(getString(R.string.cargaexitosa));
    }

    /**
     * Envia los mensajes de texto a los contactos almacenados en la base de datos.
     */
    @OnClick(R.id.btnSendSMS)
    public void onViewClicked() {
        presenter.obtenerContactos();
        presenter.enviarSms();
    }
}
