package org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia_Fase_Prueba;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterMetodologiaPersona;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterMetodologiaPersonaPrueba;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasPlantel;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasPrueba;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaPersonasFasePruebaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private List<Persona> Lista_Fase_Prueba;
    LinearLayoutManager linearLayout;
    AdapterMetodologiaPersonaPrueba adapter;
    Context context;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personas_fase_prueba);
        recyclerView=findViewById(R.id.recycler_fase_prueba);
        context=this;

        Lista_Fase_Prueba=new ArrayList<>();
        linearLayout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);


        adapter = new AdapterMetodologiaPersonaPrueba(this, Lista_Fase_Prueba, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayout);


        listar_personas_prueba(context);
    }

    private void listar_personas_prueba(final Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Metodologia - Fase Pruebas:");
        progressDialog.setMessage("Listando Postulantes...");
        progressDialog.show();


        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray personas=jsonResponse.getJSONArray("persona_pruebas");
                        for(int i=0;i<personas.length();i++){
                            JSONObject objeto= personas.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            temp.setFecha_Ultima_modificacion(objeto.getString("FECHA_ACTUALIZACION"));
                            Lista_Fase_Prueba.add(temp);
                            System.out.println(" Persona ID:"+temp.getId());

                        }

                        adapter.notifyDataSetChanged();


                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DE JUGADORES EN FASE PRUEBA"+Lista_Fase_Prueba.size());
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Relacion de Postulantes en Fase Prueba Vacio", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar fase prueba :"+e);
                }
            }
        };

        RecuperarPersonasPrueba xx = new RecuperarPersonasPrueba(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }

    public void onBackPressed() {

        if(Usuario.SESION_ACTUAL.getPersona_fase_pruebas()!=null){
            Intent intent = new Intent(ListaPersonasFasePruebaActivity.this,PrincipalActivity.class);
            intent.putExtra("o","o3");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ListaPersonasFasePruebaActivity.this.startActivity(intent);
        }


    }
}
