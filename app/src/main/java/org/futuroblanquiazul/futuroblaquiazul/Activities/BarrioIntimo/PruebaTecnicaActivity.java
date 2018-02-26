package org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.futuroblanquiazul.futuroblaquiazul.Entity.PruebaTecnica;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Captacion_Vista;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Captacion_funcional;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Diagnostico;

import static org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Diagnostico.LISTA_PRUEBA_TECNICA_CABECEO;
import static org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Diagnostico.LISTA_PRUEBA_TECNICA_CONDUCCION;
import static org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Diagnostico.LISTA_PRUEBA_TECNICA_PASE_CONTROL;
import static org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Diagnostico.LISTA_PRUEBA_TECNICA_REMATE;

public class PruebaTecnicaActivity extends AppCompatActivity {
   ScrollView scroll_prueba_tecnica;
   TextView pase_ras,pase_alto,c_ras,c_alto;
   TextView total_pase,total_control,total_remate,total_conduccion,total_cabeceo;
   EditText TT;
   TextView total_general_tecnico;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_tecnica);

        scroll_prueba_tecnica=findViewById(R.id.scroll_prueba_tecnica);
        pase_ras=findViewById(R.id.pase_ras);
        pase_alto=findViewById(R.id.pase_alto);
        c_ras=findViewById(R.id.C_ras);
        c_alto=findViewById(R.id.C_alto);
        total_pase=findViewById(R.id.total_pase);
        total_control=findViewById(R.id.total_control);
        total_remate=findViewById(R.id.total_remate);
        total_conduccion=findViewById(R.id.total_conduccion);
        total_cabeceo=findViewById(R.id.total_cabeceo);

        total_general_tecnico=findViewById(R.id.total_general_tecnico);


        Creacion_Animaciones();
        Seteo_RadioGroups();



        mostrar_resultados();
    }


    private void Creacion_Animaciones() {

        for(int i = 0; i< Recursos_Diagnostico.LISTA_VISTAS4.size(); i++){
            Debug(" Animacion - Nº :"+i);
            LinearLayout linear=findViewById(Recursos_Diagnostico.LISTA_VISTAS4.get(i).getContenedor());
            LayoutInflater inflater = LayoutInflater.from(this);

            final View view_actual = inflater.inflate(Recursos_Diagnostico.LISTA_VISTAS4.get(i).getVista(), linear, true);
            LinearLayout line=view_actual.findViewById(Recursos_Diagnostico.LISTA_VISTAS4.get(i).getArea_Accion());
            Recursos_Diagnostico.LISTA_VISTAS4.get(i).setView(line);


            LinearLayout Accion_Panel=findViewById(Recursos_Diagnostico.LISTA_VISTAS4.get(i).getPanel_Accion());
            Generar_Animacion(Recursos_Diagnostico.LISTA_VISTAS4.get(i),line,Accion_Panel);


            if(i==2){
                TT=view_actual.findViewById(R.id.textoTT);

                TT.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(String.valueOf(s).length()!=0){
                            Calcular_TT();

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }
        }


    }
    private void Generar_Animacion(final Captacion_Vista captacion_vista, final LinearLayout view_actual, LinearLayout Accion_Panel ) {

        Accion_Panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(captacion_vista.getAccion()==0){
                    scroll_prueba_tecnica.smoothScrollTo(0,0);
                    view_actual.setVisibility(View.VISIBLE);

                    captacion_vista.setAccion(1);
                    Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abajo);
                    view_actual.startAnimation(animation);

                    cerrar_ventanas(view_actual);
                }else if(captacion_vista.getAccion()==1){
                    scroll_prueba_tecnica.smoothScrollTo(0,0);
                    captacion_vista.setAccion(0);
                    Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.arriba);
                    view_actual.startAnimation(animation);
                    view_actual.setVisibility(View.GONE);
                }

            }
        });
    }
    private void cerrar_ventanas(LinearLayout view_actual) {
        for(int i = 0; i< Recursos_Diagnostico.LISTA_VISTAS4.size(); i++){
            if(Recursos_Diagnostico.LISTA_VISTAS4.get(i).getView()==view_actual){

            }else{
                Recursos_Diagnostico.LISTA_VISTAS4.get(i).getView().setVisibility(View.GONE);
            }
        }
    }
    private void Seteo_RadioGroups() {

        for(int i = 0; i< LISTA_PRUEBA_TECNICA_PASE_CONTROL.size(); i++){

            Generar_Funcion(i, LISTA_PRUEBA_TECNICA_PASE_CONTROL.get(i));
        }
        for(int i = 0; i< Recursos_Diagnostico.LISTA_PRUEBA_TECNICA_REMATE.size(); i++){

            Generar_Funcion(i, Recursos_Diagnostico.LISTA_PRUEBA_TECNICA_REMATE.get(i));

        }
        for(int i = 0; i< Recursos_Diagnostico.LISTA_PRUEBA_TECNICA_CONDUCCION.size(); i++){

            Generar_Funcion(i, Recursos_Diagnostico.LISTA_PRUEBA_TECNICA_CONDUCCION.get(i));
        }
        for(int i = 0; i< Recursos_Diagnostico.LISTA_PRUEBA_TECNICA_CABECEO.size(); i++){

            Generar_Funcion(i, Recursos_Diagnostico.LISTA_PRUEBA_TECNICA_CABECEO.get(i));
        }


    }
    private void Generar_Funcion(int v, final Captacion_funcional captacion_funcional) {
        // int n=v+1;
        // TextView textView=findViewById(captacion_funcional.getTextView());
        //textView.setText(n+".- "+captacion_funcional.getOpcion());

        RadioGroup grupo=findViewById(captacion_funcional.getGroupRadio());
        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i== captacion_funcional.getRadio1()){
                    captacion_funcional.setResultado(0);
                    //Refrescar_Totales();
                    mostrar_resultados();
                }else if(i == captacion_funcional.getRadio2()){
                    captacion_funcional.setResultado(1);
                    //Refrescar_Totales();
                    mostrar_resultados();
                }
                else if(i == captacion_funcional.getRadio3()){
                    captacion_funcional.setResultado(2);
                   // Refrescar_Totales();
                    mostrar_resultados();
                }else if(i == captacion_funcional.getRadio4()){
                    captacion_funcional.setResultado(3);
                  //  Refrescar_Totales();
                    mostrar_resultados();
                }
            }
        });
    }
    private void mostrar_resultados() {

        int to=(LISTA_PRUEBA_TECNICA_PASE_CONTROL.get(1).getResultado()+ LISTA_PRUEBA_TECNICA_PASE_CONTROL.get(3).getResultado())*20;

        PruebaTecnica.PRUEBA_TECNICA.setP_Ras(to);
        pase_ras.setText(String.valueOf(to));


        int PA=(LISTA_PRUEBA_TECNICA_PASE_CONTROL.get(5).getResultado()+LISTA_PRUEBA_TECNICA_PASE_CONTROL.get(7).getResultado())*20;
        PruebaTecnica.PRUEBA_TECNICA.setP_Alto(PA);
        pase_alto.setText(String.valueOf(PA));


        int CRAS=(LISTA_PRUEBA_TECNICA_PASE_CONTROL.get(0).getResultado()+LISTA_PRUEBA_TECNICA_PASE_CONTROL.get(2).getResultado()+LISTA_PRUEBA_TECNICA_REMATE.get(0).getResultado()+LISTA_PRUEBA_TECNICA_REMATE.get(4).getResultado())*10;
        PruebaTecnica.PRUEBA_TECNICA.setControl_Ras(CRAS);
        c_ras.setText(String.valueOf(CRAS));

        c_ras.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(String.valueOf(s).length()!=0){
                    Calcular_Control();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        int CALTO=(LISTA_PRUEBA_TECNICA_PASE_CONTROL.get(4).getResultado()+LISTA_PRUEBA_TECNICA_PASE_CONTROL.get(6).getResultado()+LISTA_PRUEBA_TECNICA_REMATE.get(2).getResultado()+LISTA_PRUEBA_TECNICA_REMATE.get(6).getResultado())*10;
        PruebaTecnica.PRUEBA_TECNICA.setContorl_Alto(CALTO);

        c_alto.setText(String.valueOf(CALTO));


        pase_ras.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(String.valueOf(s).length()!=0){
                    Calcular_Pase();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pase_alto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(String.valueOf(s).length()!=0){
                    Calcular_Pase();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        c_alto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(String.valueOf(s).length()!=0){
                    Calcular_Control();


                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        int REMATE=(LISTA_PRUEBA_TECNICA_REMATE.get(1).getResultado()+LISTA_PRUEBA_TECNICA_REMATE.get(3).getResultado()+LISTA_PRUEBA_TECNICA_REMATE.get(5).getResultado()+LISTA_PRUEBA_TECNICA_REMATE.get(7).getResultado())*10;

        PruebaTecnica.PRUEBA_TECNICA.setRemate(REMATE);
        total_remate.setText(String.valueOf(REMATE));

      Debug("ANTES");



        int totalca=(LISTA_PRUEBA_TECNICA_CABECEO.get(0).getResultado()+LISTA_PRUEBA_TECNICA_CABECEO.get(1).getResultado()+LISTA_PRUEBA_TECNICA_CABECEO.get(2).getResultado()+LISTA_PRUEBA_TECNICA_CABECEO.get(3).getResultado())*10;
        total_cabeceo.setText(String.valueOf(totalca));

        Calcular_TT();

        CalcularTotal();
    }
    private void Calcular_TT() {

        int c1=LISTA_PRUEBA_TECNICA_CONDUCCION.get(0).getResultado();
        int c2=LISTA_PRUEBA_TECNICA_CONDUCCION.get(1).getResultado();
        int c3=LISTA_PRUEBA_TECNICA_CONDUCCION.get(2).getResultado();
        int c4=LISTA_PRUEBA_TECNICA_CONDUCCION.get(3).getResultado();
        int c5=LISTA_PRUEBA_TECNICA_CONDUCCION.get(4).getResultado();

         int par=(c1+c2+c3+c4+c5)*7;
         int d=Integer.parseInt(TT.getText().toString());
         int ttt=20-d;
         int to=par+ttt;
         total_conduccion.setText(String.valueOf(to));

    }
    private void Calcular_Pase() {

        int p1=Integer.parseInt(pase_ras.getText().toString());
        int p2=Integer.parseInt(pase_alto.getText().toString());

        int total=(p1+p2)/2;

        PruebaTecnica.PRUEBA_TECNICA.setPase(total);
        total_pase.setText(String.valueOf(total));
    }
    private void Calcular_Control() {

        int p1=Integer.parseInt(c_ras.getText().toString());
        int p2=Integer.parseInt(c_alto.getText().toString());

        int total=(p1+p2)/2;
        PruebaTecnica.PRUEBA_TECNICA.setControl(total);
        total_control.setText(String.valueOf(total));
    }
    private void Debug(String sm){
        System.out.println(sm);
    }
    private void CalcularTotal(){

        double r1=Double.parseDouble(pase_ras.getText().toString());
        double r2=Double.parseDouble(pase_alto.getText().toString());
        double r3=Double.parseDouble(c_ras.getText().toString());
        double r4=Double.parseDouble(c_alto.getText().toString());
        double r5=Double.parseDouble(total_pase.getText().toString());
        double r6=Double.parseDouble(total_control.getText().toString());
        double r7=Double.parseDouble(total_remate.getText().toString());
        double r8=Double.parseDouble(total_conduccion.getText().toString());
        double r9=Double.parseDouble(total_cabeceo.getText().toString());

        Debug(" R1 :"+r1);
        Debug(" R2 :"+r2);
        Debug(" R3 :"+r3);
        Debug(" R4 :"+r4);
        Debug(" R5 :"+r5);
        Debug(" R6 :"+r6);
        Debug(" R7 :"+r7);
        Debug(" R8 :"+r8);
        Debug(" R9 :"+r9);

        double ddd=(r1+r2+r3+r4+r5+r6+r7+r8+r9)/9;
        Debug(" PREV: "+ddd);
        double total=Math.round(ddd);
        Debug(" TOTAL: "+total);
        Integer d=(int) total;
        PruebaTecnica.PRUEBA_TECNICA.setTotal_general(d);
        total_general_tecnico.setText(d+" Ptos.");

    }
}
