package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.futuroblanquiazul.futuroblaquiazul.Entity.BarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Masivo;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterBarrio extends RecyclerView.Adapter<AdapterBarrio.ViewHolder>{
    public Context context;
    private List<BarrioIntimo> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AdapterBarrio(Context context, List<BarrioIntimo> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public TextView titulo_barrio;
        public TextView ubigeo_barrio;
        public TextView creador_barrio;
        public TextView total_barrio;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            titulo_barrio=itemView.findViewById(R.id.card_barrio_titulo);
            ubigeo_barrio=itemView.findViewById(R.id.card_barrio_ubigeo);
            creador_barrio=itemView.findViewById(R.id.card_barrio_creador);
            total_barrio=itemView.findViewById(R.id.card_cant);
        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_barrio_intimo,parent,false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.titulo_barrio.setText("EVENTO: "+ my_Data.get(position).getNombreEvento());
            holder.creador_barrio.setText("SCOUT : "+my_Data.get(position).getUsuario().getUsuario());
            holder.ubigeo_barrio.setText("UBIGEO: "+my_Data.get(position).getDescripcion_ubigeo());
            holder.total_barrio.setText(String.valueOf(my_Data.get(position).getCantidad_Postulantes()));


    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }
}