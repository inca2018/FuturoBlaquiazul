package org.futuroblanquiazul.futuroblaquiazul.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Debug;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

         if(my_Data.get(position).getOpcion()!=null){
             holder.btn.setText(my_Data.get(position).getOpcion().getOpcion());
             holder.btn.setTextColor(context.getResources().getColor(R.color.blanco));
         }
         holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                final View dialoglayout4 = inflater.inflate(R.layout.gestion_campo_estadistico, null);

                final Spinner spinner_personas= dialoglayout4.findViewById(R.id.card_campoesta_personas);
                final Button boton_guardar= dialoglayout4.findViewById(R.id.card_campoesta_boton);

                Recuperar_Radios(dialoglayout4);

                final AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
                builder4.setView(dialoglayout4);
                da=builder4.show();


                Listar_Nombres_Jugador(spinner_personas,position);

                boton_guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            if(spinner_personas.getSelectedItemPosition()==0){
                                Toast.makeText(context,"Seleccione un Jugador",Toast.LENGTH_SHORT).show();
                            }else{
                                 if(Buscar_Seleccion()){
                                     Persona persona=Encontrar_Persona(spinner_personas.getSelectedItem());
                                     if(persona!=null){
                                         my_Data.get(position).setPersona(persona);
                                         System.out.println("Persona Seleccionada:"+ persona.getId());

                                         RecursoEstadisticoRadioButton campo=Encontrar_Opcion();
                                         if(campo!=null){
                                             OpcionEstadistico O=new OpcionEstadistico();
                                             O.setCodigo(campo.getEstado());
                                             O.setOpcion(campo.getDesc());
                                             my_Data.get(position).setOpcion(O);

                                             System.out.println("Opcion Seleccionada CODIGO:"+ O.getCodigo()+" OPCION:"+O.getOpcion());

                                             notifyDataSetChanged();
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
                });


                Evento_click_Radios();

            }
        });
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
                    if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("TA")){
                        contador=contador+1;
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
                    if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("TR")){
                        contador=contador+1;
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
                    if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("PG")){
                        contador=contador+1;
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
                    if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("DR")){
                        contador=contador+1;
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
                    if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("OG")){
                        contador=contador+1;
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
                    if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("R")){
                        contador=contador+1;
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
                    if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("G")){
                        contador=contador+1;
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
                    if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("OF")){
                        contador=contador+1;
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
                    if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("BP")){
                        contador=contador+1;
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
                    if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("BR")){
                        contador=contador+1;
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
                    if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("F")){
                        contador=contador+1;
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
                    if(my_Data.get(i).getOpcion().getOpcion().equalsIgnoreCase("ATJ")){
                        contador=contador+1;
                    }
                }
            }

        }

        return contador;
    }
}
