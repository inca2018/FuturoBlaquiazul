package org.futuroblanquiazul.futuroblaquiazul.Utils;

import java.util.List;

/**
 * Created by Jesus Inca on 19/02/2018.
 */

public class ResultadosDiagnostico {

    int id_persona;
    int total_fisico,total_capacidad,total_social,total_tecnico,total_psico;
    List<Integer> FISICO;
    List<Integer> CAPACIDAD;
    List<Integer> SOCIAL;
    List<Integer> TECNICO;
    List<Integer> PSICO;

    String fecha_registro;
    int Sugerido1,Sugerido2,Sugerid3;
    String Laterlaidad;
    String Nombre_Scout;
    List<String> Sugeridos;

    public final static ResultadosDiagnostico RESULTADO_TEMP=new ResultadosDiagnostico();


    public ResultadosDiagnostico(){

    }


    public List<String> getSugeridos() {
        return Sugeridos;
    }

    public void setSugeridos(List<String> sugeridos) {
        Sugeridos = sugeridos;
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public int getTotal_fisico() {
        return total_fisico;
    }

    public void setTotal_fisico(int total_fisico) {
        this.total_fisico = total_fisico;
    }

    public int getTotal_capacidad() {
        return total_capacidad;
    }

    public void setTotal_capacidad(int total_capacidad) {
        this.total_capacidad = total_capacidad;
    }

    public int getTotal_social() {
        return total_social;
    }

    public void setTotal_social(int total_social) {
        this.total_social = total_social;
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

    public List<Integer> getFISICO() {
        return FISICO;
    }

    public void setFISICO(List<Integer> FISICO) {
        this.FISICO = FISICO;
    }

    public List<Integer> getCAPACIDAD() {
        return CAPACIDAD;
    }

    public void setCAPACIDAD(List<Integer> CAPACIDAD) {
        this.CAPACIDAD = CAPACIDAD;
    }

    public List<Integer> getSOCIAL() {
        return SOCIAL;
    }

    public void setSOCIAL(List<Integer> SOCIAL) {
        this.SOCIAL = SOCIAL;
    }

    public List<Integer> getTECNICO() {
        return TECNICO;
    }

    public void setTECNICO(List<Integer> TECNICO) {
        this.TECNICO = TECNICO;
    }

    public List<Integer> getPSICO() {
        return PSICO;
    }

    public void setPSICO(List<Integer> PSICO) {
        this.PSICO = PSICO;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public int getSugerido1() {
        return Sugerido1;
    }

    public void setSugerido1(int sugerido1) {
        Sugerido1 = sugerido1;
    }

    public int getSugerido2() {
        return Sugerido2;
    }

    public void setSugerido2(int sugerido2) {
        Sugerido2 = sugerido2;
    }

    public int getSugerid3() {
        return Sugerid3;
    }

    public void setSugerid3(int sugerid3) {
        Sugerid3 = sugerid3;
    }

    public String getLaterlaidad() {
        return Laterlaidad;
    }

    public void setLaterlaidad(String laterlaidad) {
        Laterlaidad = laterlaidad;
    }

    public String getNombre_Scout() {
        return Nombre_Scout;
    }

    public void setNombre_Scout(String nombre_Scout) {
        Nombre_Scout = nombre_Scout;
    }
}
