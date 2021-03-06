package org.futuroblanquiazul.futuroblaquiazul.Utils;


import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesus Inca on 24/01/2018.
 */

public class Recursos_Diagnostico {

    public static final List<Captacion_Vista> LISTA_VISTAS= new ArrayList<>();
    public static final List<Captacion_Vista> LISTA_VISTAS2= new ArrayList<>();
    public static final List<Captacion_Vista> LISTA_VISTAS3= new ArrayList<>();
    public static final List<Captacion_Vista> LISTA_VISTAS4= new ArrayList<>();



    public static final List<Captacion_Vista> LISTA_VISTAS5= new ArrayList<>();



    public  static final List<Captacion_funcional> LISTA_FISICO =new ArrayList<>();
    public static final List<Captacion_funcional> LISTA_CAPACIDAD =new ArrayList<>();
    public static final List<Captacion_funcional> LISTA_SOCIAL =new ArrayList<>();
    public static final List<Captacion_funcional> LISTA_TECNICO =new ArrayList<>();
    public static final List<Captacion_funcional> LISTA_PSICO =new ArrayList<>();

    public static final List<Captacion_funcional> LISTA_SOCIAL2 =new ArrayList<>();
    public static final List<Captacion_funcional> LISTA_PSICO2 =new ArrayList<>();

    public static final List<Captacion_funcional> LISTA_PRUEBA_TECNICA_PASE_CONTROL =new ArrayList<>();
    public static final List<Captacion_funcional> LISTA_PRUEBA_TECNICA_REMATE =new ArrayList<>();
    public static final List<Captacion_funcional> LISTA_PRUEBA_TECNICA_CONDUCCION =new ArrayList<>();
    public static final List<Captacion_funcional> LISTA_PRUEBA_TECNICA_CABECEO=new ArrayList<>();



    public  static final List<Captacion_funcional> LISTA_INCA1=new ArrayList<>();
    public  static final List<Captacion_funcional> LISTA_INCA2=new ArrayList<>();
    public  static final List<Captacion_funcional> LISTA_INCA3=new ArrayList<>();
    public  static final List<Captacion_funcional> LISTA_INCA4=new ArrayList<>();
    public  static final List<Captacion_funcional> LISTA_INCA5=new ArrayList<>();



