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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.EdicionPersonaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.EdicionUsuarioActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.MantenimientoOponentesActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.BarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;

import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterPersona extends RecyclerView.Adapter<AdapterPersona.ViewHolder>{
    public Context context;
    private List<Persona> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    AlertDialog da;

    public AdapterPersona(Context context, List<Persona> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {


         TextView num_persona,Nombres_persona,Estado_personas;
         ImageView foto_persona,Editar_persona;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            num_persona=itemView.findViewById(R.id.card_mant_per_numero);
            Nombres_persona=itemView.findViewById(R.id.card_mant_per_nombre);
            Estado_personas=itemView.findViewById(R.id.card_mant_per_estado);
            foto_persona=itemView.findViewById(R.id.card_mant_per_foto);
            Editar_persona=itemView.findViewById(R.id.card_mant_per_editar);


        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_persona,parent,false);
        return new ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.num_persona.setText("Nº "+my_Data.get(position).getNum());
        holder.Nombres_persona.setText(my_Data.get(position).getNombre_Persona()+" "+my_Data.get(position).getApellidos_Persona());

        Glide.with(context)
                .load(my_Data.get(position).getFoto())
                .error(R.drawable.user_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.foto_persona);


        if((my_Data.get(position).getEstado()==1) || (my_Data.get(position).getEstado()==2) || (my_Data.get(position).getEstado()==3) || (my_Data.get(position).getEstado()==4) || (my_Data.get(position).getEstado()==5) || (my_Data.get(position).getEstado()==6) ){

            holder.Estado_personas.setText("DISPONIBLE");
            holder.Estado_personas.setTextColor(context.getResources().getColor(R.color.verde));
        }else if(my_Data.get(position).getEstado()==10){
            holder.Estado_personas.setText("NO DISPONIBLE");
            holder.Estado_personas.setTextColor(context.getResources().getColor(R.color.red));
        }


        holder.Editar_persona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        final PopupMenu popupMenu=new PopupMenu(context,holder.Editar_persona);
                        popupMenu.getMenuInflater().inflate(R.menu.menu_item_usuarios,popupMenu.getMenu());

                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {

                                if(item.getTitle().toString().equalsIgnoreCase("Editar")){
                                    Recursos_Mantenimientos.TEMP.setPersona_temporal(my_Data.get(position));

                                    Intent intent= new Intent(context,EdicionPersonaActivity.class);
                                    context.startActivity(intent);

                                }else if(item.getTitle().toString().equalsIgnoreCase("Habilitar")){

                                    //DesactivarUsuario(context,position);

                                }else if(item.getTitle().toString().equalsIgnoreCase("Deshabilitar")){

                                   // BloquearUsuario(context,position);


                                }else if(item.getTitle().toString().equalsIgnoreCase("Información del Jugador")){

                                    final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                    final View dialoglayout = inflater.inflate(R.layout.info_mantenimiento_personas, null);

                                    TextView u1,u2,u3,u4,u5,u6,u7,u8,u9,u10;
                                    ImageView foto_info;

                                    foto_info=dialoglayout.findViewById(R.id.info_mant_foto);
                                    u1=dialoglayout.findViewById(R.id.info_mant_estado);
                                    u2=dialoglayout.findViewById(R.id.info_mant_nombres);
                                    u3=dialoglayout.findViewById(R.id.info_mant_dni);
                                    u4=dialoglayout.findViewById(R.id.info_mant_correo);
                                    u5=dialoglayout.findViewById(R.id.info_mant_area);
                                    u6=dialoglayout.findViewById(R.id.info_mant_cargo);
                                    u7=dialoglayout.findViewById(R.id.info_mant_usuario);
                                    u8=dialoglayout.findViewById(R.id.info_mant_perfil);
                                    u9=dialoglayout.findViewById(R.id.info_mant_fecha_registro);
                                    u10=dialoglayout.findViewById(R.id.info_mant_fecha_conexion);

                                    /*
                                    final AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
                                    builder4.setView(dialoglayout);
                                    da=builder4.show();

                                    Glide.with(context).load(my_Data.get(position).getFoto()).into(foto_info);

                                    if(my_Data.get(position).getEstado()==1){
                                        u1.setText("ACTIVO");
                                        u1.setTextColor(context.getResources().getColor(R.color.verde));
                                    }else if(my_Data.get(position).getEstado()==2){
                                        u1.setText("DESACTIVADO");
                                        u1.setTextColor(context.getResources().getColor(R.color.red));
                                    }else if(my_Data.get(position).getEstado()==3){
                                        u1.setText("BLOQUEADO");
                                        u1.setTextColor(context.getResources().getColor(R.color.morado_bajo));
                                    }

                                    u2.setText(my_Data.get(position).getNombres()+" "+my_Data.get(position).getApellidos());
                                    u3.setText(String.valueOf(my_Data.get(position).getDni()));
                                    u4.setText(my_Data.get(position).getCorreo());
                                    u5.setText(my_Data.get(position).getArea_usuario().getDescripcion());
                                    u6.setText(my_Data.get(position).getCargo());
                                    u7.setText(my_Data.get(position).getUsuario());
                                    u8.setText(my_Data.get(position).getPerfil().getNombre_Perfil());
                                    u9.setText(my_Data.get(position).getFecha_creacion());
                                    u10.setText(my_Data.get(position).getFecha_conexion()); */
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