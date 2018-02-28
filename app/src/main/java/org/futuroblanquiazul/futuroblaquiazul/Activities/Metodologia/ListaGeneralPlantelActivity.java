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
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPlantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.BarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarBarrios;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPlantel;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPlantel2;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaGeneralPlantelActivity extends AppCompatActivity {


    RecyclerView  recyclerView1,recyclerView2;

    private LinearLayoutManager linearLayout,linearLayout2;
    private AdapterPlantel adapter,adapter2;
    private List<Plantel> lista_escuela_base,lista_escuela_competetiva;
    Context context;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_general_plantel);
        recyclerView1=findViewById(R.id.recycler_plantel1);
        recyclerView2=findViewById(R.id.recycler_plantel2);
        context=this;

        lista_escuela_base=new ArrayList<>();
        lista_escuela_competetiva=new ArrayList<>();
        linearLayout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        linearLayout2 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapter = new AdapterPlantel(this, lista_escuela_base, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
                Plantel p=new Plantel();
                p.setId(lista_escuela_base.get(position).getId());
                Usuario.SESION_ACTUAL.setPlantel(p);
                Intent intent = new Intent(ListaGeneralPlantelActivity.this, ListaPersonasActivity.class);
                ListaGeneralPlantelActivity.this.startActivity(intent);
            }
        });

        adapter2 = new AdapterPlantel(this, lista_escuela_competetiva, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
                Plantel p=new Plantel();
                p.setId(lista_escuela_base.get(position).getId());
                Usuario.SESION_ACTUAL.setPlantel(p);
                Intent intent = new Intent(ListaGeneralPlantelActivity.this, ListaPersonasActivity.class);
                ListaGeneralPlantelActivity.this.startActivity(intent);
            }
        });


        recyclerView1.setAdapter(adapter);
        recyclerView1.setLayoutManager(linearLayout);

        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(linearLayout2);


        Listar_Plantel1(context);


    }

    private void Listar_Plantel1(final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Plantel:");
        progressDialog.setMessage("Listando...");
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("plantel1");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Plantel temp=new Plantel();
                            temp.setId(objeto.getInt("ID"));
                            temp.setRango(objeto.getInt("ID_RANGO"));
                            temp.setNombre_categoria(objeto.getString("NOMBRE_CATEGORIA"));
                            temp.setFecha_registro(objeto.getString("FECHA_REGISTRO"));
                            temp.setId_user(objeto.getInt("ID_USER"));
                            temp.setEstado(objeto.getInt("ESTADO"));

                            lista_escuela_base.add(temp);

                        }

                        adapter.notifyDataSetChanged();

                        listar_escuela_competetiva(context);


                        System.out.println("LISTADO COMPLETO DE BARRIO");
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

        RecuperarPlantel xx = new RecuperarPlantel(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }

    private void listar_escuela_competetiva(final Context context) {

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("plantel2");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Plantel temp=new Plantel();
                            temp.setId(objeto.getInt("ID"));
                            temp.setRango(objeto.getInt("ID_RANGO"));
                            temp.setNombre_categoria(objeto.getString("NOMBRE_CATEGORIA"));
                            temp.setFecha_registro(objeto.getString("FECHA_REGISTRO"));
                            temp.setId_user(objeto.getInt("ID_USER"));
                            temp.setEstado(objeto.getInt("ESTADO"));

                            lista_escuela_competetiva.add(temp);

                        }

                        adapter2.notifyDataSetChanged();

                        progressDialog.dismiss();


                        System.out.println("LISTADO COMPLETO DE BARRIO");
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

        RecuperarPlantel2 xx = new RecuperarPlantel2(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);






    }
}
