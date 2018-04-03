package org.futuroblanquiazul.futuroblaquiazul.Utils;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PersonaEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.R;
import java.util.ArrayList;
import java.util.List;
public class Estadistico_Gestion {

    int cantidad_tiempos;

    int minutos_x_tiempo;
    int primer_aviso;
    int segundo_aviso;

    int Tiempo_Adicional;
    int Tiempo_Total;

    int Tiempo_Jugado;


    int posesion_1erTiempo;
    int getPosesion_2doTiempo;

    int tiempo_actual;

    List<Persona> Nombres_Personas;

    public static final List<RecursoEstadisticoRadioButton> RADIOS =new ArrayList<>();


    public static final List<PersonaEstadistico> LISTA_PERSONAS_INFO=new ArrayList<>();


    public static final Estadistico_Gestion TEMP=new Estadistico_Gestion();

    static {

        RADIOS.add(new RecursoEstadisticoRadioButton(1, R.id.gestion_campo_pase_gol,null,1,1,"PG"));
        RADIOS.add(new RecursoEstadisticoRadioButton(2, R.id.gestion_campo_dribbling,null,1,1,"DR"));
        RADIOS.add(new RecursoEstadisticoRadioButton(3, R.id.gestion_campo_opcion_gol,null,1,1,"OG"));
        RADIOS.add(new RecursoEstadisticoRadioButton(4, R.id.gestion_campo_remate,null,1,1,"R"));
        RADIOS.add(new RecursoEstadisticoRadioButton(5, R.id.gestion_campo_gol,null,1,1,"G"));
        RADIOS.add(new RecursoEstadisticoRadioButton(6, R.id.gestion_campo_off_sides,null,1,1,"OF"));
        RADIOS.add(new RecursoEstadisticoRadioButton(7, R.id.gestion_campo_balones_perdidos,null,1,1,"BP"));
        RADIOS.add(new RecursoEstadisticoRadioButton(8, R.id.gestion_campo_balones_recuperados,null,1,2,"BR"));
        RADIOS.add(new RecursoEstadisticoRadioButton(9, R.id.gestion_campo_faltas,null,1,2,"F"));
        RADIOS.add(new RecursoEstadisticoRadioButton(10, R.id.gestion_campo_tarjeta_amarilla,null,1,2,"TA"));
        RADIOS.add(new RecursoEstadisticoRadioButton(11, R.id.gestion_campo_tarjeta_roja,null,1,2,"TR"));
        RADIOS.add(new RecursoEstadisticoRadioButton(12, R.id.gestion_campo_atajadas,null,1,2,"ATJ"));

    }
    public Estadistico_Gestion(){

    }


    public int getTiempo_Adicional() {
        return Tiempo_Adicional;
    }

    public void setTiempo_Adicional(int tiempo_Adicional) {
        Tiempo_Adicional = tiempo_Adicional;
    }

    public int getTiempo_Total() {
        return Tiempo_Total;
    }

    public void setTiempo_Total(int tiempo_Total) {
        Tiempo_Total = tiempo_Total;
    }

    public int getTiempo_Jugado() {
        return Tiempo_Jugado;
    }

    public void setTiempo_Jugado(int tiempo_Jugado) {
        Tiempo_Jugado = tiempo_Jugado;
    }

    public List<Persona> getNombres_Personas() {
        return Nombres_Personas;
    }

    public void setNombres_Personas(List<Persona> nombres_Personas) {
        Nombres_Personas = nombres_Personas;
    }

    public int getCantidad_tiempos() {
        return cantidad_tiempos;
    }

    public void setCantidad_tiempos(int cantidad_tiempos) {
        this.cantidad_tiempos = cantidad_tiempos;
    }

    public int getMinutos_x_tiempo() {
        return minutos_x_tiempo;
    }

    public void setMinutos_x_tiempo(int minutos_x_tiempo) {
        this.minutos_x_tiempo = minutos_x_tiempo;
    }

    public int getPrimer_aviso() {
        return primer_aviso;
    }

    public void setPrimer_aviso(int primer_aviso) {
        this.primer_aviso = primer_aviso;
    }

    public int getSegundo_aviso() {
        return segundo_aviso;
    }

    public void setSegundo_aviso(int segundo_aviso) {
        this.segundo_aviso = segundo_aviso;
    }

    public int getPosesion_1erTiempo() {
        return posesion_1erTiempo;
    }

    public void setPosesion_1erTiempo(int posesion_1erTiempo) {
        this.posesion_1erTiempo = posesion_1erTiempo;
    }

    public int getGetPosesion_2doTiempo() {
        return getPosesion_2doTiempo;
    }

    public void setGetPosesion_2doTiempo(int getPosesion_2doTiempo) {
        this.getPosesion_2doTiempo = getPosesion_2doTiempo;
    }

    public int getTiempo_actual() {
        return tiempo_actual;
    }

    public void setTiempo_actual(int tiempo_actual) {
        this.tiempo_actual = tiempo_actual;
    }
}
