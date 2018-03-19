package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 19/03/2018.
 */

public class PosicionEstadistico {
    Persona persona;
    Posicion posicion;
    int num_Camiseta;


    public PosicionEstadistico(){

    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public int getNum_Camiseta() {
        return num_Camiseta;
    }

    public void setNum_Camiseta(int num_Camiseta) {
        this.num_Camiseta = num_Camiseta;
    }
}
