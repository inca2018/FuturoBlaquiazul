package org.futuroblanquiazul.futuroblaquiazul.Utils;

import org.futuroblanquiazul.futuroblaquiazul.Entity.FechaEntidad;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;

/**
 * Created by Jesus Inca on 09/04/2018.
 */

public class Info {

    Persona persona;
    Unidad_Territorial Departamento;
    Unidad_Territorial Provincia;
    Unidad_Territorial Distrito;

    FechaEntidad inicio;
    FechaEntidad fin;

    int tipo_busqueda;


    Persona Personal_Temporal;

    public static final Info GESTOR=new Info();

    public Info(){

    }

    public Persona getPersonal_Temporal() {
        return Personal_Temporal;
    }

    public void setPersonal_Temporal(Persona personal_Temporal) {
        Personal_Temporal = personal_Temporal;
    }

    public int getTipo_busqueda() {
        return tipo_busqueda;
    }

    public void setTipo_busqueda(int tipo_busqueda) {
        this.tipo_busqueda = tipo_busqueda;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
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

    public FechaEntidad getInicio() {
        return inicio;
    }

    public void setInicio(FechaEntidad inicio) {
        this.inicio = inicio;
    }

    public FechaEntidad getFin() {
        return fin;
    }

    public void setFin(FechaEntidad fin) {
        this.fin = fin;
    }
}
