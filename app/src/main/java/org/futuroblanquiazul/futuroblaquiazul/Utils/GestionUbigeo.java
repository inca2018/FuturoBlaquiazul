package org.futuroblanquiazul.futuroblaquiazul.Utils;


import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class GestionUbigeo {
    int codigo_modulo;
    Unidad_Territorial Departamento;
    Unidad_Territorial Provincia;
    Unidad_Territorial Distrito;
    String ubigeo_descripcion;
    boolean estado;

    public static final GestionUbigeo CAPTACION_UBIGEO =new  GestionUbigeo();
    public static final GestionUbigeo CAPTACION_UBIGEO_MASIVO =new  GestionUbigeo();
    public static final GestionUbigeo CAPTACION_UBIGEO_BARRIO =new  GestionUbigeo();
    public static final GestionUbigeo ESTADISTICO_UBIGEO =new  GestionUbigeo();

    public static final GestionUbigeo UBIGEO_TEMP=new GestionUbigeo();

    public  GestionUbigeo(){
    }

    public int getCodigo_modulo() {
        return codigo_modulo;
    }

    public void setCodigo_modulo(int codigo_modulo) {
        this.codigo_modulo = codigo_modulo;
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

    public String getUbigeo_descripcion() {
        return ubigeo_descripcion;
    }

    public void setUbigeo_descripcion(String ubigeo_descripcion) {
        this.ubigeo_descripcion = ubigeo_descripcion;
    }


    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
