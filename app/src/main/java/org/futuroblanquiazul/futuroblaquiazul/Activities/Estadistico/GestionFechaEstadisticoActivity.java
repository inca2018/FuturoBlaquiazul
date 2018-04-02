package org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterCampoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterEquipo;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterInfoEquipo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.CampoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.EventoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PersonaEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Posicion;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PuntosEstadisticos;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasEstadisticos;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Estadistico_Gestion;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Estadistico;
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
    AlertDialog da;
    AdapterInfoEquipo adapterInfoEquipo;
    Button opcion_Defenza_zona,opcion_zona_juego,opcion_informacion,opcion_leyenda;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_fecha_estadistico);

        recyclerView=findViewById(R.id.Recycler_Estadistico);
        context=this;
        cronometro=findViewById(R.id.cronometro_general);
        altura2=findViewById(R.id.altura_campo2);
        linear2=findViewById(R.id.linear_campo2);
        opcion_Defenza_zona=findViewById(R.id.opcion_defensa_zona);
        opcion_zona_juego=findViewById(R.id.opcion_zona_juego);
        opcion_informacion=findViewById(R.id.opcion_informacion);
        opcion_leyenda=findViewById(R.id.opcion_leyenda);

         linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
         linearLayoutManager2=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

         adapterInfoEquipo = new AdapterInfoEquipo(context,Estadistico_Gestion.LISTA_PERSONAS_INFO, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
         });

        Listar_Persona_Estadistico(context);
        Gestion_Cronometro();
        Opcion_Leyenda();
        Opcion_Zona_de_Juego();
        Opcion_Informacion();
    }

    private void Generar_Listas_Campos() {
        List<Persona> TEMP_NOMBRES=new ArrayList<>();

        for(int i=0;i<Estadistico_Gestion.LISTA_PERSONAS_INFO.size();i++){
            if(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getTitular()==1){
                TEMP_NOMBRES.add(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
            }else{

            }
        }

        Estadistico_Gestion.TEMP.setNombres_Personas(TEMP_NOMBRES);


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
                           for(int i=0;i<Estadistico_Gestion.LISTA_PERSONAS_INFO.size();i++){
                               int ta=adapterCampo.RecuperarTA(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
                               int tr=adapterCampo.RecuperarTR(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
                               int pg=adapterCampo.RecuperarPG(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
                               int dr=adapterCampo.RecuperarDR(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
                               int r=adapterCampo.RecuperarR(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
                               int  g=adapterCampo.RecuperarG(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
                               int of=adapterCampo.RecuperarOF(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
                               int bp=adapterCampo.RecuperarBP(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
                               int br=adapterCampo.RecuperarBR(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
                               int atj=adapterCampo.RecuperarATJ(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());

                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPrimerTiempo().setTarjetasAmarillas(ta);
                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPrimerTiempo().setTarjetasRojas(tr);
                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPrimerTiempo().setPaseGol(pg);
                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPrimerTiempo().setDribling(dr);
                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPrimerTiempo().setRemate(r);
                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPrimerTiempo().setGoles(g);
                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPrimerTiempo().setOfSide(of);
                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPrimerTiempo().setBalonPerdido(bp);
                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPrimerTiempo().setBalonRecuperado(br);
                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPrimerTiempo().setAtajadas(atj);
                           }

                           adapterInfoEquipo.notifyDataSetChanged();
                       }else if(Estadistico_Gestion.TEMP.getTiempo_actual()==2){
                           for(int i=0;i<Estadistico_Gestion.LISTA_PERSONAS_INFO.size();i++){
                               int ta=adapterCampo.RecuperarTA(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
                               int tr=adapterCampo.RecuperarTR(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
                               int pg=adapterCampo.RecuperarPG(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
                               int dr=adapterCampo.RecuperarDR(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
                               int r=adapterCampo.RecuperarR(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
                               int  g=adapterCampo.RecuperarG(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
                               int of=adapterCampo.RecuperarOF(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
                               int bp=adapterCampo.RecuperarBP(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
                               int br=adapterCampo.RecuperarBR(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());
                               int atj=adapterCampo.RecuperarATJ(Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getPersona());

                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getSegundoTiempo().setTarjetasAmarillas(ta);
                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getSegundoTiempo().setTarjetasRojas(tr);
                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getSegundoTiempo().setPaseGol(pg);
                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getSegundoTiempo().setDribling(dr);
                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getSegundoTiempo().setRemate(r);
                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getSegundoTiempo().setGoles(g);
                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getSegundoTiempo().setOfSide(of);
                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getSegundoTiempo().setBalonPerdido(bp);
                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getSegundoTiempo().setBalonRecuperado(br);
                               Estadistico_Gestion.LISTA_PERSONAS_INFO.get(i).getSegundoTiempo().setAtajadas(atj);

                           }
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
                    String fin=String.valueOf(Estadistico_Gestion.TEMP.getMinutos_x_tiempo());
                    debug("TIEMPO PAUSADO : "+fin+":00");
                    debug("TIEMPO ACTUAL: "+chronometer.getText().toString());

                    if(chronometer.getText().toString().trim().equalsIgnoreCase(fin.trim()+":00")){
                        cronometro.stop();

                        final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                        final View dialoglayout2 = inflater.inflate(R.layout.info_aviso2, null);
                        final android.app.AlertDialog.Builder builder4 = new android.app.AlertDialog.Builder(context);

                        final TextView text=dialoglayout2.findViewById(R.id.texto_alerta);
                        final Button bo=dialoglayout2.findViewById(R.id.boton_continuar_segundo);

                        builder4.setView(dialoglayout2);
                        da=builder4.show();

                        text.setText("Termino el Primer Tiempo");
                        bo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                da.dismiss();
                                Estadistico_Gestion.TEMP.setTiempo_actual(2);
                                CambiarSegundoTiempo();
                            }
                        });

                    }

                }
            });

    }

    private void CambiarSegundoTiempo() {

        cronometro.setBase(SystemClock.elapsedRealtime());
        cronometro.start();

        cronometro.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                String primer_Aviso=String.valueOf(Estadistico_Gestion.TEMP.getPrimer_aviso());
                String segundo_Aviso=String.valueOf(Estadistico_Gestion.TEMP.getSegundo_aviso());

                if(chronometer.getText().toString().trim().equalsIgnoreCase(primer_Aviso.trim()+":00")) {
                    //cronometro.stop();
                    debug("PRIMER AVISO");

                    final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                    final View dialoglayout2 = inflater.inflate(R.layout.info_aviso, null);
                    final android.app.AlertDialog.Builder builder4 = new android.app.AlertDialog.Builder(context);

                    final TextView text=dialoglayout2.findViewById(R.id.texto_alerta);
                    final Button bo=dialoglayout2.findViewById(R.id.boton_ok);

                    builder4.setView(dialoglayout2);
                    da=builder4.show();

                    text.setText("El Tiempo Terminara en 5 minutos");
                    bo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            da.dismiss();
                        }
                    });
                }

                if(chronometer.getText().toString().trim().equalsIgnoreCase(segundo_Aviso.trim()+":00")){
                    //cronometro.stop();
                    debug("PRIMER AVISO");

                    final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                    final View dialoglayout2 = inflater.inflate(R.layout.info_aviso, null);
                    final android.app.AlertDialog.Builder builder4 = new android.app.AlertDialog.Builder(context);

                    final TextView text=dialoglayout2.findViewById(R.id.texto_alerta);
                    final Button bo=dialoglayout2.findViewById(R.id.boton_ok);

                    builder4.setView(dialoglayout2);
                    da=builder4.show();

                    text.setText("El Tiempo Terminara en 1 minuto");
                    bo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            da.dismiss();
                        }
                    });
                }

                String tiempo_x_fase=String.valueOf(Estadistico_Gestion.TEMP.getMinutos_x_tiempo());

                if(chronometer.getText().toString().trim().equalsIgnoreCase(tiempo_x_fase.trim()+":00")){
                    cronometro.stop();
                }

            }
        });
    }

    private void Opcion_Informacion() {
    opcion_informacion.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            final View dialoglayout2 = inflater.inflate(R.layout.opcion_informacion_estadistico, null);
            final android.app.AlertDialog.Builder builder4 = new android.app.AlertDialog.Builder(context);

            RecyclerView recyclerView=dialoglayout2.findViewById(R.id.recycler_info_equipo);
            builder4.setView(dialoglayout2);
            da=builder4.show();


                recyclerView.setAdapter(adapterInfoEquipo);
                LinearLayoutManager xx=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(xx);

                adapterInfoEquipo.notifyDataSetChanged();


        }
    });
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
                            temp.setTitular(objeto.getInt("TITULAR"));
                            Estadistico_Gestion.LISTA_PERSONAS_INFO.add(temp);

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
           final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
           final View dialoglayout2 = inflater.inflate(R.layout.opcion_zona_juego, null);
           final android.app.AlertDialog.Builder builder4 = new android.app.AlertDialog.Builder(context);
           builder4.setView(dialoglayout2);
           da=builder4.show();

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
                Crear_Campo(1);
            }
            if(i>12 && i<=24 || i>36 && i<=48 || i>60 && i<=72){
                Crear_Campo(2);
            }

            if(i>72 && i<=76 || i>96 && i<=100  || i>120 && i<=124 || i>144 && i<=148  || i>168  && i<=172  || i>192  && i<=196   || i>216  && i<=220   || i>240  && i<=244   || i>264  && i<=268  || i> 288 && i<=292  || i>312  && i<= 316){
                Crear_Campo(3);
            }
            if(i>76 && i<=84 || i>100 && i<=108 || i>124 && i<=132  || i>148 && i<= 156  || i>172  && i<=180 || i>196  && i<=204  || i>220 && i<=228  || i>244  && i<=252   || i> 268 && i<=276  || i>292  && i<=300  || i>316  && i<=324 ){
                Crear_Campo(4);
            }

            if(i>84 && i<=92 || i>108 && i<=116 || i> 132 && i<=140 || i>156 && i<= 164  || i>180  && i<=188  || i>204  && i<= 212 || i>228  && i<=236  || i>252  && i<=260  || i> 276 && i<=284  || i> 300 && i<= 308   || i> 324 && i<=332 ){
                Crear_Campo(5);
            }
            if(i>92 && i<=96 || i>116 && i<=120 || i>140  && i<=144  || i>164 && i<= 168  || i>188  && i<=192  || i>212  && i<=216 || i>236  && i<=240 || i>260  && i<=264  || i> 284 && i<=288  || i> 308 && i<=312  || i>332  && i<=336  ){
                Crear_Campo(6);
            }

            if(i>336 && i<=348  || i>360  && i<=372  || i>384  && i<=396  ){
                Crear_Campo(7);
            }

            if(i>348 && i<=360  || i>372  && i<=384 || i>396  && i<=408  ){
                Crear_Campo(8);
            }
          }
        }
    private void Crear_Campo(int estado) {

        CampoEstadistico temp =new CampoEstadistico(estado,null,null,1,alt2,anc2,R.drawable.layout_border,"");
        CampoEstadistico.LISTACAMPOESTADISTICO.add(temp);
    }

    public void debug(String sm){
        System.out.println(sm);
    }
    public void onBackPressed() {
        super.onBackPressed();

        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("Evento Estadistico")
                .setMessage("¿Desea Cancelar Partido?")
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

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
