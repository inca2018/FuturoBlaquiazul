package org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterGrupoPrueba;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterJugadoresGrupoPruebas;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterMasivoPersona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPlantelPersonas;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPlantelPersonasPruebas;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListaPersonasGrupoPruebasActivity extends AppCompatActivity {

    LinearLayoutManager linearLayoutManager;
    RecyclerView recycler_jugadores_grupo_pruebas;
    AdapterJugadoresGrupoPruebas adapter;
    List<Persona> lista_personas;
    Context context;
    ProgressDialog progressDialog;
    TextView desc,estado,obser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personas_grupo_pruebas);
        lista_personas=new ArrayList<>();
        context=this;
        desc=findViewById(R.id.info_grupo_pruebas_descripcion);
        estado=findViewById(R.id.info_grupo_pruebas_estado);
        obser=findViewById(R.id.info_grupo_pruebas_observaciones);

        recycler_jugadores_grupo_pruebas=findViewById(R.id.lista_jugadores_grupo_pruebas);
        linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

        adapter = new AdapterJugadoresGrupoPruebas(this, lista_personas, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {


            }
        });

        recycler_jugadores_grupo_pruebas.setAdapter(adapter);
        recycler_jugadores_grupo_pruebas.setLayoutManager(linearLayoutManager);



       if(Usuario.SESION_ACTUAL.getGrupoPruebasTEMP()!=null){
           desc.setText(Usuario.SESION_ACTUAL.getGrupoPruebasTEMP().getDescripcion());
           obser.setText(Usuario.SESION_ACTUAL.getGrupoPruebasTEMP().getObservaciones());
           if(Usuario.SESION_ACTUAL.getGrupoPruebasTEMP().getEstado()==1){
               estado.setText("HABILITADO");
               estado.setTextColor(getResources().getColor(R.color.verde));
           }else{
               estado.setText("DESHABILITADO");
               estado.setTextColor(getResources().getColor(R.color.red));
           }
           Lista_Personas_Grupo_Pruebas(Usuario.SESION_ACTUAL.getGrupoPruebasTEMP().getPlantel().getId(),context);
       }else{
           Toast.makeText(context, "No se encontro informacion", Toast.LENGTH_SHORT).show();
       }



    }

    private void Lista_Personas_Grupo_Pruebas(int id,final Context context) {

        String id_plantel=String.valueOf(id);

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Plantel:");
        progressDialog.setMessage("Listando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("personas_grupo_pruebas");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));

                            lista_personas.add(temp);

                            adapter.notifyDataSetChanged();
                            progressDialog.dismiss();

                        }


                    } else {

                        Toast.makeText(context, "Error de conexion grupos pruebas", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar grupo pruebas :"+e);
                }
            }
        };


        RecuperarPlantelPersonasPruebas xx = new RecuperarPlantelPersonasPruebas(id_plantel,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(context, ListaGrupoPruebasActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }
}
