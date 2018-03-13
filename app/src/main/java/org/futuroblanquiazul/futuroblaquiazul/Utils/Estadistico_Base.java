package org.futuroblanquiazul.futuroblaquiazul.Utils;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;

/**
 * Created by Jesus Inca on 13/03/2018.
 */

public class Estadistico_Base {

    int id;
    int estado;
    String Nombre_de_jugador_Actual;
    ImageView imageView;
    int Recurso_foto;
    LinearLayout Area;
    int Recurso_area;
    TextView Nombre;
    int Recurso_texto;
    Persona persona;


    public Estadistico_Base(int id, int estado, String nombre_de_jugador_Actual, ImageView imageView, int recurso_foto, LinearLayout area, int recurso_area, TextView nombre, int recurso_texto, Persona persona) {
        this.id = id;
        this.estado = estado;
        Nombre_de_jugador_Actual = nombre_de_jugador_Actual;
        this.imageView = imageView;
        Recurso_foto = recurso_foto;
        Area = area;
        Recurso_area = recurso_area;
        Nombre = nombre;
        Recurso_texto = recurso_texto;
        this.persona = persona;
    }


    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }


    public int getRecurso_foto() {
        return Recurso_foto;
    }

    public void setRecurso_foto(int recurso_foto) {
        Recurso_foto = recurso_foto;
    }

    public int getRecurso_area() {
        return Recurso_area;
    }

    public void setRecurso_area(int recurso_area) {
        Recurso_area = recurso_area;
    }

    public int getRecurso_texto() {
        return Recurso_texto;
    }

    public void setRecurso_texto(int recurso_texto) {
        Recurso_texto = recurso_texto;
    }

    public String getNombre_de_jugador_Actual() {
        return Nombre_de_jugador_Actual;
    }

    public void setNombre_de_jugador_Actual(String nombre_de_jugador_Actual) {
        Nombre_de_jugador_Actual = nombre_de_jugador_Actual;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public LinearLayout getArea() {
        return Area;
    }

    public void setArea(LinearLayout area) {
        Area = area;
    }

    public TextView getNombre() {
        return Nombre;
    }

    public void setNombre(TextView nombre) {
        Nombre = nombre;
    }
}
