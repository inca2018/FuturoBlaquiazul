package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 09/04/2018.
 */

public class FechaEntidad {
    int ano;
    int mes;
    int dia;
    String fecha_base;

    public FechaEntidad(){

    }

    public String getFecha_base() {
        return fecha_base;
    }

    public void setFecha_base(String fecha_base) {
        this.fecha_base = fecha_base;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }
}
