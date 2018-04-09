package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.AlertDialog;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.futuroblanquiazul.futuroblaquiazul.Activities.CaptacionMasiva.MasivoResultadosActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.EdicionPersonaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;

import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterPersonaInfo extends RecyclerView.Adapter<AdapterPersonaInfo.ViewHolder>{
    public Context context;
    private List<Persona> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    AlertDialog da;

    public AdapterPersonaInfo(Context context, List<Persona> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {


         TextView Nombres_persona,total,tiempo;
         ImageView foto_persona;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            Nombres_persona=itemView.findViewById(R.id.info_per_nom);
            total=itemView.findViewById(R.id.info_per_total);
            foto_persona=itemView.findViewById(R.id.info_per_foto);
            tiempo=itemView.findViewById(R.id.info_per_tiempo_jugado);



        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_info_equipo_estadistico2,parent,false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.Nombres_persona.setText(my_Data.get(position).getNombre_Persona()+" "+my_Data.get(position).getApellidos_Persona());

        Glide.with(context)
                .load(my_Data.get(position).getFoto())
                .error(R.drawable.user_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.foto_persona);

        holder.total.setText(String.valueOf(my_Data.get(position).getTotal()));

        holder.tiempo.setText(String.valueOf(my_Data.get(position).getTiempo_jugado()+"´"));


    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }
}