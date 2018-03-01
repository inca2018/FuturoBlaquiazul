package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.PruebaDiagnosticoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.PruebaFisicoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.PruebaTecnicaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterBarrioPersona extends RecyclerView.Adapter<AdapterBarrioPersona.ViewHolder>{
    public Context context;
    private List<Persona> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;


    public AdapterBarrioPersona(Context context, List<Persona> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public TextView titulo_barrio;
        public ImageView acciones;
        public ImageView image_prueba;
        public LinearLayout linea;
        public TextView total_diag,total_fisico,total_tecnico;
        public TextView total_promedio;



        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            titulo_barrio=itemView.findViewById(R.id.card_barrio_titulo);
            acciones=itemView.findViewById(R.id.barrio_personas_acciones);
            image_prueba=itemView.findViewById(R.id.imagen_accion);
            linea=itemView.findViewById(R.id.liner_adicional);
            total_diag=itemView.findViewById(R.id.total_diag2);
            total_fisico=itemView.findViewById(R.id.total_fisico);
            total_tecnico=itemView.findViewById(R.id.total_tecnico);
            total_promedio=itemView.findViewById(R.id.promedio);

        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_barrio_persona,parent,false);
        return new ViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


            holder.titulo_barrio.setText(my_Data.get(position).getNombre_Persona()+" "+my_Data.get(position).getApellidos_Persona());
            holder.total_diag.setText(my_Data.get(position).getTotal_diagnostico()+" Ptos.");
            holder.total_tecnico.setText(my_Data.get(position).getTotal_tecnica()+" Ptos.");
            holder.total_fisico.setText(my_Data.get(position).getTotal_fisica()+" Ptos.");

            int resu1=my_Data.get(position).getTotal_diagnostico();
            int resu2=my_Data.get(position).getTotal_fisica();
            int resu3=my_Data.get(position).getTotal_tecnica();

            int prom=(resu1+resu2+resu3)/3;


            holder.total_promedio.setText(prom+" Ptos.");

            holder.image_prueba.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(my_Data.get(position).getNum_Camiseta()==0){
                        holder.image_prueba.setImageResource(R.mipmap.icon_arriba);
                        holder.linea.setVisibility(View.VISIBLE);
                        my_Data.get(position).setNum_Camiseta(1);
                        notifyDataSetChanged();

                    }else{
                        holder.image_prueba.setImageResource(R.mipmap.icon_abajo);
                        holder.linea.setVisibility(View.GONE);
                        my_Data.get(position).setNum_Camiseta(0);
                        notifyDataSetChanged();
                    }
                }
            });

            holder.acciones.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final PopupMenu popupMenu=new PopupMenu(context,holder.acciones);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_item_barrio,popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            if(item.getTitle().toString().equalsIgnoreCase("Evaluación Diagnostico")){

                                if(my_Data.get(position).getBarrio_diagnostico()!=0){
                                    Toast.makeText(context, "Postulante ya paso la prueba de diagnostico", Toast.LENGTH_SHORT).show();
                                }else{
                                    Usuario.SESION_ACTUAL.setPersona_barrio(my_Data.get(position));

                                    Intent intent=new Intent(context,PruebaDiagnosticoActivity.class);
                                    context.startActivity(intent);
                                }



                            }else if(item.getTitle().toString().equalsIgnoreCase("Evaluación Fisica")){

                                if(my_Data.get(position).getBarrio_fisica()!=0){
                                    Toast.makeText(context, "Postulante ya paso la prueba fisica", Toast.LENGTH_SHORT).show();
                                }else{
                                    Usuario.SESION_ACTUAL.setPersona_barrio(my_Data.get(position));
                                    Intent intent=new Intent(context,PruebaFisicoActivity.class);
                                    context.startActivity(intent);
                                }


                            }else if(item.getTitle().toString().equalsIgnoreCase("Evaluación Técnica")){

                                if(my_Data.get(position).getBarrio_tecnica()!=0){
                                    Toast.makeText(context, "Postulante ya paso la prueba Tecnica", Toast.LENGTH_SHORT).show();
                                }else{
                                    Usuario.SESION_ACTUAL.setPersona_barrio(my_Data.get(position));
                                    Intent intent=new Intent(context,PruebaTecnicaActivity.class);
                                    context.startActivity(intent);

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

    public void debug(String msn){
        System.out.println(msn);
    }

}