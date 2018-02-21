package org.futuroblanquiazul.futuroblaquiazul.Utils;

import android.text.method.SingleLineTransformationMethod;

/**
 * Created by Jesus Inca on 24/01/2018.
 */

public class Captacion_funcional {

    String Opcion;
    int textView;
    int GroupRadio;
    int Radio1;
    int Radio2;
    int Radio3;
    int Radio4;
    int Resultado;

    public Captacion_funcional(String opcion, int textView, int groupRadio, int radio1, int radio2, int radio3, int radio4, int resultado) {
        Opcion = opcion;
        this.textView = textView;
        GroupRadio = groupRadio;
        Radio1 = radio1;
        Radio2 = radio2;
        Radio3 = radio3;
        Radio4 = radio4;
        Resultado = resultado;
    }

    public String getOpcion() {
        return Opcion;
    }

    public void setOpcion(String opcion) {
        Opcion = opcion;
    }

    public int getTextView() {
        return textView;
    }

    public void setTextView(int textView) {
        this.textView = textView;
    }

    public int getGroupRadio() {
        return GroupRadio;
    }

    public void setGroupRadio(int groupRadio) {
        GroupRadio = groupRadio;
    }

    public int getRadio1() {
        return Radio1;
    }

    public void setRadio1(int radio1) {
        Radio1 = radio1;
    }

    public int getRadio2() {
        return Radio2;
    }

    public void setRadio2(int radio2) {
        Radio2 = radio2;
    }

    public int getRadio3() {
        return Radio3;
    }

    public void setRadio3(int radio3) {
        Radio3 = radio3;
    }

    public int getRadio4() {
        return Radio4;
    }

    public void setRadio4(int radio4) {
        Radio4 = radio4;
    }

    public int getResultado() {
        return Resultado;
    }

    public void setResultado(int resultado) {
        Resultado = resultado;
    }
}
