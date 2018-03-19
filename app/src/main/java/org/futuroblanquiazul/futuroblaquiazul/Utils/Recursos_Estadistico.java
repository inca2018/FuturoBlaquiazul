package org.futuroblanquiazul.futuroblaquiazul.Utils;

import android.app.AlertDialog;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesus Inca on 13/03/2018.
 */

public class Recursos_Estadistico {


    public static final List<Estadistico_Base> LISTA_BASE_7=new ArrayList<>();
    public static final List<Estadistico_Base> LISTA_BASE_8=new ArrayList<>();
    public static final List<Estadistico_Base> LISTA_BASE_9=new ArrayList<>();
    public static final List<Estadistico_Base> LISTA_BASE_COMPETETITIVA=new ArrayList<>();


    public static  final List<Persona> LISTA_PERSONA_GENERAL =new ArrayList<>();
    public static  final List<Persona> LISTA_PERSONA_TEMPORAL =new ArrayList<>();
    public static  final List<Persona> LISTA_PERSONA_BASE_ACTUAL =new ArrayList<>();

    public static final List<Integer> LISTA_NUMEROS_SELECCIONADOS=new ArrayList<>();
    public static final List<Integer> LISTA_COMPLETA=new ArrayList<>();



    AlertDialog dialog;
    int codigo_base;
    int codigo_campo;

    public static final Recursos_Estadistico RECURSO =new Recursos_Estadistico();

