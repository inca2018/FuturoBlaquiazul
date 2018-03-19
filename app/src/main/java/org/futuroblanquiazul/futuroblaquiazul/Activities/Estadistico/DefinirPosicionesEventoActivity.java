package org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterEstadisticoPersonaNumero;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterEstadisticoPersonaPosicion;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterNumero;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPlantelEdicionFormacion;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPlantelEdicionFormacion2;
import org.futuroblanquiazul.futuroblaquiazul.Entity.EventoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Extras;
import org.futuroblanquiazul.futuroblaquiazul.Entity.NumeroDisponible;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Posicion;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasPlantel2;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPosiciones;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Estadistico;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefinirPosicionesEventoActivity extends AppCompatActivity {
    RecyclerView recyclerView1,recyclerView2;
    AdapterEstadisticoPersonaPosicion adapter1;
    AdapterEstadisticoPersonaNumero adapter2;
    AdapterNumero adapter3;
    ProgressDialog progressDialog;
    private LinearLayoutManager linearLayout,linearLayout2,linearlayout3;
    List<Persona> Lista_Persona_Numero,Lista_Persona_Posiciones;
    List<Integer> Lista_Numero,Lista_Numeros_No_Disponibles,Lista_Disponible;
    TextView equipo,evento;
    Context context;
    List<NumeroDisponible> Lista_Visible;
    RecyclerView recyclerView3;
    List<Posicion> ListaPosiciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definir_posiciones_evento);
        recyclerView1=findViewById(R.id.recycler_gestion_jugadores_posiciones);
        recyclerView2=findViewById(R.id.recycler_gestion_jugadores_numeros);
        Lista_Persona_Numero=new ArrayList<>();
        Lista_Numeros_No_Disponibles=new ArrayList<>();
        Lista_Persona_Posiciones=new ArrayList<>();
        Lista_Disponible=new ArrayList<>();
        equipo=findViewById(R.id.definir_extra_categoria);
        evento=findViewById(R.id.definir_extra_evento);
        recyclerView3=findViewById(R.id.recycler_numeros_disponibles);
        Lista_Visible=new ArrayList<>();
        ListaPosiciones=new ArrayList<>();
        context=this;

        Armar_Lista();

        linearLayout=new LinearLayoutManager(context, LinearLayout.VERTICAL,false);
        linearLayout2=new LinearLayoutManager(context, LinearLayout.VERTICAL,false);
        linearlayout3=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);


        adapter1 = new AdapterEstadisticoPersonaPosicion(this, Lista_Persona_Posiciones, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
            }
        });
        adapter2 = new AdapterEstadisticoPersonaNumero(this,Extras.LISTA_EXTRAS, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
            }
        });

        adapter3 = new AdapterNumero(this,Lista_Visible, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
            }
        });


        recyclerView1.setAdapter(adapter1);
        recyclerView1.setLayoutManager(linearLayout);

        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(linearLayout2);

        recyclerView3.setAdapter(adapter3);
        recyclerView3.setLayoutManager(linearlayout3);

        for(int i=1;i<=25;i++){
            NumeroDisponible temp=new NumeroDisponible();
            temp.setEstado(0);
            temp.setNumero(i);
            Lista_Visible.add(temp);
        }
        adapter3.notifyDataSetChanged();

        if(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal()!=null){
            equipo.setText(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getPlantel().getNombre_categoria());
            evento.setText(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getDescripcion_Nombre_evento());
            //Listar_Personas_Plantel_Posicion(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getPlantel().getId(), context);
            Listar_Posiciones(context);
        }else{
            equipo.setText("No Disponible");
        }


        adapter2.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                for(int i=0;i<Lista_Visible.size();i++){
                    if(Encontrado_en_Disponible(Lista_Visible.get(i))){
                        Lista_Visible.get(i).setEstado(1);
                    }else{
                        Lista_Visible.get(i).setEstado(0);
                    }
                }
                adapter3.notifyDataSetChanged();
            }
        });


    }
    private boolean Encontrado_en_Disponible(NumeroDisponible numeroDisponible) {
      boolean ff=false;
      for(int i=0;i<Recursos_Estadistico.LISTA_NUMEROS_SELECCIONADOS.size();i++){
          if(Recursos_Estadistico.LISTA_NUMEROS_SELECCIONADOS.get(i)==numeroDisponible.getNumero()){
            ff=true;
          }
      }
      return ff;
    }
    private boolean Encontro(int d) {
      boolean ff=false;

      for(int i=0;i<Lista_Numeros_No_Disponibles.size();i++){
          if(Lista_Numeros_No_Disponibles.get(i)==d){
              ff=true;
          }
      }

      return ff;
    }
    private void Ordenar(List<Integer> numeros_disponibles) {
        Collections.sort(numeros_disponibles);

    }
    private void Armar_Lista() {
        Lista_Numero=new ArrayList<>();
        for(int i=1;i<=25;i++){
            Lista_Numero.add(i);
            Lista_Disponible.add(i);
            NumeroDisponible temp=new NumeroDisponible();
            temp.setEstado(0);
            temp.setNumero(i);

        }

    }

    private void Listar_Personas_Plantel_Posicion(final int id,final Context context) {

        String id_plantel=String.valueOf(id);


        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray XX=jsonResponse.getJSONArray("personas2");
                        for(int i=0;i<XX.length();i++){
                            JSONObject objeto= XX.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            temp.setFoto(objeto.getString("FOTO"));
                            temp.setEstado_seleccion(0);
                            temp.setId_posicion(0);
                            temp.setLista_Posiciones(ListaPosiciones);
                            temp.setCodigo_posicion(0);
                            temp.setEstado_posicion(0);
                            Lista_Persona_Posiciones.add(temp);
                            //Recursos_Estadistico.LISTA_PERSONA_GENERAL.add(temp);
                        }
                        adapter1.notifyDataSetChanged();

                        System.out.println("LISTADO COMPLETO DE JUGADORES POSICION");
                        Listar_Personas_Plantel_Numero(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getPlantel().getId(), context);
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

        RecuperarPersonasPlantel2 xx = new RecuperarPersonasPlantel2(id_plantel,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);
    }
    private void Listar_Personas_Plantel_Numero(final int id,final Context context) {

        String id_plantel=String.valueOf(id);

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("personas2");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            temp.setFoto(objeto.getString("FOTO"));
                            temp.setNumero_camiseta(0);
                            temp.setEstado_seleccion(0);
                            temp.setNumeros_Disponibles(Lista_Numero);

                            Extras temporal=new Extras();
                            temporal.setPersona(temp);
                            temporal.setActividad(false);
                            temporal.setEstado(0);
                            temporal.setLista_Numeros(Lista_Numero);
                            temporal.setNumero_Camiseta(0);
                            temporal.setEstado2(0);
                            temporal.setNumero_Anterior(0);

                            Extras.LISTA_EXTRAS.add(temporal);

                            //Recursos_Estadistico.LISTA_PERSONA_GENERAL.add(temp);

                        }

                        adapter2.notifyDataSetChanged();

                        progressDialog.dismiss();
                        System.out.println("LISTADO COMPLETO DE JUGADORES Numero");
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

        RecuperarPersonasPlantel2 xx = new RecuperarPersonasPlantel2(id_plantel,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    private void Listar_Posiciones(final Context context) {
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Estadistico:");
                progressDialog.setMessage("Listando Jugadores...");
                progressDialog.show();

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("posiciones");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Posicion temp=new Posicion();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Posicione(objeto.getString("NOMBRE_POSICION"));
                            temp.setEstado(objeto.getInt("ESTADO"));
                            ListaPosiciones.add(temp);
                        }

                        System.out.println("LISTA DE POSICIONES OK");
                        Listar_Personas_Plantel_Posicion(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getPlantel().getId(), context);

                    } else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarPosiciones xx = new RecuperarPosiciones(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);
    }
    public void onBackPressed() {

        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("Posiciones de Equipo")
                .setMessage("¿Desea salir de la Gestión de Posiciones del Equipo?")
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Extras.LISTA_EXTRAS.clear();
                                Intent intent = new Intent(DefinirPosicionesEventoActivity.this,ListaEventosEstadisticosActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                DefinirPosicionesEventoActivity.this.startActivity(intent);
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
}
