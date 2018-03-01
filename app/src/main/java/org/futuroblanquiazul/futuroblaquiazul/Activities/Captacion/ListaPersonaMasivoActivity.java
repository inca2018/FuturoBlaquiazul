package org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


import org.futuroblanquiazul.futuroblaquiazul.Activities.CaptacionMasiva.ListaMasivosActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.CaptacionMasiva.PersonaNuevoMasivoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterMasivoPersona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonas;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaPersonaMasivoActivity extends AppCompatActivity {


    private RecyclerView recycler_masivo_postulante;
    private LinearLayoutManager linearLayout;
    private AdapterMasivoPersona adapter;
    private List<Persona> lista_personas;
    Context context;
    ProgressDialog progressDialog;

    CardView nuevo_masivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_masivo_lista_persona);
        recycler_masivo_postulante=findViewById(R.id.Recylcer_Masivos_postulante);
        nuevo_masivo=findViewById(R.id.nuevo_masivo_postulante);
        lista_personas=new ArrayList<>();

        linearLayout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        listar_persona(context);

        adapter = new AdapterMasivoPersona(this, lista_personas, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
                //Toast.makeText(context, "CLick id:"+lista_personas.get(position).getId(), Toast.LENGTH_SHORT).show();

            }
        });

        recycler_masivo_postulante.setAdapter(adapter);
        recycler_masivo_postulante.setLayoutManager(linearLayout);


        nuevo_masivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaPersonaMasivoActivity.this, PersonaNuevoMasivoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ListaPersonaMasivoActivity.this.startActivity(intent);
                finish();
            }
        });
    }

    private void listar_persona(final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Postulantes:");
        progressDialog.setMessage("Listando...");
        progressDialog.show();

        String id_masivo=String.valueOf(Usuario.SESION_ACTUAL.getId_masivo());
        System.out.println("Id_ masivo :"+id_masivo);
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray masivo=jsonResponse.getJSONArray("masivos_personas");
                        for(int i=0;i<masivo.length();i++){
                            JSONObject objeto= masivo.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            temp.setEstado(objeto.getInt("ESTADO"));
                            temp.setEstado_capta(objeto.getInt("ESTADO_DIAGNOSTICO"));
                            temp.setDisponible(objeto.getInt("DISPONIBLE"));

                            lista_personas.add(temp);

                        }

                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DE MASIVOS");
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
        RecuperarPersonas xx = new RecuperarPersonas(id_masivo,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);
    }

    public void onBackPressed() {

        Intent intent = new Intent(ListaPersonaMasivoActivity.this,ListaMasivosActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ListaPersonaMasivoActivity.this.startActivity(intent);
        finish();


    }


}
