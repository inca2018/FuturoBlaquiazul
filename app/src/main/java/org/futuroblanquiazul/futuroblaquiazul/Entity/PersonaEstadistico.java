package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 19/03/2018.
 */

public class PersonaEstadistico {

    int num;
    Persona persona;
    int tipo_alineacion;
    Posicion posicion_persona;
    int camiseta_persona;
    int titular;
    PuntosEstadisticos PuntosPrimerTiempo;
    PuntosEstadisticos PuntosSegundoTiempo;
    int estado;

    public PersonaEstadistico(){

    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public int getTipo_alineacion() {
        return tipo_alineacion;
    }

    public void setTipo_alineacion(int tipo_alineacion) {
        this.tipo_alineacion = tipo_alineacion;
    }

    public Posicion getPosicion_persona() {
        return posicion_persona;
    }

    public void setPosicion_persona(Posicion posicion_persona) {
        this.posicion_persona = posicion_persona;
    }


    public int getCamiseta_persona() {
        return camiseta_persona;
    }

    public void setCamiseta_persona(int camiseta_persona) {
        this.camiseta_persona = camiseta_persona;
    }

    public int getTitular() {
        return titular;
    }

    public void setTitular(int titular) {
        this.titular = titular;
    }

    public PuntosEstadisticos getPuntosPrimerTiempo() {
        return PuntosPrimerTiempo;
    }

    public void setPuntosPrimerTiempo(PuntosEstadisticos puntosPrimerTiempo) {
        PuntosPrimerTiempo = puntosPrimerTiempo;
    }

    public PuntosEstadisticos getPuntosSegundoTiempo() {
        return PuntosSegundoTiempo;
    }

    public void setPuntosSegundoTiempo(PuntosEstadisticos puntosSegundoTiempo) {
        PuntosSegundoTiempo = puntosSegundoTiempo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
