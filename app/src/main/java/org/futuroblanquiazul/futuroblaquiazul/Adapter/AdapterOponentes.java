package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.MantenimientoOponentesActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.MantenimientoPosicionesActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Oponente;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Posicion;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.EliminarOponentes;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.EliminarPos1;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdapterOponentes extends RecyclerView.Adapter<AdapterOponentes.ViewHolder> {
    private Context context;
    private List<Oponente> my_Data;

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    AlertDialog da;

    public AdapterOponentes(Context context, List<Oponente> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView num,nombre,abreviado;
        ImageView eliminar,editar;
        ImageView foto;
        public ViewHolder(View itemView) {
            super(itemView);
         num=itemView.findViewById(R.id.card_mant_opo_numero);
         nombre=itemView.findViewById(R.id.card_mant_opo_nombre);
         abreviado=itemView.findViewById(R.id.card_mant_opo_abreviado);
         eliminar=itemView.findViewById(R.id.card_mant_opo_eliminar);
            editar=itemView.findViewById(R.id.card_mant_opo_editar);
            foto=itemView.findViewById(R.id.card_mant_opo_foto);

        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_mant_oponente,parent,false);
        return new ViewHolder(itemView);

    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

      holder.num.setText(String.valueOf(my_Data.get(position).getNum()));
      holder.nombre.setText(my_Data.get(position).getNombre_Oponente());
      holder.abreviado.setText(my_Data.get(position).getAbreviado());
        Glide.with(context).load(my_Data.get(position).getFoto()).into(holder.foto);

       holder.eliminar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Eliminar_Oponente(my_Data.get(position).getId(),context,position);

           }
       });

       holder.editar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Recursos_Mantenimientos.TEMP.setOponente_temporal(my_Data.get(position)) ;
               MantenimientoOponentesActivity.OPONENTE_TEMP.Mostrar_Oponente(context);
           }
       });
    }

    private void Eliminar_Oponente(int id,final Context context,final int pos) {
        String id_posicion=String.valueOf(id);
        debug("CODIGO ENVIADO "+id_posicion);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Toast.makeText(context, "Posición Eliminado Correctamente!", Toast.LENGTH_SHORT).show();

                        MantenimientoOponentesActivity.OPONENTE_TEMP.Actualizar_Oponentes(context);

                    }else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        EliminarOponentes xx = new EliminarOponentes(id_posicion, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }


    @Override
    public int getItemCount() {
        return my_Data.size();
    }


    public void debug(String d){
        System.out.println(d);
    }

}
