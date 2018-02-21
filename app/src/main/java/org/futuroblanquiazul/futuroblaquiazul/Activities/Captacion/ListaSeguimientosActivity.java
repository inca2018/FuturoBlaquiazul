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

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterSeguimientoPersona;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterSeguimientos;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Seguimiento;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasSeguimiento;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarSeguimientos;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ListaSeguimientosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private LinearLayoutManager linearLayout;
    private AdapterSeguimientos adapter;
    private List<Seguimiento> lista_seguimientos;
    Context context;
    ProgressDialog progressDialog;
    CardView nuevo_seguimiento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_seguimientos_persona);
        lista_seguimientos=new ArrayList<>();
        recyclerView=findViewById(R.id.Recycler_Seguimientos_lista);
        nuevo_seguimiento=findViewById(R.id.nuevo_seguimiento);
        context=this;
        linearLayout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        if(Usuario.SESION_ACTUAL.getId_persona()!=0){
           // Seguimientos_de_Persona(Usuario.SESION_ACTUAL.getId_persona(),context);
        }else{
            Toast.makeText(context, "No hay informacion del postulante", Toast.LENGTH_SHORT).show();
        }

        adapter = new AdapterSeguimientos(this, lista_seguimientos, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayout);


        nuevo_seguimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaSeguimientosActivity.this,SeguimientoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ListaSeguimientosActivity.this.startActivity(intent);

            }
        });
    }
    private void Seguimientos_de_Persona(final int id_persona2,final Context context) {
        String id_persona= String.valueOf(id_persona2);

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Seguimiento:");
        progressDialog.setMessage("Listando...");
        progressDialog.show();

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
                            Seguimiento temp=new Seguimiento();

                            lista_seguimientos.add(temp);
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
                    System.out.println("Inca  : Error de conexion al recuperar Seguimientos :"+e);
                }
            }
        };

        RecuperarSeguimientos xx = new RecuperarSeguimientos(id_persona,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);




    }


    public void onBackPressed() {
        Intent intent = new Intent(ListaSeguimientosActivity.this,ListaPersonaSeguimientoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ListaSeguimientosActivity.this.startActivity(intent);
    }


}
