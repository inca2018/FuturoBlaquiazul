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
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoPersonaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.ListaPersonasGrupoPruebasActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia_Fase_Prueba.GestionPersonaFasePruebaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Ubigeo.UbigeoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PruebaFisica;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PruebaTecnica;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActualizarPruebaFisico;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActualizarPruebaTecnico;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Actualizar_barrio3;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Actualizar_barrio_tecnica;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarFasePrueba;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarPruebaTecnica;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Captacion_Vista;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Captacion_funcional;
import org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Diagnostico;
import org.json.JSONException;
import org.json.JSONObject;

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
   Context context;
   ProgressDialog progressDialog;

   TextView persona_nombre,ubigeo;

   Button guardar_prueba_fisica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_tecnica);
        context=this;

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
        persona_nombre=findViewById(R.id.prueba_tecnica_nombre);
        ubigeo=findViewById(R.id.prueba_tecnica_ubigeo);
        guardar_prueba_fisica=findViewById(R.id.guardar_prueba_fisica);
        total_general_tecnico=findViewById(R.id.total_general_tecnico);

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
                    persona_nombre.setText("No Disponible");
                    ubigeo.setText("No Disponible");
                }

            }

        }



        limpiar_entradas();

        Creacion_Animaciones();
        Seteo_RadioGroups();

        mostrar_resultados();


        guardar_prueba_fisica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Integer.parseInt(pase_ras.getText().toString())!=0 && Integer.parseInt(pase_alto.getText().toString())!=0 && Integer.parseInt(c_ras.getText().toString())!=0 && Integer.parseInt(c_alto.getText().toString())!=0 && Integer.parseInt(total_pase.getText().toString())!=0 && Integer.parseInt(total_control.getText().toString())!=0 && Integer.parseInt(total_remate.getText().toString())!=0 && Integer.parseInt(total_conduccion.getText().toString())!=0 && Integer.parseInt(total_cabeceo.getText().toString())!=0){


                    if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
                        Registrar_Prueba_tecnica(context,Usuario.SESION_ACTUAL.getId(),0,Usuario.SESION_ACTUAL.getPersona_fase_pruebas().getId());
                    }else{
                        if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){
                            Registrar_Prueba_tecnica(context,Usuario.SESION_ACTUAL.getId(),0,Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas().getId());
                        }else{
                            Actualizar_Total(PruebaTecnica.PRUEBA_TECNICA.getTotal_general(), Usuario.SESION_ACTUAL.getId_barrio_intimo(),Usuario.SESION_ACTUAL.getPersona_barrio().getId(),context);

                        }

                    }



                }else{
                    Toast.makeText(PruebaTecnicaActivity.this, "Complete información necesaria para la evaluación", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void limpiar_entradas() {
     pase_ras.setText("0");
     pase_alto.setText("0");
     c_alto.setText("0");
     c_ras.setText("0");
     total_pase.setText("0");
     total_control.setText("0");
     total_remate.setText("0");
     total_conduccion.setText("0");
     total_cabeceo.setText("0");
     total_general_tecnico.setText("0 Ptos.");

     PruebaTecnica.PRUEBA_TECNICA.VaciarDatos();


     for(int i=0;i<LISTA_PRUEBA_TECNICA_CABECEO.size();i++){
         LISTA_PRUEBA_TECNICA_CABECEO.get(i).setResultado(0);
     }

        for(int i=0;i<LISTA_PRUEBA_TECNICA_CONDUCCION.size();i++){
            LISTA_PRUEBA_TECNICA_CONDUCCION.get(i).setResultado(0);
        }

        for(int i=0;i<LISTA_PRUEBA_TECNICA_PASE_CONTROL.size();i++){
            LISTA_PRUEBA_TECNICA_PASE_CONTROL.get(i).setResultado(0);
        }

        for(int i=0;i<LISTA_PRUEBA_TECNICA_REMATE.size();i++){
            LISTA_PRUEBA_TECNICA_REMATE.get(i).setResultado(0);
        }

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

        PruebaTecnica.PRUEBA_TECNICA.setCabeceo(totalca);

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

         PruebaTecnica.PRUEBA_TECNICA.setConduccion(to);
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


    private void Actualizar_Total(double total_general,int id_barrios, int id_peerr,final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Prueba Tecnica:");
        progressDialog.setMessage("Guardando...");
        progressDialog.show();

        String id_barrio=String.valueOf(id_barrios);
        String id_pers=String.valueOf(id_peerr);
        String total_tecnico=String.valueOf(total_general);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        debug("Actualizar Total_general_tecnico");


                        Actualizar_prueba_tecnica(Usuario.SESION_ACTUAL.getPersona_barrio().getId(),context);

                    }else {

                        Toast.makeText(context, "Error de conexion al actualizar total", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        Actualizar_barrio3 xx = new Actualizar_barrio3(id_barrio,id_pers,total_tecnico, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }
    public void debug(String sm){
        System.out.println(sm);
    }
    private void Actualizar_prueba_tecnica(int id,final Context context) {


        String id_pers=String.valueOf(id);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        debug("Actualizar prueba fisica");


                        Registrar_Prueba_tecnica(context,Usuario.SESION_ACTUAL.getId(),Usuario.SESION_ACTUAL.getId_barrio_intimo(),Usuario.SESION_ACTUAL.getPersona_barrio().getId());

                    }else {

                        Toast.makeText(context, "Error de conexion al actualizar estado fisico", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR fisico :"+e);
                }
            }
        };

        Actualizar_barrio_tecnica xx = new Actualizar_barrio_tecnica(id_pers, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);



    }
    private void Registrar_Prueba_tecnica(final Context context, int user, final int id_barrio_intimo, int id_per) {
        Debug(PruebaFisica.PRUEBA_FISICA.toString());



        String id_user=String.valueOf(user);
        String id_barrio=String.valueOf(id_barrio_intimo);
        String id_persona=String.valueOf(id_per);

        String pase_Ras=String.valueOf(PruebaTecnica.PRUEBA_TECNICA.getP_Ras());
        String pase_al=String.valueOf(PruebaTecnica.PRUEBA_TECNICA.getP_Alto());
        String control_ras=String.valueOf(PruebaTecnica.PRUEBA_TECNICA.getControl_Ras());
        String control_alto=String.valueOf(PruebaTecnica.PRUEBA_TECNICA.getContorl_Alto());
        String pase=String.valueOf(PruebaTecnica.PRUEBA_TECNICA.getPase());
        String control=String.valueOf(PruebaTecnica.PRUEBA_TECNICA.getControl());
        String remate=String.valueOf(PruebaTecnica.PRUEBA_TECNICA.getRemate());
        String conduccion=String.valueOf(PruebaTecnica.PRUEBA_TECNICA.getConduccion());
        String cabeceo=String.valueOf(PruebaTecnica.PRUEBA_TECNICA.getCabeceo());
        String total_general=String.valueOf(PruebaTecnica.PRUEBA_TECNICA.getTotal_general());

        String estado="1";

        if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Prueba Tecnica:");
            progressDialog.setMessage("Guardando...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Prueba Tecnica:");
            progressDialog.setMessage("Guardando...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
                               progressDialog.dismiss();
                            int id_tecnico=jsonResponse.getInt("id_tecnico");


                            Registrar_Fase_Prueba(Usuario.SESION_ACTUAL.getId(),Usuario.SESION_ACTUAL.getPersona_fase_pruebas().getId(),Usuario.SESION_ACTUAL.getTipoPruebas().getId(),id_tecnico,context);


                            Intent intent=new Intent(PruebaTecnicaActivity.this,GestionPersonaFasePruebaActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            PruebaTecnicaActivity.this.startActivity(intent);
                            Toast.makeText(context, "Registro de Prueba Tecnica Exitosa", Toast.LENGTH_SHORT).show();
                            limpiar_entradas();

                        }else{
                            if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){
                                progressDialog.dismiss();
                                int id_tecnico=jsonResponse.getInt("id_tecnico");
                                Actualizar_tecnico(id_tecnico,Usuario.getSesionActual().getGrupoPruebasTEMP().getId(),Usuario.SESION_ACTUAL.getGrupoPruebasTEMP().getPlantel().getId(),Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas().getId());

                                Intent intent=new Intent(PruebaTecnicaActivity.this,ListaPersonasGrupoPruebasActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                PruebaTecnicaActivity.this.startActivity(intent);
                                Toast.makeText(context, "Registro de Prueba Tecnica Exitosa", Toast.LENGTH_SHORT).show();
                                limpiar_entradas();
                            }else{
                                progressDialog.dismiss();
                                Intent intent=new Intent(PruebaTecnicaActivity.this,BarrioIntimoPersonaActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                PruebaTecnicaActivity.this.startActivity(intent);
                                Toast.makeText(context, "Registro de Prueba Tecnica Exitosa", Toast.LENGTH_SHORT).show();
                                limpiar_entradas();

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

        RegistrarPruebaTecnica xx = new RegistrarPruebaTecnica(id_user,id_barrio,id_persona,pase_Ras,pase_al,control_ras,control_alto,pase,control,remate,conduccion,cabeceo,total_general,estado, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    public void onBackPressed() {
        if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
            builder.setTitle("Metodologia")
                    .setMessage("¿Desea salir de la Evaluación Tecnica?")
                    .setPositiveButton("SI",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(PruebaTecnicaActivity.this,GestionPersonaFasePruebaActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    PruebaTecnicaActivity.this.startActivity(intent);
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
                        .setMessage("¿Desea salir de la Evaluaciòn Técnica?")
                        .setPositiveButton("SI",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(PruebaTecnicaActivity.this,ListaPersonasGrupoPruebasActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        PruebaTecnicaActivity.this.startActivity(intent);

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
                                        Intent intent = new Intent(PruebaTecnicaActivity.this,BarrioIntimoPersonaActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        PruebaTecnicaActivity.this.startActivity(intent);

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

    private void Actualizar_tecnico(int tecnico, int grupo, int plantel, int persona) {
        String id_tecnico=String.valueOf(tecnico);
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
                        debug("TECNICO ACTUALIZADO");
                    }else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        ActualizarPruebaTecnico xx = new ActualizarPruebaTecnico(id_tecnico,id_grupo,id_plantel,id_persona, responseListener);
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
