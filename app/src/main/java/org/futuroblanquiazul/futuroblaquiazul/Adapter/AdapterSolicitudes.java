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

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.CambiarPassActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Solicitudes;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.AnularSolicitud;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class AdapterSolicitudes extends RecyclerView.Adapter<AdapterSolicitudes.ViewHolder>{
    public Context context;
    private List<Solicitudes> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    ProgressDialog progressDialog;

    public AdapterSolicitudes(Context context, List<Solicitudes> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

         TextView nombres;
         TextView fecha;
         ImageView acciones;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nombres=itemView.findViewById(R.id.solicitudes_nombre);

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


        if(my_Data.get(position).getEstado()==1){
            holder.fecha.setText("ACTIVO");
            holder.fecha.setTextColor(context.getResources().getColor(R.color.verde));
        }else if(my_Data.get(position).getEstado()==2){
            holder.fecha.setText("ANULADO");
            holder.fecha.setTextColor(context.getResources().getColor(R.color.red));
        }else if(my_Data.get(position).getEstado()==3){
            holder.fecha.setText("PROCESADO");
            holder.fecha.setTextColor(context.getResources().getColor(R.color.deep_naranja400));
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

                            if(my_Data.get(position).getEstado()==1){

                                Usuario.SESION_ACTUAL.setSolicitud_temp(my_Data.get(position));

                                Intent intent=new Intent(context,CambiarPassActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(intent);
                            }else if(my_Data.get(position).getEstado()==2){
                                Toast.makeText(context, "Solicitud Anulada", Toast.LENGTH_SHORT).show();
                            }else if(my_Data.get(position).getEstado()==3){
                                Toast.makeText(context, "Solicitud Procesada", Toast.LENGTH_SHORT).show();
                            }

                        }else if(item.getTitle().toString().equalsIgnoreCase("Anular Solicitud")){
                            if(my_Data.get(position).getEstado()==1){

                                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                                builder.setTitle("Anulación")
                                        .setMessage("¿Desea Anular Solicitud?")
                                        .setPositiveButton("SI",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        Anular_Solicitud(context,my_Data.get(position).getId());
                                                        System.out.println("ID_ENVIADO:"+my_Data.get(position).getId());
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




                            }else if(my_Data.get(position).getEstado()==2){
                                Toast.makeText(context, "Solicitud  Anulada", Toast.LENGTH_SHORT).show();
                            }else if(my_Data.get(position).getEstado()==3){
                                Toast.makeText(context, "Solicitud Procesada", Toast.LENGTH_SHORT).show();
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


    public void Anular_Solicitud(final Context context,int codigo){

        String cod=String.valueOf(codigo);


        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Anulación:");
        progressDialog.setMessage("Anulando Solicitud...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Solicitud Anulada Correctamente", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();

                        Intent intent=new Intent(context,PrincipalActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("o","o2");
                        context.startActivity(intent);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error al anular solicitud", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de Anulacion :"+e);
                }
            }
        };

        AnularSolicitud xx = new AnularSolicitud(cod,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

}