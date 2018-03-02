package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.futuroblanquiazul.futuroblaquiazul.Entity.GrupoPruebas;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterGrupoPrueba extends RecyclerView.Adapter<AdapterGrupoPrueba.ViewHolder>{
    public Context context;
    private List<GrupoPruebas> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AdapterGrupoPrueba(Context context, List<GrupoPruebas> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

       TextView nombre_grupo;
       TextView fecha_grupo;
       ImageView acciones_grupo;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nombre_grupo=itemView.findViewById(R.id.Nombre_grupo_prueba);
            fecha_grupo=itemView.findViewById(R.id.Fecha_realizar);
            acciones_grupo=itemView.findViewById(R.id.menu_grupo_pruebas);

        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_grupo_prueba,parent,false);
        return new ViewHolder(itemView);




    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.nombre_grupo.setText(my_Data.get(position).getDescripcion());
        holder.fecha_grupo.setText(my_Data.get(position).getFecha_realizacion());
        holder.acciones_grupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final PopupMenu popupMenu=new PopupMenu(context,holder.acciones_grupo);
                popupMenu.getMenuInflater().inflate(R.menu.menu_item_grupo_pruebas,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getTitle().toString().equalsIgnoreCase("Evaluaciones")){

                        }else if(item.getTitle().toString().equalsIgnoreCase("Información")){

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