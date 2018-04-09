package org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterEventosEstadisticos;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPersonaInfo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Equipo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.EventoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.FechaEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarEventosEstadisticos;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarJugadoresInfo;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarVariablesInfo;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarVariablesInfo2;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarVariablesInfo3;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InformacionFechaActivity extends AppCompatActivity {

    TextView resu1,resu2,resu3,resu4,resu5,resu6,resu7,resu8,resu9;
    TextView info_nombre_local,info_nombre_evento,info_nombre_rival;
    TextView Score;
    Context context;
    ProgressDialog progressDialog;

    List<Persona> Lista_Personas_Info;
    RecyclerView recyclerView;
    private AdapterPersonaInfo adapter;
    LinearLayoutManager linearLayoutManager;

    ImageView info_imagen_rival;

    int suma_goles,suma_og,suma_remate,suma_off,suma_ta,suma_tr,suma_falta,pos1,pos2,goles_local,goles_oponente;

    TextView v1,v2,v3,v4,v5,v6,v7,v8;
    TextView m1,m2,m3,m4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_fecha);
        recyclerView=findViewById(R.id.info_lista_jugadores);
        Lista_Personas_Info=new ArrayList<>();
        context=this;
        Score=findViewById(R.id.info_score);
        resu1=findViewById(R.id.resu1);
        resu2=findViewById(R.id.resu2);
        resu3=findViewById(R.id.resu3);
        resu4=findViewById(R.id.resu4);
        resu5=findViewById(R.id.resu5);
        resu6=findViewById(R.id.resu6);
        resu7=findViewById(R.id.resu7);
        resu8=findViewById(R.id.resu8);
        resu9=findViewById(R.id.resu9);
        v1=findViewById(R.id.v1);
        v2=findViewById(R.id.v2);
        v3=findViewById(R.id.v3);
        v4=findViewById(R.id.v4);
        v5=findViewById(R.id.v5);
        v6=findViewById(R.id.v6);
        v7=findViewById(R.id.v7);
        v8=findViewById(R.id.v8);
        m1=findViewById(R.id.mm1);
        m2=findViewById(R.id.mm2);
        m3=findViewById(R.id.mm3);
        m4=findViewById(R.id.mm4);

        info_nombre_local=findViewById(R.id.info_nombre_local);
        info_nombre_evento=findViewById(R.id.info_nombre_evento);
        info_nombre_rival=findViewById(R.id.info_nombre_rival);
        info_imagen_rival=findViewById(R.id.info_imagen_rival);

        linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        adapter = new AdapterPersonaInfo(this, Lista_Personas_Info, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        if(FechaEstadistico.FECHA_ESTADISTICO_TEMP.getFecha_actual()!=null){
            Listar_Informacion_Jugadores(FechaEstadistico.FECHA_ESTADISTICO_TEMP.getFecha_actual().getId(),context);
        }
    }
    private void Listar_Informacion_Jugadores(final int id,final Context context) {

        String id_fecha=String.valueOf(id);
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Información de Fecha:");
        progressDialog.setMessage("Mostrando Resultados...");
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("personas_info");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Persona p=new Persona();

                            p.setNombre_Persona(objeto.getString("NOMBRES"));
                            p.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            p.setFoto(objeto.getString("FOTO"));
                            p.setTiempo_jugado(objeto.getInt("TJ"));
                            p.setTotal(objeto.getInt("PTOS"));

                            Lista_Personas_Info.add(p);

                        }

                        adapter.notifyDataSetChanged();

                        Recuperar_Variables(id,context);

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

        RecuperarJugadoresInfo xx = new RecuperarJugadoresInfo(id_fecha,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    private void Recuperar_Variables(final int id,final Context context){

        String id_fecha=String.valueOf(id);
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                        suma_goles=jsonResponse.getInt("v_goles");
                        suma_og=jsonResponse.getInt("v_og");
                        suma_remate=jsonResponse.getInt("v_r");
                        suma_off=jsonResponse.getInt("v_of");
                        suma_ta=jsonResponse.getInt("v_ta");
                        suma_tr=jsonResponse.getInt("v_tr");
                        suma_falta=jsonResponse.getInt("v_f");
                        pos1=jsonResponse.getInt("v_pos1");
                        pos2=jsonResponse.getInt("v_pos2");
                        goles_local=jsonResponse.getInt("v_local");
                        goles_oponente=jsonResponse.getInt("v_opone");

                        Score.setText(goles_local+" - "+goles_oponente);

                        double p1=Double.parseDouble(String.valueOf(pos1));
                        double p2=Double.parseDouble(String.valueOf(pos2));

                        double pro=((p1+p2)/90)*100;
                        double resu=Math.floor(pro);
                        Integer r=(int)resu;
                        resu1.setText(String.valueOf(r)+"%");


                        resu2.setText(String.valueOf(suma_goles));
                        resu3.setText(String.valueOf(suma_og));
                        resu4.setText(String.valueOf(suma_remate));

                         double go=Double.parseDouble(String.valueOf(suma_goles));
                         double ogg=Double.parseDouble(String.valueOf(suma_og));
                         double d=go/ogg;
                         double re= d*100;
                         double ff= Math.floor(re);
                         Integer rr=(int)ff;
                            resu5.setText(String.valueOf(rr)+"%");


                        resu6.setText(String.valueOf(suma_off));
                        resu7.setText(String.valueOf(suma_falta));
                        resu8.setText(String.valueOf(suma_ta));
                        resu9.setText(String.valueOf(suma_tr));


                        Recuperar_Cabecera(id,context);
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

        RecuperarVariablesInfo xx = new RecuperarVariablesInfo(id_fecha,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    private void Recuperar_Cabecera(final int id,final Context context){
        String id_fecha=String.valueOf(id);
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                        info_nombre_local.setText(jsonResponse.getString("local").toUpperCase());
                        info_nombre_evento.setText(jsonResponse.getString("evento").toUpperCase());
                        info_nombre_rival.setText(jsonResponse.getString("oponente").toUpperCase());

                        String fo=jsonResponse.getString("oponente_foto");
                        Glide.with(context)
                                .load(fo)
                                .error(R.drawable.no_disponible)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(info_imagen_rival);


                        Recuperar_Dimensiones(id,context);
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

        RecuperarVariablesInfo2 xx = new RecuperarVariablesInfo2(id_fecha,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    private void Recuperar_Dimensiones(int id,final Context context){
        String id_fecha=String.valueOf(id);
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                        Verificar(v1,jsonResponse.getInt("V1"));
                        Verificar(v2,jsonResponse.getInt("V2"));
                        Verificar(v3,jsonResponse.getInt("V3"));
                        Verificar(v4,jsonResponse.getInt("V4"));
                        Verificar(v5,jsonResponse.getInt("V5"));
                        Verificar(v6,jsonResponse.getInt("V6"));
                        Verificar(v7,jsonResponse.getInt("V7"));
                        Verificar(v8,jsonResponse.getInt("V8"));

                        int v9=jsonResponse.getInt("V9");
                        int v10=jsonResponse.getInt("V10");
                        int v11=jsonResponse.getInt("V11");
                        int v12=jsonResponse.getInt("V12");
                        int v13=jsonResponse.getInt("V13");
                        int v14=jsonResponse.getInt("V14");
                        int v15=jsonResponse.getInt("V15");
                        int v16=jsonResponse.getInt("V16");

                        Verificar2(m1,(v9+v10));
                        Verificar2(m2,(v11+v12));
                        Verificar2(m3,(v13+v14));
                        Verificar2(m4,(v15+v16));


                        progressDialog.dismiss();
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

        RecuperarVariablesInfo3 xx = new RecuperarVariablesInfo3(id_fecha,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    private void Verificar(TextView te, int id) {

        switch (id){
            case 0:
                te.setBackgroundColor(context.getResources().getColor(R.color.blanco));
                te.setText(String.valueOf(id));
                break;
            case 1:
                te.setBackgroundColor(context.getResources().getColor(R.color.col1));
                te.setText(String.valueOf(id));
                break;
            case 2:
                te.setBackgroundColor(context.getResources().getColor(R.color.col2));
                te.setText(String.valueOf(id));
                break;
            case 3:
                te.setBackgroundColor(context.getResources().getColor(R.color.col3));
                te.setText(String.valueOf(id));
                break;
            case 4:
                te.setBackgroundColor(context.getResources().getColor(R.color.col4));
                te.setText(String.valueOf(id));
                break;
            case 5:
                te.setBackgroundColor(context.getResources().getColor(R.color.col5));
                te.setText(String.valueOf(id));
                break;
            case 6:
                te.setBackgroundColor(context.getResources().getColor(R.color.col6));
                te.setText(String.valueOf(id));
                break;
            case 7:
                te.setBackgroundColor(context.getResources().getColor(R.color.col7));
                te.setText(String.valueOf(id));
                break;
            case 8:
                te.setBackgroundColor(context.getResources().getColor(R.color.col8));
                te.setText(String.valueOf(id));
                break;
            case 9:
                te.setBackgroundColor(context.getResources().getColor(R.color.col9));
                te.setText(String.valueOf(id));
                break;
            case 10:
                te.setBackgroundColor(context.getResources().getColor(R.color.col10));
                te.setText(String.valueOf(id));
                break;

                default:
                    te.setBackgroundColor(context.getResources().getColor(R.color.col10));
                    te.setText(String.valueOf(id));
        }

    }
    private void Verificar2(TextView te, int id) {

        switch (id){
            case 0:
                te.setBackgroundColor(context.getResources().getColor(R.color.blanco));
                te.setText(String.valueOf(id));
                break;
            case 1:
                te.setBackgroundColor(context.getResources().getColor(R.color.mm1));
                te.setText(String.valueOf(id));
                break;
            case 2:
                te.setBackgroundColor(context.getResources().getColor(R.color.mm2));
                te.setText(String.valueOf(id));
                break;
            case 3:
                te.setBackgroundColor(context.getResources().getColor(R.color.mm3));
                te.setText(String.valueOf(id));
                break;
            case 4:
                te.setBackgroundColor(context.getResources().getColor(R.color.mm4));
                te.setText(String.valueOf(id));
                break;
            case 5:
                te.setBackgroundColor(context.getResources().getColor(R.color.mm5));
                te.setText(String.valueOf(id));
                break;
            case 6:
                te.setBackgroundColor(context.getResources().getColor(R.color.mm6));
                te.setText(String.valueOf(id));
                break;
            case 7:
                te.setBackgroundColor(context.getResources().getColor(R.color.mm7));
                te.setText(String.valueOf(id));
                break;
            case 8:
                te.setBackgroundColor(context.getResources().getColor(R.color.mm8));
                te.setText(String.valueOf(id));
                break;
            case 9:
                te.setBackgroundColor(context.getResources().getColor(R.color.mm9));
                te.setText(String.valueOf(id));
                break;
            case 10:
                te.setBackgroundColor(context.getResources().getColor(R.color.mm10));
                te.setText(String.valueOf(id));
                break;

            default:
                    te.setBackgroundColor(context.getResources().getColor(R.color.mm10));
                te.setText(String.valueOf(id));
        }

    }
    public void onBackPressed() {
        Intent intent=new Intent(context,ListaFechasEstadisticosActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);


    }

}
