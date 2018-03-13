package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico.DefinirPosicionesEventoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico.ListaFechasEstadisticosActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.EventoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterEventosEstadisticos extends RecyclerView.Adapter<AdapterEventosEstadisticos.ViewHolder>{
    public Context context;
    private List<EventoEstadistico> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    AlertDialog da;

    public AdapterEventosEstadisticos(Context context, List<EventoEstadistico> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {


        TextView nombre_evento;
        TextView ubigeo_evento;
        TextView categoria_evento;
        ImageView acciones;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nombre_evento=itemView.findViewById(R.id.card_eventos_estadisticos_nombre);
            ubigeo_evento=itemView.findViewById(R.id.card_eventos_estadisticos_ubigeo);
            acciones=itemView.findViewById(R.id.eventos_estadisticos_acciones);
            categoria_evento=itemView.findViewById(R.id.card_eventos_estadisticos_categoria);


        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_eventos_estadisticos,parent,false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.nombre_evento.setText(my_Data.get(position).getDescripcion_Nombre_evento());
        holder.ubigeo_evento.setText("Ubicación: "+my_Data.get(position).getDepartamento().getDescripcion()+"/"+my_Data.get(position).getProvincia().getDescripcion()+"/"+my_Data.get(position).getDistrito().getDescripcion());

        holder.categoria_evento.setText("Categoria: "+my_Data.get(position).getPlantel().getNombre_categoria());


        holder.acciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final PopupMenu popupMenu=new PopupMenu(context,holder.acciones);
                popupMenu.getMenuInflater().inflate(R.menu.menu_item_evento_estadistico,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getTitle().toString().equalsIgnoreCase("Lista de Fechas")){

                            EventoEstadistico.EVENTO_TEMP.setEvento_Temporal(my_Data.get(position));

                            Intent intent = new Intent(context, ListaFechasEstadisticosActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);


                        }else if(item.getTitle().toString().equalsIgnoreCase("Información de Evento")){


                            final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                            final View dialoglayout = inflater.inflate(R.layout.area_informacion_evento_estadistico, null);

                            TextView nom_evento,desc_evento,usuario_evento,ubigeo_evento,fecha_evento,estado_evento,categoria,inicio,fin;

                            nom_evento=dialoglayout.findViewById(R.id.info_evento_es_nombre);
                            desc_evento=dialoglayout.findViewById(R.id.info_evento_es_detalle);
                            usuario_evento=dialoglayout.findViewById(R.id.info_evento_es_usuario);
                            ubigeo_evento=dialoglayout.findViewById(R.id.info_evento_es_ubicacion);
                            fecha_evento=dialoglayout.findViewById(R.id.info_evento_es_fecha);
                            estado_evento=dialoglayout.findViewById(R.id.info_evento_es_estado);
                            categoria=dialoglayout.findViewById(R.id.info_evento_es_categoria);
                            inicio=dialoglayout.findViewById(R.id.info_evento_es_fecha_inicio);
                            fin=dialoglayout.findViewById(R.id.info_evento_es_fecha_fin);

                            final AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
                            builder4.setView(dialoglayout);
                            da=builder4.show();


                            nom_evento.setText(my_Data.get(position).getDescripcion_Nombre_evento());
                            desc_evento.setText(my_Data.get(position).getDetalle_Evento());
                            usuario_evento.setText(my_Data.get(position).getUsuario().getUsuario().toString());
                            ubigeo_evento.setText(my_Data.get(position).getDepartamento().getDescripcion()+"/"+my_Data.get(position).getProvincia().getDescripcion()+"/"+my_Data.get(position).getDistrito().getDescripcion());
                            fecha_evento.setText(my_Data.get(position).getFecha_Registro());

                            inicio.setText(my_Data.get(position).getInicio_dia()+"/"+my_Data.get(position).getInicio_mes()+"/"+my_Data.get(position).getInicio_ano());
                            fin.setText(my_Data.get(position).getFin_dia()+"/"+my_Data.get(position).getFin_mes()+"/"+my_Data.get(position).getFin_ano());

                            if(my_Data.get(position).getEstado()==1){
                                estado_evento.setText("DISPONIBLE");
                                estado_evento.setTextColor(context.getResources().getColor(R.color.verde));
                            }else if(my_Data.get(position).getEstado()==2){
                                estado_evento.setText("NO DISPONIBLE");
                                estado_evento.setTextColor(context.getResources().getColor(R.color.red));
                            }

                            categoria.setText(my_Data.get(position).getPlantel().getNombre_categoria());

                        }else if(item.getTitle().toString().equalsIgnoreCase("Definir Posiciones de Jugadores")){

                            EventoEstadistico.EVENTO_TEMP.setEvento_Temporal(my_Data.get(position));

                            Intent intent = new Intent(context, DefinirPosicionesEventoActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);

                        }else if(item.getTitle().toString().equalsIgnoreCase("Definir Formación de Equipo")){

                            EventoEstadistico.EVENTO_TEMP.setEvento_Temporal(my_Data.get(position));

                            EventoEstadistico.EVENTO_TEMP.setEvento_Temporal(my_Data.get(position));
                            Intent intent = new Intent(context, ListaFechasEstadisticosActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);

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