package org.futuroblanquiazul.futuroblaquiazul.Entity;


import java.util.ArrayList;
import java.util.List;

public class CampoEstadistico {
    int id;
    int cod;
    int sector;
    OpcionEstadistico Opcion;
    Persona persona;
    int estado;
    int altura;
    int ancho;
    int Drawable;
    String Dato;

    public static final List<CampoEstadistico> LISTACAMPOESTADISTICO=new ArrayList<CampoEstadistico>();
    public static final List<CampoEstadistico> LISTACAMPOESTADISTICO_TIEMPO_1=new ArrayList<CampoEstadistico>();
    public static final List<CampoEstadistico> LISTACAMPOESTADISTICO_TIEMPO_2=new ArrayList<CampoEstadistico>();
    public CampoEstadistico(int id,int cod,int sector, OpcionEstadistico opcion, Persona persona, int estado, int altura, int ancho, int drawable, String dato) {
        this.id=id;
        this.cod = cod;
        this.sector=sector;
        Opcion = opcion;
        this.persona = persona;
        this.estado = estado;
        this.altura = altura;
        this.ancho = ancho;
        Drawable = drawable;
        Dato = dato;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }

    public int getDrawable() {
        return Drawable;
    }
    public void setDrawable(int drawable) {
        Drawable = drawable;
    }
    public int getAncho() {
        return ancho;
    }
    public void setAncho(int ancho) {
        this.ancho = ancho;
    }
    public String getDato() {
        return Dato;
    }
    public void setDato(String dato) {
        Dato = dato;
    }
    public int getAltura() {
        return altura;
    }
    public void setAltura(int altura) {
        this.altura = altura;
    }
    public int getCod() {
        return cod;
    }
    public void setCod(int cod) {
        this.cod = cod;
    }
    public OpcionEstadistico getOpcion() {
        return Opcion;
    }
    public void setOpcion(OpcionEstadistico opcion) {
        Opcion = opcion;
    }
    public Persona getPersona() {
        return persona;
    }
    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    public int getEstado() {
        return estado;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }

}
