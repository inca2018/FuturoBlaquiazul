package org.futuroblanquiazul.futuroblaquiazul.Activities.Pruebas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.BarrioIntimo.BarrioIntimoPersonaActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia.ListaPersonasGrupoPruebasActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.PruebaPsicologico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarPruebaFisica;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarPruebaPsicologica;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Captacion_Vista;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Captacion_funcional;
import org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Diagnostico;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Registro_Postulante;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Diagnostico.LISTA_PRUEBA_TECNICA_PASE_CONTROL;

public class PruebaPsicologicaActivity extends AppCompatActivity {
    ScrollView scroll_prueba_psico;
    TextView nombre_jugador;
    Spinner spinner_posiciones;
    TextView total_general;
    TextView ideal1,ideal2,ideal3,ideal4,ideal5;
    TextView real1,real2,real3,real4,real5;
    ProgressDialog progressDialog;
    String[] posiciones;
    Context context;
    Button guardar;
    List<Integer> Resultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_psicologico);
        scroll_prueba_psico=findViewById(R.id.scroll_prueba_psico);
        nombre_jugador=findViewById(R.id.prueba_psico_persona);
        spinner_posiciones=findViewById(R.id.prueba_psico_spinner_posiciones);
        total_general=findViewById(R.id.prueba_psico_total);
        ideal1=findViewById(R.id.prueba_psico_esperado1);
        ideal2=findViewById(R.id.prueba_psico_esperado2);
        ideal3=findViewById(R.id.prueba_psico_esperado3);
        ideal4=findViewById(R.id.prueba_psico_esperado4);
        ideal5=findViewById(R.id.prueba_psico_esperado5);
        real1=findViewById(R.id.prueba_psico_real1);
        real2=findViewById(R.id.prueba_psico_real2);
        real3=findViewById(R.id.prueba_psico_real3);
        real4=findViewById(R.id.prueba_psico_real4);
        real5=findViewById(R.id.prueba_psico_real5);
        guardar=findViewById(R.id.prueba_psico_guardar);
        context=this;
        Resultado=new ArrayList<>();

        Llenar_Spinner();

        Creacion_Animaciones();
        Seteo_RadioGroups();

        spinner_posiciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);

                switch (String.valueOf(item)){

                    case "-- Seleccione --":

                        ideal1.setText("0");
                        ideal2.setText("0");
                        ideal3.setText("0");
                        ideal4.setText("0");
                        ideal5.setText("0");
                        mostrar_resultados();

                        break;
                    case "AR":

                        ideal1.setText("73");
                        ideal2.setText("75");
                        ideal3.setText("65");
                        ideal4.setText("79");
                        ideal5.setText("60");
                        mostrar_resultados();
                        break;
                    case "DFC":
                        ideal1.setText("68");
                        ideal2.setText("75");
                        ideal3.setText("63");
                        ideal4.setText("65");
                        ideal5.setText("80");
                        mostrar_resultados();
                        break;
                    case  "DFL":

                        ideal1.setText("60");
                        ideal2.setText("75");
                        ideal3.setText("62");
                        ideal4.setText("70");
                        ideal5.setText("80");
                        mostrar_resultados();
                        break;
                    case "MCD":

                        ideal1.setText("80");
                        ideal2.setText("75");
                        ideal3.setText("65");
                        ideal4.setText("65");
                        ideal5.setText("80");
                        mostrar_resultados();
                        break;
                    case "MC - MO":
                        ideal1.setText("70");
                        ideal2.setText("75");
                        ideal3.setText("67");
                        ideal4.setText("60");
                        ideal5.setText("80");
                        mostrar_resultados();
                        break;
                    case "EXT":

                        ideal1.setText("63");
                        ideal2.setText("75");
                        ideal3.setText("55");
                        ideal4.setText("60");
                        ideal5.setText("70");
                        mostrar_resultados();

                        break;
                    case "DC":

                        ideal1.setText("77");
                        ideal2.setText("75");
                        ideal3.setText("80");
                        ideal4.setText("70");
                        ideal5.setText("80");
                        mostrar_resultados();
                        break;

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){
            nombre_jugador.setText(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas().getNombre_Persona()+" "+Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas().getApellidos_Persona());
        }



        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){

                    if(spinner_posiciones.getSelectedItemPosition()==0){
                        Toast.makeText(context, "Seleccion una Posicion Referencial", Toast.LENGTH_SHORT).show();
                    }else{
                        Registrar_Prueba_Psicologica(Usuario.SESION_ACTUAL.getId(),Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas().getId(),PruebaPsicologico.PRUEBA,context);
                    }

                }
            }
        });
    }
    private void Registrar_Prueba_Psicologica(final int user,final int  persona,final PruebaPsicologico prueba,final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Prueba Psicologica:");
        progressDialog.setMessage("Guardando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String id_user=String.valueOf(user);
        String id_persona=String.valueOf(persona);
        String o1=String.valueOf(Recursos_Diagnostico.LISTA_INCA1.get(0).getResultado());
        String o2=String.valueOf(Recursos_Diagnostico.LISTA_INCA1.get(1).getResultado());
        String o3=String.valueOf(Recursos_Diagnostico.LISTA_INCA1.get(2).getResultado());
        String o4=String.valueOf(Recursos_Diagnostico.LISTA_INCA1.get(3).getResultado());
        String o5=String.valueOf(Recursos_Diagnostico.LISTA_INCA2.get(0).getResultado());
        String o6=String.valueOf(Recursos_Diagnostico.LISTA_INCA2.get(1).getResultado());
        String o7=String.valueOf(Recursos_Diagnostico.LISTA_INCA3.get(0).getResultado());
        String o8=String.valueOf(Recursos_Diagnostico.LISTA_INCA3.get(1).getResultado());
        String o9=String.valueOf(Recursos_Diagnostico.LISTA_INCA3.get(2).getResultado());
        String o10=String.valueOf(Recursos_Diagnostico.LISTA_INCA3.get(3).getResultado());
        String o11=String.valueOf(Recursos_Diagnostico.LISTA_INCA3.get(4).getResultado());
        String o12=String.valueOf(Recursos_Diagnostico.LISTA_INCA3.get(5).getResultado());
        String o13=String.valueOf(Recursos_Diagnostico.LISTA_INCA4.get(0).getResultado());
        String o14=String.valueOf(Recursos_Diagnostico.LISTA_INCA4.get(1).getResultado());
        String o15=String.valueOf(Recursos_Diagnostico.LISTA_INCA5.get(0).getResultado());

        String cognitivo=String.valueOf(PruebaPsicologico.PRUEBA.getCognitivo());
        String motivacion =String.valueOf(PruebaPsicologico.PRUEBA.getMotivacion());
        String inteligencia=String.valueOf(PruebaPsicologico.PRUEBA.getInteligencia());
        String Liderazgo=String.valueOf(PruebaPsicologico.PRUEBA.getLiderazgo());
        String Autoconfianza=String.valueOf(PruebaPsicologico.PRUEBA.getAutoconfianza());
        String total_general=String.valueOf(PruebaPsicologico.PRUEBA.getTotal_general());
        String estado=String.valueOf(1);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {


                        if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){
                            Intent intent = new Intent(PruebaPsicologicaActivity.this,ListaPersonasGrupoPruebasActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            PruebaPsicologicaActivity.this.startActivity(intent);
                            Toast.makeText(context, "Prueba Psicologica Registrada con exito!", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(context, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error en Recupera codigo de usuario :"+e);
                }
            }
        };
        RegistrarPruebaPsicologica xx = new RegistrarPruebaPsicologica(id_user,id_persona,o1,o2,o3,o4,o5,o6,o7,o8,o9,o10,o11,o12,o13,o14,o15,cognitivo,motivacion,inteligencia,Liderazgo,Autoconfianza,total_general,estado, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }
    private void Llenar_Spinner() {

        posiciones=new String[8];
        posiciones[0]="-- Seleccione --";
        posiciones[1]="AR";
        posiciones[2]="DFC";
        posiciones[3]="DFL";
        posiciones[4]="MCD";
        posiciones[5]="MC - MO";
        posiciones[6]="EXT";
        posiciones[7]="DC";

        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,posiciones);
        spinner_posiciones.setAdapter(adapter_arr);


    }
    private void Creacion_Animaciones() {

        for(int i = 0; i< Recursos_Diagnostico.LISTA_VISTAS5.size(); i++){

            LinearLayout linear=findViewById(Recursos_Diagnostico.LISTA_VISTAS5.get(i).getContenedor());
            LayoutInflater inflater = LayoutInflater.from(this);

            final View view_actual = inflater.inflate(Recursos_Diagnostico.LISTA_VISTAS5.get(i).getVista(), linear, true);
            LinearLayout line=view_actual.findViewById(Recursos_Diagnostico.LISTA_VISTAS5.get(i).getArea_Accion());
            Recursos_Diagnostico.LISTA_VISTAS5.get(i).setView(line);

            LinearLayout Accion_Panel=findViewById(Recursos_Diagnostico.LISTA_VISTAS5.get(i).getPanel_Accion());
            Generar_Animacion(Recursos_Diagnostico.LISTA_VISTAS5.get(i),line,Accion_Panel);
        }

    }
    private void Generar_Animacion(final Captacion_Vista captacion_vista, final LinearLayout view_actual, LinearLayout Accion_Panel ) {

        Accion_Panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(captacion_vista.getAccion()==0){
                    scroll_prueba_psico.smoothScrollTo(0,0);
                    view_actual.setVisibility(View.VISIBLE);

                    captacion_vista.setAccion(1);
                    Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abajo);
                    view_actual.startAnimation(animation);

                    cerrar_ventanas(view_actual);
                }else if(captacion_vista.getAccion()==1){
                    scroll_prueba_psico.smoothScrollTo(0,0);
                    captacion_vista.setAccion(0);
                    Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.arriba);
                    view_actual.startAnimation(animation);
                    view_actual.setVisibility(View.GONE);
                }

            }
        });
    }
    private void cerrar_ventanas(LinearLayout view_actual) {
        for(int i = 0; i< Recursos_Diagnostico.LISTA_VISTAS5.size(); i++){
            if(Recursos_Diagnostico.LISTA_VISTAS5.get(i).getView()==view_actual){

            }else{
                Recursos_Diagnostico.LISTA_VISTAS5.get(i).getView().setVisibility(View.GONE);
            }
        }
    }
    private void Seteo_RadioGroups() {

        for(int i = 0; i< Recursos_Diagnostico.LISTA_INCA1.size(); i++){

            Generar_Funcion(i, Recursos_Diagnostico.LISTA_INCA1.get(i));
        }
        for(int i = 0; i< Recursos_Diagnostico.LISTA_INCA2.size(); i++){

            Generar_Funcion(i, Recursos_Diagnostico.LISTA_INCA2.get(i));

        }
        for(int i = 0; i< Recursos_Diagnostico.LISTA_INCA3.size(); i++){

            Generar_Funcion(i, Recursos_Diagnostico.LISTA_INCA3.get(i));
        }
        for(int i = 0; i< Recursos_Diagnostico.LISTA_INCA4.size(); i++){

            Generar_Funcion(i, Recursos_Diagnostico.LISTA_INCA4.get(i));
        }

        for(int i = 0; i< Recursos_Diagnostico.LISTA_INCA5.size(); i++){

            Generar_Funcion(i, Recursos_Diagnostico.LISTA_INCA5.get(i));
        }


    }
    private void Generar_Funcion(int v, final Captacion_funcional captacion_funcional) {
         int n=v+1;
         TextView textView=findViewById(captacion_funcional.getTextView());
         textView.setText(n+".- "+captacion_funcional.getOpcion());

        RadioGroup grupo=findViewById(captacion_funcional.getGroupRadio());
        grupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i== captacion_funcional.getRadio1()){
                    captacion_funcional.setResultado(0);
                    //Refrescar_Totales();
                    mostrar_resultados();
                }else if(i == captacion_funcional.getRadio2()){
                    captacion_funcional.setResultado(1);
                    //Refrescar_Totales();
                    mostrar_resultados();
                }
                else if(i == captacion_funcional.getRadio3()){
                    captacion_funcional.setResultado(2);
                    // Refrescar_Totales();
                    mostrar_resultados();
                }else if(i == captacion_funcional.getRadio4()){
                    captacion_funcional.setResultado(3);
                    //  Refrescar_Totales();
                    mostrar_resultados();
                }
            }
        });
    }
    private void mostrar_resultados() {

        int temp=0;
        for(int i=0;i<Recursos_Diagnostico.LISTA_INCA1.size();i++){
            temp=temp+Recursos_Diagnostico.LISTA_INCA1.get(i).getResultado();
            Resultado.add(Recursos_Diagnostico.LISTA_INCA1.get(i).getResultado());

        }
        double pr1=temp/Recursos_Diagnostico.LISTA_INCA1.size();
        PruebaPsicologico.PRUEBA.setProm1(pr1);

        int temp2=0;
        for(int i=0;i<Recursos_Diagnostico.LISTA_INCA2.size();i++){
            temp2=temp2+Recursos_Diagnostico.LISTA_INCA2.get(i).getResultado();
            Resultado.add(Recursos_Diagnostico.LISTA_INCA2.get(i).getResultado());
        }
        double pr2=temp2/Recursos_Diagnostico.LISTA_INCA2.size();
        PruebaPsicologico.PRUEBA.setProm2(pr2);


        int temp3=0;
        for(int i=0;i<Recursos_Diagnostico.LISTA_INCA3.size();i++){
            temp3=temp3+Recursos_Diagnostico.LISTA_INCA3.get(i).getResultado();
            Resultado.add(Recursos_Diagnostico.LISTA_INCA3.get(i).getResultado());
        }
        double pr3=temp3/Recursos_Diagnostico.LISTA_INCA3.size();
        PruebaPsicologico.PRUEBA.setProm3(pr3);


        int temp4=0;
        for(int i=0;i<Recursos_Diagnostico.LISTA_INCA4.size();i++){
            temp4=temp4+Recursos_Diagnostico.LISTA_INCA4.get(i).getResultado();
            Resultado.add(Recursos_Diagnostico.LISTA_INCA4.get(i).getResultado());
        }
        double pr4=temp4/Recursos_Diagnostico.LISTA_INCA4.size();
        PruebaPsicologico.PRUEBA.setProm4(pr4);



        int temp5=0;
        for(int i=0;i<Recursos_Diagnostico.LISTA_INCA5.size();i++){
            temp5=temp5+Recursos_Diagnostico.LISTA_INCA5.get(i).getResultado();
            Resultado.add(Recursos_Diagnostico.LISTA_INCA5.get(i).getResultado());
        }
        double pr5=temp5/Recursos_Diagnostico.LISTA_INCA5.size();
        PruebaPsicologico.PRUEBA.setProm5(pr5);


        PruebaPsicologico.PRUEBA.setResultado(Resultado);



        // cognitivo real
        double cog_esperado= Double.parseDouble(ideal1.getText().toString().trim());
        double res=cog_esperado/3;
        double cognitivo=PruebaPsicologico.PRUEBA.getProm1()*res;

        double d1=Math.floor(cognitivo);
        Integer mot1=(int)d1;

        PruebaPsicologico.PRUEBA.setCognitivo(mot1);
        real1.setText(String.valueOf(mot1));


        // motivacion real

        double moti=Double.parseDouble(ideal2.getText().toString().trim());
        double res2=moti/3;
        double motivacion=PruebaPsicologico.PRUEBA.getProm2()*res2;

        double d2=Math.floor(motivacion);
        Integer mot2=(int)d2;

        PruebaPsicologico.PRUEBA.setMotivacion(mot2);
        real2.setText(String.valueOf(mot2));


        //inteligencia emocional
        double inte=Double.parseDouble(ideal3.getText().toString().trim());
        double res3=inte/3;
        double inteligencia=PruebaPsicologico.PRUEBA.getProm3()*res3;

        double d3=Math.floor(inteligencia);
        Integer mot3=(int)d3;
        PruebaPsicologico.PRUEBA.setInteligencia(mot3);
        real3.setText(String.valueOf(mot3));

        //Liderazgo

        double li=Double.parseDouble(ideal4.getText().toString().trim());
        double res4=li/3;
        double liderazgo=PruebaPsicologico.PRUEBA.getProm4()*res4;
        double d4=Math.floor(liderazgo);
        Integer mot4=(int)d4;

        PruebaPsicologico.PRUEBA.setLiderazgo(mot4);
        real4.setText(String.valueOf(mot4));



        //Autoconfianza

        double au=Double.parseDouble(ideal5.getText().toString().trim());
        double res5=au/3;
        double autoconfianza=PruebaPsicologico.PRUEBA.getProm5()*res5;

        double d5=Math.floor(autoconfianza);
        Integer mot5=(int)d5;

        PruebaPsicologico.PRUEBA.setAutoconfianza(mot5);
        real5.setText(String.valueOf(mot5));



        real1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(String.valueOf(s).length()!=0){

                    Calcular_total();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        real2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(String.valueOf(s).length()!=0){

                    Calcular_total();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        real3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(String.valueOf(s).length()!=0){

                    Calcular_total();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        real4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(String.valueOf(s).length()!=0){

                    Calcular_total();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        real5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(String.valueOf(s).length()!=0){

                    Calcular_total();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Calcular_total();


    }
    private void Calcular_total() {

        double promedio=(PruebaPsicologico.PRUEBA.getCognitivo()+PruebaPsicologico.PRUEBA.getMotivacion()+PruebaPsicologico.PRUEBA.getInteligencia()+PruebaPsicologico.PRUEBA.getLiderazgo()+PruebaPsicologico.PRUEBA.getAutoconfianza())/5;

        double dto=Math.floor(promedio);
        Integer motto=(int)dto;

        PruebaPsicologico.PRUEBA.setTotal_general(motto);
        total_general.setText(String.valueOf(motto)+" Ptos");


    }
    public void debug(String d ){
        System.out.println(d);
    }
    public void onBackPressed() {

        if(Usuario.SESION_ACTUAL.getPersona_metodologia_pruebas()!=null){
            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
            builder.setTitle("Metodologia")
                    .setMessage("¿Desea salir de la Evaluaciòn Psicologica?")
                    .setPositiveButton("SI",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(PruebaPsicologicaActivity.this,ListaPersonasGrupoPruebasActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    PruebaPsicologicaActivity.this.startActivity(intent);

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
