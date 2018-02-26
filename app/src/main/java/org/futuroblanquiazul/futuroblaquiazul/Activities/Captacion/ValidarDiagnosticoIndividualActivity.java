package org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoPersonaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.ActivityEntity.modulo_captacion;
import org.futuroblanquiazul.futuroblaquiazul.Entity.BarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActivarEvaPersona;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActivarEvaPersonaBarrio;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Actualizar_barrio;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.DesactivarPersona;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarModuloCapta;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarPersonaRecuperarCodigo;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarResultadosDiagnostico;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Diagnostico_Otros;
import org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Diagnostico;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Registro_Postulante;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static org.futuroblanquiazul.futuroblaquiazul.ActivityEntity.modulo_captacion.BASE;


public class ValidarDiagnosticoIndividualActivity extends AppCompatActivity {

    TextView nombre,ubigeo,total;
    CardView verificar_resultados;
    Context context;

    int id_persona,id_fisico,id_social,id_capacidad,id_psico,id_tecnico;
    ProgressDialog progressDialog;
    String estado_capta;

    String id_per,id_Dep,id_Prov,id_Dis;

    List<String> RegistroPersona;
    List<Integer> ResultadosDiagnostico;

    int total_general;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar_diagnostico);
        nombre=findViewById(R.id.resultado_persona);
        ubigeo=findViewById(R.id.resultado_ubigeo);
        total=findViewById(R.id.resultado_total);
        context=this;
        RegistroPersona=new ArrayList<>();
        ResultadosDiagnostico=new ArrayList<>();


        if(Usuario.SESION_ACTUAL.getPersona_barrio().getId()!=0){

            if(GestionUbigeo.CAPTACION_UBIGEO_BARRIO.getUbigeo_descripcion().length()!=0){
                ubigeo.setText(String.valueOf(GestionUbigeo.CAPTACION_UBIGEO_BARRIO.getUbigeo_descripcion()));
            }else{
                ubigeo.setText("Ubigeo no encontrado!");
            }

            nombre.setText(Usuario.SESION_ACTUAL.getPersona_barrio().getNombre_Persona()+" "+Usuario.SESION_ACTUAL.getPersona_barrio().getApellidos_Persona());


        }else{

            if(Persona.PERSONA_TEMP.getId()!=0){
                if(GestionUbigeo.CAPTACION_UBIGEO_MASIVO.getUbigeo_descripcion().length()!=0){
                    ubigeo.setText(String.valueOf(GestionUbigeo.CAPTACION_UBIGEO_MASIVO.getUbigeo_descripcion()));
                }else {
                    ubigeo.setText("Ubigeo no encontrado!");
                }

                nombre.setText(Persona.PERSONA_TEMP.getNombre_Persona()+" "+Persona.PERSONA_TEMP.getApellidos_Persona());

            }else{
                if(GestionUbigeo.CAPTACION_UBIGEO.getUbigeo_descripcion().length()!=0){
                    ubigeo.setText(String.valueOf(GestionUbigeo.CAPTACION_UBIGEO.getUbigeo_descripcion()));
                }else {
                    ubigeo.setText("Ubigeo no encontrado!");
                }

                if(Recursos_Registro_Postulante.LISTA_REGISTRO.get(0).getValor().length()!=0 && Recursos_Registro_Postulante.LISTA_REGISTRO.get(1).getValor().length()!=0){
                    String nombress=Recursos_Registro_Postulante.LISTA_REGISTRO.get(0).getValor()+" "+Recursos_Registro_Postulante.LISTA_REGISTRO.get(1).getValor();
                    nombre.setText(nombress.toUpperCase());
                }

            }

        }


        if(Diagnostico_Otros.OTROS.getTotal_puntaje()!=0){
            total.setText(Diagnostico_Otros.OTROS.getTotal_puntaje()+" Puntos");
        }else{
            total.setText("Total no encontrado");
        }




        verificar_resultados=findViewById(R.id.confirmacion_resultados);
        verificar_resultados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Armar_Resultados();

            }
        });
    }
    private void Armar_Resultados() {

         for(int i=0;i<Recursos_Registro_Postulante.LISTA_REGISTRO.size();i++){
            RegistroPersona.add(Recursos_Registro_Postulante.LISTA_REGISTRO.get(i).getValor());
         }

        for(int i = 0; i< Recursos_Diagnostico.LISTA_FISICO.size(); i++){
            ResultadosDiagnostico.add(Recursos_Diagnostico.LISTA_FISICO.get(i).getResultado());
        }

        for(int i=0;i<Recursos_Diagnostico.LISTA_CAPACIDAD.size();i++){
            ResultadosDiagnostico.add(Recursos_Diagnostico.LISTA_CAPACIDAD.get(i).getResultado());
        }

        for(int i=0;i<Recursos_Diagnostico.LISTA_SOCIAL.size();i++){
            ResultadosDiagnostico.add(Recursos_Diagnostico.LISTA_SOCIAL.get(i).getResultado());
        }

        for(int i=0;i<Recursos_Diagnostico.LISTA_TECNICO.size();i++){
            ResultadosDiagnostico.add(Recursos_Diagnostico.LISTA_TECNICO.get(i).getResultado());
        }

        for(int i=0;i<Recursos_Diagnostico.LISTA_PSICO.size();i++){
            ResultadosDiagnostico.add(Recursos_Diagnostico.LISTA_PSICO.get(i).getResultado());
        }

        if(Usuario.SESION_ACTUAL.getPersona_barrio().getId()!=0){

            Registrar_Resultados(context,Usuario.SESION_ACTUAL.getPersona_barrio().getId(),ResultadosDiagnostico);
        }else{
            if(Persona.PERSONA_TEMP.getId()!=0){
                Registrar_Resultados(context,Persona.PERSONA_TEMP.getId(),ResultadosDiagnostico);
            }else{
                Registrar_Persona(RegistroPersona,context);
            }
        }



    }
    private void Registrar_Persona(final List<String> registroPersona,final Context context) {
        String Nombres=registroPersona.get(0).toUpperCase().trim();
        String Apellidos=registroPersona.get(1).toUpperCase().trim();
        String Nacionalidad=registroPersona.get(2).toUpperCase().trim();
        String Club=registroPersona.get(3).toUpperCase().trim();
        String Liga=registroPersona.get(4).toUpperCase().trim();
        String Categoria=registroPersona.get(5).toUpperCase().trim();
        String Dni=registroPersona.get(6).toUpperCase().trim();
        String FechaNacimiento=registroPersona.get(7).toUpperCase().trim();
        String LugarResidencia=registroPersona.get(8).toUpperCase().trim();
        String Telefonos=registroPersona.get(9).toUpperCase().trim();
        String Correo=registroPersona.get(10).toUpperCase().trim();
        String Apoderado=registroPersona.get(11).toUpperCase().trim();
        String TelefonoApoderado=registroPersona.get(12).toUpperCase().trim();
        String Estado=String.valueOf(1);
        String Estado_Capta=String.valueOf(1);


        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Registro");
        progressDialog.setMessage("Enviando Información...");
        progressDialog.show();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        id_persona=jsonResponse.getInt("id_persona");
                        BASE.setId_persona(id_persona);

                        if(id_persona==0){
                            progressDialog.dismiss();
                            Toast.makeText(context, "Problema de Conexión al Recuperar Codigo Persona", Toast.LENGTH_SHORT).show();
                        }else{

                            Registrar_Resultados(context,id_persona,ResultadosDiagnostico);

                            debug("PASO REGISTRO DE PERSONA");

                        }

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error en Recupera codigo de usuario :"+e);
                }
            }
        };

        RegistrarPersonaRecuperarCodigo xx = new RegistrarPersonaRecuperarCodigo(Nombres, Apellidos,Nacionalidad,Club, Liga,Categoria,Dni,FechaNacimiento,LugarResidencia,Telefonos,Correo,Apoderado,TelefonoApoderado,Estado,Estado_Capta,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);
    }
    private void Registrar_Resultados(final Context context,final  int id_personaa,final List<Integer> resultadosDiagnostico) {
        String f1=String.valueOf(resultadosDiagnostico.get(0));
        String f2=String.valueOf(resultadosDiagnostico.get(1));
        String f3=String.valueOf(resultadosDiagnostico.get(2));
        String f4=String.valueOf(resultadosDiagnostico.get(3));
        String f5=String.valueOf(resultadosDiagnostico.get(4));
        String f6=String.valueOf(resultadosDiagnostico.get(5));
        String f7=String.valueOf(resultadosDiagnostico.get(6));
        String c1=String.valueOf(resultadosDiagnostico.get(7));
        String c2=String.valueOf(resultadosDiagnostico.get(8));
        String c3=String.valueOf(resultadosDiagnostico.get(9));
        String c4=String.valueOf(resultadosDiagnostico.get(10));
        String s1=String.valueOf(resultadosDiagnostico.get(11));
        String s2=String.valueOf(resultadosDiagnostico.get(12));
        String s3=String.valueOf(resultadosDiagnostico.get(13));
        String s4=String.valueOf(resultadosDiagnostico.get(14));
        String t1=String.valueOf(resultadosDiagnostico.get(15));
        String t2=String.valueOf(resultadosDiagnostico.get(16));
        String t3=String.valueOf(resultadosDiagnostico.get(17));
        String t4=String.valueOf(resultadosDiagnostico.get(18));
        String t5=String.valueOf(resultadosDiagnostico.get(19));
        String t6=String.valueOf(resultadosDiagnostico.get(20));
        String p1=String.valueOf(resultadosDiagnostico.get(21));
        String p2=String.valueOf(resultadosDiagnostico.get(22));
        String p3=String.valueOf(resultadosDiagnostico.get(23));
        String p4=String.valueOf(resultadosDiagnostico.get(24));

        final String id_persona_reg=String.valueOf(id_personaa);


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        id_fisico=jsonResponse.getInt("id_fisico");
                        id_capacidad=jsonResponse.getInt("id_capacidad");
                        id_social=jsonResponse.getInt("id_social");
                        id_tecnico=jsonResponse.getInt("id_tecnico");
                        id_psico=jsonResponse.getInt("id_psico");

                        total_general=jsonResponse.getInt("total_general");
                        if(Usuario.SESION_ACTUAL.getPersona_barrio().getId()!=0 && total_general!=0){
                            Actualizar_Total(total_general,Usuario.SESION_ACTUAL.getId_barrio_intimo(),Usuario.SESION_ACTUAL.getPersona_barrio().getId(),context);
                        }

                        BASE.setCampo_fisico_id(id_fisico);
                        BASE.setCampo_capacidad_id(id_capacidad);
                        BASE.setCampo_social_id(id_social);
                        BASE.setCampo_tecnico_id(id_tecnico);
                        BASE.setCampo_psico_id(id_psico);

                        debug(BASE.toString());
                        Registrar_Modulo_Diagnostico(BASE,context);

                        debug("PASO REGISTRO DE DIAGNOSTICO");

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error en Recupera codigo de usuario :"+e);
                }
            }
        };

        RegistrarResultadosDiagnostico xx = new RegistrarResultadosDiagnostico(f1,f2,f3,f4,f5,f6,f7,c1,c2,c3,c4,s1,s2,s3,s4,t1,t2,t3,t4,t5,t6,p1,p2,p3,p4,id_persona_reg,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    private void Actualizar_Total(int total_general,int id_barrios, int id_peerr,final Context context) {
        String id_barrio=String.valueOf(id_barrios);
        String id_pers=String.valueOf(id_peerr);
        String total_siag=String.valueOf(total_general);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        debug("Actualizar Total_general_diagnsotico");
                    }else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        Actualizar_barrio xx = new Actualizar_barrio(id_barrio,id_pers,total_siag, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }

    private void Registrar_Modulo_Diagnostico(final modulo_captacion base, final Context context) {

        if(Usuario.SESION_ACTUAL.getPersona_barrio().getId()!=0){

            id_per=String.valueOf(Usuario.SESION_ACTUAL.getPersona_barrio().getId());
            id_Dep=String.valueOf(GestionUbigeo.CAPTACION_UBIGEO_BARRIO.getDepartamento().getCodigo());
            id_Prov=String.valueOf(GestionUbigeo.CAPTACION_UBIGEO_BARRIO.getProvincia().getCodigo());
            id_Dis=String.valueOf(GestionUbigeo.CAPTACION_UBIGEO_BARRIO.getDistrito().getCodigo());


            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Registro");
            progressDialog.setMessage("Enviando Información...");


        }else{
            if(Persona.PERSONA_TEMP.getId()!=0){
                id_per=String.valueOf(Persona.PERSONA_TEMP.getId());
                id_Dep=String.valueOf(GestionUbigeo.CAPTACION_UBIGEO_MASIVO.getDepartamento().getCodigo());
                id_Prov=String.valueOf(GestionUbigeo.CAPTACION_UBIGEO_MASIVO.getProvincia().getCodigo());
                id_Dis=String.valueOf(GestionUbigeo.CAPTACION_UBIGEO_MASIVO.getDistrito().getCodigo());

                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Registro");
                progressDialog.setMessage("Enviando Información...");

                Desactivar_Persona(Integer.parseInt(id_per));
                Actualizar_Estado_Capta(Integer.parseInt(id_per));
            }else{
                id_per=String.valueOf(base.getId_persona());
                id_Dep=String.valueOf(GestionUbigeo.CAPTACION_UBIGEO.getDepartamento().getCodigo());
                id_Prov=String.valueOf(GestionUbigeo.CAPTACION_UBIGEO.getProvincia().getCodigo());
                id_Dis=String.valueOf(GestionUbigeo.CAPTACION_UBIGEO.getDistrito().getCodigo());
            }

        }



        String id_user=String.valueOf(base.getId_usuario());
        String id_fisico=String.valueOf(base.getCampo_fisico_id());
        String id_capacidad=String.valueOf(base.getCampo_capacidad_id());
        String id_social=String.valueOf(base.getCampo_social_id());
        String id_tecnico=String.valueOf(base.getCampo_tecnico_id());
        String id_psico=String.valueOf(base.getCampo_psico_id());
        String id_sugerido1=String.valueOf(base.getSugerido_1().getId());
        String id_sugerido2=String.valueOf(base.getSugerido_2().getId());
        String id_sugerido3=String.valueOf(base.getSugerido_3().getId());
        String lateralidad=base.getLateralidad();

        debug("ENTRO REGISTRO DE MODULO");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        progressDialog.dismiss();


                        if(Usuario.SESION_ACTUAL.getPersona_barrio().getId()!=0){
                            Intent intent = new Intent(ValidarDiagnosticoIndividualActivity.this, BarrioIntimoPersonaActivity.class);
                            ValidarDiagnosticoIndividualActivity.this.startActivity(intent);

                            Actualizar_Estado_BARRIO(Usuario.SESION_ACTUAL.getPersona_barrio().getId());
                        }else{
                            Intent intent = new Intent(ValidarDiagnosticoIndividualActivity.this, PrincipalActivity.class);
                            intent.putExtra("o","o1");
                            ValidarDiagnosticoIndividualActivity.this.startActivity(intent);
                            Toast.makeText(context, "Registro Guardado con exito!", Toast.LENGTH_SHORT).show();

                            debug("PASO REGISTRO DE MODULO");



                        }

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error en Recupera codigo de usuario :"+e);
                }
            }
        };

        RegistrarModuloCapta xx = new RegistrarModuloCapta(id_per,id_Dep,id_Prov,id_Dis, id_user,id_fisico,id_capacidad,id_social,id_tecnico,id_psico,id_sugerido1,id_sugerido2,id_sugerido3,lateralidad,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    private void Actualizar_Estado_Capta(int i) {
        String id_persona=String.valueOf(i);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        debug("Persona CAPTA ACTIVAR");
                    }else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        ActivarEvaPersona validarSesion = new ActivarEvaPersona(id_persona, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(validarSesion);


    }


    private void Actualizar_Estado_BARRIO(int i) {

        debug("ENTRO A ACTUALIZAR ESTADO BARRIO");
        String id_persona=String.valueOf(i);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        debug("PASO Persona BARRIO ACTIVAR DIAGNOSTICO");
                    }else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        ActivarEvaPersonaBarrio validarSesion = new ActivarEvaPersonaBarrio(id_persona, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(validarSesion);


    }
    void debug(String mensaje){
        System.out.println(mensaje);
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
}
