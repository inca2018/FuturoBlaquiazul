package org.futuroblanquiazul.futuroblaquiazul.Adapter;

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

import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaDiagnosticoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaFisicoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaPsicologicaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaTecnicaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PruebaFisica;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.List;

public class AdapterJugadoresGrupoPruebas extends RecyclerView.Adapter<AdapterJugadoresGrupoPruebas.ViewHolder>{
    public Context context;
    private List<Persona> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AdapterJugadoresGrupoPruebas(Context context, List<Persona> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {


        TextView nom;
        ImageView acciones;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nom=itemView.findViewById(R.id.nombre_persona_grupo_pruebas);
            acciones=itemView.findViewById(R.id.acciones_personas_grupo_pruebas);

        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_persona_grupo_prueba,parent,false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.nom.setText(my_Data.get(position).getNombre_Persona()+" "+my_Data.get(position).getApellidos_Persona());

        holder.acciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final PopupMenu popupMenu=new PopupMenu(context,holder.acciones);
                popupMenu.getMenuInflater().inflate(R.menu.menu_item_persona_grupo_pruebas,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getTitle().toString().equalsIgnoreCase("Prueba Diagnostico")){
                            Usuario.SESION_ACTUAL.setPersona_metodologia_pruebas(my_Data.get(position));

                            Intent intent = new Intent(context, PruebaDiagnosticoActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);

                        }else if(item.getTitle().toString().equalsIgnoreCase("Prueba Fisico")){
                            Usuario.SESION_ACTUAL.setPersona_metodologia_pruebas(my_Data.get(position));

                            Intent intent = new Intent(context,PruebaFisicoActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);



                        }else if(item.getTitle().toString().equalsIgnoreCase("Prueba Tecnico")){
                            Usuario.SESION_ACTUAL.setPersona_metodologia_pruebas(my_Data.get(position));


                            Intent intent = new Intent(context,PruebaTecnicaActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);


                    }else if(item.getTitle().toString().equalsIgnoreCase("Prueba Psicologica")){
                        Usuario.SESION_ACTUAL.setPersona_metodologia_pruebas(my_Data.get(position));


                        Intent intent = new Intent(context,PruebaPsicologicaActivity.class);
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