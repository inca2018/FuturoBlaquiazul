package org.futuroblanquiazul.futuroblaquiazul.Utils;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPersonaCambio;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPersonaCambio2;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PersonaEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Total_Segmento;
import org.futuroblanquiazul.futuroblaquiazul.R;
import java.util.ArrayList;
import java.util.List;
public class Estadistico_Gestion {

    int cantidad_tiempos;

    int minutos_x_tiempo;
    int primer_aviso;
    int segundo_aviso;
    int tercer_aviso;
    int cuarto_aviso;

    int Tiempo_Adicional;
    int Tiempo_Adicional2;
    int Tiempo_Total;
    int Tiempo_Total2;
    int total_primer_tiempo;
    int total_segundo_tiempo;

    int Tiempo_Jugado;
    int bloque_tiempo;


    int posesion_1erTiempo;
    int getPosesion_2doTiempo;

    int tiempo_actual;

    int ZPG1;
    int ZF1;
    int ZR1;
    int ZPG12;

    int ZPG2;
    int ZF2;
    int ZR2;
    int ZPG22;


    int minutos_jugados;
    int segundos_jugados;
    int estado_partido;



    int OF_IZQ_DR;
    int OF_IZQ_PG;
    int OF_CEN_OG;
    int OF_CEN_R;
    int OF_ZF_OG;
    int OF_ZF_R;
    int OF_DER_DR;
    int OF_DER_PG;

    int OF_IZQ_DR2;
    int OF_IZQ_PG2;
    int OF_CEN_OG2;
    int OF_CEN_R2;
    int OF_ZF_OG2;
    int OF_ZF_R2;
    int OF_DER_DR2;
    int OF_DER_PG2;

    int Contador;

    int posesion1;
    int posesion2;

    boolean pause;
    boolean stop;


    PersonaEstadistico Titular_Cambio;
    PersonaEstadistico Suplente_Cambio;



    List<Persona> Nombres_Personas;





    public static final List<RecursoEstadisticoRadioButton> RADIOS =new ArrayList<>();


    public static final List<PersonaEstadistico> LISTA_PERSONAS_TODO =new ArrayList<>();
    public static final List<PersonaEstadistico> LISTA_PERSONAS_SUPLENTES=new ArrayList<>();
    public static final List<PersonaEstadistico> LISTA_PERSONAS_TITULARES=new ArrayList<>();
    public static AdapterPersonaCambio ADAPTER_TITULARES=null;
    public static AdapterPersonaCambio2 ADAPTER_SUPLENTES=null;



    public static final List<Total_Segmento> TOTAL_SEGMENTOS =new ArrayList<>();
    public static final List<Total_Segmento> TOTAL_AREAS =new ArrayList<>();


    public static final List<String> LISTA_LINEA_TIEMPO=new ArrayList<>();



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
        RADIOS.add(new RecursoEstadisticoRadioButton(13, R.id.gestion_campo_gol_oponente,null,1,3,"GO"));


        TOTAL_AREAS.add(new Total_Segmento(1,0));
        TOTAL_AREAS.add(new Total_Segmento(2,0));
        TOTAL_AREAS.add(new Total_Segmento(3,0));
        TOTAL_AREAS.add(new Total_Segmento(4,0));
        TOTAL_AREAS.add(new Total_Segmento(5,0));
        TOTAL_AREAS.add(new Total_Segmento(6,0));
        TOTAL_AREAS.add(new Total_Segmento(7,0));
        TOTAL_AREAS.add(new Total_Segmento(8,0));

