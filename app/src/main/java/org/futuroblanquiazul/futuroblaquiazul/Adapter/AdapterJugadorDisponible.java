package org.futuroblanquiazul.futuroblaquiazul.Adapter;

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
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.BarrioIntimoJugadoresActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.BarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActivarEvaPersona;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.AgregarJugadorEvento;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterJugadorDisponible extends RecyclerView.Adapter<AdapterJugadorDisponible.ViewHolder>{
    public Context context;
    private List<Persona> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AdapterJugadorDisponible(Context context, List<Persona> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {


         ImageView accion;
         ImageView foto;
         TextView nombre_persona;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            accion=itemView.findViewById(R.id.card_disponible_accion);
            foto=itemView.findViewById(R.id.card_disponible_foto);
            nombre_persona=itemView.findViewById(R.id.card_disponible_nombre);
        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_jugador_disponible,parent,false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.nombre_persona.setText(my_Data.get(position).getNombre_Persona()+" "+my_Data.get(position).getApellidos_Persona());

        Glide.with(context).load(my_Data.get(position).getFoto()).error(R.drawable.user_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.foto);

        holder.accion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Agregar_Persona_a_Evento(my_Data.get(position).getId(),context);
            }
        });
    }

    private void Agregar_Persona_a_Evento(int id,final Context context) {
        String id_evento=String.valueOf(Recursos_Mantenimientos.TEMP.getEvento_temporal().getId());
        String id_persona=String.valueOf(id);
        String id_usuario=String.valueOf(Usuario.SESION_ACTUAL.getId());
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        BarrioIntimoJugadoresActivity.GESTOR.Actualizar_Listas(context);
                    }else {
                        Toast.makeText(context, "No se ha podido Agregar", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        AgregarJugadorEvento validarSesion = new AgregarJugadorEvento(id_evento,id_persona,id_usuario, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(validarSesion);
    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }
}