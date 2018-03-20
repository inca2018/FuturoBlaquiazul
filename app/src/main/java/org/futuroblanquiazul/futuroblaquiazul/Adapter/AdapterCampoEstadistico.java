package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Campo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.CampoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.List;

public class AdapterCampoEstadistico extends RecyclerView.Adapter<AdapterCampoEstadistico.ViewHolder> {
    private Context context;
    private List<CampoEstadistico> my_Data;

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    int Puntostotal=0;
    AlertDialog da;

    public AdapterCampoEstadistico(Context context, List<CampoEstadistico> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;

    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btn;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
                  btn=itemView.findViewById(R.id.card_campos_boton_estadistico);

        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_campo_estadistico,parent,false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

         holder.btn.getLayoutParams().height=my_Data.get(position).getAltura();
         holder.btn.setHeight(my_Data.get(position).getAltura());

         holder.btn.setText(my_Data.get(position).getDato());
         holder.btn.setTextColor(context.getResources().getColor(R.color.blanco));

         holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                final View dialoglayout4 = inflater.inflate(R.layout.gestion_campo_estadistico, null);

                final Spinner spinner_personas= dialoglayout4.findViewById(R.id.card_campoesta_personas);
                final Spinner spinner_opciones= dialoglayout4.findViewById(R.id.card_campoesta_opciones);
                final Button boton_guardar= dialoglayout4.findViewById(R.id.card_campoesta_boton);

                final AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
                builder4.setView(dialoglayout4);
                da=builder4.show();

                 Listar_Nombres_Titulares(spinner_personas,position);

                boton_guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(spinner_opciones.getSelectedItemPosition()==0){
                            Toast.makeText(context, "Seleccione una Opción", Toast.LENGTH_SHORT).show();
                        }else{
                            if(spinner_personas.getSelectedItemPosition()==0){
                                Toast.makeText(context,"Seleccione un Jugador",Toast.LENGTH_SHORT).show();
                            }else{
                                //PASOOO A GUARDAR
                            }
                        }

                    }
                });
            }
        });
    }
    private void Listar_Nombres_Titulares(Spinner spinner_personas,final int position) {
        String [] lista_nombres_personas=new String[my_Data.get(position).getNombres_Titulares().size()+1];

        for(int i=0;i<my_Data.get(position).getNombres_Titulares().size();i++){
            lista_nombres_personas[i]=String.valueOf(my_Data.get(position).getNombres_Titulares().get(i)).trim();
        }

        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,lista_nombres_personas);
        spinner_personas.setAdapter(adapter_arr);
    }
    @Override
    public int getItemCount() {
        return my_Data.size();
    }


}