        TOTAL_SEGMENTOS.add(new Total_Segmento(1,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(2,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(3,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(4,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(5,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(6,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(7,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(8,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(9,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(10,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(11,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(12,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(13,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(14,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(15,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(16,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(17,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(18,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(19,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(20,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(21,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(22,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(23,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(24,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(25,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(26,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(27,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(28,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(29,0));
        TOTAL_SEGMENTOS.add(new Total_Segmento(30,0));


    }
    public Estadistico_Gestion(){

    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public int getPosesion1() {
        return posesion1;
    }

    public void setPosesion1(int posesion1) {
        this.posesion1 = posesion1;
    }

    public int getPosesion2() {
        return posesion2;
    }

    public void setPosesion2(int posesion2) {
        this.posesion2 = posesion2;
    }

    public int getContador() {
        return Contador;
    }

    public void setContador(int contador) {
        Contador = contador;
    }

    public int getTiempo_Total2() {
        return Tiempo_Total2;
    }

    public void setTiempo_Total2(int tiempo_Total2) {
        Tiempo_Total2 = tiempo_Total2;
    }

    public int getTiempo_Adicional2() {
        return Tiempo_Adicional2;
    }

    public void setTiempo_Adicional2(int tiempo_Adicional2) {
        Tiempo_Adicional2 = tiempo_Adicional2;
    }

    public int getTotal_primer_tiempo() {
        return total_primer_tiempo;
    }

    public void setTotal_primer_tiempo(int total_primer_tiempo) {
        this.total_primer_tiempo = total_primer_tiempo;
    }

    public int getTotal_segundo_tiempo() {
        return total_segundo_tiempo;
    }

    public void setTotal_segundo_tiempo(int total_segundo_tiempo) {
        this.total_segundo_tiempo = total_segundo_tiempo;
    }

    public int getTercer_aviso() {
        return tercer_aviso;
    }

    public void setTercer_aviso(int tercer_aviso) {
        this.tercer_aviso = tercer_aviso;
    }

    public int getCuarto_aviso() {
        return cuarto_aviso;
    }

    public void setCuarto_aviso(int cuarto_aviso) {
        this.cuarto_aviso = cuarto_aviso;
    }

    public int getOF_IZQ_DR() {
        return OF_IZQ_DR;
    }

    public void setOF_IZQ_DR(int OF_IZQ_DR) {
        this.OF_IZQ_DR = OF_IZQ_DR;
    }

    public int getOF_IZQ_PG() {
        return OF_IZQ_PG;
    }

    public void setOF_IZQ_PG(int OF_IZQ_PG) {
        this.OF_IZQ_PG = OF_IZQ_PG;
    }

    public int getOF_CEN_OG() {
        return OF_CEN_OG;
    }

    public void setOF_CEN_OG(int OF_CEN_OG) {
        this.OF_CEN_OG = OF_CEN_OG;
    }

    public int getOF_CEN_R() {
        return OF_CEN_R;
    }

    public void setOF_CEN_R(int OF_CEN_R) {
        this.OF_CEN_R = OF_CEN_R;
    }

    public int getOF_ZF_OG() {
        return OF_ZF_OG;
    }

    public void setOF_ZF_OG(int OF_ZF_OG) {
        this.OF_ZF_OG = OF_ZF_OG;
    }

    public int getOF_ZF_R() {
        return OF_ZF_R;
    }

    public void setOF_ZF_R(int OF_ZF_R) {
        this.OF_ZF_R = OF_ZF_R;
    }

    public int getOF_DER_DR() {
        return OF_DER_DR;
    }

    public void setOF_DER_DR(int OF_DER_DR) {
        this.OF_DER_DR = OF_DER_DR;
    }

    public int getOF_DER_PG() {
        return OF_DER_PG;
    }

    public void setOF_DER_PG(int OF_DER_PG) {
        this.OF_DER_PG = OF_DER_PG;
    }

    public int getOF_IZQ_DR2() {
        return OF_IZQ_DR2;
    }

    public void setOF_IZQ_DR2(int OF_IZQ_DR2) {
        this.OF_IZQ_DR2 = OF_IZQ_DR2;
    }

    public int getOF_IZQ_PG2() {
        return OF_IZQ_PG2;
    }

    public void setOF_IZQ_PG2(int OF_IZQ_PG2) {
        this.OF_IZQ_PG2 = OF_IZQ_PG2;
    }

    public int getOF_CEN_OG2() {
        return OF_CEN_OG2;
    }

    public void setOF_CEN_OG2(int OF_CEN_OG2) {
        this.OF_CEN_OG2 = OF_CEN_OG2;
    }

    public int getOF_CEN_R2() {
        return OF_CEN_R2;
    }

    public void setOF_CEN_R2(int OF_CEN_R2) {
        this.OF_CEN_R2 = OF_CEN_R2;
    }

    public int getOF_ZF_OG2() {
        return OF_ZF_OG2;
    }

    public void setOF_ZF_OG2(int OF_ZF_OG2) {
        this.OF_ZF_OG2 = OF_ZF_OG2;
    }

    public int getOF_ZF_R2() {
        return OF_ZF_R2;
    }

    public void setOF_ZF_R2(int OF_ZF_R2) {
        this.OF_ZF_R2 = OF_ZF_R2;
    }

    public int getOF_DER_DR2() {
        return OF_DER_DR2;
    }

    public void setOF_DER_DR2(int OF_DER_DR2) {
        this.OF_DER_DR2 = OF_DER_DR2;
    }

    public int getOF_DER_PG2() {
        return OF_DER_PG2;
    }

    public void setOF_DER_PG2(int OF_DER_PG2) {
        this.OF_DER_PG2 = OF_DER_PG2;
    }

    public int getEstado_partido() {
        return estado_partido;
    }

    public void setEstado_partido(int estado_partido) {
        this.estado_partido = estado_partido;
    }

    public int getMinutos_jugados() {
        return minutos_jugados;
    }

    public void setMinutos_jugados(int minutos_jugados) {
        this.minutos_jugados = minutos_jugados;
    }

    public int getSegundos_jugados() {
        return segundos_jugados;
    }

    public void setSegundos_jugados(int segundos_jugados) {
        this.segundos_jugados = segundos_jugados;
    }

    public PersonaEstadistico getTitular_Cambio() {
        return Titular_Cambio;
    }

    public void setTitular_Cambio(PersonaEstadistico titular_Cambio) {
        Titular_Cambio = titular_Cambio;
    }

    public PersonaEstadistico getSuplente_Cambio() {
        return Suplente_Cambio;
    }

    public void setSuplente_Cambio(PersonaEstadistico suplente_Cambio) {
        Suplente_Cambio = suplente_Cambio;
    }

    public int getZPG1() {
        return ZPG1;
    }

    public void setZPG1(int ZPG1) {
        this.ZPG1 = ZPG1;
    }

    public int getZF1() {
        return ZF1;
    }

    public void setZF1(int ZF1) {
        this.ZF1 = ZF1;
    }

    public int getZR1() {
        return ZR1;
    }

    public void setZR1(int ZR1) {
        this.ZR1 = ZR1;
    }

    public int getZPG12() {
        return ZPG12;
    }

    public void setZPG12(int ZPG12) {
        this.ZPG12 = ZPG12;
    }

    public int getZPG2() {
        return ZPG2;
    }

    public void setZPG2(int ZPG2) {
        this.ZPG2 = ZPG2;
    }

    public int getZF2() {
        return ZF2;
    }

    public void setZF2(int ZF2) {
        this.ZF2 = ZF2;
    }

    public int getZR2() {
        return ZR2;
    }

    public void setZR2(int ZR2) {
        this.ZR2 = ZR2;
    }

    public int getZPG22() {
        return ZPG22;
    }

    public void setZPG22(int ZPG22) {
        this.ZPG22 = ZPG22;
    }

    public int getBloque_tiempo() {
        return bloque_tiempo;
    }

    public void setBloque_tiempo(int bloque_tiempo) {
        this.bloque_tiempo = bloque_tiempo;
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
