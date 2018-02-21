package org.futuroblanquiazul.futuroblaquiazul.Entity;

import java.util.List;

/**
 * Created by Jesus Inca on 16/02/2018.
 */

public class Masivo {

    int codigo;
    String Nombre_Masivo;
    Unidad_Territorial Departamento;
    Unidad_Territorial Provincia;
    Unidad_Territorial Distrito;
    String Fecha_Creacion;
    String Usuario_Creador;
    int id_Usuario;
    int  Estado;
    int total_postulantes;

    List<Persona> Lista_Personas ;

    public Masivo(){
    }


    public int getTotal_postulantes() {
        return total_postulantes;
    }

    public void setTotal_postulantes(int total_postulantes) {
        this.total_postulantes = total_postulantes;
    }

    public List<Persona> getLista_Personas() {
        return Lista_Personas;
    }

    public void setLista_Personas(List<Persona> lista_Personas) {
        Lista_Personas = lista_Personas;
    }

    public int getCodigo() {
        return codigo;
    }


    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre_Masivo() {
        return Nombre_Masivo;
    }

    public void setNombre_Masivo(String nombre_Masivo) {
        Nombre_Masivo = nombre_Masivo;
    }

    public Unidad_Territorial getDepartamento() {
        return Departamento;
    }

    public void setDepartamento(Unidad_Territorial departamento) {
        Departamento = departamento;
    }

    public Unidad_Territorial getProvincia() {
        return Provincia;
    }

    public void setProvincia(Unidad_Territorial provincia) {
        Provincia = provincia;
    }

    public Unidad_Territorial getDistrito() {
        return Distrito;
    }

    public void setDistrito(Unidad_Territorial distrito) {
        Distrito = distrito;
    }

    public String getFecha_Creacion() {
        return Fecha_Creacion;
    }

    public void setFecha_Creacion(String fecha_Creacion) {
        Fecha_Creacion = fecha_Creacion;
    }

    public String getUsuario_Creador() {
        return Usuario_Creador;
    }

    public void setUsuario_Creador(String usuario_Creador) {
        Usuario_Creador = usuario_Creador;
    }

    public int getId_Usuario() {
        return id_Usuario;
    }

    public void setId_Usuario(int id_Usuario) {
        this.id_Usuario = id_Usuario;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int estado) {
        Estado = estado;
    }
}
