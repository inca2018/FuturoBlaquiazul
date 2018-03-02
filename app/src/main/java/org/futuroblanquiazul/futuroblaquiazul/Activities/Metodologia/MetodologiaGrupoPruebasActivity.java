package org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia;

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

import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoPersonaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterBarrio;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterGrupoPrueba;
import org.futuroblanquiazul.futuroblaquiazul.Entity.GrupoPruebas;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarGrupoPruebas;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasPlantel;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MetodologiaGrupoPruebasActivity extends AppCompatActivity {
    RecyclerView lista_tiempos;
    LinearLayoutManager linearLayoutManager;
    AdapterGrupoPrueba adapter;
    Context context;
    ProgressDialog progressDialog;

    private List<GrupoPruebas> lista_grupo_pruebas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pruebas_tiempo);
        lista_grupo_pruebas=new ArrayList<>();
        context=this;
        lista_tiempos=findViewById(R.id.lista_tiempos);
        linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

        adapter = new AdapterGrupoPrueba(this, lista_grupo_pruebas, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
        });

        lista_tiempos.setAdapter(adapter);
        lista_tiempos.setLayoutManager(linearLayoutManager);


        if(Usuario.SESION_ACTUAL.getPlantel()!=null){
            Listar_Grupo_Pruebas(Usuario.SESION_ACTUAL.getPlantel().getId(),context);
        }else{
            Toast.makeText(context, "No Hay informacion de Plantel", Toast.LENGTH_SHORT).show();
        }

    }

    private void Listar_Grupo_Pruebas(int id_p,final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Plantel:");
        progressDialog.setMessage("Listando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String id_plantel=String.valueOf(id_p);
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("grupo_pruebas");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            GrupoPruebas temp=new GrupoPruebas();
                            temp.setId(objeto.getInt("ID"));
                            Usuario u=new Usuario();
                            u.setId(objeto.getInt("ID_USER"));
                            temp.setUsuario(u);
                            temp.setDescripcion(objeto.getString("DESCRIPCION"));
                            temp.setObservaciones(objeto.getString("OBSERVACIONES"));
                            temp.setFecha_registro(objeto.getString("FECHA_REGISTRO"));
                            temp.setFecha_realizacion(objeto.getString("FECHA_REALIZAR"));
                            Plantel p=new Plantel();
                            p.setId(objeto.getInt("ID_PLANTEL"));
                            temp.setPlantel(p);
                            temp.setEstado(objeto.getInt("ESTADO"));

                            lista_grupo_pruebas.add(temp);

                        }

                        adapter.notifyDataSetChanged();


                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DE GRUPO DE PRUEBAS");
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Listado Vacio de Grupo de Pruebas", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar Grupo de pruebas :"+e);
                }
            }
        };

        RecuperarGrupoPruebas xx = new RecuperarGrupoPruebas(id_plantel,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(context, ListaPersonasPlantelActivity.class);
        intent.putExtra("o","o3");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }
}
