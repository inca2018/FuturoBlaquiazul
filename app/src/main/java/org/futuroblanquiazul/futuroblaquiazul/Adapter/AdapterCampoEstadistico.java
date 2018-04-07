package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Debug;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.futuroblanquiazul.futuroblaquiazul.Entity.CampoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.OpcionEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PersonaEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Estadistico_Gestion;
import org.futuroblanquiazul.futuroblaquiazul.Utils.RecursoEstadisticoRadioButton;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Estadistico;

import java.util.ArrayList;
import java.util.List;

public class AdapterCampoEstadistico extends RecyclerView.Adapter<AdapterCampoEstadistico.ViewHolder> implements View.OnClickListener{
    private Context context;
    private List<CampoEstadistico> my_Data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    AlertDialog da;
    public AdapterCampoEstadistico(Context context, List<CampoEstadistico> my_Data, RecyclerViewOnItemClickListener
            recyclerViewOnItemClickListener) {
        this.context = context;
        this.my_Data = my_Data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;

    }
    @Override
    public void onClick(View v) {
        Gestion_Click(v);
    }
    private void Gestion_Click(View v) {
        for(int i=0;i<Estadistico_Gestion.RADIOS.size();i++){
            if(Estadistico_Gestion.RADIOS.get(i).getId()==v.getId()){
                Estadistico_Gestion.RADIOS.get(i).setEstado(2);
                Estadistico_Gestion.RADIOS.get(i).getRadioButton().setChecked(true);
            }else{
                Estadistico_Gestion.RADIOS.get(i).setEstado(1);
                Estadistico_Gestion.RADIOS.get(i).getRadioButton().setChecked(false);
            }
        }
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button btn;
        FrameLayout base;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            btn=itemView.findViewById(R.id.card_campos_boton_estadistico);
            base=itemView.findViewById(R.id.base_frame2);


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

         if(my_Data.get(position).getOpcion()!=null){
             holder.btn.setText(my_Data.get(position).getOpcion().getOpcion());
             holder.btn.setTextColor(context.getResources().getColor(R.color.blanco));


             switch (my_Data.get(position).getOpcion().getOpcion()){
                 case "PG":
                     holder.base.setBackgroundColor(context.getResources().getColor(R.color.deep_naranja400));
                     break;
                 case "DR":
                     holder.base.setBackgroundColor(context.getResources().getColor(R.color.deep_naranja400));
                     break;
                 case "OG":
                     holder.base.setBackgroundColor(context.getResources().getColor(R.color.deep_naranja400));
                     break;
                 case "R":
                     holder.base.setBackgroundColor(context.getResources().getColor(R.color.deep_naranja400));
                     break;
                 case "G":
                     holder.base.setBackgroundColor(context.getResources().getColor(R.color.deep_naranja400));
                     break;
                 case "OF":
                     holder.base.setBackgroundColor(context.getResources().getColor(R.color.deep_naranja400));
                     break;
                 case "BP":
                     holder.base.setBackgroundColor(context.getResources().getColor(R.color.deep_naranja400));
                     break;
                 case "BR":
                     holder.base.setBackgroundColor(context.getResources().getColor(R.color.card));
                     break;
                 case "F":
                     holder.base.setBackgroundColor(context.getResources().getColor(R.color.card));
                     break;
                 case "TA":
                     holder.base.setBackgroundColor(context.getResources().getColor(R.color.amarillo));
                     break;
                 case "TR":
                     holder.base.setBackgroundColor(context.getResources().getColor(R.color.red));
                     break;
                 case "ATJ":
                     holder.base.setBackgroundColor(context.getResources().getColor(R.color.card));
                     break;

                 case "GO":
                     holder.base.setBackgroundColor(context.getResources().getColor(R.color.red));
                     break;

                 default:
                     holder.base.setBackgroundColor(Color.TRANSPARENT);

             }
         }


         holder.btn.setOnLongClickListener(new View.OnLongClickListener() {
             @Override
             public boolean onLongClick(View v) {
                 if(my_Data.get(position).getOpcion()==null){
                     Toast.makeText(context, "Area sin Información!", Toast.LENGTH_SHORT).show();
                 }else{
                     final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                     final View dialoglayout = inflater.inflate(R.layout.gestion_campo_estadistico_info, null);

                     LinearLayout area_jugador=dialoglayout.findViewById(R.id.in_area_jugador);
                     ImageView icon_jugador=dialoglayout.findViewById(R.id.in_icono_jugador);
                     ImageView icon_opcion=dialoglayout.findViewById(R.id.in_icono_opcion);
                     TextView nom_jugador=dialoglayout.findViewById(R.id.in_nombre_jugador);
                     TextView nom_opcion=dialoglayout.findViewById(R.id.in_nombre_opcion);

                     final AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
                     builder4.setView(dialoglayout);
                     da=builder4.show();

                     if(my_Data.get(position).getPersona()==null){
                         area_jugador.setVisibility(View.GONE);
                         icon_opcion.setImageResource(R.mipmap.icon_opcion_gol);
                         nom_opcion.setText("GOL DE OPONENTE");
                     }else{
                         area_jugador.setVisibility(View.VISIBLE);

                         switch (my_Data.get(position).getOpcion().getOpcion()){
                             case "PG":
                                 icon_opcion.setImageResource(R.mipmap.icon_opcion_pase_gol);
                                 nom_opcion.setText("PASE GOL");
                                 nom_jugador.setText(my_Data.get(position).getPersona().getNombre_Persona()+" "+my_Data.get(position).getPersona().getApellidos_Persona());
                                 break;
                             case "DR":
                                 icon_opcion.setImageResource(R.mipmap.icon_opcion_dribbling);
                                 nom_opcion.setText("DRIBBLING");
                                 nom_jugador.setText(my_Data.get(position).getPersona().getNombre_Persona()+" "+my_Data.get(position).getPersona().getApellidos_Persona());
                                 break;
                             case "OG":
                                 icon_opcion.setImageResource(R.mipmap.icon_opcion_opcion_gol);
                                 nom_opcion.setText("OPCION DE GOL");
                                 nom_jugador.setText(my_Data.get(position).getPersona().getNombre_Persona()+" "+my_Data.get(position).getPersona().getApellidos_Persona());
                                 break;
                             case "R":
                                 icon_opcion.setImageResource(R.mipmap.icon_opcion_remate);
                                 nom_opcion.setText("REMATE");
                                 nom_jugador.setText(my_Data.get(position).getPersona().getNombre_Persona()+" "+my_Data.get(position).getPersona().getApellidos_Persona());
                                 break;
                             case "G":
                                 icon_opcion.setImageResource(R.mipmap.icon_opcion_gol);
                                 nom_opcion.setText("GOL");
                                 nom_jugador.setText(my_Data.get(position).getPersona().getNombre_Persona()+" "+my_Data.get(position).getPersona().getApellidos_Persona());
                                 break;
                             case "OF":
                                 icon_opcion.setImageResource(R.mipmap.icon_opcion_off_sides);
                                 nom_opcion.setText("OFF SIDE");
                                 nom_jugador.setText(my_Data.get(position).getPersona().getNombre_Persona()+" "+my_Data.get(position).getPersona().getApellidos_Persona());
                                 break;
                             case "BP":
                                 icon_opcion.setImageResource(R.mipmap.icon_opcion_balones_perdidos);
                                 nom_opcion.setText("BALON PERDIDO");
                                 nom_jugador.setText(my_Data.get(position).getPersona().getNombre_Persona()+" "+my_Data.get(position).getPersona().getApellidos_Persona());
                                 break;
                             case "BR":
                                 icon_opcion.setImageResource(R.mipmap.icon_opcion_balones_recuperados);
                                 nom_opcion.setText("BALON RECUPERADO");
                                 nom_jugador.setText(my_Data.get(position).getPersona().getNombre_Persona()+" "+my_Data.get(position).getPersona().getApellidos_Persona());
                                 break;
                             case "F":
                                 icon_opcion.setImageResource(R.mipmap.icon_opcion_faltas);
                                 nom_opcion.setText("FALTA");
                                 nom_jugador.setText(my_Data.get(position).getPersona().getNombre_Persona()+" "+my_Data.get(position).getPersona().getApellidos_Persona());
                                 break;
                             case "TA":
                                 icon_opcion.setImageResource(R.mipmap.icon_opcion_tarjeta_amarilla);
                                 nom_opcion.setText("TARJETA AMARILLA");
                                 nom_jugador.setText(my_Data.get(position).getPersona().getNombre_Persona()+" "+my_Data.get(position).getPersona().getApellidos_Persona());
                                 break;
                             case "TR":
                                 icon_opcion.setImageResource(R.mipmap.icon_opcion_tarjeta_roja);
                                 nom_opcion.setText("TARJETA ROJA");
                                 nom_jugador.setText(my_Data.get(position).getPersona().getNombre_Persona()+" "+my_Data.get(position).getPersona().getApellidos_Persona());
                                 break;
                             case "ATJ":
                                 icon_opcion.setImageResource(R.mipmap.icon_opcion_atajada);
                                 nom_opcion.setText("ATAJADA");
                                 nom_jugador.setText(my_Data.get(position).getPersona().getNombre_Persona()+" "+my_Data.get(position).getPersona().getApellidos_Persona());
                                 break;

                         }

                     }


                 }


                 return true;
             }
         });
         holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Estadistico_Gestion.TEMP.getEstado_partido()==4){
                    Toast.makeText(context, "Partido Finalizado", Toast.LENGTH_SHORT).show();
                }else{
                    if(Estadistico_Gestion.TEMP.getTiempo_actual()==0){
                        Toast.makeText(context, "Iniciar Partido", Toast.LENGTH_SHORT).show();
                    }else{
                        final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                        final View dialoglayout4 = inflater.inflate(R.layout.gestion_campo_estadistico, null);

                        final Spinner spinner_personas= dialoglayout4.findViewById(R.id.card_campoesta_personas);
                        final Button boton_guardar= dialoglayout4.findViewById(R.id.card_campoesta_boton);
                        final ImageView vaciar=dialoglayout4.findViewById(R.id.limpiar_opcion);

                        Recuperar_Radios(dialoglayout4);

                        final AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
                        builder4.setView(dialoglayout4);
                        da=builder4.show();


                        Listar_Nombres_Jugador(spinner_personas,position);

                        vaciar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                my_Data.get(position).setOpcion(null);
                                notifyDataSetChanged();
                                da.dismiss();
                            }
                        });

                        boton_guardar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                if(Buscar_GolOponente()){
                                    System.out.println("ENTRO A BUSCAR GOL OPONENTE");
                                    RecursoEstadisticoRadioButton campo=Encontrar_Opcion();
                                    if(campo!=null){
                                        OpcionEstadistico O=new OpcionEstadistico();
                                        O.setCodigo(campo.getEstado());
                                        O.setOpcion(campo.getDesc());
                                        my_Data.get(position).setOpcion(O);



                                        System.out.println("Opcion Seleccionada CODIGO:"+ O.getCodigo()+" OPCION:"+O.getOpcion());

                                        String dd="GOL OPONENTE ->"+Estadistico_Gestion.TEMP.getMinutos_jugados()+"´ "+Estadistico_Gestion.TEMP.getSegundos_jugados()+"´´";
                                        Estadistico_Gestion.LISTA_LINEA_TIEMPO.add(dd);

                                        notifyDataSetChanged();

                                        Limpiar_Check();

                                        da.dismiss();

                                    }else{
                                        Toast.makeText(context, "Error al Recuperar Opcion", Toast.LENGTH_SHORT).show();
                                    }

                                }else{
                                    if(spinner_personas.getSelectedItemPosition()==0){
                                        Toast.makeText(context,"Seleccione un Jugador",Toast.LENGTH_SHORT).show();
                                    }else{
                                        if(Buscar_Seleccion()){
                                            System.out.println("NO ENTRO A BUSCAR GOL OPONENTE");
                                            Persona persona=Encontrar_Persona(spinner_personas.getSelectedItem());
                                            if(persona!=null){
                                                my_Data.get(position).setPersona(persona);
                                                System.out.println("Persona Seleccionada:"+ persona.getId());

                                                RecursoEstadisticoRadioButton campo=Encontrar_Opcion();
                                                if(campo!=null){
                                                    OpcionEstadistico O=new OpcionEstadistico();
                                                    O.setCodigo(campo.getEstado());
                                                    O.setOpcion(campo.getDesc());


                                                    Tarjetas_Mensaje(O,persona,position);

                                                    my_Data.get(position).setOpcion(O);

                                                    System.out.println("Opcion Seleccionada CODIGO:"+ O.getCodigo()+" OPCION:"+O.getOpcion());
                                                    notifyDataSetChanged();
                                                    Limpiar_Check();
                                                    da.dismiss();

                                                }else{
                                                    Toast.makeText(context, "Error al Recuperar Opcion", Toast.LENGTH_SHORT).show();
                                                }
                                            }else{
                                                Toast.makeText(context, "Error al Recuperar Jugador", Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            Toast.makeText(context, "Seleccione una Opcion", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });
                        Evento_click_Radios();
                    }
                }
            }
        });
    }

    private void Tarjetas_Mensaje(OpcionEstadistico o, Persona persona, int position) {

        if(o.getOpcion().equalsIgnoreCase("TA")){
            boolean si=Buscar_Jugador(persona);
            if(si){

                String dd="TARJETA AMARILLA -> "+persona.getApellidos_Persona()+" "+Estadistico_Gestion.TEMP.getMinutos_jugados()+"´"+Estadistico_Gestion.TEMP.getSegundos_jugados()+"´´";
                Estadistico_Gestion.LISTA_LINEA_TIEMPO.add(dd);

                Expulsar_Jugador(persona);
                my_Data.get(position).setOpcion(o);
                Toast.makeText(context, "Jugador "+persona.getNombre_Persona()+" "+persona.getApellidos_Persona() +" Expulsado por acumular  2 Amarillas", Toast.LENGTH_SHORT).show();

                String d="Expulsión -> "+"Jugador "+persona.getNombre_Persona()+" "+persona.getApellidos_Persona()+" - "+Estadistico_Gestion.TEMP.getMinutos_jugados()+"´"+Estadistico_Gestion.TEMP.getSegundos_jugados()+"´´" ;
                Estadistico_Gestion.LISTA_LINEA_TIEMPO.add(d);


            }else{
                my_Data.get(position).setOpcion(o);
                String dd="TARJETA AMARILLA -> "+persona.getApellidos_Persona()+" "+Estadistico_Gestion.TEMP.getMinutos_jugados()+"´"+Estadistico_Gestion.TEMP.getSegundos_jugados()+"´´";
                Estadistico_Gestion.LISTA_LINEA_TIEMPO.add(dd);
            }
        }

        Capturar_Tiempo(o,persona);

        if(o.getOpcion().equalsIgnoreCase("TR")){

            String dd="TARJETA ROJA -> "+persona.getApellidos_Persona()+" "+Estadistico_Gestion.TEMP.getMinutos_jugados()+"´"+Estadistico_Gestion.TEMP.getSegundos_jugados()+"´´";
            Estadistico_Gestion.LISTA_LINEA_TIEMPO.add(dd);
            Expulsar_Jugador(persona);
            Toast.makeText(context, "Jugador "+persona.getNombre_Persona()+" "+persona.getApellidos_Persona()+" Expulsado", Toast.LENGTH_SHORT).show();
            String d="Expulsión -> "+"Jugador "+persona.getNombre_Persona()+" "+persona.getApellidos_Persona()+" - "+Estadistico_Gestion.TEMP.getMinutos_jugados()+"´"+Estadistico_Gestion.TEMP.getSegundos_jugados()+"´´" ;
            Estadistico_Gestion.LISTA_LINEA_TIEMPO.add(d);

            my_Data.get(position).setOpcion(o);
        }

    }
    private boolean Buscar_Jugador(Persona persona) {
        boolean tiene=false;

       for(int i=0;i<my_Data.size();i++){
           if(my_Data.get(i).getPersona()!=null){
               if(my_Data.get(i).getPersona().getId()==persona.getId()){
                   if(my_Data.get(i).getOpcion()!=null){
                       if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("TA")){
                           tiene=true;
                           System.out.println("ENCONTRO DOBLE AMARILLA");
                       }else{
                           System.out.println("NO ENCONTRO DOBLE AMARILLA");
                           tiene=false;
                       }
                   }
               }
           }

       }

       for(int i=0;i< CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.size();i++){
           if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getPersona()!=null){
               if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getPersona().getId()==persona.getId()){
                   if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion()!=null){
                       if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().equalsIgnoreCase("TA")){
                           tiene=true;
                       }else{
                           tiene=false;
                       }
                   }
               }
           }
       }

       return  tiene;

    }
    private void Expulsar_Jugador(Persona persona) {
         for(int i=0;i<Estadistico_Gestion.LISTA_PERSONAS_TITULARES.size();i++){
             if(persona.getId()==Estadistico_Gestion.LISTA_PERSONAS_TITULARES.get(i).getPersona().getId()){
                 Estadistico_Gestion.LISTA_PERSONAS_TITULARES.get(i).setExpulsado(true);
             }
         }
         Estadistico_Gestion.ADAPTER_TITULARES.notifyDataSetChanged();



        Estadistico_Gestion.TEMP.setNombres_Personas(null);
        List<Persona> TEMP_NOMBRES=new ArrayList<>();
        for(int i=0;i<Estadistico_Gestion.LISTA_PERSONAS_TITULARES.size();i++){
            if(Estadistico_Gestion.LISTA_PERSONAS_TITULARES.get(i).isExpulsado()){

            }else {
                TEMP_NOMBRES.add(Estadistico_Gestion.LISTA_PERSONAS_TITULARES.get(i).getPersona());
            }

        }

        Estadistico_Gestion.TEMP.setNombres_Personas(TEMP_NOMBRES);

    }
    private void Capturar_Tiempo(OpcionEstadistico o, Persona persona) {



        if(o.getOpcion().equalsIgnoreCase("G")){
            String dd="GOL -> "+persona.getApellidos_Persona()+" "+Estadistico_Gestion.TEMP.getMinutos_jugados()+"´"+Estadistico_Gestion.TEMP.getSegundos_jugados()+"´´";
            Estadistico_Gestion.LISTA_LINEA_TIEMPO.add(dd);
        }

        if(o.getOpcion().equalsIgnoreCase("OF")){
            String dd="OFF SIDES -> "+persona.getApellidos_Persona()+" "+Estadistico_Gestion.TEMP.getMinutos_jugados()+"´"+Estadistico_Gestion.TEMP.getSegundos_jugados()+"´´";
            Estadistico_Gestion.LISTA_LINEA_TIEMPO.add(dd);
        }
    }
    private boolean Buscar_GolOponente() {
        boolean t=false;
        System.out.println("---------------------BUSQUEDA OPONENTE GOL----------------");
        for(int i=0;i<Estadistico_Gestion.RADIOS.size();i++){
            if(Estadistico_Gestion.RADIOS.get(i).getEstado()==2){
                if(Estadistico_Gestion.RADIOS.get(i).getTipo()==3){
                    t=true;
                    System.out.println("Gol oponente encontrado!!!!!!!!");
                }else{
                    System.out.println("Gol oponente NO encontrado!!!!!!!!");
                }
            }
        }
        return  t;

    }
    private void Limpiar_Check() {

        for(int i=0;i<Estadistico_Gestion.RADIOS.size();i++){
                Estadistico_Gestion.RADIOS.get(i).setEstado(1);
                Estadistico_Gestion.RADIOS.get(i).getRadioButton().setChecked(false);
        }

    }
    private Persona Encontrar_Persona(Object selectedItem) {
       Persona p=null;
       String persona_sele=String.valueOf(selectedItem);

        for(int i=0;i<Estadistico_Gestion.TEMP.getNombres_Personas().size();i++){
            String per=Estadistico_Gestion.TEMP.getNombres_Personas().get(i).getNombre_Persona()+" "+Estadistico_Gestion.TEMP.getNombres_Personas().get(i).getApellidos_Persona();
            if(per.trim().equalsIgnoreCase(persona_sele.trim())){
                p=Estadistico_Gestion.TEMP.getNombres_Personas().get(i);
            }
        }
       return  p;

    }
    private RecursoEstadisticoRadioButton Encontrar_Opcion() {
        RecursoEstadisticoRadioButton d=null;
        for(int i=0;i<Estadistico_Gestion.RADIOS.size();i++){
            if(Estadistico_Gestion.RADIOS.get(i).getEstado()==2){
                d=Estadistico_Gestion.RADIOS.get(i);
            }
        }

        return  d;
    }
    private void Listar_Nombres_Jugador(Spinner spinner_personas, int position) {
        String [] lista_bases_jugadores=new String[Estadistico_Gestion.TEMP.getNombres_Personas().size()+1];
        lista_bases_jugadores[0]="-- SELECCIONE --";

        for(int i=0;i<Estadistico_Gestion.TEMP.getNombres_Personas().size();i++){
            lista_bases_jugadores[i+1]=String.valueOf(Estadistico_Gestion.TEMP.getNombres_Personas().get(i).getNombre_Persona()+" "+Estadistico_Gestion.TEMP.getNombres_Personas().get(i).getApellidos_Persona()).trim();
        }
        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,lista_bases_jugadores);
        spinner_personas.setAdapter(adapter_arr);

    }
    private boolean Buscar_Seleccion() {
      boolean t=false;

      for(int i=0;i<Estadistico_Gestion.RADIOS.size();i++){

              if(Estadistico_Gestion.RADIOS.get(i).getEstado()==2){
                      t=true;
              }

      }

      return  t;
    }
    private void Evento_click_Radios() {

        for(int i=0;i<Estadistico_Gestion.RADIOS.size();i++){
           Estadistico_Gestion.RADIOS.get(i).getRadioButton().setOnClickListener(this);
        }
    }
    private void Recuperar_Radios(View dialoglayout4) {

        for(int i=0;i<Estadistico_Gestion.RADIOS.size();i++){
            RadioButton ra=dialoglayout4.findViewById(Estadistico_Gestion.RADIOS.get(i).getId());
            Estadistico_Gestion.RADIOS.get(i).setRadioButton(ra);
        }
    }
    @Override
    public int getItemCount() {
        return my_Data.size();
    }
    public int RecuperarTA(Persona persona){
        int contador=0;
        for(int i=0;i<my_Data.size();i++){
            if(my_Data.get(i).getPersona()!=null){
                if(my_Data.get(i).getPersona().getId()==persona.getId()){
                    if(my_Data.get(i).getOpcion()!=null){
                        if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("TA")){
                            contador=contador+1;
                        }
                    }

                }
            }

        }

        return contador;
    }
    public int RecuperarTR(Persona persona){
        int contador=0;
        for(int i=0;i<my_Data.size();i++){
            if(my_Data.get(i).getPersona()!=null){
                if(my_Data.get(i).getPersona().getId()==persona.getId()){
                    if(my_Data.get(i).getOpcion()!=null){
                        if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("TR")){
                            contador=contador+1;
                        }
                    }

                }
            }

        }

        return contador;
    }
    public int RecuperarPG(Persona persona){
        int contador=0;
        for(int i=0;i<my_Data.size();i++){
            if(my_Data.get(i).getPersona()!=null){
                if(my_Data.get(i).getPersona().getId()==persona.getId()){
                    if(my_Data.get(i).getOpcion()!=null){
                        if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("PG")){
                            contador=contador+1;
                        }
                    }

                }
            }

        }

        return contador;
    }
    public int RecuperarDR(Persona persona){
        int contador=0;
        for(int i=0;i<my_Data.size();i++){
            if(my_Data.get(i).getPersona()!=null){
                if(my_Data.get(i).getPersona().getId()==persona.getId()){
                    if(my_Data.get(i).getOpcion()!=null){
                        if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("DR")){
                            contador=contador+1;
                        }
                    }

                }
            }

        }

        return contador;
    }
    public int RecuperarOG(Persona persona){
        int contador=0;
        for(int i=0;i<my_Data.size();i++){
            if(my_Data.get(i).getPersona()!=null){
                if(my_Data.get(i).getPersona().getId()==persona.getId()){
                    if(my_Data.get(i).getOpcion()!=null){
                        if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("OG")){
                            contador=contador+1;
                        }
                    }

                }
            }

        }

        return contador;
    }
    public int RecuperarR(Persona persona){
        int contador=0;
        for(int i=0;i<my_Data.size();i++){
            if(my_Data.get(i).getPersona()!=null){
                if(my_Data.get(i).getPersona().getId()==persona.getId()){
                    if(my_Data.get(i).getOpcion()!=null){
                        if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("R")){
                            contador=contador+1;
                        }
                    }

                }
            }

        }

        return contador;
    }
    public int RecuperarG(Persona persona){
        int contador=0;
        for(int i=0;i<my_Data.size();i++){
            if(my_Data.get(i).getPersona()!=null){
                if(my_Data.get(i).getPersona().getId()==persona.getId()){
                    if(my_Data.get(i).getOpcion()!=null){
                        if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("G")){
                            contador=contador+1;
                        }
                    }

                }
            }

        }

        return contador;
    }
    public int RecuperarOF(Persona persona){
        int contador=0;
        for(int i=0;i<my_Data.size();i++){
            if(my_Data.get(i).getPersona()!=null){
                if(my_Data.get(i).getPersona().getId()==persona.getId()){
                    if(my_Data.get(i).getOpcion()!=null){
                        if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("OF")){
                            contador=contador+1;
                        }
                    }

                }
            }

        }

        return contador;
    }
    public int RecuperarBP(Persona persona){
        int contador=0;
        for(int i=0;i<my_Data.size();i++){
            if(my_Data.get(i).getPersona()!=null){
                if(my_Data.get(i).getPersona().getId()==persona.getId()){
                    if(my_Data.get(i).getOpcion()!=null){
                        if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("BP")){
                            contador=contador+1;
                        }
                    }

                }
            }

        }

        return contador;
    }
    public int RecuperarBR(Persona persona){
        int contador=0;
        for(int i=0;i<my_Data.size();i++){
            if(my_Data.get(i).getPersona()!=null){
                if(my_Data.get(i).getPersona().getId()==persona.getId()){
                    if(my_Data.get(i).getOpcion()!=null){
                        if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("BR")){
                            contador=contador+1;
                        }
                    }

                }
            }

        }

        return contador;
    }
    public int RecuperarF(Persona persona){
        int contador=0;
        for(int i=0;i<my_Data.size();i++){
            if(my_Data.get(i).getPersona()!=null){
                if(my_Data.get(i).getPersona().getId()==persona.getId()){
                    if(my_Data.get(i).getOpcion()!=null){
                        if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("F")){
                            contador=contador+1;
                        }
                    }

                }
            }

        }

        return contador;
    }
    public int RecuperarATJ(Persona persona){
        int contador=0;
        for(int i=0;i<my_Data.size();i++){
            if(my_Data.get(i).getPersona()!=null){
                if(my_Data.get(i).getPersona().getId()==persona.getId()){
                    if(my_Data.get(i).getOpcion()!=null){
                        if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("ATJ")){
                            contador=contador+1;
                        }
                    }

                }
            }

        }

        return contador;
    }
    public int RecuperarGolesLocal(){
        int d=0;
        for(int i=0;i<my_Data.size();i++){
            if(my_Data.get(i).getOpcion()!=null){

                if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("G")){
                    d=d+1;
                }
            }
        }
        return d;
    }
    public int RecuperarGolesOponente(){
        int d=0;
        for(int i=0;i<my_Data.size();i++){
            if(my_Data.get(i).getOpcion()!=null){
                if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("GO")){
                    d=d+1;
                }
            }
        }
        return d;
    }
}
