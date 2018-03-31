package org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoPersonaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.CaptacionMasiva.ListaPersonaMasivoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.RegistroPostulantesActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.ValidarDiagnosticoIndividualActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.ListaPersonasGrupoPruebasActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia_Fase_Prueba.GestionPersonaFasePruebaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Posicion;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActivarPersona;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.DesactivarPersona;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPosiciones;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Captacion_Vista;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Captacion_funcional;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Diagnostico_Otros;
import org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Diagnostico;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PruebaDiagnosticoActivity extends AppCompatActivity {
    CardView card,card_aprobacion,card_saltar;
    ScrollView scroll;
    Spinner sugerido1,sugerido2,sugerido3;
    TextView ubicacion_texto;
    Context context;
    List<Posicion> ListaPosiciones;
    String[] ArregloString_Posiciones;
    RadioGroup opciones_lateralidad;
    TextView texto_continuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captacion);
        card=findViewById(R.id.card_totalizado);
        card_aprobacion=findViewById(R.id.card_aprobacion);
        card_saltar=findViewById(R.id.card_saltar);
        scroll=findViewById(R.id.scroll_captacion);
        ubicacion_texto=findViewById(R.id.ubicacion_texto);
        sugerido1=findViewById(R.id.sugerido1);
        sugerido2=findViewById(R.id.sugerido2);
        sugerido3=findViewById(R.id.sugerido3);
        opciones_lateralidad=findViewById(R.id.opciones_lateralidad);
        texto_continuar=findViewById(R.id.texto_continuar);

        ListaPosiciones=new ArrayList<>();
        context=this;

        Listar_Posiciones(context);
        System.out.println("ID_PERSONA RECIBIDO: "+ Persona.PERSONA_TEMP.getId());

        if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
                ubicacion_texto.setText("Plantel");
        }else{
            if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){
                ubicacion_texto.setText("Plantel");
            }else{
                if(Usuario.SESION_ACTUAL.getPersona_barrio()!=null){

                    if(GestionUbigeo.CAPTACION_UBIGEO_BARRIO.getUbigeo_descripcion().length()!=0){
                        ubicacion_texto.setText("Ubicación de Diagnostico: "+GestionUbigeo.CAPTACION_UBIGEO_BARRIO.getUbigeo_descripcion());
                    }else{
                        ubicacion_texto.setText("Ubicación no disponible");
                    }

                }else{
                    if(Persona.PERSONA_TEMP.getId()!=0){
                        Activar_Persona(Persona.PERSONA_TEMP.getId());
                        if(GestionUbigeo.CAPTACION_UBIGEO_MASIVO.getUbigeo_descripcion().length()!=0){
                            ubicacion_texto.setText("Ubicación de Diagnostico: "+GestionUbigeo.CAPTACION_UBIGEO_MASIVO.getUbigeo_descripcion());
                        }else{
                            ubicacion_texto.setText("Ubicación no disponible");
                        }
                    }else{
                        //MODULO CAPTACION DIAGNOSTICO INDIVIDUAL
                        if(GestionUbigeo.CAPTACION_UBIGEO.getUbigeo_descripcion().length()!=0){
                            ubicacion_texto.setText("Ubicación de Diagnostico: "+GestionUbigeo.CAPTACION_UBIGEO.getUbigeo_descripcion());
                        }else{
                            ubicacion_texto.setText("Ubicación no disponible");
                        }
                    }
                }
            }
        }


        // Animaciones de Vistas Captacion
         Creacion_Animaciones();
        //Seleccion de Opciones group checked!
        Seteo_RadioGroups();

        card_aprobacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
                    Intent intent = new Intent(PruebaDiagnosticoActivity.this, ValidarDiagnosticoIndividualActivity.class);
                    PruebaDiagnosticoActivity.this.startActivity(intent);
             }else{
                 if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){
                     Intent intent = new Intent(PruebaDiagnosticoActivity.this, ValidarDiagnosticoIndividualActivity.class);
                     PruebaDiagnosticoActivity.this.startActivity(intent);
                 }else{
                     if(Usuario.SESION_ACTUAL.getPersona_barrio()!=null){
                         Intent intent = new Intent(PruebaDiagnosticoActivity.this, ValidarDiagnosticoIndividualActivity.class);
                         PruebaDiagnosticoActivity.this.startActivity(intent);
                     }else{
                         if(Persona.PERSONA_TEMP.getId()!=0){
                             Intent intent = new Intent(PruebaDiagnosticoActivity.this, ValidarDiagnosticoIndividualActivity.class);
                             PruebaDiagnosticoActivity.this.startActivity(intent);
                         }else{
                             Intent intent = new Intent(PruebaDiagnosticoActivity.this, RegistroPostulantesActivity.class);
                             PruebaDiagnosticoActivity.this.startActivity(intent);
                         }
                     }
                 }

             }


            }
        });

        card_saltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
                    Intent intent = new Intent(PruebaDiagnosticoActivity.this, ValidarDiagnosticoIndividualActivity.class);
                    PruebaDiagnosticoActivity.this.startActivity(intent);
                }else{
                    if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){
                        Intent intent = new Intent(PruebaDiagnosticoActivity.this, ValidarDiagnosticoIndividualActivity.class);
                        PruebaDiagnosticoActivity.this.startActivity(intent);
                    }else{
                        if(Usuario.SESION_ACTUAL.getPersona_barrio()!=null){
                            Intent intent = new Intent(PruebaDiagnosticoActivity.this, ValidarDiagnosticoIndividualActivity.class);
                            PruebaDiagnosticoActivity.this.startActivity(intent);
                        }else{
                            if(Persona.PERSONA_TEMP.getId()!=0){
                                Intent intent = new Intent(PruebaDiagnosticoActivity.this, ValidarDiagnosticoIndividualActivity.class);
                                PruebaDiagnosticoActivity.this.startActivity(intent);
                            }else{
                                Intent intent = new Intent(PruebaDiagnosticoActivity.this, RegistroPostulantesActivity.class);
                                PruebaDiagnosticoActivity.this.startActivity(intent);
                            }
                        }
                    }

                }

            }
        });

        Verificar_lateralidad();
        Verificar_Sugeridos();

        if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){
            card_aprobacion.setVisibility(View.VISIBLE);
        }
    }
    private void Activar_Persona(int id) {
       String id_persona=String.valueOf(id);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                            debug("Persona Activada");
                    }else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        ActivarPersona validarSesion = new ActivarPersona(id_persona, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(validarSesion);

    }
    private void Desactivar_Persona(int id) {
        String id_persona=String.valueOf(id);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        debug("Persona Desactivada");
                    }else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        DesactivarPersona validarSesion = new DesactivarPersona(id_persona, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(validarSesion);

    }
    private void debug(String mensaje){
        System.out.println(mensaje);
    }
    private void Verificar_Sugeridos() {
        sugerido1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Object item = adapterView.getItemAtPosition(i);
                for(int x=0;x<ListaPosiciones.size();x++){
                    if(ListaPosiciones.get(x).getNombre_Posicione().equalsIgnoreCase(String.valueOf(item))){
                         Diagnostico_Otros.OTROS.setSugerido1(ListaPosiciones.get(x));


                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sugerido2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                for(int x=0;x<ListaPosiciones.size();x++){
                    if(ListaPosiciones.get(x).getNombre_Posicione().equalsIgnoreCase(String.valueOf(item))){
                        Diagnostico_Otros.OTROS.setSugerido2(ListaPosiciones.get(x));


                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sugerido3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                for(int x=0;x<ListaPosiciones.size();x++){
                    if(ListaPosiciones.get(x).getNombre_Posicione().equalsIgnoreCase(String.valueOf(item))){
                        Diagnostico_Otros.OTROS.setSugerido3(ListaPosiciones.get(x));


                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void Verificar_lateralidad() {

        opciones_lateralidad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.lateral_derecho){
                    Diagnostico_Otros.OTROS.setLateralidad("LATERALIDAD DERECHA");

                }else if(i==R.id.lateral_izquierdo){
                    Diagnostico_Otros.OTROS.setLateralidad("LATERALIDAD IZQUIERDA");

                }else if(i==R.id.lateral_ambos){
                    Diagnostico_Otros.OTROS.setLateralidad("LATERALIDAD AMBAS PIERNAS");

                }
            }
        });
    }
    private void Listar_Posiciones(final Context context) {
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("posiciones");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Posicion temp=new Posicion();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Posicione(objeto.getString("NOMBRE_POSICION"));
                            temp.setEstado(objeto.getInt("ESTADO"));
                            ListaPosiciones.add(temp);
                        }

                        ArregloString_Posiciones=new String[ListaPosiciones.size()];
                        for(int i=0;i<ListaPosiciones.size();i++){
                            ArregloString_Posiciones[i]=ListaPosiciones.get(i).getNombre_Posicione();
                        }
                        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,ArregloString_Posiciones);
                        sugerido1.setAdapter(adapter_arr);
                        sugerido2.setAdapter(adapter_arr);
                        sugerido3.setAdapter(adapter_arr);

                    } else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarPosiciones xx = new RecuperarPosiciones(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);
    }
    private void Seteo_RadioGroups() {

        for(int i = 0; i< Recursos_Diagnostico.LISTA_FISICO.size(); i++){
            Generar_Funcion(i, Recursos_Diagnostico.LISTA_FISICO.get(i));
        }
        for(int i = 0; i< Recursos_Diagnostico.LISTA_CAPACIDAD.size(); i++){
            Generar_Funcion(i, Recursos_Diagnostico.LISTA_CAPACIDAD.get(i));
        }
        for(int i = 0; i< Recursos_Diagnostico.LISTA_SOCIAL.size(); i++){
            Generar_Funcion(i, Recursos_Diagnostico.LISTA_SOCIAL.get(i));
        }
        for(int i = 0; i< Recursos_Diagnostico.LISTA_PSICO.size(); i++){
            Generar_Funcion(i, Recursos_Diagnostico.LISTA_PSICO.get(i));
        }
        for(int i = 0; i< Recursos_Diagnostico.LISTA_TECNICO.size(); i++){
            Generar_Funcion(i, Recursos_Diagnostico.LISTA_TECNICO.get(i));
        }

    }
    private void Creacion_Animaciones() {

        for(int i = 0; i< Recursos_Diagnostico.LISTA_VISTAS.size(); i++){

            LinearLayout linear=findViewById(Recursos_Diagnostico.LISTA_VISTAS.get(i).getContenedor());
            LayoutInflater inflater = LayoutInflater.from(this);

            final View view_actual = inflater.inflate(Recursos_Diagnostico.LISTA_VISTAS.get(i).getVista(), linear, true);
            LinearLayout line=view_actual.findViewById(Recursos_Diagnostico.LISTA_VISTAS.get(i).getArea_Accion());
            Recursos_Diagnostico.LISTA_VISTAS.get(i).setView(line);
            LinearLayout Accion_Panel=findViewById(Recursos_Diagnostico.LISTA_VISTAS.get(i).getPanel_Accion());
            Generar_Animacion(Recursos_Diagnostico.LISTA_VISTAS.get(i),line,Accion_Panel);
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
                    Refrescar_Totales();
                }else if(i == captacion_funcional.getRadio2()){
                    captacion_funcional.setResultado(1);
                    Refrescar_Totales();
                }
                else if(i == captacion_funcional.getRadio3()){
                    captacion_funcional.setResultado(2);
                    Refrescar_Totales();
                }else if(i == captacion_funcional.getRadio4()){
                    captacion_funcional.setResultado(3);
                    Refrescar_Totales();
                }
            }
        });

    }
    private void Refrescar_Totales() {
        int total1=0;
        int total2=0;
        int total3=0;
        int total4=0;
        int total5=0;

        int total_general=0;

        for(int i = 0; i< Recursos_Diagnostico.LISTA_FISICO.size(); i++){
            total1=total1+ Recursos_Diagnostico.LISTA_FISICO.get(i).getResultado();
        }
        TextView fisico_total=findViewById(Recursos_Diagnostico.LISTA_VISTAS.get(0).getTextViewTotal());
        fisico_total.setText(total1+" Ptos.");
        for(int i = 0; i< Recursos_Diagnostico.LISTA_CAPACIDAD.size(); i++){
            total2=total2+ Recursos_Diagnostico.LISTA_CAPACIDAD.get(i).getResultado();
        }

        TextView capacidad_total=findViewById(Recursos_Diagnostico.LISTA_VISTAS.get(1).getTextViewTotal());
        capacidad_total.setText(total2+" Ptos.");
        for(int i = 0; i< Recursos_Diagnostico.LISTA_SOCIAL.size(); i++){
            total3=total3+ Recursos_Diagnostico.LISTA_SOCIAL.get(i).getResultado();
        }
        TextView social_total=findViewById(Recursos_Diagnostico.LISTA_VISTAS.get(2).getTextViewTotal());
        social_total.setText(total3+" Ptos.");
        for(int i = 0; i< Recursos_Diagnostico.LISTA_TECNICO.size(); i++){
            total4=total4+ Recursos_Diagnostico.LISTA_TECNICO.get(i).getResultado();
        }
        TextView tecnico_total=findViewById(Recursos_Diagnostico.LISTA_VISTAS.get(3).getTextViewTotal());
        tecnico_total.setText(total4+" Ptos.");
        for(int i = 0; i< Recursos_Diagnostico.LISTA_PSICO.size(); i++){
            total5=total5+ Recursos_Diagnostico.LISTA_PSICO.get(i).getResultado();
        }
        TextView psico_total=findViewById(Recursos_Diagnostico.LISTA_VISTAS.get(4).getTextViewTotal());
        psico_total.setText(total5+" Ptos.");

        TextView total_g=findViewById(R.id.total_captacion);
        total_general=total1+total2+total3+total4+total5;
        total_g.setText(total_general+" Ptos.");
        Diagnostico_Otros.OTROS.setTotal_puntaje(total_general);

        if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){

        }else{
            if(total_general>=45 && total_general<=49){
               // card.setCardBackgroundColor(getResources().getColor(R.color.Orange));
                card_aprobacion.setVisibility(View.GONE);
                card_saltar.setVisibility(View.VISIBLE);
            }else if(total_general>=50){
              //  card.setCardBackgroundColor(getResources().getColor(R.color.green));
                card_aprobacion.setVisibility(View.VISIBLE);
                card_saltar.setVisibility(View.GONE);
            }else if(total_general<=44){
                card.setCardBackgroundColor(getResources().getColor(R.color.grey));
                card_aprobacion.setVisibility(View.GONE);
                card_saltar.setVisibility(View.GONE);
            }
        }

    }
    private void Generar_Animacion(final Captacion_Vista captacion_vista, final LinearLayout view_actual, LinearLayout Accion_Panel ) {

        Accion_Panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(captacion_vista.getAccion()==0){
                    scroll.smoothScrollTo(0,0);
                    view_actual.setVisibility(View.VISIBLE);

                    captacion_vista.setAccion(1);
                    Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abajo);
                    view_actual.startAnimation(animation);

                    cerrar_ventanas(view_actual);
                }else if(captacion_vista.getAccion()==1){
                    scroll.smoothScrollTo(0,0);
                    captacion_vista.setAccion(0);
                    Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.arriba);
                    view_actual.startAnimation(animation);
                    view_actual.setVisibility(View.GONE);
                }

            }
        });
    }
    private void cerrar_ventanas(LinearLayout view_actual) {
        for(int i = 0; i< Recursos_Diagnostico.LISTA_VISTAS.size(); i++){
            if(Recursos_Diagnostico.LISTA_VISTAS.get(i).getView()==view_actual){

            }else{
               Recursos_Diagnostico.LISTA_VISTAS.get(i).getView().setVisibility(View.GONE);
            }
        }
    }

    private void Limpiar_Listas() {


        for(int i=0;i<Recursos_Diagnostico.LISTA_FISICO.size();i++){
            Recursos_Diagnostico.LISTA_FISICO.get(i).setResultado(0);
        }
        for(int i=0;i<Recursos_Diagnostico.LISTA_SOCIAL.size();i++){
            Recursos_Diagnostico.LISTA_SOCIAL.get(i).setResultado(0);
        }
        for(int i=0;i<Recursos_Diagnostico.LISTA_TECNICO.size();i++){
            Recursos_Diagnostico.LISTA_TECNICO.get(i).setResultado(0);
        }
        for(int i=0;i<Recursos_Diagnostico.LISTA_PSICO.size();i++){
            Recursos_Diagnostico.LISTA_PSICO.get(i).setResultado(0);
        }

        for(int i=0;i<Recursos_Diagnostico.LISTA_CAPACIDAD.size();i++){
            Recursos_Diagnostico.LISTA_CAPACIDAD.get(i).setResultado(0);
        }



    }
    public void onBackPressed() {


    if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("Metodologia")
                .setMessage("¿Desea salir de la Evaluaciòn de Diagnostico?")
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Limpiar_Listas();
                                Intent intent = new Intent(PruebaDiagnosticoActivity.this,GestionPersonaFasePruebaActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                PruebaDiagnosticoActivity.this.startActivity(intent);
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
                    .setMessage("¿Desea salir de la Evaluaciòn de Diagnostico?")
                    .setPositiveButton("SI",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Limpiar_Listas();
                                    Intent intent = new Intent(PruebaDiagnosticoActivity.this,ListaPersonasGrupoPruebasActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    PruebaDiagnosticoActivity.this.startActivity(intent);
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
            if(Usuario.SESION_ACTUAL.getPersona_barrio()!=null){

                Limpiar_Listas();
                Intent intent = new Intent(PruebaDiagnosticoActivity.this,BarrioIntimoPersonaActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PruebaDiagnosticoActivity.this.startActivity(intent);
            }else{
                if(Persona.PERSONA_TEMP.getId()!=0){

                    final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                    builder.setTitle("Captacion")
                            .setMessage("¿Desea salir de la Evaluaciòn?")
                            .setPositiveButton("SI",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Limpiar_Listas();
                                            Desactivar_Persona(Persona.PERSONA_TEMP.getId());
                                            Intent intent = new Intent(PruebaDiagnosticoActivity.this,ListaPersonaMasivoActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            PruebaDiagnosticoActivity.this.startActivity(intent);
                                            finish();
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
                    builder.setTitle("Captacion")
                            .setMessage("¿Desea salir de la Evaluaciòn?")
                            .setPositiveButton("SI",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Limpiar_Listas();
                                            Intent intent = new Intent(PruebaDiagnosticoActivity.this,PrincipalActivity.class);
                                            intent.putExtra("o","o1");
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            PruebaDiagnosticoActivity.this.startActivity(intent);
                                            finish();
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
    }






    }

}
