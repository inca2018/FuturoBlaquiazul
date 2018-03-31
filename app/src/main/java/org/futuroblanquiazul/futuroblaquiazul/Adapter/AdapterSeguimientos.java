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
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.ListaPersonaSeguimientoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.ListaSeguimientosActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Seguimiento;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.EliminarSeguimiento;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.MigrarFasePrueba;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AdapterSeguimientos extends RecyclerView.Adapter<AdapterSeguimientos.ViewHolder>{
    public Context context;
    private List<Seguimiento> my_Data;
    ProgressDialog progressDialog;
    AlertDialog da;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AdapterSeguimientos(Context context, List<Seguimiento> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public TextView codigo_seguimiento;
        public ImageView acciones;
        public TextView nombre_competencia;
        public TextView total;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            codigo_seguimiento=itemView.findViewById(R.id.card_seguimiento_codigo);
            acciones=itemView.findViewById(R.id.card_seguimiento2_acciones);
            nombre_competencia=itemView.findViewById(R.id.card_seguimiento_competencia);
            total=itemView.findViewById(R.id.card_seguimiento_total);

        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_seguimiento,parent,false);
        return new ViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
              holder.nombre_competencia.setText(my_Data.get(position).getNombre_Competencia().toUpperCase());
              holder.total.setText(String.valueOf(my_Data.get(position).getTotal_Puntaje()));
              holder.codigo_seguimiento.setText(my_Data.get(position).getCodigo_Seguimiento());

              holder.acciones.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      final PopupMenu popupMenu=new PopupMenu(context,holder.acciones);
                      popupMenu.getMenuInflater().inflate(R.menu.menu_item_seguimiento2,popupMenu.getMenu());
                      popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                          @Override
                          public boolean onMenuItemClick(MenuItem item) {
                              if(item.getTitle().toString().equalsIgnoreCase("Información de Seguimiento")){

                                  final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                                  final View dialoglayout = inflater.inflate(R.layout.area_informacion_seguimiento,null);

                                  TextView p_nombre,p_creador,p_ubigeo,p_competencia,p_rival,p_minutos,p_1,p_2,p_3,p_4,p_5,p_total,p_fecha;

                                  p_nombre=dialoglayout.findViewById(R.id.info_seg_nombres);
                                  p_creador=dialoglayout.findViewById(R.id.info_seg_usuario);
                                  p_ubigeo=dialoglayout.findViewById(R.id.info_seg_ubigeo);
                                  p_competencia=dialoglayout.findViewById(R.id.info_seg_competencia);
                                  p_rival=dialoglayout.findViewById(R.id.info_seg_rival);
                                  p_minutos=dialoglayout.findViewById(R.id.info_seg_minutos_jugados);
                                  p_1=dialoglayout.findViewById(R.id.info_seg_pb);
                                  p_2=dialoglayout.findViewById(R.id.info_seg_rb);
                                  p_3=dialoglayout.findViewById(R.id.info_seg_pg);
                                  p_4=dialoglayout.findViewById(R.id.info_seg_dr);
                                  p_5=dialoglayout.findViewById(R.id.info_seg_goles);
                                  p_total=dialoglayout.findViewById(R.id.info_seg_total);
                                  p_fecha=dialoglayout.findViewById(R.id.info_seg_fecha);


                                  final AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
                                  builder4.setView(dialoglayout);
                                  da=builder4.show();


                                  p_nombre.setText(Usuario.SESION_ACTUAL.getPersona_seguimiento().getNombre_Persona()+" "+Usuario.SESION_ACTUAL.getPersona_seguimiento().getApellidos_Persona());
                                  p_creador.setText(my_Data.get(position).getUsuario().getNombres()+" "+my_Data.get(position).getUsuario().getApellidos());
                                  p_ubigeo.setText(Usuario.SESION_ACTUAL.getPersona_seguimiento().getUbigeo());
                                  p_competencia.setText(my_Data.get(position).getNombre_Competencia());
                                  p_rival.setText(my_Data.get(position).getNombre_Rival());
                                  p_minutos.setText(String.valueOf(my_Data.get(position).getMinutos_Juego()));
                                  p_1.setText(String.valueOf(my_Data.get(position).getCantidad_Pierde_Balon()));
                                  p_2.setText(String.valueOf(my_Data.get(position).getCantidad_Recupera_Balon()));
                                  p_3.setText(String.valueOf(my_Data.get(position).getCantidad_Pase_Gol()));
                                  p_4.setText(String.valueOf(my_Data.get(position).getCantidad_Dribbling()));
                                  p_5.setText(String.valueOf(my_Data.get(position).getGoles()));
                                  p_total.setText(String.valueOf(my_Data.get(position).getTotal_Puntaje()));
                                  p_fecha.setText(my_Data.get(position).getFecha_Registro());



                              }else if(item.getTitle().toString().equalsIgnoreCase("Eliminar Seguimiento")){
                                  final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                                  builder.setTitle("Seguimientos")
                                          .setMessage("¿Desea Eliminar Seguimiento?")
                                          .setPositiveButton("SI",
                                                  new DialogInterface.OnClickListener() {
                                                      @Override
                                                      public void onClick(DialogInterface dialog, int which) {
                                                         Eliminar_Seguimiento(my_Data.get(position).getId(),context);
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

                              return true;
                          }
                      });

                      popupMenu.show();

                  }
              });
    }

    private void Eliminar_Seguimiento(int id,final Context context) {

        String id_persona=String.valueOf(id);

        System.out.println("ELIMINAR SEGUIMIENTO" );

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Seguimiento:");
        progressDialog.setMessage("Eliminando Seguimiento...");
        progressDialog.show();

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        Toast.makeText(context, "Seguimiento eliminado correctamente!", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        Intent intent = new Intent(context,ListaSeguimientosActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        progressDialog.dismiss();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "No se pudo eliminar", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al eliminar seguimiento:"+e);
                }
            }
        };

        EliminarSeguimiento xx = new EliminarSeguimiento(id_persona,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }

    public Double getPromedio(){
        double suma=0;
        double promedio;

           for(int i=0;i<my_Data.size();i++){
               suma=suma+my_Data.get(i).getTotal_Puntaje();
           }
           promedio=suma/my_Data.size();

        return promedio;
    }

}