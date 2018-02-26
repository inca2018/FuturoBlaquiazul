package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 26/02/2018.
 */

public class BarrioIntimo {
    int id;
    Usuario usuario;
    String NombreEvento;
    String Descripcion;
    Unidad_Territorial Departamento;
    Unidad_Territorial Provincia;
    Unidad_Territorial Distrito;
    String FechaRealizacion;
    String FechaRegistro;
    int Estado;
    int cantidad_Postulantes;
    String Descripcion_ubigeo;


    public BarrioIntimo(){


    }

    public String getDescripcion_ubigeo() {
        return Descripcion_ubigeo;
    }

    public void setDescripcion_ubigeo(String descripcion_ubigeo) {
        Descripcion_ubigeo = descripcion_ubigeo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombreEvento() {
        return NombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        NombreEvento = nombreEvento;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
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

    public String getFechaRealizacion() {
        return FechaRealizacion;
    }

    public void setFechaRealizacion(String fechaRealizacion) {
        FechaRealizacion = fechaRealizacion;
    }

    public String getFechaRegistro() {
        return FechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        FechaRegistro = fechaRegistro;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int estado) {
        Estado = estado;
    }

    public int getCantidad_Postulantes() {
        return cantidad_Postulantes;
    }

    public void setCantidad_Postulantes(int cantidad_Postulantes) {
        this.cantidad_Postulantes = cantidad_Postulantes;
    }
}
