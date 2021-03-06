package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.List;

public class AdapterMetodologiaPersona extends RecyclerView.Adapter<AdapterMetodologiaPersona.ViewHolder>{
    public Context context;
    private List<Persona> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AdapterMetodologiaPersona(Context context, List<Persona> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {


        TextView nom;
        ImageView acciones;
        ImageView metodologia_persona_foto;
        TextView estado;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nom=itemView.findViewById(R.id.card_metodologia_jugador);
            metodologia_persona_foto=itemView.findViewById(R.id.metodologia_persona_foto);
            estado=itemView.findViewById(R.id.card_metodologia_estado);

        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_metodologia_persona,parent,false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.nom.setText(my_Data.get(position).getNombre_Persona()+" "+my_Data.get(position).getApellidos_Persona());
        Glide.with(context)
                .load(my_Data.get(position).getFoto()).into(holder.metodologia_persona_foto);


        if(my_Data.get(position).getEstado()==1){
            holder.estado.setText("TITULAR");
            holder.estado.setTextColor(context.getResources().getColor(R.color.morado_bajo));
        }else{
            holder.estado.setText("PRUEBA");
            holder.estado.setTextColor(context.getResources().getColor(R.color.deep_naranja400));
        }
    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }

    public int J_Titulares(){
        int to=0;
        for(int i=0;i<my_Data.size();i++){
            if(my_Data.get(i).getEstado()==1){
                to=to+1;
            }
        }
        return to;
    }
    public int J_Prueba(){
        int to=0;
        for(int i=0;i<my_Data.size();i++){
            if(my_Data.get(i).getEstado()==2){
                to=to+1;
            }
        }
        return to;
    }

}