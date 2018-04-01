package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaDiagnosticoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaFisicoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaTecnicaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterPlantelEdicionFormacion extends RecyclerView.Adapter<AdapterPlantelEdicionFormacion.ViewHolder>{
    public Context context;
    private List<Persona> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;


    public AdapterPlantelEdicionFormacion(Context context, List<Persona> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {


        TextView estado_texto,nombre_jugador;
        ImageView foto_jugador;
        CardView color;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            estado_texto=itemView.findViewById(R.id.card_edicion_texto_estado);
            nombre_jugador=itemView.findViewById(R.id.card_edicion_jugador);
            foto_jugador=itemView.findViewById(R.id.card_edicion_foto);
            color=itemView.findViewById(R.id.card_edicion_color_estado);

        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_plantel_edicion_formacion,parent,false);
        return new ViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
               holder.nombre_jugador.setText(my_Data.get(position).getNombre_Persona()+" "+my_Data.get(position).getApellidos_Persona());

               Glide.with(context)
                       .load(my_Data.get(position).getFoto())
                       .error(R.drawable.user_default)
                       .diskCacheStrategy(DiskCacheStrategy.NONE)
                       .into(holder.foto_jugador);
               if(my_Data.get(position).getEstado_edicion()==0){
                   holder.estado_texto.setText("SUPLENTE");
                   holder.color.setCardBackgroundColor(context.getResources().getColor(R.color.verde_bajo));
               }else if(my_Data.get(position).getEstado_edicion()==1){
                   holder.estado_texto.setText("TITULAR");
                   holder.color.setCardBackgroundColor(context.getResources().getColor(R.color.deep_naranja400));
               }

    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }

    public void debug(String msn){
        System.out.println(msn);
    }

}