package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 01/04/2018.
 */

public class GestionEstadistico {
    int tiempo_total;
    int tiempo_Extra;
    int Duracion_Tiempo;
    Fase PrimerTiempo;
    Fase SegundoTiempo;
    public static GestionEstadistico GESTOR=new GestionEstadistico();
    public GestionEstadistico(){

    }
    public Fase getPrimerTiempo() {
        return PrimerTiempo;
    }
    public void setPrimerTiempo(Fase primerTiempo) {
        PrimerTiempo = primerTiempo;
    }
    public Fase getSegundoTiempo() {
        return SegundoTiempo;
    }
    public void setSegundoTiempo(Fase segundoTiempo) {
        SegundoTiempo = segundoTiempo;
    }
    public int getTiempo_total() {
        return tiempo_total;
    }
    public void setTiempo_total(int tiempo_total) {
        this.tiempo_total = tiempo_total;
    }
    public int getTiempo_Extra() {
        return tiempo_Extra;
    }
    public void setTiempo_Extra(int tiempo_Extra) {
        this.tiempo_Extra = tiempo_Extra;
    }
    public int getDuracion_Tiempo() {
        return Duracion_Tiempo;
    }
    public void setDuracion_Tiempo(int duracion_Tiempo) {
        Duracion_Tiempo = duracion_Tiempo;
    }
}
