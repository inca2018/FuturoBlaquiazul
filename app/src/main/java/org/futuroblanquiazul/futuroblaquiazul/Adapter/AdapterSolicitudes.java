package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.PruebaDiagnosticoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.PruebaFisicoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.PruebaTecnicaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.ListaPersonaMasivoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.MasivoResultadosActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Solicitudes;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.EliminarResultados;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarResultadosDiagnostico;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Recuperar_Sugerido;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.ResultadosDiagnostico;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class AdapterSolicitudes extends RecyclerView.Adapter<AdapterSolicitudes.ViewHolder>{
    public Context context;
    private List<Solicitudes> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AdapterSolicitudes(Context context, List<Solicitudes> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

         TextView nombres;
         TextView dni;
         TextView fecha;
         ImageView acciones;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nombres=itemView.findViewById(R.id.solicitudes_nombre);
            dni=itemView.findViewById(R.id.solicitudes_dni);
            fecha=itemView.findViewById(R.id.solicitudes_fecha);
            acciones=itemView.findViewById(R.id.solicitudes_acciones);

        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_solicitudes,parent,false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        holder.nombres.setText(my_Data.get(position).getNombre_solicitud());
        holder.dni.setText(String.valueOf("DNI:"+my_Data.get(position).getDni_solicitud()));

        if(my_Data.get(position).getEstado()==1){
            holder.fecha.setText("ACTIVO");
            holder.fecha.setTextColor(context.getResources().getColor(R.color.verde));
        }else{
            holder.fecha.setText("ANULADO");
            holder.fecha.setTextColor(context.getResources().getColor(R.color.red));
        }


        holder.acciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final PopupMenu popupMenu=new PopupMenu(context,holder.acciones);
                popupMenu.getMenuInflater().inflate(R.menu.menu_item_solicitud,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getTitle().toString().equalsIgnoreCase("Cambiar Contraseña")){

                        }else if(item.getTitle().toString().equalsIgnoreCase("Anular Solicitud")){

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