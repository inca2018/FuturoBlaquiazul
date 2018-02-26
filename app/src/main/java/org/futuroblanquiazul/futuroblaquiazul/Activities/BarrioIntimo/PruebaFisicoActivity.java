package org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.futuroblanquiazul.futuroblaquiazul.Entity.PruebaFisica;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Captacion_Vista;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Diagnostico;

import java.text.DecimalFormat;

public class PruebaFisicoActivity extends AppCompatActivity {
    ScrollView scroll_prueba_fisica;
    Button guardar_prueba_fisica;
    EditText e1,e2,e3,e4,e5,e6,e7,e8;
    TextView info_velocidad,info_potencia,info_resistencia,prom_velocidad,prom_potencia,prom_resistencia;
    TextView total_fisico_prueba;
    DecimalFormat df,df2;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_fisico);
        context=this;
        Animar();
         df=new DecimalFormat("0.0");
         df2=new DecimalFormat("0.00");
        //String formate = df.format(value);
        total_fisico_prueba=findViewById(R.id.total_fisico_prueba);
        scroll_prueba_fisica=findViewById(R.id.scroll_prueba_fisica);
        guardar_prueba_fisica=findViewById(R.id.guardar_prueba_fisica);
        info_velocidad=findViewById(R.id.info_velocidad);
        info_potencia=findViewById(R.id.info_potencia);
        info_resistencia=findViewById(R.id.info_resistencia);
        prom_velocidad=findViewById(R.id.prom_velocidad);
        prom_potencia=findViewById(R.id.prom_potencia);
        prom_resistencia=findViewById(R.id.prom_resistencia);
        e1=findViewById(R.id.e_Peso);
        e2=findViewById(R.id.e_talla);
        e3=findViewById(R.id.e_RJ);
        e4=findViewById(R.id.e_CMJ);
        e5=findViewById(R.id.e_ABK);
        e6=findViewById(R.id.e_FMS);
        e7=findViewById(R.id.e_vel_s);
        e8=findViewById(R.id.e_yo);

        Calculos();

        //Boton  Guardar
        guardar_prueba_fisica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Usuario.SESION_ACTUAL.getPersona_barrio().getId()!=0){

                    Recuperar_entradas();
                    Registrar_Prueba_fisica(context,Usuario.SESION_ACTUAL.getId(),Usuario.SESION_ACTUAL.getId_barrio_intimo(),Usuario.SESION_ACTUAL.getPersona_barrio().getId());

                }else{
                    Toast.makeText(PruebaFisicoActivity.this, "Problemas para recuperar Datos de Postulante", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Recuperar_entradas() {

         double peso=Double.parseDouble(e1.getText().toString());
         double talla=Double.parseDouble(e2.getText().toString());
         double RJ=Double.parseDouble(e3.getText().toString());
         double CMJ=Double.parseDouble(e4.getText().toString());
         double ABK=Double.parseDouble(e5.getText().toString());
         double FMS=Double.parseDouble(e6.getText().toString());
         double VELOCIDAD=Double.parseDouble(e7.getText().toString());
         double YOYO=Double.parseDouble(e8.getText().toString());

         PruebaFisica.PRUEBA_FISICA.setE_peso(peso);
         PruebaFisica.PRUEBA_FISICA.setE_talla(talla);
         PruebaFisica.PRUEBA_FISICA.setE_RJ(RJ);
         PruebaFisica.PRUEBA_FISICA.setE_CMJ(CMJ);
         PruebaFisica.PRUEBA_FISICA.setE_ABK(ABK);
         PruebaFisica.PRUEBA_FISICA.setE_FMS(FMS);
         PruebaFisica.PRUEBA_FISICA.setE_Velocidad(VELOCIDAD);
         PruebaFisica.PRUEBA_FISICA.setE_YOYO(YOYO);
    }

    private void Registrar_Prueba_fisica(Context context, int id, int id_barrio_intimo, int id1) {

                   Debug(PruebaFisica.PRUEBA_FISICA.toString());

    }

    private void Calculos() {


        e7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()!=0){
                double dato=Double.parseDouble(s.toString());
                if(dato==0){
                    PruebaFisica.PRUEBA_FISICA.setInformativoVelocidad(0);
                }else{
                    double to;
                    double pre1=(dato*60);
                    double pre2=27*1000;
                    to=pre2/pre1;
                    PruebaFisica.PRUEBA_FISICA.setInformativoVelocidad(to);

                    String temp=df.format(PruebaFisica.PRUEBA_FISICA.getInformativoVelocidad());
                    info_velocidad.setText(temp+" Kms/Hr.");

                    Verificar_PromVelocidad();
                     }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()!=0){



                double to=Double.parseDouble(s.toString());

                PruebaFisica.PRUEBA_FISICA.setE_peso(to);

                Contruir_Potencia();
                Verificar_Total();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        e4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().length()!=0){
                    double to=Double.parseDouble(s.toString());

                if(to==0){
                    PruebaFisica.PRUEBA_FISICA.setInformativoPotencia(0);
                    PruebaFisica.PRUEBA_FISICA.setE_CMJ(0);
                }else{
                    PruebaFisica.PRUEBA_FISICA.setE_CMJ(to);
                    Contruir_Potencia();
                }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        e8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().length()!=0){
                 double to=Double.parseDouble(String.valueOf(s));
                 double or=4.025*to;
                 PruebaFisica.PRUEBA_FISICA.setInformativoResistencia(or);

                 String pr=df.format(PruebaFisica.PRUEBA_FISICA.getInformativoResistencia());
                 PruebaFisica.PRUEBA_FISICA.setInformativoResistencia(Double.parseDouble(pr));
                 info_resistencia.setText(pr+" Vo2Max");

                 if(PruebaFisica.PRUEBA_FISICA.getInformativoResistencia()!=0){
                     double toto=PruebaFisica.PRUEBA_FISICA.getInformativoResistencia()-12.6;
                     PruebaFisica.PRUEBA_FISICA.setPromResistencia(toto);

                     String res=df.format(PruebaFisica.PRUEBA_FISICA.getPromResistencia());
                     prom_resistencia.setText(res+" Ptos.");
                 }
                 }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        e3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()!=0){

                double to=Double.parseDouble(s.toString());
                PruebaFisica.PRUEBA_FISICA.setE_RJ(to);
                Verificar_PromVelocidad();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        e6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()!=0){

                double to=Double.parseDouble(s.toString());
                PruebaFisica.PRUEBA_FISICA.setE_FMS(to);

                Verificar_PromVelocidad();

            }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        prom_velocidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()!=0){
                    Verificar_Total();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        prom_potencia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()!=0){
                    Verificar_Total();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        prom_resistencia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()!=0){
                    Verificar_Total();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }
    private void Verificar_Total() {

         if(PruebaFisica.PRUEBA_FISICA.getPromVelocidad()!=0 || PruebaFisica.PRUEBA_FISICA.getPromPotencia()!=0 || PruebaFisica.PRUEBA_FISICA.getPromResistencia()!=0){

             double prom_Velo=PruebaFisica.PRUEBA_FISICA.getPromVelocidad();
             double prom_pote=PruebaFisica.PRUEBA_FISICA.getPromPotencia();
             double prom_Resi=PruebaFisica.PRUEBA_FISICA.getPromResistencia();

             double total=(prom_Velo+prom_pote+prom_Resi)/3;
             PruebaFisica.PRUEBA_FISICA.setTotal_general(total);
             String f=df.format(total);
             total_fisico_prueba.setText(f+" Ptos.");

         }


    }
    private void Verificar_PromVelocidad() {
        if(PruebaFisica.PRUEBA_FISICA.getInformativoVelocidad()==0){
            prom_velocidad.setText(String.valueOf(0.00)+" Ptos.");
        }else{
            if(PruebaFisica.PRUEBA_FISICA.getE_RJ()!=0 || PruebaFisica.PRUEBA_FISICA.getE_FMS()!=0){
                 double prom=(PruebaFisica.PRUEBA_FISICA.getE_RJ()+PruebaFisica.PRUEBA_FISICA.getE_FMS())/2;
                 double total=PruebaFisica.PRUEBA_FISICA.getInformativoVelocidad()+(prom-20);
                 PruebaFisica.PRUEBA_FISICA.setPromVelocidad(total);

                 double x=PruebaFisica.PRUEBA_FISICA.getPromVelocidad();
                 Integer ff=(int)x;
                 prom_velocidad.setText(String.valueOf(ff)+" Ptos.");
            }
        }
    }
    private void Contruir_Potencia() {

        if(PruebaFisica.PRUEBA_FISICA.getE_peso()!=0 || PruebaFisica.PRUEBA_FISICA.getE_CMJ()!=0){
            double pre1=51.9*PruebaFisica.PRUEBA_FISICA.getE_CMJ();
            double pre2=48.9*PruebaFisica.PRUEBA_FISICA.getE_peso();
            double total=(pre1+pre2)-2007;
            PruebaFisica.PRUEBA_FISICA.setInformativoPotencia(total);


            double pot=PruebaFisica.PRUEBA_FISICA.getInformativoPotencia();
            Integer i =(int) pot;
            info_potencia.setText(String.valueOf(i)+" W");


            if(PruebaFisica.PRUEBA_FISICA.getInformativoPotencia()!=0){
                double to=PruebaFisica.PRUEBA_FISICA.getInformativoPotencia()/52.5;
                PruebaFisica.PRUEBA_FISICA.setPromPotencia(to);

                double t=Math.round(PruebaFisica.PRUEBA_FISICA.getPromPotencia());
                Integer u=(int) t;
                prom_potencia.setText(String.valueOf(u)+" Ptos.");
            }


        }

        /*
        else if(PruebaFisica.PRUEBA_FISICA.getE_peso()==0 && PruebaFisica.PRUEBA_FISICA.getE_CMJ()!=0){
            double pre1=51.9*PruebaFisica.PRUEBA_FISICA.getE_CMJ();
            double pre2=48.9*0;
            double total=(pre1+pre2)-2007;
            PruebaFisica.PRUEBA_FISICA.setInformativoPotencia(total);

            double pot=PruebaFisica.PRUEBA_FISICA.getInformativoPotencia();
            Integer i =(int) pot;
            info_potencia.setText(String.valueOf(i));



            if(PruebaFisica.PRUEBA_FISICA.getInformativoPotencia()!=0){
                double to=PruebaFisica.PRUEBA_FISICA.getInformativoPotencia()/52.5;
                PruebaFisica.PRUEBA_FISICA.setPromPotencia(to);

                double t=PruebaFisica.PRUEBA_FISICA.getPromPotencia();
                Integer u=(int) t;
                prom_potencia.setText(String.valueOf(u));
            }
        }*/

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

    public void Debug(String sms){
        System.out.println(sms);
    }
}
