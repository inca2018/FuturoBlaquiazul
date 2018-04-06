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
    PuntosEstadisticos PrimerTiempo,SegundoTiempo;
    int estado;

    boolean activo;

    int total_Puntos;
    int tiempo_jugado;

    int entrante;
    int saliente;
    int Minuto_Cambio;

    boolean cambiado;

    boolean expulsado;

    public PersonaEstadistico(){

    }


    public boolean isCambiado() {
        return cambiado;
    }

    public boolean isExpulsado() {
        return expulsado;
    }

    public void setExpulsado(boolean expulsado) {
        this.expulsado = expulsado;
    }

    public void setCambiado(boolean cambiado) {
        this.cambiado = cambiado;
    }

    public int getEntrante() {
        return entrante;
    }

    public int getMinuto_Cambio() {
        return Minuto_Cambio;
    }

    public void setMinuto_Cambio(int minuto_Cambio) {
        Minuto_Cambio = minuto_Cambio;
    }

    public void setEntrante(int entrante) {
        this.entrante = entrante;
    }

    public int getSaliente() {
        return saliente;
    }

    public void setSaliente(int saliente) {
        this.saliente = saliente;
    }

    public int getTotal_Puntos() {
        return total_Puntos;
    }

    public void setTotal_Puntos(int total_Puntos) {
        this.total_Puntos = total_Puntos;
    }

    public int getTiempo_jugado() {
        return tiempo_jugado;
    }

    public void setTiempo_jugado(int tiempo_jugado) {
        this.tiempo_jugado = tiempo_jugado;
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

    public PuntosEstadisticos getPrimerTiempo() {
        return PrimerTiempo;
    }

    public void setPrimerTiempo(PuntosEstadisticos primerTiempo) {
        PrimerTiempo = primerTiempo;
    }

    public PuntosEstadisticos getSegundoTiempo() {
        return SegundoTiempo;
    }

    public void setSegundoTiempo(PuntosEstadisticos segundoTiempo) {
        SegundoTiempo = segundoTiempo;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
