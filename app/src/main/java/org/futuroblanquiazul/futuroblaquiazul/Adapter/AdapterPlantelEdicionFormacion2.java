package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico.DefinirFormacionActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Estadistico;

import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterPlantelEdicionFormacion2 extends RecyclerView.Adapter<AdapterPlantelEdicionFormacion2.ViewHolder>{
    public Context context;
    private List<Persona> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;


    public AdapterPlantelEdicionFormacion2(Context context, List<Persona> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {


        TextView nombre_jugador;
        ImageView foto_jugador;
        LinearLayout accion;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            nombre_jugador=itemView.findViewById(R.id.card_edicion_jugador2);
            foto_jugador=itemView.findViewById(R.id.card_edicion_foto2);
            accion=itemView.findViewById(R.id.linear_accion);

        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_plantel_edicion_formacion2,parent,false);
        return new ViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
               holder.nombre_jugador.setText(my_Data.get(position).getNombre_Persona().toString()+" "+my_Data.get(position).getApellidos_Persona().toString());

               Glide.with(context).load(my_Data.get(position).getFoto()).into(holder.foto_jugador);
               holder.accion.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Toast.makeText(context, "click Salio", Toast.LENGTH_SHORT).show();
                       if(Recursos_Estadistico.RECURSO.getDialog()!=null){
                           Recursos_Estadistico.RECURSO.getDialog().dismiss();
                           DefinirFormacionActivity.DEFINIR.Actualizar_Campo(my_Data.get(position),context);
                       }

                   }
               });

    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }

    public void debug(String msn){
        System.out.println(msn);
    }

}