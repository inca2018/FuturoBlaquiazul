package org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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




        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nombre_evento.getText().toString().length()!=0 && detalle_evento.getText().toString().length()!=0 && spinner_categorias.getSelectedItemPosition()!=0){

                    final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                    builder.setTitle("Creación de Evento")
                            .setMessage("Desea Crear Nuevo Evento en la siguiente Ubicación:\n\n"+"- "+GestionUbigeo.ESTADISTICO_UBIGEO.getUbigeo_descripcion())

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
                    Toast.makeText(context, "Complete los datos necesarios para crear un nuevo evento", Toast.LENGTH_SHORT).show();
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

        Listar_Plantel(context);
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
        String estado2=String.valueOf(2);


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

        RegistrarEventoNuevo recuperarDepa = new RegistrarEventoNuevo(nombre,detalle,departamento,provincia,distrito,id_user,id_plantel,foto,estado,estado2,responseListener);
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
        EventoEstadistico.EVENTO_CREACION.Vaciar_Temporal();
    }
}
