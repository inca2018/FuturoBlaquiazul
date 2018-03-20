package org.futuroblanquiazul.futuroblaquiazul.Utils;

import java.util.List;

/**
 * Created by Jesus Inca on 19/03/2018.
 */

public class Estadistico_Gestion {

    int cantidad_tiempos;
    int minutos_x_tiempo;
    int primer_aviso;
    int segundo_aviso;

    int posesion_1erTiempo;
    int getPosesion_2doTiempo;

    int tiempo_actual;

    List<String> Nombres_Personas;

    public static final Estadistico_Gestion TEMP=new Estadistico_Gestion();
    public Estadistico_Gestion(){

    }

    public List<String> getNombres_Personas() {
        return Nombres_Personas;
    }

    public void setNombres_Personas(List<String> nombres_Personas) {
        Nombres_Personas = nombres_Personas;
    }



    public int getCantidad_tiempos() {
        return cantidad_tiempos;
    }

    public void setCantidad_tiempos(int cantidad_tiempos) {
        this.cantidad_tiempos = cantidad_tiempos;
    }

    public int getMinutos_x_tiempo() {
        return minutos_x_tiempo;
    }

    public void setMinutos_x_tiempo(int minutos_x_tiempo) {
        this.minutos_x_tiempo = minutos_x_tiempo;
    }

    public int getPrimer_aviso() {
        return primer_aviso;
    }

    public void setPrimer_aviso(int primer_aviso) {
        this.primer_aviso = primer_aviso;
    }

    public int getSegundo_aviso() {
        return segundo_aviso;
    }

    public void setSegundo_aviso(int segundo_aviso) {
        this.segundo_aviso = segundo_aviso;
    }

    public int getPosesion_1erTiempo() {
        return posesion_1erTiempo;
    }

    public void setPosesion_1erTiempo(int posesion_1erTiempo) {
        this.posesion_1erTiempo = posesion_1erTiempo;
    }

    public int getGetPosesion_2doTiempo() {
        return getPosesion_2doTiempo;
    }

    public void setGetPosesion_2doTiempo(int getPosesion_2doTiempo) {
        this.getPosesion_2doTiempo = getPosesion_2doTiempo;
    }

    public int getTiempo_actual() {
        return tiempo_actual;
    }

    public void setTiempo_actual(int tiempo_actual) {
        this.tiempo_actual = tiempo_actual;
    }
}
