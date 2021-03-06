package org.futuroblanquiazul.futuroblaquiazul.Entity;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
  int id;
  String usuario;
  String password;
  String Nombres;
  String Apellidos;
  int dni;
  int tipo_usuario;
  int estado;
  int area;
  String Cargo;
  String foto;
  String correo;

  String fecha_creacion;
  String fecha_conexion;

  int id_masivo;
  int id_persona;

  Persona persona_seguimiento;
  Persona persona_barrio;

  Seguimiento temporal;

  Persona  persona_fase_pruebas;

    int altura;
    int ancho;

    int id_barrio_intimo;

    BarrioIntimo barrio_datos;
    Persona persona_metodologia;
    Persona persona_captacion_individual;
    Persona persona_metodologia_pruebas;
    Plantel plantel;


    Grupo grupo_filtro;
    Plantel plantel_filtro;
    Solicitudes solicitud_temp;

    GrupoPruebas grupoPruebasTEMP;

    TipoPruebas tipoPruebas;


    int id_fisico;
    int id_social;
    int id_capacidad;
    int id_tecnico;
    int id_psico;


    Perfil perfil;
    Area_Usuario area_usuario;

    String FechaModificado;
    Usuario usuario_mantenimiento;
    Plantel plantel_temporal;


    public static final Usuario SESION_ACTUAL=new Usuario();



    public Usuario(){

    }

    public Plantel getPlantel_temporal() {
        return plantel_temporal;
    }

    public void setPlantel_temporal(Plantel plantel_temporal) {
        this.plantel_temporal = plantel_temporal;
    }

    public String getFechaModificado() {
        return FechaModificado;
    }

    public Usuario getUsuario_mantenimiento() {
        return usuario_mantenimiento;
    }

    public void setUsuario_mantenimiento(Usuario usuario_mantenimiento) {
        this.usuario_mantenimiento = usuario_mantenimiento;
    }

    public void setFechaModificado(String fechaModificado) {
        FechaModificado = fechaModificado;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Area_Usuario getArea_usuario() {
        return area_usuario;
    }

    public void setArea_usuario(Area_Usuario area_usuario) {
        this.area_usuario = area_usuario;
    }

    public TipoPruebas getTipoPruebas() {
        return tipoPruebas;
    }

    public void setTipoPruebas(TipoPruebas tipoPruebas) {
        this.tipoPruebas = tipoPruebas;
    }

    public int getId_fisico() {
        return id_fisico;
    }


    public Persona getPersona_fase_pruebas() {
        return persona_fase_pruebas;
    }

    public void setPersona_fase_pruebas(Persona persona_fase_pruebas) {
        this.persona_fase_pruebas = persona_fase_pruebas;
    }

    public Seguimiento getTemporal() {
        return temporal;
    }

    public void setTemporal(Seguimiento temporal) {
        this.temporal = temporal;
    }

    public Persona getPersona_metodologia_pruebas() {
        return persona_metodologia_pruebas;
    }

    public void setPersona_metodologia_pruebas(Persona persona_metodologia_pruebas) {
        this.persona_metodologia_pruebas = persona_metodologia_pruebas;
    }

    public GrupoPruebas getGrupoPruebasTEMP() {
        return grupoPruebasTEMP;
    }

    public void setGrupoPruebasTEMP(GrupoPruebas grupoPruebasTEMP) {
        this.grupoPruebasTEMP = grupoPruebasTEMP;
    }

    public Persona getPersona_captacion_individual() {
        return persona_captacion_individual;
    }

    public void setPersona_captacion_individual(Persona persona_captacion_individual) {
        this.persona_captacion_individual = persona_captacion_individual;
    }

    public void setId_fisico(int id_fisico) {
        this.id_fisico = id_fisico;
    }

    public int getId_social() {
        return id_social;
    }

    public void setId_social(int id_social) {
        this.id_social = id_social;
    }

    public int getId_capacidad() {
        return id_capacidad;
    }

    public void setId_capacidad(int id_capacidad) {
        this.id_capacidad = id_capacidad;
    }

    public int getId_tecnico() {
        return id_tecnico;
    }

    public void setId_tecnico(int id_tecnico) {
        this.id_tecnico = id_tecnico;
    }

    public int getId_psico() {
        return id_psico;
    }

    public void setId_psico(int id_psico) {
        this.id_psico = id_psico;
    }

    public Solicitudes getSolicitud_temp() {
        return solicitud_temp;
    }

    public void setSolicitud_temp(Solicitudes solicitud_temp) {
        this.solicitud_temp = solicitud_temp;
    }

    public Grupo getGrupo_filtro() {
        return grupo_filtro;
    }

    public void setGrupo_filtro(Grupo grupo_filtro) {
        this.grupo_filtro = grupo_filtro;
    }

    public Plantel getPlantel_filtro() {
        return plantel_filtro;
    }

    public void setPlantel_filtro(Plantel plantel_filtro) {
        this.plantel_filtro = plantel_filtro;
    }

    public Persona getPersona_metodologia() {
        return persona_metodologia;
    }

    public void setPersona_metodologia(Persona persona_metodologia) {
        this.persona_metodologia = persona_metodologia;
    }

    public Plantel getPlantel() {
        return plantel;
    }

    public void setPlantel(Plantel plantel) {
        this.plantel = plantel;
    }

    public Persona getPersona_barrio() {
        return persona_barrio;
    }

    public void setPersona_barrio(Persona persona_barrio) {
        this.persona_barrio = persona_barrio;
    }

    public BarrioIntimo getBarrio_datos() {
        return barrio_datos;
    }

    public void setBarrio_datos(BarrioIntimo barrio_datos) {
        this.barrio_datos = barrio_datos;
    }

    public int getId_barrio_intimo() {
        return id_barrio_intimo;
    }

    public void setId_barrio_intimo(int id_barrio_intimo) {
        this.id_barrio_intimo = id_barrio_intimo;
    }

    public Persona getPersona_seguimiento() {
        return persona_seguimiento;
    }

    public void setPersona_seguimiento(Persona persona_seguimiento) {
        this.persona_seguimiento = persona_seguimiento;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getId_masivo() {
        return id_masivo;
    }

    public void setId_masivo(int id_masivo) {
        this.id_masivo = id_masivo;
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(int tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String cargo) {
        Cargo = cargo;
    }

    public static Usuario getSesionActual() {
        return SESION_ACTUAL;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getFecha_conexion() {
        return fecha_conexion;
    }

    public void setFecha_conexion(String fecha_conexion) {
        this.fecha_conexion = fecha_conexion;
    }
}
