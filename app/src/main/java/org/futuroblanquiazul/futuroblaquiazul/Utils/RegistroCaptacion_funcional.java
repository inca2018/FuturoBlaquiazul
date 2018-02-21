package org.futuroblanquiazul.futuroblaquiazul.Utils;

import android.widget.EditText;

/**
 * Created by Jesus Inca on 30/01/2018.
 */

public class RegistroCaptacion_funcional {
    int id;
    int Recurso;
    EditText CampoEditText;
    String Mensaje_Vacio;
    boolean Estado;
    String Valor;

    public RegistroCaptacion_funcional(int id, int recurso, EditText campoEditText, String mensaje_Vacio, boolean estado,String Valor) {
        this.id = id;
        Recurso = recurso;
        CampoEditText = campoEditText;
        Mensaje_Vacio = mensaje_Vacio;
        Estado = estado;
        Valor=Valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRecurso() {
        return Recurso;
    }

    public void setRecurso(int recurso) {
        Recurso = recurso;
    }

    public EditText getCampoEditText() {
        return CampoEditText;
    }

    public void setCampoEditText(EditText campoEditText) {
        CampoEditText = campoEditText;
    }

    public String getMensaje_Vacio() {
        return Mensaje_Vacio;
    }

    public void setMensaje_Vacio(String mensaje_Vacio) {
        Mensaje_Vacio = mensaje_Vacio;
    }

    public boolean isEstado() {
        return Estado;
    }

    public void setEstado(boolean estado) {
        Estado = estado;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String valor) {
        Valor = valor;
    }
}
