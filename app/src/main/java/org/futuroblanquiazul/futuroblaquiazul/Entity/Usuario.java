package org.futuroblanquiazul.futuroblaquiazul.Entity;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class Usuario {
  int id;
  String usuario;
  String password;
  String Nombres;
  String Apellidos;
  int dni;
  int tipo_usuario;
  int estado;
  String area;
  String Cargo;
  String foto;
  String correo;

  String fecha_creacion;
  String fecha_conexion;

  int id_masivo;
  int id_persona;

    int altura;
    int ancho;


    public static final Usuario SESION_ACTUAL=new Usuario();


    public Usuario(){

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
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
