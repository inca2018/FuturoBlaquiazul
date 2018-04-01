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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterEstadisticoPersonaNumero;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterEstadisticoPersonaPosicion;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterNumero;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPlantelEdicionFormacion;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPlantelEdicionFormacion2;
import org.futuroblanquiazul.futuroblaquiazul.Entity.EventoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Extras;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Formacion;
import org.futuroblanquiazul.futuroblaquiazul.Entity.NumeroDisponible;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Posicion;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PosicionEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActualizarEventoFormacion;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActualizarEventoFormacion2;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.GuardarFormacion;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.GuardarPosiciones;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasPlantel2;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPosiciones;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPosiciones2;
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
    ImageView borrar_1,borrar_2;
    Button guardar;
    List<PosicionEstadistico> Lista_Resultados;
    boolean posiciones_pase=false,numeros_pase=false;
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
        borrar_1=findViewById(R.id.borrar_posiciones);
        borrar_2=findViewById(R.id.borrar_camisetas);
        guardar=findViewById(R.id.guardar_definir_posicion);
        Lista_Resultados=new ArrayList<>();

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
            equipo.setText(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getEquipo().getNombre_equipo());
            evento.setText(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getDescripcion_Nombre_evento());

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

        // POSICIONES LIMPIAR
        borrar_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setTitle("Posiciones del Equipo")
                        .setMessage("¿Desea eliminar datos de posiciones seleccionadas?")
                        .setPositiveButton("SI",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Borrar_Datos_Posiciones();

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

        // CAMISETAS LIMPIAR
        borrar_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setTitle("Numero de Camisetas del Equipo")
                        .setMessage("¿Desea eliminar datos de camisetas seleccionadas?")
                        .setPositiveButton("SI",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Borrar_Datos_Camisetas();

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


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setTitle("Posiciones y Camisetas")
                        .setMessage("¿Desea guardar la Información seleccionada?")
                        .setPositiveButton("SI",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        for(int i=0;i<Extras.LISTA_EXTRAS.size();i++){
                                            if(Extras.LISTA_EXTRAS.get(i).getNumero_Camiseta()==0){
                                                numeros_pase=true;
                                            }
                                        }

                                        for(int i=0;i<Lista_Persona_Posiciones.size();i++){
                                            if(Lista_Persona_Posiciones.get(i).getEstado_posicion()==0){
                                                posiciones_pase=true;
                                            }
                                        }

                                        if(numeros_pase==true && posiciones_pase==true){
                                            Toast.makeText(context, "Debe completar Información de Posiciones y Numero de Camisetas para Continuar", Toast.LENGTH_SHORT).show();
                                        }else{
                                            if(numeros_pase==true){
                                                Toast.makeText(context, "Debe Seleccionar Numero de Camisetas para todos los Integrantes del Equipo", Toast.LENGTH_SHORT).show();
                                                numeros_pase=false;
                                            }else{
                                                if(posiciones_pase==true){
                                                    Toast.makeText(context, "Debe Seleccionar Posición inicial para todos los Integrantes del Equipo", Toast.LENGTH_SHORT).show();
                                                    posiciones_pase=false;
                                                }else{
                                                    Guardar_Informacion();
                                                }
                                            }

                                        }


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

    private void Guardar_Informacion() {

          for(int i=0;i<Extras.LISTA_EXTRAS.size();i++){
               PosicionEstadistico pos=new PosicionEstadistico();
               pos.setPersona(Extras.LISTA_EXTRAS.get(i).getPersona());
               pos.setNum_Camiseta(Extras.LISTA_EXTRAS.get(i).getNumero_Camiseta());
               Lista_Resultados.add(pos);
          }

          for(int i=0;i<Lista_Resultados.size();i++){
               Buscar_Posicion_Persona(Lista_Resultados.get(i));
          }


          for(int i=0;i<Lista_Resultados.size();i++){
              Guardar_Resultados(Lista_Resultados.get(i), Usuario.SESION_ACTUAL.getId(),EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getId(),context);
              if(i==Lista_Resultados.size()-1){
                  Actualizar_Evento(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getId(),context);
              }
          }

    }

    private void Actualizar_Evento(int id_e,final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Posiciones Estadistico:");
        progressDialog.setMessage("Guardando Información...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
        String evento=String.valueOf(id_e);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        progressDialog.dismiss();
                        Borrar_Datos_Camisetas();
                        Borrar_Datos_Posiciones();

                        Intent intent = new Intent(DefinirPosicionesEventoActivity.this,ListaEventosEstadisticosActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        DefinirPosicionesEventoActivity.this.startActivity(intent);
                        Toast.makeText(context, "Información Guardada Correctamente", Toast.LENGTH_SHORT).show();

                    }else {

                        Toast.makeText(context, "Error de Conexion al guardar formacion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de Servidor:"+e);
                }
            }
        };

        ActualizarEventoFormacion2 xx = new ActualizarEventoFormacion2(evento, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);
    }

    private void Guardar_Resultados(final PosicionEstadistico posicionEstadistico, int id_u, int id_ev,final Context context) {

        String id_user=String.valueOf(id_u);
        String id_evento=String.valueOf(id_ev);
        String id_persona=String.valueOf(posicionEstadistico.getPersona().getId());
        String id_posicion=String.valueOf(posicionEstadistico.getPosicion().getId());
        String num_camiseta=String.valueOf(posicionEstadistico.getNum_Camiseta());
        String estado=String.valueOf(1);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        System.out.println(" POSICION GUARDADA");
                    }else {

                        Toast.makeText(context, "Error de Conexion al guardar formacion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de Servidor:"+e);
                }
            }
        };

        GuardarPosiciones xx = new GuardarPosiciones(id_user,id_evento,id_persona,id_posicion,num_camiseta,estado, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    private void Buscar_Posicion_Persona(PosicionEstadistico posicionEstadistico) {

        for(int i=0;i<Lista_Persona_Posiciones.size();i++){
             if(Lista_Persona_Posiciones.get(i).getId()==posicionEstadistico.getPersona().getId()){
                 Posicion n=new Posicion();
                 n.setId(Lista_Persona_Posiciones.get(i).getCodigo_posicion());
                 posicionEstadistico.setPosicion(n);
             }
        }

    }

    private void Borrar_Datos_Camisetas() {

        for(int i=0;i<Extras.LISTA_EXTRAS.size();i++){
            Extras.LISTA_EXTRAS.get(i).setNumero_Camiseta(0);
            Extras.LISTA_EXTRAS.get(i).setEstado2(0);
            Extras.LISTA_EXTRAS.get(i).setLista_Numeros(Lista_Numero);
        }

        Recursos_Estadistico.LISTA_NUMEROS_SELECCIONADOS.clear();

        adapter2.notifyDataSetChanged();

    }

    private void Borrar_Datos_Posiciones() {

        for(int i=0;i<Lista_Persona_Posiciones.size();i++){
            Lista_Persona_Posiciones.get(i).setEstado_posicion(0);
            Lista_Persona_Posiciones.get(i).setCodigo_posicion(0);
            Lista_Persona_Posiciones.get(i).setPosicion_posicion(0);
        }

        adapter1.notifyDataSetChanged();
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
                        Listar_Personas_Plantel_Numero(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getEquipo().getId(), context);
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

                        Mostrar();
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

    private void Mostrar() {

        for(int i=0;i<ListaPosiciones.size();i++){
             debug(" POSICON: "+ListaPosiciones.get(i).getNombre_Posicione());
        }
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
                        Listar_Personas_Plantel_Posicion(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getEquipo().getId(), context);

                    } else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarPosiciones2 xx = new RecuperarPosiciones2(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);
    }

    public void debug(String f){
        System.out.println(f);
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
