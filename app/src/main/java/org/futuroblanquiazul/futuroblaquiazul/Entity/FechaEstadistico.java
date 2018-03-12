package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 12/03/2018.
 */

public class FechaEstadistico {
    int id;
    int num;
    EventoEstadistico evento;
    Usuario usuario;
    Oponente oponente;
    String fecha_Realizacion;
    String fecha_registro;
    String Observacion;
    int estado;

    public final static FechaEstadistico FECHA_ESTADISTICO_TEMP=new FechaEstadistico();
    public final static FechaEstadistico FECHA_ESTADISTICO_CREACION=new FechaEstadistico();
    public FechaEstadistico(){

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

    public EventoEstadistico getEvento() {
        return evento;
    }

    public void setEvento(EventoEstadistico evento) {
        this.evento = evento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Oponente getOponente() {
        return oponente;
    }

    public void setOponente(Oponente oponente) {
        this.oponente = oponente;
    }

    public String getFecha_Realizacion() {
        return fecha_Realizacion;
    }

    public void setFecha_Realizacion(String fecha_Realizacion) {
        this.fecha_Realizacion = fecha_Realizacion;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String observacion) {
        Observacion = observacion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
