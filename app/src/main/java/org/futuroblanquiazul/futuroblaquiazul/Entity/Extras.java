package org.futuroblanquiazul.futuroblaquiazul.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesus Inca on 16/03/2018.
 */

public class Extras {
    Persona persona;
    List<Integer> Lista_Numeros;
    int Numero_Camiseta;
    int Numero_Anterior;
    int estado;
    int estado2;
    boolean actividad;

    public Extras(){
    }

    public static final List<Extras> LISTA_EXTRAS=new ArrayList<>();


    public int getNumero_Anterior() {
        return Numero_Anterior;
    }

    public void setNumero_Anterior(int numero_Anterior) {
        Numero_Anterior = numero_Anterior;
    }

    public int getEstado2() {
        return estado2;
    }

    public void setEstado2(int estado2) {
        this.estado2 = estado2;
    }

    public boolean isActividad() {
        return actividad;
    }

    public void setActividad(boolean actividad) {
        this.actividad = actividad;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public List<Integer> getLista_Numeros() {
        return Lista_Numeros;
    }

    public void setLista_Numeros(List<Integer> lista_Numeros) {
        Lista_Numeros = lista_Numeros;
    }

    public int getNumero_Camiseta() {
        return Numero_Camiseta;
    }

    public void setNumero_Camiseta(int numero_Camiseta) {
        Numero_Camiseta = numero_Camiseta;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void Mostrar_Lista(){
        System.out.println("-------------Persona: "+this.persona.getId()+"---------------------------------------------");
        for(int i=0;i<this.Lista_Numeros.size();i++){
            System.out.println("-------------Numero : "+this.Lista_Numeros.get(i)+"---------------------------------------------");
        }
    }
}
