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

import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.ListaPersonasPlantelActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.ListaGrupoPruebasActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterPlantel extends RecyclerView.Adapter<AdapterPlantel.ViewHolder>{
    public Context context;
    private List<Plantel> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    AlertDialog da;

    public AdapterPlantel(Context context, List<Plantel> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {


        TextView nom;
        ImageView menu;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nom=itemView.findViewById(R.id.card_plantel_nombre);
            menu=itemView.findViewById(R.id.menu_plantel);


        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_plantel,parent,false);
        return new ViewHolder(itemView);



    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
           holder.nom.setText(my_Data.get(position).getNombre_categoria());

           holder.menu.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   final PopupMenu popupMenu=new PopupMenu(context,holder.menu);
                   popupMenu.getMenuInflater().inflate(R.menu.menu_item_plantel,popupMenu.getMenu());

                   popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                       @Override
                       public boolean onMenuItemClick(MenuItem item) {

                           if(item.getTitle().toString().equalsIgnoreCase("Jugadores")){

                               Usuario.SESION_ACTUAL.setPlantel(my_Data.get(position));
                               Intent intent = new Intent(context, ListaPersonasPlantelActivity.class);
                               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                               context.startActivity(intent);

                           }else if(item.getTitle().toString().equalsIgnoreCase("Grupo de Evaluaciones")){

                               Usuario.SESION_ACTUAL.setPlantel(my_Data.get(position));

                               Intent intent = new Intent(context, ListaGrupoPruebasActivity.class);
                               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                               context.startActivity(intent);

                           }else if(item.getTitle().toString().equalsIgnoreCase("Información")){
                               Usuario.SESION_ACTUAL.setPlantel(my_Data.get(position));


                               final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                               final View dialoglayout = inflater.inflate(R.layout.area_informacion_plantel, null);

                               TextView p_grupo,p_categoria,p_usuario,p_fecha,p_estado;

                               p_grupo=dialoglayout.findViewById(R.id.info_plantel_grupo);
                               p_categoria=dialoglayout.findViewById(R.id.info_plantel_categoria);
                               p_usuario=dialoglayout.findViewById(R.id.info_plantel_creador);
                               p_fecha=dialoglayout.findViewById(R.id.info_plantel_fecha);
                               p_estado=dialoglayout.findViewById(R.id.info_plantel_estado);

                               final AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
                               builder4.setView(dialoglayout);
                               da=builder4.show();


                               p_grupo.setText(my_Data.get(position).getRango().getDescripcion());
                               p_categoria.setText(my_Data.get(position).getNombre_categoria());

                               p_usuario.setText(my_Data.get(position).getUsuario().getUsuario());
                               p_fecha.setText(my_Data.get(position).getFecha_registro());


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