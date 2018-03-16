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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterEstadisticoPersonaNumero;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterEstadisticoPersonaPosicion;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPlantelEdicionFormacion;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPlantelEdicionFormacion2;
import org.futuroblanquiazul.futuroblaquiazul.Entity.EventoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Extras;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasPlantel2;
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
    ProgressDialog progressDialog;
    private LinearLayoutManager linearLayout,linearLayout2;
    List<Persona> Lista_Persona_Numero,Lista_Persona_Posiciones;
    List<Integer> Lista_Numero;
    TextView equipo,evento;
    Context context;
    Extras Temporal;
    int num_temporal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definir_posiciones_evento);

        recyclerView1=findViewById(R.id.recycler_gestion_jugadores_posiciones);
        recyclerView2=findViewById(R.id.recycler_gestion_jugadores_numeros);
        Lista_Persona_Numero=new ArrayList<>();
        Lista_Persona_Posiciones=new ArrayList<>();
        equipo=findViewById(R.id.definir_extra_categoria);
        evento=findViewById(R.id.definir_extra_evento);
        context=this;

        Armar_Lista();

        linearLayout=new LinearLayoutManager(context, LinearLayout.VERTICAL,false);
        linearLayout2=new LinearLayoutManager(context, LinearLayout.VERTICAL,false);


        adapter1 = new AdapterEstadisticoPersonaPosicion(this, Lista_Persona_Posiciones, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
            }
        });
        adapter2 = new AdapterEstadisticoPersonaNumero(this,Extras.LISTA_EXTRAS, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

                    Temporal=adapter2.Recuperar_Modificado();

            }
        });


        recyclerView1.setAdapter(adapter1);
        recyclerView1.setLayoutManager(linearLayout);

        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(linearLayout2);

        if(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal()!=null){
            equipo.setText(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getPlantel().getNombre_categoria());
            evento.setText(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getDescripcion_Nombre_evento());
            Listar_Personas_Plantel_Posicion(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getPlantel().getId(), context);
        }else{
            equipo.setText("No Disponible");
        }


        adapter2.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();

                  int accion=adapter2.getAccion();
                  if(accion==1){
                      System.out.println("ACTUALIZAR");



                  }else if(accion==2){
                      System.out.println("NUEVO");

                      Temporal=adapter2.getPersona_temp();
                      num_temporal=adapter2.getNumero_seleccion();

                      for(int i=0;i<Lista_Numero.size();i++){
                          if(Lista_Numero.get(i)==num_temporal){
                              Lista_Numero.remove(i);
                          }
                      }
                      for(int i=0;i<Extras.LISTA_EXTRAS.size();i++){
                              Extras.LISTA_EXTRAS.get(i).setLista_Numeros(Lista_Numero);
                              Ordenar(Extras.LISTA_EXTRAS.get(i).getLista_Numeros());
                      }

                      for(int i=0;i<Extras.LISTA_EXTRAS.size();i++){
                          if(Extras.LISTA_EXTRAS.get(i).getPersona().getId()==Temporal.getPersona().getId()){
                              Agregar_Numero(Extras.LISTA_EXTRAS.get(i),num_temporal);
                              Extras.LISTA_EXTRAS.get(i).setNumero_Camiseta(num_temporal);
                              Extras.LISTA_EXTRAS.get(i).setEstado2(1);
                          }

                      }

                      adapter2.setAccion(0);
                      adapter2.setNumero_seleccion(0);
                      adapter2.setPersona_temp(null);
                      adapter2.notifyDataSetChanged();
                  }



            }
        });


    }

    private void Agregar_Numero(Extras extras, int num_temporal) {

         extras.getLista_Numeros().add(num_temporal);
         Ordenar(extras.getLista_Numeros());
    }

    private void Eliminar_Numero(Extras extras, int num_temporal) {

       for(int i=0;i<extras.getLista_Numeros().size();i++){
           if(extras.getLista_Numeros().get(i)==num_temporal){
               extras.getLista_Numeros().remove(i);
           }else{
               System.out.println("Num No eliminado "+extras.getLista_Numeros().get(i));
           }
       }

       Ordenar(extras.getLista_Numeros());

    }

    private void Ordenar(List<Integer> numeros_disponibles) {
        Collections.sort(numeros_disponibles);

    }

    private void Armar_Lista() {
        Lista_Numero=new ArrayList<>();
        Lista_Numero.add(1);
        Lista_Numero.add(2);
        Lista_Numero.add(3);
        Lista_Numero.add(4);
        Lista_Numero.add(5);
        Lista_Numero.add(6);
        Lista_Numero.add(7);
        Lista_Numero.add(8);
        Lista_Numero.add(9);
        Lista_Numero.add(10);
        Lista_Numero.add(11);
        Lista_Numero.add(12);
        Lista_Numero.add(13);
        Lista_Numero.add(14);
        Lista_Numero.add(15);
        Lista_Numero.add(16);
        Lista_Numero.add(17);
        Lista_Numero.add(18);
        Lista_Numero.add(19);
        Lista_Numero.add(20);
        Lista_Numero.add(21);
        Lista_Numero.add(22);
        Lista_Numero.add(23);
        Lista_Numero.add(24);
        Lista_Numero.add(25);
    }

    private void Listar_Personas_Plantel_Posicion(final int id,final Context context) {

        String id_plantel=String.valueOf(id);

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Estadistico:");
        progressDialog.setMessage("Listando Jugadores...");
        progressDialog.show();

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
