package org.futuroblanquiazul.futuroblaquiazul.Entity;

import java.util.List;

/**
 * Created by Jesus Inca on 23/02/2018.
 */

public class PruebaTecnica {
    int id;
    Usuario usuario;
    Persona persona;
    Unidad_Territorial Departamento;
    Unidad_Territorial Provincia;
    Unidad_Territorial Distrito;
    String Fecha_registro;
    int P_Ras;
    int P_Alto;
    int Control_Ras;
    int Contorl_Alto;
    int Pase;
    int Control;
    int Remate;
    int Conduccion;
    int Cabeceo;
    int Total_general;
    List<Integer> PASE_CONTROL_RES;
    List<Integer> REMATE_RES;
    List<Integer> CONDUCCION_RES;
    List<Integer> CABECEO;
    int TT_conduccion;


    public static PruebaTecnica PRUEBA_TECNICA = new PruebaTecnica();

    public PruebaTecnica(){

    }

    public int getTT_conduccion() {
        return TT_conduccion;
    }

    public void setTT_conduccion(int TT_conduccion) {
        this.TT_conduccion = TT_conduccion;
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

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
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

    public String getFecha_registro() {
        return Fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        Fecha_registro = fecha_registro;
    }

    public int getP_Ras() {
        return P_Ras;
    }

    public void setP_Ras(int p_Ras) {
        P_Ras = p_Ras;
    }

    public int getP_Alto() {
        return P_Alto;
    }

    public void setP_Alto(int p_Alto) {
        P_Alto = p_Alto;
    }

    public int getControl_Ras() {
        return Control_Ras;
    }

    public void setControl_Ras(int control_Ras) {
        Control_Ras = control_Ras;
    }

    public int getContorl_Alto() {
        return Contorl_Alto;
    }

    public void setContorl_Alto(int contorl_Alto) {
        Contorl_Alto = contorl_Alto;
    }

    public int getPase() {
        return Pase;
    }

    public void setPase(int pase) {
        Pase = pase;
    }

    public int getControl() {
        return Control;
    }

    public void setControl(int control) {
        Control = control;
    }

    public int getRemate() {
        return Remate;
    }

    public void setRemate(int remate) {
        Remate = remate;
    }

    public int getConduccion() {
        return Conduccion;
    }

    public void setConduccion(int conduccion) {
        Conduccion = conduccion;
    }

    public int getCabeceo() {
        return Cabeceo;
    }

    public void setCabeceo(int cabeceo) {
        Cabeceo = cabeceo;
    }

    public int getTotal_general() {
        return Total_general;
    }

    public void setTotal_general(int total_general) {
        Total_general = total_general;
    }

    public List<Integer> getPASE_CONTROL_RES() {
        return PASE_CONTROL_RES;
    }

    public void setPASE_CONTROL_RES(List<Integer> PASE_CONTROL_RES) {
        this.PASE_CONTROL_RES = PASE_CONTROL_RES;
    }

    public List<Integer> getREMATE_RES() {
        return REMATE_RES;
    }

    public void setREMATE_RES(List<Integer> REMATE_RES) {
        this.REMATE_RES = REMATE_RES;
    }

    public List<Integer> getCONDUCCION_RES() {
        return CONDUCCION_RES;
    }

    public void setCONDUCCION_RES(List<Integer> CONDUCCION_RES) {
        this.CONDUCCION_RES = CONDUCCION_RES;
    }

    public List<Integer> getCABECEO() {
        return CABECEO;
    }

    public void setCABECEO(List<Integer> CABECEO) {
        this.CABECEO = CABECEO;
    }
}
