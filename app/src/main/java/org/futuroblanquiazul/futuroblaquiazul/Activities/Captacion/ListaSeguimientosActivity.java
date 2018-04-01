package org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion;

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
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterSeguimientoPersona;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterSeguimientos;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Seguimiento;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.MigrarFasePrueba;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasSeguimiento;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarSeguimientos;
import org.futuroblanquiazul.futuroblaquiazul.R;
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

                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setTitle("Migrar")
                        .setMessage("¿Desea Migrar a "+Usuario.SESION_ACTUAL.getPersona_seguimiento().getNombre_Persona()+" "+Usuario.SESION_ACTUAL.getPersona_seguimiento().getApellidos_Persona() +" hacia la Fase Prueba?")
                        .setPositiveButton("SI",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(Usuario.SESION_ACTUAL.getPersona_seguimiento()!=null){
                                            Migrar_fase_pruebas(Usuario.SESION_ACTUAL.getPersona_seguimiento().getId(),context);
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

    private void Migrar_fase_pruebas(int id,final Context context) {
        String id_persona=String.valueOf(id);

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

        MigrarFasePrueba xx = new MigrarFasePrueba(id_persona,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }

    private void Seguimientos_de_Persona(final int id_persona2,final Context context) {
        String id_persona= String.valueOf(id_persona2);

        System.out.println("ID_PERSONA :"+id_persona);

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Seguimiento:");
        progressDialog.setMessage("Listando...");
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
                        progressDialog.dismiss();
                        System.out.println("LISTADO COMPLETO DE MASIVOS");
                    } else {
                        progressDialog.dismiss();
                        adapter.notifyDataSetChanged();
                        //Toast.makeText(context, "Listado Vacio", Toast.LENGTH_SHORT).show();
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


}
