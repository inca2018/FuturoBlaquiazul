package org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterJugadorBarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterJugadorCategoria;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterJugadorDisponible;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterJugadorDisponible2;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarDisponibles;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarJugadoresBarrio;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarJugadoresCategoria;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CategoriasJugadoresActivity extends AppCompatActivity {

    Context context;
    TextView nombre_cate,Fecha,total_jugadores;
    RecyclerView recyclerView1,recyclerView2;
    LinearLayoutManager linearLayoutManager,linearLayoutManager2;
    ProgressDialog progressDialog;
    RelativeLayout vacio1,vacio2;

    public static CategoriasJugadoresActivity GESTOR=new CategoriasJugadoresActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias_jugadores);
        context=this;
        nombre_cate=findViewById(R.id.ca_nom);
        Fecha=findViewById(R.id.ca_fecha);
        total_jugadores=findViewById(R.id.ca_total);
        recyclerView1=findViewById(R.id.ca_lista_categoria);
        recyclerView2=findViewById(R.id.ca_lista_disponible);
        vacio1=findViewById(R.id.lista_vacia_cod3);
        vacio2=findViewById(R.id.lista_vacia_cod4);

        if(Recursos_Mantenimientos.TEMP.getCategoria_temporal()!=null){
            nombre_cate.setText(Recursos_Mantenimientos.TEMP.getCategoria_temporal().getNombre_categoria());
            Fecha.setText(Recursos_Mantenimientos.TEMP.getCategoria_temporal().getFecha_registro());
        }

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE2 = new AdapterJugadorDisponible2(this, Recursos_Mantenimientos.LISTA_DISPONIBLE2, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
            }
        });
        recyclerView2.setAdapter( Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE2);
        recyclerView2.setLayoutManager(linearLayoutManager);


        Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE2.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if( Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE2.getItemCount()==0){
                    vacio2.setVisibility(View.VISIBLE);
                    recyclerView2.setVisibility(View.GONE);
                }else{
                    vacio2.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.VISIBLE);
                }
            }
        });



        linearLayoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        Recursos_Mantenimientos.ADAPTER_CATEGORIA = new AdapterJugadorCategoria(this, Recursos_Mantenimientos.LISTA_CATEGORIA, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
            }
        });
        recyclerView1.setAdapter( Recursos_Mantenimientos.ADAPTER_CATEGORIA);
        recyclerView1.setLayoutManager(linearLayoutManager2);


        Recursos_Mantenimientos.ADAPTER_CATEGORIA.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                if(Recursos_Mantenimientos.ADAPTER_CATEGORIA.getItemCount()==0){
                    total_jugadores.setText("0");
                    vacio1.setVisibility(View.VISIBLE);
                    recyclerView1.setVisibility(View.GONE);
                }else{
                    vacio1.setVisibility(View.GONE);
                    recyclerView1.setVisibility(View.VISIBLE);
                    total_jugadores.setText(String.valueOf(Recursos_Mantenimientos.ADAPTER_CATEGORIA.getItemCount()));
                }
            }
        });

        Listar_Categorias(Recursos_Mantenimientos.TEMP.getCategoria_temporal().getId(),context);
    }

    private void Listar_Categorias(int id,final Context context) {

        String id_plantel=String.valueOf(id);
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Categoria:");
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
                        JSONArray departamentos=jsonResponse.getJSONArray("jugadores_categoria");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            temp.setFoto(objeto.getString("FOTO"));
                            temp.setEstado(objeto.getInt("ESTADO"));
                            Recursos_Mantenimientos.LISTA_CATEGORIA.add(temp);
                            System.out.println(" CANTIDAD CATEGORIA: "+(i+1));
                        }

                        Recursos_Mantenimientos.ADAPTER_CATEGORIA.notifyDataSetChanged();

                        Listar_Personas_Disponibles(context);
                        System.out.println("LISTADO COMPLETO DEEVENTO");
                    } else {
                        Listar_Personas_Disponibles(context);
                        Recursos_Mantenimientos.ADAPTER_CATEGORIA.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarJugadoresCategoria xx = new RecuperarJugadoresCategoria(id_plantel,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    private void Listar_Personas_Disponibles(final Context context) {

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("jugadores_disponible");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            temp.setFoto(objeto.getString("FOTO"));

                            Recursos_Mantenimientos.LISTA_DISPONIBLE2.add(temp);
                        }

                        Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE2.notifyDataSetChanged();
                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DISPONIBLES");
                    } else {
                        Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE2.notifyDataSetChanged();
                        progressDialog.dismiss();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarDisponibles xx = new RecuperarDisponibles(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    public void Actualizar_Listas(Context context){
        Recursos_Mantenimientos.LISTA_DISPONIBLE2.clear();
        Recursos_Mantenimientos.LISTA_CATEGORIA.clear();
        Listar_Categorias(Recursos_Mantenimientos.TEMP.getCategoria_temporal().getId(),context);
    }

    public void onBackPressed() {
        Recursos_Mantenimientos.LISTA_DISPONIBLE2.clear();
        Recursos_Mantenimientos.LISTA_CATEGORIA.clear();
        Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE2=null;
        Recursos_Mantenimientos.ADAPTER_CATEGORIA=null;
        Intent intent=new Intent(CategoriasJugadoresActivity.this,MantenimientoCategoriasActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        CategoriasJugadoresActivity.this.startActivity(intent);

    }
}
