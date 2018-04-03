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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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

        TextView gol_local;
        TextView gol_rival;
        TextView estado;
        TextView equipo_local;
        ImageView escudo_oponente;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            numero=itemView.findViewById(R.id.card_fecha_numero);
            oponente=itemView.findViewById(R.id.card_fecha_oponente);
            acciones=itemView.findViewById(R.id.fecha_acciones);
            fecha=itemView.findViewById(R.id.card_fecha_numero);

            gol_local=itemView.findViewById(R.id.card_fecha_resu_local);
            gol_rival=itemView.findViewById(R.id.card_fecha_resu_rival);
            estado=itemView.findViewById(R.id.card_fecha_estado);
            equipo_local=itemView.findViewById(R.id.card_fecha_equipo);
            escudo_oponente=itemView.findViewById(R.id.card_fecha_escudo_oponente);




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
        holder.oponente.setText(my_Data.get(position).getOponente().getNombre_Oponente().toUpperCase());

        if(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal()!=null){
            holder.equipo_local.setText(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getEquipo().getNombre_equipo().toUpperCase());
        }else{
            holder.equipo_local.setText("Equipo no Disponible");
        }

        holder.gol_local.setText(String.valueOf(my_Data.get(position).getGoles_local()));
        holder.gol_rival.setText(String.valueOf(my_Data.get(position).getGoles_rival()));
        holder.fecha.setText("FECHA Nº"+my_Data.get(position).getNum());

        if(my_Data.get(position).getEstado()==1){
            holder.estado.setText("PENDIENTE");
            holder.estado.setTextColor(context.getResources().getColor(R.color.verde));
        }else if(my_Data.get(position).getEstado()==2){
            holder.estado.setText("FINALIZADO");
            holder.estado.setTextColor(context.getResources().getColor(R.color.red));
        }else if(my_Data.get(position).getEstado()==3){
            holder.estado.setText("W.O");
            holder.estado.setTextColor(context.getResources().getColor(R.color.morado_bajo));
        }


        Glide.with(context).load(my_Data.get(position).getOponente().getFoto()).error(R.drawable.no_disponible)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.escudo_oponente);
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

                                         if(my_Data.get(position).getEstado()==1){
                                             FechaEstadistico.FECHA_ESTADISTICO_TEMP.setFecha_actual(my_Data.get(position));
                                             Intent intent = new Intent(context, DefinirTiemposActivity.class);
                                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                             context.startActivity(intent);
                                         }else if(my_Data.get(position).getEstado()==2){
                                             Toast.makeText(context, "Fecha ya Realizada!", Toast.LENGTH_SHORT).show();
                                         }else if(my_Data.get(position).getEstado()==3){
                                             Toast.makeText(context, "Fecha Registrada como WALKOVER (W.O)", Toast.LENGTH_SHORT).show();
                                         }


                                     }else{
                                         Toast.makeText(context, "Complete Información de Posiciones y Camisetas!", Toast.LENGTH_SHORT).show();
                                     }
                                 }else{
                                     Toast.makeText(context, "Complete Formación!", Toast.LENGTH_SHORT).show();
                                 }
                             }


                        }else if(item.getTitle().toString().equalsIgnoreCase("Información de Fecha")){
                            if(my_Data.get(position).getEstado()==1){
                                Toast.makeText(context, "Fecha sin Información para mostrar", Toast.LENGTH_SHORT).show();
                            }else if(my_Data.get(position).getEstado()==2){

                            }else if(my_Data.get(position).getEstado()==3){
                                Toast.makeText(context, "Fecha Registrada como WALKOVER (W.O)", Toast.LENGTH_SHORT).show();
                            }
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