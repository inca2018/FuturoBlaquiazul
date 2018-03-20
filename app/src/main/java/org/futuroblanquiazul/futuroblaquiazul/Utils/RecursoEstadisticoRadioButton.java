package org.futuroblanquiazul.futuroblaquiazul.Utils;

import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Jesus Inca on 20/03/2018.
 */

public class RecursoEstadisticoRadioButton {

    int num;
    int id;
    RadioButton radioButton;
    int estado;
    int tipo;
    String desc;


    public RecursoEstadisticoRadioButton(int num, int id, RadioButton radioButton, int estado, int tipo,String desc) {
        this.num = num;
        this.id = id;
        this.radioButton = radioButton;
        this.estado = estado;
        this.tipo = tipo;
        this.desc=desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public RadioButton getRadioButton() {
        return radioButton;
    }

    public void setRadioButton(RadioButton radioButton) {
        this.radioButton = radioButton;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
