package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Campo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.NumeroDisponible;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.List;

public class AdapterNumero extends RecyclerView.Adapter<AdapterNumero.ViewHolder> {
    private Context context;
    private List<NumeroDisponible> my_Data;

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AdapterNumero(Context context, List<NumeroDisponible> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;

    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btn_numero;
        CardView area;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
                  btn_numero=itemView.findViewById(R.id.card_numero_disponible);
                  area=itemView.findViewById(R.id.card_area_numero);

        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_numero_disponible,parent,false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.btn_numero.setText(String.valueOf(my_Data.get(position).getNumero()));
        if(my_Data.get(position).getEstado()==0){
            holder.area.setCardBackgroundColor(context.getResources().getColor(R.color.fb));
            holder.btn_numero.setTextColor(context.getResources().getColor(R.color.titulos));
        }else{
            holder.area.setCardBackgroundColor(context.getResources().getColor(R.color.blue));
            holder.btn_numero.setTextColor(context.getResources().getColor(R.color.blanco));
        }
    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }


}
