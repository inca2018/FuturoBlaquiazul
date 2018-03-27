package org.futuroblanquiazul.futuroblaquiazul.Utils;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterOponentes;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPosiciones;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPosiciones2;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Oponente;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Posicion;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;

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
    Button  accion_guardar;
    boolean actualizar;
    boolean actualizar_usuarios;

    Unidad_Territorial departamento;
    Unidad_Territorial provincia;
    Unidad_Territorial distrito;
    Persona persona_temporal;

    public static final  List<Posicion> Posiciones1=new ArrayList<>();
    public static final  List<Posicion> Posiciones2=new ArrayList<>();
    public static  AdapterPosiciones ADAPTER1=null;
    public static  AdapterPosiciones2 ADAPTER2=null;

    public static  final List<Oponente> OPONENTES=new ArrayList<>();
    public static AdapterOponentes ADAPTER_OPONENTE=null;




    public static final Recursos_Mantenimientos TEMP=new Recursos_Mantenimientos();



    public Recursos_Mantenimientos(){

    }


    public Persona getPersona_temporal() {
        return persona_temporal;
    }

    public void setPersona_temporal(Persona persona_temporal) {
        this.persona_temporal = persona_temporal;
    }

    public Unidad_Territorial getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Unidad_Territorial departamento) {
        this.departamento = departamento;
    }

    public Unidad_Territorial getProvincia() {
        return provincia;
    }

    public void setProvincia(Unidad_Territorial provincia) {
        this.provincia = provincia;
    }

    public Unidad_Territorial getDistrito() {
        return distrito;
    }

    public void setDistrito(Unidad_Territorial distrito) {
        this.distrito = distrito;
    }

    public boolean isActualizar_usuarios() {
        return actualizar_usuarios;
    }

    public void setActualizar_usuarios(boolean actualizar_usuarios) {
        this.actualizar_usuarios = actualizar_usuarios;
    }

    public Button getAccion_guardar() {
        return accion_guardar;
    }

    public boolean isActualizar() {
        return actualizar;
    }

    public void setActualizar(boolean actualizar) {
        this.actualizar = actualizar;
    }

    public void setAccion_guardar(Button accion_guardar) {
        this.accion_guardar = accion_guardar;
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
