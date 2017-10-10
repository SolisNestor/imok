package com.fox.imok.dashboard.eventbus;

/**
 * Created by nestorso on 4/10/2017.
 */

public class ContactoEventBus {
    private String numero;
    private String nombre;

    public ContactoEventBus() {
        this.numero = "";
        this.nombre = "";
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
