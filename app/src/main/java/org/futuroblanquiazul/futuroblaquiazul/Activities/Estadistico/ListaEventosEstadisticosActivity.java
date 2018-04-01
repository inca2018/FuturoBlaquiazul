package org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterEventosEstadisticos;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Equipo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.EventoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarBarrios;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarEventosEstadisticos;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaEventosEstadisticosActivity extends AppCompatActivity {
    Button nuevo_evento;
    RecyclerView recyclerView;
    private AdapterEventosEstadisticos adapter;
    List<EventoEstadistico> Lista_Eventos_Estadisticos;
    LinearLayoutManager linearLayoutManager;
    Context context;
    ProgressDialog progressDialog;
    CardView card_nuevo_Evento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos_estadisticos);
        nuevo_evento=findViewById(R.id.boton_nuevo_evento_estadistico);
        recyclerView=findViewById(R.id.recycler_eventos_estadisticos);
        Lista_Eventos_Estadisticos=new ArrayList<>();
        card_nuevo_Evento=findViewById(R.id.card_nuevo_Evento);
        if(Usuario.SESION_ACTUAL.getPerfil().getId()==1 || Usuario.SESION_ACTUAL.getPerfil().getId()==2 || Usuario.SESION_ACTUAL.getPerfil().getId()==3){
            card_nuevo_Evento.setVisibility(View.VISIBLE);
        }
        context=this;

        linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        adapter = new AdapterEventosEstadisticos(this, Lista_Eventos_Estadisticos, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        Listar_Eventos_Estadisticos(context);


        nuevo_evento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,NuevoEventoEstadisticoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });

    }
    private void Listar_Eventos_Estadisticos(final Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Modulo Estadistico:");
        progressDialog.setMessage("Listando Eventos...");
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("eventos_estadisticos");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            EventoEstadistico temp=new EventoEstadistico();
                            temp.setId(objeto.getInt("ID"));
                            temp.setDescripcion_Nombre_evento(objeto.getString("DESCRIPCION_EVENTO"));
                            temp.setDetalle_Evento(objeto.getString("DETALLE_EVENTO"));
                            Unidad_Territorial depar=new Unidad_Territorial();
                            depar.setCodigo(objeto.getInt("idDepa"));
                            depar.setDescripcion(objeto.getString("departamento"));
                            Unidad_Territorial prov=new Unidad_Territorial();
                            prov.setCodigo(objeto.getInt("idProv"));
                            prov.setDescripcion(objeto.getString("provincia"));
                            Unidad_Territorial dis=new Unidad_Territorial();
                            dis.setCodigo(objeto.getInt("idDist"));
                            dis.setDescripcion(objeto.getString("distrito"));
                            Usuario u=new Usuario();
                            u.setId(objeto.getInt("ID_USUARIO"));
                            u.setUsuario(objeto.getString("USUARIO"));
                            u.setNombres(objeto.getString("NOMBRES"));
                            u.setApellidos(objeto.getString("APELLIDOS"));
                            Equipo pla=new Equipo();
                            pla.setId(objeto.getInt("ID_EQUIPO"));
                            pla.setNombre_equipo(objeto.getString("NOMBRE_SUB"));
                            temp.setDepartamento(depar);
                            temp.setProvincia(prov);
                            temp.setDistrito(dis);
                            temp.setUsuario(u);
                            temp.setEquipo(pla);
                            temp.setFecha_Registro(objeto.getString("FECHA_REGISTRO"));
                            temp.setFoto(objeto.getString("IMAGEN"));
                            temp.setEstado(objeto.getInt("ESTADO"));
                            temp.setEstado2(objeto.getInt("ESTADO2"));
                            temp.setInicio_ano(objeto.getInt("ANO_INICIO"));
                            temp.setInicio_mes(objeto.getInt("MES_INICIO"));
                            temp.setInicio_dia(objeto.getInt("DIA_INICIO"));
                            temp.setFin_ano(objeto.getInt("ANO_FIN"));
                            temp.setFin_mes(objeto.getInt("MES_FIN"));
                            temp.setFin_dia(objeto.getInt("DIA_FIN"));
                            temp.setEstado_formacion(objeto.getInt("FORMACION"));
                            temp.setEstado_posiciones(objeto.getInt("POSICIONES"));

                            Lista_Eventos_Estadisticos.add(temp);


                        }

                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DE EVENTOS ESTADISTICOS");
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

        RecuperarEventosEstadisticos xx = new RecuperarEventosEstadisticos(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(context,PrincipalActivity.class);
        intent.putExtra("o","o4");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);


    }
}
