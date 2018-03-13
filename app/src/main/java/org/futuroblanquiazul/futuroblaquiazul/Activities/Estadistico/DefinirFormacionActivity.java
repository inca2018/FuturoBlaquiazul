package org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPlantelEdicionFormacion;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPlantelEdicionFormacion2;
import org.futuroblanquiazul.futuroblaquiazul.Entity.EventoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasPlantel2;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Estadistico_Base;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Estadistico;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DefinirFormacionActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout base7,base8,base9,competetitiva;
    Spinner spinner_base;
    String[] lista_bases_nombres;
    Context context;
    RecyclerView recyclerView;
    TextView equipo;

    private LinearLayoutManager linearLayout,linearLayout2;
    private AdapterPlantelEdicionFormacion adapter;
    private AdapterPlantelEdicionFormacion2 adapter2;
    List<Persona> Lista_Jugadores;
    List<Persona> Lista_JugadoresEdicion;
    ProgressDialog progressDialog;
    AlertDialog da;

    public static final DefinirFormacionActivity DEFINIR=new DefinirFormacionActivity();

    RecyclerView recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definir_formacion);
        context=this;
        spinner_base=findViewById(R.id.spinner_formacion);
        base7=findViewById(R.id.area_base7);
        base8=findViewById(R.id.area_base8);
        base9=findViewById(R.id.area_base9);
        competetitiva=findViewById(R.id.area_campetetiva);
        recyclerView=findViewById(R.id.recycler_Equipo_Edicion_Formacion);
        equipo=findViewById(R.id.id_equipo_edicion_formacion);
        Lista_Jugadores=new ArrayList<>();
        Lista_JugadoresEdicion=new ArrayList<>();

        linearLayout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        linearLayout2= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapter = new AdapterPlantelEdicionFormacion(this, Lista_Jugadores, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
        });
        adapter2 = new AdapterPlantelEdicionFormacion2(this, Lista_JugadoresEdicion, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayout);





        if(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal()!=null){
            equipo.setText(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getPlantel().getNombre_categoria());
            Listar_Personas_Plantel(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getPlantel().getId(), context);
        }else{
            equipo.setText("No Disponible");
        }


       armar_Spinner();

       Gestion_Recursos();

       Activar_Funcionalidad();

       Iniciar_datos();


    }

    private void Listar_Personas_Plantel(int id, final Context context) {

        String id_plantel=String.valueOf(id);


        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Estadistico:");
        progressDialog.setMessage("Listando Jugadores...");
        progressDialog.show();

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("personas2");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            temp.setFoto(objeto.getString("FOTO"));
                            temp.setEstado_edicion(0);
                            Lista_Jugadores.add(temp);
                        }

                        adapter.notifyDataSetChanged();

                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DE JUGADORES");
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Listado Vacio", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarPersonasPlantel2 xx = new RecuperarPersonasPlantel2(id_plantel,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    private void Iniciar_datos() {
        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_7.size();i++){
            Recursos_Estadistico.LISTA_BASE_7.get(i).getImageView().setImageResource(R.drawable.user_default);
        }
        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_8.size();i++){
            Recursos_Estadistico.LISTA_BASE_8.get(i).getImageView().setImageResource(R.drawable.user_default);
        }

        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_9.size();i++){
            Recursos_Estadistico.LISTA_BASE_9.get(i).getImageView().setImageResource(R.drawable.user_default);
        }
        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.size();i++){
            Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getImageView().setImageResource(R.drawable.user_default);
        }
    }

    private void Activar_Funcionalidad() {

        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_7.size();i++){
            Recursos_Estadistico.LISTA_BASE_7.get(i).getArea().setOnClickListener(this);
        }
        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_8.size();i++){
            Recursos_Estadistico.LISTA_BASE_8.get(i).getArea().setOnClickListener(this);
        }

        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_9.size();i++){
            Recursos_Estadistico.LISTA_BASE_9.get(i).getArea().setOnClickListener(this);
        }
        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.size();i++){
            Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getArea().setOnClickListener(this);
        }
    }
    private void Gestion_Recursos() {

        for(int i=0;i< Recursos_Estadistico.LISTA_BASE_7.size();i++){
            ImageView ima=findViewById(Recursos_Estadistico.LISTA_BASE_7.get(i).getRecurso_foto());
            Recursos_Estadistico.LISTA_BASE_7.get(i).setImageView(ima);

            LinearLayout lin=findViewById(Recursos_Estadistico.LISTA_BASE_7.get(i).getRecurso_area());
            Recursos_Estadistico.LISTA_BASE_7.get(i).setArea(lin);

            TextView text=findViewById(Recursos_Estadistico.LISTA_BASE_7.get(i).getRecurso_texto());
            Recursos_Estadistico.LISTA_BASE_7.get(i).setNombre(text);

        }

        for(int i=0;i< Recursos_Estadistico.LISTA_BASE_8.size();i++){
            ImageView ima=findViewById(Recursos_Estadistico.LISTA_BASE_8.get(i).getRecurso_foto());
            Recursos_Estadistico.LISTA_BASE_8.get(i).setImageView(ima);

            LinearLayout lin=findViewById(Recursos_Estadistico.LISTA_BASE_8.get(i).getRecurso_area());
            Recursos_Estadistico.LISTA_BASE_8.get(i).setArea(lin);

            TextView text=findViewById(Recursos_Estadistico.LISTA_BASE_8.get(i).getRecurso_texto());
            Recursos_Estadistico.LISTA_BASE_8.get(i).setNombre(text);
        }



        for(int i=0;i< Recursos_Estadistico.LISTA_BASE_9.size();i++){
            ImageView ima=findViewById(Recursos_Estadistico.LISTA_BASE_9.get(i).getRecurso_foto());
            Recursos_Estadistico.LISTA_BASE_9.get(i).setImageView(ima);

            LinearLayout lin=findViewById(Recursos_Estadistico.LISTA_BASE_9.get(i).getRecurso_area());
            Recursos_Estadistico.LISTA_BASE_9.get(i).setArea(lin);

            TextView text=findViewById(Recursos_Estadistico.LISTA_BASE_9.get(i).getRecurso_texto());
            Recursos_Estadistico.LISTA_BASE_9.get(i).setNombre(text);
        }


        for(int i=0;i< Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.size();i++){
            ImageView ima=findViewById(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getRecurso_foto());
            Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).setImageView(ima);
            LinearLayout lin=findViewById(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getRecurso_area());
            Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).setArea(lin);
            TextView text=findViewById(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getRecurso_texto());
            Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).setNombre(text);

        }
    }
    private void armar_Spinner() {

        lista_bases_nombres=new String[5];
        lista_bases_nombres[0]="-- Seleccione Base --";
        lista_bases_nombres[1]=" Base 7 Jugadores";
        lista_bases_nombres[2]=" Base 8 Jugadores";
        lista_bases_nombres[3]=" Base 9 Jugadores";
        lista_bases_nombres[4]=" Base Competetiva(11 Jugadores)";

        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,lista_bases_nombres);
        spinner_base.setAdapter(adapter_arr);

        spinner_base.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 switch (position){
                     case 0:
                       base7.setVisibility(View.GONE);
                       base8.setVisibility(View.GONE);
                       base9.setVisibility(View.GONE);
                       competetitiva.setVisibility(View.GONE);

                         break;
                     case 1:
                         base7.setVisibility(View.VISIBLE);
                         base8.setVisibility(View.GONE);
                         base9.setVisibility(View.GONE);
                         competetitiva.setVisibility(View.GONE);
                         break;
                     case 2:
                         base7.setVisibility(View.GONE);
                         base8.setVisibility(View.VISIBLE);
                         base9.setVisibility(View.GONE);
                         competetitiva.setVisibility(View.GONE);
                         break;
                     case 3:
                         base7.setVisibility(View.GONE);
                         base8.setVisibility(View.GONE);
                         base9.setVisibility(View.VISIBLE);
                         competetitiva.setVisibility(View.GONE);
                         break;
                     case 4:
                         base7.setVisibility(View.GONE);
                         base8.setVisibility(View.GONE);
                         base9.setVisibility(View.GONE);
                         competetitiva.setVisibility(View.VISIBLE);
                         break;
                 }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void debug(String d){
        System.out.println(d);
    }
    @Override
    public void onClick(View v) {

        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_7.size();i++){
            if(Recursos_Estadistico.LISTA_BASE_7.get(i).getRecurso_area()==v.getId()){
                Mostrar_Dialog_Seleccion(Recursos_Estadistico.LISTA_BASE_7,Recursos_Estadistico.LISTA_BASE_7.get(i), context);
                Recursos_Estadistico.RECURSO.setCodigo_base(0);
            }
        }

        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_8.size();i++){
            if(Recursos_Estadistico.LISTA_BASE_8.get(i).getRecurso_area()==v.getId()){
                Mostrar_Dialog_Seleccion(Recursos_Estadistico.LISTA_BASE_8, Recursos_Estadistico.LISTA_BASE_8.get(i), context);
                Recursos_Estadistico.RECURSO.setCodigo_base(1);
            }
        }

        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_9.size();i++){
            if(Recursos_Estadistico.LISTA_BASE_9.get(i).getRecurso_area()==v.getId()){
                Mostrar_Dialog_Seleccion(Recursos_Estadistico.LISTA_BASE_9, Recursos_Estadistico.LISTA_BASE_9.get(i), context);
                Recursos_Estadistico.RECURSO.setCodigo_base(2);
            }
        }
        for(int i=0;i<Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.size();i++){
            if(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getRecurso_area()==v.getId()){
                Mostrar_Dialog_Seleccion(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA, Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i),context);
                Recursos_Estadistico.RECURSO.setCodigo_base(3);
            }
        }

    }

    private void Mostrar_Dialog_Seleccion(List<Estadistico_Base> listaBase7, Estadistico_Base BASE, final Context context) {

        Recursos_Estadistico.RECURSO.setCodigo_campo(BASE.getId());

        final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final View dialoglayout = inflater.inflate(R.layout.area_seleccion_persona, null);

        ImageView foto;
        TextView jugador;
        LinearLayout liena;
        recyclerView2=dialoglayout.findViewById(R.id.recycler_edicion_cambio);
        foto=dialoglayout.findViewById(R.id.info_imagen_cambio);
        jugador=dialoglayout.findViewById(R.id.info_jugador_cambio);
        liena=dialoglayout.findViewById(R.id.linear_ocultar);

        final AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
        builder4.setView(dialoglayout);
        da=builder4.show();



        if(BASE.getEstado()==1){



            if(linearLayout2!=null){
                linearLayout2= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            }

            recyclerView2.setAdapter(adapter2);
            recyclerView2.setLayoutManager(linearLayout2);

            if(Lista_JugadoresEdicion.size()!=0){
                Lista_JugadoresEdicion.clear();
                Listar_Personas_Edicion(listaBase7,BASE,context);
            }else{
                Listar_Personas_Edicion(listaBase7, BASE, context);
            }



        }else if(BASE.getEstado()==2){
            liena.setVisibility(View.VISIBLE);
            Glide.with(context).load(BASE.getPersona().getFoto()).into(foto);
            jugador.setText(BASE.getPersona().getNombre_Persona()+" "+BASE.getPersona().getApellidos_Persona());

            if(linearLayout2!=null){
                linearLayout2= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            }



            recyclerView2.setAdapter(adapter2);
            recyclerView2.setLayoutManager(linearLayout2);

            if(Lista_JugadoresEdicion.size()!=0){
                Lista_JugadoresEdicion.clear();
                Listar_Personas_Edicion(listaBase7,BASE,context);
            }else{
                Listar_Personas_Edicion(listaBase7, BASE, context);
            }


        }



        Recursos_Estadistico.RECURSO.setDialog(da);
    }

    private void Listar_Personas_Edicion(final List<Estadistico_Base> listaBase7, final Estadistico_Base BASE, final Context context) {
        if(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal()!=null){
            String id_plantel=String.valueOf(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getPlantel().getId());


            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Estadistico:");
            progressDialog.setMessage("Listando Jugadores...");
            progressDialog.show();

            com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            JSONArray departamentos=jsonResponse.getJSONArray("personas2");
                            for(int i=0;i<departamentos.length();i++){
                                JSONObject objeto= departamentos.getJSONObject(i);
                                Persona temp=new Persona();
                                temp.setId(objeto.getInt("ID"));
                                temp.setNombre_Persona(objeto.getString("NOMBRES"));
                                temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                                temp.setFoto(objeto.getString("FOTO"));
                                temp.setEstado_edicion(0);

                                if(BASE.getPersona()!=null){
                                        if(BASE.getPersona().getId()!=temp.getId()){
                                            for(int x=0;x<listaBase7.size();x++){
                                                if(listaBase7.get(x).getPersona()!=null){
                                                    if(listaBase7.get(x).getPersona().getId()!=temp.getId()){
                                                        Lista_JugadoresEdicion.add(temp);
                                                    }
                                                }

                                            }
                                        }
                                }else{
                                    Lista_JugadoresEdicion.add(temp);
                                }
                            }

                            adapter2.notifyDataSetChanged();

                            progressDialog.dismiss();

                            System.out.println("LISTADO COMPLETO DE JUGADORES");
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Listado Vacio", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                    }
                }
            };

            RecuperarPersonasPlantel2 xx = new RecuperarPersonasPlantel2(id_plantel,responseListener);
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(xx);
        }else{
            Toast.makeText(context, "No se encontro Codigo del Plantel", Toast.LENGTH_SHORT).show();
        }

    }

    public void Actualizar_Campo(Persona p, Context context2){

        switch (Recursos_Estadistico.RECURSO.getCodigo_base()){
            case 0:

                for(int i=0;i<Recursos_Estadistico.LISTA_BASE_7.size();i++){
                    if(Recursos_Estadistico.LISTA_BASE_7.get(i).getId()==Recursos_Estadistico.RECURSO.getCodigo_campo()){
                        Recursos_Estadistico.LISTA_BASE_7.get(i).getNombre().setText(p.getNombre_Persona()+" "+p.getApellidos_Persona());
                        Glide.with(context2).load(p.getFoto()).into( Recursos_Estadistico.LISTA_BASE_7.get(i).getImageView());
                        Recursos_Estadistico.LISTA_BASE_7.get(i).setEstado(2);
                        Recursos_Estadistico.LISTA_BASE_7.get(i).setPersona(p);
                    }
                }
                break;
            case 1:
                for(int i=0;i<Recursos_Estadistico.LISTA_BASE_8.size();i++){
                    if(Recursos_Estadistico.LISTA_BASE_8.get(i).getId()==Recursos_Estadistico.RECURSO.getCodigo_campo()){
                        Recursos_Estadistico.LISTA_BASE_8.get(i).getNombre().setText(p.getNombre_Persona()+" "+p.getApellidos_Persona());
                        Glide.with(context2).load(p.getFoto()).into( Recursos_Estadistico.LISTA_BASE_8.get(i).getImageView());
                        Recursos_Estadistico.LISTA_BASE_8.get(i).setEstado(2);
                        Recursos_Estadistico.LISTA_BASE_8.get(i).setPersona(p);
                    }
                }
                break;
            case 2:
                for(int i=0;i<Recursos_Estadistico.LISTA_BASE_9.size();i++){
                    if(Recursos_Estadistico.LISTA_BASE_9.get(i).getId()==Recursos_Estadistico.RECURSO.getCodigo_campo()){
                        Recursos_Estadistico.LISTA_BASE_9.get(i).getNombre().setText(p.getNombre_Persona()+" "+p.getApellidos_Persona());
                        Glide.with(context2).load(p.getFoto()).into( Recursos_Estadistico.LISTA_BASE_9.get(i).getImageView());
                        Recursos_Estadistico.LISTA_BASE_9.get(i).setEstado(2);
                        Recursos_Estadistico.LISTA_BASE_9.get(i).setPersona(p);
                    }
                }
                break;
            case 3:
                for(int i=0;i<Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.size();i++){
                    if(Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getId()==Recursos_Estadistico.RECURSO.getCodigo_campo()){
                        Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getNombre().setText(p.getNombre_Persona()+" "+p.getApellidos_Persona());
                        Glide.with(context2).load(p.getFoto()).into( Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).getImageView());
                        Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).setEstado(2);
                        Recursos_Estadistico.LISTA_BASE_COMPETETITIVA.get(i).setPersona(p);
                    }
                }
                break;
        }

    }

}
