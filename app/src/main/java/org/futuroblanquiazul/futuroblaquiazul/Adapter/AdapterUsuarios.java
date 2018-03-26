package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.AlertDialog;
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
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.EdicionUsuarioActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.MantenimientoUsuarioActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.ListaGrupoPruebasActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.ListaPersonasPlantelActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActivarPersona;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.BloquearUsuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.DesactivarUsuario;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.ViewHolder>{
    public Context context;
    private List<Usuario> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    AlertDialog da;

    ProgressDialog progressDialog;

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
        TextView estado;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            foto_usuario=itemView.findViewById(R.id.m_usuarios_foto);
            nombres_usuario=itemView.findViewById(R.id.m_usuarios_nombres);
            usuario_usuario=itemView.findViewById(R.id.m_usuarios_usuario);
            acciones=itemView.findViewById(R.id.m_usuarios_acciones);
            estado=itemView.findViewById(R.id.m_usuarios_estado);
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
        holder.usuario_usuario.setText("Perfil: "+my_Data.get(position).getPerfil().getNombre_Perfil());
        holder.nombres_usuario.setText(my_Data.get(position).getNombres()+" "+my_Data.get(position).getApellidos());

        Glide.with(context).load(my_Data.get(position).getFoto())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.foto_usuario);
        if(my_Data.get(position).getEstado()==1){
            holder.estado.setText("ACTIVO");
            holder.estado.setTextColor(context.getResources().getColor(R.color.verde));
        }else if(my_Data.get(position).getEstado()==2){
            holder.estado.setText("DESACTIVADO");
            holder.estado.setTextColor(context.getResources().getColor(R.color.red));
        }else if(my_Data.get(position).getEstado()==3){
            holder.estado.setText("BLOQUEADO");
            holder.estado.setTextColor(context.getResources().getColor(R.color.morado_bajo));
        }

        holder.acciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu=new PopupMenu(context,holder.acciones);
                popupMenu.getMenuInflater().inflate(R.menu.menu_item_usuarios,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getTitle().toString().equalsIgnoreCase("Editar")){
                            Recursos_Mantenimientos.TEMP.setActualizar_usuarios(true);
                            Usuario.SESION_ACTUAL.setUsuario_mantenimiento(my_Data.get(position));
                            Intent intent= new Intent(context,EdicionUsuarioActivity.class);
                            context.startActivity(intent);

                        }else if(item.getTitle().toString().equalsIgnoreCase("Desactivar")){

                            DesactivarUsuario(context,position);

                        }else if(item.getTitle().toString().equalsIgnoreCase("Bloquear")){

                            BloquearUsuario(context,position);


                        }else if(item.getTitle().toString().equalsIgnoreCase("Información del Usuario")){

                            final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                            final View dialoglayout = inflater.inflate(R.layout.info_mantenimiento_usuarios, null);

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
                            u10.setText(my_Data.get(position).getFecha_conexion());
                        }

                        return true;
                    }
                });

                popupMenu.show();
            }
        });
    }

    private void BloquearUsuario(final Context context, final int position) {

        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("Desactivación")
                .setMessage("¿Desea Bloquear Usuario? \n \n \n"+
                            "Usuario:"+my_Data.get(position).getNombres()+" "+my_Data.get(position).getApellidos()+"  \n \n \n"+
                            "Detalle:  El Bloquea Inabilita al Usuario a realizar Operaciones en el Sistema.")
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                   AccionBloqueo(position,my_Data.get(position).getId(),context);
                            }
                        })
                .setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
        builder.show();
    }
    private void AccionBloqueo(final int position, int id, final Context context) {


        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Usuarios:");
        progressDialog.setMessage("Bloqueando Usuario...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String id_usuario=String.valueOf(id);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                         my_Data.get(position).setEstado(3);
                         notifyDataSetChanged();

                        progressDialog.dismiss();
                    }else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        BloquearUsuario xx = new BloquearUsuario(id_usuario, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    private void DesactivarUsuario(final Context context,final int position) {

        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("Desactivación")
                .setMessage("¿Desea Desactivar Usuario? \n \n \n"+
                        "Usuario:"+my_Data.get(position).getNombres()+" "+my_Data.get(position).getApellidos()+"  \n \n \n"+
                        "Detalle:  La Desactivación deshabilita al Usuario en el Sistema")
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AccionDesactivarUsuario(position,my_Data.get(position).getId(),context);

                            }
                        })
                .setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
        builder.show();


    }

    private void AccionDesactivarUsuario(final int position,final int id,final Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Usuarios:");
        progressDialog.setMessage("Desactivar Usuario...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String id_usuario=String.valueOf(id);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        my_Data.get(position).setEstado(2);
                        notifyDataSetChanged();

                        progressDialog.dismiss();
                    }else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        DesactivarUsuario xx = new DesactivarUsuario(id_usuario, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }
}