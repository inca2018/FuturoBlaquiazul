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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Extras;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;

import java.util.Collections;
import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterEstadisticoPersonaNumero extends RecyclerView.Adapter<AdapterEstadisticoPersonaNumero.ViewHolder>{
    public Context context;
    private List<Extras> my_Data;
    ProgressDialog progressDialog;
    String[] lista_bases_numeros;

    private Boolean spinnerTouched = false;

    int numero_seleccion;
    Extras persona_temp;
    int accion;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public AdapterEstadisticoPersonaNumero(Context context, List<Extras> my_Data, RecyclerViewOnItemClickListener
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
            persona=itemView.findViewById(R.id.card_persona_numero);
            foto=itemView.findViewById(R.id.card_foto_numero);
            spinner_numero=itemView.findViewById(R.id.card_espinner_numero);
        }
        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v,getAdapterPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_estadistico_persona_numero,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
          setAccion(0);
          holder.persona.setText(my_Data.get(position).getPersona().getNombre_Persona()+" "+my_Data.get(position).getPersona().getApellidos_Persona());
          Glide.with(context).load(my_Data.get(position).getPersona().getFoto()).into(holder.foto);
          Listar_Numeros(holder.spinner_numero,position);

          // solo actualiza el spinner  cuando tiene valor en Numero Camiseta

            holder.spinner_numero.setSelection(my_Data.get(position).getNumero_Camiseta(),false);


        holder.spinner_numero.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Real Toco.");
                spinnerTouched = true;
                return false;
            }
        });

          holder.spinner_numero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                  if (spinnerTouched){
                      Object dato=parent.getItemAtPosition(pos);
                      if(String.valueOf(dato).equalsIgnoreCase("-- Seleccione --")){

                      }else{
                          // SOLO PASA CUANDO SELECCIONA NUMERO

                          if(my_Data.get(position).getNumero_Anterior()!=0){
                              setAccion(1);
                              setNumero_seleccion(Integer.parseInt(String.valueOf(dato)));
                              setPersona_temp(my_Data.get(position));
                              notifyDataSetChanged();
                              debug("Paso accion 1");
                          }else{
                              setAccion(2);
                              setNumero_seleccion(Integer.parseInt(String.valueOf(dato)));
                              setPersona_temp(my_Data.get(position));
                              notifyDataSetChanged();
                              debug("Paso accion 2");
                          }

                      }
                  }
                  spinnerTouched = false;

              }
              @Override
              public void onNothingSelected(AdapterView<?> parent) {

              }
          });


    }


    @Override
    public int getItemCount() {
        return my_Data.size();
    }
    public void debug(String msn){
        System.out.println(msn);
    }
    public void Listar_Numeros(Spinner spinner_numero,int position){

            lista_bases_numeros=new String[my_Data.get(position).getLista_Numeros().size()+1];
            lista_bases_numeros[0]="-- Seleccione --";

            for(int i=0;i<my_Data.get(position).getLista_Numeros().size();i++){
                lista_bases_numeros[i+1]=String.valueOf(my_Data.get(position).getLista_Numeros().get(i));
            }

            ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,lista_bases_numeros);
            spinner_numero.setAdapter(adapter_arr);



        }
    public Extras Recuperar_Modificado(){
        Extras ex =new Extras();

          for(int i=0;i<my_Data.size();i++){
              if(my_Data.get(i).isActividad()==true){
                  ex=my_Data.get(i);
              }
          }
        return ex;
    }


    public int getNumero_seleccion() {
        return numero_seleccion;
    }

    public void setNumero_seleccion(int numero_seleccion) {
        this.numero_seleccion = numero_seleccion;
    }

    public Extras getPersona_temp() {
        return persona_temp;
    }

    public void setPersona_temp(Extras persona_temp) {
        this.persona_temp = persona_temp;
    }

    public int getAccion() {
        return accion;
    }

    public void setAccion(int accion) {
        this.accion = accion;
    }
}