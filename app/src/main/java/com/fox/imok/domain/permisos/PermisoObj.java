package com.fox.imok.domain.permisos;

/**
 * Created by nesto on 2/08/2016.
 */
public class PermisoObj {
    private String[] permisos;
    private int request;

    public PermisoObj(String[] permisos, int request) {
        this.permisos = permisos;
        this.request= request;
    }
    public String[] getPermisos() {
        return permisos;
    }
    public void setPermisos(String[] permisos) {
        this.permisos = permisos;
    }
    public int getRequest() {
        return request;
    }
    public void setRequest(int request) {
        this.request = request;
    }
}

