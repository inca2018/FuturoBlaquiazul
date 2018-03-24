package org.futuroblanquiazul.futuroblaquiazul.Utils;

import android.widget.EditText;
import android.widget.ImageView;

import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterOponentes;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPosiciones;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPosiciones2;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Oponente;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Posicion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesus Inca on 24/03/2018.
 */

public class Recursos_Mantenimientos {

    EditText nombre_oponente;
    EditText apodo;
    ImageView foto_oponente;
    Oponente oponente_temporal;

    public static final  List<Posicion> Posiciones1=new ArrayList<>();
    public static final  List<Posicion> Posiciones2=new ArrayList<>();
    public static  AdapterPosiciones ADAPTER1=null;
    public static  AdapterPosiciones2 ADAPTER2=null;

    public static  final List<Oponente> OPONENTES=new ArrayList<>();
    public static AdapterOponentes ADAPTER_OPONENTE=null;

    public static   Oponente OPONENTE=new Oponente();


    public static final Recursos_Mantenimientos TEMP=new Recursos_Mantenimientos();



    public Recursos_Mantenimientos(){

    }

    public Oponente getOponente_temporal() {
        return oponente_temporal;
    }

    public void setOponente_temporal(Oponente oponente_temporal) {
        this.oponente_temporal = oponente_temporal;
    }

    public EditText getNombre_oponente() {
        return nombre_oponente;
    }

    public void setNombre_oponente(EditText nombre_oponente) {
        this.nombre_oponente = nombre_oponente;
    }

    public EditText getApodo() {
        return apodo;
    }

    public void setApodo(EditText apodo) {
        this.apodo = apodo;
    }

    public ImageView getFoto_oponente() {
        return foto_oponente;
    }

    public void setFoto_oponente(ImageView foto_oponente) {
        this.foto_oponente = foto_oponente;
    }
}
