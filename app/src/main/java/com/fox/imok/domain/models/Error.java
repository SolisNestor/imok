package com.fox.imok.domain.models;

/**
 * Created by nestorso on 21/10/2017.
 */

public class Error {
    private String mensaje;
    private int resourceMensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getResourceMensaje() {
        return resourceMensaje;
    }

    public void setResourceMensaje(int resourceMensaje) {
        this.resourceMensaje = resourceMensaje;
    }
}
