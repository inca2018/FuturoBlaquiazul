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

import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.ListaGrupoPruebasActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.ListaPersonasPlantelActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.ViewHolder>{
    public Context context;
    private List<Usuario> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    AlertDialog da;

    public AdapterUsuarios(Context context, List<Usuario> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        ImageView foto_usuario;
        TextView nombres_usuario,usuario_usuario;
        ImageView acciones;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            foto_usuario=itemView.findViewById(R.id.m_usuarios_foto);
            nombres_usuario=itemView.findViewById(R.id.m_usuarios_nombres);
            usuario_usuario=itemView.findViewById(R.id.m_usuarios_usuario);
            acciones=itemView.findViewById(R.id.m_usuarios_acciones);


        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_usuarios,parent,false);
        return new ViewHolder(itemView);



    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.usuario_usuario.setText(my_Data.get(position).getUsuario());
        holder.nombres_usuario.setText(my_Data.get(position).getNombres()+" "+my_Data.get(position).getApellidos());
        Glide.with(context).load(my_Data.get(position).getFoto()).into(holder.foto_usuario);
    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }
}