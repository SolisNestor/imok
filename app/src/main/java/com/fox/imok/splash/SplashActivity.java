package com.fox.imok.splash;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.fox.imok.R;
import com.fox.imok.alerta.AlertaActivity;
import com.fox.imok.dashboard.DashboardActivity;
import com.fox.imok.domain.bd.TBContactos;
import com.fox.imok.domain.models.ContactosOperaciones;
import com.fox.imok.domain.models.ContactosOperacionesContrato;
import com.fox.imok.domain.permisos.PermisoObj;
import com.fox.imok.domain.permisos.Permisos;

import java.util.List;

public class SplashActivity extends AppCompatActivity implements SplashContrato.Vista, ContactosOperacionesContrato.CallbackContactosOperaciones {

    private SplashContrato.Presentador presentador;
    private Permisos permisos;
    private PermisoObj permisosObj = new PermisoObj(new String[]{
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.CALL_PHONE
    }, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        presentador = new SplashPresentador(this);
        permisos = new Permisos(this, permisosObj);
        if (!permisos.comprobarPermisos())
            permisos.solicitarPermisos();
        else
            presentador.iniciarTiempo();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permisos.onRequestPermissionsResult(requestCode, permissions, grantResults)) {

            presentador.iniciarTiempo();
        } else
            mostrarMensaje(getString(R.string.aceptarpermisos));


    }

    @Override
    public void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cambiarPantalla() {
        ContactosOperacionesContrato contactosOperaciones = new ContactosOperaciones();
        contactosOperaciones.setCallbackContactosOperaciones(this);
        contactosOperaciones.initContactos(new ContactosOperacionesContrato.AgendaCallback() {
            @Override
            public void onResult(boolean result, String message) {
                abrirPantalla();
            }
        });

    }

    @Override
    public void onResult(boolean result, String message) {
        abrirPantalla();
    }

    @Override
    public void requestPermission(String permiso) {
        permisosObj = new PermisoObj(new String[]{
                permiso
        }, 1);
        permisos = new Permisos(this, permisosObj);
        permisos.solicitarPermisos();
    }

    private void abrirPantalla() {
        Intent i = new Intent(SplashActivity.this, AlertaActivity.class);
        startActivity(i);
        SplashActivity.this.finish();
    }


}
