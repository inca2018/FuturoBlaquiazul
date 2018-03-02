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

import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.ListaPersonasGrupoPruebasActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.ListaPersonasPlantelActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.GrupoPruebas;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
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
    AlertDialog da;

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

                        if(item.getTitle().toString().equalsIgnoreCase("Iniciar Pruebas")){
                            Usuario.SESION_ACTUAL.setGrupoPruebasTEMP(my_Data.get(position));

                            Intent intent = new Intent(context, ListaPersonasGrupoPruebasActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);


                        }else if(item.getTitle().toString().equalsIgnoreCase("Información")){


                            Usuario.SESION_ACTUAL.setGrupoPruebasTEMP(my_Data.get(position));


                            final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                            final View dialoglayout = inflater.inflate(R.layout.area_informacion_grupo_pruebas, null);

                            TextView  p_creador,p_categoria,p_descripcion,p_observaciones,p_fecha,p_fecha2,p_estado;

                            p_categoria=dialoglayout.findViewById(R.id.info_grupo_categoria);
                            p_creador=dialoglayout.findViewById(R.id.info_grupo_creador);
                            p_descripcion=dialoglayout.findViewById(R.id.info_grupo_descripcion);
                            p_observaciones=dialoglayout.findViewById(R.id.info_grupo_observaciones);
                            p_fecha=dialoglayout.findViewById(R.id.info_grupo_fecha1);
                            p_fecha2=dialoglayout.findViewById(R.id.info_grupo_fecha2);
                            p_estado=dialoglayout.findViewById(R.id.info_grupo_estado);

                            final AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
                            builder4.setView(dialoglayout);
                            da=builder4.show();


                            p_creador.setText(my_Data.get(position).getUsuario().getUsuario());
                            p_descripcion.setText(my_Data.get(position).getDescripcion());

                            p_observaciones.setText(my_Data.get(position).getObservaciones());
                            p_fecha.setText(my_Data.get(position).getFecha_registro());
                            p_fecha2.setText(my_Data.get(position).getFecha_realizacion());
                            p_categoria.setText(my_Data.get(position).getPlantel().getNombre_categoria());



                            if(my_Data.get(position).getEstado()==1){
                                p_estado.setText("DISPONIBLE");
                                p_estado.setTextColor(context.getResources().getColor(R.color.verde));
                            }else if(my_Data.get(position).getEstado()==2){
                                p_estado.setText("NO DISPONIBLE");
                                p_estado.setTextColor(context.getResources().getColor(R.color.red));
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