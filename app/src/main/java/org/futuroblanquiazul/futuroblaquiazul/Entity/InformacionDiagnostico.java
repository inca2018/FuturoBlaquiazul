package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 09/03/2018.
 */

public class InformacionDiagnostico {
    int id;
    String fecha_registro;
    String ubigeo;
    String usuario;
    int total_fisico;
    int total_social;
    int total_capacidad;
    int total_tecnico;
    int total_psico;
    int estado_sugerido1;
    String nom_sugerido1;
    int estado_sugerido2;
    String nom_sugerido2;
    int estado_sugerido3;
    String nom_sugerido3;
    String lateralidad;
    int estado;
    int total_general;


    public final static InformacionDiagnostico INFO_DIAG=new InformacionDiagnostico();

    public InformacionDiagnostico(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getTotal_fisico() {
        return total_fisico;
    }

    public void setTotal_fisico(int total_fisico) {
        this.total_fisico = total_fisico;
    }

    public int getTotal_social() {
        return total_social;
    }

    public void setTotal_social(int total_social) {
        this.total_social = total_social;
    }

    public int getTotal_capacidad() {
        return total_capacidad;
    }

    public void setTotal_capacidad(int total_capacidad) {
        this.total_capacidad = total_capacidad;
    }

    public int getTotal_tecnico() {
        return total_tecnico;
    }

    public void setTotal_tecnico(int total_tecnico) {
        this.total_tecnico = total_tecnico;
    }

    public int getTotal_psico() {
        return total_psico;
    }

    public void setTotal_psico(int total_psico) {
        this.total_psico = total_psico;
    }

    public int getEstado_sugerido1() {
        return estado_sugerido1;
    }

    public void setEstado_sugerido1(int estado_sugerido1) {
        this.estado_sugerido1 = estado_sugerido1;
    }

    public String getNom_sugerido1() {
        return nom_sugerido1;
    }

    public void setNom_sugerido1(String nom_sugerido1) {
        this.nom_sugerido1 = nom_sugerido1;
    }

    public int getEstado_sugerido2() {
        return estado_sugerido2;
    }

    public void setEstado_sugerido2(int estado_sugerido2) {
        this.estado_sugerido2 = estado_sugerido2;
    }

    public String getNom_sugerido2() {
        return nom_sugerido2;
    }

    public void setNom_sugerido2(String nom_sugerido2) {
        this.nom_sugerido2 = nom_sugerido2;
    }

    public int getEstado_sugerido3() {
        return estado_sugerido3;
    }

    public void setEstado_sugerido3(int estado_sugerido3) {
        this.estado_sugerido3 = estado_sugerido3;
    }

    public String getNom_sugerido3() {
        return nom_sugerido3;
    }

    public void setNom_sugerido3(String nom_sugerido3) {
        this.nom_sugerido3 = nom_sugerido3;
    }

    public String getLateralidad() {
        return lateralidad;
    }

    public void setLateralidad(String lateralidad) {
        this.lateralidad = lateralidad;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getTotal_general() {
        return total_general;
    }

    public void setTotal_general(int total_general) {
        this.total_general = total_general;
    }
}
