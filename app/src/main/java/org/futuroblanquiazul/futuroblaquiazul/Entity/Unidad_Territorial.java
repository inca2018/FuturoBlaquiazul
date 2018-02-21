package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class Unidad_Territorial {

    int codigo;
    String Descripcion;
    int externo;


    public Unidad_Territorial(){

    }


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getExterno() {
        return externo;
    }

    public void setExterno(int externo) {
        this.externo = externo;
    }
}
