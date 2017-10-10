package com.fox.imok.domain.permisos;

/**
 * Created by nesto on 2/08/2016.
 */
public interface PermisosListener {
    void solicitarPermisos();
    boolean comprobarPermisos();
    boolean onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults);
}
