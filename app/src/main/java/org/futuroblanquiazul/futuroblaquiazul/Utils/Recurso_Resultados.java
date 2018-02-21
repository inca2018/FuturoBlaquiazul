package org.futuroblanquiazul.futuroblaquiazul.Utils;
 ;

 import org.futuroblanquiazul.futuroblaquiazul.R;

 import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesus Inca on 19/02/2018.
 */

public class Recurso_Resultados {

    String Pregunta;
    int Text_Pregunta;
    int Resultado;
    int Text_Resultado;

    public static final List<Recurso_Resultados> OPCION_FISICO= new ArrayList<>();
    public static final List<Recurso_Resultados> OPCION_CAPACIDAD= new ArrayList<>();
    public static final List<Recurso_Resultados> OPCION_SOCIAL= new ArrayList<>();
    public static final List<Recurso_Resultados> OPCION_TECNICO= new ArrayList<>();
    public static final List<Recurso_Resultados> OPCION_PSICO= new ArrayList<>();

    static{

        OPCION_FISICO.add(new Recurso_Resultados("Estatura según Posición", R.id.tab1_f1,0,R.id.tab1_r1));
        OPCION_FISICO.add(new Recurso_Resultados("Peso según Posición",R.id.tab1_f2,0,R.id.tab1_r2));
        OPCION_FISICO.add(new Recurso_Resultados("Técnica de Carrera",R.id.tab1_f3,0,R.id.tab1_r3));
        OPCION_FISICO.add(new Recurso_Resultados("Posición del Talón",R.id.tab1_f4,0,R.id.tab1_r4));
        OPCION_FISICO.add(new Recurso_Resultados("Aceleración",R.id.tab1_f5,0,R.id.tab1_r5));
        OPCION_FISICO.add(new Recurso_Resultados("Potencia",R.id.tab1_f6,0,R.id.tab1_r6));
        OPCION_FISICO.add(new Recurso_Resultados("Resistencia",R.id.tab1_f7,0,R.id.tab1_r7));

        OPCION_CAPACIDAD.add(new Recurso_Resultados("Percepción e Interpretación",R.id.tab2_f1,0,R.id.tab2_r1));
        OPCION_CAPACIDAD.add(new Recurso_Resultados("Toma de Decisiones",R.id.tab2_f2,0,R.id.tab2_r2));
        OPCION_CAPACIDAD.add(new Recurso_Resultados("Inteligencia Táctica y Posicionamiento",R.id.tab2_f3,0,R.id.tab2_r3));
        OPCION_CAPACIDAD.add(new Recurso_Resultados("Visión de juego",R.id.tab2_f4,0,R.id.tab2_r4));

        OPCION_SOCIAL.add(new Recurso_Resultados("Actitud ante el Adversario",R.id.tab3_f1,0,R.id.tab3_r1));
        OPCION_SOCIAL.add(new Recurso_Resultados("Actitud con Compañeros",R.id.tab3_f2,0,R.id.tab3_r2));
        OPCION_SOCIAL.add(new Recurso_Resultados("Actitud con el Árbitro",R.id.tab3_f3,0,R.id.tab3_r3));
        OPCION_SOCIAL.add(new Recurso_Resultados("Actitud con el Público",R.id.tab3_f4,0,R.id.tab3_r4));


        OPCION_TECNICO.add(new Recurso_Resultados("Control de Balones",R.id.tab4_f1,0,R.id.tab4_r1));
        OPCION_TECNICO.add(new Recurso_Resultados("Pases largos y entre líneas",R.id.tab4_f2,0,R.id.tab4_r2));
        OPCION_TECNICO.add(new Recurso_Resultados("Tiros libres",R.id.tab4_f3,0,R.id.tab4_r3));
        OPCION_TECNICO.add(new Recurso_Resultados("Juego y Técnica de Cabeceo",R.id.tab4_f4,0,R.id.tab4_r4));
        OPCION_TECNICO.add(new Recurso_Resultados("Dribbling (1 vs 1)",R.id.tab4_f5,0,R.id.tab4_r5));
        OPCION_TECNICO.add(new Recurso_Resultados("Técnica de centro",R.id.tab4_f6,0,R.id.tab4_r6));



        OPCION_PSICO.add(new Recurso_Resultados("Liderazgo en el campo",R.id.tab5_f1,0,R.id.tab5_r1));
        OPCION_PSICO.add(new Recurso_Resultados("Soporte emocional al compañero",R.id.tab5_f2,0,R.id.tab5_r2));
        OPCION_PSICO.add(new Recurso_Resultados("Respeto a la Autoridad y las Reglas",R.id.tab5_f3,0,R.id.tab5_r3));
        OPCION_PSICO.add(new Recurso_Resultados("Capacidad de sacrificio",R.id.tab5_f4,0,R.id.tab5_r4));




    }


    public Recurso_Resultados(String pregunta, int text_Pregunta, int resultado, int text_Resultado) {
        Pregunta = pregunta;
        Text_Pregunta = text_Pregunta;
        Resultado = resultado;
        Text_Resultado = text_Resultado;
    }

    public String getPregunta() {
        return Pregunta;
    }

    public void setPregunta(String pregunta) {
        Pregunta = pregunta;
    }

    public int getText_Pregunta() {
        return Text_Pregunta;
    }

    public void setText_Pregunta(int text_Pregunta) {
        Text_Pregunta = text_Pregunta;
    }

    public int getResultado() {
        return Resultado;
    }

    public void setResultado(int resultado) {
        Resultado = resultado;
    }

    public int getText_Resultado() {
        return Text_Resultado;
    }

    public void setText_Resultado(int text_Resultado) {
        Text_Resultado = text_Resultado;
    }
}
