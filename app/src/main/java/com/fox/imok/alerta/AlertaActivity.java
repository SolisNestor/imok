package com.fox.imok.alerta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fox.imok.R;
import com.fox.imok.domain.models.Error;
import com.fox.imok.domain.models.Progreso;

public class AlertaActivity extends AppCompatActivity implements AlertaContrato.Vista {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void ocultarBotonPrincipal() {

    }

    @Override
    public void mostrarProgreso(Progreso progreso) {

    }

    @Override
    public void mostrarAlertaEnProceso() {

    }

    @Override
    public void mostrarError(Error error) {

    }
}
