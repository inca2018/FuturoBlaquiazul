package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 21/03/2018.
 */

public class Perfil {
    int id;
    String Nombre_Perfil;

    public Perfil(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_Perfil() {
        return Nombre_Perfil;
    }

    public void setNombre_Perfil(String nombre_Perfil) {
        Nombre_Perfil = nombre_Perfil;
    }
}
