package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Campo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PersonaEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Estadistico_Gestion;

import java.util.List;

public class AdapterInfoEquipo extends RecyclerView.Adapter<AdapterInfoEquipo.ViewHolder> {
    private Context context;
    private List<PersonaEstadistico> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;


    public AdapterInfoEquipo(Context context, List<PersonaEstadistico> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;

    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

             TextView o1,o2,o3,o4,o5,o6,o7,o8,o9,o10,o11,o12,o13,o14,o15,o16,o17,o18;
        public ViewHolder(View itemView) {
            super(itemView);

            o1=itemView.findViewById(R.id.card_est_num);
            o2=itemView.findViewById(R.id.card_est_persona);
            o3=itemView.findViewById(R.id.card_est_camiseta);
            o4=itemView.findViewById(R.id.card_est_posicion);
            o5=itemView.findViewById(R.id.card_est_pg);
            o6=itemView.findViewById(R.id.card_est_dr);
            o7=itemView.findViewById(R.id.card_est_og);
            o8=itemView.findViewById(R.id.card_est_r);
            o9=itemView.findViewById(R.id.card_est_g);
            o10=itemView.findViewById(R.id.card_est_of);
            o11=itemView.findViewById(R.id.card_est_bp);
            o12=itemView.findViewById(R.id.card_est_br);
            o13=itemView.findViewById(R.id.card_est_f);
            o14=itemView.findViewById(R.id.card_est_ta);
            o15=itemView.findViewById(R.id.card_est_tr);
            o16=itemView.findViewById(R.id.card_est_atj);
            o17=itemView.findViewById(R.id.card_est_tj);
            o18=itemView.findViewById(R.id.card_est_ptje);


        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_info_equipo_estadistico,parent,false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.o1.setText(String.valueOf(my_Data.get(position).getNum()));
        holder.o2.setText(String.valueOf(my_Data.get(position).getPersona().getNombre_Persona()+" "+my_Data.get(position).getPersona().getApellidos_Persona()));
        holder.o3.setText(String.valueOf(my_Data.get(position).getCamiseta_persona()));
        holder.o4.setText(String.valueOf(my_Data.get(position).getPosicion_persona().getNombre_Posicione()));


       if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
            holder.o5.setText(String.valueOf(my_Data.get(position).getPrimerTiempo().getPaseGol()));
            holder.o6.setText(String.valueOf(my_Data.get(position).getPrimerTiempo().getDribling()));
            holder.o7.setText(String.valueOf(my_Data.get(position).getPrimerTiempo().getOpcionGol()));
            holder.o8.setText(String.valueOf(my_Data.get(position).getPrimerTiempo().getRemate()));
            holder.o9.setText(String.valueOf(my_Data.get(position).getPrimerTiempo().getGoles()));
            holder.o10.setText(String.valueOf(my_Data.get(position).getPrimerTiempo().getOfSide()));
            holder.o11.setText(String.valueOf(my_Data.get(position).getPrimerTiempo().getBalonPerdido()));
            holder.o12.setText(String.valueOf(my_Data.get(position).getPrimerTiempo().getBalonRecuperado()));
            holder.o13.setText(String.valueOf(my_Data.get(position).getPrimerTiempo().getFaltas()));
            holder.o14.setText(String.valueOf(my_Data.get(position).getPrimerTiempo().getTarjetasAmarillas()));
            holder.o15.setText(String.valueOf(my_Data.get(position).getPrimerTiempo().getTarjetasRojas()));
            holder.o16.setText(String.valueOf(my_Data.get(position).getPrimerTiempo().getAtajadas()));
            holder.o17.setText(String.valueOf(my_Data.get(position).getPrimerTiempo().getTiempo_Jugados()));
            holder.o18.setText(String.valueOf(my_Data.get(position).getPrimerTiempo().getPuntajes()));
       }else if(Estadistico_Gestion.TEMP.getTiempo_actual()==2){
            holder.o5.setText(String.valueOf(my_Data.get(position).getSegundoTiempo().getPaseGol()));
            holder.o6.setText(String.valueOf(my_Data.get(position).getSegundoTiempo().getDribling()));
            holder.o7.setText(String.valueOf(my_Data.get(position).getSegundoTiempo().getOpcionGol()));
            holder.o8.setText(String.valueOf(my_Data.get(position).getSegundoTiempo().getRemate()));
            holder.o9.setText(String.valueOf(my_Data.get(position).getSegundoTiempo().getGoles()));
            holder.o10.setText(String.valueOf(my_Data.get(position).getSegundoTiempo().getOfSide()));
            holder.o11.setText(String.valueOf(my_Data.get(position).getSegundoTiempo().getBalonPerdido()));
            holder.o12.setText(String.valueOf(my_Data.get(position).getSegundoTiempo().getBalonRecuperado()));
            holder.o13.setText(String.valueOf(my_Data.get(position).getSegundoTiempo().getFaltas()));
            holder.o14.setText(String.valueOf(my_Data.get(position).getSegundoTiempo().getTarjetasAmarillas()));
            holder.o15.setText(String.valueOf(my_Data.get(position).getSegundoTiempo().getTarjetasRojas()));
            holder.o16.setText(String.valueOf(my_Data.get(position).getSegundoTiempo().getAtajadas()));
            holder.o17.setText(String.valueOf(my_Data.get(position).getSegundoTiempo().getTiempo_Jugados()));
            holder.o18.setText(String.valueOf(my_Data.get(position).getSegundoTiempo().getPuntajes()));
        }
    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }


}
