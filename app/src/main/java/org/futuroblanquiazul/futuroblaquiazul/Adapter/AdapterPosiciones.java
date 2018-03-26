package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Debug;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos.MantenimientoPosicionesActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Campo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Posicion;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.EliminarPos1;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarPosicion1;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdapterPosiciones extends RecyclerView.Adapter<AdapterPosiciones.ViewHolder> {
    private Context context;
    private List<Posicion> my_Data;

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    int Puntostotal=0;
    AlertDialog da;

    public AdapterPosiciones(Context context, List<Posicion> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;

    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView num,posicion;
        ImageView eliminar;
        public ViewHolder(View itemView) {
            super(itemView);
         num=itemView.findViewById(R.id.card_mant_pos_numero);
         posicion=itemView.findViewById(R.id.card_mant_pos_posicion);
         eliminar=itemView.findViewById(R.id.card_mant_pos_eliminar);

        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_mant_posicion,parent,false);
        return new ViewHolder(itemView);

    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

       holder.num.setText(String.valueOf(my_Data.get(position).getNum()));
       holder.posicion.setText(my_Data.get(position).getNombre_Posicione());

       holder.eliminar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
               builder.setTitle("Posiciones:")
                       .setMessage("¿Desea Eliminar Posicion? \n \n \n"+"- "+my_Data.get(position).getNombre_Posicione())
                       .setPositiveButton("SI",
                               new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                       Eliminar_Posicion(my_Data.get(position).getId(),context,position);
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
       });
    }

    private void Eliminar_Posicion(int id,final Context context,final int pos) {
        String id_posicion=String.valueOf(id);
        debug("CODIGO ENVIADO "+id_posicion);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Toast.makeText(context, "Posición Eliminado Correctamente!", Toast.LENGTH_SHORT).show();

                        MantenimientoPosicionesActivity.MANT_POS.Actualizar_Pos1(context);

                    }else {

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error ACTIVAR :"+e);
                }
            }
        };

        EliminarPos1 xx = new EliminarPos1(id_posicion, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }


    @Override
    public int getItemCount() {
        return my_Data.size();
    }


    public void debug(String d){
        System.out.println(d);
    }

}
