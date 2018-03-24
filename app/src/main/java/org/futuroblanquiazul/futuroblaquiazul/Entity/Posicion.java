package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 15/02/2018.
 */

public class Posicion {

    int num;
    int id;
    String Nombre_Posicione;
    int estado;


    public Posicion( ) {

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

    public String getNombre_Posicione() {
        return Nombre_Posicione;
    }

    public void setNombre_Posicione(String nombre_Posicione) {
        Nombre_Posicione = nombre_Posicione;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
