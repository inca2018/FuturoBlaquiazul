package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.List;

public class AdapterMetodologiaPersonaPrueba extends RecyclerView.Adapter<AdapterMetodologiaPersonaPrueba.ViewHolder>{
    public Context context;
    private List<Persona> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AdapterMetodologiaPersonaPrueba(Context context, List<Persona> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {


        TextView nom;
        ImageView acciones;
        TextView fecha;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nom=itemView.findViewById(R.id.card_metodologia_fase_pruebas_jugador);
            acciones=itemView.findViewById(R.id.metodologia_personas_fase_pruebas_acciones);
            fecha=itemView.findViewById(R.id.card_metodologia_fase_pruebas_fecha);

        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_metodologia_persona_fase_pruebas,parent,false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.nom.setText(my_Data.get(position).getNombre_Persona()+" "+my_Data.get(position).getApellidos_Persona());
        holder.fecha.setText("Fecha de Aceptación: "+my_Data.get(position).getFecha_Ultima_modificacion());

    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }
}