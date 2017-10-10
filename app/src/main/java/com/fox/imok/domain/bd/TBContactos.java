package com.fox.imok.domain.bd;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by NestorSo on 28/09/2017.
 */
@Table(name = "contactos")
public class TBContactos extends Model {

    @Column(name = "nombre")
    private String nombre;
    @Column(name = "telefono",  unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String telefono;
    @Column(name = "fechahoraenvio")
    private long fechaHoraEnvio;
    @Column(name = "mensajeenvio")
    private String mensajeEnvio;
    @Column(name = "fechahorarespuesta")
    private long fechaHoraRespuesta;
    @Column(name = "mensajerespuesta")
    private String mensajeRespuesta;
    @Column(name = "ok")
    private boolean ok;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        telefono= telefono.trim();
        telefono= telefono.replaceAll(" ","");
        this.telefono = telefono;
    }

    public long getFechaHoraEnvio() {
        return fechaHoraEnvio;
    }

    public void setFechaHoraEnvio(long fechaHoraEnvio) {
        this.fechaHoraEnvio = fechaHoraEnvio;
    }

    public String getMensajeEnvio() {
        return mensajeEnvio;
    }

    public void setMensajeEnvio(String mensajeEnvio) {
        this.mensajeEnvio = mensajeEnvio;
    }

    public long getFechaHoraRespuesta() {
        return fechaHoraRespuesta;
    }

    public void setFechaHoraRespuesta(long fechaHoraRespuesta) {
        this.fechaHoraRespuesta = fechaHoraRespuesta;
    }

    public String getMensajeRespuesta() {
        return mensajeRespuesta;
    }

    public void setMensajeRespuesta(String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
