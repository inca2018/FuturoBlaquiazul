package org.futuroblanquiazul.futuroblaquiazul.Entity;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesus Inca on 15/03/2018.
 */

public class Formacion {

    int id;
    int user;
    int evento;
    int persona;
    int formacion;
    int posicion;
    int estado;
    String Fecha_registro;

    public Formacion(){
    }

    public static final List<Formacion> LISTA_FORMACION=new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getEvento() {
        return evento;
    }

    public void setEvento(int evento) {
        this.evento = evento;
    }

    public int getPersona() {
        return persona;
    }

    public void setPersona(int persona) {
        this.persona = persona;
    }

    public int getFormacion() {
        return formacion;
    }

    public void setFormacion(int formacion) {
        this.formacion = formacion;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getFecha_registro() {
        return Fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        Fecha_registro = fecha_registro;
    }
}
