package org.futuroblanquiazul.futuroblaquiazul.Utils;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterJugadorBarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterJugadorCategoria;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterJugadorDisponible;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterJugadorDisponible2;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterJugadorDisponible3;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterJugadorEquipo;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterOponentes;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPosiciones;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPosiciones2;
import org.futuroblanquiazul.futuroblaquiazul.Entity.BarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Equipo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Oponente;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
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
    BarrioIntimo evento_temporal;
    Plantel categoria_temporal;
    Equipo equipo_temporal;

    int estado_Temporal;
    int rango_temporal;

    public static final  List<Posicion> Posiciones1=new ArrayList<>();
    public static final  List<Posicion> Posiciones2=new ArrayList<>();
    public static  AdapterPosiciones ADAPTER1=null;
    public static  AdapterPosiciones2 ADAPTER2=null;

    public static  final List<Oponente> OPONENTES=new ArrayList<>();
    public static AdapterOponentes ADAPTER_OPONENTE=null;

    public static AdapterJugadorDisponible ADAPTER_JUGADOR_DISPONIBLE=null;
    public static final List<Persona> LISTA_DISPONIBLE=new ArrayList<>();

    public static AdapterJugadorBarrioIntimo ADAPTER_JUGADOR_BARRIO_INTIMO=null;
    public static final List<Persona> LISTA_PERSONA_BARRIO=new ArrayList<>();


    public static AdapterJugadorDisponible2 ADAPTER_JUGADOR_DISPONIBLE2=null;
    public static final List<Persona> LISTA_DISPONIBLE2=new ArrayList<>();

    public static AdapterJugadorCategoria ADAPTER_CATEGORIA=null;
    public static final List<Persona> LISTA_CATEGORIA=new ArrayList<>();



    public static AdapterJugadorDisponible3 ADAPTER_JUGADOR_DISPONIBLE3=null;
    public static  List<Persona> LISTA_DISPONIBLE3=new ArrayList<>();

    public static AdapterJugadorEquipo ADAPTER_EQUIPO=null;
    public static final List<Persona> LISTA_EQUIPO=new ArrayList<>();






    public static final Recursos_Mantenimientos TEMP=new Recursos_Mantenimientos();

    public Recursos_Mantenimientos(){

    }

    public Equipo getEquipo_temporal() {
        return equipo_temporal;
    }

    public void setEquipo_temporal(Equipo equipo_temporal) {
        this.equipo_temporal = equipo_temporal;
    }

    public int getEstado_Temporal() {
        return estado_Temporal;
    }

    public void setEstado_Temporal(int estado_Temporal) {
        this.estado_Temporal = estado_Temporal;
    }

    public int getRango_temporal() {
        return rango_temporal;
    }

    public void setRango_temporal(int rango_temporal) {
        this.rango_temporal = rango_temporal;
    }

    public Plantel getCategoria_temporal() {
        return categoria_temporal;
    }

    public void setCategoria_temporal(Plantel categoria_temporal) {
        this.categoria_temporal = categoria_temporal;
    }

    public Persona getPersona_temporal() {
        return persona_temporal;
    }

    public void setPersona_temporal(Persona persona_temporal) {
        this.persona_temporal = persona_temporal;
    }

    public BarrioIntimo getEvento_temporal() {
        return evento_temporal;
    }

    public void setEvento_temporal(BarrioIntimo evento_temporal) {
        this.evento_temporal = evento_temporal;
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
