package org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterSeguimientoPersona;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterSeguimientos;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Grupo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Seguimiento;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.MigrarFasePrueba;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasSeguimiento;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPlantel2;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPlantel_Todo;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarSeguimientos;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ListaSeguimientosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private LinearLayoutManager linearLayout;
    private AdapterSeguimientos adapter;
    private List<Seguimiento> lista_seguimientos;
    Context context;
    ProgressDialog progressDialog;
    CardView nuevo_seguimiento;
    TextView seg_nombre,seg_ubigeo,seg_prom;
    TextView opcion_migrar_fase;
    RelativeLayout lista_vacia;
    AlertDialog da;
    List<Plantel> Lista_Categorias;
    String[] Nombres_Categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_seguimientos_persona);
        lista_seguimientos=new ArrayList<>();
        recyclerView=findViewById(R.id.Recycler_Seguimientos_lista);
        nuevo_seguimiento=findViewById(R.id.nuevo_seguimiento);
        seg_nombre=findViewById(R.id.info_seguimiento_persona);
        seg_ubigeo=findViewById(R.id.info_seguimiento_ubigeo);
        seg_prom=findViewById(R.id.info_seguimiento_promedio);
        opcion_migrar_fase=findViewById(R.id.opcion_migrar_fase);
        lista_vacia=findViewById(R.id.lista_vacia_seguimiento2);
        Lista_Categorias=new ArrayList<>();

        context=this;

        SpannableString mitextoU = new SpannableString("Migrar a Fase de Pruebas");
        mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
        opcion_migrar_fase.setText(mitextoU);

        linearLayout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        if(Usuario.SESION_ACTUAL.getPersona_seguimiento()!=null){
            System.out.println("ID_PERSONA_USUARIO_SESION:"+Usuario.SESION_ACTUAL.getPersona_seguimiento().getId());
          //  System.out.println("ID_PERSONA_SEGUIMIENTO:"+Seguimiento.SEGUIMIENTO.getPersona().getId());

            seg_nombre.setText(Usuario.SESION_ACTUAL.getPersona_seguimiento().getNombre_Persona()+" "+Usuario.SESION_ACTUAL.getPersona_seguimiento().getApellidos_Persona());
            seg_ubigeo.setText(Usuario.SESION_ACTUAL.getPersona_seguimiento().getUbigeo());

           Seguimientos_de_Persona(Usuario.SESION_ACTUAL.getPersona_seguimiento().getId(),context);
        }else{
            Toast.makeText(context, "No hay informacion del postulante", Toast.LENGTH_SHORT).show();
        }

        adapter = new AdapterSeguimientos(this, lista_seguimientos, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayout);


        nuevo_seguimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaSeguimientosActivity.this,SeguimientoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ListaSeguimientosActivity.this.startActivity(intent);

            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if(adapter.getItemCount()==0){
                    lista_vacia.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else{
                    lista_vacia.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

                if(adapter.getItemCount()!=0){
  
                    double prom=adapter.getPromedio();
                    double roundOff = (double) Math.round(prom * 100) / 100;
                    seg_prom.setText(roundOff+" Ptos");

                }
            }
        });



        opcion_migrar_fase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Seleccion_equipo_Migracio(Usuario.SESION_ACTUAL.getPersona_seguimiento());
            }
        });
    }

    private void Seleccion_equipo_Migracio(Persona persona_seguimiento) {

        final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final View dialoglayout = inflater.inflate(R.layout.info_seleccion_equipo_migra, null);
        TextView migra_scout=dialoglayout.findViewById(R.id.migra_scout);
        Button cancelar=dialoglayout.findViewById(R.id.migra_cancelar);
        Button grabar=dialoglayout.findViewById(R.id.migra_guardar);
        Spinner spinner_equipos=dialoglayout.findViewById(R.id.migrar_spinner);

        final AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
        builder4.setView(dialoglayout);
        da=builder4.show();

        migra_scout.setText(persona_seguimiento.getUsuario().getNombres()+" "+persona_seguimiento.getUsuario().getApellidos());

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                da.dismiss();
            }
        });

        Nombres_Categorias=new String[Lista_Categorias.size()+1];
        Nombres_Categorias[0]="-- SELECCIONE --";
        for(int i=0;i<Lista_Categorias.size();i++){
            Nombres_Categorias[i+1]=Lista_Categorias.get(i).getNombre_categoria();
        }
        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,Nombres_Categorias);

        spinner_equipos.setAdapter(adapter_arr);


        spinner_equipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if(String.valueOf(item).equalsIgnoreCase("-- SELECCIONE --")){
                    Usuario.SESION_ACTUAL.setPlantel_temporal(null);
                }else{
                    for(int x=0;x<Lista_Categorias.size();x++){
                        if(Lista_Categorias.get(x).getNombre_categoria().equalsIgnoreCase(String.valueOf(item))){
                            Usuario.SESION_ACTUAL.setPlantel_temporal(Lista_Categorias.get(x));
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setTitle("Migrar")
                        .setMessage("¿Desea Migrar a "+Usuario.SESION_ACTUAL.getPersona_seguimiento().getNombre_Persona()+" "+Usuario.SESION_ACTUAL.getPersona_seguimiento().getApellidos_Persona() +" hacia la Fase Prueba?")
                        .setPositiveButton("SI",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                       if(Usuario.SESION_ACTUAL.getPlantel_temporal()!=null){
                                 Migrar_fase_pruebas(Usuario.SESION_ACTUAL.getPersona_seguimiento(),Usuario.SESION_ACTUAL.getPlantel_temporal().getId(),context);
                                       }else{
                                           Toast.makeText(context, "Seleccione Categoria!", Toast.LENGTH_SHORT).show();
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
    private void Migrar_fase_pruebas(Persona persona_seguimiento, int id_equ, final Context context) {
        String id_user=String.valueOf(persona_seguimiento.getUsuario().getId());
        String id_persona=String.valueOf(persona_seguimiento.getId());
        String id_equipo=String.valueOf(id_equ);
        String estado=String.valueOf(1);
        System.out.println("MIGRACION FASE PRUEBAS" );
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Seguimiento:");
        progressDialog.setMessage("Migrando a Fase Pruebas...");
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        Toast.makeText(context, "Postulante se Migro a Fase Prueba con Exito!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ListaSeguimientosActivity.this,ListaPersonaSeguimientoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        ListaSeguimientosActivity.this.startActivity(intent);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "No se pudo migrar", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al migrar fase prueba :"+e);
                }
            }
        };

        MigrarFasePrueba xx = new MigrarFasePrueba(id_user,id_persona,id_equipo,estado,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }
    private void Seguimientos_de_Persona(final int id_persona2,final Context context) {
        String id_persona= String.valueOf(id_persona2);

        System.out.println("ID_PERSONA :"+id_persona);

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Seguimiento:");
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
                        JSONArray masivo=jsonResponse.getJSONArray("seguimientos");
                        for(int i=0;i<masivo.length();i++){
                            JSONObject objeto= masivo.getJSONObject(i);
                            Seguimiento temp=new Seguimiento();

                            temp.setId(objeto.getInt("ID"));
                            temp.setCodigo_Seguimiento(objeto.getString("CODIGO_SEGUIMIENTO"));
                            Usuario u=new Usuario();
                            u.setId(objeto.getInt("ID_USER"));
                            u.setNombres(objeto.getString("NOMBRES"));
                            u.setApellidos(objeto.getString("APELLIDOS"));
                            temp.setUsuario(u);
                            temp.setFecha_Registro(objeto.getString("FECHA_REGISTRO"));
                            temp.setTotal_Puntaje(objeto.getInt("TOTAL_PUNTAJE"));
                            temp.setEstado(objeto.getInt("ESTADO"));
                            temp.setNombre_Competencia(objeto.getString("NOM_COMPETENCIA"));
                            temp.setNombre_Rival(objeto.getString("RIVAL"));
                            temp.setMinutos_Juego(objeto.getInt("MINUTOS"));
                            temp.setCantidad_Pierde_Balon(objeto.getInt("OP_PIERDE_BALON"));
                            temp.setCantidad_Recupera_Balon(objeto.getInt("OP_RECUPERA_BALON"));
                            temp.setCantidad_Pase_Gol(objeto.getInt("OP_PASE_GOL"));
                            temp.setCantidad_Dribbling(objeto.getInt("OP_DRIBBLING"));
                            temp.setGoles(objeto.getInt("GOLES"));

                            lista_seguimientos.add(temp);
                        }

                        adapter.notifyDataSetChanged();

                        System.out.println("LISTADO COMPLETO DE MASIVOS");

                        listar_categorias(context);
                    } else {
                        progressDialog.dismiss();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(context, "Listado Vacio", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar Seguimientos :"+e);
                }
            }
        };

        RecuperarSeguimientos xx = new RecuperarSeguimientos(id_persona,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);
    }
    public void onBackPressed() {
        Intent intent = new Intent(ListaSeguimientosActivity.this,ListaPersonaSeguimientoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        ListaSeguimientosActivity.this.startActivity(intent);
    }
    private void listar_categorias(final Context context) {

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("plantel_todo");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Plantel temp=new Plantel();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_categoria(objeto.getString("NOMBRE_CATEGORIA"));
                            Lista_Categorias.add(temp);
                        }

                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        //Toast.makeText(context, "Listado Vacio", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarPlantel_Todo xx = new RecuperarPlantel_Todo(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
}
