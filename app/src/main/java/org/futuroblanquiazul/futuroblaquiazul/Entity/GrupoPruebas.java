package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 02/03/2018.
 */

public class GrupoPruebas {
    int num;
    int id;
    Usuario usuario;
    String Descripcion;
    String Observaciones;
    String Fecha_registro;
    String Fecha_realizacion;
    Plantel plantel;
    int Estado;

    public GrupoPruebas(){

    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }

    public String getFecha_registro() {
        return Fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        Fecha_registro = fecha_registro;
    }

    public String getFecha_realizacion() {
        return Fecha_realizacion;
    }

    public void setFecha_realizacion(String fecha_realizacion) {
        Fecha_realizacion = fecha_realizacion;
    }

    public Plantel getPlantel() {
        return plantel;
    }

    public void setPlantel(Plantel plantel) {
        this.plantel = plantel;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int estado) {
        Estado = estado;
    }
}