    static{

        LISTA_VISTAS.add(new Captacion_Vista(
                R.layout.area_fisico,
                R.id.contenedor_fisico,
                R.id.panel_fisico,
                R.id.area_fisico,
                0,
                R.id.total_fisico));
        LISTA_VISTAS.add(new Captacion_Vista(R.layout.area_capacidad,R.id.contenedor_capacidad,R.id.panel_capacidad,R.id.area_capacidad,0,R.id.total_capacidad));
        LISTA_VISTAS.add(new Captacion_Vista(R.layout.area_social,R.id.contenedor_social,R.id.panel_social,R.id.area_social,0,R.id.total_social));
        LISTA_VISTAS.add(new Captacion_Vista(R.layout.area_tecnico,R.id.contenedor_tecnico,R.id.panel_tecnico,R.id.area_tecnico,0,R.id.total_tecnico));
        LISTA_VISTAS.add(new Captacion_Vista(R.layout.area_psico,R.id.contenedor_psico,R.id.panel_psico,R.id.area_psico,0,R.id.total_psico));

        LISTA_VISTAS2.add(new Captacion_Vista(
                R.layout.area_social2,
                R.id.contenedor_social2,
                R.id.panel_social2,
                R.id.area_social2,
                0,
                R.id.total_social2));
        LISTA_VISTAS2.add(new Captacion_Vista(
                R.layout.area_psico2,
                R.id.contenedor_psico2,
                R.id.panel_psico2,
                R.id.area_psico2,
                0,
                R.id.total_psico2));

        LISTA_VISTAS3.add(new Captacion_Vista(
                     R.layout.entradas_fisico,
                     R.id.contenedor_fisico_prueba,
                     R.id.panel_fisico_prueba,
                     R.id.area_opcion_fisico,
                     0,
                    R.id.total_fisico_prueba
        ));



  // PRUEBA TECNICA  DATOS IMPLEMENTADOD  ARMADO DE LISTAS
        LISTA_VISTAS4.add(new Captacion_Vista(
                R.layout.area_pase_control_opcion1,
                R.id.contenedor_tecnica1_opcion1,
                R.id.panel_tecnica1_opcion1,
                R.id.area_tactica_opcion1,
                0,
                0
        ));

        LISTA_VISTAS4.add(new Captacion_Vista(
                R.layout.area_remate,
                R.id.contenedor_tecnica2,
                R.id.panel_tecnica2,
                R.id.area_tactica2,
                0,
                0
        ));

      LISTA_VISTAS4.add(new Captacion_Vista(
                R.layout.area_conduccion,
                R.id.contenedor_tecnica3,
                R.id.panel_tecnica3,
                R.id.area_tactica3,
                0,
                0
        ));

           LISTA_VISTAS4.add(new Captacion_Vista(
                R.layout.area_cabeceo,
                R.id.contenedor_tecnica4,
                R.id.panel_tecnica4,
                R.id.area_tactica4,
                0,
                0
        ));



        LISTA_VISTAS5.add(new Captacion_Vista(
                R.layout.area_info_psico1,
                R.id.contenedor_inca1,
                R.id.panel_inca1,
                R.id.area_inca1,
                0,
                0
        ));

        LISTA_VISTAS5.add(new Captacion_Vista(
                R.layout.area_info_psico2,
                R.id.contenedor_inca2,
                R.id.panel_inca2,
                R.id.area_inca2,
                0,
                0
        ));

        LISTA_VISTAS5.add(new Captacion_Vista(
                R.layout.area_info_psico3,
                R.id.contenedor_inca3,
                R.id.panel_inca3,
                R.id.area_inca3,
                0,
                0
        ));

        LISTA_VISTAS5.add(new Captacion_Vista(
                R.layout.area_info_psico4,
                R.id.contenedor_inca4,
                R.id.panel_inca4,
                R.id.area_inca4,
                0,
                0
        ));

        LISTA_VISTAS5.add(new Captacion_Vista(
                R.layout.area_info_psico5,
                R.id.contenedor_inca5,
                R.id.panel_inca5,
                R.id.area_inca5,
                0,
                0
        ));



        LISTA_PRUEBA_TECNICA_PASE_CONTROL.add(new Captacion_funcional("",R.id.op1,
                              R.id.grupo_tecnico1,
                              R.id.tac11,
                              R.id.tac12,
                              R.id.tac13,
                              R.id.tac14,
                             0
                              ));
        LISTA_PRUEBA_TECNICA_PASE_CONTROL.add(new Captacion_funcional("",R.id.op2,
                R.id.grupo_tecnico2,
                R.id.tac21,
                R.id.tac22,
                R.id.tac23,
                R.id.tac24,
                0
        ));
        LISTA_PRUEBA_TECNICA_PASE_CONTROL.add(new Captacion_funcional("",R.id.op3,
                R.id.grupo_tecnico3,
                R.id.tac31,
                R.id.tac32,
                R.id.tac33,
                R.id.tac34,
                0
        ));

        LISTA_PRUEBA_TECNICA_PASE_CONTROL.add(new Captacion_funcional("",R.id.op4,
                R.id.grupo_tecnico4,
                R.id.tac41,
                R.id.tac42,
                R.id.tac43,
                R.id.tac44,
                0
        ));

        LISTA_PRUEBA_TECNICA_PASE_CONTROL.add(new Captacion_funcional("",R.id.op5,
                R.id.grupo_tecnico5,
                R.id.tac51,
                R.id.tac52,
                R.id.tac53,
                R.id.tac54,
                0
        ));

        LISTA_PRUEBA_TECNICA_PASE_CONTROL.add(new Captacion_funcional("",R.id.op6,
                R.id.grupo_tecnico6,
                R.id.tac61,
                R.id.tac62,
                R.id.tac63,
                R.id.tac64,
                0
        ));

        LISTA_PRUEBA_TECNICA_PASE_CONTROL.add(new Captacion_funcional("",R.id.op7,
                R.id.grupo_tecnico7,
                R.id.tac71,
                R.id.tac72,
                R.id.tac73,
                R.id.tac74,
                0
        ));

        LISTA_PRUEBA_TECNICA_PASE_CONTROL.add(new Captacion_funcional("",R.id.op8,
                R.id.grupo_tecnico8,
                R.id.tac81,
                R.id.tac82,
                R.id.tac83,
                R.id.tac84,
                0
        ));





        LISTA_PRUEBA_TECNICA_REMATE.add(new Captacion_funcional("",R.id.op12,
                R.id.grupo_tecnico12,
                R.id.re112,
                R.id.re122,
                R.id.re132,
                R.id.re142,
                0
        ));
        LISTA_PRUEBA_TECNICA_REMATE.add(new Captacion_funcional("",R.id.op22,
                R.id.grupo_tecnico22,
                R.id.re212,
                R.id.re222,
                R.id.re232,
                R.id.re242,
                0
        ));
        LISTA_PRUEBA_TECNICA_REMATE.add(new Captacion_funcional("",R.id.op32,
                R.id.grupo_tecnico32,
                R.id.re312,
                R.id.re322,
                R.id.re332,
                R.id.re342,
                0
        ));

        LISTA_PRUEBA_TECNICA_REMATE.add(new Captacion_funcional("",R.id.op42,
                R.id.grupo_tecnico42,
                R.id.re412,
                R.id.re422,
                R.id.re432,
                R.id.re442,
                0
        ));

        LISTA_PRUEBA_TECNICA_REMATE.add(new Captacion_funcional("",R.id.op52,
                R.id.grupo_tecnico52,
                R.id.re512,
                R.id.re522,
                R.id.re532,
                R.id.re542,
                0
        ));

        LISTA_PRUEBA_TECNICA_REMATE.add(new Captacion_funcional("",R.id.op62,
                R.id.grupo_tecnico62,
                R.id.re612,
                R.id.re622,
                R.id.re632,
                R.id.re642,
                0
        ));

        LISTA_PRUEBA_TECNICA_REMATE.add(new Captacion_funcional("",R.id.op72,
                R.id.grupo_tecnico72,
                R.id.re712,
                R.id.re722,
                R.id.re732,
                R.id.re742,
                0
        ));

        LISTA_PRUEBA_TECNICA_REMATE.add(new Captacion_funcional("",R.id.op82,
                R.id.grupo_tecnico82,
                R.id.re812,
                R.id.re822,
                R.id.re832,
                R.id.re842,
                0
        ));







        LISTA_PRUEBA_TECNICA_CONDUCCION.add(new Captacion_funcional("",R.id.op123,
                R.id.grupo_tecnico123,
                R.id.cond112,
                R.id.cond122,
                R.id.cond132,
                R.id.cond142,
                0
        ));
        LISTA_PRUEBA_TECNICA_CONDUCCION.add(new Captacion_funcional("",R.id.op223,
                R.id.grupo_tecnico223,
                R.id.cond212,
                R.id.cond222,
                R.id.cond232,
                R.id.cond242,
                0
        ));
        LISTA_PRUEBA_TECNICA_CONDUCCION.add(new Captacion_funcional("",R.id.op323,
                R.id.grupo_tecnico323,
                R.id.cond312,
                R.id.cond322,
                R.id.cond332,
                R.id.cond342,
                0
        ));

        LISTA_PRUEBA_TECNICA_CONDUCCION.add(new Captacion_funcional("",R.id.op423,
                R.id.grupo_tecnico423,
                R.id.cond412,
                R.id.cond422,
                R.id.cond432,
                R.id.cond442,
                0
        ));

        LISTA_PRUEBA_TECNICA_CONDUCCION.add(new Captacion_funcional("",R.id.op523,
                R.id.grupo_tecnico523,
                R.id.cond512,
                R.id.cond522,
                R.id.cond532,
                R.id.cond542,
                0
        ));





        LISTA_PRUEBA_TECNICA_CABECEO.add(new Captacion_funcional("",R.id.op1234,
                R.id.grupo_tecnico1234,
                R.id.cab112,
                R.id.cab122,
                R.id.cab132,
                R.id.cab142,
                0
        ));
        LISTA_PRUEBA_TECNICA_CABECEO.add(new Captacion_funcional("",R.id.op2234,
                R.id.grupo_tecnico2234,
                R.id.cab212,
                R.id.cab222,
                R.id.cab232,
                R.id.cab242,
                0
        ));
        LISTA_PRUEBA_TECNICA_CABECEO.add(new Captacion_funcional("",R.id.op3234,
                R.id.grupo_tecnico3234,
                R.id.cab312,
                R.id.cab322,
                R.id.cab332,
                R.id.cab342,
                0
        ));

        LISTA_PRUEBA_TECNICA_CABECEO.add(new Captacion_funcional("",R.id.op4234,
                R.id.grupo_tecnico4234,
                R.id.cab412,
                R.id.cab422,
                R.id.cab432,
                R.id.cab442,
                0
        ));


        // ----------------------------------------------------------------------------------

        LISTA_FISICO.add(new Captacion_funcional("Estatura según Posición",R.id.opcion_fisico1,
                             R.id.group_fisico1,
                             R.id.f11,
                             R.id.f12,
                             R.id.f13,
                             R.id.f14,0));
        LISTA_FISICO.add(new Captacion_funcional("Peso según Posición",R.id.opcion_fisico2,
                R.id.group_fisico2,
                R.id.f21,
                R.id.f22,
                R.id.f23,
                R.id.f24,0));
        LISTA_FISICO.add(new Captacion_funcional("Técnica de Carrera",R.id.opcion_fisico3,
                R.id.group_fisico3,
                R.id.f31,
                R.id.f32,
                R.id.f33,
                R.id.f34,0));
        LISTA_FISICO.add(new Captacion_funcional("Posición del Talón",R.id.opcion_fisico4,
                R.id.group_fisico4,
                R.id.f41,
                R.id.f42,
                R.id.f43,
                R.id.f44,0));
        LISTA_FISICO.add(new Captacion_funcional("Aceleración",R.id.opcion_fisico5,
                R.id.group_fisico5,
                R.id.f51,
                R.id.f52,
                R.id.f53,
                R.id.f54,0));

        LISTA_FISICO.add(new Captacion_funcional("Potencia",R.id.opcion_fisico6,
                R.id.group_fisico6,
                R.id.f61,
                R.id.f62,
                R.id.f63,
                R.id.f64,0));
        LISTA_FISICO.add(new Captacion_funcional("Resistencia",R.id.opcion_fisico7,
                R.id.group_fisico7,
                R.id.f71,
                R.id.f72,
                R.id.f73,
                R.id.f74,0));

        LISTA_CAPACIDAD.add(new Captacion_funcional("Percepción e Interpretación",R.id.opcion_capacidad1,
                R.id.group_capacidad1,
                R.id.c11,
                R.id.c12,
                R.id.c13,
                R.id.c14,0));
        LISTA_CAPACIDAD.add(new Captacion_funcional("Toma de Decisiones",R.id.opcion_capacidad2,
                R.id.group_capacidad2,
                R.id.c21,
                R.id.c22,
                R.id.c23,
                R.id.c24,0));
        LISTA_CAPACIDAD.add(new Captacion_funcional("Inteligencia Táctica y Posicionamiento",R.id.opcion_capacidad3,
                R.id.group_capacidad3,
                R.id.c31,
                R.id.c32,
                R.id.c33,
                R.id.c34,0));

        LISTA_CAPACIDAD.add(new Captacion_funcional("Visión de juego",R.id.opcion_capacidad4,
                R.id.group_capacidad4,
                R.id.c41,
                R.id.c42,
                R.id.c43,
                R.id.c44,0));

        LISTA_SOCIAL.add(new Captacion_funcional("Actitud ante el Adversario",R.id.opcion_social1,
                R.id.group_social1,
                R.id.tac512,
                R.id.s12,
                R.id.s13,
                R.id.s14,0));

        LISTA_SOCIAL.add(new Captacion_funcional("Actitud con Compañeros",R.id.opcion_social2,
                R.id.group_social2,
                R.id.tac612,
                R.id.s22,
                R.id.s23,
                R.id.s24,0));

        LISTA_SOCIAL.add(new Captacion_funcional("Actitud con el Árbitro",R.id.opcion_social3,
                R.id.group_social3,
                R.id.tac712,
                R.id.s32,
                R.id.s33,
                R.id.s34,0));
        LISTA_SOCIAL.add(new Captacion_funcional("Actitud con el Público",R.id.opcion_social4,
                R.id.group_social4,
                R.id.tac412,
                R.id.s42,
                R.id.s43,
                R.id.s44,0));

        LISTA_TECNICO.add(new Captacion_funcional("Control de Balones",R.id.opcion_tecnico1,
                R.id.group_tecnico1,
                R.id.t11,
                R.id.t12,
                R.id.t13,
                R.id.t14,0));

        LISTA_TECNICO.add(new Captacion_funcional("Pases largos y entre líneas",R.id.opcion_tecnico2,
                R.id.group_tecnico2,
                R.id.t21,
                R.id.t22,
                R.id.t23,
                R.id.t24,0));

        LISTA_TECNICO.add(new Captacion_funcional("Tiros libres",R.id.opcion_tecnico3,
                R.id.group_tecnico3,
                R.id.t31,
                R.id.t32,
                R.id.t33,
                R.id.t34,0));


        LISTA_TECNICO.add(new Captacion_funcional("Juego y Técnica de Cabeceo",R.id.opcion_tecnico4,
                R.id.group_tecnico4,
                R.id.t41,
                R.id.t42,
                R.id.t43,
                R.id.t44,0));


        LISTA_TECNICO.add(new Captacion_funcional("Dribbling (1 vs 1)",R.id.opcion_tecnico5,
                R.id.group_tecnico5,
                R.id.t51,
                R.id.t52,
                R.id.t53,
                R.id.t54,0));


        LISTA_TECNICO.add(new Captacion_funcional("Técnica de centro",R.id.opcion_tecnico6,
                R.id.group_tecnico6,
                R.id.t61,
                R.id.t62,
                R.id.t63,
                R.id.t64,0));


        LISTA_PSICO.add(new Captacion_funcional("Liderazgo en el campo",R.id.opcion_psico1,
                R.id.group_psico1,
                R.id.p11,
                R.id.p12,
                R.id.p13,
                R.id.p14,0));
        LISTA_PSICO.add(new Captacion_funcional("Soporte emocional al compañero",R.id.opcion_psico2,
                R.id.group_psico2,
                R.id.p21,
                R.id.p22,
                R.id.p23,
                R.id.p24,0));
        LISTA_PSICO.add(new Captacion_funcional("Respeto a la Autoridad y las Reglas",R.id.opcion_psico3,
                R.id.group_psico3,
                R.id.p31,
                R.id.p32,
                R.id.p33,
                R.id.p34,0));
        LISTA_PSICO.add(new Captacion_funcional("Capacidad de sacrificio",R.id.opcion_psico4,
                R.id.group_psico4,
                R.id.p41,
                R.id.p42,
                R.id.p43,
                R.id.p44,0));





        LISTA_SOCIAL2.add(new Captacion_funcional("Actitud ante el Adversario",R.id.opcion_social12,
                R.id.group_social12,
                R.id.s112,
                R.id.s122,
                R.id.s132,
                R.id.s142,0));

        LISTA_SOCIAL2.add(new Captacion_funcional("Actitud con Compañeros",R.id.opcion_social22,
                R.id.group_social22,
                R.id.s212,
                R.id.s222,
                R.id.s232,
                R.id.s242,0));

        LISTA_SOCIAL2.add(new Captacion_funcional("Actitud con el Árbitro",R.id.opcion_social32,
                R.id.group_social32,
                R.id.s312,
                R.id.s322,
                R.id.s332,
                R.id.s342,0));
        LISTA_SOCIAL2.add(new Captacion_funcional("Actitud con el Público",R.id.opcion_social42,
                R.id.group_social42,
                R.id.s412,
                R.id.s422,
                R.id.s432,
                R.id.s442,0));


        LISTA_PSICO2.add(new Captacion_funcional("Liderazgo en el campo",R.id.opcion_psico12,
                R.id.group_psico12,
                R.id.p112,
                R.id.p122,
                R.id.p132,
                R.id.p142,0));
        LISTA_PSICO2.add(new Captacion_funcional("Soporte emocional al compañero",R.id.opcion_psico22,
                R.id.group_psico22,
                R.id.p212,
                R.id.p222,
                R.id.p232,
                R.id.p242,0));
        LISTA_PSICO2.add(new Captacion_funcional("Respeto a la Autoridad y las Reglas",R.id.opcion_psico32,
                R.id.group_psico32,
                R.id.p312,
                R.id.p322,
                R.id.p332,
                R.id.p342,0));
        LISTA_PSICO2.add(new Captacion_funcional("Capacidad de sacrificio",R.id.opcion_psico42,
                R.id.group_psico42,
                R.id.p412,
                R.id.p422,
                R.id.p432,
                R.id.p442,0));



        LISTA_INCA1.add(new Captacion_funcional("Conocimiento Táctico",R.id.opcion_inca1,
                R.id.group_inca1,
                R.id.inca11,
                R.id.inca12,
                R.id.inca13,
                R.id.inca14,0));

        LISTA_INCA1.add(new Captacion_funcional("Concentración",R.id.opcion_inca2,
                R.id.group_inca2,
                R.id.inca21,
                R.id.inca22,
                R.id.inca23,
                R.id.inca24,0));

        LISTA_INCA1.add(new Captacion_funcional("Toma de Decisiones",R.id.opcion_inca3,
                R.id.group_inca3,
                R.id.inca31,
                R.id.inca32,
                R.id.inca33,
                R.id.inca34,0));

        LISTA_INCA1.add(new Captacion_funcional("Visión",R.id.opcion_inca4,
                R.id.group_inca4,
                R.id.inca41,
                R.id.inca42,
                R.id.inca43,
                R.id.inca44,0));

        LISTA_INCA2.add(new Captacion_funcional("Disciplina",R.id.opcion_inca12,
                R.id.group_inca12,
                R.id.inca112,
                R.id.inca122,
                R.id.inca132,
                R.id.inca142,0));


        LISTA_INCA2.add(new Captacion_funcional("Motivación",R.id.opcion_inca22,
                R.id.group_inca22,
                R.id.inca212,
                R.id.inca222,
                R.id.inca232,
                R.id.inca242,0));


        LISTA_INCA3.add(new Captacion_funcional("Gestión de Estrés",R.id.opcion_inca13,
                R.id.group_inca13,
                R.id.inca113,
                R.id.inca123,
                R.id.inca133,
                R.id.inca143,0));

        LISTA_INCA3.add(new Captacion_funcional("Afrontamiento",R.id.opcion_inca23,
                R.id.group_inca23,
                R.id.inca213,
                R.id.inca223,
                R.id.inca233,
                R.id.inca243,0));

        LISTA_INCA3.add(new Captacion_funcional("Control de Juego",R.id.opcion_inca33,
                R.id.group_inca33,
                R.id.inca313,
                R.id.inca323,
                R.id.inca333,
                R.id.inca343,0));

        LISTA_INCA3.add(new Captacion_funcional("Regulación del Esfuerzo Fisico",R.id.opcion_inca43,
                        R.id.group_inca43,
                        R.id.inca413,
                        R.id.inca423,
                        R.id.inca433,
                        R.id.inca443,0));

        LISTA_INCA3.add(new Captacion_funcional("Agresividad Defensiva",R.id.opcion_inca53,
                R.id.group_inca53,
                R.id.inca511,
                R.id.inca512,
                R.id.inca513,
                R.id.inca514,0));

        LISTA_INCA3.add(new Captacion_funcional("Control de Activación de Faltas innecesarias",R.id.opcion_inca61,
                R.id.group_inca61,
                R.id.inca611,
                R.id.inca612,
                R.id.inca613,
                R.id.inca614,0));

        LISTA_INCA4.add(new Captacion_funcional("Liderazgo",R.id.opcion_inca124,
                R.id.group_inca124,
                R.id.inca1124,
                R.id.inca1224,
                R.id.inca1324,
                R.id.inca1424,0));

        LISTA_INCA4.add(new Captacion_funcional("Comunicación",R.id.opcion_inca224,
                R.id.group_inca224,
                R.id.inca2124,
                R.id.inca2224,
                R.id.inca2324,
                R.id.inca2424,0));

        LISTA_INCA5.add(new Captacion_funcional("Autoconfianza",R.id.opcion_inca1245,
                R.id.group_inca1245,
                R.id.inca11245,
                R.id.inca12245,
                R.id.inca13245,
                R.id.inca14245,0));

    }

}
