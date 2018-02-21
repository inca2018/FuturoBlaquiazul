package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 21/02/2018.
 */

public class Seguimiento {


    int id;
    String Codigo_Seguimiento;
    Usuario usuario;
    Persona persona;
    int id_campo_social;
    int id_campo_psico;
    int titular;
    int capitan;
    Unidad_Territorial Departamento;
    Unidad_Territorial Provincia;
    Unidad_Territorial Distrito;
    String Nombre_Competencia;
    String Nombre_Rival;
    int Minutos_Juego;
    int Cantidad_Pierde_Balon;
    int Cantidad_Recupera_Balon;
    int Cantidad_Pase_Gol;
    int Cantidad_Dribbling;
    int Total_Puntaje;
    String Fecha_Registro;
    int Estado;
    int Goles;

   public final static Seguimiento SEGUIMIENTO=new Seguimiento();

    public Seguimiento(){

    }


    public int getGoles() {
        return Goles;
    }

    public void setGoles(int goles) {
        Goles = goles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo_Seguimiento() {
        return Codigo_Seguimiento;
    }

    public void setCodigo_Seguimiento(String codigo_Seguimiento) {
        Codigo_Seguimiento = codigo_Seguimiento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public int getId_campo_social() {
        return id_campo_social;
    }

    public void setId_campo_social(int id_campo_social) {
        this.id_campo_social = id_campo_social;
    }

    public int getId_campo_psico() {
        return id_campo_psico;
    }

    public void setId_campo_psico(int id_campo_psico) {
        this.id_campo_psico = id_campo_psico;
    }

    public int getTitular() {
        return titular;
    }

    public void setTitular(int titular) {
        this.titular = titular;
    }

    public int getCapitan() {
        return capitan;
    }

    public void setCapitan(int capitan) {
        this.capitan = capitan;
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

    public String getNombre_Competencia() {
        return Nombre_Competencia;
    }

    public void setNombre_Competencia(String nombre_Competencia) {
        Nombre_Competencia = nombre_Competencia;
    }

    public String getNombre_Rival() {
        return Nombre_Rival;
    }

    public void setNombre_Rival(String nombre_Rival) {
        Nombre_Rival = nombre_Rival;
    }

    public int getMinutos_Juego() {
        return Minutos_Juego;
    }

    public void setMinutos_Juego(int minutos_Juego) {
        Minutos_Juego = minutos_Juego;
    }

    public int getCantidad_Pierde_Balon() {
        return Cantidad_Pierde_Balon;
    }

    public void setCantidad_Pierde_Balon(int cantidad_Pierde_Balon) {
        Cantidad_Pierde_Balon = cantidad_Pierde_Balon;
    }

    public int getCantidad_Recupera_Balon() {
        return Cantidad_Recupera_Balon;
    }

    public void setCantidad_Recupera_Balon(int cantidad_Recupera_Balon) {
        Cantidad_Recupera_Balon = cantidad_Recupera_Balon;
    }

    public int getCantidad_Pase_Gol() {
        return Cantidad_Pase_Gol;
    }

    public void setCantidad_Pase_Gol(int cantidad_Pase_Gol) {
        Cantidad_Pase_Gol = cantidad_Pase_Gol;
    }

    public int getCantidad_Dribbling() {
        return Cantidad_Dribbling;
    }

    public void setCantidad_Dribbling(int cantidad_Dribbling) {
        Cantidad_Dribbling = cantidad_Dribbling;
    }

    public int getTotal_Puntaje() {
        return Total_Puntaje;
    }

    public void setTotal_Puntaje(int total_Puntaje) {
        Total_Puntaje = total_Puntaje;
    }

    public String getFecha_Registro() {
        return Fecha_Registro;
    }

    public void setFecha_Registro(String fecha_Registro) {
        Fecha_Registro = fecha_Registro;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int estado) {
        Estado = estado;
    }
}
