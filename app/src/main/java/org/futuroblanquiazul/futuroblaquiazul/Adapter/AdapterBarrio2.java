package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.EdicionBarrioIntimoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.BarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;

import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterBarrio2 extends RecyclerView.Adapter<AdapterBarrio2.ViewHolder>{
    public Context context;
    private List<BarrioIntimo> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AdapterBarrio2(Context context, List<BarrioIntimo> my_Data, RecyclerViewOnItemClickListener
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
        LinearLayout editar;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            titulo_barrio=itemView.findViewById(R.id.card_barrio_titulo);
            ubigeo_barrio=itemView.findViewById(R.id.card_barrio_ubigeo);
            creador_barrio=itemView.findViewById(R.id.card_barrio_creador);
            total_barrio=itemView.findViewById(R.id.card_cant);
            editar=itemView.findViewById(R.id.mant_barrio_editar);
        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_barrio_intimo2,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.titulo_barrio.setText("EVENTO: "+ my_Data.get(position).getNombreEvento());
            holder.creador_barrio.setText("SCOUT : "+my_Data.get(position).getUsuario().getNombres()+" "+my_Data.get(position).getUsuario().getApellidos());
            holder.ubigeo_barrio.setText("UBIGEO: "+my_Data.get(position).getDescripcion_ubigeo());
            holder.total_barrio.setText(String.valueOf(my_Data.get(position).getCantidad_Postulantes()));

            holder.editar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Recursos_Mantenimientos.TEMP.setEvento_temporal(my_Data.get(position));
                    Intent intent =new Intent(context,EdicionBarrioIntimoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }
            });


    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }
}