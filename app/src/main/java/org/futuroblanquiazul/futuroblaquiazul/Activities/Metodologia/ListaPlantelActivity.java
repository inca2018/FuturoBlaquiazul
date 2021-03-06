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

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPlantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Grupo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPlantel;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPlantel2;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaPlantelActivity extends AppCompatActivity {


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

            }
        });

        adapter2 = new AdapterPlantel(this, lista_escuela_competetiva, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

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
        progressDialog.setCanceledOnTouchOutside(false);
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
                            Grupo t=new Grupo();
                            t.setId(objeto.getInt("ID_RANGO"));
                            if(t.getId()==1){
                                t.setDescripcion("ESCUELA BASE");
                            }else if(t.getId()==2){
                                t.setDescripcion("ESCUELA COMPETETITIVA");
                            }
                            temp.setRango(t);
                            temp.setNombre_categoria(objeto.getString("NOMBRE_CATEGORIA"));
                            temp.setFecha_registro(objeto.getString("FECHA_REGISTRO"));
                            Usuario u=new Usuario();
                            u.setId(objeto.getInt("ID_USER"));
                            u.setUsuario(objeto.getString("USUARIO"));
                            temp.setUsuario(u);
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
                            Grupo t=new Grupo();
                            t.setId(objeto.getInt("ID_RANGO"));
                            if(t.getId()==1){
                                t.setDescripcion("ESCUELA BASE");
                            }else if(t.getId()==2){
                                t.setDescripcion("ESCUELA COMPETETITIVA");
                            }
                            temp.setRango(t);
                            temp.setNombre_categoria(objeto.getString("NOMBRE_CATEGORIA"));
                            temp.setFecha_registro(objeto.getString("FECHA_REGISTRO"));

                            Usuario u=new Usuario();
                            u.setId(objeto.getInt("ID_USER"));
                            u.setUsuario(objeto.getString("USUARIO"));
                            temp.setUsuario(u);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(context, PrincipalActivity.class);
        intent.putExtra("o","o3");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }
}
