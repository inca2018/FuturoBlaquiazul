package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Entity.BarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.EtapaPruebas;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.TipoPruebas;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarEtapaFasePruebas;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarInformacionDiagnostico;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static org.futuroblanquiazul.futuroblaquiazul.Entity.InformacionDiagnostico.INFO_DIAG;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterEtapaPruebas extends RecyclerView.Adapter<AdapterEtapaPruebas.ViewHolder>{
    public Context context;
    private List<EtapaPruebas> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    ProgressDialog progressDialog;
    String sugerido="";

    AlertDialog da;

    public AdapterEtapaPruebas(Context context, List<EtapaPruebas> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

          TextView num,tipo,detalle;
          ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            num=itemView.findViewById(R.id.card_etapa_numero);
            tipo=itemView.findViewById(R.id.card_etapa_tipo);
            detalle=itemView.findViewById(R.id.card_etapa_detalle);
            image=itemView.findViewById(R.id.card_etapa_image);

        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_etapa_pruebas,parent,false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        SpannableString mitextoU = new SpannableString("Detalles");
        mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
        holder.detalle.setText(mitextoU);


            holder.num.setText("Nº "+my_Data.get(position).getNum());
            holder.tipo.setText(my_Data.get(position).getTipoPruebas().getDescripcion());

            switch (my_Data.get(position).getTipoPruebas().getId()){

                case 1:
                    holder.image.setImageResource(R.mipmap.ic_prueba_diagnostico);

                    holder.detalle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            debug("CLICK EN DETALLE DIAGNOSTICO ");

                            Recupera_Informacion_Diagnostico(my_Data.get(position).getPersona().getId(),my_Data.get(position).getId_evaluacion(),context);
                        }
                    });
                    break;

                case 2:
                    holder.image.setImageResource(R.mipmap.image_fisico);
                    break;
                case 3:
                    holder.image.setImageResource(R.mipmap.image_capacidad);
                    break;
                case 4:
                    holder.image.setImageResource(R.mipmap.image_tecnico);
                    break;
                case 5:
                    holder.image.setImageResource(R.mipmap.image_psico);
                    break;
                case 6:
                    holder.image.setImageResource(R.mipmap.ic_prueba_nutricional);
                    break;



            }

    }

    private void Recupera_Informacion_Diagnostico(int id_per, int id_eva,final Context context2) {

        debug("INGRESO A RECUPERAR DIAGNOSTICO");
        String id_persona=String.valueOf(id_per);
        String id_diagnostico=String.valueOf(id_eva);
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Información:");
        progressDialog.setMessage("Buscando Información de Prueba...");
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        progressDialog.dismiss();

                        debug("RECUPERO DATOS DIAGNOSTICO CORRECTAMENTE");
                         INFO_DIAG.setId(jsonResponse.getInt("id"));
                         INFO_DIAG.setFecha_registro(jsonResponse.getString("fecha_registro"));
                         INFO_DIAG.setUbigeo(jsonResponse.getString("ubigeo"));
                         INFO_DIAG.setUsuario(jsonResponse.getString("usuario"));
                         INFO_DIAG.setTotal_fisico(jsonResponse.getInt("total_fisico"));
                         INFO_DIAG.setTotal_social(jsonResponse.getInt("total_social"));
                         INFO_DIAG.setTotal_capacidad(jsonResponse.getInt("total_capacidad"));
                         INFO_DIAG.setTotal_tecnico(jsonResponse.getInt("total_tecnico"));
                         INFO_DIAG.setTotal_psico(jsonResponse.getInt("total_psico"));
                         INFO_DIAG.setTotal_general(jsonResponse.getInt("total_general"));
                         INFO_DIAG.setEstado_sugerido1(jsonResponse.getInt("estado_sugerido1"));
                         INFO_DIAG.setEstado_sugerido2(jsonResponse.getInt("estado_sugerido2"));
                         INFO_DIAG.setEstado_sugerido3(jsonResponse.getInt("estado_sugerido3"));
                         INFO_DIAG.setNom_sugerido1(jsonResponse.getString("desc_sugerido1"));
                         INFO_DIAG.setNom_sugerido2(jsonResponse.getString("desc_sugerido2"));
                         INFO_DIAG.setNom_sugerido3(jsonResponse.getString("desc_sugerido3"));
                         INFO_DIAG.setLateralidad(jsonResponse.getString("lateralidad"));
                         INFO_DIAG.setEstado(jsonResponse.getInt("estado"));

                        Mostrar_Dialog_Diagnostico(context2);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "No existe Informacion de diagnostico", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar Info diagnostico :"+e);
                }
            }
        };

        RecuperarInformacionDiagnostico xx = new RecuperarInformacionDiagnostico(id_persona,id_diagnostico,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }

    private void Mostrar_Dialog_Diagnostico(Context context2) {

                            final LayoutInflater inflater = (LayoutInflater) context2.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                            final View dialoglayout = inflater.inflate(R.layout.area_info_prueba_diagnostico, null);

                            TextView  t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11 ;

                            t1=dialoglayout.findViewById(R.id.card_info_diagnostico_fecha);
                            t2=dialoglayout.findViewById(R.id.card_info_diagnostico_user);
                            t3=dialoglayout.findViewById(R.id.card_info_diagnostico_ubigeo);
                            t4=dialoglayout.findViewById(R.id.card_info_diagnostico_sugeridos);
                            t5=dialoglayout.findViewById(R.id.card_info_diagnostico_lateralidad);
                            t6=dialoglayout.findViewById(R.id.card_info_diagnostico_total_fisico);
                            t7=dialoglayout.findViewById(R.id.card_info_diagnostico_total_social);
                            t8=dialoglayout.findViewById(R.id.card_info_diagnostico_total_capacidad);
                            t9=dialoglayout.findViewById(R.id.card_info_diagnostico_total_tecnico);
                            t10=dialoglayout.findViewById(R.id.card_info_diagnostico_total_psico);
                            t11=dialoglayout.findViewById(R.id.card_info_diagnostico_total_general);

                            final AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
                            builder4.setView(dialoglayout);
                            da=builder4.show();

                            t1.setText(INFO_DIAG.getFecha_registro().toString());
                            t2.setText(INFO_DIAG.getUsuario().toString());
                            t3.setText(INFO_DIAG.getUbigeo().toString());

                            if(INFO_DIAG.getEstado_sugerido1()==2){
                                   sugerido=sugerido+"- "+INFO_DIAG.getNom_sugerido1()+"\n";
                            }
                            if(INFO_DIAG.getEstado_sugerido2()==2){
                                   sugerido=sugerido+"- "+INFO_DIAG.getNom_sugerido2()+"\n";
                            }
                            if(INFO_DIAG.getEstado_sugerido3()==2){
                                   sugerido=sugerido+"- "+INFO_DIAG.getNom_sugerido3()+"\n";
                            }

                            t4.setText(sugerido.toString());
                            t5.setText(INFO_DIAG.getLateralidad());

                            t6.setText(String.valueOf(INFO_DIAG.getTotal_fisico()));
                            t7.setText(String.valueOf(INFO_DIAG.getTotal_social()));
                            t8.setText(String.valueOf(INFO_DIAG.getTotal_capacidad()));
                            t9.setText(String.valueOf(INFO_DIAG.getTotal_tecnico()));
                            t10.setText(String.valueOf(INFO_DIAG.getTotal_psico()));
                            t11.setText(String.valueOf(INFO_DIAG.getTotal_general()));


    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }


    public void debug(String sm){
        System.out.println(sm);
    }
}