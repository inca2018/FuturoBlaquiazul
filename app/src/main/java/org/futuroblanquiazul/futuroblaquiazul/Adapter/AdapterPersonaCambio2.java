package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PersonaEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Estadistico_Gestion;

import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterPersonaCambio2 extends RecyclerView.Adapter<AdapterPersonaCambio2.ViewHolder>{
    public Context context;
    private List<PersonaEstadistico> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    AlertDialog da;

    public AdapterPersonaCambio2(Context context, List<PersonaEstadistico> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {


         ImageView foto_persona;
         TextView nom_persona;
         CardView cardView_Seleccion;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            foto_persona=itemView.findViewById(R.id.card_cambio_foto);
            nom_persona=itemView.findViewById(R.id.card_cambio_nombre);
            cardView_Seleccion=itemView.findViewById(R.id.card_cambio_seleccion);


        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_persona_cambio,parent,false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        if(my_Data.get(position).isActivo()==true){
            holder.cardView_Seleccion.setCardBackgroundColor(context.getResources().getColor(R.color.blue));
            holder.nom_persona.setTextColor(context.getResources().getColor(R.color.blanco));
        }else{
            holder.cardView_Seleccion.setCardBackgroundColor(context.getResources().getColor(R.color.blanco));
            holder.nom_persona.setTextColor(context.getResources().getColor(R.color.titulos));
        }

        if(my_Data.get(position).isCambiado()==true){
            holder.cardView_Seleccion.setCardBackgroundColor(context.getResources().getColor(R.color.titulos));
            holder.nom_persona.setTextColor(context.getResources().getColor(R.color.blanco));
        }

        Glide.with(context)
                .load(my_Data.get(position).getPersona().getFoto())
                .error(R.drawable.user_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.foto_persona);

        holder.nom_persona.setText(my_Data.get(position).getPersona().getNombre_Persona()+" "+my_Data.get(position).getPersona().getApellidos_Persona());


        holder.cardView_Seleccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(my_Data.get(position).isCambiado()){
                    Toast.makeText(context, "Jugador no Disponible", Toast.LENGTH_SHORT).show();
                }else{
                    my_Data.get(position).setActivo(true);
                    Estadistico_Gestion.TEMP.setSuplente_Cambio(my_Data.get(position));
                    limpiar_seleccion(my_Data.get(position).getPersona());
                }

            }
        });

    }

    private void limpiar_seleccion(Persona persona) {

      for(int i=0;i<my_Data.size();i++){
         if(persona.getId()!=my_Data.get(i).getPersona().getId()){
             my_Data.get(i).setActivo(false);
         }
      }
      notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }
}