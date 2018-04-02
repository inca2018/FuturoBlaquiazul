package org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterEventosEstadisticos;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterFechasEstadisticos;
import org.futuroblanquiazul.futuroblaquiazul.Entity.EventoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.FechaEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Oponente;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarEventosEstadisticos;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarFechasEstadisticos;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaFechasEstadisticosActivity extends AppCompatActivity {
    TextView nombre_Evento,categoria_evento,ubigeo_evento;
    ImageView foto_evento;
    Button agregar_Fecha;
    RecyclerView recyclerView;
    Context context;
    LinearLayoutManager linearLayoutManager;
    ProgressDialog progressDialog;
    private AdapterFechasEstadisticos adapter;
    List<FechaEstadistico> Lista_Fechas;
    CardView card_nueva_fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_fechas_estadisticos);
        nombre_Evento=findViewById(R.id.fecha_nombre);
        categoria_evento=findViewById(R.id.fecha_categoria);
        ubigeo_evento=findViewById(R.id.fecha_ubicacion);
        foto_evento=findViewById(R.id.fecha_evento_foto);
        agregar_Fecha=findViewById(R.id.boton_nueva_fecha);
        recyclerView=findViewById(R.id.fecha_recycler);
        Lista_Fechas=new ArrayList<>();
        card_nueva_fecha=findViewById(R.id.card_nueva_fecha);
        context=this;

        if(Usuario.SESION_ACTUAL.getPerfil().getId()==1 || Usuario.SESION_ACTUAL.getPerfil().getId()==2 || Usuario.SESION_ACTUAL.getPerfil().getId()==3){
            card_nueva_fecha.setVisibility(View.VISIBLE);
        }


        linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        adapter = new AdapterFechasEstadisticos(this, Lista_Fechas, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        if(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal()!=null){
            Listar_Fechas_Estadisticas(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getId(),context);
        }



        if(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal()!=null){
            nombre_Evento.setText(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getDescripcion_Nombre_evento());
            categoria_evento.setText(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getEquipo().getNombre_equipo());
            ubigeo_evento.setText(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getDepartamento().getDescripcion()+"/"+EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getProvincia().getDescripcion()+"/"+EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getDistrito().getDescripcion());
        }


        agregar_Fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,NuevaFechaEstadisticoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);

            }
        });

    }

    private void Listar_Fechas_Estadisticas(final int id,final Context context) {

        String id_evento=String.valueOf(id);

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Modulo Estadistico:");
        progressDialog.setMessage("Listando Fechas...");
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("fechas");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            FechaEstadistico temp=new FechaEstadistico();
                            temp.setNum(i+1);
                            temp.setId(objeto.getInt("ID"));
                            EventoEstadistico ev=new EventoEstadistico();
                            ev.setId(objeto.getInt("ID_EVENTO"));
                            ev.setDescripcion_Nombre_evento(objeto.getString("DESCRIPCION_EVENTO"));
                            temp.setEvento(ev);
                            Oponente opo=new Oponente();
                            opo.setId(objeto.getInt("ID_OPONENTE"));
                            opo.setNombre_Oponente(objeto.getString("NOMBRE_OPONENTE"));
                            temp.setOponente(opo);
                            Usuario u=new Usuario();
                            u.setId(objeto.getInt("ID_USUARIO"));
                            u.setUsuario(objeto.getString("USUARIO"));
                            temp.setUsuario(u);
                            temp.setFecha_Realizacion(objeto.getString("FECHA_REALIZAR"));
                            temp.setFecha_registro(objeto.getString("FECHA_REGISTRO"));
                            temp.setObservacion(objeto.getString("OBSER"));
                            temp.setEstado(objeto.getInt("ESTADO"));


                            Lista_Fechas.add(temp);


                        }

                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DE Fechas");
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Listado Vacio", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar eventos estadisticos :"+e);
                }
            }
        };

        RecuperarFechasEstadisticos xx = new RecuperarFechasEstadisticos(id_evento,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(context,ListaEventosEstadisticosActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);


    }
}
