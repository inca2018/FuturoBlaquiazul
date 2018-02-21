package org.futuroblanquiazul.futuroblaquiazul.Utils;


import org.futuroblanquiazul.futuroblaquiazul.Entity.Posicion;

/**
 * Created by Jesus Inca on 15/02/2018.
 */

public class Diagnostico_Otros {

    Posicion sugerido1;
    Posicion sugerido2;
    Posicion sugerido3;

    String Nombre_Segurido3;
    String Lateralidad;
    int total_puntaje;

    int id_fisico;
    int id_capacidad;
    int id_social;
    int id_tecnico;
    int id_psico;

        public static final Diagnostico_Otros OTROS =new  Diagnostico_Otros();

    public Diagnostico_Otros() {
    }


    public int getId_fisico() {
        return id_fisico;
    }

    public void setId_fisico(int id_fisico) {
        this.id_fisico = id_fisico;
    }

    public int getId_capacidad() {
        return id_capacidad;
    }

    public void setId_capacidad(int id_capacidad) {
        this.id_capacidad = id_capacidad;
    }

    public int getId_social() {
        return id_social;
    }

    public void setId_social(int id_social) {
        this.id_social = id_social;
    }

    public int getId_tecnico() {
        return id_tecnico;
    }

    public void setId_tecnico(int id_tecnico) {
        this.id_tecnico = id_tecnico;
    }

    public int getId_psico() {
        return id_psico;
    }

    public void setId_psico(int id_psico) {
        this.id_psico = id_psico;
    }

    public Posicion getSugerido1() {
        return sugerido1;
    }

    public void setSugerido1(Posicion sugerido1) {
        this.sugerido1 = sugerido1;
    }

    public Posicion getSugerido2() {
        return sugerido2;
    }

    public void setSugerido2(Posicion sugerido2) {
        this.sugerido2 = sugerido2;
    }

    public Posicion getSugerido3() {
        return sugerido3;
    }

    public void setSugerido3(Posicion sugerido3) {
        this.sugerido3 = sugerido3;
    }

    public String getNombre_Segurido3() {
        return Nombre_Segurido3;
    }

    public void setNombre_Segurido3(String nombre_Segurido3) {
        Nombre_Segurido3 = nombre_Segurido3;
    }

    public String getLateralidad() {
        return Lateralidad;
    }

    public void setLateralidad(String lateralidad) {
        Lateralidad = lateralidad;
    }

    public int getTotal_puntaje() {
        return total_puntaje;
    }

    public void setTotal_puntaje(int total_puntaje) {
        this.total_puntaje = total_puntaje;
    }
}
