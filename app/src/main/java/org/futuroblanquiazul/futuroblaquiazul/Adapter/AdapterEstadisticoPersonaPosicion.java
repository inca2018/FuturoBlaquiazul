package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterEstadisticoPersonaPosicion extends RecyclerView.Adapter<AdapterEstadisticoPersonaPosicion.ViewHolder>{
    public Context context;
    private List<Persona> my_Data;
    private Boolean spinnerTouched = false;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AdapterEstadisticoPersonaPosicion(Context context, List<Persona> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public TextView persona;
        public ImageView foto;
        public Spinner spinner_numero;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            persona=itemView.findViewById(R.id.card_persona_numero2);
            foto=itemView.findViewById(R.id.card_foto_numero2);
            spinner_numero=itemView.findViewById(R.id.card_espinner_numero2);
        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_estadistico_persona_posicion,parent,false);
        return new ViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(my_Data.get(position).getEstado_posicion()==1){
            holder.spinner_numero.setSelection(my_Data.get(position).getPosicion_posicion());
        }

        holder.persona.setText(my_Data.get(position).getNombre_Persona()+" "+my_Data.get(position).getApellidos_Persona());
        Glide.with(context).load(my_Data.get(position).getFoto()).into(holder.foto);


        Listar_Posiciones(holder.spinner_numero,position);



        holder.spinner_numero.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Check Toco.");
                spinnerTouched = true;
                return false;
            }
        });
        holder.spinner_numero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if (spinnerTouched){

                    Object dato=parent.getItemAtPosition(pos);
                    if(String.valueOf(dato).equalsIgnoreCase("-- SELECCIONE --")){
                          my_Data.get(position).setEstado_posicion(0);
                          my_Data.get(position).setCodigo_posicion(0);
                          my_Data.get(position).setPosicion_posicion(0);
                          notifyDataSetChanged();
                          debug(" SELECCION 0");

                    }else{
                          my_Data.get(position).setEstado_posicion(1);
                          my_Data.get(position).setCodigo_posicion(my_Data.get(position).getLista_Posiciones().get(pos).getId());
                          my_Data.get(position).setPosicion_posicion(pos);
                        notifyDataSetChanged();


                        debug(" SELECCION POSICION");
                    }

                }

                spinnerTouched=false;

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    private void Listar_Posiciones(Spinner spinner_numero, int position) {


        String [] lista_bases_numeros=new String[my_Data.get(position).getLista_Posiciones().size()];

        for(int i=0;i<my_Data.get(position).getLista_Posiciones().size();i++){
            lista_bases_numeros[i]=String.valueOf(my_Data.get(position).getLista_Posiciones().get(i).getNombre_Posicione().toString()).trim();

        }

        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,lista_bases_numeros);
        spinner_numero.setAdapter(adapter_arr);



    }
    @Override
    public int getItemCount() {
        return my_Data.size();
    }

    public void debug(String msn){
        System.out.println(msn);
    }

}