package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 01/03/2018.
 */

public class Solicitudes {

    int id;
    String nombre_solicitud;
    int dni_solicitud;
    int estado;
    String fecha_re;

    public Solicitudes(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_solicitud() {
        return nombre_solicitud;
    }

    public void setNombre_solicitud(String nombre_solicitud) {
        this.nombre_solicitud = nombre_solicitud;
    }

    public int getDni_solicitud() {
        return dni_solicitud;
    }

    public void setDni_solicitud(int dni_solicitud) {
        this.dni_solicitud = dni_solicitud;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getFecha_re() {
        return fecha_re;
    }

    public void setFecha_re(String fecha_re) {
        this.fecha_re = fecha_re;
    }
}
