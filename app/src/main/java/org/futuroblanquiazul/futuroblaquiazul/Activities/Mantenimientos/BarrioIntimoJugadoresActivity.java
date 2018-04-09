package org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoPersonaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Ubigeo.UbigeoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterBarrio;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterJugadorBarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterJugadorDisponible;
import org.futuroblanquiazul.futuroblaquiazul.Entity.BarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.EventoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Actualizar_Ubigeo;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Desligar_Jugador;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Finalizar_Evento;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarBarrios;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarDisponibles;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarJugadoresBarrio;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BarrioIntimoJugadoresActivity extends AppCompatActivity {

    Context context;
    TextView nombre_evento,Fecha_Realizar,total_jugadores;
    RecyclerView recyclerView1,recyclerView2;
    LinearLayoutManager linearLayoutManager,linearLayoutManager2;
    ProgressDialog progressDialog;
    RelativeLayout vacio1,vacio2;
    Button finalizar_evento;

    TextView dis_text;
    LinearLayout dis_linear;
    CardView finalizar;
    public static BarrioIntimoJugadoresActivity GESTOR=new BarrioIntimoJugadoresActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barrio_intimo_jugadores);
        context=this;
        nombre_evento=findViewById(R.id.ge_barrio_nom);
        Fecha_Realizar=findViewById(R.id.ge_barrio_fecha);
        total_jugadores=findViewById(R.id.ge_barrio_total);
        recyclerView1=findViewById(R.id.ge_barrio_lista_evento);
        recyclerView2=findViewById(R.id.ge_barrio_disponible);
        vacio1=findViewById(R.id.lista_vacia_cod1);
        vacio2=findViewById(R.id.lista_vacia_cod2);
        finalizar_evento=findViewById(R.id.finalizar_evento);
        dis_text=findViewById(R.id.dis_text);
        dis_linear=findViewById(R.id.dis_linear);
        finalizar=findViewById(R.id.card_finalizar);

        if(Recursos_Mantenimientos.TEMP.getEvento_temporal()!=null){
            nombre_evento.setText(Recursos_Mantenimientos.TEMP.getEvento_temporal().getNombreEvento());
            Fecha_Realizar.setText(Recursos_Mantenimientos.TEMP.getEvento_temporal().getFechaRealizacion());

            if(Recursos_Mantenimientos.TEMP.getEvento_temporal().getEstado()==2){
                dis_text.setVisibility(View.GONE);
                dis_linear.setVisibility(View.GONE);
                finalizar.setEnabled(false);
                finalizar_evento.setEnabled(false);
            }else if(Recursos_Mantenimientos.TEMP.getEvento_temporal().getEstado()==1){
                dis_text.setVisibility(View.VISIBLE);
                dis_linear.setVisibility(View.VISIBLE);
                finalizar.setEnabled(true);
                finalizar_evento.setEnabled(true);
            }
        }

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE = new AdapterJugadorDisponible(this, Recursos_Mantenimientos.LISTA_DISPONIBLE, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
            }
        });
        recyclerView2.setAdapter( Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE);
        recyclerView2.setLayoutManager(linearLayoutManager);

        Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if( Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE.getItemCount()==0){
                    vacio2.setVisibility(View.VISIBLE);
                    recyclerView2.setVisibility(View.GONE);
                }else{
                    vacio2.setVisibility(View.GONE);
                    recyclerView2.setVisibility(View.VISIBLE);
                }
            }
        });

                linearLayoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        Recursos_Mantenimientos.ADAPTER_JUGADOR_BARRIO_INTIMO = new AdapterJugadorBarrioIntimo(this, Recursos_Mantenimientos.LISTA_PERSONA_BARRIO, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
            }
        });
        recyclerView1.setAdapter( Recursos_Mantenimientos.ADAPTER_JUGADOR_BARRIO_INTIMO);
        recyclerView1.setLayoutManager(linearLayoutManager2);


        Recursos_Mantenimientos.ADAPTER_JUGADOR_BARRIO_INTIMO.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                if(Recursos_Mantenimientos.ADAPTER_JUGADOR_BARRIO_INTIMO.getItemCount()==0){
                    total_jugadores.setText("0");
                    vacio1.setVisibility(View.VISIBLE);
                    recyclerView1.setVisibility(View.GONE);
                }else{
                    vacio1.setVisibility(View.GONE);
                    recyclerView1.setVisibility(View.VISIBLE);
                    total_jugadores.setText(String.valueOf(Recursos_Mantenimientos.ADAPTER_JUGADOR_BARRIO_INTIMO.getItemCount()));
                }
            }
        });

        Listar_Barrio_intimo(Recursos_Mantenimientos.TEMP.getEvento_temporal().getId(),context);

        finalizar_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setTitle("Finalizar")
                        .setMessage("¿Desea Finalizar Evento de Barrio Intimo? \n \n "+
                                    "-Se Bloqueara las funcionalidad de agregar nuevo Jugadores \n"+
                                    "-Se Desligaran Jugadores del Evento")
                        .setPositiveButton("SI",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                           Finalizar_Evento(Recursos_Mantenimientos.TEMP.getEvento_temporal().getId(),context);
                                    }
                                })
                        .setNegativeButton("NO",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                builder.show();

            }
        });
    }

    private void Finalizar_Evento(final int id,final Context context) {

        for(int i=0;i<Recursos_Mantenimientos.LISTA_PERSONA_BARRIO.size();i++){
                Desligar_Jugador(Recursos_Mantenimientos.LISTA_PERSONA_BARRIO.get(i).getId(),context);
            if(i==(Recursos_Mantenimientos.LISTA_PERSONA_BARRIO.size()-1)){
                Desligar_Jugador(Recursos_Mantenimientos.LISTA_PERSONA_BARRIO.get(i).getId(),context);
                Finalizar_Evento_Barrio(id,context);
            }
        }

    }

    private void Desligar_Jugador(final int id,final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Finalizar:");
        progressDialog.setMessage("Finalizando Evento...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String id_evento=String.valueOf(id);

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {


                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error actualizacion ubigeo :"+e);
                }
            }
        };

        Desligar_Jugador xx = new Desligar_Jugador(id_evento, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    private void Finalizar_Evento_Barrio(final int id,final Context context) {
        String id_evento=String.valueOf(id);

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                        progressDialog.dismiss();
                        Toast.makeText(context, "Evento Finalizado con exito!", Toast.LENGTH_SHORT).show();
                        Recursos_Mantenimientos.LISTA_DISPONIBLE.clear();
                        Recursos_Mantenimientos.LISTA_PERSONA_BARRIO.clear();
                        Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE=null;
                        Recursos_Mantenimientos.ADAPTER_JUGADOR_BARRIO_INTIMO=null;
                        Intent intent=new Intent(BarrioIntimoJugadoresActivity.this,MantenimientoBarrioIntimoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        BarrioIntimoJugadoresActivity.this.startActivity(intent);

                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error  :"+e);
                }
            }
        };

        Finalizar_Evento xx = new Finalizar_Evento(id_evento, responseListener);
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

                            Recursos_Mantenimientos.LISTA_DISPONIBLE.add(temp);
                        }

                        Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE.notifyDataSetChanged();
                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DISPONIBLES");
                    } else {
                        Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE.notifyDataSetChanged();
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
    private void Listar_Barrio_intimo(int d,final Context context) {

        String id_evento=String.valueOf(d);
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Evento:");
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
                        JSONArray departamentos=jsonResponse.getJSONArray("jugadores_evento_barrio");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            temp.setFoto(objeto.getString("FOTO"));
                            temp.setEstado(objeto.getInt("ESTADO"));
                            Recursos_Mantenimientos.LISTA_PERSONA_BARRIO.add(temp);
                        }

                        Recursos_Mantenimientos.ADAPTER_JUGADOR_BARRIO_INTIMO.notifyDataSetChanged();

                        Listar_Personas_Disponibles(context);
                        System.out.println("LISTADO COMPLETO DEEVENTO");
                    } else {
                        Listar_Personas_Disponibles(context); 
                        Recursos_Mantenimientos.ADAPTER_JUGADOR_BARRIO_INTIMO.notifyDataSetChanged();
                        progressDialog.dismiss();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarJugadoresBarrio xx = new RecuperarJugadoresBarrio(id_evento,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);
    }

    public void Actualizar_Listas(Context context){
        Recursos_Mantenimientos.LISTA_DISPONIBLE.clear();
        Recursos_Mantenimientos.LISTA_PERSONA_BARRIO.clear();
        Listar_Barrio_intimo(Recursos_Mantenimientos.TEMP.getEvento_temporal().getId(),context);
    }
    public void onBackPressed() {
        Recursos_Mantenimientos.LISTA_DISPONIBLE.clear();
        Recursos_Mantenimientos.LISTA_PERSONA_BARRIO.clear();
        Recursos_Mantenimientos.ADAPTER_JUGADOR_DISPONIBLE=null;
        Recursos_Mantenimientos.ADAPTER_JUGADOR_BARRIO_INTIMO=null;
        Intent intent=new Intent(BarrioIntimoJugadoresActivity.this,MantenimientoBarrioIntimoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        BarrioIntimoJugadoresActivity.this.startActivity(intent);

    }

}
