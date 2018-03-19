package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Debug;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Extras;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Estadistico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Jesus on 23/12/2016.
 */
public class AdapterEstadisticoPersonaNumero extends RecyclerView.Adapter<AdapterEstadisticoPersonaNumero.ViewHolder>{
    public Context context;
    private List<Extras> my_Data;
    ProgressDialog progressDialog;


    private Boolean spinnerTouched = false;
    private Boolean checkTouched = false;

    int numero_seleccion;
    Extras persona_temp;
    int accion;
    int ubicacion;
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
        public CheckBox habilitar;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            persona=itemView.findViewById(R.id.card_persona_numero);
            foto=itemView.findViewById(R.id.card_foto_numero);
            spinner_numero=itemView.findViewById(R.id.card_espinner_numero);
            habilitar=itemView.findViewById(R.id.definir_numero);
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

          holder.persona.setText(my_Data.get(position).getPersona().getNombre_Persona()+" "+my_Data.get(position).getPersona().getApellidos_Persona());
          Glide.with(context).load(my_Data.get(position).getPersona().getFoto()).into(holder.foto);

          Listar_Numeros(holder.spinner_numero,position);

          // solo actualiza el spinner  cuando tiene valor en Numero Camiseta

       if(my_Data.get(position).getEstado2()==0){
           holder.habilitar.setChecked(false);
       }else{
           holder.habilitar.setChecked(true);
       }


        holder.habilitar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Check Toco.");
                checkTouched = true;
                return false;
            }
        });

       holder.habilitar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

               if (checkTouched){
                   String dato=String.valueOf(holder.spinner_numero.getSelectedItem());
                   if(isChecked==true){
                       System.out.println("HABILITAR DATO");
                       if(dato.equalsIgnoreCase("-- SELECCIONE --")){
                           Toast.makeText(context, "Opcion no aceptada", Toast.LENGTH_SHORT).show();
                           holder.habilitar.setChecked(false);
                       }else{
                            if(Recursos_Estadistico.LISTA_NUMEROS_SELECCIONADOS.size()!=0){
                                System.out.println("LISTA CON DATOS");
                                boolean fg=false;
                                  for(int i=0;i<Recursos_Estadistico.LISTA_NUMEROS_SELECCIONADOS.size();i++){
                                      if(Recursos_Estadistico.LISTA_NUMEROS_SELECCIONADOS.get(i)==Integer.parseInt(dato)){
                                          fg=true;
                                      }
                                  }

                                  if(fg){
                                      System.out.println("LISTA CON DATOS Y ENCONTRADO  - ACTUALIZAR");
                                      Toast.makeText(context, "Numero no Disponible", Toast.LENGTH_SHORT).show();
                                      holder.habilitar.setChecked(false);
                                      holder.spinner_numero.setSelection(0);
                                  }else{
                                      System.out.println("LISTA CON DATOS Y NO ENCONTRADO - NUEVO");
                                      Recursos_Estadistico.LISTA_NUMEROS_SELECCIONADOS.add(Integer.parseInt(dato));
                                      my_Data.get(position).setEstado2(1);
                                      my_Data.get(position).setNumero_Camiseta(Integer.parseInt(dato));
                                      notifyDataSetChanged();
                                  }
                            }else{
                                System.out.println("LISTA sin DATOS");
                                 Recursos_Estadistico.LISTA_NUMEROS_SELECCIONADOS.add(Integer.parseInt(dato));
                                 my_Data.get(position).setEstado2(1);
                                 my_Data.get(position).setNumero_Camiseta(Integer.parseInt(dato));
                                 notifyDataSetChanged();
                            }
                       }

                   }else{
                       System.out.println("DESHABILITAR DATO");
                       my_Data.get(position).setEstado2(0);
                       for(int i=0;i<Recursos_Estadistico.LISTA_NUMEROS_SELECCIONADOS.size();i++){
                           if(Recursos_Estadistico.LISTA_NUMEROS_SELECCIONADOS.get(i)==my_Data.get(position).getNumero_Camiseta()){
                               Recursos_Estadistico.LISTA_NUMEROS_SELECCIONADOS.remove(i);
                           }
                       }
                       my_Data.get(position).setNumero_Camiseta(0);

                       notifyDataSetChanged();
                   }
               }
               checkTouched = false;
           }

       });

        if(my_Data.get(position).getNumero_Camiseta()!=0){
            holder.spinner_numero.setSelection(my_Data.get(position).getNumero_Camiseta());
        }
        holder.spinner_numero.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Spinner Toco.");
                spinnerTouched = true;
                return false;
            }
        });


       holder.spinner_numero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
               if (spinnerTouched){
                   Object dato=parent.getItemAtPosition(pos);
                     if(my_Data.get(position).getEstado2()==1){
                         if(!String.valueOf(dato).equalsIgnoreCase("-- SELECCIONE --")){
                              if(my_Data.get(position).getNumero_Camiseta()!=Integer.parseInt(String.valueOf(dato))){
                                  Quitar_Elemento(my_Data.get(position).getNumero_Camiseta());
                                  holder.habilitar.setChecked(false);
                                  debug("CAMBIO YA SELECCIONADO");
                              }
                         }
                     }else{
                         debug("ENTRO A SPINNER SIN CAMBIO ALGUNO");
                     }
               }
               spinnerTouched = false;
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
    }

    private void Quitar_Elemento(int numero_camiseta) {
         for(int i=0;i<Recursos_Estadistico.LISTA_NUMEROS_SELECCIONADOS.size();i++){
             if(numero_camiseta==Recursos_Estadistico.LISTA_NUMEROS_SELECCIONADOS.get(i)){
                 Recursos_Estadistico.LISTA_NUMEROS_SELECCIONADOS.remove(i);
             }
         }
    }

    @Override
    public int getItemCount() {
        return my_Data.size();
    }

    public void Listar_Numeros(Spinner spinner_numero,int position){

            String [] lista_bases_numeros=new String[my_Data.get(position).getLista_Numeros().size()+1];
            lista_bases_numeros[0]="-- SELECCIONE --";

            for(int i=0;i<my_Data.get(position).getLista_Numeros().size();i++){
                lista_bases_numeros[i+1]=String.valueOf(my_Data.get(position).getLista_Numeros().get(i)).trim();
            }

            ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,lista_bases_numeros);
            spinner_numero.setAdapter(adapter_arr);



           /*
            if(my_Data.get(position).getNumero_Camiseta()!=0){

                  System.out.println("-----------------------------------------Personas Seleccion:"+my_Data.get(position).getPersona().getId());
                for(int i=0;i<lista_bases_numeros.length;i++){
                  System.out.println(" Posicion: "+i+" Numero "+lista_bases_numeros[i]);
                }

                for(int i=0;i<lista_bases_numeros.length;i++){
                    if(lista_bases_numeros[i].equalsIgnoreCase(String.valueOf(my_Data.get(position).getNumero_Camiseta()).trim())){
                        ubicacion=i;
                    }
                }
                if(ubicacion==0){
                    System.out.println("No encontrado");
                }else{
                    spinner_numero.setSelection(ubicacion);
                    System.out.println("Posicion Buscado :"+ubicacion);
                }
            }else{
                System.out.println("-----------------------------------------Personas NO Seleccionada:"+my_Data.get(position).getPersona().getId());
                for(int i=0;i<lista_bases_numeros.length;i++){
                    System.out.println(" Posicion : "+i+" Numero "+lista_bases_numeros[i]);
                }
            }

            */

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

    public  void debug(String msn){
        System.out.println(msn);
    }
}