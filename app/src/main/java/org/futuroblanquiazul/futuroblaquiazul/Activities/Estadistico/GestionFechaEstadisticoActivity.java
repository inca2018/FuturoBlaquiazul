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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterCampoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterInfoEquipo;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPersonaCambio;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterPersonaCambio2;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Campo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.CampoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.EventoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.FechaEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PersonaEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Posicion;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PuntosEstadisticos;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActivarPersona;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.EliminarSeguimiento;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPersonasEstadisticos;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Registrar_info_accion_campo;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Registrar_info_general;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Registrar_info_jugadores;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Registrar_info_linea_tiempo;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Registrar_info_medidas;
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
    AlertDialog da,da2,da3,da4,da5,daPrimer,daprofundidad;
    AdapterInfoEquipo adapterInfoEquipo;
    Button opcion_profundidad_zona,opcion_zona_juego,opcion_informacion,opcion_leyenda;
    int valor1=0,valor2=0,valor3=0,valor4=0;
    int primer_tiempo_fin;
    String fin_primer_tiempo;
    ImageView foto_opo;
    TextView nom_opo;
    TextView num_fecha;
    TextView actual_equipo;
    boolean start;
    boolean pause=false;
    CardView accion_partido;
    Button boton_partido;
    long timeWhenStopped = 0;
    String aviso1,aviso2,aviso3,aviso4;
    String tiempo_total;
    Button opcion_cambios;
    Button opcion_grabar;
    TextView adicional_tiempo;
    TextView goles_local;
    TextView goles_oponentes;
    boolean validador=false;
    int prof1=0,prof2=0,prof3=0,prof4=0,prof5=0,prof6=0,prof7=0,prof8=0;

    int id_gestion;

    EditText pos1,pos2;
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

        pos1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                   if(s.toString().length()!=0){
                       Estadistico_Gestion.TEMP.setPosesion1(Integer.parseInt(s.toString()));
                   }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pos2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()!=0){
                    Estadistico_Gestion.TEMP.setPosesion2(Integer.parseInt(s.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
    private void Opcion_Profundidad() {

        opcion_profundidad_zona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Estadistico_Gestion.TEMP.getEstado_partido()==4){
                    Toast.makeText(context, "Partido Finalizado!", Toast.LENGTH_SHORT).show();
                }else{
                    if(Estadistico_Gestion.TEMP.getTiempo_actual()==0){
                        Toast.makeText(context, "Inicie Partido!", Toast.LENGTH_SHORT).show();
                    }else{

                        final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                        final View dialoglayout2 = inflater.inflate(R.layout.opcion_zona_profundidad, null);
                        final android.app.AlertDialog.Builder builder4 = new android.app.AlertDialog.Builder(context);

                        ImageView op1_menos,op1_mas,op2_menos,op2_mas,op3_menos,op3_mas,op4_menos,op4_mas,op5_menos,op5_mas,op6_menos,op6_mas,op7_menos,op7_mas,op8_menos,op8_mas;
                        final TextView op1_valor,op2_valor,op3_valor,op4_valor,op5_valor,op6_valor,op7_valor,op8_valor;
                        //variables
                        builder4.setView(dialoglayout2);
                        daprofundidad=builder4.show();

                        op1_menos=dialoglayout2.findViewById(R.id.op1_menos);
                        op1_menos.setEnabled(false);
                        op1_mas=dialoglayout2.findViewById(R.id.op1_mas);
                        op1_mas.setEnabled(false);
                        op1_valor=dialoglayout2.findViewById(R.id.op1_valor);

                        op2_menos=dialoglayout2.findViewById(R.id.op2_menos);
                        op2_menos.setEnabled(false);
                        op2_mas=dialoglayout2.findViewById(R.id.op2_mas);
                        op2_mas.setEnabled(false);
                        op2_valor=dialoglayout2.findViewById(R.id.op2_valor);

                        op3_menos=dialoglayout2.findViewById(R.id.op3_menos);
                        op3_menos.setEnabled(false);
                        op3_mas=dialoglayout2.findViewById(R.id.op3_mas);
                        op3_mas.setEnabled(false);
                        op3_valor=dialoglayout2.findViewById(R.id.op3_valor);

                        op4_menos=dialoglayout2.findViewById(R.id.op4_menos);
                        op4_menos.setEnabled(false);
                        op4_mas=dialoglayout2.findViewById(R.id.op4_mas);
                        op4_mas.setEnabled(false);
                        op4_valor=dialoglayout2.findViewById(R.id.op4_valor);

                        op5_menos=dialoglayout2.findViewById(R.id.op5_menos);
                        op5_menos.setEnabled(false);
                        op5_mas=dialoglayout2.findViewById(R.id.op5_mas);
                        op5_mas.setEnabled(false);
                        op5_valor=dialoglayout2.findViewById(R.id.op5_valor);


                        op6_menos=dialoglayout2.findViewById(R.id.op6_menos);
                        op6_menos.setEnabled(false);
                        op6_mas=dialoglayout2.findViewById(R.id.op6_mas);
                        op6_mas.setEnabled(false);
                        op6_valor=dialoglayout2.findViewById(R.id.op6_valor);


                        op7_menos=dialoglayout2.findViewById(R.id.op7_menos);
                        op7_menos.setEnabled(false);
                        op7_mas=dialoglayout2.findViewById(R.id.op7_mas);
                        op7_mas.setEnabled(false);
                        op7_valor=dialoglayout2.findViewById(R.id.op7_valor);

                        op8_menos=dialoglayout2.findViewById(R.id.op8_menos);
                        op8_menos.setEnabled(false);
                        op8_mas=dialoglayout2.findViewById(R.id.op8_mas);
                        op8_mas.setEnabled(false);
                        op8_valor=dialoglayout2.findViewById(R.id.op8_valor);


                        if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                            BUSCAR_PROFUNDIDAD_PRIMER_TIEMPO();
                            op1_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getOF_IZQ_DR()));
                            op2_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getOF_IZQ_PG()));
                            op3_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getOF_CEN_OG()));
                            op4_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getOF_CEN_R()));
                            op5_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getOF_ZF_OG()));
                            op6_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getOF_ZF_R()));
                            op7_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getOF_DER_DR()));
                            op8_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getOF_DER_PG()));


                        }else if(Estadistico_Gestion.TEMP.getTiempo_actual()==2){
                            BUSCAR_PROFUNDIDAD_SEGUNDO_TIEMPO();
                            op1_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getOF_IZQ_DR2()));
                            op2_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getOF_IZQ_PG2()));
                            op3_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getOF_CEN_OG2()));
                            op4_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getOF_CEN_R2()));
                            op5_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getOF_ZF_OG2()));
                            op6_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getOF_ZF_R2()));
                            op7_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getOF_DER_DR2()));
                            op8_valor.setText(String.valueOf(Estadistico_Gestion.TEMP.getOF_DER_PG2()));
                        }


                        op1_menos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(prof1==0){
                                }else{
                                    prof1=prof1-1;
                                    op1_valor.setText(String.valueOf(prof1));

                                    if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                        Estadistico_Gestion.TEMP.setOF_IZQ_DR(prof1);
                                    }else{
                                        Estadistico_Gestion.TEMP.setOF_IZQ_DR2(prof1);
                                    }
                                }
                            }
                        });

                        op1_mas.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                prof1=prof1+1;
                                op1_valor.setText(String.valueOf(prof1));

                                if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                    Estadistico_Gestion.TEMP.setOF_IZQ_DR(prof1);
                                }else{
                                    Estadistico_Gestion.TEMP.setOF_IZQ_DR2(prof1);
                                }
                            }
                        });


                        op2_menos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(prof2==0){
                                }else{
                                    prof2=prof2-1;
                                    op2_valor.setText(String.valueOf(prof2));

                                    if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                        Estadistico_Gestion.TEMP.setOF_IZQ_PG(prof2);
                                    }else{
                                        Estadistico_Gestion.TEMP.setOF_IZQ_PG2(prof2);
                                    }
                                }
                            }
                        });

                        op2_mas.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                prof2=prof2+1;
                                op2_valor.setText(String.valueOf(prof2));

                                if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                    Estadistico_Gestion.TEMP.setOF_IZQ_PG(prof2);
                                }else{
                                    Estadistico_Gestion.TEMP.setOF_IZQ_PG2(prof2);
                                }
                            }
                        });


                        op3_menos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(prof3==0){
                                }else{
                                    prof3=prof3-1;
                                    op3_valor.setText(String.valueOf(prof3));

                                    if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                        Estadistico_Gestion.TEMP.setOF_CEN_OG(prof3);
                                    }else{
                                        Estadistico_Gestion.TEMP.setOF_CEN_OG2(prof3);
                                    }
                                }
                            }
                        });

                        op3_mas.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                prof3=prof3+1;
                                op3_valor.setText(String.valueOf(prof3));

                                if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                    Estadistico_Gestion.TEMP.setOF_CEN_OG(prof3);
                                }else{
                                    Estadistico_Gestion.TEMP.setOF_CEN_OG2(prof3);
                                }
                            }
                        });


                        op4_menos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(prof4==0){
                                }else{
                                    prof4=prof4-1;
                                    op4_valor.setText(String.valueOf(prof4));

                                    if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                        Estadistico_Gestion.TEMP.setOF_CEN_R(prof4);
                                    }else{
                                        Estadistico_Gestion.TEMP.setOF_CEN_R2(prof4);
                                    }
                                }
                            }
                        });

                        op4_mas.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                prof4=prof4+1;
                                op4_valor.setText(String.valueOf(prof4));

                                if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                    Estadistico_Gestion.TEMP.setOF_CEN_R(prof4);
                                }else{
                                    Estadistico_Gestion.TEMP.setOF_CEN_R2(prof4);
                                }
                            }
                        });


                        op5_menos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(prof5==0){
                                }else{
                                    prof5=prof5-1;
                                    op5_valor.setText(String.valueOf(prof5));

                                    if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                        Estadistico_Gestion.TEMP.setOF_ZF_OG(prof5);
                                    }else{
                                        Estadistico_Gestion.TEMP.setOF_ZF_OG2(prof5);
                                    }
                                }
                            }
                        });

                        op5_mas.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                prof5=prof5+1;
                                op5_valor.setText(String.valueOf(prof5));

                                if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                    Estadistico_Gestion.TEMP.setOF_ZF_OG(prof5);
                                }else{
                                    Estadistico_Gestion.TEMP.setOF_ZF_OG2(prof5);
                                }
                            }
                        });


                        op6_menos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(prof6==0){
                                }else{
                                    prof6=prof6-1;
                                    op6_valor.setText(String.valueOf(prof6));

                                    if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                        Estadistico_Gestion.TEMP.setOF_ZF_R(prof6);
                                    }else{
                                        Estadistico_Gestion.TEMP.setOF_ZF_R2(prof6);
                                    }
                                }
                            }
                        });

                        op6_mas.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                prof6=prof6+1;
                                op6_valor.setText(String.valueOf(prof6));

                                if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                    Estadistico_Gestion.TEMP.setOF_ZF_R(prof6);
                                }else{
                                    Estadistico_Gestion.TEMP.setOF_ZF_R2(prof6);
                                }
                            }
                        });


                        op7_menos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(prof7==0){
                                }else{
                                    prof7=prof7-1;
                                    op7_valor.setText(String.valueOf(prof7));

                                    if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                        Estadistico_Gestion.TEMP.setOF_DER_DR(prof7);
                                    }else{
                                        Estadistico_Gestion.TEMP.setOF_DER_DR2(prof7);
                                    }
                                }
                            }
                        });

                        op7_mas.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                prof7=prof7+1;
                                op7_valor.setText(String.valueOf(prof7));

                                if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                    Estadistico_Gestion.TEMP.setOF_DER_DR(prof7);
                                }else{
                                    Estadistico_Gestion.TEMP.setOF_DER_DR2(prof7);
                                }
                            }
                        });


                        op8_menos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(prof8==0){
                                }else{
                                    prof8=prof8-1;
                                    op8_valor.setText(String.valueOf(prof8));

                                    if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                        Estadistico_Gestion.TEMP.setOF_DER_PG(prof8);
                                    }else{
                                        Estadistico_Gestion.TEMP.setOF_DER_PG2(prof8);
                                    }
                                }
                            }
                        });

                        op8_mas.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                prof8=prof8+1;
                                op8_valor.setText(String.valueOf(prof8));

                                if(Estadistico_Gestion.TEMP.getTiempo_actual()==1){
                                    Estadistico_Gestion.TEMP.setOF_DER_PG(prof8);
                                }else{
                                    Estadistico_Gestion.TEMP.setOF_DER_PG2(prof8);
                                }
                            }
                        });



                    }

                }

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
        goles_local=findViewById(R.id.goles_local);
        goles_oponentes=findViewById(R.id.goles_oponentes);

        adicional_tiempo=findViewById(R.id.adicional_tiempo);

        pos1=findViewById(R.id.esta_posesion1);
        pos2=findViewById(R.id.esta_posesion2);
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
                        valor1=0;
                        valor2=0;
                        valor3=0;
                        valor4=0;
                        prof1=0;
                        prof2=0;
                        prof3=0;
                        prof4=0;
                        prof5=0;
                        prof6=0;
                        prof7=0;
                        prof8=0;
                        adicional_tiempo.setText("");

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
                        if(Estadistico_Gestion.TEMP.getPosesion1()!=0){
                            if(Estadistico_Gestion.TEMP.getPosesion2()!=0){
                                Recuperar_Informacion_General();
                                Guardar_Informacion_General(context);
                            }else{
                                Toast.makeText(context, "Complete Posesión de Segundo Tiempo", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(context, "Complete Posesión de Primer Tiempo", Toast.LENGTH_SHORT).show();
                        }


                    }else{
                        Toast.makeText(context, "Debe Finalizar el Partido!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
    private void Guardar_Informacion_General(final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Estadistico:");
        progressDialog.setMessage("Guardando Resultados...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String id_user=String.valueOf(Usuario.SESION_ACTUAL.getId());
        String id_evento=String.valueOf(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getId());
        String id_fecha=String.valueOf(FechaEstadistico.FECHA_ESTADISTICO_TEMP.getFecha_actual().getId());
        String gol_local=goles_local.getText().toString();
        String gol_oponente=goles_oponentes.getText().toString();
        String pos_1=pos1.getText().toString();
        String pos_2=pos2.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                         int id_gestion=jsonResponse.getInt("id_gestion");
                         Registrar_Jugadores_Informacion(id_gestion,context);
                    }else {
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error Registrar :"+e);
                }
            }
        };

        Registrar_info_general validarSesion = new Registrar_info_general(id_user,id_evento,id_fecha,gol_local,gol_oponente,pos_1,pos_2,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(validarSesion);



    }
    private void Registrar_Jugadores_Informacion(final int id_gestion,final Context context) {

      for(int i=0;i<Estadistico_Gestion.LISTA_PERSONAS_TODO.size();i++){
             Registrar_Informacion_Jugador(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i),id_gestion,context);
         if(i==(Estadistico_Gestion.LISTA_PERSONAS_TODO.size()-1)) {
             Registrar_Informacion_Jugador(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i),id_gestion,context);
             Registrar_info_medidas(id_gestion,context);
         }
      }

    }
    private void Registrar_Informacion_Jugador(final PersonaEstadistico personaEstadistico, int id_ges, final Context context) {

        String titular;
        String expulsado;
        String id_gestion=String.valueOf(id_ges);
        String id_persona=String.valueOf(personaEstadistico.getPersona().getId());
        String pg=String.valueOf(personaEstadistico.getPrimerTiempo().getPaseGol()+personaEstadistico.getSegundoTiempo().getPaseGol());
        String dr=String.valueOf(personaEstadistico.getPrimerTiempo().getDribling()+personaEstadistico.getSegundoTiempo().getDribling());
        String og=String.valueOf(personaEstadistico.getPrimerTiempo().getOpcionGol()+personaEstadistico.getSegundoTiempo().getOpcionGol());
        String r=String.valueOf(personaEstadistico.getPrimerTiempo().getRemate()+personaEstadistico.getSegundoTiempo().getRemate());
        String g=String.valueOf(personaEstadistico.getPrimerTiempo().getGoles()+personaEstadistico.getSegundoTiempo().getGoles());
        String of=String.valueOf(personaEstadistico.getPrimerTiempo().getOfSide()+personaEstadistico.getSegundoTiempo().getOfSide());
        String bp=String.valueOf(personaEstadistico.getPrimerTiempo().getBalonPerdido()+personaEstadistico.getSegundoTiempo().getBalonPerdido());
        String br=String.valueOf(personaEstadistico.getPrimerTiempo().getBalonRecuperado()+personaEstadistico.getSegundoTiempo().getBalonRecuperado());
        String f=String.valueOf(personaEstadistico.getPrimerTiempo().getFaltas()+personaEstadistico.getSegundoTiempo().getFaltas());
        String ta=String.valueOf(personaEstadistico.getPrimerTiempo().getTarjetasAmarillas()+personaEstadistico.getSegundoTiempo().getTarjetasAmarillas());
        String tr=String.valueOf(personaEstadistico.getPrimerTiempo().getTarjetasRojas()+personaEstadistico.getSegundoTiempo().getTarjetasRojas());
        String atj=String.valueOf(personaEstadistico.getPrimerTiempo().getAtajadas()+personaEstadistico.getSegundoTiempo().getAtajadas());
        String tj=String.valueOf(personaEstadistico.getTiempo_jugado());
        String ptos=String.valueOf(personaEstadistico.getTotal_Puntos());

        if(personaEstadistico.getEntrante()==1 || personaEstadistico.isCambiado()){
             titular=String.valueOf(1);
        }else{
             titular=String.valueOf(0);
        }

                if(personaEstadistico.isExpulsado()){
                     expulsado=String.valueOf(1);
                 }else{
                     expulsado=String.valueOf(0);
                }


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                       debug("Persona registrada :"+personaEstadistico.getPersona().getId());
                    }else {
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error Registrar :"+e);
                }
            }
        };

        Registrar_info_jugadores validarSesion = new Registrar_info_jugadores(id_gestion,id_persona,pg,dr,og,r,g,of,bp,br,f,ta,tr,atj,tj,ptos,titular,expulsado,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(validarSesion);


    }
    private void Registrar_info_medidas(int id_ges,final Context context) {

      final String id_gestion=String.valueOf(id_ges);
      String op1=String.valueOf(Estadistico_Gestion.TEMP.getZPG1());
      String op2=String.valueOf(Estadistico_Gestion.TEMP.getZPG2());
      String op3=String.valueOf(Estadistico_Gestion.TEMP.getZF1());
      String op4=String.valueOf(Estadistico_Gestion.TEMP.getZF2());
      String op5=String.valueOf(Estadistico_Gestion.TEMP.getZR1());
      String op6=String.valueOf(Estadistico_Gestion.TEMP.getZR2());
      String op7=String.valueOf(Estadistico_Gestion.TEMP.getZPG12());
      String op8=String.valueOf(Estadistico_Gestion.TEMP.getZPG22());

      String op9=String.valueOf(Estadistico_Gestion.TEMP.getOF_IZQ_DR()+Estadistico_Gestion.TEMP.getOF_IZQ_DR2());
      String op10=String.valueOf(Estadistico_Gestion.TEMP.getOF_IZQ_PG()+Estadistico_Gestion.TEMP.getOF_IZQ_PG2());
      String op11=String.valueOf(Estadistico_Gestion.TEMP.getOF_ZF_OG()+Estadistico_Gestion.TEMP.getOF_ZF_OG2());
      String op12=String.valueOf(Estadistico_Gestion.TEMP.getOF_ZF_R()+Estadistico_Gestion.TEMP.getOF_ZF_R2());
      String op13=String.valueOf(Estadistico_Gestion.TEMP.getOF_CEN_OG()+Estadistico_Gestion.TEMP.getOF_CEN_OG2());
      String op14=String.valueOf(Estadistico_Gestion.TEMP.getOF_CEN_R()+Estadistico_Gestion.TEMP.getOF_CEN_R2());
      String op15=String.valueOf(Estadistico_Gestion.TEMP.getOF_DER_DR()+Estadistico_Gestion.TEMP.getOF_DER_DR2());
      String op16=String.valueOf(Estadistico_Gestion.TEMP.getOF_DER_PG()+Estadistico_Gestion.TEMP.getOF_DER_PG2());

      String estado=String.valueOf(1);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                             Registrar_Accion_Campo(id_gestion,context);
                    }else {
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error Registrar :"+e);
                }
            }
        };

        Registrar_info_medidas xx= new Registrar_info_medidas(id_gestion,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op13,op14,op15,op16,estado,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    private void Registrar_Accion_Campo(String id_ges,final Context context) {

        final String id_gestion=String.valueOf(id_ges);
        String op1=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(0).getTotal());
        String op2=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(1).getTotal());
        String op3=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(2).getTotal());
        String op4=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(3).getTotal());
        String op5=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(4).getTotal());
        String op6=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(5).getTotal());
        String op7=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(6).getTotal());
        String op8=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(7).getTotal());
        String op9=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(8).getTotal());
        String op10=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(9).getTotal());
        String op11=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(10).getTotal());
        String op12=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(11).getTotal());
        String op13=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(12).getTotal());
        String op14=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(13).getTotal());
        String op15=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(14).getTotal());
        String op16=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(15).getTotal());
        String op17=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(16).getTotal());
        String op18=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(17).getTotal());
        String op19=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(18).getTotal());
        String op20=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(19).getTotal());
        String op21=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(20).getTotal());
        String op22=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(21).getTotal());
        String op23=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(22).getTotal());
        String op24=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(23).getTotal());
        String op25=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(24).getTotal());
        String op26=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(25).getTotal());
        String op27=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(26).getTotal());
        String op28=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(27).getTotal());
        String op29=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(28).getTotal());
        String op30=String.valueOf(Estadistico_Gestion.TOTAL_SEGMENTOS.get(29).getTotal());

        String estado=String.valueOf(1);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                        Registrar_Linea_tiempo(id_gestion,context);
                    }else {
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error Registrar :"+e);
                }
            }
        };

        Registrar_info_accion_campo xx= new Registrar_info_accion_campo(id_gestion,op1,op2,op3,op4,op5,op6,op7,op8,op9,op10,op11,op12,op13,op14,op15,op16,op17,op18,op19,op20,op21,op22,op23,op24,op25,op26,op27,op28,op29,op30,estado,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    private void Registrar_Linea_tiempo(String id_gestion,final Context context) {

           for(int i=0;i<Estadistico_Gestion.LISTA_LINEA_TIEMPO.size();i++){
               Registrar_Linea(Estadistico_Gestion.LISTA_LINEA_TIEMPO.get(i),id_gestion,context);
               if(i==(Estadistico_Gestion.LISTA_LINEA_TIEMPO.size()-1)){
                   Registrar_Linea(Estadistico_Gestion.LISTA_LINEA_TIEMPO.get(i),id_gestion,context);
                   Validado_Ok();
               }
           }
    }
    private void Validado_Ok() {
      progressDialog.dismiss();
        Toast.makeText(context, "Información Guardad correctamente!", Toast.LENGTH_SHORT).show();

        Limpiar_Variables_Globales();

        Estadistico_Gestion.LISTA_PERSONAS_TODO.clear();
        Intent intent=new Intent(context,ListaFechasEstadisticosActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }
    private void Registrar_Linea(String accion, String id_ges,final Context context) {

        String id_gestion=String.valueOf(id_ges);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                      debug("ACCION REGISTRADA");
                    }else {
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error Registrar :"+e);
                }
            }
        };

        Registrar_info_linea_tiempo xx = new Registrar_info_linea_tiempo(id_gestion,accion,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }

    private void Recuperar_Informacion_General() {
           debug("RECUPERANDO INFORAMCION GENERAL");
         int s1=0,s2=0,s3=0,s4=0,s5=0,s6=0,s7=0,s8=0,s9=0,s10=0,s11=0,s12=0,s13=0,s14=0,s15=0,s16=0,s17=0,s18=0,s19=0,s20=0,s21=0,s22=0,s23=0,s24=0,s25=0,s26=0,s27=0,s28=0,s29=0,s30=0;

        int sb1=0,sb2=0,sb3=0,sb4=0,sb5=0,sb6=0,sb7=0,sb8=0,sb9=0,sb10=0,sb11=0,sb12=0,sb13=0,sb14=0,sb15=0,sb16=0,sb17=0,sb18=0,sb19=0,sb20=0,sb21=0,sb22=0,sb23=0,sb24=0,sb25=0,sb26=0,sb27=0,sb28=0,sb29=0,sb30=0;

        for(int i=0;i<CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.size();i++){
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion()!=null){



            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==1){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                    s1=s1+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==2){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s2=s2+1;
                }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==3){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                    s3=s3+1;
                }}

            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==4){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s4=s4+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==5){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s5=s5+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==6){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s6=s6+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==7){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s7=s7+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==8){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s8=s8+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==9){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){

                s9=s9+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==10){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s10=s10+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==11){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){

                s11=s11+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==12){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s12=s12+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==13){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s13=s13+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==14){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s14=s14+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==15){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s15=s15+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==16){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s16=s16+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==17){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s17=s17+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==18){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s18=s18+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==19){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s19=s19+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==20){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s20=s20+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==21){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s21=s21+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==22){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s22=s22+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==23){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s23=s23+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==24){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s24=s24+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==25){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s25=s25+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==26){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s26=s26+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==27){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s27=s27+1;
                }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==28){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s28=s28+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==29){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s29=s29+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getSector()==30){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().length()!=0){
                s30=s30+1;
            } }

            }
        }

        for(int i=0;i<CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.size();i++){
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion()!=null){


            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==1){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb1=sb1+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==2){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb2=sb2+1;
                }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==3){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb3=sb3+1;
            }  }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==4){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb4=sb4+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==5){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb5=sb5+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==6){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb6=sb6+1;
            }  }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==7){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb7=sb7+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==8){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb8=sb8+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==9){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb9=sb9+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==10){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb10=sb10+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==11){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb11=sb11+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==12){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb12=sb12+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==13){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb13=sb13+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==14){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb14=sb14+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==15){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb15=sb15+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==16){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb16=sb16+1;
            }  }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==17){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb17=sb17+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==18){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb18=sb18+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==19){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb19=sb19+1;
                } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==20){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb20=sb20+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==21){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb21=sb21+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==22){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb22=sb22+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==23){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb23=sb23+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==24){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb24=sb24+1;
            }}
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==25){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb25=sb25+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==26){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb26=sb26+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==27){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb27=sb27+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==28){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb28=sb28+1;
            } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==29){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb29=sb29+1;
                } }
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getSector()==30){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().length()!=0){
                sb30=sb30+1;
            }}

            }
        }

        Estadistico_Gestion.TOTAL_SEGMENTOS.get(0).setTotal(s1+sb1);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(1).setTotal(s2+sb2);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(2).setTotal(s3+sb3);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(3).setTotal(s4+sb4);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(4).setTotal(s5+sb5);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(5).setTotal(s6+sb6);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(6).setTotal(s7+sb7);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(7).setTotal(s8+sb8);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(8).setTotal(s9+sb9);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(9).setTotal(s10+sb10);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(10).setTotal(s11+sb11);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(11).setTotal(s12+sb12);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(12).setTotal(s13+sb13);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(13).setTotal(s14+sb14);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(14).setTotal(s15+sb15);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(15).setTotal(s16+sb16);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(16).setTotal(s17+sb17);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(17).setTotal(s18+sb18);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(18).setTotal(s19+sb19);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(19).setTotal(s20+sb20);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(20).setTotal(s21+sb21);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(21).setTotal(s22+sb22);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(22).setTotal(s23+sb23);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(23).setTotal(s24+sb24);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(24).setTotal(s25+sb25);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(25).setTotal(s26+sb26);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(26).setTotal(s27+sb27);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(27).setTotal(s28+sb28);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(28).setTotal(s29+sb29);
        Estadistico_Gestion.TOTAL_SEGMENTOS.get(29).setTotal(s30+sb30);

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

        String cambio="CAMBIO JUGADOR -> "+Estadistico_Gestion.TEMP.getMinutos_jugados()+"´"+Estadistico_Gestion.TEMP.getSegundos_jugados()+"´´ - "+titular.getPersona().getApellidos_Persona()+" Nº "+titular.getCamiseta_persona()+" por "+suplente.getPersona().getApellidos_Persona()+ " Nº "+suplente.getCamiseta_persona();

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

        titular.setCambiado(true);
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
                int minuto_cambio=Estadistico_Gestion.TEMP.getContador()/60;
                Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).setMinuto_Cambio(minuto_cambio);
            }
        }

    }
    private void Actualizar_Lista_Titulares_Campo() {

        Estadistico_Gestion.TEMP.setNombres_Personas(null);
        List<Persona> TEMP_NOMBRES=new ArrayList<>();
        for(int i=0;i<Estadistico_Gestion.LISTA_PERSONAS_TITULARES.size();i++){
            if(Estadistico_Gestion.LISTA_PERSONAS_TITULARES.get(i).isExpulsado()){

            }else{
                TEMP_NOMBRES.add(Estadistico_Gestion.LISTA_PERSONAS_TITULARES.get(i).getPersona());
            }

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

        if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.size()==0){
            listar_card();
        }else{
            CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.clear();
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


                int gol_lo=Buscar_Gol_Local();
                goles_local.setText(String.valueOf(gol_lo));
                int gol_opo=Buscar_Gol_Oponente();
                goles_oponentes.setText(String.valueOf(gol_opo));
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


                    int prueba=Estadistico_Gestion.TEMP.getContador()+1;
                    debug("Prueba : "+prueba);

                    Estadistico_Gestion.TEMP.setContador(prueba);

                    primer_tiempo_fin=Estadistico_Gestion.TEMP.getTiempo_Total();
                    if(chronometer.getText().toString().length()==4){
                        fin_primer_tiempo=String.valueOf(primer_tiempo_fin);
                    }else{
                        if(primer_tiempo_fin<10){
                            fin_primer_tiempo="0"+primer_tiempo_fin;
                        }else{
                            fin_primer_tiempo=String.valueOf(primer_tiempo_fin);
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

                    debug("FASE ACTUAL: "+Estadistico_Gestion.TEMP.getTiempo_actual());
                    debug("TIEMPO JUGADO - MINUTOS:"+Estadistico_Gestion.TEMP.getTiempo_Jugado());
                    debug("TOTAL PRIMER TIEMPO:"+Estadistico_Gestion.TEMP.getTotal_primer_tiempo());
                    debug("TOTAL SEGUNDO TIEMPO:"+Estadistico_Gestion.TEMP.getTotal_segundo_tiempo());
                    debug("PRIMER AVISO:"+Estadistico_Gestion.TEMP.getPrimer_aviso());
                    debug("SEGUNDO AVISO:"+Estadistico_Gestion.TEMP.getSegundo_aviso());
                    debug("TERCER AVISO:"+Estadistico_Gestion.TEMP.getTercer_aviso());
                    debug("CUARTO AVISO:"+Estadistico_Gestion.TEMP.getCuarto_aviso());

                    debug("MINUTOS CONTADOR: "+(Estadistico_Gestion.TEMP.getContador()/60));

                    debug("----------------------------------------------");

                    debug(" LINEA DE TIEMPO");
                    for(int i=0;i<Estadistico_Gestion.LISTA_LINEA_TIEMPO.size();i++){
                        debug((i+1)+"-"+Estadistico_Gestion.LISTA_LINEA_TIEMPO.get(i));
                    }
                    debug("----------------------------------------------");

                    //RECOPILAR AVISOS PRIMER TIEMPO

                    String primerAviso=String.valueOf(Estadistico_Gestion.TEMP.getPrimer_aviso());
                    if(chronometer.getText().toString().length()==4){
                        aviso1=primerAviso;
                    }else{
                        if(Estadistico_Gestion.TEMP.getTercer_aviso()<10){
                            aviso1="0"+primerAviso;
                        }else{
                            aviso1=primerAviso;
                        }
                    }

                    String segundoAviso=String.valueOf(Estadistico_Gestion.TEMP.getTiempo_Total2()-1);
                    Estadistico_Gestion.TEMP.setSegundo_aviso(Estadistico_Gestion.TEMP.getTiempo_Total2()-1);

                    if(chronometer.getText().toString().length()==4){
                        aviso2=segundoAviso;
                    }else{
                        if(Estadistico_Gestion.TEMP.getSegundo_aviso()<10){
                            aviso2="0"+segundoAviso;
                        }else{
                            aviso2=segundoAviso;
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


                    //Primer Tiempo Finalizado
                    if(chronometer.getText().toString().trim().equalsIgnoreCase(fin_primer_tiempo.trim()+":00")){
                        debug("ENTRO CIERRE DE PRIMER TIEMPO:");
                        fin_primer_tiempo="";
                        Estadistico_Gestion.TEMP.setTiempo_Total(0);
                        Estadistico_Gestion.TEMP.setTiempo_Total2(0);
                        primer_tiempo_fin=0;

                        cronometro.stop();
                        Mostrar_Dialog_Finalizado_Primer_Tiempo();
                    }






                    //RECUPERAR TIEMPO DE AVISOS
                    String tercerAviso=String.valueOf(Estadistico_Gestion.TEMP.getTercer_aviso());

                    if(chronometer.getText().toString().length()==4){
                         aviso3=tercerAviso;
                    }else{
                        if(Estadistico_Gestion.TEMP.getTercer_aviso()<10){
                            aviso3="0"+tercerAviso;

                        }else{
                            aviso3=tercerAviso;

                        }
                    }


                    String cuartoAviso=String.valueOf(Estadistico_Gestion.TEMP.getTotal_segundo_tiempo()-1);
                    Estadistico_Gestion.TEMP.setCuarto_aviso(Estadistico_Gestion.TEMP.getTotal_segundo_tiempo()-1);

                    if(chronometer.getText().toString().length()==4){
                        aviso4=cuartoAviso;
                    }else{
                        if(Estadistico_Gestion.TEMP.getCuarto_aviso()<10){
                            aviso4="0"+cuartoAviso;

                        }else{
                            aviso4=cuartoAviso;

                        }
                    }


                      //TERCER AVISO
                    if(chronometer.getText().toString().trim().equalsIgnoreCase(aviso3.trim()+":00")) {
                        debug("TERCER AVISO");
                        Mostrar_Tercer_Aviso();
                    }

                    if(validador==false){
                        //CUARTO AVISO
                        if(chronometer.getText().toString().trim().equalsIgnoreCase(aviso4.trim()+":00")){
                            debug("CUARTO AVISO");
                            Mostrar_Cuarto_Aviso();
                        }
                    }

                    //TIEMPO FINALIZADO
                    int tt=Estadistico_Gestion.TEMP.getTotal_segundo_tiempo();
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

                       Mostrar_Mensaje_final();
                    }

                }
            });
    }
    private void Mostrar_Mensaje_final() {

        if(da4!=null){
            da4.dismiss();
        }
        if(da5!=null){
            da5.dismiss();
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
    private void Actualizar_Tiempos_Partido() {
        int min_transcurridos=Estadistico_Gestion.TEMP.getContador()/60;
        debug("Minutos Transcurridos -----> "+min_transcurridos);
        for(int i = 0; i<Estadistico_Gestion.LISTA_PERSONAS_TODO.size(); i++){
            if(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getTitular()==1){
                if(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getSaliente()==1){
                }else{
                    if(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).isExpulsado()==false){
                        Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).setTiempo_jugado(min_transcurridos);

                        if(Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getEntrante()==1){
                            int d=Estadistico_Gestion.TEMP.getTiempo_Jugado()-Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).getMinuto_Cambio();                                              Estadistico_Gestion.LISTA_PERSONAS_TODO.get(i).setTiempo_jugado(d);
                        }
                    }
                }

            }

        }

        adapterInfoEquipo.notifyDataSetChanged();
        debug("ENTRO A ACTUALIZAR TIEMPOS DE PARTIDO");
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

        text.setText("El Primer Tiempo Terminara en 3 minutos");

        bo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  tiempo_Adicional=Integer.parseInt(text_extra.getText().toString());
                Estadistico_Gestion.TEMP.setTiempo_Adicional(tiempo_Adicional);
                if(Estadistico_Gestion.TEMP.getTiempo_Adicional()!=0){
                    adicional_tiempo.setText("+"+Estadistico_Gestion.TEMP.getTiempo_Adicional());
                    adicional_tiempo.setTextColor(context.getResources().getColor(R.color.verde));
                }

                int total_nuevo=Estadistico_Gestion.TEMP.getTotal_primer_tiempo()+tiempo_Adicional;
                debug("TOTAL NUEVO PRIMER TIEMPO: "+total_nuevo);
                Estadistico_Gestion.TEMP.setTotal_primer_tiempo(total_nuevo);
                Estadistico_Gestion.TEMP.setBloque_tiempo(total_nuevo);
                Estadistico_Gestion.TEMP.setTiempo_Total(total_nuevo);
                Estadistico_Gestion.TEMP.setTiempo_Total2(total_nuevo);



                debug("CERRAR PRIMER AVISO");
                da2.dismiss();
            }
        });
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

        text.setText("El Primer Tiempo Terminara en 1 minuto");

        bo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tiempo_adicional=Integer.parseInt(text_extra.getText().toString());
                Estadistico_Gestion.TEMP.setTiempo_Adicional(tiempo_adicional);

                if(Estadistico_Gestion.TEMP.getTiempo_Adicional()!=0){
                    adicional_tiempo.setText("+"+Estadistico_Gestion.TEMP.getTiempo_Adicional());
                    adicional_tiempo.setTextColor(context.getResources().getColor(R.color.verde));
                }

                int total_nuevo=Estadistico_Gestion.TEMP.getTotal_primer_tiempo()+tiempo_adicional;
                debug(" Total_Nuevo : "+total_nuevo);
                Estadistico_Gestion.TEMP.setTotal_primer_tiempo(total_nuevo);
                Estadistico_Gestion.TEMP.setBloque_tiempo(total_nuevo);
                Estadistico_Gestion.TEMP.setTiempo_Total(total_nuevo);
                Estadistico_Gestion.TEMP.setTiempo_Total2(total_nuevo);

                debug("CERRAR SEGUNDO AVISO");
                da3.dismiss();
            }
        });

    }
    private void Mostrar_Tercer_Aviso() {
        debug("ABRIR TERCER AVISO");

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
        final TextView text_extra=dialoglayout2.findViewById(R.id.tiempo_Extra);
        final Button bo=dialoglayout2.findViewById(R.id.boton_ok);
        final LinearLayout linear=dialoglayout2.findViewById(R.id.linear_adicional);

        builder4.setView(dialoglayout2);
        builder4.setCancelable(false);
        da4=builder4.show();

        text.setText("El Segundo Tiempo Terminara en 3 minutos");

        bo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int  tiempo_Adicional=Integer.parseInt(text_extra.getText().toString());
                Estadistico_Gestion.TEMP.setTiempo_Adicional2(tiempo_Adicional);

                if(Estadistico_Gestion.TEMP.getTiempo_Adicional2()!=0){
                    adicional_tiempo.setText("+"+Estadistico_Gestion.TEMP.getTiempo_Adicional2());
                    adicional_tiempo.setTextColor(context.getResources().getColor(R.color.verde));
                }

                int total_nuevo=Estadistico_Gestion.TEMP.getTotal_segundo_tiempo()+tiempo_Adicional;
                debug("TOTAL NUEVO SEGUNDO TIEMPO: "+total_nuevo);
                Estadistico_Gestion.TEMP.setTotal_segundo_tiempo(total_nuevo);


                debug("CERRAR TERCER AVISO");
                da4.dismiss();
            }
        });
    }
    private void Mostrar_Cuarto_Aviso() {
        debug("ABRIR CUARTO AVISO");
        if(da2!=null){
            da2.dismiss();
        }
        if(da3!=null){
            da3.dismiss();
        }
        if(da4!=null){
            da4.dismiss();
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
        da5=builder4.show();

        if(Estadistico_Gestion.TEMP.getTiempo_Adicional2()!=0){
            linear.setVisibility(View.GONE);
        }else{
            linear.setVisibility(View.VISIBLE);
        }

        text.setText("El Segundo Tiempo Terminara en 1 minuto");

        bo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tiempo_adicional=Integer.parseInt(text_extra.getText().toString());
                Estadistico_Gestion.TEMP.setTiempo_Adicional2(tiempo_adicional);
                if(tiempo_adicional!=0){
                    validador=true;
                }

                if(Estadistico_Gestion.TEMP.getTiempo_Adicional2()!=0){
                    adicional_tiempo.setText("+"+Estadistico_Gestion.TEMP.getTiempo_Adicional2());
                    adicional_tiempo.setTextColor(context.getResources().getColor(R.color.verde));
                }

                int total_nuevo=Estadistico_Gestion.TEMP.getTotal_segundo_tiempo()+tiempo_adicional;
                debug(" Total_Nuevo : "+total_nuevo);
                Estadistico_Gestion.TEMP.setTotal_segundo_tiempo(total_nuevo);

                debug("CERRAR SEGUNDO AVISO");
                da5.dismiss();
            }
        });

    }
    private void Mostrar_Dialog_Finalizado_Primer_Tiempo() {

        if(da2!=null){
            da2.dismiss();
        }
        if(da3!=null){
            da3.dismiss();
        }

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

                   if(Estadistico_Gestion.TEMP.isPause()==true || Estadistico_Gestion.TEMP.isStop()==true){
                       opcion1_menos.setEnabled(false);
                       opcion1_mas.setEnabled(false);
                       opcion2_menos.setEnabled(false);
                       opcion2_mas.setEnabled(false);
                       opcion3_menos.setEnabled(false);
                       opcion3_mas.setEnabled(false);
                       opcion4_menos.setEnabled(false);
                       opcion4_mas.setEnabled(false);
                   }else{
                       opcion1_menos.setEnabled(true);
                       opcion1_mas.setEnabled(true);
                       opcion2_menos.setEnabled(true);
                       opcion2_mas.setEnabled(true);
                       opcion3_menos.setEnabled(true);
                       opcion3_mas.setEnabled(true);
                       opcion4_menos.setEnabled(true);
                       opcion4_mas.setEnabled(true);
                   }

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


               }
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
        adapterCampo = new AdapterCampoEstadistico(this,CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1, new RecyclerViewOnItemClickListener(){
            @Override
            public void onClick(View v, int position) {
            }
        });

        recyclerView.setAdapter(adapterCampo);
        recyclerView.setLayoutManager(grid);
    }
    private void recyclercampoooo2() {

        grid = new GridLayoutManager(this,24);
        adapterCampo = new AdapterCampoEstadistico(this,CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2, new RecyclerViewOnItemClickListener(){
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
    private void listar_card2() {

        if(ancho>1500){
            int alt=altura;
            int anc=ancho;
            alt2=alt/18;
            anc2=anc/24;
            altura2.getLayoutParams().height=(alt-65);
            System.out.println("PANTALLA XX GRANDE");
            Agregado_Campos2();
            adapterCampo.notifyDataSetChanged();
        }else if(ancho>800 && ancho<1200){

            int alt=altura;
            int anc=ancho;
            alt2=alt/18;
            anc2=anc/24;
            altura2.getLayoutParams().height=(alt-25);
            System.out.println("PANTALLA GRANDE");
            Agregado_Campos2();
            adapterCampo.notifyDataSetChanged();
        }else{
            int alt=altura;
            int anc=ancho;
            alt2=alt/13;
            anc2=anc/24;
            altura2.getLayoutParams().height=(alt-30);
            System.out.println("PANTALLA PEKE");
            Agregado_Campos2();
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
                    Crear_Campo(i,1,1);
                }
                if(i>4 && i<=8  || i>28 && i<=32 || i>52 && i<=56   ){
                    Crear_Campo(i,2,1);
                }
                if(i>8 && i<=12  || i>32 && i<=36 || i>56 && i<=60  ){
                    Crear_Campo(i,3,1);
                }
            }


            if(i>12 && i<=24 || i>36 && i<=48 || i>60 && i<=72){

                if(i>12 && i<=16 || i>36 && i<=40 || i>60 && i<=64 ){
                    Crear_Campo(i,4,2);
                }
                if(i>16 && i<=20 || i>40 && i<=44 || i>64 && i<=68   ){
                    Crear_Campo(i,5,2);
                }
                if(i>20 && i<=24 || i>44 && i<=48 || i>68 && i<=72   ){
                    Crear_Campo(i,6,2);
                }

            }


            if(i>72 && i<=76 || i>96 && i<=100  || i>120 && i<=124 || i>144 && i<=148  || i>168  && i<=172  || i>192  && i<=196   || i>216  && i<=220   || i>240  && i<=244   || i>264  && i<=268  || i> 288 && i<=292  || i>312  && i<= 316){


                if(i>72 && i<=76 || i>96 && i<=100 || i>120 && i<=124){
                    Crear_Campo(i,7,3);
                }
                //---------------------------------
                if(i>144 && i<=148 || i>168 && i<=172 || i>192 && i<=196  || i>216 && i<=220 || i>240 && i<=244){
                    Crear_Campo(i,13,3);
                }


                //---------------------------------
                if(i>264 && i<=268 || i>288 && i<=292 || i>312 && i<=316){
                    Crear_Campo(i,19,3);
                }


            }
            if(i>76 && i<=84 || i>100 && i<=108 || i>124 && i<=132  || i>148 && i<= 156  || i>172  && i<=180 || i>196  && i<=204  || i>220 && i<=228  || i>244  && i<=252   || i> 268 && i<=276  || i>292  && i<=300  || i>316  && i<=324 ){

                     //Crear_Campo(0,4);
                if(i>76 && i<=80 || i>100 && i<=104 || i>124 && i<=128){
                    Crear_Campo(i,8,4);
                }

                if(i>80 && i<=84 || i>104 && i<=108 || i>128 && i<=132){
                    Crear_Campo(i,9,4);
                }

                //---------------------------------
                if(i>148 && i<=152 || i>172 && i<=176 || i>196 && i<=200 || i>220 && i<=224  || i>244 && i<=248){
                    Crear_Campo(i,14,4);
                }

                if(i>152 && i<=156 || i>176 && i<=180 || i>200 && i<=204 || i>224 && i<=228 || i>248 && i<=252){
                    Crear_Campo(i,15,4);
                }



                //---------------------------------
                if(i>268 && i<=272   || i>292 && i<=296 || i>316 && i<=320){
                    Crear_Campo(i,20,4);
                }

                if(i>272 && i<=276  || i>296 && i<=300 || i>320 && i<=324){
                    Crear_Campo(i,21,4);
                }




            }

            if(i>84 && i<=92 || i>108 && i<=116 || i> 132 && i<=140 || i>156 && i<= 164  || i>180  && i<=188  || i>204  && i<= 212 || i>228  && i<=236  || i>252  && i<=260  || i> 276 && i<=284  || i> 300 && i<= 308   || i> 324 && i<=332 ){
                //Crear_Campo(5);

                if(i>84 && i<=88 || i>108 && i<=112 || i>132 && i<=136){
                    Crear_Campo(i,10,5);
                }

                if(i>88 && i<=92 || i>112 && i<=116 || i>136 && i<=140){
                    Crear_Campo(i,11,5);
                }


                //---------------------------------
                if(i>156 && i<=160 || i>180 && i<=184 || i>204 && i<=208 || i>228 && i<=232 || i>252 && i<=256){
                    Crear_Campo(i,16,5);
                }

                if(i>160 && i<=164 || i>184 && i<=188 || i>208 && i<=212 || i>232 && i<=236 || i>256 && i<=260){
                    Crear_Campo(i,17,5);
                }



                //---------------------------------
                if(i>276 && i<=280  || i>300 && i<=304 || i>324 && i<=328){
                    Crear_Campo(i,22,5);
                }

                if(i>280 && i<=284 || i>304 && i<=308 || i>328 && i<=332){
                    Crear_Campo(i,23,5);
                }

            }
            if(i>92 && i<=96 || i>116 && i<=120 || i>140  && i<=144  || i>164 && i<= 168  || i>188  && i<=192  || i>212  && i<=216 || i>236  && i<=240 || i>260  && i<=264  || i> 284 && i<=288  || i> 308 && i<=312  || i>332  && i<=336  ){
                //Crear_Campo(6);

                if(i>92 && i<=96 || i>116 && i<=120 || i>140 && i<=144){
                    Crear_Campo(i,12,6);
                }
                //---------------------------------
                if(i>164 && i<=168 || i>188 && i<=192 || i>212 && i<=216 || i>236 && i<=240 || i>260 && i<=264){
                    Crear_Campo(i,18,6);
                }
                //---------------------------------
                if(i>284 && i<=288 || i>308 && i<=312  || i>332 && i<=336){
                    Crear_Campo(i,24,6);
                }
            }

            if(i>336 && i<=348  || i>360  && i<=372  || i>384  && i<=396  ){
                //Crear_Campo(7);

                if(i>336 && i<=340 || i>360 && i<=364  || i>384 && i<=388){
                    Crear_Campo(i,25,7);
                }

                if(i>340 && i<=344 || i>364 && i<=368 || i>388 && i<=392){
                    Crear_Campo(i,26,7);
                }

                if(i>344 && i<=348 || i>368 && i<=372 || i>392 && i<=396){
                    Crear_Campo(i,27,7);
                }

            }

            if(i>348 && i<=360  || i>372  && i<=384 || i>396  && i<=408  ){
                if(i>348 && i<=352 || i>372 && i<=376 || i>396 && i<=400){
                    Crear_Campo(i,28,8);
                }

                if(i>352 && i<=356 || i>376 && i<=380 || i>400 && i<=404){
                    Crear_Campo(i,29,8);
                }

                if(i>356 && i<=360 || i>380 && i<=384 || i>404 && i<=408){
                    Crear_Campo(i,30,8);
                }

            }
          }
        }
    private void Agregado_Campos2() {

        for(int i=1;i<=408;i++){
            if(i<=12 ||  i>24 && i<=36 || i>48 && i<=60){


                if(i<=4  || i>24 && i<=28 || i>48 && i<=52  ){
                    Crear_Campo2(i,1,1);
                }
                if(i>4 && i<=8  || i>28 && i<=32 || i>52 && i<=56   ){
                    Crear_Campo2(i,2,1);
                }
                if(i>8 && i<=12  || i>32 && i<=36 || i>56 && i<=60  ){
                    Crear_Campo2(i,3,1);
                }
            }


            if(i>12 && i<=24 || i>36 && i<=48 || i>60 && i<=72){

                if(i>12 && i<=16 || i>36 && i<=40 || i>60 && i<=64 ){
                    Crear_Campo2(i,4,2);
                }
                if(i>16 && i<=20 || i>40 && i<=44 || i>64 && i<=68   ){
                    Crear_Campo2(i,5,2);
                }
                if(i>20 && i<=24 || i>44 && i<=48 || i>68 && i<=72   ){
                    Crear_Campo2(i,6,2);
                }

            }


            if(i>72 && i<=76 || i>96 && i<=100  || i>120 && i<=124 || i>144 && i<=148  || i>168  && i<=172  || i>192  && i<=196   || i>216  && i<=220   || i>240  && i<=244   || i>264  && i<=268  || i> 288 && i<=292  || i>312  && i<= 316){


                if(i>72 && i<=76 || i>96 && i<=100 || i>120 && i<=124){
                    Crear_Campo2(i,7,3);
                }
                //---------------------------------
                if(i>144 && i<=148 || i>168 && i<=172 || i>192 && i<=196  || i>216 && i<=220 || i>240 && i<=244){
                    Crear_Campo2(i,13,3);
                }


                //---------------------------------
                if(i>264 && i<=268 || i>288 && i<=292 || i>312 && i<=316){
                    Crear_Campo2(i,19,3);
                }


            }
            if(i>76 && i<=84 || i>100 && i<=108 || i>124 && i<=132  || i>148 && i<= 156  || i>172  && i<=180 || i>196  && i<=204  || i>220 && i<=228  || i>244  && i<=252   || i> 268 && i<=276  || i>292  && i<=300  || i>316  && i<=324 ){

                //Crear_Campo(0,4);
                if(i>76 && i<=80 || i>100 && i<=104 || i>124 && i<=128){
                    Crear_Campo2(i,8,4);
                }

                if(i>80 && i<=84 || i>104 && i<=108 || i>128 && i<=132){
                    Crear_Campo2(i,9,4);
                }

                //---------------------------------
                if(i>148 && i<=152 || i>172 && i<=176 || i>196 && i<=200 || i>220 && i<=224  || i>244 && i<=248){
                    Crear_Campo2(i,14,4);
                }

                if(i>152 && i<=156 || i>176 && i<=180 || i>200 && i<=204 || i>224 && i<=228 || i>248 && i<=252){
                    Crear_Campo2(i,15,4);
                }



                //---------------------------------
                if(i>268 && i<=272   || i>292 && i<=296 || i>316 && i<=320){
                    Crear_Campo2(i,20,4);
                }

                if(i>272 && i<=276  || i>296 && i<=300 || i>320 && i<=324){
                    Crear_Campo2(i,21,4);
                }




            }

            if(i>84 && i<=92 || i>108 && i<=116 || i> 132 && i<=140 || i>156 && i<= 164  || i>180  && i<=188  || i>204  && i<= 212 || i>228  && i<=236  || i>252  && i<=260  || i> 276 && i<=284  || i> 300 && i<= 308   || i> 324 && i<=332 ){
                //Crear_Campo(5);

                if(i>84 && i<=88 || i>108 && i<=112 || i>132 && i<=136){
                    Crear_Campo2(i,10,5);
                }

                if(i>88 && i<=92 || i>112 && i<=116 || i>136 && i<=140){
                    Crear_Campo2(i,11,5);
                }


                //---------------------------------
                if(i>156 && i<=160 || i>180 && i<=184 || i>204 && i<=208 || i>228 && i<=232 || i>252 && i<=256){
                    Crear_Campo2(i,16,5);
                }

                if(i>160 && i<=164 || i>184 && i<=188 || i>208 && i<=212 || i>232 && i<=236 || i>256 && i<=260){
                    Crear_Campo2(i,17,5);
                }



                //---------------------------------
                if(i>276 && i<=280  || i>300 && i<=304 || i>324 && i<=328){
                    Crear_Campo2(i,22,5);
                }

                if(i>280 && i<=284 || i>304 && i<=308 || i>328 && i<=332){
                    Crear_Campo2(i,23,5);
                }

            }
            if(i>92 && i<=96 || i>116 && i<=120 || i>140  && i<=144  || i>164 && i<= 168  || i>188  && i<=192  || i>212  && i<=216 || i>236  && i<=240 || i>260  && i<=264  || i> 284 && i<=288  || i> 308 && i<=312  || i>332  && i<=336  ){
                //Crear_Campo(6);

                if(i>92 && i<=96 || i>116 && i<=120 || i>140 && i<=144){
                    Crear_Campo2(i,12,6);
                }
                //---------------------------------
                if(i>164 && i<=168 || i>188 && i<=192 || i>212 && i<=216 || i>236 && i<=240 || i>260 && i<=264){
                    Crear_Campo2(i,18,6);
                }
                //---------------------------------
                if(i>284 && i<=288 || i>308 && i<=312  || i>332 && i<=336){
                    Crear_Campo2(i,24,6);
                }
            }

            if(i>336 && i<=348  || i>360  && i<=372  || i>384  && i<=396  ){
                //Crear_Campo(7);

                if(i>336 && i<=340 || i>360 && i<=364  || i>384 && i<=388){
                    Crear_Campo2(i,25,7);
                }

                if(i>340 && i<=344 || i>364 && i<=368 || i>388 && i<=392){
                    Crear_Campo2(i,26,7);
                }

                if(i>344 && i<=348 || i>368 && i<=372 || i>392 && i<=396){
                    Crear_Campo2(i,27,7);
                }

            }

            if(i>348 && i<=360  || i>372  && i<=384 || i>396  && i<=408  ){
                if(i>348 && i<=352 || i>372 && i<=376 || i>396 && i<=400){
                    Crear_Campo2(i,28,8);
                }

                if(i>352 && i<=356 || i>376 && i<=380 || i>400 && i<=404){
                    Crear_Campo2(i,29,8);
                }

                if(i>356 && i<=360 || i>380 && i<=384 || i>404 && i<=408){
                    Crear_Campo2(i,30,8);
                }

            }
        }
    }
    private void Crear_Campo(int i,int segmento,int area) {

        CampoEstadistico temp =new CampoEstadistico(i,area,segmento,null,null,1,alt2,anc2,R.drawable.layout_border, "");
        CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.add(temp);
    }
    private void Crear_Campo2(int i,int segmento,int area) {

        CampoEstadistico temp =new CampoEstadistico(i,area,segmento,null,null,1,alt2,anc2,R.drawable.layout_border, "");
        CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.add(temp);
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
        Estadistico_Gestion.TEMP.setPause(true);
    }
    private void Reanudar() {
        debug("REANUDAR");
        Estadistico_Gestion.TEMP.setPause(false);
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
        boton_partido.setText("Continuar");
        Estadistico_Gestion.TEMP.setEstado_partido(3);
        Estadistico_Gestion.TEMP.setStop(true);
        cronometro.stop();
    }
    private void Continuar_Segundo_Tiempo() {
        Guardar_Informacion_Primer_Tiempo();

        Estadistico_Gestion.TEMP.setTiempo_actual(2);
        Estadistico_Gestion.TEMP.setTiempo_Total(0);
        Estadistico_Gestion.TEMP.setTiempo_Total2(0);
        Estadistico_Gestion.TEMP.setBloque_tiempo(0);

        Estadistico_Gestion.TEMP.setEstado_partido(2);
        Estadistico_Gestion.TEMP.setStop(false);

        accion_partido.setCardBackgroundColor(getResources().getColor(R.color.deep_naranja400));
        boton_partido.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_pause, 0, 0, 0);
        boton_partido.setText("PAUSE");
        Estadistico_Gestion.TEMP.setBloque_tiempo(0);
        primer_tiempo_fin=0;
        int minuto_corte=Estadistico_Gestion.TEMP.getMinutos_x_tiempo();
        long m_corte=(minuto_corte*60)*1000;
        cronometro.setBase(SystemClock.elapsedRealtime()-m_corte);
        cronometro.start();
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

                                    Limpiar_Variables_Globales();

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

                                    Limpiar_Variables_Globales();

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
    private void Limpiar_Variables_Globales() {
         Estadistico_Gestion.LISTA_PERSONAS_TODO.clear();
         Estadistico_Gestion.LISTA_PERSONAS_TITULARES.clear();
         Estadistico_Gestion.LISTA_PERSONAS_SUPLENTES.clear();
         Estadistico_Gestion.ADAPTER_TITULARES=null;
         Estadistico_Gestion.ADAPTER_SUPLENTES=null;

         Estadistico_Gestion.LISTA_LINEA_TIEMPO.clear();
        Estadistico_Gestion.TOTAL_SEGMENTOS.clear();
         Estadistico_Gestion.TEMP.setBloque_tiempo(0);
         Estadistico_Gestion.TEMP.setCantidad_tiempos(0);
         Estadistico_Gestion.TEMP.setContador(0);


         Estadistico_Gestion.TEMP.setOF_CEN_OG(0);
         Estadistico_Gestion.TEMP.setOF_CEN_OG2(0);
         Estadistico_Gestion.TEMP.setOF_CEN_R(0);
         Estadistico_Gestion.TEMP.setOF_CEN_R2(0);
         Estadistico_Gestion.TEMP.setOF_IZQ_DR(0);
        Estadistico_Gestion.TEMP.setOF_IZQ_DR2(0);
        Estadistico_Gestion.TEMP.setOF_IZQ_PG(0);
        Estadistico_Gestion.TEMP.setOF_IZQ_PG2(0);
        Estadistico_Gestion.TEMP.setOF_DER_DR(0);
        Estadistico_Gestion.TEMP.setOF_DER_DR2(0);
        Estadistico_Gestion.TEMP.setOF_DER_PG(0);
        Estadistico_Gestion.TEMP.setOF_DER_PG2(0);
        Estadistico_Gestion.TEMP.setOF_ZF_OG(0);
        Estadistico_Gestion.TEMP.setOF_ZF_OG2(0);
        Estadistico_Gestion.TEMP.setOF_ZF_R(0);
        Estadistico_Gestion.TEMP.setOF_ZF_R2(0);

        Estadistico_Gestion.TEMP.setZF1(0);
        Estadistico_Gestion.TEMP.setZF2(0);
        Estadistico_Gestion.TEMP.setZPG1(0);
        Estadistico_Gestion.TEMP.setZPG2(0);
        Estadistico_Gestion.TEMP.setZPG12(0);
        Estadistico_Gestion.TEMP.setZPG22(0);
        Estadistico_Gestion.TEMP.setZR1(0);
        Estadistico_Gestion.TEMP.setZR2(0);

    }


    private void Guardar_Informacion_Primer_Tiempo() {

        adapterCampo=null;
        recyclercampoooo2();
        if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.size()==0){
            listar_card2();
        }else{
            CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.clear();
            listar_card2();
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

                int gol_lo=Buscar_Gol_Local();
                goles_local.setText(String.valueOf(gol_lo));
                int gol_opo=Buscar_Gol_Oponente();
                goles_oponentes.setText(String.valueOf(gol_opo));
            }
        });

    }

    private int Buscar_Gol_Local() {
        int d=0;

        for(int i=0;i<CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.size();i++){
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion()!=null){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().equalsIgnoreCase("G")){
                    d=d+1;
                }
            }

        }
        for(int i=0;i<CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.size();i++){
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion()!=null){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().equalsIgnoreCase("G")){
                    d=d+1;
                }
            }

        }

        return  d;
    }

    private int Buscar_Gol_Oponente() {
        int d=0;

        for(int i=0;i<CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.size();i++){
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion()!=null){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().equalsIgnoreCase("GO")){
                    d=d+1;
                }
            }

        }
        for(int i=0;i<CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.size();i++){
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion()!=null){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().equalsIgnoreCase("GO")){
                    d=d+1;
                }
            }

        }

        return  d;
    }

    public void BUSCAR_PROFUNDIDAD_PRIMER_TIEMPO(){

        int z1=0,z2=0,z3=0,z4=0,z5=0,z6=0,z7=0,z8=0;
        for(int i=0;i<CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.size();i++){
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion()!=null){
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getCod()==2){

                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().equalsIgnoreCase("DR")){
                    z1=z1+1;

                }

                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().equalsIgnoreCase("PG")){
                    z2=z2+1;

                }


            }

            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getCod()==6){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().equalsIgnoreCase("OG")){
                    z3=z3+1;
                }

                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().equalsIgnoreCase("R")){
                   z4=z4+1;
                }
            }

            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getCod()==5){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().equalsIgnoreCase("OG")){
                    z5=z5+1;
                }

                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().equalsIgnoreCase("R")){
                    z6=z6+1;
                }
            }

            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getCod()==8){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().equalsIgnoreCase("DR")){
                    z7=z7+1;
                }

                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_1.get(i).getOpcion().getOpcion().equalsIgnoreCase("PG")){
                    z8=z8+1;
                }
            }
            }
        }

        Estadistico_Gestion.TEMP.setOF_IZQ_DR(z1);
        Estadistico_Gestion.TEMP.setOF_IZQ_PG(z2);
        Estadistico_Gestion.TEMP.setOF_ZF_OG(z3);
        Estadistico_Gestion.TEMP.setOF_ZF_R(z4);
        Estadistico_Gestion.TEMP.setOF_CEN_OG(z5);
        Estadistico_Gestion.TEMP.setOF_CEN_R(z6);
        Estadistico_Gestion.TEMP.setOF_DER_DR(z7);
        Estadistico_Gestion.TEMP.setOF_DER_PG(z8);
    }
    public void BUSCAR_PROFUNDIDAD_SEGUNDO_TIEMPO(){

        int z1=0,z2=0,z3=0,z4=0,z5=0,z6=0,z7=0,z8=0;
        for(int i=0;i<CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.size();i++){
            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion()!=null){


            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getCod()==2){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().equalsIgnoreCase("DR")){
                    z1=z1+1;
                }

                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().equalsIgnoreCase("PG")){
                    z2=z2+1;
                }
            }

            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getCod()==6){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().equalsIgnoreCase("OG")){
                   z3=z3+1;
                }

                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().equalsIgnoreCase("R")){
                   z4=z4+1;
                }
            }

            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getCod()==5){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().equalsIgnoreCase("OG")){
                    z5=z5+1;
                }

                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().equalsIgnoreCase("R")){
                   z6=z6+1;
                }
            }

            if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getCod()==8){
                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().equalsIgnoreCase("DR")){
                    z7=z7+1;
                }

                if(CampoEstadistico.LISTACAMPOESTADISTICO_TIEMPO_2.get(i).getOpcion().getOpcion().equalsIgnoreCase("PG")){
                    z8=z8+1;
                }
            }
         }
        }

        Estadistico_Gestion.TEMP.setOF_IZQ_DR2(z1);
        Estadistico_Gestion.TEMP.setOF_IZQ_PG2(z2);
        Estadistico_Gestion.TEMP.setOF_ZF_OG2(z3);
        Estadistico_Gestion.TEMP.setOF_ZF_R2(z4);
        Estadistico_Gestion.TEMP.setOF_CEN_OG2(z5);
        Estadistico_Gestion.TEMP.setOF_CEN_R2(z6);
        Estadistico_Gestion.TEMP.setOF_DER_DR2(z7);
        Estadistico_Gestion.TEMP.setOF_DER_PG2(z8);
    }

    public void debug(String sm){
        System.out.println(sm);
    }
}
