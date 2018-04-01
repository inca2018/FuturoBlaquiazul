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

import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.EdicionBarrioIntimoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.EdicionCategoriactivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.ListaGrupoPruebasActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.ListaPersonasPlantelActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;

import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterCategoria extends RecyclerView.Adapter<AdapterCategoria.ViewHolder>{
    public Context context;
    private List<Plantel> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    AlertDialog da;

    public AdapterCategoria(Context context, List<Plantel> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {


        TextView num,nombre,creador,total,estado;
        ImageView card_edit;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
             num=itemView.findViewById(R.id.card_cate_num);
             nombre=itemView.findViewById(R.id.card_categoria_nom);
             creador=itemView.findViewById(R.id.card_categoria_creador);
             total=itemView.findViewById(R.id.card_categoria_total);
             card_edit=itemView.findViewById(R.id.card_cate_edit);
             estado=itemView.findViewById(R.id.card_estado_ca2);


        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_categoria_mant,parent,false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
                  holder.num.setText(String.valueOf(my_Data.get(position).getNum()));
                  holder.nombre.setText(my_Data.get(position).getNombre_categoria());
                  holder.creador.setText("Creador:"+my_Data.get(position).getUsuario().getNombres()+" "+my_Data.get(position).getUsuario().getApellidos());

                  holder.total.setText(String.valueOf(my_Data.get(position).getTotal()));

                  holder.card_edit.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          Recursos_Mantenimientos.TEMP.setCategoria_temporal(my_Data.get(position));

                          Intent intent =new Intent(context,EdicionCategoriactivity.class);
                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                          context.startActivity(intent);

                      }
                  });

        if(my_Data.get(position).getEstado()==1){
            holder.estado.setText("ACTIVO");
            holder.estado.setTextColor(context.getResources().getColor(R.color.verde));
        }else if(my_Data.get(position).getEstado()==2){
            holder.estado.setText("INACTIVO");
            holder.estado.setTextColor(context.getResources().getColor(R.color.red));
        }

    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }
}