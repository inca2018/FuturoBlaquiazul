package org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.BarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActualizarEventoBarrio;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarDepartamentos;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarDistritos;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarProvincias;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarEventoBarrio;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarPersona;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EdicionBarrioIntimoActivity extends AppCompatActivity {
    EditText nom_evento;
    EditText desc_evento;
    Spinner departamento;
    Spinner provincias;
    Spinner distritos;
    ImageView icono_fecha;
    EditText text_fecha;
    Button guardar;
    Context context;
    String[] spinner_departamentos,spinner_provincias,spinner_distritos;
    List<Unidad_Territorial> DepartamentosLista,ProvinciasLista,DistritoLista;

    boolean departamento_accion=false;
    boolean provincia_accion=false;
    boolean distrito_accion=false;
    boolean actualizar_accion=false;
    int pos_depa;
    int pos_prov;
    int pos_dis;
    String fecha_realizar_temporal;
    ProgressDialog progressDialog;
    int ano,mes,dia;
    Calendar dateTime = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_barrio_intimo);
        context=this;
        nom_evento=findViewById(R.id.mant_barrio_nom);
        desc_evento=findViewById(R.id.mant_barrio_desc);
        departamento=findViewById(R.id.mant_barrio_depa);
        provincias=findViewById(R.id.mant_barrio_prov);
        distritos=findViewById(R.id.mant_barrio_dist);
        icono_fecha=findViewById(R.id.mant_barrio_icon_f);
        text_fecha=findViewById(R.id.mant_barrio_text_f);
        guardar=findViewById(R.id.mant_barrio_grabar);
        DepartamentosLista=new ArrayList<>();
        ProvinciasLista=new ArrayList<>();
        DistritoLista=new ArrayList<>();

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Validar_Datos();
            }
        });

        if(Recursos_Mantenimientos.TEMP.getEvento_temporal()!=null){
            debug("ENTRO A ACTUALIZAR");
            actualizar_accion=true;

        }else{
            debug("ENTRO A NUEVO");
            ano=dateTime.get(Calendar.YEAR);
            mes=dateTime.get(Calendar.MONTH);
            dia=dateTime.get(Calendar.DAY_OF_MONTH);
        }
        Listar_DepartamentosUpdate(context);
        departamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Object item = adapterView.getItemAtPosition(i);
                for(int x=0;x<DepartamentosLista.size();x++){
                    if(DepartamentosLista.get(x).getDescripcion().equalsIgnoreCase(String.valueOf(item))){
                        Recursos_Mantenimientos.TEMP.setDepartamento(DepartamentosLista.get(x));
                    }
                }

                if(departamento_accion==true){
                    debug("PASO DEPARTAMENTO NUEVO CASO TRUE");
                }else{
                    debug("ENTRO A LISTAR PROVINCIAS");
                    ProvinciasLista.clear();
                    DistritoLista.clear();
                    Listar_Provincias(Recursos_Mantenimientos.TEMP.getDepartamento().getCodigo(),context);

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        provincias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                debug("ENTRO A ONCHANGE PROVINCIA CON CLICK");
                Object item = adapterView.getItemAtPosition(i);
                for(int x=0;x<ProvinciasLista.size();x++){
                    if(ProvinciasLista.get(x).getDescripcion().equalsIgnoreCase(String.valueOf(item))){
                        Recursos_Mantenimientos.TEMP.setProvincia(ProvinciasLista.get(x));
                    }
                }


                if(provincia_accion==true){
                    debug("ENTRO A PROVINCIA ACTUALIZAR");
                }else{
                    debug("ENTRO A LISTAR DISTRITOS");
                    DistritoLista.clear();
                    Listar_Distritos(Recursos_Mantenimientos.TEMP.getProvincia().getCodigo(),context);

                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        distritos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                debug("ENTRO A ONCHANGE DISTRITO");

                Object item = adapterView.getItemAtPosition(i);
                for(int x=0;x<DistritoLista.size();x++){
                    if(DistritoLista.get(x).getDescripcion().equalsIgnoreCase(String.valueOf(item))){
                        Recursos_Mantenimientos.TEMP.setDistrito(DistritoLista.get(x));
                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        icono_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });

    }

    private void Validar_Datos() {
        if(nom_evento.getText().toString().length()!=0){
            if(text_fecha.getText().toString().length()!=0){

                Recuperar_Datos();
            }else{
                Toast.makeText(context, "Seleccione Fecha a realizar", Toast.LENGTH_SHORT).show();
            }
        }else{
            nom_evento.setError("Campo Obligatorio");
        }

    }
    private void Recuperar_Datos() {

           if(Recursos_Mantenimientos.TEMP.getEvento_temporal()!=null){

               BarrioIntimo temp=new BarrioIntimo();
               temp.setNombreEvento(nom_evento.getText().toString());
               temp.setDescripcion(desc_evento.getText().toString());
               temp.setDepartamento(Recursos_Mantenimientos.TEMP.getDepartamento());
               temp.setProvincia(Recursos_Mantenimientos.TEMP.getProvincia());
               temp.setDistrito(Recursos_Mantenimientos.TEMP.getDistrito());
               temp.setFechaRealizacion(fecha_realizar_temporal);

               Actualizar_Evento(Recursos_Mantenimientos.TEMP.getEvento_temporal().getId(),temp,context);

           }else{
               BarrioIntimo temp=new BarrioIntimo();
               temp.setNombreEvento(nom_evento.getText().toString());
               temp.setDescripcion(desc_evento.getText().toString());
               temp.setDepartamento(Recursos_Mantenimientos.TEMP.getDepartamento());
               temp.setProvincia(Recursos_Mantenimientos.TEMP.getProvincia());
               temp.setDistrito(Recursos_Mantenimientos.TEMP.getDistrito());

               temp.setFechaRealizacion(fecha_realizar_temporal);
               Registrar_Nuevo_Evento(Usuario.SESION_ACTUAL.getId(),temp,context);
           }
    }
    private void Actualizar_Evento(int id_eve, BarrioIntimo temp, final Context context) {

        debug("ENTRO A REGISTRAR NUEVO EVENTO");
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Barrio Intimo:");
        progressDialog.setMessage("Registrando Evento...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String id_evento=String.valueOf(id_eve);

        String nom_evento=temp.getNombreEvento();
        String desc_evento=temp.getDescripcion();
        String id_depa=String.valueOf(temp.getDepartamento().getCodigo());
        String id_prov=String.valueOf(temp.getProvincia().getCodigo());
        String id_dis=String.valueOf(temp.getDistrito().getCodigo());
        String f_realizar=temp.getFechaRealizacion();

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Intent intent=new Intent(context,MantenimientoBarrioIntimoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        fecha_realizar_temporal="";
                        Recursos_Mantenimientos.TEMP.setDepartamento(null);
                        Recursos_Mantenimientos.TEMP.setProvincia(null);
                        Recursos_Mantenimientos.TEMP.setDistrito(null);
                        actualizar_accion=false;
                        Toast.makeText(context, "Evento Actualizado Exitosamente", Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();

                        Toast.makeText(context, "No se pudo registrar Evento,consulte con el administrador del sistema", Toast.LENGTH_SHORT).show();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al registrar Jugador :"+e);
                }
            }
        };

        ActualizarEventoBarrio xx = new ActualizarEventoBarrio(id_evento,nom_evento,desc_evento,id_depa,id_prov,id_dis,f_realizar ,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }
    private void Registrar_Nuevo_Evento(int id_u, BarrioIntimo temp,final Context context) {

        debug("ENTRO A REGISTRAR NUEVO EVENTO");
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Barrio Intimo:");
        progressDialog.setMessage("Registrando Evento...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String id_user=String.valueOf(id_u);
        String nom_evento=temp.getNombreEvento();
        String desc_evento=temp.getDescripcion();
        String id_depa=String.valueOf(temp.getDepartamento().getCodigo());
        String id_prov=String.valueOf(temp.getProvincia().getCodigo());
        String id_dis=String.valueOf(temp.getDistrito().getCodigo());
        String f_realizar=temp.getFechaRealizacion();
        String estado=String.valueOf(1);

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Intent intent=new Intent(context,MantenimientoBarrioIntimoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        fecha_realizar_temporal="";
                        Recursos_Mantenimientos.TEMP.setDepartamento(null);
                        Recursos_Mantenimientos.TEMP.setProvincia(null);
                        Recursos_Mantenimientos.TEMP.setDistrito(null);
                        Toast.makeText(context, "Evento Nuevo Guardado Exitosamente", Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();

                        Toast.makeText(context, "No se pudo registrar Evento,consulte con el administrador del sistema", Toast.LENGTH_SHORT).show();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al registrar Jugador :"+e);
                }
            }
        };

        RegistrarEventoBarrio xx = new RegistrarEventoBarrio(id_user,nom_evento,desc_evento,id_depa,id_prov,id_dis,f_realizar,estado ,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }
    private void Listar_Distritos(int codigo,final Context context) {

        String id_prov=String.valueOf(codigo);

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray xx=jsonResponse.getJSONArray("distrito");
                        for(int i=0;i<xx.length();i++){
                            JSONObject objeto= xx.getJSONObject(i);
                            Unidad_Territorial temp=new Unidad_Territorial();
                            temp.setCodigo(objeto.getInt("idDist"));
                            temp.setDescripcion(objeto.getString("distrito"));
                            DistritoLista.add(temp);
                        }
                        spinner_distritos=new String[DistritoLista.size()];
                        for(int i=0;i<DistritoLista.size();i++){
                            spinner_distritos[i]=DistritoLista.get(i).getDescripcion();
                        }
                        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,spinner_distritos);
                        distritos.setAdapter(adapter_arr);
                        System.out.println("DISTRITOS NEW");

                    } else {

                        Toast.makeText(context, "Error de conexion distritos", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar distritos :"+e);
                }
            }
        };

        RecuperarDistritos recuperarDist = new RecuperarDistritos(id_prov,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(recuperarDist);
    }
    private void Listar_Provincias(int codigo,final Context context) {
        String id_depa=String.valueOf(codigo);

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray xx=jsonResponse.getJSONArray("provincia");
                        for(int i=0;i<xx.length();i++){
                            JSONObject objeto= xx.getJSONObject(i);
                            Unidad_Territorial temp=new Unidad_Territorial();
                            temp.setCodigo(objeto.getInt("idProv"));
                            temp.setDescripcion(objeto.getString("provincia"));
                            ProvinciasLista.add(temp);
                        }
                        spinner_provincias=new String[ProvinciasLista.size()];
                        for(int i=0;i<ProvinciasLista.size();i++){
                            spinner_provincias[i]=ProvinciasLista.get(i).getDescripcion();
                        }
                        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,spinner_provincias);
                        provincias.setAdapter(adapter_arr);

                        System.out.println("PROVINCIAS NEW");
                    } else {

                        Toast.makeText(context, "Error de conexion provincias", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar provincias :"+e);
                }
            }
        };

        RecuperarProvincias recuperarProv = new RecuperarProvincias(id_depa,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(recuperarProv);

    }
    private void Listar_Departamentos(final Context context) {
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        JSONArray xx=jsonResponse.getJSONArray("departamento");
                        for(int i=0;i<xx.length();i++){
                            JSONObject objeto= xx.getJSONObject(i);
                            Unidad_Territorial temp=new Unidad_Territorial();
                            temp.setCodigo(objeto.getInt("idDepa"));
                            temp.setDescripcion(objeto.getString("departamento"));
                            DepartamentosLista.add(temp);
                            System.out.println("id_Depa="+temp.getCodigo()+"  Descripcion_depa="+temp.getDescripcion());
                        }


                        spinner_departamentos=new String[DepartamentosLista.size()];
                        for(int i=0;i<DepartamentosLista.size();i++){
                            spinner_departamentos[i]=DepartamentosLista.get(i).getDescripcion();
                        }
                        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,spinner_departamentos);
                        departamento.setAdapter(adapter_arr);

                        System.out.println("DEPARTAMENTOS NEW");
                    } else {

                        Toast.makeText(context, "Error de conexion departamento ", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarDepartamentos recuperarDepa = new RecuperarDepartamentos(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(recuperarDepa);


    }
    private void Listar_DepartamentosUpdate(final Context context) {
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    debug("LISTANDO DEPARTAMENTOS--------------------------");
                    if (success) {
                        departamento_accion=true;
                        JSONArray xx=jsonResponse.getJSONArray("departamento");
                        for(int i=0;i<xx.length();i++){
                            JSONObject objeto= xx.getJSONObject(i);
                            Unidad_Territorial temp=new Unidad_Territorial();
                            temp.setCodigo(objeto.getInt("idDepa"));
                            temp.setDescripcion(objeto.getString("departamento"));
                            DepartamentosLista.add(temp);
                            debug("Departamento : "+temp.getDescripcion());
                        }


                        spinner_departamentos=new String[DepartamentosLista.size()];
                        for(int i=0;i<DepartamentosLista.size();i++){
                            spinner_departamentos[i]=DepartamentosLista.get(i).getDescripcion();
                        }
                        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,spinner_departamentos);
                        departamento.setAdapter(adapter_arr);


                        if(actualizar_accion){
                            for(int i=0;i<spinner_departamentos.length;i++){
                                if(spinner_departamentos[i].equalsIgnoreCase(Recursos_Mantenimientos.TEMP.getEvento_temporal().getDepartamento().getDescripcion())){
                                    pos_depa=i;
                                }
                            }

                            departamento.setSelection(pos_depa);
                            Listar_ProvinciasUpdate(Recursos_Mantenimientos.TEMP.getEvento_temporal().getDepartamento().getCodigo(),context);

                        }else{
                            for(int i=0;i<spinner_departamentos.length;i++){
                                if(spinner_departamentos[i].equalsIgnoreCase("LIMA")){
                                    pos_depa=i;
                                }
                            }


                            departamento.setSelection(pos_depa);

                            Listar_ProvinciasUpdate(15,context);
                        }


                    } else {

                        Toast.makeText(context, "Error de conexion Departamento Update", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos Update :"+e);
                }
            }
        };

        RecuperarDepartamentos recuperarDepa = new RecuperarDepartamentos(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(recuperarDepa);


    }
    private void Listar_ProvinciasUpdate(int codigo,final Context context) {
        String id_depa=String.valueOf(codigo);

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        provincia_accion=true;

                        JSONArray xx=jsonResponse.getJSONArray("provincia");
                        debug("LISTANDO PROVINCIAS--------------------------");
                        for(int i=0;i<xx.length();i++){

                            JSONObject objeto= xx.getJSONObject(i);
                            Unidad_Territorial temp=new Unidad_Territorial();
                            temp.setCodigo(objeto.getInt("idProv"));
                            temp.setDescripcion(objeto.getString("provincia"));
                            ProvinciasLista.add(temp);
                            debug("Provincias: "+temp.getDescripcion());
                        }
                        spinner_provincias=new String[ProvinciasLista.size()];
                        for(int i=0;i<ProvinciasLista.size();i++){
                            spinner_provincias[i]=ProvinciasLista.get(i).getDescripcion();
                        }
                        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,spinner_provincias);
                        provincias.setAdapter(adapter_arr);



                        if(actualizar_accion){
                            for(int i=0;i<spinner_provincias.length;i++){
                                if(spinner_provincias[i].equalsIgnoreCase(Recursos_Mantenimientos.TEMP.getEvento_temporal().getProvincia().getDescripcion())){
                                    pos_prov=i;
                                }
                            }
                            provincias.setSelection(pos_prov);
                            Listar_DistritosUpdate(Recursos_Mantenimientos.TEMP.getEvento_temporal().getProvincia().getCodigo(),context);

                        }else{
                            for(int i=0;i<spinner_provincias.length;i++){
                                if(spinner_provincias[i].equalsIgnoreCase("LIMA")){
                                    pos_prov=i;
                                }
                            }

                            provincias.setSelection(pos_prov);
                            Listar_DistritosUpdate(127,context);

                        }


                    } else {

                        Toast.makeText(context, "Error de conexion Provincia Update", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar provincias Update :"+e);
                }
            }
        };

        RecuperarProvincias recuperarProv = new RecuperarProvincias(id_depa,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(recuperarProv);

    }
    private void Listar_DistritosUpdate(int codigo,final Context context) {

        String id_prov=String.valueOf(codigo);

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        distrito_accion=true;
                        JSONArray xx=jsonResponse.getJSONArray("distrito");
                        debug("LISTANDO DISTRITOS--------------------------");
                        for(int i=0;i<xx.length();i++){
                            JSONObject objeto= xx.getJSONObject(i);
                            Unidad_Territorial temp=new Unidad_Territorial();
                            temp.setCodigo(objeto.getInt("idDist"));
                            temp.setDescripcion(objeto.getString("distrito"));
                            DistritoLista.add(temp);
                            debug("Distritos: "+temp.getDescripcion());
                        }
                        spinner_distritos=new String[DistritoLista.size()];
                        for(int i=0;i<DistritoLista.size();i++){
                            spinner_distritos[i]=DistritoLista.get(i).getDescripcion();
                        }
                        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,spinner_distritos);
                        distritos.setAdapter(adapter_arr);


                        if(actualizar_accion){
                            for(int i=0;i<spinner_distritos.length;i++){
                                if(spinner_distritos[i].equalsIgnoreCase( Recursos_Mantenimientos.TEMP.getEvento_temporal().getDistrito().getDescripcion())){
                                    pos_dis=i;
                                }
                            }

                            distritos.setSelection(pos_dis);
                        }else{
                            for(int i=0;i<spinner_distritos.length;i++){
                                if(spinner_distritos[i].equalsIgnoreCase("LIMA")){
                                    pos_dis=i;
                                }
                            }

                            distritos.setSelection(pos_dis);
                        }

                        departamento_accion=false;
                        provincia_accion=false;
                        distrito_accion=false;


                        if(actualizar_accion){
                            Mostrar_Informacion();
                        }

                    } else {

                        Toast.makeText(context, "Error de conexion Distrito Update", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar distritos Update :"+e);
                }
            }
        };

        RecuperarDistritos recuperarDist = new RecuperarDistritos(id_prov,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(recuperarDist);
    }
    private void Mostrar_Informacion() {
        nom_evento.setText(Recursos_Mantenimientos.TEMP.getEvento_temporal().getNombreEvento());
        desc_evento.setText(Recursos_Mantenimientos.TEMP.getEvento_temporal().getDescripcion());
        text_fecha.setText(Recursos_Mantenimientos.TEMP.getEvento_temporal().getFechaRealizacion());
        ano=Recursos_Mantenimientos.TEMP.getEvento_temporal().getAno();
        mes=Recursos_Mantenimientos.TEMP.getEvento_temporal().getMes();
        dia=Recursos_Mantenimientos.TEMP.getEvento_temporal().getDia();

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
    private void updateDate(){
        new DatePickerDialog(this, d1,ano,mes,dia).show();
    }

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
        fecha_realizar_temporal=anoo+"-"+meso+"-"+diao;
        text_fecha.setText(fecha_realizar_temporal);

        }



    public void debug(String d){
        System.out.println(d);
    }

    public void onBackPressed() {

        Recursos_Mantenimientos.TEMP.setEvento_temporal(null);
        Intent intent=new Intent(EdicionBarrioIntimoActivity.this,MantenimientoBarrioIntimoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        EdicionBarrioIntimoActivity.this.startActivity(intent);

    }
}
