package com.fox.imok.alerta;

import android.content.Context;

import com.fox.imok.domain.models.Error;
import com.fox.imok.domain.models.Progreso;

/**
 * Created by nestorso on 21/10/2017.
 */

public interface AlertaContrato {
    interface Vista{
        void ocultarBotonPrincipal();
        void mostrarProgreso(Progreso progreso);
        void mostrarAlertaEnProceso();
        void mostrarError(Error error);
    }

    interface Presentador{
        void validarAlertaEnProceso(Context context);
        void enviarAlerta();
    }
}
