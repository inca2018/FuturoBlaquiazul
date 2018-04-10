package org.futuroblanquiazul.futuroblaquiazul.Activities.Ubigeo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Actualizar_Ubigeo;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarDepartamentos;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarDistritos;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarProvincias;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.Registrar_Ubigeo;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UbigeoActivity extends AppCompatActivity {
    Spinner departamento,provincias,distritos;
    List<Unidad_Territorial> DepartamentosLista,ProvinciasLista,DistritoLista;
    String[] spinner_departamentos,spinner_provincias,spinner_distritos;
    Context mcontext;
    int pos_depa=0,pos_prov=0,pos_dist=0;
    ProgressDialog progressDialog;
    boolean depa_accion=false,prov_accion=false,dist_accion=false;
    Button card_guardar;
    String MODULO="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubigeo);
        DepartamentosLista=new ArrayList<>();
        ProvinciasLista=new ArrayList<>();
        DistritoLista=new ArrayList<>();

        mcontext=this;

        departamento=findViewById(R.id.spinner_departamento);
        provincias=findViewById(R.id.spinner_provincia);
        distritos=findViewById(R.id.spinner_distrito);
        card_guardar=findViewById(R.id.guardar_ubigeo);
        String OPCION=getIntent().getStringExtra("TYPE");
        MODULO=getIntent().getStringExtra("MODULO");

        switch (OPCION){
            case "UPDATE":

                switch (MODULO){
                    case "1":
                        Listar_Departamentos_update(mcontext, GestionUbigeo.CAPTACION_UBIGEO);
                          activar_accion_update1();
                        break;
                    case "2":
                        Listar_Departamentos_update(mcontext,GestionUbigeo.CAPTACION_UBIGEO_MASIVO);
                        activar_accion_update2();
                        break;

                    case "3":
                        Listar_Departamentos_update(mcontext,GestionUbigeo.CAPTACION_UBIGEO_BARRIO);
                        activar_accion_update3();
                        break;

                    case "4":
                        Listar_Departamentos_update(mcontext,GestionUbigeo.ESTADISTICO_UBIGEO);
                        activar_accion_update4();
                        break;

                }

                break;
            case "NEW":
                Listar_Departamentos(mcontext);

                switch (MODULO){
                    case "1":
                        activar_accion_nuevo1();
                        break;
                    case "2":
                        activar_accion_nuevo2();
                        break;
                    case "3":
                        activar_accion_nuevo3();
                        break;
                    case "4":
                        activar_accion_nuevo4();
                        break;
                }
                break;
        }

        departamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                for(int x=0;x<DepartamentosLista.size();x++){
                    if(DepartamentosLista.get(x).getDescripcion().equalsIgnoreCase(String.valueOf(item))){
                        GestionUbigeo.UBIGEO_TEMP.setDepartamento(DepartamentosLista.get(x));
                    }
                }
                if(depa_accion==true){
                    System.out.println("TRUE DEPA CHANGE");
                }else{

                    ProvinciasLista.clear();
                    DistritoLista.clear();
                    Listar_Provincias(GestionUbigeo.UBIGEO_TEMP.getDepartamento().getCodigo(),mcontext);
                    System.out.println("FALSE DEPA CHANGE");
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        provincias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                for(int x=0;x<ProvinciasLista.size();x++){
                    if(ProvinciasLista.get(x).getDescripcion().equalsIgnoreCase(String.valueOf(item))){
                        GestionUbigeo.UBIGEO_TEMP.setProvincia(ProvinciasLista.get(x));
                    }
                }

                if(prov_accion==false){
                    DistritoLista.clear();
                    Listar_Distritos(GestionUbigeo.UBIGEO_TEMP.getProvincia().getCodigo(),mcontext);
                    System.out.println("FALSE PROVINCIA CHANGE");
                }else{
                    System.out.println("TRUE PROVINCIA CHANGE");
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        distritos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Object item = adapterView.getItemAtPosition(i);
                for(int x=0;x<DistritoLista.size();x++){
                    if(DistritoLista.get(x).getDescripcion().equalsIgnoreCase(String.valueOf(item))){
                        GestionUbigeo.UBIGEO_TEMP.setDistrito(DistritoLista.get(x));
                    }
                }

                if(dist_accion==true){
                    System.out.println("TRUE DISTRITO CHANGE");
                }else{
                    System.out.println("FALSE DISTRITO CHANGE");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }
    private void activar_accion_update1() {
        card_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(mcontext);
                progressDialog.setTitle("Actualizacion");
                progressDialog.setMessage("Actualizando Ubicación de Trabajo...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();


                Actualizar_Datos_Ubigeo(GestionUbigeo.CAPTACION_UBIGEO.getCodigo_modulo(), Usuario.SESION_ACTUAL.getId(),GestionUbigeo.UBIGEO_TEMP,mcontext);
            }
        });
    }
    private void activar_accion_update2() {
        card_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(mcontext);
                progressDialog.setTitle("Actualizacion");
                progressDialog.setMessage("Actualizando Ubicación de Trabajo...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                Actualizar_Datos_Ubigeo(GestionUbigeo.CAPTACION_UBIGEO_MASIVO.getCodigo_modulo(), Usuario.SESION_ACTUAL.getId(),GestionUbigeo.UBIGEO_TEMP,mcontext);
            }
        });
    }
    private void activar_accion_update3() {
        card_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(mcontext);
                progressDialog.setTitle("Actualizacion");
                progressDialog.setMessage("Actualizando Ubicación de Trabajo...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                Actualizar_Datos_Ubigeo(GestionUbigeo.CAPTACION_UBIGEO_BARRIO.getCodigo_modulo(), Usuario.SESION_ACTUAL.getId(),GestionUbigeo.UBIGEO_TEMP,mcontext);
            }
        });
    }
    private void activar_accion_update4() {
        card_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(mcontext);
                progressDialog.setTitle("Actualizacion");
                progressDialog.setMessage("Actualizando Ubicación de Trabajo...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                Actualizar_Datos_Ubigeo(GestionUbigeo.ESTADISTICO_UBIGEO.getCodigo_modulo(), Usuario.SESION_ACTUAL.getId(),GestionUbigeo.UBIGEO_TEMP,mcontext);
            }
        });
    }
    private void activar_accion_nuevo1() {
        card_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog = new ProgressDialog(mcontext);
                progressDialog.setTitle("Registrando");
                progressDialog.setMessage("Guardando Ubicación de Trabajo...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();


                Registrar_Datos_Ubigeo(GestionUbigeo.CAPTACION_UBIGEO.getCodigo_modulo(), Usuario.SESION_ACTUAL.getId(),GestionUbigeo.UBIGEO_TEMP,mcontext);
            }
        });

    }
    private void activar_accion_nuevo2() {

        card_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(mcontext);
                progressDialog.setTitle("Registrando");
                progressDialog.setMessage("Guardando Ubicación de Trabajo...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                Registrar_Datos_Ubigeo(GestionUbigeo.CAPTACION_UBIGEO_MASIVO.getCodigo_modulo(), Usuario.SESION_ACTUAL.getId(),GestionUbigeo.UBIGEO_TEMP,mcontext);
            }
        });
    }
    private void activar_accion_nuevo3() {

        card_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(mcontext);
                progressDialog.setTitle("Registrando");
                progressDialog.setMessage("Guardando Ubicación de Trabajo...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                Registrar_Datos_Ubigeo(GestionUbigeo.CAPTACION_UBIGEO_BARRIO.getCodigo_modulo(), Usuario.SESION_ACTUAL.getId(),GestionUbigeo.UBIGEO_TEMP,mcontext);
            }
        });
    }
    private void activar_accion_nuevo4() {

        card_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(mcontext);
                progressDialog.setTitle("Registrando");
                progressDialog.setMessage("Guardando Ubicación de Trabajo...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                Registrar_Datos_Ubigeo(GestionUbigeo.ESTADISTICO_UBIGEO.getCodigo_modulo(), Usuario.SESION_ACTUAL.getId(),GestionUbigeo.UBIGEO_TEMP,mcontext);
            }
        });
    }
    private void Actualizar_Datos_Ubigeo(final int codigo_modulo,final int id_userr,final GestionUbigeo ubigeoTemp,final Context context) {
           String id_departamento=String.valueOf(ubigeoTemp.getDepartamento().getCodigo());
           String id_provincia=String.valueOf(ubigeoTemp.getProvincia().getCodigo());
           String id_distrito=String.valueOf(ubigeoTemp.getDistrito().getCodigo());
           String id_user=String.valueOf(id_userr).toString();
           String id_modulo=String.valueOf(codigo_modulo);

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {

                        if(MODULO.equalsIgnoreCase("4")){
                            progressDialog.dismiss();
                            Toast.makeText(context, "Ubicación actualizada correctamente!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UbigeoActivity.this, PrincipalActivity.class);
                            intent.putExtra("o","o4");
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            UbigeoActivity.this.startActivity(intent);
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(context, "Ubicación actualizada correctamente!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UbigeoActivity.this, PrincipalActivity.class);
                            intent.putExtra("o","o1");
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            UbigeoActivity.this.startActivity(intent);
                        }


                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error actualizacion ubigeo :"+e);
                }
            }
        };

        Actualizar_Ubigeo xx = new Actualizar_Ubigeo(id_user, id_modulo,id_departamento,id_provincia,id_distrito, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }
    private void Registrar_Datos_Ubigeo(final int codigo_modulo,final int id_userr,final GestionUbigeo ubigeoTemp,final Context context) {
        String id_departamento=String.valueOf(ubigeoTemp.getDepartamento().getCodigo());
        String id_provincia=String.valueOf(ubigeoTemp.getProvincia().getCodigo());
        String id_distrito=String.valueOf(ubigeoTemp.getDistrito().getCodigo());
        String id_user=String.valueOf(id_userr).toString();
        String id_modulo=String.valueOf(codigo_modulo);

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        if(MODULO.equalsIgnoreCase("4")){
                            progressDialog.dismiss();
                            Toast.makeText(context, "Ubicación Registrada correctamente!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UbigeoActivity.this, PrincipalActivity.class);
                            intent.putExtra("o","o4");
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            UbigeoActivity.this.startActivity(intent);
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(context, "Ubicación Registrada correctamente!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UbigeoActivity.this, PrincipalActivity.class);
                            intent.putExtra("o","o1");
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            UbigeoActivity.this.startActivity(intent);

                        }


                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error actualizacion ubigeo :"+e);
                }
            }
        };

        Registrar_Ubigeo xx = new Registrar_Ubigeo(id_user, id_modulo,id_departamento,id_provincia,id_distrito, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }
    private void Listar_Departamentos_update(final Context context,final GestionUbigeo captacionUbigeo) {
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        depa_accion=true;

                        JSONArray departamentos=jsonResponse.getJSONArray("departamento");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Unidad_Territorial temp=new Unidad_Territorial();
                            temp.setCodigo(objeto.getInt("idDepa"));
                            temp.setDescripcion(objeto.getString("departamento"));
                            DepartamentosLista.add(temp);
                        }

                        //LLENAR SPINNER DEPARTAMENTOS
                        spinner_departamentos=new String[DepartamentosLista.size()];
                        for(int i=0;i<DepartamentosLista.size();i++){
                            spinner_departamentos[i]=DepartamentosLista.get(i).getDescripcion();
                        }
                        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,spinner_departamentos);

                        departamento.setAdapter(adapter_arr);

                        //RECUPERANDO DEPARTAMENTO ACTUAL DEL MODULO DEL USUARIO
                        String depa=captacionUbigeo.getDepartamento().getDescripcion();

                        for(int i=0;i<spinner_departamentos.length;i++){
                            if(spinner_departamentos[i].equalsIgnoreCase(depa)){
                                pos_depa=i;
                            }
                        }


                        departamento.setSelection(pos_depa);

                        //RECUPERANDO PROVINCIAS

                        Listar_Provincias_update(captacionUbigeo.getDepartamento().getCodigo(),mcontext,captacionUbigeo);
                        System.out.println("DEPARTAMENTOS UPDATE");

                    } else {

                        Toast.makeText(context, "Error de conexion DEPA UPDATE", Toast.LENGTH_SHORT).show();
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
    private void Listar_Provincias_update(final int id_departamento,final Context context,final GestionUbigeo captacionUbigeo) {
        String id_depa=String.valueOf(id_departamento);

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {



                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        prov_accion=true;
                        JSONArray provinciass=jsonResponse.getJSONArray("provincia");
                        for(int i=0;i<provinciass.length();i++){
                            JSONObject objeto= provinciass.getJSONObject(i);
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


                        //RECUPERANDO PROVINCIA ACTUAL DEL MODULO DEL USUARIO
                        String provi=captacionUbigeo.getProvincia().getDescripcion();

                        for(int i=0;i<spinner_provincias.length;i++){
                            if(spinner_provincias[i].equalsIgnoreCase(provi)){
                                pos_prov=i;
                            }
                        }

                        provincias.setSelection(pos_prov);


                        Listar_Distritos_update(captacionUbigeo.getProvincia().getCodigo(),mcontext,captacionUbigeo);
                        System.out.println("PROVINCIAS UPDATE");

                    } else {

                        Toast.makeText(context, "Error de conexion PROV UPDATE", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarProvincias recuperarProv = new RecuperarProvincias(id_depa,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(recuperarProv);


    }
    private void Listar_Distritos_update(int id_provincia, final Context context, final GestionUbigeo captacionUbigeo) {
        String id_prov=String.valueOf(id_provincia);

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        dist_accion=true;
                        JSONArray distritoss=jsonResponse.getJSONArray("distrito");
                        for(int i=0;i<distritoss.length();i++){
                            JSONObject objeto= distritoss.getJSONObject(i);
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

                        //RECUPERANDO DISTRITO ACTUAL DEL MODULO DEL USUARIO
                        String dist=captacionUbigeo.getDistrito().getDescripcion();
                        for(int i=0;i<spinner_distritos.length;i++){
                            if(spinner_distritos[i].equalsIgnoreCase(dist)){
                                pos_dist=i;
                            }
                        }


                        distritos.setSelection(pos_dist);

                        depa_accion=false;
                        prov_accion=false;
                        dist_accion=false;

                        System.out.println("DISTRITOS UPDATE");

                    } else {

                        Toast.makeText(context, "Error de conexion DIST UPDATE", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarDistritos recuperarDist = new RecuperarDistritos(id_prov,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(recuperarDist);
    }
    private void Listar_Departamentos(final Context context){

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                         JSONArray departamentos=jsonResponse.getJSONArray("departamento");
                          for(int i=0;i<departamentos.length();i++){
                               JSONObject objeto= departamentos.getJSONObject(i);
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

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
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
    private void Listar_Provincias(final int id_departamento,final Context context){
       String id_depa=String.valueOf(id_departamento);

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray provinciass=jsonResponse.getJSONArray("provincia");
                        for(int i=0;i<provinciass.length();i++){
                            JSONObject objeto= provinciass.getJSONObject(i);
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

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarProvincias recuperarProv = new RecuperarProvincias(id_depa,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(recuperarProv);
    }
    private void Listar_Distritos(final int id_provincia,final Context context) {
        String id_prov=String.valueOf(id_provincia);

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray distritoss=jsonResponse.getJSONArray("distrito");
                        for(int i=0;i<distritoss.length();i++){
                            JSONObject objeto= distritoss.getJSONObject(i);
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

                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarDistritos recuperarDist = new RecuperarDistritos(id_prov,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(recuperarDist);

    }
}
