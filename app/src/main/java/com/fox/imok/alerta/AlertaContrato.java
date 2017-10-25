package com.fox.imok.alerta;

import android.content.Context;

import com.fox.imok.domain.models.Error;
import com.fox.imok.domain.models.Progreso;

/**
 * Created by nestorso on 21/10/2017.
 */

public interface AlertaContrato {
    interface Vista{
        void ocultarBotonPrincipal(boolean show);
        void mostrarProgreso(Progreso progreso);
        void mostrarMensaje(String mensaje);
        void mostrarAlertaEnProceso();
        void mostrarError(Error error);
        void solicitarPermiso(String permiso);
    }

    interface Presentador{
        void enviarAlerta();
    }
}
