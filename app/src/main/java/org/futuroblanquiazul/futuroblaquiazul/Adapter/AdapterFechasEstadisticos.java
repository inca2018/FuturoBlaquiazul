package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico.DefinirTiemposActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico.GestionFechaEstadisticoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico.ListaFechasEstadisticosActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.EventoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.FechaEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterFechasEstadisticos extends RecyclerView.Adapter<AdapterFechasEstadisticos.ViewHolder>{
    public Context context;
    private List<FechaEstadistico> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    AlertDialog da;

    public AdapterFechasEstadisticos(Context context, List<FechaEstadistico> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {


        TextView numero;
        TextView oponente;
        TextView fecha;
        ImageView acciones;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            numero=itemView.findViewById(R.id.card_fecha_numero);
            oponente=itemView.findViewById(R.id.card_fecha_oponente);
            acciones=itemView.findViewById(R.id.fecha_acciones);
            fecha=itemView.findViewById(R.id.card_fecha_fecha);


        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_fechas_estadisticos,parent,false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.numero.setText("FECHA Nº "+(my_Data.get(position).getNum()));
        holder.oponente.setText("RIVAL : "+my_Data.get(position).getOponente().getNombre_Oponente());

           holder.fecha.setText("FECHA : "+my_Data.get(position).getFecha_Realizacion());


        holder.acciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final PopupMenu popupMenu=new PopupMenu(context,holder.acciones);
                popupMenu.getMenuInflater().inflate(R.menu.menu_item_fecha_estadistico,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getTitle().toString().equalsIgnoreCase("Gestión de Fecha")){
                             if(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal()!=null){
                                 if(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getEstado_formacion()==2){
                                     if(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getEstado_posiciones()==2){

                                         FechaEstadistico.FECHA_ESTADISTICO_TEMP.setFecha_actual(my_Data.get(position));
                                         Intent intent = new Intent(context, DefinirTiemposActivity.class);
                                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                         context.startActivity(intent);
                                     }else{
                                         Toast.makeText(context, "Complete Información de Posiciones y Camisetas!", Toast.LENGTH_SHORT).show();
                                     }
                                 }else{
                                     Toast.makeText(context, "Complete Formación!", Toast.LENGTH_SHORT).show();
                                 }
                             }


                        }else if(item.getTitle().toString().equalsIgnoreCase("Información de Fecha")){

                        }

                        return true;
                    }
                });

                popupMenu.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return my_Data.size();

    }
}