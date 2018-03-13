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
import android.util.EventLog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Entity.EventoEstadistico;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarCategorias;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarDepartamentos;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarEventoNuevo;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Registro_Postulante;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NuevoEventoEstadisticoActivity extends AppCompatActivity {

    EditText nombre_evento,detalle_evento;
    Spinner spinner_categorias;
    ImageView foto;
    Button crear;
    Context context;
    ProgressDialog progressDialog;
    String [] lista_categorias_nombres;
    List<Plantel> lista_plantel;

    ImageView fecha_inicio,fecha_fin;
    TextView texto_inicio,texto_fin;
    Calendar dateTime = Calendar.getInstance();
    Calendar dateTime2 = Calendar.getInstance();
    long minimo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_evento_estadistico);
        context=this;
        nombre_evento=findViewById(R.id.nuevo_evento_nombre);
        detalle_evento=findViewById(R.id.nuevo_evento_detalle);
        spinner_categorias=findViewById(R.id.nuevo_evento_spinner);
        foto=findViewById(R.id.nuevo_evento_foto);
        crear=findViewById(R.id.nuevo_evento_crear);
        lista_plantel=new ArrayList<>();

        fecha_inicio=findViewById(R.id.nuevo_evento_fecha_inicio);
        fecha_fin=findViewById(R.id.nuevo_evento_fecha_fin);
        texto_inicio=findViewById(R.id.nuevo_evento_fecha_inicio_texto);
        texto_fin=findViewById(R.id.nuevo_evento_fecha_fin_texto);

        fecha_fin.setEnabled(false);


        fecha_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateDate();
            }
        });

        fecha_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog xx = onCreateDialog();
                xx.show();
            }
        });




       opciones();

        Listar_Plantel(context);
    }

    private void opciones() {

        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nombre_evento.getText().toString().length()!=0){
                    if(detalle_evento.getText().toString().length()!=0){


                            if(texto_inicio.getText().toString().length()!=0){
                                if(texto_fin.getText().toString().length()!=0){
                                    final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                                    builder.setTitle("Creación de Evento")
                                            .setMessage("Desea Crear Nuevo Evento en la siguiente Ubicación:\n\n"+"- "+
                                                    ""+GestionUbigeo.ESTADISTICO_UBIGEO.getUbigeo_descripcion())

                                            .setPositiveButton("SI",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                                            CrearEventoNuevo(EventoEstadistico.EVENTO_CREACION,context);

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
                                    Toast.makeText(context, "Ingrese Fecha final del Evento", Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(context, "Ingrese Fecha de Inicio del Evento", Toast.LENGTH_SHORT).show();
                            }




                    }else{
                        detalle_evento.setError("Ingrese Detalle de Evento");
                    }
                }else{

                    nombre_evento.setError("Ingrese Nombre de Evento");
                }





            }
        });




        nombre_evento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(String.valueOf(s).length()!=0){

                    EventoEstadistico.EVENTO_CREACION.setDescripcion_Nombre_evento(String.valueOf(s).trim());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        detalle_evento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(String.valueOf(s).length()!=0){

                    EventoEstadistico.EVENTO_CREACION.setDetalle_Evento(String.valueOf(s).trim());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        spinner_categorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                for(int x=0;x<lista_plantel.size();x++){
                    if(lista_plantel.get(x).getNombre_categoria().equalsIgnoreCase(String.valueOf(item))){
                        EventoEstadistico.EVENTO_CREACION.setPlantel(lista_plantel.get(x));
                    }
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void CrearEventoNuevo(final EventoEstadistico eventoCreacion,final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Nuevo Evento Estadistico:");
        progressDialog.setMessage("Guardando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String nombre=String.valueOf(eventoCreacion.getDescripcion_Nombre_evento()).toUpperCase();
        String detalle=String.valueOf(eventoCreacion.getDetalle_Evento()).toUpperCase();
        String departamento=String.valueOf(GestionUbigeo.ESTADISTICO_UBIGEO.getDepartamento().getCodigo());
        String provincia=String.valueOf(GestionUbigeo.ESTADISTICO_UBIGEO.getProvincia().getCodigo());
        String distrito=String.valueOf(GestionUbigeo.ESTADISTICO_UBIGEO.getDistrito().getCodigo());
        String id_user=String.valueOf(Usuario.SESION_ACTUAL.getId());
        String id_plantel=String.valueOf(eventoCreacion.getPlantel().getId());
        String foto="";
        String estado=String.valueOf(1);
        String estado2=String.valueOf(1);
        String fecha_inicio=String.valueOf(eventoCreacion.getFecha_Inicio());
        String fecha_fin=String.valueOf(eventoCreacion.getFecha_Fin());


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
                        Toast.makeText(context, "Evento Nuevo Guardado Exitosamente", Toast.LENGTH_SHORT).show();


                    } else {

                        Toast.makeText(context, "Error de conexion Recuperar Categorias", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar categorias :"+e);
                }
            }
        };

        RegistrarEventoNuevo recuperarDepa = new RegistrarEventoNuevo(nombre,detalle,departamento,provincia,distrito,id_user,id_plantel,foto,estado,estado2,fecha_inicio,fecha_fin,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(recuperarDepa);

    }

    private void Listar_Plantel(final Context context) {
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        JSONArray departamentos=jsonResponse.getJSONArray("categorias");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Plantel temp=new Plantel();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_categoria(objeto.getString("NOMBRE_CATEGORIA"));
                            lista_plantel.add(temp);
                        }

                        //LLENAR SPINNER DEPARTAMENTOS
                        lista_categorias_nombres=new String[lista_plantel.size()];
                        for(int i=0;i<lista_plantel.size();i++){
                            lista_categorias_nombres[i]=lista_plantel.get(i).getNombre_categoria();
                        }
                        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,lista_categorias_nombres);

                        spinner_categorias.setAdapter(adapter_arr);

                    } else {

                        Toast.makeText(context, "Error de conexion Recuperar Categorias", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar categorias :"+e);
                }
            }
        };

        RecuperarCategorias recuperarDepa = new RecuperarCategorias(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(recuperarDepa);


    }

    public void limpiar_Variable(){
        nombre_evento.setText("");
        detalle_evento.setText("");
        spinner_categorias.setSelection(0);
        texto_inicio.setText("");
        texto_fin.setText(" ");
        EventoEstadistico.EVENTO_CREACION.Vaciar_Temporal();
    }

    private void updateDate(){
         new DatePickerDialog(this, d1,dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }



    DatePickerDialog.OnDateSetListener d1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTextLabel1();


        }
    };


    private void updateTextLabel1(){
        String meso="";
        String diao="";
        int mes =dateTime.get(Calendar.MONTH)+1;
        int dia=dateTime.get(Calendar.DAY_OF_MONTH);
        int anoo=dateTime.get(Calendar.YEAR);
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
        EventoEstadistico.EVENTO_CREACION.setFecha_Inicio(fecha_inicio);
        texto_inicio.setText(diao+"/"+meso+"/"+anoo);


        fecha_fin.setEnabled(true);
        minimo=Recuperar_minimo(dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH));

    }

    private void updateTextLabel2(){
        String meso="";
        String diao="";
        int mes =dateTime2.get(Calendar.MONTH)+1;
        int dia=dateTime2.get(Calendar.DAY_OF_MONTH);
        int anoo=dateTime2.get(Calendar.YEAR);
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

        String fecha_fin=anoo+"-"+meso+"-"+diao;
        EventoEstadistico.EVENTO_CREACION.setFecha_Fin(fecha_fin);
        texto_fin.setText(diao+"/"+meso+"/"+anoo);

    }



    public Dialog onCreateDialog() {

        DatePickerDialog.OnDateSetListener pDateSetListener = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                dateTime2.set(Calendar.YEAR, year);
                dateTime2.set(Calendar.MONTH, monthOfYear);
                dateTime2.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateTextLabel2();

            }
        };

        DatePickerDialog dialog = new DatePickerDialog(this, pDateSetListener,dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(minimo);

        return dialog;

    }

    public long Recuperar_minimo(int year,int month,int day){
        long d=0;

        dateTime2.set(Calendar.YEAR,year);
        dateTime2.set(Calendar.MONTH,month);
        dateTime2.set(Calendar.DAY_OF_MONTH,day);

        d=dateTime2.getTimeInMillis();
        return d;
    }

}