    static{

        for(int i=1;i<=25;i++){
            LISTA_COMPLETA.add(i);
        }

        LISTA_BASE_7.add(
                new Estadistico_Base(1,1,null,null,R.id.base7_1,null,R.id.base7_1_linear,null,R.id.base7_1_text,null));
        LISTA_BASE_7.add(
                new Estadistico_Base(2,1,null,null,R.id.base7_2,null,R.id.base7_2_linear,null,R.id.base7_2_text,null));
        LISTA_BASE_7.add(
                new Estadistico_Base(3,1,null,null,R.id.base7_3,null,R.id.base7_3_linear,null,R.id.base7_3_text,null));
        LISTA_BASE_7.add(
                new Estadistico_Base(4,1,null,null,R.id.base7_4,null,R.id.base7_4_linear,null,R.id.base7_4_text,null));
        LISTA_BASE_7.add(
                new Estadistico_Base(5,1,null,null,R.id.base7_5,null,R.id.base7_5_linear,null,R.id.base7_5_text,null));
        LISTA_BASE_7.add(
                new Estadistico_Base(6,1,null,null,R.id.base7_6,null,R.id.base7_6_linear,null,R.id.base7_6_text,null));
        LISTA_BASE_7.add(
                new Estadistico_Base(7,1,null,null,R.id.base7_7,null,R.id.base7_7_linear,null,R.id.base7_7_text,null));


        LISTA_BASE_8.add(
                new Estadistico_Base(1,1,null,null,R.id.base8_1,null,R.id.base8_1_linear,null,R.id.base8_1_text,null));
        LISTA_BASE_8.add(
                new Estadistico_Base(2,1,null,null,R.id.base8_2,null,R.id.base8_2_linear,null,R.id.base8_2_text,null));
        LISTA_BASE_8.add(
                new Estadistico_Base(3,1,null,null,R.id.base8_3,null,R.id.base8_3_linear,null,R.id.base8_3_text,null));
        LISTA_BASE_8.add(
                new Estadistico_Base(4,1,null,null,R.id.base8_4,null,R.id.base8_4_linear,null,R.id.base8_4_text,null));
        LISTA_BASE_8.add(
                new Estadistico_Base(5,1,null,null,R.id.base8_5,null,R.id.base8_5_linear,null,R.id.base8_5_text,null));
        LISTA_BASE_8.add(
                new Estadistico_Base(6,1,null,null,R.id.base8_6,null,R.id.base8_6_linear,null,R.id.base8_6_text,null));
        LISTA_BASE_8.add(
                new Estadistico_Base(7,1,null,null,R.id.base8_7,null,R.id.base8_7_linear,null,R.id.base8_7_text,null));
        LISTA_BASE_8.add(
                new Estadistico_Base(8,1,null,null,R.id.base8_8,null,R.id.base8_8_linear,null,R.id.base8_8_text,null));


        LISTA_BASE_9.add(
                new Estadistico_Base(1,1,null,null,R.id.base9_1,null,R.id.base9_1_linear,null,R.id.base9_1_text,null));
        LISTA_BASE_9.add(
                new Estadistico_Base(2,1,null,null,R.id.base9_2,null,R.id.base9_2_linear,null,R.id.base9_2_text,null));
        LISTA_BASE_9.add(
                new Estadistico_Base(3,1,null,null,R.id.base9_3,null,R.id.base9_3_linear,null,R.id.base9_3_text,null));
        LISTA_BASE_9.add(
                new Estadistico_Base(4,1,null,null,R.id.base9_4,null,R.id.base9_4_linear,null,R.id.base9_4_text,null));
        LISTA_BASE_9.add(
                new Estadistico_Base(5,1,null,null,R.id.base9_5,null,R.id.base9_5_linear,null,R.id.base9_5_text,null));
        LISTA_BASE_9.add(
                new Estadistico_Base(6,1,null,null,R.id.base9_6,null,R.id.base9_6_linear,null,R.id.base9_6_text,null));
        LISTA_BASE_9.add(
                new Estadistico_Base(7,1,null,null,R.id.base9_7,null,R.id.base9_7_linear,null,R.id.base9_7_text,null));
        LISTA_BASE_9.add(
                new Estadistico_Base(8,1,null,null,R.id.base9_8,null,R.id.base9_8_linear,null,R.id.base9_8_text,null));
        LISTA_BASE_9.add(
                new Estadistico_Base(9,1,null,null,R.id.base9_9,null,R.id.base9_9_linear,null,R.id.base9_9_text,null));



        LISTA_BASE_COMPETETITIVA.add(
                new Estadistico_Base(1,1,null,null,R.id.compete1_foto,null,R.id.compete1_linear,null,R.id.compete1_text,null));
        LISTA_BASE_COMPETETITIVA.add(
                new Estadistico_Base(2,1,null,null,R.id.compete2_foto,null,R.id.compete2_linear,null,R.id.compete2_text,null));
        LISTA_BASE_COMPETETITIVA.add(
                new Estadistico_Base(3,1,null,null,R.id.compete3_foto,null,R.id.compete3_linear,null,R.id.compete3_text,null));
        LISTA_BASE_COMPETETITIVA.add(
                new Estadistico_Base(4,1,null,null,R.id.compete4_foto,null,R.id.compete4_linear,null,R.id.compete4_text,null));
        LISTA_BASE_COMPETETITIVA.add(
                new Estadistico_Base(5,1,null,null,R.id.compete5_foto,null,R.id.compete5_linear,null,R.id.compete5_text,null));
        LISTA_BASE_COMPETETITIVA.add(
                new Estadistico_Base(6,1,null,null,R.id.compete6_foto,null,R.id.compete6_linear,null,R.id.compete6_text,null));
        LISTA_BASE_COMPETETITIVA.add(
                new Estadistico_Base(7,1,null,null,R.id.compete7_foto,null,R.id.compete7_linear,null,R.id.compete7_text,null));
        LISTA_BASE_COMPETETITIVA.add(
                new Estadistico_Base(8,1,null,null,R.id.compete8_foto,null,R.id.compete8_linear,null,R.id.compete8_text,null));
        LISTA_BASE_COMPETETITIVA.add(
                new Estadistico_Base(9,1,null,null,R.id.compete9_foto,null,R.id.compete9_linear,null,R.id.compete9_text,null));
        LISTA_BASE_COMPETETITIVA.add(
                new Estadistico_Base(10,1,null,null,R.id.compete10_foto,null,R.id.compete10_linear,null,R.id.compete10_text,null));
        LISTA_BASE_COMPETETITIVA.add(
                new Estadistico_Base(11,1,null,null,R.id.compete11_foto,null,R.id.compete11_linear,null,R.id.compete11_text,null));

    }




    public int getCodigo_base() {
        return codigo_base;
    }

    public void setCodigo_base(int codigo_base) {
        this.codigo_base = codigo_base;
    }

    public int getCodigo_campo() {
        return codigo_campo;
    }

    public void setCodigo_campo(int codigo_campo) {
        this.codigo_campo = codigo_campo;
    }

    public AlertDialog getDialog() {
        return dialog;
    }

    public void setDialog(AlertDialog dialog) {
        this.dialog = dialog;
    }
}
