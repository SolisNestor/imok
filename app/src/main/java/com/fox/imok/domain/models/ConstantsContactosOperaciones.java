package com.fox.imok.domain.models;

import com.fox.imok.R;

/**
 * Created by NestorSo on 25/10/2017.
 */

public enum ConstantsContactosOperaciones {
    PERMISO_LEER_CONTACTO(R.string.contactosoperaciones_permiso_leercontacto),
    PERMISO_LLAMAR(R.string.contactosoperaciones_permiso_llamar),
    PERMISO_ENVIAR_SMS(R.string.contactosoperaciones_permiso_enviarsms),
    OPERACION_EXITOSA(R.string.contactosoperaciones_operacionexitosa),
    CONTACTO_NO_EXISTE(R.string.contactosoperaciones_contactonoexiste);

    private int mensaje;

    ConstantsContactosOperaciones(int mensaje) {
        this.mensaje = mensaje;
    }

    public int getMensaje() {
        return mensaje;
    }
}
