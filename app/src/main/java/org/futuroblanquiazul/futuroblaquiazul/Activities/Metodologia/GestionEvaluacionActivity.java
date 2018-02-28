package org.futuroblanquiazul.futuroblaquiazul.Activities.Metodologia;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterBarrio;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterFiltroPersona;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterMetodologiaPersona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.BarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Grupo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Plantel;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarDepartamentos;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarGrupoPlantel;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarGrupos;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPlantelPersonas;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.SSLPeerUnverifiedException;

public class GestionEvaluacionActivity extends AppCompatActivity {

    RecyclerView recyclerView_filtro;
    LinearLayoutManager linearLayout;
    AdapterFiltroPersona adapter;
    Context context;
    Spinner spinner_grupo,spinner_categoria;

    List<Grupo> lista_grupos;
    List<Plantel> lista_plantel;
    String[] nombres_grupos,nombres_plantel;
    ProgressDialog progressDialog;

    List<Persona> lista_personas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_evaluacion);
        lista_grupos=new ArrayList<>();
        lista_plantel=new ArrayList<>();
        lista_personas=new ArrayList<>();

        recyclerView_filtro=findViewById(R.id.recycler_filtro);
        context=this;
        spinner_grupo=findViewById(R.id.spinner_grupo);
        spinner_categoria=findViewById(R.id.spinner_categoria);


        Listar_grupos(context);


        spinner_grupo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                for(int x=0;x<lista_grupos.size();x++){
                    if(lista_grupos.get(x).getDescripcion().equalsIgnoreCase(String.valueOf(item))){
                         Usuario.SESION_ACTUAL.setGrupo_filtro(lista_grupos.get(x));
                    }
                }

                lista_plantel.clear();

                Listar_plantel(Usuario.SESION_ACTUAL.getGrupo_filtro().getId(),context);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                for(int x=0;x<lista_plantel.size();x++){
                    if(lista_plantel.get(x).getNombre_categoria().equalsIgnoreCase(String.valueOf(item))){
                        Usuario.SESION_ACTUAL.setPlantel_filtro(lista_plantel.get(x));
                    }
                }


                if(Usuario.SESION_ACTUAL.getPlantel_filtro()!=null){
                    lista_personas.clear();
                    Listar_Personas_Plantel(Usuario.SESION_ACTUAL.getPlantel_filtro().getId(),context);
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        linearLayout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapter = new AdapterFiltroPersona(this, lista_personas, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
        });

        recyclerView_filtro.setAdapter(adapter);
        recyclerView_filtro.setLayoutManager(linearLayout);



    }

    private void Listar_Personas_Plantel(int id,final Context context) {

        String id_plantel=String.valueOf(id);

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Plantel:");
        progressDialog.setMessage("Listando...");
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("plantel_personas");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));

                            lista_personas.add(temp);

                            adapter.notifyDataSetChanged();
                            progressDialog.dismiss();

                        }


                    } else {

                        Toast.makeText(context, "Error de conexion grupos", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };


        RecuperarPlantelPersonas xx = new RecuperarPlantelPersonas(id_plantel,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);



    }

    private void Listar_plantel(int id,final Context context) {

        String id_grupo=String.valueOf(id);
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("grupo_plantel");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Plantel temp=new Plantel();
                            temp.setId(objeto.getInt("ID"));
                            temp.setRango(objeto.getInt("ID_RANGO"));
                            temp.setNombre_categoria(objeto.getString("NOMBRE_CATEGORIA"));
                            temp.setFecha_registro(objeto.getString("FECHA_REGISTRO"));
                            temp.setId_user(objeto.getInt("ID_USER"));
                            temp.setEstado(objeto.getInt("ESTADO"));

                            lista_plantel.add(temp);

                        }


                        nombres_plantel=new String[lista_plantel.size()];
                        for(int i=0;i<lista_plantel.size();i++){
                            nombres_plantel[i]=lista_plantel.get(i).getNombre_categoria();
                        }
                        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,nombres_plantel);
                        spinner_categoria.setAdapter(adapter_arr);


                    } else {

                        Toast.makeText(context, "Error de conexion grupos", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };


        RecuperarGrupoPlantel xx = new RecuperarGrupoPlantel(id_grupo,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }
    private void Listar_grupos(final Context context) {
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("grupos");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Grupo temp=new Grupo();
                            temp.setId(objeto.getInt("ID"));
                            temp.setDescripcion(objeto.getString("DESCRIPCION"));
                            temp.setFecha_registro(objeto.getString("FECHA_REGISTRO"));
                            temp.setId_user(objeto.getInt("ID_USER"));
                            temp.setEstado(objeto.getInt("ESTADO"));
                            lista_grupos.add(temp);

                        }


                        nombres_grupos=new String[lista_grupos.size()];
                        for(int i=0;i<lista_grupos.size();i++){
                            nombres_grupos[i]=lista_grupos.get(i).getDescripcion();
                        }
                        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,nombres_grupos);
                        spinner_grupo.setAdapter(adapter_arr);


                    } else {

                        Toast.makeText(context, "Error de conexion grupos", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };


        RecuperarGrupos xx = new RecuperarGrupos(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);



    }
}
