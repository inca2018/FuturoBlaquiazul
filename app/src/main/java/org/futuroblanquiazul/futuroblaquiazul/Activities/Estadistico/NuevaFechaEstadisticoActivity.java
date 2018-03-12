package org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.EventoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.FechaEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Oponente;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarCategorias;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarOponentes;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarEventoNuevo;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarFechaNueva;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NuevaFechaEstadisticoActivity extends AppCompatActivity {

    Spinner spinner_oponente;
    String[] lista_oponentes_nombres;
    List<Oponente> lista_Oponentes;
    TextView observaciones;
    ImageView fecha_Realizar;
    TextView texto_Fecha_Realizar;
    Button nuevo_Fecha;
    ProgressDialog progressDialog;

    Context context;
    final Calendar c = Calendar.getInstance();
    Calendar date_minimo=Calendar.getInstance();
    Calendar date_maximo=Calendar.getInstance();
    private int pYear;
    private int pMonth;
    private int pDay;
    long minimo,maximo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_fecha_estadistico);
        context=this;
        lista_Oponentes=new ArrayList<>();
        spinner_oponente=findViewById(R.id.nuevo_fecha_spinner);
        observaciones=findViewById(R.id.nuevo_fecha_observaciones);
        fecha_Realizar=findViewById(R.id.nuevo_fecha_fecha);
        texto_Fecha_Realizar=findViewById(R.id.nuevo_fecha_texto);
        nuevo_Fecha=findViewById(R.id.nuevo_fecha_crear);

        pYear = c.get(Calendar.YEAR);
        pMonth = c.get(Calendar.MONTH);
        pDay = c.get(Calendar.DAY_OF_MONTH);

        if(EventoEstadistico.EVENTO_TEMP!=null){
            minimo=Recuperar_minimo(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getInicio_ano(),EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getInicio_mes(),EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getInicio_dia());
            maximo=Recuperar_maximo(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getFin_ano(),EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getFin_mes(),EventoEstadistico.EVENTO_TEMP.getEvento_Temporal().getFin_dia());
        }

        Listar_Oponentes(context);
        spinner_oponente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                for(int x=0;x<lista_Oponentes.size();x++){
                    if(lista_Oponentes.get(x).getNombre_Oponente().equalsIgnoreCase(String.valueOf(item))){
                        FechaEstadistico.FECHA_ESTADISTICO_CREACION.setOponente(lista_Oponentes.get(x));
                    }
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        observaciones.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(String.valueOf(s).toString().length()!=0){
                    FechaEstadistico.FECHA_ESTADISTICO_CREACION.setObservacion(String.valueOf(s).toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fecha_Realizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog xx = onCreateDialog();
                xx.show();
            }
        });


        if(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal()!=null){
            FechaEstadistico.FECHA_ESTADISTICO_CREACION.setEvento(EventoEstadistico.EVENTO_TEMP.getEvento_Temporal());
        }



        nuevo_Fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(observaciones.getText().toString().length()!=0){
                    if(texto_Fecha_Realizar.getText().toString().length()!=0){
                        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                        builder.setTitle("Creación de Fecha")
                                .setMessage("Desea Crear Nuevo Fecha con los Siguientes Datos:\n\n"+
                                        "- Oponente:"+"\n\n"+FechaEstadistico.FECHA_ESTADISTICO_CREACION.getOponente().getNombre_Oponente()+"\n\n"+
                                        "- Fecha:"+"\n\n"+FechaEstadistico.FECHA_ESTADISTICO_CREACION.getFecha_Realizacion()+"\n\n"+
                                        "- Datos Adicionales :"+"\n\n"+FechaEstadistico.FECHA_ESTADISTICO_CREACION.getObservacion())
                                .setPositiveButton("SI",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                CrearFechaNuevo(FechaEstadistico.FECHA_ESTADISTICO_CREACION,context);

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
                        Toast.makeText(context, "Debe Ingresar Fecha de Realización", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    observaciones.setError("Ingrese Observaciones");
                }
            }
        });
    }

    private void CrearFechaNuevo(FechaEstadistico fechaEstadisticoCreacion,final Context context) {


        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Nueva Fecha Estadistico:");
        progressDialog.setMessage("Guardando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

       String id_evento=String.valueOf(fechaEstadisticoCreacion.getEvento().getId());
       String id_oponente=String.valueOf(fechaEstadisticoCreacion.getOponente().getId());
       String fecha_realizar=String.valueOf(fechaEstadisticoCreacion.getFecha_Realizacion());
       String observaciones=String.valueOf(fechaEstadisticoCreacion.getObservacion());
       String estado=String.valueOf(1);
       String id_user=String.valueOf(Usuario.SESION_ACTUAL.getId());



        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        Intent intent=new Intent(context,ListaEventosEstadisticosActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);

                        limpiar_Variable();
                        Toast.makeText(context, "Fecha Nuevo Guardado Exitosamente", Toast.LENGTH_SHORT).show();


                    } else {

                        Toast.makeText(context, "Error de conexion Registro fecha nueva", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al registro fecha nueva :"+e);
                }
            }
        };

        RegistrarFechaNueva xx = new RegistrarFechaNueva(id_evento,id_user,id_oponente,fecha_realizar,observaciones,estado,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }

    private void limpiar_Variable() {

        observaciones.setText("");
    }

    private void Listar_Oponentes(final Context context) {
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        JSONArray departamentos=jsonResponse.getJSONArray("oponentes");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Oponente temp=new Oponente();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Oponente(objeto.getString("NOMBRE_OPONENTE"));
                            lista_Oponentes.add(temp);
                        }

                        //LLENAR SPINNER DEPARTAMENTOS
                        lista_oponentes_nombres=new String[lista_Oponentes.size()];
                        for(int i=0;i<lista_Oponentes.size();i++){
                            lista_oponentes_nombres[i]=lista_Oponentes.get(i).getNombre_Oponente();
                        }
                        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,lista_oponentes_nombres);

                        spinner_oponente.setAdapter(adapter_arr);

                    } else {

                        Toast.makeText(context, "Error de conexion Recuperar Oponentes", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar Oponentes :"+e);
                }
            }
        };

        RecuperarOponentes xx = new RecuperarOponentes(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    public Dialog onCreateDialog() {

                DatePickerDialog.OnDateSetListener pDateSetListener = new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        pYear = year;
                        pMonth = monthOfYear;
                        pDay = dayOfMonth;

                        updateTextLabel();

                    }
                };

        DatePickerDialog dialog = new DatePickerDialog(this, pDateSetListener, pYear, pMonth, pDay);
        dialog.getDatePicker().setMinDate(minimo);
        dialog.getDatePicker().setMaxDate(maximo);
        return dialog;

    }
    public long Recuperar_minimo(int year,int month,int day){
        long d=0;

        date_minimo.set(Calendar.YEAR,year);
        date_minimo.set(Calendar.MONTH,(month-1));
        date_minimo.set(Calendar.DAY_OF_MONTH,day);

        d=date_minimo.getTimeInMillis();
        return d;
    }
    public long Recuperar_maximo(int year,int month,int day){
        long d=0;

        date_maximo.set(Calendar.YEAR,year);
        date_maximo.set(Calendar.MONTH,(month-1));
        date_maximo.set(Calendar.DAY_OF_MONTH,day);

        d=date_maximo.getTimeInMillis();
        return d;
    }
    private void updateTextLabel(){
        String meso="";
        String diao="";
        int mes =pMonth+1;
        int dia=pDay;
        int anoo=pYear;
        if(mes<10){
            meso="0"+mes;
        }else{
            meso=""+mes;
        }
        if(dia<10){
            diao="0"+dia;
        }else{
            diao=""+dia;
        }

        String fecha_inicio=anoo+"-"+meso+"-"+diao;
        FechaEstadistico.FECHA_ESTADISTICO_CREACION.setFecha_Realizacion(fecha_inicio);
        texto_Fecha_Realizar.setText(diao+"/"+meso+"/"+anoo);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(context,ListaFechasEstadisticosActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);


    }
}
