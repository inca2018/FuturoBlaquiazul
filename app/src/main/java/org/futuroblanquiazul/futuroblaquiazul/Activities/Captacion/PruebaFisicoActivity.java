package org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Captacion_Vista;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Diagnostico;

public class PruebaFisicoActivity extends AppCompatActivity {

    ScrollView scroll_prueba_fisica;
    CardView guardar_prueba_fisica;

    EditText e1,e2,e3,e4,e5,e6,e7,e8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_fisico);
        scroll_prueba_fisica=findViewById(R.id.scroll_prueba_fisica);
        guardar_prueba_fisica=findViewById(R.id.guardar_prueba_fisica);
        Animar();
        e1=findViewById(R.id.e_Peso);
        e2=findViewById(R.id.e_talla);
        e3=findViewById(R.id.e_RJ);
        e4=findViewById(R.id.e_CMJ);
        e5=findViewById(R.id.e_ABK);
        e6=findViewById(R.id.e_FMS);
        e7=findViewById(R.id.e_vel_s);
        e8=findViewById(R.id.e_yo);


         e1.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
                 Toast.makeText(PruebaFisicoActivity.this, s.toString(), Toast.LENGTH_SHORT).show();
             }

             @Override
             public void afterTextChanged(Editable s) {

             }
         });


        //Boton  Guardar
        guardar_prueba_fisica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void Animar() {
        for(int i = 0; i< Recursos_Diagnostico.LISTA_VISTAS3.size(); i++){

            LinearLayout linear=findViewById(Recursos_Diagnostico.LISTA_VISTAS3.get(i).getContenedor());
            LayoutInflater inflater = LayoutInflater.from(this);

            final View view_actual = inflater.inflate(Recursos_Diagnostico.LISTA_VISTAS3.get(i).getVista(), linear, true);
            LinearLayout line=view_actual.findViewById(Recursos_Diagnostico.LISTA_VISTAS3.get(i).getArea_Accion());
            Recursos_Diagnostico.LISTA_VISTAS3.get(i).setView(line);
            LinearLayout Accion_Panel=findViewById(Recursos_Diagnostico.LISTA_VISTAS3.get(i).getPanel_Accion());
            Generar_Animacion(Recursos_Diagnostico.LISTA_VISTAS3.get(i),line,Accion_Panel);
        }

    }
    private void Generar_Animacion(final Captacion_Vista captacion_vista, final LinearLayout view_actual, LinearLayout Accion_Panel ) {

        Accion_Panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(captacion_vista.getAccion()==0){
                    scroll_prueba_fisica.smoothScrollTo(0,0);
                    view_actual.setVisibility(View.VISIBLE);

                    captacion_vista.setAccion(1);
                    Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abajo);
                    view_actual.startAnimation(animation);

                    cerrar_ventanas(view_actual);
                }else if(captacion_vista.getAccion()==1){
                    scroll_prueba_fisica.smoothScrollTo(0,0);
                    captacion_vista.setAccion(0);
                    Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.arriba);
                    view_actual.startAnimation(animation);
                    view_actual.setVisibility(View.GONE);
                }

            }
        });
    }
    private void cerrar_ventanas(LinearLayout view_actual) {
        for(int i = 0; i< Recursos_Diagnostico.LISTA_VISTAS3.size(); i++){
            if(Recursos_Diagnostico.LISTA_VISTAS3.get(i).getView()==view_actual){

            }else{
                Recursos_Diagnostico.LISTA_VISTAS3.get(i).getView().setVisibility(View.GONE);
            }
        }
    }


}
