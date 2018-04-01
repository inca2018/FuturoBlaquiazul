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

import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterJugadorCategoria;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterJugadorDisponible2;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterJugadorDisponible3;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterJugadorEquipo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarDisponibles;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarDisponibles2;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarJugadoresCategoria;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarJugadoresEquipo;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EquipoJugadoresActivity extends AppCompatActivity {

    Context context;
    TextView nombre_cate,Fecha,total_jugadores;
    RecyclerView recyclerView1,recyclerView2;
    LinearLayoutManager linearLayoutManager,linearLayoutManager2;
    ProgressDialog progressDialog;
    RelativeLayout vacio1,vacio2;

    public static EquipoJugadoresActivity GESTOR=new EquipoJugadoresActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipo_jugadores);

        context=this;
        nombre_cate=findViewById(R.id.eq_nombre);
        Fecha=findViewById(R.id.eq_fecha_Reg);
        total_jugadores=findViewById(R.id.eq_total);
        recyclerView1=findViewById(R.id.eq_lista_equipo);
        recyclerView2=findViewById(R.id.eq_lista_disponible);
        vacio1=findViewById(R.id.lista_vacia_cod5);
        vacio2=findViewById(R.id.lista_vacia_cod6);

        if(Recursos_Mantenimientos.TEMP.getEquipo_temporal()!=null){
            nombre_cate.setText(Recursos_Mantenimientos.TEMP.getEquipo_temporal().getNombre_equipo());
            Fecha.setText(Recursos_Mantenimientos.TEMP.getEquipo_temporal().getFecha_registro());
        }

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE3 = new AdapterJugadorDisponible3(this, Recursos_Mantenimientos.LISTA_DISPONIBLE3, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
            }
        });
        recyclerView2.setAdapter( Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE3);
        recyclerView2.setLayoutManager(linearLayoutManager);


        Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE3.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if( Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE3.getItemCount()==0){
                    vacio2.setVisibility(View.VISIBLE);
                    recyclerView2.setVisibility(View.GONE);
                }else{
                    vacio2.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.VISIBLE);
                }
            }
        });



        linearLayoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        Recursos_Mantenimientos.ADAPTER_EQUIPO = new AdapterJugadorEquipo(this, Recursos_Mantenimientos.LISTA_EQUIPO, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
            }
        });
        recyclerView1.setAdapter( Recursos_Mantenimientos.ADAPTER_EQUIPO);
        recyclerView1.setLayoutManager(linearLayoutManager2);


        Recursos_Mantenimientos.ADAPTER_EQUIPO.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                if(Recursos_Mantenimientos.ADAPTER_EQUIPO.getItemCount()==0){
                    total_jugadores.setText("0");
                    vacio1.setVisibility(View.VISIBLE);
                    recyclerView1.setVisibility(View.GONE);
                }else{
                    vacio1.setVisibility(View.GONE);
                    recyclerView1.setVisibility(View.VISIBLE);
                    total_jugadores.setText(String.valueOf(Recursos_Mantenimientos.ADAPTER_EQUIPO.getItemCount()));
                }
            }
        });

        Listar_Equipos(Recursos_Mantenimientos.TEMP.getEquipo_temporal().getId(),context);

    }

    private void Listar_Equipos(int id,final Context context) {

        String id_equipo=String.valueOf(id);
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Equipo:");
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
                        JSONArray xx=jsonResponse.getJSONArray("equipos_jugadores");
                        for(int i=0;i<xx.length();i++){
                            JSONObject objeto= xx.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            temp.setCategoria_Actual(objeto.getString("NOMBRE_CATEGORIA"));
                            temp.setFoto(objeto.getString("FOTO"));
                            temp.setEstado(objeto.getInt("ESTADO"));
                            Recursos_Mantenimientos.LISTA_EQUIPO.add(temp);

                        }

                        Recursos_Mantenimientos.ADAPTER_EQUIPO.notifyDataSetChanged();

                        Listar_Personas_Disponibles(context);
                        System.out.println("LISTADO COMPLETO DEEVENTO");
                    } else {
                        Listar_Personas_Disponibles(context);
                        Recursos_Mantenimientos.ADAPTER_EQUIPO.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarJugadoresEquipo xx = new RecuperarJugadoresEquipo(id_equipo,responseListener);
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
                        JSONArray departamentos=jsonResponse.getJSONArray("personas_metodologia");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            temp.setFoto(objeto.getString("FOTO"));
                            temp.setCategoria_Actual(objeto.getString("NOMBRE_CATEGORIA"));

                            Recursos_Mantenimientos.LISTA_DISPONIBLE3.add(temp);
                        }

                        Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE3.notifyDataSetChanged();
                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DISPONIBLES");
                    } else {
                        Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE3.notifyDataSetChanged();
                        progressDialog.dismiss();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarDisponibles2 xx = new RecuperarDisponibles2(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }


    public void Actualizar_Listas(Context context){
        Recursos_Mantenimientos.LISTA_DISPONIBLE3.clear();
        Recursos_Mantenimientos.LISTA_EQUIPO.clear();
        Listar_Equipos(Recursos_Mantenimientos.TEMP.getEquipo_temporal().getId(),context);
    }

    public void onBackPressed() {
        Recursos_Mantenimientos.LISTA_DISPONIBLE3.clear();
        Recursos_Mantenimientos.LISTA_EQUIPO.clear();
        Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE3=null;
        Recursos_Mantenimientos.ADAPTER_EQUIPO=null;
        Intent intent=new Intent(EquipoJugadoresActivity.this,MantenimientoEquipoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        EquipoJugadoresActivity.this.startActivity(intent);

    }

}
