package org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoPersonaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterBarrio;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterCampo;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterInfoEquipo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Campo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.EventoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PersonaEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Posicion;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PuntosEstadisticos;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonas;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasEstadisticos;
import org.futuroblanquiazul.futuroblaquiazul.R;
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

    private GridLayoutManager grid;
    private LinearLayoutManager linearLayoutManager;
    private AdapterCampo adapterCampo;
    AlertDialog da;
    AdapterInfoEquipo adapterInfoEquipo;

    Button opcion_Defenza_zona,opcion_zona_juego,opcion_informacion,opcion_leyenda;

    List<PersonaEstadistico> List_Persona_INFO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_fecha_estadistico);
        List_Persona_INFO=new ArrayList<>();

        recyclerView=findViewById(R.id.Recycler_Estadistico);
        context=this;
        cronometro=findViewById(R.id.cronometro_general);
        altura2=findViewById(R.id.altura_campo2);
        linear2=findViewById(R.id.linear_campo2);
        opcion_Defenza_zona=findViewById(R.id.opcion_defensa_zona);
        opcion_zona_juego=findViewById(R.id.opcion_zona_juego);
        opcion_informacion=findViewById(R.id.opcion_informacion);
        opcion_leyenda=findViewById(R.id.opcion_leyenda);

        cronometro.setBase( SystemClock.elapsedRealtime() );
        cronometro.start();

        cronometro.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                debug("-"+chronometer.getText().toString());

                if(chronometer.getText().toString().trim().equalsIgnoreCase("00:10")) {
                    cronometro.stop();
                }
            }
        });

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels; // ancho absoluto en pixels
        int height = metrics.heightPixels; // alto absoluto en pixels
        altura=height;
        ancho=width;
        Usuario.SESION_ACTUAL.setAltura(height); //600
        Usuario.SESION_ACTUAL.setAncho(width); //1024
        recyclercampoooo();

        if(Campo.LISTACAMPO.size()==0){
            listar_card();
        }else{
            Campo.LISTACAMPO.clear();
            listar_card();

        }

        Opcion_Leyenda();
        Opcion_Zona_de_Juego();
        Opcion_Informacion();
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

            linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

            adapterInfoEquipo = new AdapterInfoEquipo(context, List_Persona_INFO, new RecyclerViewOnItemClickListener() {
                public void onClick(View v, int position) {

                }
            });

            recyclerView.setAdapter(adapterInfoEquipo);
            recyclerView.setLayoutManager(linearLayoutManager);

            Listar_Persona_Estadistico(context);



        }
    });
    }

    private void Listar_Persona_Estadistico(final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Equipo Estadistico:");
        progressDialog.setMessage("Recuperando Información de Equipo...");
        progressDialog.show();

        // String id_evento=String.valueOf(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getId());
        String id_evento=String.valueOf(4);

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

                            temp.setPuntosPrimerTiempo(pru1);
                            temp.setPuntosSegundoTiempo(pru2);
                            temp.setEstado(1);

                            temp.setNum(i+1);

                            List_Persona_INFO.add(temp);

                        }

                        adapterInfoEquipo.notifyDataSetChanged();
                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DE PERSONAS ESTADISTICOS");
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
        adapterCampo = new AdapterCampo(this,Campo.LISTACAMPO, new RecyclerViewOnItemClickListener(){
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

            for(int i=0;i<408;i++){
                Campo temp=new Campo(i,"",0,alt2,anc2,R.drawable.layout_border);
                Campo.LISTACAMPO.add(temp);
            }

            adapterCampo.notifyDataSetChanged();
        }else if(ancho>800 && ancho<1200){

            int alt=altura;
            int anc=ancho;
            alt2=alt/18;
            anc2=anc/24;
            altura2.getLayoutParams().height=(alt-25);

            System.out.println("PANTALLA GRANDE");

            for(int i=0;i<408;i++){
                Campo temp=new Campo(i,"",0,alt2,anc2,R.drawable.layout_border);
                Campo.LISTACAMPO.add(temp);
            }

            adapterCampo.notifyDataSetChanged();
        }else{
            int alt=altura;
            int anc=ancho;
            alt2=alt/13;
            anc2=anc/24;
            altura2.getLayoutParams().height=(alt-30);
            System.out.println("PANTALLA PEKE");
            for(int i=0;i<312;i++){
                Campo temp=new Campo(i,"",0,alt2+1,anc2,R.drawable.layout_border);
                Campo.LISTACAMPO.add(temp);
            }

            adapterCampo.notifyDataSetChanged();
        }

        System.out.println("Altura BASE:"+(altura-20));
        System.out.println("ANcho BASE:"+(ancho-20));

        System.out.println("Altura Card:"+alt2);
        System.out.println("ANcho Card:"+anc2);


    }

    public void debug(String sm){
        System.out.println(sm);
    }
}
