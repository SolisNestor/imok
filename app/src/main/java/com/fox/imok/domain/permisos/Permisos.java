package com.fox.imok.domain.permisos;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class Permisos implements PermisosListener {


    private Activity activity;
    private PermisoObj permisosObj;

    public Permisos(Activity activity, PermisoObj permisosObj) {
        this.activity = activity;
        this.permisosObj= permisosObj;
    }

    @Override
    public void solicitarPermisos() {
        ActivityCompat.requestPermissions(getActivity(),
                permisosObj.getPermisos(),
                permisosObj.getRequest());
    }

    @Override
    public boolean comprobarPermisos() {
        boolean isAceptado=true;

        if(permisosObj.getPermisos().length>0)
        {
            for (int i = 0; i <permisosObj.getPermisos().length ; i++) {
                if(!permisoOtorgado(permisosObj.getPermisos()[i]))
                {
                    isAceptado= false;
                }
            }
        }
        return isAceptado;
    }

    @Override
    public boolean onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode==permisosObj.getRequest())
        {
            if(permisosOtorgados(grantResults)) {
                return true;
            }
        }
        return false;
    }

    private boolean permisosOtorgados( int[] grantResults)
    {
        boolean isAceptado=true;
        if(grantResults.length==0)
            isAceptado=false;
        for (int i = 0; i < grantResults.length ; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                isAceptado= false;
            }
        }

        return isAceptado;
    }

    private boolean permisoOtorgado(String permiso)
    {
        return PackageManager.PERMISSION_GRANTED== ContextCompat.checkSelfPermission(activity,permiso);
    }

    public static boolean permisoUtilCheck(Context context, String permiso)
    {
        return ContextCompat.checkSelfPermission(context,permiso)== PackageManager.PERMISSION_GRANTED;
    }

    public Activity getActivity() {
        return activity;
    }




}
