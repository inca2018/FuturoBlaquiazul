package org.futuroblanquiazul.futuroblaquiazul.Entity;


import java.util.ArrayList;
import java.util.List;

public class Campo {
    int cod;
    String dato;
    int cant;
    int altura;
    int ancho;
    int Drawable;

    public static final List<Campo> LISTACAMPO=new ArrayList<Campo>();

    public Campo(int cod, String dato, int cant,int altura, int ancho,int Drawable) {
        this.cod = cod;
        this.dato = dato;
        this.cant=cant;
        this.altura=altura;
        this.ancho=ancho;
        this.Drawable=Drawable;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getDrawable() {
        return Drawable;
    }

    public void setDrawable(int drawable) {
        Drawable = drawable;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }
}
