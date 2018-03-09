package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 08/03/2018.
 */

public class EtapaPruebas {

    int id;
    int num;
    Usuario user;
    Persona persona;
    TipoPruebas tipoPruebas;
    int id_evaluacion;
    String Fecha_Registro;


    public final static EtapaPruebas TIPO_PRUEBAS=new EtapaPruebas();

    public EtapaPruebas(){

    }


    public String getFecha_Registro() {
        return Fecha_Registro;
    }

    public void setFecha_Registro(String fecha_Registro) {
        Fecha_Registro = fecha_Registro;
    }

    public TipoPruebas getTipoPruebas() {
        return tipoPruebas;
    }

    public void setTipoPruebas(TipoPruebas tipoPruebas) {
        this.tipoPruebas = tipoPruebas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getId_evaluacion() {
        return id_evaluacion;
    }

    public void setId_evaluacion(int id_evaluacion) {
        this.id_evaluacion = id_evaluacion;
    }
}
