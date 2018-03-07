package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 07/03/2018.
 */

public class PruebaNutricional {

    int id;
    Usuario user;
    Persona persona;
    double IMC;
    double graso;
    double aks;
    String fecha_registro;
    double total_general;
    int estado;


    public static final PruebaNutricional PRUEBA_NUTRICIONAL=new PruebaNutricional();
    public PruebaNutricional(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public double getIMC() {
        return IMC;
    }

    public void setIMC(double IMC) {
        this.IMC = IMC;
    }

    public double getGraso() {
        return graso;
    }

    public void setGraso(double graso) {
        this.graso = graso;
    }

    public double getAks() {
        return aks;
    }

    public void setAks(double aks) {
        this.aks = aks;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public double getTotal_general() {
        return total_general;
    }

    public void setTotal_general(double total_general) {
        this.total_general = total_general;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
