package org.futuroblanquiazul.futuroblaquiazul.Entity;




/**
 * Created by Jesus Inca on 24/01/2018.
 */

public class Persona {

    int id;
    int estado;
    String estado_segmento;
    String Nombre_Persona;
    String Apellidos_Persona;
    String Fecha_Nacimiento;
    int edad;
    String nacionalidad;
    String Lugar_Residencia;
    String club_actual;
    String liga_actual;
    int telefono;
    int dni;
    String correo;
    String Nombre_Apoderado;
    int telefono_apoderado;
    String Categoria_Actual;
    String Fecha_registro_Captacion;
    String Fecha_registro_Masivo;
    String Fecha_Ultima_modificacion;
    int Num_Camiseta;

    int estado_capta;
    int disponible;
    int totales_seguimientos;


    int barrio_diagnostico;
    int barrio_fisica;
    int barrio_tecnica;
    int total_diagnostico;
    int total_fisica;
    int total_tecnica;

    int id_fisico;
    int id_social;
    int id_capacidad;
    int id_tecnico;
    int id_psico;
    String ubigeo;

    String foto;


    int estado_edicion;


    public static final Persona PERSONA_TEMP=new Persona();

    public Persona(){

    }


    public int getEstado_edicion() {
        return estado_edicion;
    }

    public void setEstado_edicion(int estado_edicion) {
        this.estado_edicion = estado_edicion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getTotal_diagnostico() {
        return total_diagnostico;
    }

    public void setTotal_diagnostico(int total_diagnostico) {
        this.total_diagnostico = total_diagnostico;
    }


    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    public int getId_fisico() {
        return id_fisico;
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

    public int getTotal_fisica() {
        return total_fisica;
    }

    public void setTotal_fisica(int total_fisica) {
        this.total_fisica = total_fisica;
    }

    public int getTotal_tecnica() {
        return total_tecnica;
    }

    public void setTotal_tecnica(int total_tecnica) {
        this.total_tecnica = total_tecnica;
    }

    public int getBarrio_diagnostico() {
        return barrio_diagnostico;
    }

    public void setBarrio_diagnostico(int barrio_diagnostico) {
        this.barrio_diagnostico = barrio_diagnostico;
    }

    public int getBarrio_fisica() {
        return barrio_fisica;
    }

    public void setBarrio_fisica(int barrio_fisica) {
        this.barrio_fisica = barrio_fisica;
    }

    public int getBarrio_tecnica() {
        return barrio_tecnica;
    }

    public void setBarrio_tecnica(int barrio_tecnica) {
        this.barrio_tecnica = barrio_tecnica;
    }



    public int getTotales_seguimientos() {
        return totales_seguimientos;
    }

    public void setTotales_seguimientos(int totales_seguimientos) {
        this.totales_seguimientos = totales_seguimientos;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }


    public int getEstado_capta() {
        return estado_capta;
    }

    public void setEstado_capta(int estado_capta) {
        this.estado_capta = estado_capta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getEstado_segmento() {
        return estado_segmento;
    }

    public void setEstado_segmento(String estado_segmento) {
        this.estado_segmento = estado_segmento;
    }

    public String getNombre_Persona() {
        return Nombre_Persona;
    }

    public void setNombre_Persona(String nombre_Persona) {
        Nombre_Persona = nombre_Persona;
    }

    public String getApellidos_Persona() {
        return Apellidos_Persona;
    }

    public void setApellidos_Persona(String apellidos_Persona) {
        Apellidos_Persona = apellidos_Persona;
    }

    public String getFecha_Nacimiento() {
        return Fecha_Nacimiento;
    }

    public void setFecha_Nacimiento(String fecha_Nacimiento) {
        Fecha_Nacimiento = fecha_Nacimiento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getLugar_Residencia() {
        return Lugar_Residencia;
    }

    public void setLugar_Residencia(String lugar_Residencia) {
        Lugar_Residencia = lugar_Residencia;
    }

    public String getClub_actual() {
        return club_actual;
    }

    public void setClub_actual(String club_actual) {
        this.club_actual = club_actual;
    }

    public String getLiga_actual() {
        return liga_actual;
    }

    public void setLiga_actual(String liga_actual) {
        this.liga_actual = liga_actual;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre_Apoderado() {
        return Nombre_Apoderado;
    }

    public void setNombre_Apoderado(String nombre_Apoderado) {
        Nombre_Apoderado = nombre_Apoderado;
    }

    public int getTelefono_apoderado() {
        return telefono_apoderado;
    }

    public void setTelefono_apoderado(int telefono_apoderado) {
        this.telefono_apoderado = telefono_apoderado;
    }

    public String getCategoria_Actual() {
        return Categoria_Actual;
    }

    public void setCategoria_Actual(String categoria_Actual) {
        Categoria_Actual = categoria_Actual;
    }

    public String getFecha_registro_Captacion() {
        return Fecha_registro_Captacion;
    }

    public void setFecha_registro_Captacion(String fecha_registro_Captacion) {
        Fecha_registro_Captacion = fecha_registro_Captacion;
    }

    public String getFecha_registro_Masivo() {
        return Fecha_registro_Masivo;
    }

    public void setFecha_registro_Masivo(String fecha_registro_Masivo) {
        Fecha_registro_Masivo = fecha_registro_Masivo;
    }

    public String getFecha_Ultima_modificacion() {
        return Fecha_Ultima_modificacion;
    }

    public void setFecha_Ultima_modificacion(String fecha_Ultima_modificacion) {
        Fecha_Ultima_modificacion = fecha_Ultima_modificacion;
    }

    public int getNum_Camiseta() {
        return Num_Camiseta;
    }

    public void setNum_Camiseta(int num_Camiseta) {
        Num_Camiseta = num_Camiseta;
    }
}
