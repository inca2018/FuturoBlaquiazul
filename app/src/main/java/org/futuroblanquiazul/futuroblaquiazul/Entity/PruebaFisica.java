package org.futuroblanquiazul.futuroblaquiazul.Entity;

/**
 * Created by Jesus Inca on 22/02/2018.
 */

public class PruebaFisica {

    int id;
    Persona persona;
    Usuario usuario;
    Unidad_Territorial Departamemto;
    Unidad_Territorial Provincia;
    Unidad_Territorial Distrito;

    double E_peso;
    double E_talla;
    double E_RJ;
    double E_CMJ;
    double E_ABK;
    double E_FMS;
    double E_Velocidad;
    double E_YOYO;

    double InformativoVelocidad;
    double InformativoPotencia;
    double InformativoResistencia;


    double PromVelocidad;
    double PromPotencia;
    double PromResistencia;

    double Total_general;

    String Fecha_registro;



    public static final PruebaFisica PRUEBA_FISICA=new PruebaFisica();
  public PruebaFisica(){

  }

    public String getFecha_registro() {
        return Fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        Fecha_registro = fecha_registro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Unidad_Territorial getDepartamemto() {
        return Departamemto;
    }

    public void setDepartamemto(Unidad_Territorial departamemto) {
        Departamemto = departamemto;
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

    public double getE_peso() {
        return E_peso;
    }

    public void setE_peso(double e_peso) {
        E_peso = e_peso;
    }

    public double getE_talla() {
        return E_talla;
    }

    public void setE_talla(double e_talla) {
        E_talla = e_talla;
    }

    public double getE_RJ() {
        return E_RJ;
    }

    public void setE_RJ(double e_RJ) {
        E_RJ = e_RJ;
    }

    public double getE_CMJ() {
        return E_CMJ;
    }

    public void setE_CMJ(double e_CMJ) {
        E_CMJ = e_CMJ;
    }

    public double getE_ABK() {
        return E_ABK;
    }

    public void setE_ABK(double e_ABK) {
        E_ABK = e_ABK;
    }

    public double getE_FMS() {
        return E_FMS;
    }

    public void setE_FMS(double e_FMS) {
        E_FMS = e_FMS;
    }

    public double getE_Velocidad() {
        return E_Velocidad;
    }

    public void setE_Velocidad(double e_Velocidad) {
        E_Velocidad = e_Velocidad;
    }

    public double getE_YOYO() {
        return E_YOYO;
    }

    public void setE_YOYO(double e_YOYO) {
        E_YOYO = e_YOYO;
    }

    public double getInformativoVelocidad() {
        return InformativoVelocidad;
    }

    public void setInformativoVelocidad(double informativoVelocidad) {
        InformativoVelocidad = informativoVelocidad;
    }

    public double getInformativoPotencia() {
        return InformativoPotencia;
    }

    public void setInformativoPotencia(double informativoPotencia) {
        InformativoPotencia = informativoPotencia;
    }

    public double getInformativoResistencia() {
        return InformativoResistencia;
    }

    public void setInformativoResistencia(double informativoResistencia) {
        InformativoResistencia = informativoResistencia;
    }

    public double getPromVelocidad() {
        return PromVelocidad;
    }

    public void setPromVelocidad(double promVelocidad) {
        PromVelocidad = promVelocidad;
    }

    public double getPromPotencia() {
        return PromPotencia;
    }

    public void setPromPotencia(double promPotencia) {
        PromPotencia = promPotencia;
    }

    public double getPromResistencia() {
        return PromResistencia;
    }

    public void setPromResistencia(double promResistencia) {
        PromResistencia = promResistencia;
    }

    public double getTotal_general() {
        return Total_general;
    }

    public void setTotal_general(double total_general) {
        Total_general = total_general;
    }


}
