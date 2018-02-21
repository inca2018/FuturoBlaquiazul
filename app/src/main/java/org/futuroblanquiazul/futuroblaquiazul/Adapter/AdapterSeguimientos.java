package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Seguimiento;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.List;

public class AdapterSeguimientos extends RecyclerView.Adapter<AdapterSeguimientos.ViewHolder>{
    public Context context;
    private List<Seguimiento> my_Data;
    ProgressDialog progressDialog;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AdapterSeguimientos(Context context, List<Seguimiento> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public TextView codigo_seguimiento;
        public ImageView acciones;
        public TextView nombre_competencia;
        public TextView nombre_rival;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            codigo_seguimiento=itemView.findViewById(R.id.card_seguimiento_codigo);
            acciones=itemView.findViewById(R.id.card_seguimiento2_acciones);
            nombre_competencia=itemView.findViewById(R.id.card_seguimiento_competencia);
            nombre_rival=itemView.findViewById(R.id.card_seguimiento_rival);

        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_masivo_persona,parent,false);
        return new ViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
              holder.nombre_competencia.setText(my_Data.get(position).getNombre_Competencia());
              holder.nombre_rival.setText(my_Data.get(position).getNombre_Rival());
              holder.codigo_seguimiento.setText(my_Data.get(position).getCodigo_Seguimiento());

              holder.acciones.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      final PopupMenu popupMenu=new PopupMenu(context,holder.acciones);
                      popupMenu.getMenuInflater().inflate(R.menu.menu_item_masivo,popupMenu.getMenu());
                      popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                          @Override
                          public boolean onMenuItemClick(MenuItem item) {
                              if(item.getTitle().toString().equalsIgnoreCase("Migración a Etapa de Prueba")){

                              }else if(item.getTitle().toString().equalsIgnoreCase("Mis Seguimientos")){

                              }

                              return true;
                          }
                      });

                  }
              });
    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }

}