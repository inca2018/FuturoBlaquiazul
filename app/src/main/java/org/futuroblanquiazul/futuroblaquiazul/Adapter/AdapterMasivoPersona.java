package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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


import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.PruebaDiagnosticoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.ListaPersonaMasivoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.MasivoResultadosActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.EliminarResultados;
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
public class AdapterMasivoPersona extends RecyclerView.Adapter<AdapterMasivoPersona.ViewHolder>{
    public Context context;
    private List<Persona> my_Data;
    ProgressDialog progressDialog;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AdapterMasivoPersona(Context context, List<Persona> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public TextView titulo_masivo;
        public ImageView acciones;
        public TextView texto_disponible;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            titulo_masivo=itemView.findViewById(R.id.card_masivo_titulo);
            acciones=itemView.findViewById(R.id.masivo_personas_acciones);
            texto_disponible=itemView.findViewById(R.id.texto_disponibilidad);
        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_masivo_persona,parent,false);
        return new ViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.titulo_masivo.setText(my_Data.get(position).getNombre_Persona()+" "+my_Data.get(position).getApellidos_Persona());
            int disponibilidad=my_Data.get(position).getDisponible();
            if(disponibilidad==1){
                holder.texto_disponible.setText("DISPONIBLE");
                holder.texto_disponible.setTextColor(context.getResources().getColor(R.color.verde));
            }else if(disponibilidad==2){
                holder.texto_disponible.setText("NO DISPONIBLE");
                holder.texto_disponible.setTextColor(context.getResources().getColor(R.color.red));
            }
            holder.acciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupMenu popupMenu=new PopupMenu(context,holder.acciones);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_item_masivo,popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if(item.getTitle().toString().equalsIgnoreCase("Evaluar")){
                                int estado=my_Data.get(position).getEstado_capta();
                                int disponibilidad=my_Data.get(position).getDisponible();
                                if(disponibilidad==1){
                                    if(estado==2){
                                        Toast.makeText(context, "Postulante ya tiene una Evaluaciòn Realizada!", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Persona.PERSONA_TEMP.setId(my_Data.get(position).getId());
                                        Persona.PERSONA_TEMP.setNombre_Persona(my_Data.get(position).getNombre_Persona());
                                        Persona.PERSONA_TEMP.setApellidos_Persona(my_Data.get(position).getApellidos_Persona());

                                        Intent intent = new Intent(context, PruebaDiagnosticoActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        context.startActivity(intent);

                                    }

                                }else{
                                    Toast.makeText(context, "Potulante no Disponible", Toast.LENGTH_SHORT).show();
                                }

                            }else if(item.getTitle().toString().equalsIgnoreCase("Resultados")){
                                int estado=my_Data.get(position).getEstado_capta();
                                if(estado==2){

                                    Persona.PERSONA_TEMP.setId(my_Data.get(position).getId());
                                    Persona.PERSONA_TEMP.setNombre_Persona(my_Data.get(position).getNombre_Persona());
                                    Persona.PERSONA_TEMP.setApellidos_Persona(my_Data.get(position).getApellidos_Persona());

                                    Recuperar_Resultados(Persona.PERSONA_TEMP.getId(),context);

                                }else if(estado==1){
                                    Toast.makeText(context, "Postulante no tiene Evaluaciòn Disponible", Toast.LENGTH_SHORT).show();
                                }
                            }else if(item.getTitle().toString().equalsIgnoreCase("Eliminar")){
                                int estado=my_Data.get(position).getEstado_capta();
                                if(estado==2){

                                    final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                                    builder.setTitle("RESULTADOS")
                                            .setMessage("¿Desea Eliminar Resultados?")
                                            .setPositiveButton("SI",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                       Persona.PERSONA_TEMP.setId(my_Data.get(position).getId());
                                       Persona.PERSONA_TEMP.setNombre_Persona(my_Data.get(position).getNombre_Persona());
                                       Persona.PERSONA_TEMP.setApellidos_Persona(my_Data.get(position).getApellidos_Persona());

                                          int id_persona=Persona.PERSONA_TEMP.getId();
                                          Eliminar_Resultados(id_persona,context);

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
                                }else{
        Toast.makeText(context, "Postulante no tiene Evaluaciones para eliminar", Toast.LENGTH_SHORT).show();
                                }


                            }
                            return true;
                        }
                    });

                    popupMenu.show();
                }
            });

    }

    private void Eliminar_Resultados(final  int id_per,final Context context) {

        String id_persona=String.valueOf(id_per);
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Resultados");
        progressDialog.setMessage("Eliminado Resultados...");
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        Toast.makeText(context, "Resultados Eliminados Correctamente", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        Intent intent = new Intent(context, ListaPersonaMasivoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "No se Pudo ELiminar Resultados", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar Resultados :"+e);
                }
            }
        };

        EliminarResultados xx = new EliminarResultados(id_persona,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


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

                        for(int i=0;i<ResultadosDiagnostico.RESULTADO_TEMP.getFISICO().size();i++){
                            debug("FISICO Nº "+i+" "+ResultadosDiagnostico.RESULTADO_TEMP.getFISICO().get(i));
                        }

                        for(int i=0;i<ResultadosDiagnostico.RESULTADO_TEMP.getSOCIAL().size();i++){
                            debug("SOCIAL Nº "+i+" "+ResultadosDiagnostico.RESULTADO_TEMP.getSOCIAL().get(i));
                        }

                        for(int i=0;i<ResultadosDiagnostico.RESULTADO_TEMP.getCAPACIDAD().size();i++){
                            debug("CAPACIDAD Nº "+i+" "+ResultadosDiagnostico.RESULTADO_TEMP.getCAPACIDAD().get(i));
                        }

                        for(int i=0;i<ResultadosDiagnostico.RESULTADO_TEMP.getTECNICO().size();i++){
                            debug("TECNICO Nº "+i+" "+ResultadosDiagnostico.RESULTADO_TEMP.getTECNICO().get(i));
                        }

                        for(int i=0;i<ResultadosDiagnostico.RESULTADO_TEMP.getPSICO().size();i++){
                            debug("PSICO Nº "+i+" "+ResultadosDiagnostico.RESULTADO_TEMP.getPSICO().get(i));
                        }



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
    public void debug(String msn){
        System.out.println(msn);
    }

}