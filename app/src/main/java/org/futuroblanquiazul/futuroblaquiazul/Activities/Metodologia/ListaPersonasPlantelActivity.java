package org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.PeriodicSync;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterMetodologiaPersona;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPlantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasPlantel;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPlantel;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaPersonasPlantelActivity extends AppCompatActivity {

    RecyclerView recycler;
    LinearLayoutManager linearLayout;
    AdapterMetodologiaPersona adapter;
    Context context;
    ProgressDialog progressDialog;

    List<Persona> lista_personas;
    TextView titulo_plantel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personas);
        context=this;
        recycler=findViewById(R.id.recycler_plantel_jugadores);
        lista_personas=new ArrayList<>();
        titulo_plantel=findViewById(R.id.titulo_plantel);
        linearLayout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);


        adapter = new AdapterMetodologiaPersona(this, lista_personas, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
        });

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(linearLayout);


        if(Usuario.SESION_ACTUAL.getPlantel().getId()!=0){
            Lista_jugadores(Usuario.SESION_ACTUAL.getPlantel().getId(),context);
            titulo_plantel.setText("Plantel : "+Usuario.SESION_ACTUAL.getPlantel().getNombre_categoria());
        }else{
            Toast.makeText(context, "Lista sin datos", Toast.LENGTH_SHORT).show();
        }

    }

    private void Lista_jugadores(int id,final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Plantel:");
        progressDialog.setMessage("Listando...");
        progressDialog.show();

        String id_plantel=String.valueOf(id);
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("personas");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            temp.setFoto(objeto.getString("FOTO"));

                            lista_personas.add(temp);

                        }

                        adapter.notifyDataSetChanged();


                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DE JUGADORES");
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Listado Vacio", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarPersonasPlantel xx = new RecuperarPersonasPlantel(id_plantel,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(context, ListaPlantelActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }
}
