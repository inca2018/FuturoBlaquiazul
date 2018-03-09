package org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoPersonaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.ListaPersonasGrupoPruebasActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia_Fase_Prueba.GestionPersonaFasePruebaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PruebaFisica;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActualizarPruebaDiagnostico;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActualizarPruebaFisico;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Actualizar_barrio2;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Actualizar_barrio_fisica;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarFasePrueba;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarPruebaFisica;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Captacion_Vista;
import org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Diagnostico;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class PruebaFisicoActivity extends AppCompatActivity {
    ScrollView scroll_prueba_fisica;
    Button guardar_prueba_fisica;
    EditText e1,e2,e3,e4,e5,e6,e7,e8;
    TextView info_velocidad,info_potencia,info_resistencia,prom_velocidad,prom_potencia,prom_resistencia;
    TextView total_fisico_prueba;
    DecimalFormat df,df2;
    Context context;
    ProgressDialog progressDialog;

    TextView persona_nombre ,ubigeo;
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
        persona_nombre=findViewById(R.id.prueba_fisica_nombre);
        ubigeo=findViewById(R.id.prueba_fisica_ubigeo);

        e1=findViewById(R.id.e_Peso);
        e2=findViewById(R.id.e_talla);
        e3=findViewById(R.id.e_RJ);
        e4=findViewById(R.id.e_CMJ);
        e5=findViewById(R.id.e_ABK);
        e6=findViewById(R.id.e_FMS);
        e7=findViewById(R.id.e_vel_s);
        e8=findViewById(R.id.e_yo);

        if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
            persona_nombre.setText(Usuario.SESION_ACTUAL.getPersona_fase_pruebas().getNombre_Persona()+" "+Usuario.SESION_ACTUAL.getPersona_fase_pruebas().getApellidos_Persona());
            ubigeo.setText(" Plantel");
        }else{

            if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){
                persona_nombre.setText(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas().getNombre_Persona()+" "+Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas().getApellidos_Persona());
                ubigeo.setText(" Plantel");
            }else{
                if(Usuario.SESION_ACTUAL.getPersona_barrio()!=null){
                    persona_nombre.setText(Usuario.SESION_ACTUAL.getPersona_barrio().getNombre_Persona()+" "+Usuario.SESION_ACTUAL.getPersona_barrio().getApellidos_Persona());

                    if(GestionUbigeo.CAPTACION_UBIGEO_BARRIO!=null){
                        ubigeo.setText(GestionUbigeo.CAPTACION_UBIGEO_BARRIO.getUbigeo_descripcion());
                    }else{
                        ubigeo.setText("No Disponible");
                    }
                }else{
                    ubigeo.setText("No Disponible");
                }
                persona_nombre.setText("No Disponible");
                ubigeo.setText("No Disponible");
            }
        }



        Calculos();

        //Boton  Guardar
        guardar_prueba_fisica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
                    Recuperar_entradas();
                }else{
                    if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){
                        Recuperar_entradas();
                    }else{
                        if(Usuario.SESION_ACTUAL.getPersona_barrio().getId()!=0){
                            Recuperar_entradas();
                        }else{
                            Toast.makeText(PruebaFisicoActivity.this, "Problemas para recuperar Datos de Postulante", Toast.LENGTH_SHORT).show();
                        }
                    }


                }



            }
        });
    }

    private void Recuperar_entradas() {

         String peso= e1.getText().toString();
         String talla=e2.getText().toString();
         String RJ=e3.getText().toString();
         String CMJ=e4.getText().toString();
         String ABK=e5.getText().toString();
         String FMS=e6.getText().toString();
         String VELOCIDAD=e7.getText().toString();
         String YOYO=e8.getText().toString();

         if(peso.length()!=0 && talla.length()!=0 &&RJ.length()!=0 && CMJ.length()!=0 && ABK.length()!=0 && FMS.length()!=0 && VELOCIDAD.length()!=0 && YOYO.length()!=0){
             PruebaFisica.PRUEBA_FISICA.setE_peso(Double.parseDouble(peso));
             PruebaFisica.PRUEBA_FISICA.setE_talla(Double.parseDouble(talla));
             PruebaFisica.PRUEBA_FISICA.setE_RJ(Double.parseDouble(RJ));
             PruebaFisica.PRUEBA_FISICA.setE_CMJ(Double.parseDouble(CMJ));
             PruebaFisica.PRUEBA_FISICA.setE_ABK(Double.parseDouble(ABK));
             PruebaFisica.PRUEBA_FISICA.setE_FMS(Double.parseDouble(FMS));
             PruebaFisica.PRUEBA_FISICA.setE_Velocidad(Double.parseDouble(VELOCIDAD));
             PruebaFisica.PRUEBA_FISICA.setE_YOYO(Double.parseDouble(YOYO));

             if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){

                 Registrar_Prueba_fisica(context,Usuario.SESION_ACTUAL.getId(),0,Usuario.SESION_ACTUAL.getPersona_fase_pruebas().getId());

             }else{

                 if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){

                     Registrar_Prueba_fisica(context,Usuario.SESION_ACTUAL.getId(),0,Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas().getId());
                 }else{
                     Actualizar_Total(PruebaFisica.PRUEBA_FISICA.getTotal_general(),Usuario.SESION_ACTUAL.getId_barrio_intimo(),Usuario.SESION_ACTUAL.getPersona_barrio().getId(),context);

                 }
             }


         }else{
             Toast.makeText(context, "Complete información de las entradas de la Evaluación", Toast.LENGTH_SHORT).show();
         }


    }

    private void Registrar_Prueba_fisica(final Context context, int user, final int id_barrio_intimo, int id_per) {

                   if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
                       progressDialog = new ProgressDialog(context);
                       progressDialog.setTitle("Prueba Fisica:");
                       progressDialog.setMessage("Guardando...");
                       progressDialog.show();
                   }
                   if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){
                       progressDialog = new ProgressDialog(context);
                       progressDialog.setTitle("Prueba Fisica:");
                       progressDialog.setMessage("Guardando...");
                       progressDialog.show();
                   }
                   String id_user=String.valueOf(user);
                   String id_barrio=String.valueOf(id_barrio_intimo);
                   String id_persona=String.valueOf(id_per);
                   String peso=String.valueOf(PruebaFisica.PRUEBA_FISICA.getE_peso());
                   String talla=String.valueOf(PruebaFisica.PRUEBA_FISICA.getE_talla());
                   String rj=String.valueOf(PruebaFisica.PRUEBA_FISICA.getE_RJ());
                   String cmj=String.valueOf(PruebaFisica.PRUEBA_FISICA.getE_CMJ());
                   String abk=String.valueOf(PruebaFisica.PRUEBA_FISICA.getE_ABK());
                   String fms=String.valueOf(PruebaFisica.PRUEBA_FISICA.getE_FMS());
                   String velocidad=String.valueOf(PruebaFisica.PRUEBA_FISICA.getE_Velocidad());
                   String yoyo=String.valueOf(PruebaFisica.PRUEBA_FISICA.getE_YOYO());
                   String info_velo=String.valueOf(PruebaFisica.PRUEBA_FISICA.getInformativoVelocidad());
                   String info_pot=String.valueOf(PruebaFisica.PRUEBA_FISICA.getInformativoPotencia());
                   String info_resi=String.valueOf(PruebaFisica.PRUEBA_FISICA.getInformativoResistencia());
                   String prom_velo=String.valueOf(PruebaFisica.PRUEBA_FISICA.getPromVelocidad());
                   String prom_pote=String.valueOf(PruebaFisica.PRUEBA_FISICA.getPromPotencia());
                   String prom_resi=String.valueOf(PruebaFisica.PRUEBA_FISICA.getPromResistencia());
                   String total=String.valueOf(PruebaFisica.PRUEBA_FISICA.getTotal_general());

                   String estado="1";


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
                            progressDialog.dismiss();
                            int id_fisico=jsonResponse.getInt("id_fisico");

                            Registrar_Fase_Prueba(Usuario.SESION_ACTUAL.getId(),Usuario.SESION_ACTUAL.getPersona_fase_pruebas().getId(),Usuario.SESION_ACTUAL.getTipoPruebas().getId(),id_fisico,context);

                            Intent intent=new Intent(PruebaFisicoActivity.this,GestionPersonaFasePruebaActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            PruebaFisicoActivity.this.startActivity(intent);
                            Toast.makeText(context, "Registro de Prueba Tecnica Exitosa", Toast.LENGTH_SHORT).show();

                        }else{


                            if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){
                                progressDialog.dismiss();
                                int id_fisico=jsonResponse.getInt("id_fisico");
                                Actualizar_fisico(id_fisico,Usuario.getSesionActual().getGrupoPruebasTEMP().getId(),Usuario.SESION_ACTUAL.getGrupoPruebasTEMP().getPlantel().getId(),Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas().getId());

                                Intent intent=new Intent(PruebaFisicoActivity.this,ListaPersonasGrupoPruebasActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                PruebaFisicoActivity.this.startActivity(intent);
                                Toast.makeText(context, "Registro de Prueba Tecnica Exitosa", Toast.LENGTH_SHORT).show();
                            }else{
                                progressDialog.dismiss();
                                Intent intent=new Intent(PruebaFisicoActivity.this,BarrioIntimoPersonaActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                PruebaFisicoActivity.this.startActivity(intent);
                                Toast.makeText(context, "Registro de Prueba Tecnica Exitosa", Toast.LENGTH_SHORT).show();
                            }
                        }


                    } else {
                        Toast.makeText(context, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error en Recupera codigo de usuario :"+e);
                }
            }
        };

        RegistrarPruebaFisica xx = new RegistrarPruebaFisica(id_user,id_barrio,id_persona,peso,talla,rj,cmj,abk,fms,velocidad,yoyo,info_velo,info_pot,info_resi,prom_velo,prom_pote,prom_resi,total,estado, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    private void Calculos() {


        e7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()!=0){
                double dato=Double.parseDouble(s.toString().trim());
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



                double to=Double.parseDouble(s.toString().trim());

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
                    double to=Double.parseDouble(s.toString().trim());

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
                 double to=Double.parseDouble(String.valueOf(s).trim());
                 double or=4.025*to;
                 PruebaFisica.PRUEBA_FISICA.setInformativoResistencia(or);

                 String pr=df.format(PruebaFisica.PRUEBA_FISICA.getInformativoResistencia());

                 //PruebaFisica.PRUEBA_FISICA.setInformativoResistencia(Double.parseDouble(pr));
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

                double to=Double.parseDouble(s.toString().trim());
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

                double to=Double.parseDouble(s.toString().trim());
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


    private void Actualizar_Total(double total_general,int id_barrios, int id_peerr,final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Prueba Fisica:");
        progressDialog.setMessage("Guardando...");
        progressDialog.show();

        String id_barrio=String.valueOf(id_barrios);
        String id_pers=String.valueOf(id_peerr);
        String total_fisico=String.valueOf(total_general);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        debug("Actualizar Total_general_fisico");


                        Actualizar_prueba_fisica(Usuario.SESION_ACTUAL.getPersona_barrio().getId(),context);

                    }else {

                        Toast.makeText(context, "Error de conexion al actualizar total", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        Actualizar_barrio2 xx = new Actualizar_barrio2(id_barrio,id_pers,total_fisico, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }

    private void Actualizar_prueba_fisica(int id,final Context context) {


        String id_pers=String.valueOf(id);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        debug("Actualizar prueba fisica");


                        Registrar_Prueba_fisica(context,Usuario.SESION_ACTUAL.getId(),Usuario.SESION_ACTUAL.getId_barrio_intimo(),Usuario.SESION_ACTUAL.getPersona_barrio().getId());

                    }else {

                        Toast.makeText(context, "Error de conexion al actualizar estado fisico", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR fisico :"+e);
                }
            }
        };

        Actualizar_barrio_fisica xx = new Actualizar_barrio_fisica(id_pers, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);



    }


    public void debug(String sm){
        System.out.println(sm);
    }


    public void onBackPressed() {

        if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
            builder.setTitle("Metodologia")
                    .setMessage("¿Desea salir de la Evaluaciòn Fisica?")
                    .setPositiveButton("SI",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(PruebaFisicoActivity.this,GestionPersonaFasePruebaActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    PruebaFisicoActivity.this.startActivity(intent);
                                }
                            })
                    .setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

            builder.show();

        }else{
            if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setTitle("Metodologia")
                        .setMessage("¿Desea salir de la Evaluaciòn Fisica?")
                        .setPositiveButton("SI",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(PruebaFisicoActivity.this,ListaPersonasGrupoPruebasActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        PruebaFisicoActivity.this.startActivity(intent);

                                    }
                                })
                        .setNegativeButton("NO",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                builder.show();
            }else{
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setTitle("Barrio Intimo")
                        .setMessage("¿Desea salir de la Evaluaciòn?")
                        .setPositiveButton("SI",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(PruebaFisicoActivity.this,BarrioIntimoPersonaActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        PruebaFisicoActivity.this.startActivity(intent);

                                    }
                                })
                        .setNegativeButton("NO",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                builder.show();
            }

        }






    }



    private void Actualizar_fisico(int diagnostico, int grupo, int plantel, int persona) {
        String id_fisico=String.valueOf(diagnostico);
        String id_grupo=String.valueOf(grupo);
        String id_plantel=String.valueOf(plantel);
        String id_persona=String.valueOf(persona);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        debug("FISICO ACTUALIZADO");
                    }else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        ActualizarPruebaFisico xx = new ActualizarPruebaFisico(id_fisico,id_grupo,id_plantel,id_persona, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }




    private void Registrar_Fase_Prueba(final int usuario,final int persona,final int tipo_prueba,final int id_prueba,final Context context) {

        String id_usuario=String.valueOf(usuario);
        String id_persona=String.valueOf(persona);
        String t_prueba=String.valueOf(tipo_prueba);
        String id_diagnostico=String.valueOf(id_prueba);


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        debug("FASE PRUEBA REGISTRADO");
                    }else {

                        Toast.makeText(context, "Error de conexion FASE PRUEBA", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR FASE PRUEBA :"+e);
                }
            }
        };

        RegistrarFasePrueba xx = new RegistrarFasePrueba(id_usuario,id_persona,t_prueba,id_diagnostico, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }
}
