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


    int cantidad_tiempos;
    int minutos_x_tiempo;


    FechaEstadistico fecha_actual;

    public final static FechaEstadistico FECHA_ESTADISTICO_TEMP=new FechaEstadistico();
    public final static FechaEstadistico FECHA_ESTADISTICO_CREACION=new FechaEstadistico();
    public FechaEstadistico(){

    }

    public int getMinutos_x_tiempo() {
        return minutos_x_tiempo;
    }

    public void setMinutos_x_tiempo(int minutos_x_tiempo) {
        this.minutos_x_tiempo = minutos_x_tiempo;
    }

    public int getCantidad_tiempos() {
        return cantidad_tiempos;
    }

    public void setCantidad_tiempos(int cantidad_tiempos) {
        this.cantidad_tiempos = cantidad_tiempos;
    }

    public FechaEstadistico getFecha_actual() {
        return fecha_actual;
    }

    public void setFecha_actual(FechaEstadistico fecha_actual) {
        this.fecha_actual = fecha_actual;
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
