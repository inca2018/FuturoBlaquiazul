package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.ListaSeguimientosActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.CaptacionMasiva.MasivoResultadosActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Seguimiento;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarResultadosDiagnostico;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Recuperar_Sugerido;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.ResultadosDiagnostico;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterSeguimientoPersona extends RecyclerView.Adapter<AdapterSeguimientoPersona.ViewHolder>{
    public Context context;
    private List<Persona> my_Data;
    ProgressDialog progressDialog;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AdapterSeguimientoPersona(Context context, List<Persona> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public TextView Nombre_persona;
        public ImageView acciones;
        public TextView texto_seguimientos_totales;
        public TextView texto_seguimiento_ubigeo;
        public ImageView foto_per_seguimiento;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            Nombre_persona=itemView.findViewById(R.id.card_seguimiento_titulo);
            acciones=itemView.findViewById(R.id.card_seguimiento_personas_acciones);
            texto_seguimientos_totales=itemView.findViewById(R.id.card_seguimiento_seguimientos);
            texto_seguimiento_ubigeo=itemView.findViewById(R.id.card_seguimiento_ubigeo);
            foto_per_seguimiento=itemView.findViewById(R.id.foto_per_seguimiento);
        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_seguimiento_persona,parent,false);
        return new ViewHolder(itemView);


    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

            Glide.with(context)
                    .load(my_Data.get(position).getFoto())
                    .error(R.drawable.user_default)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(holder.foto_per_seguimiento);

              holder.Nombre_persona.setText(my_Data.get(position).getNombre_Persona()+" "+my_Data.get(position).getApellidos_Persona());
              holder.texto_seguimientos_totales.setText("Cantidad de Seguimientos: "+my_Data.get(position).getTotales_seguimientos());
              holder.texto_seguimiento_ubigeo.setText("Ubicación de Seguimiento: "+my_Data.get(position).getUbigeo());
              holder.acciones.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      final PopupMenu popupMenu=new PopupMenu(context,holder.acciones);
                      popupMenu.getMenuInflater().inflate(R.menu.menu_item_seguimiento,popupMenu.getMenu());
                      popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                          @Override
                          public boolean onMenuItemClick(MenuItem item) {

                              if(item.getTitle().toString().equalsIgnoreCase("Mis Seguimientos")){

                                  Usuario.SESION_ACTUAL.setPersona_seguimiento(my_Data.get(position));

                                  Seguimiento.SEGUIMIENTO.setPersona(my_Data.get(position));
                                  Intent intent = new Intent(context,ListaSeguimientosActivity.class);
                                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                  context.startActivity(intent);
                              }else if(item.getTitle().toString().equalsIgnoreCase("Resultado Diagnostico")){

                                  Usuario.SESION_ACTUAL.setPersona_seguimiento(my_Data.get(position));
                                  Recuperar_Resultados(Usuario.SESION_ACTUAL.getPersona_seguimiento().getId(),context);
                              }

                              return true;
                          }
                      });

                      popupMenu.show();

                  }
              });
    }


    @Override
    public int getItemCount() {
        return my_Data.size();
    }

    private void Recuperar_Resultados(final int id,final Context context) {
        String id_persona=String.valueOf(id);

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Resultados");
        progressDialog.setMessage("Recuperando Resultados...");
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        List<Integer> RespFisico=new ArrayList<>();
                        RespFisico.add(jsonResponse.getInt("f1"));
                        RespFisico.add(jsonResponse.getInt("f2"));
                        RespFisico.add(jsonResponse.getInt("f3"));
                        RespFisico.add(jsonResponse.getInt("f4"));
                        RespFisico.add(jsonResponse.getInt("f5"));
                        RespFisico.add(jsonResponse.getInt("f6"));
                        RespFisico.add(jsonResponse.getInt("f7"));
                        ResultadosDiagnostico.RESULTADO_TEMP.setFISICO(RespFisico);

                        List<Integer> RespCapacidad=new ArrayList<>();
                        RespCapacidad.add(jsonResponse.getInt("c1"));
                        RespCapacidad.add(jsonResponse.getInt("c2"));
                        RespCapacidad.add(jsonResponse.getInt("c3"));
                        RespCapacidad.add(jsonResponse.getInt("c4"));

                        ResultadosDiagnostico.RESULTADO_TEMP.setCAPACIDAD(RespCapacidad);
                        List<Integer> RespSocial=new ArrayList<>();
                        RespSocial.add(jsonResponse.getInt("s1"));
                        RespSocial.add(jsonResponse.getInt("s2"));
                        RespSocial.add(jsonResponse.getInt("s3"));
                        RespSocial.add(jsonResponse.getInt("s4"));

                        ResultadosDiagnostico.RESULTADO_TEMP.setSOCIAL(RespSocial);
                        List<Integer> RespTecnico=new ArrayList<>();
                        RespTecnico.add(jsonResponse.getInt("t1"));
                        RespTecnico.add(jsonResponse.getInt("t2"));
                        RespTecnico.add(jsonResponse.getInt("t3"));
                        RespTecnico.add(jsonResponse.getInt("t4"));
                        RespTecnico.add(jsonResponse.getInt("t5"));
                        RespTecnico.add(jsonResponse.getInt("t6"));

                        ResultadosDiagnostico.RESULTADO_TEMP.setTECNICO(RespTecnico);

                        List<Integer> RespPsico=new ArrayList<>();
                        RespPsico.add(jsonResponse.getInt("p1"));
                        RespPsico.add(jsonResponse.getInt("p2"));
                        RespPsico.add(jsonResponse.getInt("p3"));
                        RespPsico.add(jsonResponse.getInt("p4"));

                        ResultadosDiagnostico.RESULTADO_TEMP.setPSICO(RespPsico);

                        ResultadosDiagnostico.RESULTADO_TEMP.setFecha_registro(jsonResponse.getString("fecha_registro"));
                        ResultadosDiagnostico.RESULTADO_TEMP.setSugerido1(jsonResponse.getInt("sugerido1"));
                        ResultadosDiagnostico.RESULTADO_TEMP.setSugerido2(jsonResponse.getInt("sugerido2"));
                        ResultadosDiagnostico.RESULTADO_TEMP.setSugerid3(jsonResponse.getInt("sugerido3"));
                        ResultadosDiagnostico.RESULTADO_TEMP.setLaterlaidad(jsonResponse.getString("lateralidad"));

                        ResultadosDiagnostico.RESULTADO_TEMP.setTotal_fisico(jsonResponse.getInt("total_fisico"));
                        ResultadosDiagnostico.RESULTADO_TEMP.setTotal_capacidad(jsonResponse.getInt("total_capacidad"));
                        ResultadosDiagnostico.RESULTADO_TEMP.setTotal_social(jsonResponse.getInt("total_social"));
                        ResultadosDiagnostico.RESULTADO_TEMP.setTotal_tecnico(jsonResponse.getInt("total_tecnico"));
                        ResultadosDiagnostico.RESULTADO_TEMP.setTotal_psico(jsonResponse.getInt("totalpsico"));
                        ResultadosDiagnostico.RESULTADO_TEMP.setNombre_Scout(jsonResponse.getString("scout").toUpperCase());

                        Recuperar_Posiciones(ResultadosDiagnostico.RESULTADO_TEMP.getSugerido1(),ResultadosDiagnostico.RESULTADO_TEMP.getSugerido2(),ResultadosDiagnostico.RESULTADO_TEMP.getSugerid3(),context);




                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "No se encontraron Resultados Diagnostico", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar Resultados :"+e);
                }
            }
        };

        RecuperarResultadosDiagnostico xx = new RecuperarResultadosDiagnostico(id_persona,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }
    private void Recuperar_Posiciones(final int sug1,final int sug2,final int sug3,final Context context) {

        String sugerido1=String.valueOf(sug1);
        String sugerido2=String.valueOf(sug2);
        String sugerido3=String.valueOf(sug3);
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        List<String> Sugerido=new ArrayList<>();
                        String sug1=jsonResponse.getString("sugerido1");
                        int estado1=jsonResponse.getInt("estado1");
                        String sug2=jsonResponse.getString("sugerido2");
                        int estado2=jsonResponse.getInt("estado2");
                        String sug3=jsonResponse.getString("sugerido3");
                        int estado3=jsonResponse.getInt("estado3");

                        if(estado1==2){
                            Sugerido.add(sug1);
                        }else{

                        }
                        if(estado2==2){
                            Sugerido.add(sug2);
                        }else{

                        }
                        if(estado3==2){
                            Sugerido.add(sug3);
                        }else{

                        }

                        ResultadosDiagnostico.RESULTADO_TEMP.setSugeridos(Sugerido);
                        Intent intent=new Intent(context, MasivoResultadosActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        progressDialog.dismiss();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "No se encontraron Resultados Posiciones", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar Resultados :"+e);
                }
            }
        };

        Recuperar_Sugerido xx = new Recuperar_Sugerido(sugerido1,sugerido2,sugerido3,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);
    }

}