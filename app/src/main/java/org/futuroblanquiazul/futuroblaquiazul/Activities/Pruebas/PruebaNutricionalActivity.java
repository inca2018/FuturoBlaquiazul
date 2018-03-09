package org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoPersonaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.ListaPersonasGrupoPruebasActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia_Fase_Prueba.GestionPersonaFasePruebaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PruebaNutricional;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PruebaTactica;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActualizarPruebaNutricional;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActualizarPruebaTactico;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarFasePrueba;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarPruebaNutricion;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarPruebaTactica;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONException;
import org.json.JSONObject;

public class PruebaNutricionalActivity extends AppCompatActivity {

    EditText IMC,GRASO,AKS;
    TextView Promedio;
    Button grabar;
    TextView persona;
    Context context;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_nutricional);
        IMC=findViewById(R.id.prueba_nutri_imc);
        GRASO=findViewById(R.id.prueba_nutri_graso);
        AKS=findViewById(R.id.prueba_nutri_aks);
        Promedio=findViewById(R.id.prueba_nutri_promedio);
        grabar=findViewById(R.id.prueba_nutri_grabar);
        persona=findViewById(R.id.prueba_nutri_persona);
        context=this;

        Limpiar_Entradas();

        variables();

        grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
                    RegistrarPruebaNutricional(Usuario.SESION_ACTUAL.getId(),Usuario.SESION_ACTUAL.getPersona_fase_pruebas().getId(), PruebaNutricional.PRUEBA_NUTRICIONAL,context);
                }else{
                    if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){

                        RegistrarPruebaNutricional(Usuario.SESION_ACTUAL.getId(),Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas().getId(), PruebaNutricional.PRUEBA_NUTRICIONAL,context);
                    }

                }


            }
        });


        if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){

            persona.setText(Usuario.SESION_ACTUAL.getPersona_fase_pruebas().getNombre_Persona()+" "+Usuario.SESION_ACTUAL.getPersona_fase_pruebas().getApellidos_Persona());
        }else{
            if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){

                persona.setText(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas().getNombre_Persona()+" "+Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas().getApellidos_Persona());
            }
        }

    }

    private void Limpiar_Entradas() {
    IMC.setText("");
    GRASO.setText("");
    AKS.setText("");
    Promedio.setText("0 Ptos.");

    }

    private void RegistrarPruebaNutricional(final int user, final int persona,final  PruebaNutricional pruebaNutricional,final  Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Prueba Tactica:");
        progressDialog.setMessage("Guardando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String id_user=String.valueOf(user);
        String id_persona=String.valueOf(persona);
        String imc=String.valueOf(pruebaNutricional.getIMC());
        String graso=String.valueOf(pruebaNutricional.getGraso());
        String aks=String.valueOf(pruebaNutricional.getAks());

        String total_general=String.valueOf(pruebaNutricional.getTotal_general());
        String estado=String.valueOf(1);


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
                            int id_nutricional=jsonResponse.getInt("id_nutricional");


                            Registrar_Fase_Prueba(Usuario.SESION_ACTUAL.getId(),Usuario.SESION_ACTUAL.getPersona_fase_pruebas().getId(),Usuario.SESION_ACTUAL.getTipoPruebas().getId(),id_nutricional,context);

                            Intent intent = new Intent(PruebaNutricionalActivity.this,GestionPersonaFasePruebaActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            PruebaNutricionalActivity.this.startActivity(intent);
                            Toast.makeText(context, "Prueba Nutricional Registrada con exito!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Limpiar_Entradas();
                        }else{
                            if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){

                                int id_nutricional=jsonResponse.getInt("id_nutricional");
                                Actualizar_Nutricional(id_nutricional,Usuario.getSesionActual().getGrupoPruebasTEMP().getId(),Usuario.SESION_ACTUAL.getGrupoPruebasTEMP().getPlantel().getId(),Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas().getId());

                                Intent intent = new Intent(PruebaNutricionalActivity.this,ListaPersonasGrupoPruebasActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                PruebaNutricionalActivity.this.startActivity(intent);
                                Toast.makeText(context, "Prueba Nutricional Registrada con exito!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Limpiar_Entradas();
                            }
                        }




                    } else {
                        Toast.makeText(context, "No se pudo registrar Prueba Nutricional", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                    System.out.println("Inca  : Error en Registrar Nutricional :"+e);
                }
            }
        };
        RegistrarPruebaNutricion xx = new RegistrarPruebaNutricion(id_user,id_persona,imc,graso,aks,total_general,estado, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);



    }

    private void variables() {
        IMC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(String.valueOf(s).trim().length()!=0){
                    double valor= Double.parseDouble(String.valueOf(s));
                    PruebaNutricional.PRUEBA_NUTRICIONAL.setIMC(valor);
                    Calcular_Promedio();
                }else{
                    PruebaNutricional.PRUEBA_NUTRICIONAL.setIMC(0);
                    Calcular_Promedio();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        GRASO.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(String.valueOf(s).trim().length()!=0){
                    double valor= Double.parseDouble(String.valueOf(s));
                    PruebaNutricional.PRUEBA_NUTRICIONAL.setGraso(valor);
                    Calcular_Promedio();
                }else{
                    PruebaNutricional.PRUEBA_NUTRICIONAL.setGraso(0);
                    Calcular_Promedio();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        AKS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(String.valueOf(s).trim().length()!=0){
                    double valor= Double.parseDouble(String.valueOf(s));
                    PruebaNutricional.PRUEBA_NUTRICIONAL.setAks(valor);
                    Calcular_Promedio();
                }else{
                    PruebaNutricional.PRUEBA_NUTRICIONAL.setAks(0);
                    Calcular_Promedio();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
    private void Calcular_Promedio() {

          if(PruebaNutricional.PRUEBA_NUTRICIONAL.getIMC()!=0 || PruebaNutricional.PRUEBA_NUTRICIONAL.getAks()!=0 || PruebaNutricional.PRUEBA_NUTRICIONAL.getGraso()!=0){

              double prom=(PruebaNutricional.PRUEBA_NUTRICIONAL.getAks()+PruebaNutricional.PRUEBA_NUTRICIONAL.getIMC()+PruebaNutricional.PRUEBA_NUTRICIONAL.getGraso())/3;
              double dd=Math.floor(prom);
              PruebaNutricional.PRUEBA_NUTRICIONAL.setTotal_general(dd);

              Promedio.setText(dd+" Ptos.");
          }


    }


    public void onBackPressed() {

        if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
            builder.setTitle("Metodologia")
                    .setMessage("¿Desea salir de la Evaluación  Nutricional?")
                    .setPositiveButton("SI",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(PruebaNutricionalActivity.this,GestionPersonaFasePruebaActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    PruebaNutricionalActivity.this.startActivity(intent);
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
                        .setMessage("¿Desea salir de la Evaluaciòn Nutricional")
                        .setPositiveButton("SI",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(PruebaNutricionalActivity.this,ListaPersonasGrupoPruebasActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        PruebaNutricionalActivity.this.startActivity(intent);

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


    private void Actualizar_Nutricional(int psico, int grupo, int plantel, int persona) {
        String id_nutri=String.valueOf(psico);
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
                        System.out.println("NUTRICIONAL ACTUALIZADO");
                    }else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        ActualizarPruebaNutricional xx = new ActualizarPruebaNutricional(id_nutri,id_grupo,id_plantel,id_persona, responseListener);
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
