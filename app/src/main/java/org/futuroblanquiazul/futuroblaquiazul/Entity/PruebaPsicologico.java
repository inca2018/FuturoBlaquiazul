package org.futuroblanquiazul.futuroblaquiazul.Entity;

import java.util.List;

/**
 * Created by Jesus Inca on 07/03/2018.
 */

public class PruebaPsicologico {

    int id;
    Usuario usuario;
    Persona persona;
    List<Integer> resultado;

    double cognitivo;
    double motivacion;
    double inteligencia;
    double liderazgo;
    double autoconfianza;
    double total_general;
    String fecha_registro;
    int estado;


    double prom1;
    double prom2;
    double prom3;
    double prom4;
    double prom5;


    public static final PruebaPsicologico PRUEBA=new PruebaPsicologico();
    public  PruebaPsicologico(){

    }


    public double getProm1() {
        return prom1;
    }

    public void setProm1(double prom1) {
        this.prom1 = prom1;
    }

    public double getProm2() {
        return prom2;
    }

    public void setProm2(double prom2) {
        this.prom2 = prom2;
    }

    public double getProm3() {
        return prom3;
    }

    public void setProm3(double prom3) {
        this.prom3 = prom3;
    }

    public double getProm4() {
        return prom4;
    }

    public void setProm4(double prom4) {
        this.prom4 = prom4;
    }

    public double getProm5() {
        return prom5;
    }

    public void setProm5(double prom5) {
        this.prom5 = prom5;
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

    public List<Integer> getResultado() {
        return resultado;
    }

    public void setResultado(List<Integer> resultado) {
        this.resultado = resultado;
    }

    public double getCognitivo() {
        return cognitivo;
    }

    public void setCognitivo(double cognitivo) {
        this.cognitivo = cognitivo;
    }

    public double getMotivacion() {
        return motivacion;
    }

    public void setMotivacion(double motivacion) {
        this.motivacion = motivacion;
    }

    public double getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(double inteligencia) {
        this.inteligencia = inteligencia;
    }

    public double getLiderazgo() {
        return liderazgo;
    }

    public void setLiderazgo(double liderazgo) {
        this.liderazgo = liderazgo;
    }

    public double getAutoconfianza() {
        return autoconfianza;
    }

    public void setAutoconfianza(double autoconfianza) {
        this.autoconfianza = autoconfianza;
    }

    public double getTotal_general() {
        return total_general;
    }

    public void setTotal_general(double total_general) {
        this.total_general = total_general;
    }

    public void setTotal_general(int total_general) {
        this.total_general = total_general;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
