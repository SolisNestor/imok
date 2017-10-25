package com.fox.imok.alerta;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fox.imok.R;
import com.fox.imok.domain.models.Error;
import com.fox.imok.domain.models.Progreso;
import com.fox.imok.domain.permisos.PermisoObj;
import com.fox.imok.domain.permisos.Permisos;
import com.fox.imok.reporte.ReporteActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlertaActivity extends AppCompatActivity implements AlertaContrato.Vista {


    public static final String KEY_SHARED_ALERTA = "EN_ALERTA";
    private SharedPreferences sharedPreferences;
    private AlertaContrato.Presentador presentador;
    private Permisos permisos;
    private PermisoObj permisosObj = new PermisoObj(new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.CALL_PHONE
    }, 1);

    @BindView(R.id.alert_sent)
    LinearLayout progress;
    @BindView(R.id.btn_active_alert)
    RelativeLayout btn_active_alert;
    @BindView(R.id.sending_alert_progress)
    LinearLayout sendingAlertProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        presentador = new AlertaPresentador(this);
        permisos = new Permisos(this, permisosObj);
        if (!permisos.comprobarPermisos())
            permisos.solicitarPermisos();
        validarAlertaProceso();
    }

    private void validarAlertaProceso() {
        sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        boolean enAlerta = sharedPreferences.getBoolean(KEY_SHARED_ALERTA, false);
        if (enAlerta) {
            ocultarBotonPrincipal(true);
            mostrarAlertaEnProceso();
        } else if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getBoolean("alerta", false))
                activar();
        }
    }


    @Override
    public void ocultarBotonPrincipal(boolean hide) {
        btn_active_alert.setVisibility(hide ? View.GONE : View.VISIBLE);
        if (!hide)
            progress.setVisibility(View.GONE);
        sendingAlertProgress.setVisibility(View.GONE);

    }

    @Override
    public void mostrarProgreso(Progreso progreso) {
        boolean show = progreso.getProgress() >= 0;
        progress.setVisibility(show ? View.VISIBLE : View.GONE);
        btn_active_alert.setVisibility(show ? View.GONE : View.VISIBLE);
        sendingAlertProgress.setVisibility(View.GONE);
    }

    @Override
    public void mostrarAlertaEnProceso() {
        sendingAlertProgress.setVisibility(View.VISIBLE);
        btn_active_alert.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarError(Error error) {
        String mensaje;
        if (error.getMensaje() == null || error.getMensaje().isEmpty())
            mensaje = getString(error.getResourceMensaje());
        else
            mensaje = error.getMensaje();
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        actualizarAlerta(false);
    }

    @Override
    public void solicitarPermiso(String permiso) {
        permisosObj = new PermisoObj(new String[]{
                permiso
        }, 1);
        permisos = new Permisos(this, permisosObj);
        permisos.solicitarPermisos();
        actualizarAlerta(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permisos.onRequestPermissionsResult(requestCode, permissions, grantResults))
            mostrarMensaje(getString(R.string.continuarahora));
        else
            mostrarMensaje(getString(R.string.aceptarpermisos));

        actualizarAlerta(false);

    }


    @OnClick(R.id.btnActivar)
    public void activar() {
        presentador.enviarAlerta();
        mostrarProgreso(new Progreso(0));
        actualizarAlerta(true);
    }


    private void actualizarAlerta(boolean value) {
        if (!value)
            ocultarBotonPrincipal(false);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_SHARED_ALERTA, value);
        editor.apply();
        editor.commit();
    }

    @OnClick(R.id.btnReporteEmpleados)
    public void abrirReporte() {
        Intent i = new Intent(this, ReporteActivity.class);
        startActivity(i);
    }
}
