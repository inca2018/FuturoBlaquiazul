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
    String Fecha_Inicio;
    String Fecha_Fin;
    EventoEstadistico Evento_Temporal;

    int inicio_ano,inicio_mes,inicio_dia;
    int fin_ano,fin_mes,fin_dia;

    int estado_formacion;
    int estado_posiciones;


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

    public int getEstado_formacion() {
        return estado_formacion;
    }

    public void setEstado_formacion(int estado_formacion) {
        this.estado_formacion = estado_formacion;
    }

    public int getEstado_posiciones() {
        return estado_posiciones;
    }

    public void setEstado_posiciones(int estado_posiciones) {
        this.estado_posiciones = estado_posiciones;
    }

    public int getInicio_ano() {
        return inicio_ano;
    }

    public void setInicio_ano(int inicio_ano) {
        this.inicio_ano = inicio_ano;
    }

    public int getInicio_mes() {
        return inicio_mes;
    }

    public void setInicio_mes(int inicio_mes) {
        this.inicio_mes = inicio_mes;
    }

    public int getInicio_dia() {
        return inicio_dia;
    }

    public void setInicio_dia(int inicio_dia) {
        this.inicio_dia = inicio_dia;
    }

    public int getFin_ano() {
        return fin_ano;
    }

    public void setFin_ano(int fin_ano) {
        this.fin_ano = fin_ano;
    }

    public int getFin_mes() {
        return fin_mes;
    }

    public void setFin_mes(int fin_mes) {
        this.fin_mes = fin_mes;
    }

    public int getFin_dia() {
        return fin_dia;
    }

    public void setFin_dia(int fin_dia) {
        this.fin_dia = fin_dia;
    }

    public String getFecha_Inicio() {
        return Fecha_Inicio;
    }

    public void setFecha_Inicio(String fecha_Inicio) {
        Fecha_Inicio = fecha_Inicio;
    }

    public String getFecha_Fin() {
        return Fecha_Fin;
    }

    public void setFecha_Fin(String fecha_Fin) {
        Fecha_Fin = fecha_Fin;
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
