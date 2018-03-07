package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 07/03/2018.
 */

public class PruebaTactica {

    int id;
    Usuario usuario;
    Persona persona;
    double ataque;
    double definicion;
    double defensa;
    double recuperacion;
    String fecha_registro;
    double total_general;
    int estado;
    public static final PruebaTactica PRUEBA_TACTICA=new PruebaTactica();

    public PruebaTactica(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public double getAtaque() {
        return ataque;
    }

    public void setAtaque(double ataque) {
        this.ataque = ataque;
    }

    public double getDefinicion() {
        return definicion;
    }

    public void setDefinicion(double definicion) {
        this.definicion = definicion;
    }

    public double getDefensa() {
        return defensa;
    }

    public void setDefensa(double defensa) {
        this.defensa = defensa;
    }

    public double getRecuperacion() {
        return recuperacion;
    }

    public void setRecuperacion(double recuperacion) {
        this.recuperacion = recuperacion;
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
