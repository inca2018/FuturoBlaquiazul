package org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia_Fase_Prueba.GestionPersonaFasePruebaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaDiagnosticoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPlantelEdicionFormacion;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPlantelEdicionFormacion2;
import org.futuroblanquiazul.futuroblaquiazul.Entity.EventoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Formacion;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActivarPersona;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActualizarEventoFormacion;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.GuardarFormacion;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasPlantel2;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Estadistico_Base;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Estadistico;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.http.POST;

public class DefinirFormacionActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout base7,base8,base9,competetitiva;
    Spinner spinner_base;
    String[] lista_bases_nombres;
    Context context;
    RecyclerView recyclerView;
    TextView equipo;
    ScrollView scrollView;

    private LinearLayoutManager linearLayout,linearLayout2;

    private AdapterPlantelEdicionFormacion2 adapter2;

    List<Persona> Lista_JugadoresEdicion;
    ProgressDialog progressDialog;
    AlertDialog da,da2;

    public static final DefinirFormacionActivity DEFINIR=new DefinirFormacionActivity();
    public static final  List<Persona> Lista_Jugadores =new ArrayList<>();
    public static  AdapterPlantelEdicionFormacion adapter = null;

    ImageView accion_limpiar;

    RecyclerView recyclerView2;
    RadioGroup limpiar_grupo;

    int estado_accion=0;
    int variable_spinner;

    int estado_accion_guardar=1;

    Button definir_formacion_guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definir_formacion);
        context=this;
        spinner_base=findViewById(R.id.spinner_formacion);
        base7=findViewById(R.id.area_base7);
        base8=findViewById(R.id.area_base8);
        base9=findViewById(R.id.area_base9);
        competetitiva=findViewById(R.id.area_campetetiva);
        recyclerView=findViewById(R.id.recycler_Equipo_Edicion_Formacion);
        equipo=findViewById(R.id.id_equipo_edicion_formacion);
        accion_limpiar=findViewById(R.id.accion_limpiar);
        limpiar_grupo=findViewById(R.id.limpiar_grupo);
        scrollView=findViewById(R.id.definir_formacion_scroll);
        definir_formacion_guardar=findViewById(R.id.definir_formacion_guardar);

        Lista_JugadoresEdicion=new ArrayList<>();

        linearLayout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        linearLayout2= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapter = new AdapterPlantelEdicionFormacion(this, Lista_Jugadores, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
            }
        });
        adapter2 = new AdapterPlantelEdicionFormacion2(this,Recursos_Estadistico.LISTA_PERSONA_TEMPORAL, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayout);

        if(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal()!=null){
            equipo.setText(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getPlantel().getNombre_categoria());
            Listar_Personas_Plantel(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getPlantel().getId(), context);
        }else{
            equipo.setText("No Disponible");
        }
       armar_Spinner();
       Gestion_Recursos();
       Activar_Funcionalidad();
       Iniciar_datos();
       LimpiarEntradas();


       Accion_Limpiar();

        limpiar_grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.limpiar_seleccion){
                    estado_accion=1;
                }else if(checkedId==R.id.limpiar_todo){
                    estado_accion=2;
                }
            }
        });


       Accion_Guardar();
    }

    private void Accion_Guardar() {
        definir_formacion_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                final View dialoglayout = inflater.inflate(R.layout.area_seleccion_guardar_formacion, null);

                RadioGroup radio;
                Button boton_guardar;
                final AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
                builder4.setView(dialoglayout);
                da2=builder4.show();

                radio=dialoglayout.findViewById(R.id.grupo_formacion_guardar);
                boton_guardar=dialoglayout.findViewById(R.id.guardar_definir_formacion);


                radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        if(checkedId==R.id.radio_formacion7){
                            estado_accion_guardar=1;

                        }else if(checkedId==R.id.radio_formacion8){
                            estado_accion_guardar=2;

                        }else if(checkedId==R.id.radio_formacion9){
                            estado_accion_guardar=3;

                        }else if(checkedId==R.id.radio_formacionCompete){
                            estado_accion_guardar=4;

                        }
                    }
                });

                boton_guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(estado_accion_guardar!=0){
                            switch (estado_accion_guardar){
                                case 1:
                                    boolean vacio=Verificar_Lista_Completo(Recursos_Estadistico.LISTA_BASE_7);
                                    Mostrar_elementos(Recursos_Estadistico.LISTA_BASE_7);
                                    if(vacio!=true){

                                        final android.support.v7.app.AlertDialog.Builder builder4 = new android.support.v7.app.AlertDialog.Builder(context);
                                        builder4.setTitle("Formación de Equipo")
                                                .setMessage("¿Desea Guardar datos de  Formación (BASE 7 Jugadores)?")
                                                .setPositiveButton("SI",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Enviar_Datos(Recursos_Estadistico.LISTA_BASE_7,1);

                                                            }
                                                        })
                                                .setNegativeButton("NO",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });

                                        builder4.show();

                                    }else{
                                        Toast.makeText(DefinirFormacionActivity.this, "Formación de Base 7 Jugadores no esta Completa", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case 2:
                                    boolean vacio2=Verificar_Lista_Completo(Recursos_Estadistico.LISTA_BASE_8);
                                    Mostrar_elementos(Recursos_Estadistico.LISTA_BASE_8);
                                    if(vacio2!=true){
                                        final android.support.v7.app.AlertDialog.Builder builder4 = new android.support.v7.app.AlertDialog.Builder(context);
                                        builder4.setTitle("Formación de Equipo")
                                                .setMessage("¿Desea Guardar datos de  Formación (BASE 8 Jugadores)?")
                                                .setPositiveButton("SI",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Enviar_Datos(Recursos_Estadistico.LISTA_BASE_8,2);

                                                            }
                                                        })
                                                .setNegativeButton("NO",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });

                                        builder4.show();

                                    }else{
                                        Toast.makeText(DefinirFormacionActivity.this, "Formación de Base 8 Jugadores no esta Completa", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case 3:

                                    boolean vacio3=Verificar_Lista_Completo(Recursos_Estadistico.LISTA_BASE_9);
                                    Mostrar_elementos(Recursos_Estadistico.LISTA_BASE_9);
                                    if(vacio3!=true){

                                        final android.support.v7.app.AlertDialog.Builder builder4 = new android.support.v7.app.AlertDialog.Builder(context);
                                        builder4.setTitle("Formación de Equipo")
                                                .setMessage("¿Desea Guardar datos de  Formación (BASE 9 Jugadores)?")
                                                .setPositiveButton("SI",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Enviar_Datos(Recursos_Estadistico.LISTA_BASE_9,3);

                                                            }
                                                        })
                                                .setNegativeButton("NO",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });

                                        builder4.show();

                                    }else{
                                        Toast.makeText(DefinirFormacionActivity.this, "Formación de Base 9 Jugadores no esta Completa", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case 4:
                                    boolean vacio4=Verificar_Lista_Completo(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA);
                                    Mostrar_elementos(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA);
                                    if(vacio4!=true){

                                        final android.support.v7.app.AlertDialog.Builder builder4 = new android.support.v7.app.AlertDialog.Builder(context);
                                        builder4.setTitle("Formación de Equipo")
                                                .setMessage("¿Desea Guardar datos de  Formación (BASE COMPETETITIVA 11 Jugadores)?")
                                                .setPositiveButton("SI",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Enviar_Datos(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA,4);
                                                            }
                                                        })
                                                .setNegativeButton("NO",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });

                                        builder4.show();

                                    }else{
                                        Toast.makeText(DefinirFormacionActivity.this, "Formación de Base Competetitiva 11 Jugadores no esta Completa", Toast.LENGTH_SHORT).show();
                                    }
                                    break;


                            }
                        }else{
                            Toast.makeText(DefinirFormacionActivity.this, "No se puede guardar seleccion", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });
    }
    private void Accion_Limpiar() {
        accion_limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                debug(" Ingreso a click");
                if(estado_accion==0){
                    Toast.makeText(context, "Seleccione Opcion para Vaciar Datos", Toast.LENGTH_SHORT).show();
                }else if(estado_accion==1){
                    debug(" Ingreso a ESTADO ACCION 1 ");

                    if(variable_spinner!=0){
                        switch (variable_spinner){
                            case 1:
                                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                                builder.setTitle("Formación de Equipo")
                                        .setMessage("¿Desea eliminar datos de  Formación (BASE 7 Jugadores)?")
                                        .setPositiveButton("SI",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        Borrar_Datos_Formacion(Recursos_Estadistico.LISTA_BASE_7,0);

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

                                break;
                            case 2:
                                final android.support.v7.app.AlertDialog.Builder builder2 = new android.support.v7.app.AlertDialog.Builder(context);
                                builder2.setTitle("Formación de Equipo")
                                        .setMessage("¿Desea eliminar datos de  Formación (BASE 8 Jugadores)?")
                                        .setPositiveButton("SI",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        Borrar_Datos_Formacion(Recursos_Estadistico.LISTA_BASE_8,1);

                                                    }
                                                })
                                        .setNegativeButton("NO",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });

                                builder2.show();

                                break;
                            case 3:
                                final android.support.v7.app.AlertDialog.Builder builder3 = new android.support.v7.app.AlertDialog.Builder(context);
                                builder3.setTitle("Formación de Equipo")
                                        .setMessage("¿Desea eliminar datos de  Formación (BASE 9 Jugadores)?")
                                        .setPositiveButton("SI",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        Borrar_Datos_Formacion(Recursos_Estadistico.LISTA_BASE_9,2);

                                                    }
                                                })
                                        .setNegativeButton("NO",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });

                                builder3.show();
                                break;
                            case 4:
                                final android.support.v7.app.AlertDialog.Builder builder4 = new android.support.v7.app.AlertDialog.Builder(context);
                                builder4.setTitle("Formación de Equipo")
                                        .setMessage("¿Desea eliminar datos de  Formación (BASE COMPETITIVA 11 Jugadores)?")
                                        .setPositiveButton("SI",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        Borrar_Datos_Formacion(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA,3);

                                                    }
                                                })
                                        .setNegativeButton("NO",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });

                                builder4.show();
                                break;

                        }

                    }else{
                        Toast.makeText(context, "Seleccione Elemento en Lista de Formación", Toast.LENGTH_SHORT).show();
                    }

                }else if(estado_accion==2){
                    final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                    builder.setTitle("Formación de Equipo")
                            .setMessage("¿Desea eliminar datos de Todas las Formaciones?")
                            .setPositiveButton("SI",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            Borrar_Datos_Formacion(Recursos_Estadistico.LISTA_BASE_7,0);
                                            Borrar_Datos_Formacion(Recursos_Estadistico.LISTA_BASE_8,0);
                                            Borrar_Datos_Formacion(Recursos_Estadistico.LISTA_BASE_9,0);
                                            Borrar_Datos_Formacion(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA,0);

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
        });
    }
    private void Mostrar_elementos(List<Estadistico_Base> listaBase7) {

       for(int i=0;i<listaBase7.size();i++){
           if(listaBase7.get(i).getPersona()!=null){
               debug("PERSONA :"+listaBase7.get(i).getPersona().getId()+"  ESTADO:"+listaBase7.get(i).getEstado());
           }

       }
    }
    private void Enviar_Datos(List<Estadistico_Base> listaBase,int codigo_formacion) {


         for(int i=0;i<Lista_Jugadores.size();i++){
             Formacion temp2=new Formacion();
             temp2.setUser(Usuario.SESION_ACTUAL.getId());
             temp2.setEvento(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getId());
             temp2.setPersona(Lista_Jugadores.get(i).getId());
             temp2.setFormacion(codigo_formacion);
             temp2.setPosicion(100);
             temp2.setEstado(Lista_Jugadores.get(i).getEstado_edicion());
             debug("JUGADORES Agregados TOTAL:"+temp2.getPersona());
             Formacion.LISTA_FORMACION.add(temp2);
         }


         for(int i=0;i<Formacion.LISTA_FORMACION.size();i++){
                int codigo_posicion=Buscar_Posicion(Formacion.LISTA_FORMACION.get(i).getPersona(),listaBase);
                if(codigo_posicion!=(-1)){
                    Formacion.LISTA_FORMACION.get(i).setPosicion(codigo_posicion);
                }else{
                    Formacion.LISTA_FORMACION.get(i).setPosicion(-1);
                }
         }




          for(int i=0;i<Formacion.LISTA_FORMACION.size();i++){
              Guardar_Formación(Formacion.LISTA_FORMACION.get(i),context);
              debug("ELEMENTO : "+(i+1));
              if(i==Formacion.LISTA_FORMACION.size()-1){
                 debug("ULTIMO ELEMENTO :"+(i+1));

                 Actualizar_Evento(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getId(),context);
              }
          }

    }
    private void Actualizar_Evento(int id, final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Barrio Intimo:");
        progressDialog.setMessage("Listando...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
    String evento=String.valueOf(id);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        progressDialog.dismiss();
                        da2.dismiss();

                        LimpiarEntradas();
                        Formacion.LISTA_FORMACION.clear();
                        Intent intent = new Intent(DefinirFormacionActivity.this,ListaEventosEstadisticosActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        DefinirFormacionActivity.this.startActivity(intent);
                        Toast.makeText(context, "Formacion Creado Correctamente!", Toast.LENGTH_SHORT).show();

                    }else {

                        Toast.makeText(context, "Error de Conexion al guardar formacion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de Servidor:"+e);
                }
            }
        };

       ActualizarEventoFormacion xx = new ActualizarEventoFormacion(evento, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    private int Buscar_Posicion(int id_persona, List<Estadistico_Base> listaBase) {
        int val=-1;

          for(int i=0;i<listaBase.size();i++){
              if(listaBase.get(i).getPersona().getId()==id_persona){
                  val=listaBase.get(i).getId();
              }
          }

        return val;
    }
    private void Guardar_Formación(Formacion myarray, final Context context) {
         String id_user=String.valueOf(myarray.getUser());
         String id_evento=String.valueOf(myarray.getEvento());
         String id_persona=String.valueOf(myarray.getPersona());
         String id_formacion=String.valueOf(myarray.getFormacion());
         String id_posicion=String.valueOf(myarray.getPosicion());
         String estado_actual=String.valueOf(myarray.getEstado());

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                         debug(" FORMACION GUARDADA");
                    }else {

                        Toast.makeText(context, "Error de Conexion al guardar formacion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de Servidor:"+e);
                }
            }
        };

        GuardarFormacion xx = new GuardarFormacion(id_user,id_evento,id_persona,id_formacion,id_posicion,estado_actual,myarray.toString(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);
    }
    private boolean Verificar_Lista_Completo(List<Estadistico_Base> listaBase) {
        debug("Entra a verificar");
       boolean temp=false;
               for(int i=0;i<listaBase.size();i++){
                   if(listaBase.get(i).getEstado()==1){
                       temp=true;
                   }else{
                       temp=false;
                   }
               }
       return temp;
    }
    private void Borrar_Datos_Formacion(List<Estadistico_Base> listaBase,int codigo) {


        for(int i=0;i<listaBase.size();i++){
            listaBase.get(i).setPersona(null);
            listaBase.get(i).getImageView().setImageResource(R.drawable.user_default);
            listaBase.get(i).getNombre().setText("");
            listaBase.get(i).setEstado(1);
        }

        Actualizar_Entradas_General(codigo);


    }
    private void LimpiarEntradas() {
        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_7.size();i++){
            Recursos_Estadistico.LISTA_BASE_7.get(i).setPersona(null);
            Recursos_Estadistico.LISTA_BASE_7.get(i).setEstado(1);
        }
        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_8.size();i++){
            Recursos_Estadistico.LISTA_BASE_8.get(i).setPersona(null);
            Recursos_Estadistico.LISTA_BASE_8.get(i).setEstado(1);
        }
        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_9.size();i++){
            Recursos_Estadistico.LISTA_BASE_9.get(i).setPersona(null);
            Recursos_Estadistico.LISTA_BASE_9.get(i).setEstado(1);
        }
        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.size();i++){
            Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).setPersona(null);
            Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).setEstado(1);
        }

        Lista_Jugadores.clear();
        adapter.notifyDataSetChanged();
    }
    private void Actualizar_Entradas_General(int codigo) {

        switch (codigo){
            case 0:
                for(int i=0;i<Lista_Jugadores.size();i++){
                    Lista_Jugadores.get(i).setEstado_edicion(0);
                }

                for(int i=0;i<Lista_Jugadores.size();i++){
                    for(int z=0;z<Recursos_Estadistico.LISTA_BASE_7.size();z++){
                        if(Recursos_Estadistico.LISTA_BASE_7.get(z).getEstado()==2){
                            if(Recursos_Estadistico.LISTA_BASE_7.get(z).getPersona().getId()==Lista_Jugadores.get(i).getId()){
                                Lista_Jugadores.get(i).setEstado_edicion(1);
                            }
                        }

                    }
                }

                ReordenarLista();
                adapter.notifyDataSetChanged();
                break;
            case 1:

                for(int i=0;i<Lista_Jugadores.size();i++){
                    Lista_Jugadores.get(i).setEstado_edicion(0);
                }

                for(int i=0;i<Lista_Jugadores.size();i++){
                    for(int z=0;z<Recursos_Estadistico.LISTA_BASE_8.size();z++){
                        if(Recursos_Estadistico.LISTA_BASE_8.get(z).getEstado()==2){
                            if(Recursos_Estadistico.LISTA_BASE_8.get(z).getPersona().getId()==Lista_Jugadores.get(i).getId()){
                                Lista_Jugadores.get(i).setEstado_edicion(1);
                            }
                        }

                    }
                }

                ReordenarLista();
                adapter.notifyDataSetChanged();
                break;
            case 2:

                for(int i=0;i<Lista_Jugadores.size();i++){
                    Lista_Jugadores.get(i).setEstado_edicion(0);
                }

                for(int i=0;i<Lista_Jugadores.size();i++){
                    for(int z=0;z<Recursos_Estadistico.LISTA_BASE_9.size();z++){
                        if(Recursos_Estadistico.LISTA_BASE_9.get(z).getEstado()==2){
                            if(Recursos_Estadistico.LISTA_BASE_9.get(z).getPersona().getId()==Lista_Jugadores.get(i).getId()){
                                Lista_Jugadores.get(i).setEstado_edicion(1);
                            }
                        }

                    }
                }

                ReordenarLista();
                adapter.notifyDataSetChanged();
                break;
            case 3:
                for(int i=0;i<Lista_Jugadores.size();i++){
                    Lista_Jugadores.get(i).setEstado_edicion(0);
                }

                for(int i=0;i<Lista_Jugadores.size();i++){
                    for(int z=0;z<Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.size();z++){
                        if(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(z).getEstado()==2){
                            if(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(z).getPersona().getId()==Lista_Jugadores.get(i).getId()){
                                Lista_Jugadores.get(i).setEstado_edicion(1);
                            }
                        }

                    }
                }

                ReordenarLista();
                adapter.notifyDataSetChanged();
                break;
        }



    }
    private void Listar_Personas_Plantel(int id, final Context context) {

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
                        JSONArray departamentos=jsonResponse.getJSONArray("personas2");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            temp.setFoto(objeto.getString("FOTO"));
                            temp.setEstado_edicion(0);
                            Lista_Jugadores.add(temp);
                            Recursos_Estadistico.LISTA_PERSONA_GENERAL.add(temp);


                        }

                        adapter.notifyDataSetChanged();

                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DE JUGADORES");
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
    private void Iniciar_datos() {
        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_7.size();i++){
            Recursos_Estadistico.LISTA_BASE_7.get(i).getImageView().setImageResource(R.drawable.user_default);
        }
        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_8.size();i++){
            Recursos_Estadistico.LISTA_BASE_8.get(i).getImageView().setImageResource(R.drawable.user_default);
        }

        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_9.size();i++){
            Recursos_Estadistico.LISTA_BASE_9.get(i).getImageView().setImageResource(R.drawable.user_default);
        }
        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.size();i++){
            Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getImageView().setImageResource(R.drawable.user_default);
        }
    }
    private void Activar_Funcionalidad() {

        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_7.size();i++){
            Recursos_Estadistico.LISTA_BASE_7.get(i).getArea().setOnClickListener(this);
        }
        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_8.size();i++){
            Recursos_Estadistico.LISTA_BASE_8.get(i).getArea().setOnClickListener(this);
        }

        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_9.size();i++){
            Recursos_Estadistico.LISTA_BASE_9.get(i).getArea().setOnClickListener(this);
        }
        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.size();i++){
            Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getArea().setOnClickListener(this);
        }
    }
    private void Gestion_Recursos() {

        for(int i=0;i< Recursos_Estadistico.LISTA_BASE_7.size();i++){
            ImageView ima=findViewById(Recursos_Estadistico.LISTA_BASE_7.get(i).getRecurso_foto());
            Recursos_Estadistico.LISTA_BASE_7.get(i).setImageView(ima);

            LinearLayout lin=findViewById(Recursos_Estadistico.LISTA_BASE_7.get(i).getRecurso_area());
            Recursos_Estadistico.LISTA_BASE_7.get(i).setArea(lin);

            TextView text=findViewById(Recursos_Estadistico.LISTA_BASE_7.get(i).getRecurso_texto());
            Recursos_Estadistico.LISTA_BASE_7.get(i).setNombre(text);

        }

        for(int i=0;i< Recursos_Estadistico.LISTA_BASE_8.size();i++){
            ImageView ima=findViewById(Recursos_Estadistico.LISTA_BASE_8.get(i).getRecurso_foto());
            Recursos_Estadistico.LISTA_BASE_8.get(i).setImageView(ima);

            LinearLayout lin=findViewById(Recursos_Estadistico.LISTA_BASE_8.get(i).getRecurso_area());
            Recursos_Estadistico.LISTA_BASE_8.get(i).setArea(lin);

            TextView text=findViewById(Recursos_Estadistico.LISTA_BASE_8.get(i).getRecurso_texto());
            Recursos_Estadistico.LISTA_BASE_8.get(i).setNombre(text);
        }



        for(int i=0;i< Recursos_Estadistico.LISTA_BASE_9.size();i++){
            ImageView ima=findViewById(Recursos_Estadistico.LISTA_BASE_9.get(i).getRecurso_foto());
            Recursos_Estadistico.LISTA_BASE_9.get(i).setImageView(ima);

            LinearLayout lin=findViewById(Recursos_Estadistico.LISTA_BASE_9.get(i).getRecurso_area());
            Recursos_Estadistico.LISTA_BASE_9.get(i).setArea(lin);

            TextView text=findViewById(Recursos_Estadistico.LISTA_BASE_9.get(i).getRecurso_texto());
            Recursos_Estadistico.LISTA_BASE_9.get(i).setNombre(text);
        }


        for(int i=0;i< Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.size();i++){
            ImageView ima=findViewById(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getRecurso_foto());
            Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).setImageView(ima);
            LinearLayout lin=findViewById(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getRecurso_area());
            Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).setArea(lin);
            TextView text=findViewById(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getRecurso_texto());
            Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).setNombre(text);

        }
    }
    private void armar_Spinner() {

        lista_bases_nombres=new String[5];
        lista_bases_nombres[0]="-- Seleccione Base --";
        lista_bases_nombres[1]=" Base 7 Jugadores";
        lista_bases_nombres[2]=" Base 8 Jugadores";
        lista_bases_nombres[3]=" Base 9 Jugadores";
        lista_bases_nombres[4]=" Base Competetiva(11 Jugadores)";

        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,lista_bases_nombres);
        spinner_base.setAdapter(adapter_arr);

        spinner_base.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 switch (position){
                     case 0:
                       base7.setVisibility(View.GONE);
                       base8.setVisibility(View.GONE);
                       base9.setVisibility(View.GONE);
                       competetitiva.setVisibility(View.GONE);
                       variable_spinner=0;
                       recyclerView.setVisibility(View.GONE);
                         break;
                     case 1:
                         base7.setVisibility(View.VISIBLE);
                         base8.setVisibility(View.GONE);
                         base9.setVisibility(View.GONE);
                         competetitiva.setVisibility(View.GONE);

                         Actualizar_Entradas_General(0);

                         variable_spinner=1;
                         recyclerView.setVisibility(View.VISIBLE);
                         scrollView.smoothScrollTo(0,0);

                         break;
                     case 2:
                         base7.setVisibility(View.GONE);
                         base8.setVisibility(View.VISIBLE);
                         base9.setVisibility(View.GONE);
                         competetitiva.setVisibility(View.GONE);
                         Actualizar_Entradas_General(1);
                         variable_spinner=2;
                         recyclerView.setVisibility(View.VISIBLE);
                         scrollView.smoothScrollTo(0,0);

                         break;
                     case 3:
                         base7.setVisibility(View.GONE);
                         base8.setVisibility(View.GONE);
                         base9.setVisibility(View.VISIBLE);
                         competetitiva.setVisibility(View.GONE);
                         Actualizar_Entradas_General(2);
                         variable_spinner=3;
                         recyclerView.setVisibility(View.VISIBLE);
                         scrollView.smoothScrollTo(0,0);

                         break;
                     case 4:
                         base7.setVisibility(View.GONE);
                         base8.setVisibility(View.GONE);
                         base9.setVisibility(View.GONE);
                         competetitiva.setVisibility(View.VISIBLE);
                         Actualizar_Entradas_General(3);
                         variable_spinner=4;
                         recyclerView.setVisibility(View.VISIBLE);
                         scrollView.smoothScrollTo(0,0);

                         break;
                 }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void debug(String d){
        System.out.println(d);
    }
    @Override
    public void onClick(View v) {

        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_7.size();i++){
            if(Recursos_Estadistico.LISTA_BASE_7.get(i).getRecurso_area()==v.getId()){
                Mostrar_Dialog_Seleccion(Recursos_Estadistico.LISTA_BASE_7,Recursos_Estadistico.LISTA_BASE_7.get(i), context);
                Recursos_Estadistico.RECURSO.setCodigo_base(0);
            }
        }

        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_8.size();i++){
            if(Recursos_Estadistico.LISTA_BASE_8.get(i).getRecurso_area()==v.getId()){
                Mostrar_Dialog_Seleccion(Recursos_Estadistico.LISTA_BASE_8, Recursos_Estadistico.LISTA_BASE_8.get(i), context);
                Recursos_Estadistico.RECURSO.setCodigo_base(1);
            }
        }

        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_9.size();i++){
            if(Recursos_Estadistico.LISTA_BASE_9.get(i).getRecurso_area()==v.getId()){
                Mostrar_Dialog_Seleccion(Recursos_Estadistico.LISTA_BASE_9, Recursos_Estadistico.LISTA_BASE_9.get(i), context);
                Recursos_Estadistico.RECURSO.setCodigo_base(2);
            }
        }
        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.size();i++){
            if(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getRecurso_area()==v.getId()){
                Mostrar_Dialog_Seleccion(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA, Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i),context);
                Recursos_Estadistico.RECURSO.setCodigo_base(3);
                Toast.makeText(context, "click en :"+Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getId(), Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void Mostrar_Dialog_Seleccion(List<Estadistico_Base> listaBase, Estadistico_Base BASE, final Context context) {

        Recursos_Estadistico.RECURSO.setCodigo_campo(BASE.getId());
        Recursos_Estadistico.LISTA_PERSONA_BASE_ACTUAL.clear();
        Recursos_Estadistico.LISTA_PERSONA_TEMPORAL.clear();

        final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final View dialoglayout = inflater.inflate(R.layout.area_seleccion_persona, null);

        ImageView foto;
        TextView jugador;
        LinearLayout liena;
        recyclerView2=dialoglayout.findViewById(R.id.recycler_edicion_cambio);
        foto=dialoglayout.findViewById(R.id.info_imagen_cambio);
        jugador=dialoglayout.findViewById(R.id.info_jugador_cambio);
        liena=dialoglayout.findViewById(R.id.linear_ocultar);

        final AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
        builder4.setView(dialoglayout);
        da=builder4.show();


        if(linearLayout2!=null){
            linearLayout2= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        }

        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(linearLayout2);


        if(BASE.getEstado()==1){

             //Armar Lista Base
                for(int rr=0;rr<listaBase.size();rr++){
                    if(listaBase.get(rr).getEstado()==2){
                        Recursos_Estadistico.LISTA_PERSONA_BASE_ACTUAL.add(listaBase.get(rr).getPersona());
                    }
                }
             // Armar Lista Temporal

                for(int f=0;f<Recursos_Estadistico.LISTA_PERSONA_GENERAL.size();f++){
                    Recursos_Estadistico.LISTA_PERSONA_TEMPORAL.add(Recursos_Estadistico.LISTA_PERSONA_GENERAL.get(f));
                   }


            if(Recursos_Estadistico.LISTA_PERSONA_BASE_ACTUAL.size()==0){
                    adapter2.notifyDataSetChanged();
            }else{
                for(int y=0;y<Recursos_Estadistico.LISTA_PERSONA_BASE_ACTUAL.size();y++){
                           RemoverPersona(Recursos_Estadistico.LISTA_PERSONA_BASE_ACTUAL.get(y));

                }
                    adapter2.notifyDataSetChanged();
            }



        }else if(BASE.getEstado()==2){
            liena.setVisibility(View.VISIBLE);
            Glide.with(context).load(BASE.getPersona().getFoto()).into(foto);
            jugador.setText(BASE.getPersona().getNombre_Persona()+" "+BASE.getPersona().getApellidos_Persona());

            Actualizar_Persona_Cambiada(BASE);

            //Armar Lista Base
            for(int rr=0;rr<listaBase.size();rr++){
                if(listaBase.get(rr).getEstado()==2){
                    Recursos_Estadistico.LISTA_PERSONA_BASE_ACTUAL.add(listaBase.get(rr).getPersona());
                }
            }
            // Armar Lista Temporal

            for(int f=0;f<Recursos_Estadistico.LISTA_PERSONA_GENERAL.size();f++){
                Recursos_Estadistico.LISTA_PERSONA_TEMPORAL.add(Recursos_Estadistico.LISTA_PERSONA_GENERAL.get(f));
            }


            debug(" CAPACIDAD LISTA BASE ACTUAL:"+Recursos_Estadistico.LISTA_PERSONA_BASE_ACTUAL.size());
            debug(" CAPACIDAD LISTA TEMPORAL:"+Recursos_Estadistico.LISTA_PERSONA_TEMPORAL.size());



            if(Recursos_Estadistico.LISTA_PERSONA_BASE_ACTUAL.size()==0){
                adapter2.notifyDataSetChanged();
            }else{
                for(int y=0;y<Recursos_Estadistico.LISTA_PERSONA_BASE_ACTUAL.size();y++){
                    RemoverPersona(Recursos_Estadistico.LISTA_PERSONA_BASE_ACTUAL.get(y));

                }
                adapter2.notifyDataSetChanged();
            }
        }

        Recursos_Estadistico.RECURSO.setDialog(da);
    }
    private void Actualizar_Persona_Cambiada(Estadistico_Base base) {
       debug("entro a cambiada");
        for(int i=0;i<Lista_Jugadores.size();i++){
            if(Lista_Jugadores.get(i).getId()==base.getPersona().getId()){
                Lista_Jugadores.get(i).setEstado_edicion(0);
                //adapter.notifyDataSetChanged();
                debug("actualizo a cambiada");
            }
        }

    }
    public void ReordenarLista(){
           List<Persona> TEMP=new ArrayList<>();
          for(int i=0;i<Lista_Jugadores.size();i++){
            if(Lista_Jugadores.get(i).getEstado_edicion()==1){
                TEMP.add(Lista_Jugadores.get(i));
            }
         }
        for(int i=0;i<Lista_Jugadores.size();i++){
            if(Lista_Jugadores.get(i).getEstado_edicion()==0){
                TEMP.add(Lista_Jugadores.get(i));
            }
        }

        Lista_Jugadores.clear();

       for(int t=0;t<TEMP.size();t++){
           Lista_Jugadores.add(TEMP.get(t));
       }

       adapter.notifyDataSetChanged();



    }
    private void RemoverPersona(Persona persona) {

       for(int i=0;i<Recursos_Estadistico.LISTA_PERSONA_TEMPORAL.size();i++){
            if(Recursos_Estadistico.LISTA_PERSONA_TEMPORAL.get(i).getId()==persona.getId()){
                Recursos_Estadistico.LISTA_PERSONA_TEMPORAL.remove(i);
            }
       }
    }
    public void Actualizar_Campo(Persona p, Context context2){

        switch (Recursos_Estadistico.RECURSO.getCodigo_base()){
            case 0:

                for(int i=0;i<Recursos_Estadistico.LISTA_BASE_7.size();i++){
                    if(Recursos_Estadistico.LISTA_BASE_7.get(i).getId()==Recursos_Estadistico.RECURSO.getCodigo_campo()){
                        Recursos_Estadistico.LISTA_BASE_7.get(i).getNombre().setText(p.getNombre_Persona()+" "+p.getApellidos_Persona());
                        Glide.with(context2).load(p.getFoto()).into( Recursos_Estadistico.LISTA_BASE_7.get(i).getImageView());
                        Recursos_Estadistico.LISTA_BASE_7.get(i).setEstado(2);
                        Recursos_Estadistico.LISTA_BASE_7.get(i).setPersona(p);
                        Actualizar_lista_general(0,p);

                    }
                }

                break;
            case 1:
                for(int i=0;i<Recursos_Estadistico.LISTA_BASE_8.size();i++){
                    if(Recursos_Estadistico.LISTA_BASE_8.get(i).getId()==Recursos_Estadistico.RECURSO.getCodigo_campo()){
                        Recursos_Estadistico.LISTA_BASE_8.get(i).getNombre().setText(p.getNombre_Persona()+" "+p.getApellidos_Persona());
                        Glide.with(context2).load(p.getFoto()).into( Recursos_Estadistico.LISTA_BASE_8.get(i).getImageView());
                        Recursos_Estadistico.LISTA_BASE_8.get(i).setEstado(2);
                        Recursos_Estadistico.LISTA_BASE_8.get(i).setPersona(p);
                        Actualizar_lista_general(1,p);

                    }
                }

                break;
            case 2:
                for(int i=0;i<Recursos_Estadistico.LISTA_BASE_9.size();i++){
                    if(Recursos_Estadistico.LISTA_BASE_9.get(i).getId()==Recursos_Estadistico.RECURSO.getCodigo_campo()){
                        Recursos_Estadistico.LISTA_BASE_9.get(i).getNombre().setText(p.getNombre_Persona()+" "+p.getApellidos_Persona());
                        Glide.with(context2).load(p.getFoto()).into( Recursos_Estadistico.LISTA_BASE_9.get(i).getImageView());
                        Recursos_Estadistico.LISTA_BASE_9.get(i).setEstado(2);
                        Recursos_Estadistico.LISTA_BASE_9.get(i).setPersona(p);
                        Actualizar_lista_general(2,p);

                    }
                }

                break;
            case 3:
                for(int i=0;i<Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.size();i++){
                    if(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getId()==Recursos_Estadistico.RECURSO.getCodigo_campo()){
                        Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getNombre().setText(p.getNombre_Persona()+" "+p.getApellidos_Persona());
                        Glide.with(context2).load(p.getFoto()).into( Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getImageView());
                        Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).setEstado(2);
                        Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).setPersona(p);
                        Actualizar_lista_general(3,p);

                    }
                }

                break;
        }

    }
    private void Actualizar_lista_general(int codigo,Persona p) {


                if(Lista_Jugadores.size()!=0){

                    for(int i=0;i<Lista_Jugadores.size();i++){
                        if(Lista_Jugadores.get(i).getId()==p.getId()){
                            Lista_Jugadores.get(i).setEstado_edicion(1);
                            adapter.notifyDataSetChanged();
                        }
                    }

                }else{
                    Toast.makeText(context, "Lista Vacia, no se puede actualizar", Toast.LENGTH_SHORT).show();
                }



    }
    public void onBackPressed() {

        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("Formación de Equipo")
                .setMessage("¿Desea salir de la Gestión de Formación del Equipo?")
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LimpiarEntradas();
                                Intent intent = new Intent(DefinirFormacionActivity.this,ListaEventosEstadisticosActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                DefinirFormacionActivity.this.startActivity(intent);
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
