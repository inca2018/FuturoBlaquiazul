package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import org.futuroblanquiazul.futuroblaquiazul.Entity.Masivo;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.List;



/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterMasivo extends RecyclerView.Adapter<AdapterMasivo.ViewHolder>{
    public Context context;
    private List<Masivo> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AdapterMasivo(Context context, List<Masivo> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public TextView titulo_masivo;
        public TextView ubigeo_masivo;
        public TextView creador_masivo;
        public TextView total_masivo;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            titulo_masivo=itemView.findViewById(R.id.card_masivo_titulo);
            ubigeo_masivo=itemView.findViewById(R.id.card_masivo_ubigeo);
            creador_masivo=itemView.findViewById(R.id.card_masivo_creador);
            total_masivo=itemView.findViewById(R.id.card_masivo_cantidad_postulantes);
        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_masivo,parent,false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.titulo_masivo.setText(my_Data.get(position).getNombre_Masivo());
        String ubigeo_Desc="Ubicación: "+my_Data.get(position).getDepartamento().getDescripcion()+"/"+my_Data.get(position).getProvincia().getDescripcion()+"/"+my_Data.get(position).getDistrito().getDescripcion();
        holder.ubigeo_masivo.setText(ubigeo_Desc);
        holder.creador_masivo.setText("Registrado por: "+my_Data.get(position).getUsuario_Creador());
        holder.total_masivo.setText(String.valueOf(my_Data.get(position).getTotal_postulantes()));

    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }
}