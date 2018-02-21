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

import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.CaptacionActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.ListaPersonaMasivoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.ListaPersonaSeguimientoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.ListaSeguimientosActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.MasivoResultadosActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Seguimiento;
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


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterSeguimientoPersona extends RecyclerView.Adapter<AdapterSeguimientoPersona.ViewHolder>{
    public Context context;
    private List<Persona> my_Data;
    ProgressDialog progressDialog;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AdapterSeguimientoPersona(Context context, List<Persona> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public TextView Nombre_persona;
        public ImageView acciones;
        public TextView texto_seguimientos_totales;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            Nombre_persona=itemView.findViewById(R.id.card_seguimiento_titulo);
            acciones=itemView.findViewById(R.id.card_seguimiento_personas_acciones);
            texto_seguimientos_totales=itemView.findViewById(R.id.card_seguimiento_seguimientos);
        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_seguimiento_persona,parent,false);
        return new ViewHolder(itemView);



    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
              holder.Nombre_persona.setText(my_Data.get(position).getNombre_Persona()+" "+my_Data.get(position).getApellidos_Persona());
              holder.texto_seguimientos_totales.setText("Seguimientos Realizados: "+my_Data.get(position).getTotales_seguimientos());

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

                                  Usuario.SESION_ACTUAL.setId_persona(my_Data.get(position).getId());
                                  Seguimiento.SEGUIMIENTO.setPersona(my_Data.get(position));
                                  Intent intent = new Intent(context,ListaSeguimientosActivity.class);
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