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

import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.ListaPersonasGrupoPruebasActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PruebaNutricional;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PruebaPsicologico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PruebaTactica;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActualizarPruebaPsico;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActualizarPruebaTactico;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarPruebaPsicologica;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarPruebaTactica;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Diagnostico;
import org.json.JSONException;
import org.json.JSONObject;

public class PruebaTacticaActivity extends AppCompatActivity {

    EditText ataque,definicion,defensa,recuperacion;
    Button grabar;
    TextView Promedio;
    TextView persona;
    Context context;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_tactica);
        ataque=findViewById(R.id.prueba_tac_ataque);
        definicion=findViewById(R.id.prueba_tac_definicion);
        defensa=findViewById(R.id.prueba_tac_defensa);
        recuperacion=findViewById(R.id.prueba_tac_recuperacion);
        grabar=findViewById(R.id.prueba_tac_grabar);
        Promedio=findViewById(R.id.prueba_tac_promedio);
        persona=findViewById(R.id.prueba_tac_persona);
        context=this;

        Limpiar_entradas();
        variables();

        grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){

                    RegistrarPruebaTactica(Usuario.SESION_ACTUAL.getId(),Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas().getId(),PruebaTactica.PRUEBA_TACTICA,context);
                }

            }
        });


        if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){

            persona.setText(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas().getNombre_Persona()+" "+Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas().getApellidos_Persona());
        }


    }

    private void Limpiar_entradas() {
    ataque.setText("");
    definicion.setText("");
    defensa.setText("");
    recuperacion.setText("");
    Promedio.setText("0 Ptos.");



    }

    private void RegistrarPruebaTactica(final int user,final int persona, final PruebaTactica pruebaTactica,final  Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Prueba Tactica:");
        progressDialog.setMessage("Guardando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String id_user=String.valueOf(user);
        String id_persona=String.valueOf(persona);
        String ataque=String.valueOf(pruebaTactica.getAtaque());
        String definicion=String.valueOf(pruebaTactica.getDefinicion());
        String defensa=String.valueOf(pruebaTactica.getDefensa());
        String recuperacion=String.valueOf(pruebaTactica.getRecuperacion());
        String total_general=String.valueOf(pruebaTactica.getTotal_general());
        String estado=String.valueOf(1);


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {


                        if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){

                            int id_tactico=jsonResponse.getInt("id_tactico");

                            Actualizar_Tactico(id_tactico,Usuario.getSesionActual().getGrupoPruebasTEMP().getId(),Usuario.SESION_ACTUAL.getGrupoPruebasTEMP().getPlantel().getId(),Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas().getId());


                            Intent intent = new Intent(PruebaTacticaActivity.this,ListaPersonasGrupoPruebasActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            PruebaTacticaActivity.this.startActivity(intent);
                            Toast.makeText(context, "Prueba Tactica Registrada con exito!", Toast.LENGTH_SHORT).show();


                            Limpiar_entradas();
                        }


                    } else {
                        Toast.makeText(context, "No se pudo registrar Prueba Tactica", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error en Registrar Tactica :"+e);
                }
            }
        };
        RegistrarPruebaTactica xx = new RegistrarPruebaTactica(id_user,id_persona,ataque,definicion,defensa,recuperacion,total_general,estado, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);





    }

    private void variables() {
        ataque.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(String.valueOf(s).trim().length()!=0){
                    double valor= Double.parseDouble(String.valueOf(s));
                    PruebaTactica.PRUEBA_TACTICA.setAtaque(valor);
                    Calcular_Promedio();
                }else{
                    PruebaTactica.PRUEBA_TACTICA.setAtaque(0);
                    Calcular_Promedio();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        definicion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(String.valueOf(s).trim().length()!=0){
                    double valor= Double.parseDouble(String.valueOf(s));
                    PruebaTactica.PRUEBA_TACTICA.setDefinicion(valor);
                    Calcular_Promedio();
                }else{
                    PruebaTactica.PRUEBA_TACTICA.setDefinicion(0);
                    Calcular_Promedio();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        defensa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(String.valueOf(s).trim().length()!=0){
                    double valor= Double.parseDouble(String.valueOf(s));
                    PruebaTactica.PRUEBA_TACTICA.setDefensa(valor);
                    Calcular_Promedio();
                }else{
                    PruebaTactica.PRUEBA_TACTICA.setDefensa(0);
                    Calcular_Promedio();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        recuperacion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(String.valueOf(s).trim().length()!=0){
                    double valor= Double.parseDouble(String.valueOf(s));
                    PruebaTactica.PRUEBA_TACTICA.setRecuperacion(valor);
                    Calcular_Promedio();
                }else{
                    PruebaTactica.PRUEBA_TACTICA.setRecuperacion(0);
                    Calcular_Promedio();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void Calcular_Promedio() {


        if(PruebaTactica.PRUEBA_TACTICA.getAtaque()!=0 || PruebaTactica.PRUEBA_TACTICA.getDefinicion()!=0 || PruebaTactica.PRUEBA_TACTICA.getDefensa()!=0 || PruebaTactica.PRUEBA_TACTICA.getRecuperacion()!=0 ){

            double prom=(PruebaTactica.PRUEBA_TACTICA.getAtaque()+PruebaTactica.PRUEBA_TACTICA.getDefinicion()+PruebaTactica.PRUEBA_TACTICA.getDefensa()+PruebaTactica.PRUEBA_TACTICA.getRecuperacion())/4;
            double dd=Math.floor(prom);
            PruebaTactica.PRUEBA_TACTICA.setTotal_general(dd) ;

            Promedio.setText(dd+" Ptos.");
        }

    }

    public void onBackPressed() {

        if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){
            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
            builder.setTitle("Metodologia")
                    .setMessage("¿Desea salir de la Evaluaciòn Nutricional")
                    .setPositiveButton("SI",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(PruebaTacticaActivity.this,ListaPersonasGrupoPruebasActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    PruebaTacticaActivity.this.startActivity(intent);

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


    private void Actualizar_Tactico(int psico, int grupo, int plantel, int persona) {
        String id_psico=String.valueOf(psico);
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
                        System.out.println("TACTICO ACTUALIZADO");
                    }else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        ActualizarPruebaTactico xx = new ActualizarPruebaTactico(id_psico,id_grupo,id_plantel,id_persona, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }

}
