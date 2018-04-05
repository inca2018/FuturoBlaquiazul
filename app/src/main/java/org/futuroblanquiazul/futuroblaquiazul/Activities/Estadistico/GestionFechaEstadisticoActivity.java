package org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterCampoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterInfoEquipo;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPersonaCambio;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPersonaCambio2;
import org.futuroblanquiazul.futuroblaquiazul.Entity.CampoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.EventoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.FechaEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PersonaEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Posicion;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PuntosEstadisticos;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasEstadisticos;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Estadistico_Gestion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GestionFechaEstadisticoActivity extends AppCompatActivity {
    Chronometer cronometro;
    RecyclerView recyclerView;
    Context context;
    LinearLayout altura2,linear2;
    ProgressDialog progressDialog;
    int altura,ancho,alt2,anc2;
    GridLayoutManager grid;
    LinearLayoutManager linearLayoutManager,linearLayoutManager2;
    AdapterCampoEstadistico adapterCampo;
    AlertDialog da,da2,da3,da4,daPrimer;
    AdapterInfoEquipo adapterInfoEquipo;
    Button opcion_profundidad_zona,opcion_zona_juego,opcion_informacion,opcion_leyenda;
    int valor1=0,valor2=0,valor3=0,valor4=0;
    int tiempo_fin;
    String fin;
    ImageView foto_opo;
    TextView nom_opo;
    TextView num_fecha;
    TextView actual_equipo;

    boolean start;
    boolean pause=false;
    CardView accion_partido;
    Button boton_partido;
    long timeWhenStopped = 0;
    String aviso1,aviso2;
    String tiempo_total;
    Button opcion_cambios;
    Button opcion_grabar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_fecha_estadistico);
        Variables();
        Recuperar_Datos_Mostrar();
        Adapter_Lista_Info();
        Opcion_Leyenda();
        Opcion_Zona_de_Juego();
        Opcion_Informacion();
        Opcion_Inicio();
        Opcion_Cambios();
        Opcion_Grabar();

        Opcion_Profundidad();
    }

    private void Opcion_Profundidad() {

        opcion_profundidad_zona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void Adapter_Lista_Info() {
        linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        linearLayoutManager2=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

        adapterInfoEquipo = new AdapterInfoEquipo(context,Estadistico_Gestion.LISTA_PERSONAS_TODO, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
        });
        Listar_Persona_Estadistico(context);

    }
    private void Recuperar_Datos_Mostrar() {
        if(FechaEstadistico.FECHA_ESTADISTICO_TEMP.getFecha_actual()!=null){
            Glide.with(context)
                    .load( FechaEstadistico.FECHA_ESTADISTICO_TEMP.getFecha_actual().getOponente().getFoto())
                    .error(R.drawable.no_disponible)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(foto_opo);

            nom_opo.setText(FechaEstadistico.FECHA_ESTADISTICO_TEMP.getFecha_actual().getOponente().getNombre_Oponente().toUpperCase());
            num_fecha.setText("FECHA Nº "+FechaEstadistico.FECHA_ESTADISTICO_TEMP.getFecha_actual().getNum());
            actual_equipo.setText(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getEquipo().getNombre_equipo().toUpperCase());

        }
    }
    private void Variables() {
        recyclerView=findViewById(R.id.Recycler_Estadistico);
        context=this;
        cronometro=findViewById(R.id.cronometro_general);
        altura2=findViewById(R.id.altura_campo2);
        linear2=findViewById(R.id.linear_campo2);
        opcion_profundidad_zona=findViewById(R.id.opcion_defensa_zona);
        opcion_zona_juego=findViewById(R.id.opcion_zona_juego);
        opcion_informacion=findViewById(R.id.opcion_informacion);
        opcion_leyenda=findViewById(R.id.opcion_leyenda);
        foto_opo=findViewById(R.id.opo_foto);
        nom_opo=findViewById(R.id.opo_nombre);
        num_fecha=findViewById(R.id.actual_nom_fecha);
        actual_equipo=findViewById(R.id.actual_equipo);
        opcion_cambios=findViewById(R.id.opcion_cambios);
        accion_partido=findViewById(R.id.accion_start);
        boton_partido=findViewById(R.id.accion_button_start);
        opcion_grabar=findViewById(R.id.opcion_grabar);
    }
    private void Opcion_Inicio() {
        boton_partido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (Estadistico_Gestion.TEMP.getEstado_partido()){
                    case 1:
                        Partido_Start();
                        break;
                    case 2:
                        if(pause){
                            Reanudar();
                        }else{
                            Partido_Pause();
                        }
                        break;
                    case 3:
                        Continuar_Segundo_Tiempo();
                        break;
                    case 4:
                        Toast.makeText(context, "Partido Finalizado!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
    private void Opcion_Grabar() {
        opcion_grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Estadistico_Gestion.TEMP.getTiempo_actual()==0){
                    Toast.makeText(context, "Inicie el Partido!", Toast.LENGTH_SHORT).show();

                }else{
                    if(Estadistico_Gestion.TEMP.getEstado_partido()==4){
                        //GUARDA INFORMACION


                    }else{
                        Toast.makeText(context, "Debe Finalizar el Partido!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
    private void Opcion_Cambios() {

        opcion_cambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Estadistico_Gestion.TEMP.getEstado_partido()==4){
                    Toast.makeText(context, "Partido Finalizado!", Toast.LENGTH_SHORT).show();
                }else{
                    if(Estadistico_Gestion.TEMP.getTiempo_actual()==0){
                        Toast.makeText(context, "Inicie Partido!", Toast.LENGTH_SHORT).show();
                    }else{
                        Limpiar_Listas();

                        final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                        final View dialoglayout2 = inflater.inflate(R.layout.opcion_cambios, null);
                        final android.app.AlertDialog.Builder builder4 = new android.app.AlertDialog.Builder(context);

                        RecyclerView recyclerView1=dialoglayout2.findViewById(R.id.lista_titulares);
                        RecyclerView recyclerView2=dialoglayout2.findViewById(R.id.lista_suplentes);
                        ImageView accion_cambio=dialoglayout2.findViewById(R.id.accion_cambio_jugador);
                        builder4.setView(dialoglayout2);


                        recyclerView1.setAdapter(Estadistico_Gestion.ADAPTER_TITULARES);
                        LinearLayoutManager xx=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
                        recyclerView1.setLayoutManager(xx);

                        recyclerView2.setAdapter(Estadistico_Gestion.ADAPTER_SUPLENTES);
                        LinearLayoutManager xx2=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
                        recyclerView2.setLayoutManager(xx2);

                        accion_cambio.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(Estadistico_Gestion.TEMP.getTitular_Cambio()!=null){
                                    if(Estadistico_Gestion.TEMP.getSuplente_Cambio()!=null){

                                        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                                        builder.setTitle("Cambio Jugador")
                                                .setMessage("¿Desea Realizar el Siguiente Cambio? \n \n "+
                                                        "Jugador Titular : \n \n "+
                                                        "- "+Estadistico_Gestion.TEMP.getTitular_Cambio().getPersona().getNombre_Persona()+" "+Estadistico_Gestion.TEMP.getTitular_Cambio().getPersona().getApellidos_Persona()+"\n \n"+
                                                        "Jugador Suplente: \n \n"+
                                                        "- "+Estadistico_Gestion.TEMP.getSuplente_Cambio().getPersona().getNombre_Persona()+" "+Estadistico_Gestion.TEMP.getSuplente_Cambio().getPersona().getApellidos_Persona()+" \n \n")
                                                .setPositiveButton("SI",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {

                                                                Realizar_Cambio_Jugadores();
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

                                    }else{
                                        Toast.makeText(context, "Seleccione Suplente para Cambio", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(context, "Seleccione Titular para Cambio", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        da=builder4.show();



                        Estadistico_Gestion.ADAPTER_TITULARES.notifyDataSetChanged();
                        Estadistico_Gestion.ADAPTER_SUPLENTES.notifyDataSetChanged();
                    }
                }


            }
        });
    }
    private void Realizar_Cambio_Jugadores() {
        PersonaEstadistico titular= Estadistico_Gestion.TEMP.getTitular_Cambio();
        PersonaEstadistico suplente = Estadistico_Gestion.TEMP.getSuplente_Cambio();

        String cambio="Cambio de Jugador -> "+Estadistico_Gestion.TEMP.getMinutos_jugados()+"´ "+Estadistico_Gestion.TEMP.getSegundos_jugados()+"´´ - "+titular.getPersona().getApellidos_Persona()+" Nº "+titular.getCamiseta_persona()+" por "+suplente.getPersona().getApellidos_Persona()+ " Nº "+suplente.getCamiseta_persona();

        Estadistico_Gestion.LISTA_LINEA_TIEMPO.add(cambio);

        for(int i=0;i<Estadistico_Gestion.LISTA_PERSONAS_TITULARES.size();i++){
            if(Estadistico_Gestion.LISTA_PERSONAS_TITULARES.get(i).getPersona().getId()==titular.getPersona().getId()){
                Estadistico_Gestion.LISTA_PERSONAS_TITULARES.remove(i);
            }
        }

        for(int i=0;i<Estadistico_Gestion.LISTA_PERSONAS_SUPLENTES.size();i++){
            if(Estadistico_Gestion.LISTA_PERSONAS_SUPLENTES.get(i).getPersona().getId()==suplente.getPersona().getId()){
                Estadistico_Gestion.LISTA_PERSONAS_SUPLENTES.remove(i);
            }
        }


        Estadistico_Gestion.LISTA_PERSONAS_TITULARES.add(suplente);
        Estadistico_Gestion.LISTA_PERSONAS_SUPLENTES.add(titular);

        Estadistico_Gestion.ADAPTER_SUPLENTES.notifyDataSetChanged();
        Estadistico_Gestion.ADAPTER_TITULARES.notifyDataSetChanged();

        Actualizar_Todo(titular,suplente);
        Actualizar_Lista_Titulares_Campo();

        Toast.makeText(context, "Cambio Realizado con Exito!", Toast.LENGTH_SHORT).show();

        if(da!=null){
            da.dismiss();
        }
    }
    private void Actualizar_Todo(PersonaEstadistico titular, PersonaEstadistico suplente) {

        for(int i=0;i<Estadistico_Gestion.LISTA_PERSONAS_TODO.size();i++){

            if(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona().getId()==titular.getPersona().getId()){
                Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona().setTitular(0);
                Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).setTitular(0);
                Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).setSaliente(1);
            }

            if(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona().getId()==suplente.getPersona().getId()){
                Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona().setTitular(1);
                Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).setTitular(1);
                Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).setEntrante(1);
                Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).setMinuto_Cambio(Estadistico_Gestion.TEMP.getMinutos_jugados());
            }
        }

    }
    private void Actualizar_Lista_Titulares_Campo() {
        Estadistico_Gestion.TEMP.setNombres_Personas(null);
        List<Persona> TEMP_NOMBRES=new ArrayList<>();
        for(int i=0;i<Estadistico_Gestion.LISTA_PERSONAS_TITULARES.size();i++){
            TEMP_NOMBRES.add(Estadistico_Gestion.LISTA_PERSONAS_TITULARES.get(i).getPersona());
        }

        Estadistico_Gestion.TEMP.setNombres_Personas(TEMP_NOMBRES);


    }
    private void Generar_Listas_Campos() {
        List<Persona> TEMP_NOMBRES=new ArrayList<>();

        for(int i = 0; i<Estadistico_Gestion.LISTA_PERSONAS_TODO.size(); i++){
            if(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getTitular()==1){
                Estadistico_Gestion.LISTA_PERSONAS_TITULARES.add(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i));
            }else{
                Estadistico_Gestion.LISTA_PERSONAS_SUPLENTES.add(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i));
            }
        }


        for(int i=0;i<Estadistico_Gestion.LISTA_PERSONAS_TITULARES.size();i++){
            TEMP_NOMBRES.add(Estadistico_Gestion.LISTA_PERSONAS_TITULARES.get(i).getPersona());
        }

        Estadistico_Gestion.TEMP.setNombres_Personas(TEMP_NOMBRES);





        Estadistico_Gestion.ADAPTER_TITULARES = new AdapterPersonaCambio(context,Estadistico_Gestion.LISTA_PERSONAS_TITULARES, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
        });


        Estadistico_Gestion.ADAPTER_SUPLENTES = new AdapterPersonaCambio2(context,Estadistico_Gestion.LISTA_PERSONAS_SUPLENTES, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
        });

    }
    private void Gestion_Campo_Estadistico() {

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels; // ancho absoluto en pixels
        int height = metrics.heightPixels; // alto absoluto en pixels
        altura=height;
        ancho=width;
        Usuario.SESION_ACTUAL.setAltura(height); //600
        Usuario.SESION_ACTUAL.setAncho(width); //1024
        recyclercampoooo();
        if(CampoEstadistico.LISTACAMPOESTADISTICO.size()==0){
            listar_card();
        }else{
            CampoEstadistico.LISTACAMPOESTADISTICO.clear();
            listar_card();
        }

        adapterCampo.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                       if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                           for(int i = 0; i<Estadistico_Gestion.LISTA_PERSONAS_TODO.size(); i++){
                               int ta=adapterCampo.RecuperarTA(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int tr=adapterCampo.RecuperarTR(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int pg=adapterCampo.RecuperarPG(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int dr=adapterCampo.RecuperarDR(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int r=adapterCampo.RecuperarR(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int  g=adapterCampo.RecuperarG(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int of=adapterCampo.RecuperarOF(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int bp=adapterCampo.RecuperarBP(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int br=adapterCampo.RecuperarBR(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int atj=adapterCampo.RecuperarATJ(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int og=adapterCampo.RecuperarOG(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int f=adapterCampo.RecuperarF(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());

                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPrimerTiempo().setTarjetasAmarillas(ta);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPrimerTiempo().setTarjetasRojas(tr);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPrimerTiempo().setPaseGol(pg);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPrimerTiempo().setDribling(dr);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPrimerTiempo().setRemate(r);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPrimerTiempo().setGoles(g);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPrimerTiempo().setOfSide(of);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPrimerTiempo().setBalonPerdido(bp);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPrimerTiempo().setBalonRecuperado(br);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPrimerTiempo().setAtajadas(atj);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPrimerTiempo().setOpcionGol(og);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPrimerTiempo().setFaltas(f);

                           }

                           adapterInfoEquipo.notifyDataSetChanged();

                       }else if(Estadistico_Gestion.TEMP.getTiempo_actual()==2){
                           for(int i = 0; i<Estadistico_Gestion.LISTA_PERSONAS_TODO.size(); i++){
                               int ta=adapterCampo.RecuperarTA(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int tr=adapterCampo.RecuperarTR(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int pg=adapterCampo.RecuperarPG(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int dr=adapterCampo.RecuperarDR(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int r=adapterCampo.RecuperarR(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int  g=adapterCampo.RecuperarG(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int of=adapterCampo.RecuperarOF(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int bp=adapterCampo.RecuperarBP(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int br=adapterCampo.RecuperarBR(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int atj=adapterCampo.RecuperarATJ(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int og=adapterCampo.RecuperarOG(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());
                               int f=adapterCampo.RecuperarF(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getPersona());


                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getSegundoTiempo().setTarjetasAmarillas(ta);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getSegundoTiempo().setTarjetasRojas(tr);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getSegundoTiempo().setPaseGol(pg);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getSegundoTiempo().setDribling(dr);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getSegundoTiempo().setRemate(r);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getSegundoTiempo().setGoles(g);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getSegundoTiempo().setOfSide(of);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getSegundoTiempo().setBalonPerdido(bp);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getSegundoTiempo().setBalonRecuperado(br);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getSegundoTiempo().setAtajadas(atj);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getSegundoTiempo().setOpcionGol(og);
                               Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getSegundoTiempo().setFaltas(f);



                           }
                           adapterInfoEquipo.notifyDataSetChanged();
                       }


            }
        });
    }
    private void Gestion_Cronometro() {

            Estadistico_Gestion.TEMP.setTiempo_actual(1);

            cronometro.setBase(SystemClock.elapsedRealtime());
            cronometro.start();
            cronometro.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer chronometer) {

                    tiempo_fin=Estadistico_Gestion.TEMP.getBloque_tiempo();
                    if(chronometer.getText().toString().length()==4){
                        fin=String.valueOf(tiempo_fin);
                    }else{
                        if(tiempo_fin<10){
                            fin="0"+tiempo_fin;
                        }else{
                            fin=String.valueOf(tiempo_fin);
                        }
                    }


                    long tiempoActual=SystemClock.elapsedRealtime()-cronometro.getBase();
                    int hours = (int) (tiempoActual / 3600000);
                    int minutes = (int) (tiempoActual - hours * 3600000) / 60000;
                    int seconds = (int) (tiempoActual - hours * 3600000 - minutes * 60000) / 1000;

                    Estadistico_Gestion.TEMP.setMinutos_jugados(minutes);
                    Estadistico_Gestion.TEMP.setSegundos_jugados(seconds);
                    Estadistico_Gestion.TEMP.setTiempo_Jugado(minutes);

                    Actualizar_Tiempos_Partido();

                    debug("----------------------------------------------");

                    debug("TIEMPO ACTUAL: "+Estadistico_Gestion.TEMP.getTiempo_actual());
                    debug("TIEMPO JUGADO  : "+Estadistico_Gestion.TEMP.getTiempo_Jugado());
                    debug(" TIEMPO TOTAL : "+Estadistico_Gestion.TEMP.getTiempo_Total());
                    debug(" BLOQUE TIEMPO : "+Estadistico_Gestion.TEMP.getBloque_tiempo());
                    debug(" PRIMER TIEMPO FIN : -"+fin+"-");
                    debug(" Cronometro Tiempo : -"+chronometer.getText().toString().trim()+"-");

                    //Primer Tiempo Finalizado
                    if(chronometer.getText().toString().trim().equalsIgnoreCase(fin.trim()+":00")){
                        debug("ENTRO CIERRE DE PRIMER TIEMPO:");
                        cronometro.stop();
                        Mostrar_Dialog_Finalizado_Primer_Tiempo();
                    }


                    //RECUPERAR TIEMPO DE AVISOS
                    String primer_Aviso=String.valueOf(Estadistico_Gestion.TEMP.getPrimer_aviso());

                    if(chronometer.getText().toString().length()==4){
                         aviso1=primer_Aviso;
                    }else{
                        if(Estadistico_Gestion.TEMP.getPrimer_aviso()<10){
                            aviso1="0"+primer_Aviso;
                            debug("PRIMER AVISO IGUAL:"+aviso1);
                        }else{
                            aviso1=primer_Aviso;
                            debug("PRIMER AVISO IGUAL:"+aviso1);
                        }
                    }


                    String segundo_Aviso=String.valueOf(Estadistico_Gestion.TEMP.getTiempo_Total()-1);
                    Estadistico_Gestion.TEMP.setSegundo_aviso(Estadistico_Gestion.TEMP.getTiempo_Total()-1);

                    if(chronometer.getText().toString().length()==4){
                        aviso2=segundo_Aviso;
                    }else{
                        if(Estadistico_Gestion.TEMP.getSegundo_aviso()<10){
                            aviso2="0"+segundo_Aviso;
                            debug("SEGUNDO AVISO IGUAL:"+aviso2);
                        }else{
                            aviso2=segundo_Aviso;
                            debug("SEGUNDO AVISO IGUAL:"+aviso2);
                        }
                    }



                      //PRIMER AVISO
                    if(chronometer.getText().toString().trim().equalsIgnoreCase(aviso1.trim()+":00")) {
                        debug("PRIMER AVISO");
                       Mostrar_Primer_Aviso();
                    }
                    //SEGUNDO AVISO
                    if(chronometer.getText().toString().trim().equalsIgnoreCase(aviso2.trim()+":00")){
                        debug("SEGUNDO AVISO");
                        Mostrar_Segundo_Aviso();
                    }

                    //TIEMPO FINALIZADO
                    int tt=Estadistico_Gestion.TEMP.getTiempo_Total();
                    if(chronometer.getText().toString().length()==4){
                        tiempo_total=String.valueOf(tt);
                    }else{
                        if(tt<10){
                            tiempo_total="0"+tt;
                        }else{
                            tiempo_total=String.valueOf(tt);
                        }
                    }


                    if(chronometer.getText().toString().trim().equalsIgnoreCase(tiempo_total.trim()+":00")){
                        cronometro.stop();
                        if(da2!=null){
                            da2.dismiss();
                        }

                        if(da3!=null){
                            da3.dismiss();
                        }

                        final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                        final View dialoglayout2 = inflater.inflate(R.layout.info_aviso, null);
                        final android.app.AlertDialog.Builder builder4 = new android.app.AlertDialog.Builder(context);

                        final TextView text=dialoglayout2.findViewById(R.id.texto_alerta);
                        final Button bo=dialoglayout2.findViewById(R.id.boton_ok);
                        final LinearLayout linear=dialoglayout2.findViewById(R.id.linear_adicional);

                        builder4.setView(dialoglayout2);
                        da4=builder4.show();

                        linear.setVisibility(View.GONE);

                        text.setText("Termino el Partido");
                        bo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Finalizar_Partido();
                                da4.dismiss();
                            }
                        });
                    }


                }
            });
    }
    private void Actualizar_Tiempos_Partido() {

        for(int i = 0; i<Estadistico_Gestion.LISTA_PERSONAS_TODO.size(); i++){

            if(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getTitular()==1){
                if(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getSaliente()==1){
                }else{
                    Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).setTiempo_jugado(Estadistico_Gestion.TEMP.getTiempo_Jugado());

                    if(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getEntrante()==1){
                        int d=Estadistico_Gestion.TEMP.getTiempo_Jugado()-Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getMinuto_Cambio();                    Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).setTiempo_jugado(d);
                    }

                }

            }

        }

        adapterInfoEquipo.notifyDataSetChanged();
        debug("ENTRO A ACTUALIZAR TIEMPOS DE PARTIDO");
    }
    private void Mostrar_Segundo_Aviso() {
        debug("ABRIR SEGUNDO AVISO");
        if(da2!=null){
            da2.dismiss();
        }
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final View dialoglayout2 = inflater.inflate(R.layout.info_aviso, null);
        final android.app.AlertDialog.Builder builder4 = new android.app.AlertDialog.Builder(context);

        final TextView text=dialoglayout2.findViewById(R.id.texto_alerta);
        final Button bo=dialoglayout2.findViewById(R.id.boton_ok);
        final TextView text_extra=dialoglayout2.findViewById(R.id.tiempo_Extra);
        final LinearLayout linear=dialoglayout2.findViewById(R.id.linear_adicional);

        builder4.setView(dialoglayout2);
        builder4.setCancelable(false);
        da3=builder4.show();

        if(Estadistico_Gestion.TEMP.getTiempo_Adicional()!=0){
            linear.setVisibility(View.GONE);
        }else{
            linear.setVisibility(View.VISIBLE);
        }

        text.setText("El Tiempo Terminara en 1 minuto");

        bo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tiempo_adicional=Integer.parseInt(text_extra.getText().toString());
                Estadistico_Gestion.TEMP.setTiempo_Adicional(tiempo_adicional);

                int total_nuevo=Estadistico_Gestion.TEMP.getTiempo_Total()+tiempo_adicional;
                debug(" Total_Nuevo : "+total_nuevo);
                Estadistico_Gestion.TEMP.setTiempo_Total(total_nuevo);

                debug("CERRAR SEGUNDO AVISO");
                da3.dismiss();
            }
        });

    }
    private void Mostrar_Primer_Aviso() {
        debug("ABRIR PRIMER AVISO");

        final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final View dialoglayout2 = inflater.inflate(R.layout.info_aviso, null);
        final android.app.AlertDialog.Builder builder4 = new android.app.AlertDialog.Builder(context);

        final TextView text=dialoglayout2.findViewById(R.id.texto_alerta);
        final TextView text_extra=dialoglayout2.findViewById(R.id.tiempo_Extra);
        final Button bo=dialoglayout2.findViewById(R.id.boton_ok);
        final LinearLayout linear=dialoglayout2.findViewById(R.id.linear_adicional);

        builder4.setView(dialoglayout2);
        builder4.setCancelable(false);
        da2=builder4.show();

        text.setText("El Tiempo Terminara en 3 minutos");

        bo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  tiempo_Adicional=Integer.parseInt(text_extra.getText().toString());
                Estadistico_Gestion.TEMP.setTiempo_Adicional(tiempo_Adicional);

                int total_nuevo=Estadistico_Gestion.TEMP.getTiempo_Total()+tiempo_Adicional;
                debug("TOTAL NUEVO PRIMER: "+total_nuevo);

                Estadistico_Gestion.TEMP.setTiempo_Total(total_nuevo);


                debug("CERRAR PRIMER AVISO");
                da2.dismiss();
            }
        });
    }
    private void Mostrar_Dialog_Finalizado_Primer_Tiempo() {

        final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final View dialoglayout = inflater.inflate(R.layout.info_aviso, null);
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

        final TextView text=dialoglayout.findViewById(R.id.texto_alerta);
        final TextView text_extra=dialoglayout.findViewById(R.id.tiempo_Extra);
        final Button bo=dialoglayout.findViewById(R.id.boton_ok);
        final LinearLayout linear=dialoglayout.findViewById(R.id.linear_adicional);

        builder.setView(dialoglayout);
        builder.setCancelable(false);
        daPrimer=builder.show();
        linear.setVisibility(View.GONE);
        text.setText("Termino el Primer Tiempo del Partido");
        bo.setText("OK");

        bo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Estadistico_Gestion.TEMP.setMinutos_x_tiempo(0);
                Partido_Stop();
                daPrimer.dismiss();
            }
        });
    }
    private void Opcion_Informacion() {
    opcion_informacion.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(Estadistico_Gestion.TEMP.getTiempo_actual()==0){
                Toast.makeText(context, "Inicia Partido!", Toast.LENGTH_SHORT).show();
            }else{



                final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                final View dialoglayout2 = inflater.inflate(R.layout.opcion_informacion_estadistico, null);
                final android.app.AlertDialog.Builder builder4 = new android.app.AlertDialog.Builder(context);

                RecyclerView recyclerView=dialoglayout2.findViewById(R.id.recycler_info_equipo);

                builder4.setView(dialoglayout2);
                da=builder4.show();


                recyclerView.setAdapter(adapterInfoEquipo);
                LinearLayoutManager xx=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(xx);

                Calcular_Puntaje();

                adapterInfoEquipo.notifyDataSetChanged();
            }
        }
    });

    }
    private void Limpiar_Listas() {

        for(int i=0;i<Estadistico_Gestion.LISTA_PERSONAS_TITULARES.size();i++){
            Estadistico_Gestion.LISTA_PERSONAS_TITULARES.get(i).setActivo(false);
        }
        for(int i=0;i<Estadistico_Gestion.LISTA_PERSONAS_SUPLENTES.size();i++){
            Estadistico_Gestion.LISTA_PERSONAS_SUPLENTES.get(i).setActivo(false);
        }

        Estadistico_Gestion.TEMP.setTitular_Cambio(null);
        Estadistico_Gestion.TEMP.setSuplente_Cambio(null);
    }
    private void Calcular_Puntaje() {
        for(int i = 0; i<Estadistico_Gestion.LISTA_PERSONAS_TODO.size(); i++){
            Calcular_Total(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i));
        }

    }
    private void Calcular_Total(PersonaEstadistico personaEstadistico) {

        if(personaEstadistico.getTiempo_jugado()==0){
            personaEstadistico.setTotal_Puntos(0);
        }else{
            int total;

            int goles=personaEstadistico.getPrimerTiempo().getGoles()+personaEstadistico.getSegundoTiempo().getGoles();
            int atajada=personaEstadistico.getPrimerTiempo().getAtajadas()+personaEstadistico.getSegundoTiempo().getAtajadas();
            int balonR=personaEstadistico.getPrimerTiempo().getBalonRecuperado()+personaEstadistico.getSegundoTiempo().getBalonRecuperado();
            int remate=personaEstadistico.getPrimerTiempo().getRemate()+personaEstadistico.getSegundoTiempo().getRemate();
            int Dribbling=personaEstadistico.getPrimerTiempo().getDribling()+personaEstadistico.getSegundoTiempo().getDribling();
            int PaseGol=personaEstadistico.getPrimerTiempo().getPaseGol()+personaEstadistico.getSegundoTiempo().getPaseGol();
            int ogol=personaEstadistico.getPrimerTiempo().getOpcionGol()+personaEstadistico.getSegundoTiempo().getOpcionGol();

            int prom1=(goles+atajada+balonR+remate+Dribbling+PaseGol+ogol)/7;

            int OfSide=personaEstadistico.getPrimerTiempo().getOfSide()+personaEstadistico.getSegundoTiempo().getOfSide();
            int BalonP=personaEstadistico.getPrimerTiempo().getBalonPerdido()+personaEstadistico.getSegundoTiempo().getBalonPerdido();
            int Falta=personaEstadistico.getPrimerTiempo().getFaltas()+ personaEstadistico.getSegundoTiempo().getFaltas();
            int Tamarilla=personaEstadistico.getPrimerTiempo().getTarjetasAmarillas()+personaEstadistico.getSegundoTiempo().getTarjetasAmarillas();
            int Troja=personaEstadistico.getPrimerTiempo().getTarjetasRojas()+personaEstadistico.getSegundoTiempo().getTarjetasRojas();

            int prom2=(OfSide+BalonP+Falta+Tamarilla+Troja)/5;

            total=5+((prom1)-(prom2))+goles;
            personaEstadistico.setTotal_Puntos(total);
        }

    }
    private void Listar_Persona_Estadistico(final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Equipo Estadistico:");
        progressDialog.setMessage("Recuperando Información de Equipo...");
        progressDialog.show();

         String id_evento=String.valueOf(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getId());
        //String id_evento=String.valueOf(4);

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray xx=jsonResponse.getJSONArray("personas_estadistico");
                        for(int i=0;i<xx.length();i++){
                            JSONObject objeto= xx.getJSONObject(i);
                            PersonaEstadistico temp=new PersonaEstadistico();

                            Persona p=new Persona();
                            p.setId(objeto.getInt("ID_PERSONAS"));
                            p.setNombre_Persona(objeto.getString("NOMBRES"));
                            p.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            p.setFoto(objeto.getString("FOTO"));
                            p.setTitular(objeto.getInt("TITULAR"));

                            p.setActivo(false);

                            Posicion pos=new Posicion();
                            pos.setId(objeto.getInt("ID_POSICION"));
                            pos.setNombre_Posicione(objeto.getString("NOMBRE_POSICION"));
                            temp.setCamiseta_persona(objeto.getInt("NUM_CAMISETA"));
                            temp.setPersona(p);
                            temp.setPosicion_persona(pos);

                            PuntosEstadisticos pru1=new PuntosEstadisticos();
                            PuntosEstadisticos pru2=new PuntosEstadisticos();

                            pru1.setAtajadas(0);
                            pru1.setBalonPerdido(0);
                            pru1.setBalonRecuperado(0);
                            pru1.setDribling(0);
                            pru1.setFaltas(0);
                            pru1.setGoles(0);
                            pru1.setOfSide(0);
                            pru1.setOpcionGol(0);
                            pru1.setPuntajes(0);
                            pru1.setTarjetasAmarillas(0);
                            pru1.setTarjetasRojas(0);
                            pru1.setOfSide(0);
                            pru1.setPaseGol(0);
                            pru1.setRemate(0);

                            pru2.setAtajadas(0);
                            pru2.setBalonPerdido(0);
                            pru2.setBalonRecuperado(0);
                            pru2.setDribling(0);
                            pru2.setFaltas(0);
                            pru2.setGoles(0);
                            pru2.setOfSide(0);
                            pru2.setOpcionGol(0);
                            pru2.setPuntajes(0);
                            pru2.setTarjetasAmarillas(0);
                            pru2.setTarjetasRojas(0);
                            pru2.setOfSide(0);
                            pru2.setPaseGol(0);
                            pru2.setRemate(0);


                            temp.setPrimerTiempo(pru1);
                            temp.setSegundoTiempo(pru2);
                            temp.setEstado(1);

                            temp.setNum(i+1);
                            temp.setEntrante(0);
                            temp.setSaliente(0);


                            temp.setTitular(objeto.getInt("TITULAR"));
                            Estadistico_Gestion.LISTA_PERSONAS_TODO.add(temp);

                        }

                        //adapterInfoEquipo.notifyDataSetChanged();
                        progressDialog.dismiss();
                        System.out.println("LISTADO COMPLETO DE PERSONAS ESTADISTICOS");
                        Generar_Listas_Campos();
                        Gestion_Campo_Estadistico();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Listado Vacio de Personas Estadisticos", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar personas Estadisticos :"+e);
                }
            }
        };
        RecuperarPersonasEstadisticos xx = new RecuperarPersonasEstadisticos(id_evento,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    private void Opcion_Zona_de_Juego() {

   opcion_zona_juego.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {

           if(Estadistico_Gestion.TEMP.getEstado_partido()==4){
               Toast.makeText(context, "Partido Finalizado", Toast.LENGTH_SHORT).show();
           }else{
               if(Estadistico_Gestion.TEMP.getTiempo_actual()==0){
                   Toast.makeText(context, "Iniciar Partido!", Toast.LENGTH_SHORT).show();
               }else{

                   final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                   final View dialoglayout2 = inflater.inflate(R.layout.opcion_zona_juego, null);
                   final android.app.AlertDialog.Builder builder4 = new android.app.AlertDialog.Builder(context);
                   ImageView opcion1_menos,opcion2_menos,opcion3_menos,opcion4_menos;
                   ImageView opcion1_mas,opcion2_mas,opcion3_mas,opcion4_mas;
                   final TextView opcion1_valor,opcion2_valor,opcion3_valor,opcion4_valor;
                   builder4.setView(dialoglayout2);
                   da=builder4.show();

                   opcion1_menos=dialoglayout2.findViewById(R.id.opcion1_menos);
                   opcion2_menos=dialoglayout2.findViewById(R.id.opcion2_menos);
                   opcion3_menos=dialoglayout2.findViewById(R.id.opcion3_menos);
                   opcion4_menos=dialoglayout2.findViewById(R.id.opcion4_menos);

                   opcion1_mas=dialoglayout2.findViewById(R.id.opcion1_mas);
                   opcion2_mas=dialoglayout2.findViewById(R.id.opcion2_mas);
                   opcion3_mas=dialoglayout2.findViewById(R.id.opcion3_mas);
                   opcion4_mas=dialoglayout2.findViewById(R.id.opcion4_mas);

                   opcion1_valor=dialoglayout2.findViewById(R.id.opcion1_valor);
                   opcion2_valor=dialoglayout2.findViewById(R.id.opcion2_valor);
                   opcion3_valor=dialoglayout2.findViewById(R.id.opcion3_valor);
                   opcion4_valor=dialoglayout2.findViewById(R.id.opcion4_valor);

                   if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                       opcion1_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getZPG1()));
                       opcion2_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getZF1()));
                       opcion3_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getZR1()));
                       opcion4_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getZPG12()));

                   }else if(Estadistico_Gestion.TEMP.getTiempo_actual()==2){
                       opcion1_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getZPG2()));
                       opcion2_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getZF2()));
                       opcion3_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getZR2()));
                       opcion4_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getZPG22()));
                   }


                   opcion1_menos.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           if(valor1==0){
                           }else{
                               valor1=valor1-1;
                               opcion1_valor.setText(String.valueOf(valor1));

                               if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                   Estadistico_Gestion.TEMP.setZPG1(valor1);
                               }else{
                                   Estadistico_Gestion.TEMP.setZPG2(valor1);
                               }
                           }
                       }
                   });

                   opcion1_mas.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           valor1=valor1+1;
                           opcion1_valor.setText(String.valueOf(valor1));

                           if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                               Estadistico_Gestion.TEMP.setZPG1(valor1);
                           }else{
                               Estadistico_Gestion.TEMP.setZPG2(valor1);
                           }
                       }
                   });


                   opcion2_menos.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           if(valor2==0){
                           }else{
                               valor2=valor2-1;
                               opcion2_valor.setText(String.valueOf(valor2));

                               if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                   Estadistico_Gestion.TEMP.setZF1(valor2);
                               }else{
                                   Estadistico_Gestion.TEMP.setZF2(valor2);
                               }
                           }
                       }
                   });
                   opcion2_mas.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           valor2=valor2+1;
                           opcion2_valor.setText(String.valueOf(valor2));

                           if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                               Estadistico_Gestion.TEMP.setZF1(valor2);
                           }else{
                               Estadistico_Gestion.TEMP.setZF2(valor2);
                           }
                       }
                   });


                   opcion3_menos.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           if(valor3==0){
                           }else{
                               valor3=valor3-1;
                               opcion3_valor.setText(String.valueOf(valor3));

                               if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                   Estadistico_Gestion.TEMP.setZR1(valor3);
                               }else{
                                   Estadistico_Gestion.TEMP.setZR2(valor3);
                               }
                           }
                       }
                   });
                   opcion3_mas.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           valor3=valor3+1;
                           opcion3_valor.setText(String.valueOf(valor3));

                           if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                               Estadistico_Gestion.TEMP.setZR1(valor3);
                           }else{
                               Estadistico_Gestion.TEMP.setZR2(valor3);
                           }
                       }
                   });


                   opcion4_menos.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           if(valor4==0){
                           }else{
                               valor4=valor4-1;
                               opcion4_valor.setText(String.valueOf(valor4));

                               if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                   Estadistico_Gestion.TEMP.setZPG12(valor4);
                               }else{
                                   Estadistico_Gestion.TEMP.setZPG22(valor4);
                               }
                           }
                       }
                   });
                   opcion4_mas.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           valor4=valor4+1;
                           opcion4_valor.setText(String.valueOf(valor4));

                           if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                               Estadistico_Gestion.TEMP.setZPG12(valor4);
                           }else{
                               Estadistico_Gestion.TEMP.setZPG22(valor4);
                           }
                       }
                   });


               }  //+
           }



       }
   });

    }
    private void Opcion_Leyenda() {

    opcion_leyenda.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            final View dialoglayout2 = inflater.inflate(R.layout.opcion_leyenda, null);
            final android.app.AlertDialog.Builder builder4 = new android.app.AlertDialog.Builder(context);
            builder4.setView(dialoglayout2);
            da=builder4.show();



        }
    });
    }
    private void recyclercampoooo() {

        grid = new GridLayoutManager(this,24);
        adapterCampo = new AdapterCampoEstadistico(this,CampoEstadistico.LISTACAMPOESTADISTICO, new RecyclerViewOnItemClickListener(){
            @Override
            public void onClick(View v, int position) {
            }
        });

        recyclerView.setAdapter(adapterCampo);
        recyclerView.setLayoutManager(grid);
    }
    private void listar_card() {

        if(ancho>1500){
            int alt=altura;
            int anc=ancho;
            alt2=alt/18;
            anc2=anc/24;
            altura2.getLayoutParams().height=(alt-65);
            System.out.println("PANTALLA XX GRANDE");
            Agregado_Campos();
            adapterCampo.notifyDataSetChanged();
        }else if(ancho>800 && ancho<1200){

            int alt=altura;
            int anc=ancho;
            alt2=alt/18;
            anc2=anc/24;
            altura2.getLayoutParams().height=(alt-25);
            System.out.println("PANTALLA GRANDE");
            Agregado_Campos();
            adapterCampo.notifyDataSetChanged();
        }else{
            int alt=altura;
            int anc=ancho;
            alt2=alt/13;
            anc2=anc/24;
            altura2.getLayoutParams().height=(alt-30);
            System.out.println("PANTALLA PEKE");
            Agregado_Campos();
            adapterCampo.notifyDataSetChanged();
        }
        System.out.println("Altura BASE:"+(altura-20));
        System.out.println("ANcho BASE:"+(ancho-20));
        System.out.println("Altura Card:"+alt2);
        System.out.println("ANcho Card:"+anc2);
    }
    private void Agregado_Campos() {

        for(int i=1;i<=408;i++){
            if(i<=12 ||  i>24 && i<=36 || i>48 && i<=60){


                if(i<=4  || i>24 && i<=28 || i>48 && i<=52  ){
                    Crear_Campo(1,1);
                }
                if(i>4 && i<=8  || i>28 && i<=32 || i>52 && i<=56   ){
                    Crear_Campo(2,1);
                }
                if(i>8 && i<=12  || i>32 && i<=36 || i>56 && i<=60  ){
                    Crear_Campo(3,1);
                }
            }


            if(i>12 && i<=24 || i>36 && i<=48 || i>60 && i<=72){

                if(i>12 && i<=16 || i>36 && i<=40 || i>60 && i<=64 ){
                    Crear_Campo(4,2);
                }
                if(i>16 && i<=20 || i>40 && i<=44 || i>64 && i<=68   ){
                    Crear_Campo(5,2);
                }
                if(i>20 && i<=24 || i>44 && i<=48 || i>68 && i<=72   ){
                    Crear_Campo(6,2);
                }

            }


            if(i>72 && i<=76 || i>96 && i<=100  || i>120 && i<=124 || i>144 && i<=148  || i>168  && i<=172  || i>192  && i<=196   || i>216  && i<=220   || i>240  && i<=244   || i>264  && i<=268  || i> 288 && i<=292  || i>312  && i<= 316){


                if(i>72 && i<=76 || i>96 && i<=100 || i>120 && i<=124){
                    Crear_Campo(7,3);
                }
                //---------------------------------
                if(i>144 && i<=148 || i>168 && i<=172 || i>192 && i<=196  || i>216 && i<=220 || i>240 && i<=244){
                    Crear_Campo(13,3);
                }


                //---------------------------------
                if(i>264 && i<=268 || i>288 && i<=292 || i>312 && i<=316){
                    Crear_Campo(19,3);
                }


            }
            if(i>76 && i<=84 || i>100 && i<=108 || i>124 && i<=132  || i>148 && i<= 156  || i>172  && i<=180 || i>196  && i<=204  || i>220 && i<=228  || i>244  && i<=252   || i> 268 && i<=276  || i>292  && i<=300  || i>316  && i<=324 ){

                     //Crear_Campo(0,4);
                if(i>76 && i<=80 || i>100 && i<=104 || i>124 && i<=128){
                    Crear_Campo(8,4);
                }

                if(i>80 && i<=84 || i>104 && i<=108 || i>128 && i<=132){
                    Crear_Campo(9,4);
                }

                //---------------------------------
                if(i>148 && i<=152 || i>172 && i<=176 || i>196 && i<=200 || i>220 && i<=224  || i>244 && i<=248){
                    Crear_Campo(14,4);
                }

                if(i>152 && i<=156 || i>176 && i<=180 || i>200 && i<=204 || i>224 && i<=228 || i>248 && i<=252){
                    Crear_Campo(15,4);
                }



                //---------------------------------
                if(i>268 && i<=272   || i>292 && i<=296 || i>316 && i<=320){
                    Crear_Campo(20,4);
                }

                if(i>272 && i<=276  || i>296 && i<=300 || i>320 && i<=324){
                    Crear_Campo(21,4);
                }




            }

            if(i>84 && i<=92 || i>108 && i<=116 || i> 132 && i<=140 || i>156 && i<= 164  || i>180  && i<=188  || i>204  && i<= 212 || i>228  && i<=236  || i>252  && i<=260  || i> 276 && i<=284  || i> 300 && i<= 308   || i> 324 && i<=332 ){
                //Crear_Campo(5);

                if(i>84 && i<=88 || i>108 && i<=112 || i>132 && i<=136){
                    Crear_Campo(10,5);
                }

                if(i>88 && i<=92 || i>112 && i<=116 || i>136 && i<=140){
                    Crear_Campo(11,5);
                }


                //---------------------------------
                if(i>156 && i<=160 || i>180 && i<=184 || i>204 && i<=208 || i>228 && i<=232 || i>252 && i<=256){
                    Crear_Campo(16,5);
                }

                if(i>160 && i<=164 || i>184 && i<=188 || i>208 && i<=212 || i>232 && i<=236 || i>256 && i<=260){
                    Crear_Campo(17,5);
                }



                //---------------------------------
                if(i>276 && i<=280  || i>300 && i<=304 || i>324 && i<=328){
                    Crear_Campo(22,5);
                }

                if(i>280 && i<=284 || i>304 && i<=308 || i>328 && i<=332){
                    Crear_Campo(23,5);
                }

            }
            if(i>92 && i<=96 || i>116 && i<=120 || i>140  && i<=144  || i>164 && i<= 168  || i>188  && i<=192  || i>212  && i<=216 || i>236  && i<=240 || i>260  && i<=264  || i> 284 && i<=288  || i> 308 && i<=312  || i>332  && i<=336  ){
                //Crear_Campo(6);

                if(i>92 && i<=96 || i>116 && i<=120 || i>140 && i<=144){
                    Crear_Campo(12,6);
                }
                //---------------------------------
                if(i>164 && i<=168 || i>188 && i<=192 || i>212 && i<=216 || i>236 && i<=240 || i>260 && i<=264){
                    Crear_Campo(18,6);
                }
                //---------------------------------
                if(i>284 && i<=288 || i>308 && i<=312  || i>332 && i<=336){
                    Crear_Campo(24,6);
                }
            }

            if(i>336 && i<=348  || i>360  && i<=372  || i>384  && i<=396  ){
                //Crear_Campo(7);

                if(i>336 && i<=340 || i>360 && i<=364  || i>384 && i<=388){
                    Crear_Campo(25,7);
                }

                if(i>340 && i<=344 || i>364 && i<=368 || i>388 && i<=392){
                    Crear_Campo(26,7);
                }

                if(i>344 && i<=348 || i>368 && i<=372 || i>392 && i<=396){
                    Crear_Campo(27,7);
                }

            }

            if(i>348 && i<=360  || i>372  && i<=384 || i>396  && i<=408  ){
                if(i>348 && i<=352 || i>372 && i<=376 || i>396 && i<=400){
                    Crear_Campo(28,8);
                }

                if(i>352 && i<=356 || i>376 && i<=380 || i>400 && i<=404){
                    Crear_Campo(29,8);
                }

                if(i>356 && i<=360 || i>380 && i<=384 || i>404 && i<=408){
                    Crear_Campo(30,8);
                }

            }
          }
        }
    private void Crear_Campo(int segmento,int area) {

        CampoEstadistico temp =new CampoEstadistico(area,segmento,null,null,1,alt2,anc2,R.drawable.layout_border, "");
        CampoEstadistico.LISTACAMPOESTADISTICO.add(temp);
    }
    public void debug(String sm){
        System.out.println(sm);
    }
    public void Partido_Start(){
        accion_partido.setCardBackgroundColor(getResources().getColor(R.color.deep_naranja400));
        boton_partido.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_pause, 0, 0, 0);
        boton_partido.setText("PAUSE");
        Estadistico_Gestion.TEMP.setEstado_partido(2);
        start=true;
        Gestion_Cronometro();
    }
    public void Partido_Pause(){
        debug("PAUSAR");
        accion_partido.setCardBackgroundColor(getResources().getColor(R.color.deep_naranja400));
        boton_partido.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_stop, 0, 0, 0);
        boton_partido.setText("REANUDAR");

        timeWhenStopped = cronometro.getBase() - SystemClock.elapsedRealtime();
        cronometro.stop();
        pause=true;
    }
    private void Reanudar() {
        debug("REANUDAR");
        accion_partido.setCardBackgroundColor(getResources().getColor(R.color.deep_naranja400));
        boton_partido.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_pause, 0, 0, 0);
        boton_partido.setText("PAUSE");

        cronometro.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        cronometro.start();
        timeWhenStopped = 0;
        pause=false;
    }
    public void Partido_Stop(){
        accion_partido.setCardBackgroundColor(getResources().getColor(R.color.red));
        boton_partido.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_stop, 0, 0, 0);
        boton_partido.setText("STOP");
        Estadistico_Gestion.TEMP.setEstado_partido(3);
        timeWhenStopped = cronometro.getBase() - SystemClock.elapsedRealtime();
        cronometro.stop();
    }
    private void Continuar_Segundo_Tiempo() {
        Estadistico_Gestion.TEMP.setTiempo_actual(2);
        Estadistico_Gestion.TEMP.setBloque_tiempo(0);
        Estadistico_Gestion.TEMP.setEstado_partido(2);

        accion_partido.setCardBackgroundColor(getResources().getColor(R.color.deep_naranja400));
        boton_partido.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_pause, 0, 0, 0);
        boton_partido.setText("PAUSE");

        cronometro.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        cronometro.start();
        timeWhenStopped = 0;

    }
    private void Finalizar_Partido() {
        accion_partido.setCardBackgroundColor(getResources().getColor(R.color.red));
        boton_partido.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_stop, 0, 0, 0);
        Estadistico_Gestion.TEMP.setEstado_partido(4);

    }
    public void onBackPressed() {

        if(Estadistico_Gestion.TEMP.getEstado_partido()==4){
            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
            builder.setTitle("Estadistico")
                    .setMessage("¿Desea Cancelar Partido? \n \n "+"Perdera los datos Registrados actualmente")
                    .setPositiveButton("SI",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Estadistico_Gestion.LISTA_PERSONAS_TODO.clear();
                                    Intent intent=new Intent(context,ListaFechasEstadisticosActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    context.startActivity(intent);
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
        }else{
            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
            builder.setTitle("Estadistico")
                    .setMessage("¿Desea Cancelar Partido?")
                    .setPositiveButton("SI",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Estadistico_Gestion.LISTA_PERSONAS_TODO.clear();
                                    Intent intent=new Intent(context,ListaFechasEstadisticosActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    context.startActivity(intent);
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

    }
}
