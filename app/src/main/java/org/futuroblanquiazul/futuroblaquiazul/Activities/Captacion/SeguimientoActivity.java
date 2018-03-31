package org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoPersonaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.ListaPersonasGrupoPruebasActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas.PruebaFisicoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterCampo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Campo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Seguimiento;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarResultadosDiagnostico;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarResultadosDiagnosticoSeguimiento;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarSeguimientos;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Captacion_Vista;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Captacion_funcional;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Diagnostico_Otros;
import org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Diagnostico;
import org.futuroblanquiazul.futuroblaquiazul.Utils.ResultadosDiagnostico;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class SeguimientoActivity extends AppCompatActivity {
    private RecyclerView recyclerCampo;
    private GridLayoutManager grid;
    private AdapterCampo adapterCampo;
    Button opcion_guardar,opcion_informacion,opcion_prueba;
    int altura,ancho,alt2,anc2;
    Context context;
    AlertDialog da,da2;
    LinearLayout linea;
    View vieee;
    List<Integer> Resultados_Diagnostico;
    int id_psico,id_social;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento);
        recyclerCampo=findViewById(R.id.Recycler_seguimiento);
        opcion_guardar=findViewById(R.id.opciones_guardar);
        opcion_informacion=findViewById(R.id.opciones_informacion);
        opcion_prueba=findViewById(R.id.opciones_diagnostico);
        Resultados_Diagnostico=new ArrayList<>();

        Seguimiento.SEGUIMIENTO.Vaciar_Datos();

        for(int i=0;i<Recursos_Diagnostico.LISTA_SOCIAL2.size();i++){
            Recursos_Diagnostico.LISTA_SOCIAL2.get(i).setResultado(0);
        }
        for(int i=0;i<Recursos_Diagnostico.LISTA_PSICO2.size();i++){
            Recursos_Diagnostico.LISTA_PSICO2.get(i).setResultado(0);
        }




        linea=findViewById(R.id.altura_campo);
        context=this;


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

       Opcion_Prueba();
       Opcion_Informacion();

        opcion_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Seguimiento.SEGUIMIENTO.getNombre_Competencia()!=null){
                    if(Seguimiento.SEGUIMIENTO.getNombre_Rival()!=null){
                        if(Seguimiento.SEGUIMIENTO.getMinutos_Juego()!=0){

                            if (Seguimiento.SEGUIMIENTO.getMinutos_Juego() <= 120) {
                                Intent intent = new Intent(SeguimientoActivity.this,ValidarSeguimientoActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                SeguimientoActivity.this.startActivity(intent);
                            }else{
                                Toast.makeText(context, "El tiempo de juego debe ser menor a 120 minutos", Toast.LENGTH_SHORT).show();
                            }

                           // Guardar_Seguimiento();
                        }else{
                            MostrarError("Debe Ingresar Minutos Jugados en el evento");
                        }
                    }else{
                        MostrarError("Debe Ingresar el Nombre del Rival");
                    }

                }else{
                    MostrarError("Debe Ingresar el Nombre de la Competencia para continuar");
                }




            }
        });

        adapterCampo.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                Seguimiento.SEGUIMIENTO.setCantidad_Pierde_Balon(adapterCampo.getP());
                Seguimiento.SEGUIMIENTO.setCantidad_Pase_Gol(adapterCampo.getPG());
                Seguimiento.SEGUIMIENTO.setCantidad_Dribbling(adapterCampo.getDR());
                Seguimiento.SEGUIMIENTO.setCantidad_Recupera_Balon(adapterCampo.getR());
                Seguimiento.SEGUIMIENTO.setGoles(adapterCampo.getGoles());

            }
        });


    }
    private void Opcion_Prueba() {
        opcion_prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                final View dialoglayout = inflater.inflate(R.layout.areas_pruebas, null);
                final ImageView cerrar=dialoglayout.findViewById(R.id.cerrar_ventana);

                final AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
                builder4.setView(dialoglayout);
                da=builder4.show();

                cerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        da.dismiss();
                    }
                });

                Crear_Animaciones(dialoglayout);
                Seteo_RadioGroups(dialoglayout);
            }
        });
    }
    private void Opcion_Informacion() {
        opcion_informacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                final View dialoglayout2 = inflater.inflate(R.layout.areas_informacion, null);
                final ImageView cerrar2=dialoglayout2.findViewById(R.id.cerrar_ventana2);

                final EditText nom_competencia=dialoglayout2.findViewById(R.id.nombre_competencia);
                final EditText nom_rival=dialoglayout2.findViewById(R.id.nombre_rival);
                final EditText tiempo_jugado=dialoglayout2.findViewById(R.id.tiempo_jugado);
                final CheckBox checTitular=dialoglayout2.findViewById(R.id.check_titular);
                final CheckBox checkCapitan=dialoglayout2.findViewById(R.id.check_capitan);



                final AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
                builder4.setView(dialoglayout2);
                da2=builder4.show();

                if(Seguimiento.SEGUIMIENTO.getNombre_Competencia()!=null){
                    nom_competencia.setText(Seguimiento.SEGUIMIENTO.getNombre_Competencia());
                }

                if(Seguimiento.SEGUIMIENTO.getNombre_Rival()!=null){
                    nom_rival.setText(Seguimiento.SEGUIMIENTO.getNombre_Rival());
                }

                if(Seguimiento.SEGUIMIENTO.getMinutos_Juego()!=0){
                    tiempo_jugado.setText(String.valueOf(Seguimiento.SEGUIMIENTO.getMinutos_Juego()));
                }

                if(Seguimiento.SEGUIMIENTO.getTitular()==1){
                    checTitular.setChecked(true);
                }

                if(Seguimiento.SEGUIMIENTO.getCapitan()==1){
                    checkCapitan.setChecked(true);
                }


                cerrar2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        da2.dismiss();
                    }
                });


                checkCapitan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if(isChecked==true){
                            Seguimiento.SEGUIMIENTO.setCapitan(1);
                        }else{
                            Seguimiento.SEGUIMIENTO.setCapitan(0);

                        }
                    }
                });

                checTitular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked==true){
                            Seguimiento.SEGUIMIENTO.setTitular(1);
                        }else{
                            Seguimiento.SEGUIMIENTO.setTitular(0);
                        }
                    }
                });

                nom_competencia.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.toString().length()!=0){
                            Seguimiento.SEGUIMIENTO.setNombre_Competencia(s.toString());

                        }else{
                            Seguimiento.SEGUIMIENTO.setNombre_Competencia("");

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                nom_rival.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.toString().length()!=0){
                            Seguimiento.SEGUIMIENTO.setNombre_Rival(s.toString());
                        }else{
                            Seguimiento.SEGUIMIENTO.setNombre_Rival("");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                tiempo_jugado.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.toString().length()!=0){
                            int tiempo=Integer.parseInt(s.toString());
                            Seguimiento.SEGUIMIENTO.setMinutos_Juego(tiempo);
                        }else{
                            Seguimiento.SEGUIMIENTO.setMinutos_Juego(0);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }
        });
    }
    private void Crear_Animaciones(View v) {
      debug("Entro a crear animacion");
        for(int i = 0; i< Recursos_Diagnostico.LISTA_VISTAS2.size(); i++){

            LinearLayout linear=v.findViewById(Recursos_Diagnostico.LISTA_VISTAS2.get(i).getContenedor());
            LayoutInflater inflater2 = LayoutInflater.from(context);

            final View view_actual = inflater2.inflate(Recursos_Diagnostico.LISTA_VISTAS2.get(i).getVista(), linear, true);
            LinearLayout line=view_actual.findViewById(Recursos_Diagnostico.LISTA_VISTAS2.get(i).getArea_Accion());

            Recursos_Diagnostico.LISTA_VISTAS2.get(i).setView(line);

            LinearLayout Accion_Panel=v.findViewById(Recursos_Diagnostico.LISTA_VISTAS2.get(i).getPanel_Accion());
            Generar_Animacion(Recursos_Diagnostico.LISTA_VISTAS2.get(i),line,Accion_Panel,v);
        }

    }
    private void recyclercampoooo() {

        grid = new GridLayoutManager(this,24);
        adapterCampo = new AdapterCampo(this,Campo.LISTACAMPO, new RecyclerViewOnItemClickListener(){
            @Override
            public void onClick(View v, int position) {
            }
        });

        recyclerCampo.setAdapter(adapterCampo);
        recyclerCampo.setLayoutManager(grid);
    }
    private void listar_card() {

        if(ancho>1500){
            int alt=altura;
            int anc=ancho;
            alt2=alt/18;
            anc2=anc/24;
            linea.getLayoutParams().height=(alt-65);

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
            linea.getLayoutParams().height=(alt-25);

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
            linea.getLayoutParams().height=(alt-30);
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
    private void Generar_Animacion(final Captacion_Vista captacion_vista, final LinearLayout view_actual, LinearLayout Accion_Panel,View v ) {
        debug("Entro a Generar");
        Accion_Panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(captacion_vista.getAccion()==0){

                    view_actual.setVisibility(View.VISIBLE);
                    captacion_vista.setAccion(1);
                    Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abajo);
                    view_actual.startAnimation(animation);
                    cerrar_ventanas(view_actual);
                }else if(captacion_vista.getAccion()==1){

                    captacion_vista.setAccion(0);
                    Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.arriba);
                    view_actual.startAnimation(animation);
                    view_actual.setVisibility(View.GONE);
                }

            }
        });
    }
    private void cerrar_ventanas(LinearLayout view_actual) {
        for(int i = 0; i< Recursos_Diagnostico.LISTA_VISTAS2.size(); i++){
            if(Recursos_Diagnostico.LISTA_VISTAS2.get(i).getView()==view_actual){

            }else{
                Recursos_Diagnostico.LISTA_VISTAS2.get(i).getView().setVisibility(View.GONE);
            }
        }

    }
    private void Seteo_RadioGroups(View v) {

            for(int i = 0; i< Recursos_Diagnostico.LISTA_SOCIAL2.size(); i++){
                Generar_Funcion(i, Recursos_Diagnostico.LISTA_SOCIAL2.get(i),v);
            }
            for(int i = 0; i< Recursos_Diagnostico.LISTA_PSICO2.size(); i++){
                Generar_Funcion(i, Recursos_Diagnostico.LISTA_PSICO2.get(i),v);
            }
    }
    private void Generar_Funcion(int v, final Captacion_funcional captacion_funcional, View vie) {
        debug("Entro a Generar Funcion");
        int n=v+1;
        vieee=vie;
        TextView textView=vie.findViewById(captacion_funcional.getTextView());
        textView.setText(n+".- "+captacion_funcional.getOpcion());

        RadioGroup grupo=vie.findViewById(captacion_funcional.getGroupRadio());
        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i== captacion_funcional.getRadio1()){
                    captacion_funcional.setResultado(0);
                    Refrescar_Totales(vieee);
                }else if(i == captacion_funcional.getRadio2()){
                    captacion_funcional.setResultado(1);
                    Refrescar_Totales(vieee);
                }
                else if(i == captacion_funcional.getRadio3()){
                    captacion_funcional.setResultado(2);
                    Refrescar_Totales(vieee);
                }else if(i == captacion_funcional.getRadio4()){
                    captacion_funcional.setResultado(3);
                    Refrescar_Totales(vieee);
                }
            }
        });

        if(captacion_funcional.getResultado()==0){
            grupo.check(captacion_funcional.getRadio1());
        }else if(captacion_funcional.getResultado()==1){
            grupo.check(captacion_funcional.getRadio2());
        }else if(captacion_funcional.getResultado()==2){
            grupo.check(captacion_funcional.getRadio3());
        }else if(captacion_funcional.getResultado()==3){
            grupo.check(captacion_funcional.getRadio4());
        }

    }

    private void Refrescar_Totales(View v) {
        debug("Entro a Refrescar totales");
        int total3=0;
        int total5=0;

        for(int i = 0; i< Recursos_Diagnostico.LISTA_SOCIAL2.size(); i++){
            total3=total3+ Recursos_Diagnostico.LISTA_SOCIAL2.get(i).getResultado();
        }
        TextView social_total=v.findViewById(Recursos_Diagnostico.LISTA_VISTAS2.get(0).getTextViewTotal());
        social_total.setText(total3+" Ptos.");

        for(int i = 0; i< Recursos_Diagnostico.LISTA_PSICO2.size(); i++){
            total5=total5+ Recursos_Diagnostico.LISTA_PSICO2.get(i).getResultado();
        }
        TextView psico_total=v.findViewById(Recursos_Diagnostico.LISTA_VISTAS2.get(1).getTextViewTotal());
        psico_total.setText(total5+" Ptos.");
    }
    public void debug(String sm){
        System.out.println(sm);
    }

    private void MostrarError(String sms){
        Toast.makeText(context,sms, Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed() {


        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("Seguimiento")
                .setMessage("¿Desea salir de la Evaluaciòn de Seguimiento?")
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(SeguimientoActivity.this,ListaSeguimientosActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                SeguimientoActivity.this.startActivity(intent);

                                Seguimiento.SEGUIMIENTO.Vaciar_Datos();

                                for(int i=0;i<Recursos_Diagnostico.LISTA_SOCIAL2.size();i++){
                                    Recursos_Diagnostico.LISTA_SOCIAL2.get(i).setResultado(0);
                                }

                                for(int i=0;i<Recursos_Diagnostico.LISTA_PSICO2.size();i++){
                                    Recursos_Diagnostico.LISTA_PSICO2.get(i).setResultado(0);
                                }

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
