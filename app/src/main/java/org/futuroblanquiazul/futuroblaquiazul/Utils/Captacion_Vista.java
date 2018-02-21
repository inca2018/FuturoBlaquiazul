package org.futuroblanquiazul.futuroblaquiazul.Utils;

import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Jesus Inca on 24/01/2018.
 */

public class Captacion_Vista {

    int Vista;
    int Contenedor;
    int Panel_Accion;
    int Area_Accion;
    int accion;
    int TextViewTotal;
    LinearLayout view;

    public Captacion_Vista(int vista, int contenedor, int panel_Accion, int area_Accion, int accion, int textViewTotal) {
        Vista = vista;
        Contenedor = contenedor;
        Panel_Accion = panel_Accion;
        Area_Accion = area_Accion;
        this.accion = accion;
        TextViewTotal = textViewTotal;
    }

    public int getVista() {
        return Vista;
    }

    public void setVista(int vista) {
        Vista = vista;
    }

    public int getContenedor() {
        return Contenedor;
    }

    public void setContenedor(int contenedor) {
        Contenedor = contenedor;
    }

    public int getPanel_Accion() {
        return Panel_Accion;
    }

    public void setPanel_Accion(int panel_Accion) {
        Panel_Accion = panel_Accion;
    }

    public int getArea_Accion() {
        return Area_Accion;
    }

    public void setArea_Accion(int area_Accion) {
        Area_Accion = area_Accion;
    }

    public int getAccion() {
        return accion;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }

    public int getTextViewTotal() {
        return TextViewTotal;
    }

    public void setTextViewTotal(int textViewTotal) {
        TextViewTotal = textViewTotal;
    }

    public LinearLayout getView() {
        return view;
    }

    public void setView(LinearLayout view) {
        this.view = view;
    }
}
