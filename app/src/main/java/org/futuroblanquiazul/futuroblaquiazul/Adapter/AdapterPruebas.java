package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.MantenimientoPosicionesActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.GrupoPruebas;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Posicion;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.EliminarPos1;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdapterPruebas extends RecyclerView.Adapter<AdapterPruebas.ViewHolder> {
    private Context context;
    private List<GrupoPruebas> my_Data;

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    int Puntostotal=0;
    AlertDialog da;

    public AdapterPruebas(Context context, List<GrupoPruebas> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;

    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView num,posicion;
        ImageView eliminar;
        public ViewHolder(View itemView) {
            super(itemView);
         num=itemView.findViewById(R.id.card_mant_pos_numero);
         posicion=itemView.findViewById(R.id.card_mant_pos_posicion);
         eliminar=itemView.findViewById(R.id.card_mant_pos_eliminar);

        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_mant_posicion,parent,false);
        return new ViewHolder(itemView);

    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

       holder.num.setText(String.valueOf(my_Data.get(position).getNum()));
       holder.posicion.setText(my_Data.get(position).getDescripcion().toUpperCase());

       holder.eliminar.setVisibility(View.GONE);
    }


    @Override
    public int getItemCount() {
        return my_Data.size();
    }


    public void debug(String d){
        System.out.println(d);
    }

}
