package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 12/03/2018.
 */

public class EventoEstadistico {

    int id;
    String Descripcion_Nombre_evento;
    String Detalle_Evento;
    Unidad_Territorial Departamento;
    Unidad_Territorial Provincia;
    Unidad_Territorial Distrito;
    Usuario usuario;
    Plantel plantel;
    String Fecha_Registro;
    String foto;
    int estado;
    int estado2;
    EventoEstadistico Evento_Temporal;


    public static final EventoEstadistico EVENTO_TEMP=new EventoEstadistico();
    public static final EventoEstadistico EVENTO_CREACION=new EventoEstadistico();


    public EventoEstadistico(){

    }


    public EventoEstadistico getEvento_Temporal() {
        return Evento_Temporal;
    }

    public void setEvento_Temporal(EventoEstadistico evento_Temporal) {
        Evento_Temporal = evento_Temporal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion_Nombre_evento() {
        return Descripcion_Nombre_evento;
    }

    public void setDescripcion_Nombre_evento(String descripcion_Nombre_evento) {
        Descripcion_Nombre_evento = descripcion_Nombre_evento;
    }

    public String getDetalle_Evento() {
        return Detalle_Evento;
    }

    public void setDetalle_Evento(String detalle_Evento) {
        Detalle_Evento = detalle_Evento;
    }

    public Unidad_Territorial getDepartamento() {
        return Departamento;
    }

    public void setDepartamento(Unidad_Territorial departamento) {
        Departamento = departamento;
    }

    public Unidad_Territorial getProvincia() {
        return Provincia;
    }

    public void setProvincia(Unidad_Territorial provincia) {
        Provincia = provincia;
    }

    public Unidad_Territorial getDistrito() {
        return Distrito;
    }

    public void setDistrito(Unidad_Territorial distrito) {
        Distrito = distrito;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Plantel getPlantel() {
        return plantel;
    }

    public void setPlantel(Plantel plantel) {
        this.plantel = plantel;
    }

    public String getFecha_Registro() {
        return Fecha_Registro;
    }

    public void setFecha_Registro(String fecha_Registro) {
        Fecha_Registro = fecha_Registro;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getEstado2() {
        return estado2;
    }

    public void setEstado2(int estado2) {
        this.estado2 = estado2;
    }

    public void Vaciar_Temporal(){
        EVENTO_CREACION.setDetalle_Evento(null);
        EVENTO_CREACION.setDescripcion_Nombre_evento(null);
        EVENTO_CREACION.setEstado2(0);
        EVENTO_CREACION.setEstado2(0);
        EVENTO_CREACION.setFoto(null);
        EVENTO_CREACION.setPlantel(null);
        EVENTO_CREACION.setUsuario(null);
        EVENTO_CREACION.setDepartamento(null);
        EVENTO_CREACION.setProvincia(null);
        EVENTO_CREACION.setDistrito(null);
        EVENTO_CREACION.setId(0);
        EVENTO_CREACION.setFecha_Registro(null);

    }
}
