package org.futuroblanquiazul.futuroblaquiazul.Entity;


import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jesus Inca on 24/01/2018.
 */

public class Persona {
    int num;
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
    int telefono_fijo;
    String Fecha_registro;
    String Fecha_actualizacion;

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
    int numero_camiseta;
    int id_posicion;
    int estado_seleccion;

    int estado_posicion;
    int codigo_posicion;
    int posicion_posicion;

    List<Integer> Numeros_Disponibles;

    public static final Persona PERSONA_TEMP=new Persona();

    List<Posicion> Lista_Posiciones;

    Unidad_Territorial departamento;
    Unidad_Territorial provincia;
    Unidad_Territorial distrito;

    int bautizo;
    int comunion;
    int confirmacion;

    int ano_nacimiento;
    int mes_nacimiento;
    int dia_nacimiento;



    public Persona(){

    }


    public String getFecha_registro() {
        return Fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        Fecha_registro = fecha_registro;
    }

    public String getFecha_actualizacion() {
        return Fecha_actualizacion;
    }

    public void setFecha_actualizacion(String fecha_actualizacion) {
        Fecha_actualizacion = fecha_actualizacion;
    }

    public int getAno_nacimiento() {
        return ano_nacimiento;
    }

    public void setAno_nacimiento(int ano_nacimiento) {
        this.ano_nacimiento = ano_nacimiento;
    }

    public int getMes_nacimiento() {
        return mes_nacimiento;
    }

    public void setMes_nacimiento(int mes_nacimiento) {
        this.mes_nacimiento = mes_nacimiento;
    }

    public int getDia_nacimiento() {
        return dia_nacimiento;
    }

    public void setDia_nacimiento(int dia_nacimiento) {
        this.dia_nacimiento = dia_nacimiento;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getTelefono_fijo() {
        return telefono_fijo;
    }

    public void setTelefono_fijo(int telefono_fijo) {
        this.telefono_fijo = telefono_fijo;
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

    public int getBautizo() {
        return bautizo;
    }

    public void setBautizo(int bautizo) {
        this.bautizo = bautizo;
    }

    public int getComunion() {
        return comunion;
    }

    public void setComunion(int comunion) {
        this.comunion = comunion;
    }

    public int getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(int confirmacion) {
        this.confirmacion = confirmacion;
    }

    public int getPosicion_posicion() {
        return posicion_posicion;
    }

    public void setPosicion_posicion(int posicion_posicion) {
        this.posicion_posicion = posicion_posicion;
    }

    public int getEstado_posicion() {
        return estado_posicion;
    }

    public void setEstado_posicion(int estado_posicion) {
        this.estado_posicion = estado_posicion;
    }

    public int getCodigo_posicion() {
        return codigo_posicion;
    }

    public void setCodigo_posicion(int codigo_posicion) {
        this.codigo_posicion = codigo_posicion;
    }

    public List<Posicion> getLista_Posiciones() {
        return Lista_Posiciones;
    }

    public void setLista_Posiciones(List<Posicion> lista_Posiciones) {
        Lista_Posiciones = lista_Posiciones;
    }

    public List<Integer> getNumeros_Disponibles() {
        return Numeros_Disponibles;
}

    public void setNumeros_Disponibles(List<Integer> numeros_Disponibles) {
        Numeros_Disponibles = numeros_Disponibles;
    }

    public void Eliminar_Elemento(int elemento){
        for(int i=0;i<this.Numeros_Disponibles.size();i++){
            if(this.Numeros_Disponibles.get(i)==(elemento)){
                this.Numeros_Disponibles.remove(i);
            }
        }

        Ordenar(this.Numeros_Disponibles);
    }

    private void Ordenar(List<Integer> numeros_disponibles) {

        Collections.sort(numeros_disponibles);
    }

    public void Agregar_Elemento(int elemento){
        for(int i=0;i<this.Numeros_Disponibles.size();i++){
                this.Numeros_Disponibles.add(elemento);
        }
        Ordenar(this.Numeros_Disponibles);
    }



    public int getNumero_camiseta() {
        return numero_camiseta;
    }

    public void setNumero_camiseta(int numero_camiseta) {
        this.numero_camiseta = numero_camiseta;
    }

    public int getId_posicion() {
        return id_posicion;
    }

    public void setId_posicion(int id_posicion) {
        this.id_posicion = id_posicion;
    }

    public int getEstado_seleccion() {
        return estado_seleccion;
    }

    public void setEstado_seleccion(int estad_seleccion) {
        this.estado_seleccion = estad_seleccion;
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
