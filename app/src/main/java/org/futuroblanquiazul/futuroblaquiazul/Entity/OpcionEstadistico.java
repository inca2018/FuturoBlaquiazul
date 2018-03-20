package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 20/03/2018.
 */

public class OpcionEstadistico {

    int codigo;
    String Opcion;
    int codigo_area;
    public OpcionEstadistico(){
    }

    public int getCodigo() {
        return codigo;
    }

    public int getCodigo_area() {
        return codigo_area;
    }

    public void setCodigo_area(int codigo_area) {
        this.codigo_area = codigo_area;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getOpcion() {
        return Opcion;
    }

    public void setOpcion(String opcion) {
        Opcion = opcion;
    }
}
