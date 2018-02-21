package org.futuroblanquiazul.futuroblaquiazul.ActivityEntity;


import org.futuroblanquiazul.futuroblaquiazul.Entity.Posicion;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;

import java.util.List;

/**
 * Created by Jesus Inca on 13/02/2018.
 */

public class modulo_captacion {
    int id_persona;
    Unidad_Territorial Departamento;
    Unidad_Territorial Provincia;
    Unidad_Territorial Distrito;
    String DescripcionUbigeo;
    int id_usuario;
    int campo_fisico_id;
    int campo_capacidad_id;
    int campo_social_id;
    int campo_tecnico_id;
    int campo_psico_id;
    Posicion sugerido_1;
    Posicion sugerido_2;
    Posicion sugerido_3;
    String lateralidad;
    int estado;
    List<Integer> Resultados_Fisico;
    List<Integer> Resultados_capacidad;
    List<Integer> Resultados_social;
    List<Integer> Resultados_tecnico;
    List<Integer> Resultados_psico;
    List<String>  DatosPostulante;


    public final static modulo_captacion BASE = new modulo_captacion();

    public modulo_captacion(){

    }

    public String getDescripcionUbigeo() {
        return DescripcionUbigeo;
    }

    public void setDescripcionUbigeo(String descripcionUbigeo) {
        DescripcionUbigeo = descripcionUbigeo;
    }

    public Unidad_Territorial getDepartamento() {
        return Departamento;
    }

    public void setDepartamento(Unidad_Territorial departamento) {
        Departamento = departamento;
    }

    public Unidad_Territorial getProvincia() {
        return Provincia;
    }

    public void setProvincia(Unidad_Territorial provincia) {
        Provincia = provincia;
    }

    public Unidad_Territorial getDistrito() {
        return Distrito;
    }

    public void setDistrito(Unidad_Territorial distrito) {
        Distrito = distrito;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public List<String> getDatosPostulante() {
        return DatosPostulante;
    }

    public void setDatosPostulante(List<String> datosPostulante) {
        DatosPostulante = datosPostulante;
    }

    public List<Integer> getResultados_Fisico() {
        return Resultados_Fisico;
    }

    public void setResultados_Fisico(List<Integer> resultados_Fisico) {
        Resultados_Fisico = resultados_Fisico;
    }

    public List<Integer> getResultados_capacidad() {
        return Resultados_capacidad;
    }

    public void setResultados_capacidad(List<Integer> resultados_capacidad) {
        Resultados_capacidad = resultados_capacidad;
    }

    public List<Integer> getResultados_social() {
        return Resultados_social;
    }

    public void setResultados_social(List<Integer> resultados_social) {
        Resultados_social = resultados_social;
    }

    public List<Integer> getResultados_tecnico() {
        return Resultados_tecnico;
    }

    public void setResultados_tecnico(List<Integer> resultados_tecnico) {
        Resultados_tecnico = resultados_tecnico;
    }

    public List<Integer> getResultados_psico() {
        return Resultados_psico;
    }

    public void setResultados_psico(List<Integer> resultados_psico) {
        Resultados_psico = resultados_psico;
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }


    public int getCampo_fisico_id() {
        return campo_fisico_id;
    }

    public void setCampo_fisico_id(int campo_fisico_id) {
        this.campo_fisico_id = campo_fisico_id;
    }

    public int getCampo_capacidad_id() {
        return campo_capacidad_id;
    }

    public void setCampo_capacidad_id(int campo_capacidad_id) {
        this.campo_capacidad_id = campo_capacidad_id;
    }

    public int getCampo_social_id() {
        return campo_social_id;
    }

    public void setCampo_social_id(int campo_social_id) {
        this.campo_social_id = campo_social_id;
    }

    public int getCampo_tecnico_id() {
        return campo_tecnico_id;
    }

    public void setCampo_tecnico_id(int campo_tecnico_id) {
        this.campo_tecnico_id = campo_tecnico_id;
    }

    public int getCampo_psico_id() {
        return campo_psico_id;
    }

    public void setCampo_psico_id(int campo_psico_id) {
        this.campo_psico_id = campo_psico_id;
    }

    public Posicion getSugerido_1() {
        return sugerido_1;
    }

    public void setSugerido_1(Posicion sugerido_1) {
        this.sugerido_1 = sugerido_1;
    }

    public Posicion getSugerido_2() {
        return sugerido_2;
    }

    public void setSugerido_2(Posicion sugerido_2) {
        this.sugerido_2 = sugerido_2;
    }

    public Posicion getSugerido_3() {
        return sugerido_3;
    }

    public void setSugerido_3(Posicion sugerido_3) {
        this.sugerido_3 = sugerido_3;
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


    public void Limpiar_Base (modulo_captacion BASE){
       BASE.setCampo_capacidad_id(0);
       BASE.setCampo_fisico_id(0);
       BASE.setCampo_psico_id(0);
       BASE.setCampo_social_id(0);
       BASE.setCampo_tecnico_id(0);
       BASE.setDepartamento(null);
       BASE.setProvincia(null);
       BASE.setDistrito(null);
       BASE.setEstado(0);
       BASE.setId_persona(0);
       BASE.setId_usuario(0);
       BASE.setSugerido_1(null);
       BASE.setSugerido_2(null);
       BASE.setSugerido_3(null);
       BASE.setLateralidad(null);
       BASE.setResultados_capacidad(null);
       BASE.setResultados_Fisico(null);
       BASE.setResultados_psico(null);
       BASE.setResultados_social(null);
       BASE.setResultados_tecnico(null);
       BASE.setDatosPostulante(null);
       BASE.setDescripcionUbigeo(null);

    }

    @Override
    public String toString() {
        return "modulo_captacion{" +
                "id_persona=" + id_persona +
                ", Departamento=" + Departamento +
                ", Provincia=" + Provincia +
                ", Distrito=" + Distrito +
                ", DescripcionUbigeo='" + DescripcionUbigeo + '\'' +
                ", id_usuario=" + id_usuario +
                ", campo_fisico_id=" + campo_fisico_id +
                ", campo_capacidad_id=" + campo_capacidad_id +
                ", campo_social_id=" + campo_social_id +
                ", campo_tecnico_id=" + campo_tecnico_id +
                ", campo_psico_id=" + campo_psico_id +
                ", sugerido_1=" + sugerido_1.getNombre_Posicione() +
                ", sugerido_2=" + sugerido_2.getNombre_Posicione() +
                ", sugerido_3=" + sugerido_3.getNombre_Posicione() +
                ", lateralidad='" + lateralidad + '\'' +
                ", estado=" + estado +
                ", Resultados_Fisico=" + Resultados_Fisico +
                ", Resultados_capacidad=" + Resultados_capacidad +
                ", Resultados_social=" + Resultados_social +
                ", Resultados_tecnico=" + Resultados_tecnico +
                ", Resultados_psico=" + Resultados_psico +
                ", DatosPostulante=" + DatosPostulante +
                '}';
    }
}
