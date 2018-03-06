package org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Captacion_Vista;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Captacion_funcional;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Diagnostico;

import static org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Diagnostico.LISTA_PRUEBA_TECNICA_PASE_CONTROL;

public class PruebaPsicologicaActivity extends AppCompatActivity {

    ScrollView scroll_prueba_psico;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_psicologico);
        scroll_prueba_psico=findViewById(R.id.scroll_prueba_psico);


        Creacion_Animaciones();

        Seteo_RadioGroups();
    }


    private void Creacion_Animaciones() {

        for(int i = 0; i< Recursos_Diagnostico.LISTA_VISTAS5.size(); i++){

            LinearLayout linear=findViewById(Recursos_Diagnostico.LISTA_VISTAS5.get(i).getContenedor());
            LayoutInflater inflater = LayoutInflater.from(this);

            final View view_actual = inflater.inflate(Recursos_Diagnostico.LISTA_VISTAS5.get(i).getVista(), linear, true);
            LinearLayout line=view_actual.findViewById(Recursos_Diagnostico.LISTA_VISTAS5.get(i).getArea_Accion());
            Recursos_Diagnostico.LISTA_VISTAS5.get(i).setView(line);

            LinearLayout Accion_Panel=findViewById(Recursos_Diagnostico.LISTA_VISTAS5.get(i).getPanel_Accion());
            Generar_Animacion(Recursos_Diagnostico.LISTA_VISTAS5.get(i),line,Accion_Panel);
        }

    }
    private void Generar_Animacion(final Captacion_Vista captacion_vista, final LinearLayout view_actual, LinearLayout Accion_Panel ) {

        Accion_Panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(captacion_vista.getAccion()==0){
                    scroll_prueba_psico.smoothScrollTo(0,0);
                    view_actual.setVisibility(View.VISIBLE);

                    captacion_vista.setAccion(1);
                    Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abajo);
                    view_actual.startAnimation(animation);

                    cerrar_ventanas(view_actual);
                }else if(captacion_vista.getAccion()==1){
                    scroll_prueba_psico.smoothScrollTo(0,0);
                    captacion_vista.setAccion(0);
                    Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.arriba);
                    view_actual.startAnimation(animation);
                    view_actual.setVisibility(View.GONE);
                }

            }
        });
    }
    private void cerrar_ventanas(LinearLayout view_actual) {
        for(int i = 0; i< Recursos_Diagnostico.LISTA_VISTAS5.size(); i++){
            if(Recursos_Diagnostico.LISTA_VISTAS5.get(i).getView()==view_actual){

            }else{
                Recursos_Diagnostico.LISTA_VISTAS5.get(i).getView().setVisibility(View.GONE);
            }
        }
    }


    private void Seteo_RadioGroups() {

        for(int i = 0; i< Recursos_Diagnostico.LISTA_INCA1.size(); i++){

            Generar_Funcion(i, Recursos_Diagnostico.LISTA_INCA1.get(i));
        }
        for(int i = 0; i< Recursos_Diagnostico.LISTA_INCA2.size(); i++){

            Generar_Funcion(i, Recursos_Diagnostico.LISTA_INCA2.get(i));

        }
        for(int i = 0; i< Recursos_Diagnostico.LISTA_INCA3.size(); i++){

            Generar_Funcion(i, Recursos_Diagnostico.LISTA_INCA3.get(i));
        }
        for(int i = 0; i< Recursos_Diagnostico.LISTA_INCA4.size(); i++){

            Generar_Funcion(i, Recursos_Diagnostico.LISTA_INCA4.get(i));
        }

        for(int i = 0; i< Recursos_Diagnostico.LISTA_INCA5.size(); i++){

            Generar_Funcion(i, Recursos_Diagnostico.LISTA_INCA5.get(i));
        }


    }


    private void Generar_Funcion(int v, final Captacion_funcional captacion_funcional) {
         int n=v+1;
         TextView textView=findViewById(captacion_funcional.getTextView());
         textView.setText(n+".- "+captacion_funcional.getOpcion());

        RadioGroup grupo=findViewById(captacion_funcional.getGroupRadio());
        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i== captacion_funcional.getRadio1()){
                    captacion_funcional.setResultado(0);
                    //Refrescar_Totales();
                    //mostrar_resultados();
                }else if(i == captacion_funcional.getRadio2()){
                    captacion_funcional.setResultado(1);
                    //Refrescar_Totales();
                    //mostrar_resultados();
                }
                else if(i == captacion_funcional.getRadio3()){
                    captacion_funcional.setResultado(2);
                    // Refrescar_Totales();
                    //mostrar_resultados();
                }else if(i == captacion_funcional.getRadio4()){
                    captacion_funcional.setResultado(3);
                    //  Refrescar_Totales();
                    //mostrar_resultados();
                }
            }
        });
    }
}
