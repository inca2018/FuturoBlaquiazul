package org.futuroblanquiazul.futuroblaquiazul.Activities.Informacion;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.ListaPersonaSeguimientoActivity;
import org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion.ListaSeguimientosActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterBusquedaPersona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.FechaEntidad;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarBusqueda1;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarBusqueda2;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarBusqueda3;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarDepartamentos;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarDistritos;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarProvincias;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Info;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InfoBuscadorActivity extends AppCompatActivity {

    LinearLayout linea1,linea2,linea3;
    RecyclerView recyclerView1,recyclerView2,recyclerView3;
    AdapterBusquedaPersona adapterBusquedaPersona1,adapterBusquedaPersona2,adapterBusquedaPersona3;
    List<Persona> lista_persona_captacion,lista_persona_metodologia,lista_persona_estadistico;
    LinearLayoutManager linearLayoutManager1,linearLayoutManager2,linearLayoutManager3;
    Button info_buscar;
    List<Unidad_Territorial> Lista_Departamentos;
    List<Unidad_Territorial> Lista_Provincias;
    List<Unidad_Territorial> Lista_Distritos;
    Spinner info_departamento;
    Spinner info_provincia;
    Spinner info_distrito;
    String[] Departamento_Nombres;
    String[] Provincias_Nombres;
    String[] Distritos_Nombres;
    ArrayAdapter<String> adapter_spinner_departamentos,adapter_spinner_provincias,adapter_spinner_distritos;
    Context context;
    boolean select_depa=false,select_prov=false,select_dis=false;
    ImageView info_inicio,info_fin;
    TextView info_inicio_texto,info_fin_text;
    Calendar dateTime = Calendar.getInstance();
    Calendar dateTime2 = Calendar.getInstance();
    Calendar inicio = Calendar.getInstance();
    long minimo;
    List<Persona> Lista_General_persona;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_buscador);
        Variables();
        Armado_Listas();
        Verificar_ventanas();
        Gestion_Ubigeo();
        info_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               System.out.println("ENTRO A BUSCAR");
                if(Info.GESTOR.getDepartamento()!=null){
                    if(Info.GESTOR.getInicio()!=null){
                        if(Info.GESTOR.getFin()!=null){
                                    Buscar_jugadores_Filtro();
                        }else{
                            Toast.makeText(context, "Seleccione Fecha de Fin para comenzar la Busqueda!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(context, "Seleccione Fecha de Inicio para comenzar la Busqueda!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Seleccione Departamento!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Seleccion_Fechas();
    }
    private void Buscar_jugadores_Filtro() {

        lista_persona_metodologia.clear();
        lista_persona_estadistico.clear();
        lista_persona_captacion.clear();

        if(Info.GESTOR.getDepartamento()!=null && Info.GESTOR.getProvincia()!=null && Info.GESTOR.getDistrito()!=null ){
            //DEPARTAMENTO,PROVINCIA,DISTRITO
           debug("ENTRO A BUSQUEDA 3");
            Listar_Jugadores_DEP_PROV_DIST(Info.GESTOR);
        }else
        if(Info.GESTOR.getDepartamento()!=null &&  Info.GESTOR.getProvincia()!=null &&  Info.GESTOR.getDistrito()==null){
            //DEPARTAMENTO,PROVINCIA
            debug("ENTRO A BUSQUEDA 2");
            Listar_Jugadores_DEP_PROV(Info.GESTOR);
        } else
        if(Info.GESTOR.getDepartamento()!=null &&  Info.GESTOR.getProvincia()==null &&  Info.GESTOR.getDistrito()==null){
            //DEPARTAMENTO
            debug("ENTRO A BUSQUEDA 1");
            Listar_Jugadores_DEP(Info.GESTOR);
        }

    }
    private void Listar_Jugadores_DEP(Info gestor) {

        String inicio=gestor.getInicio().getFecha_base();
        String fin=gestor.getFin().getFecha_base();

        String departamento=String.valueOf(gestor.getDepartamento().getCodigo());

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("busqueda");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            temp.setDni(objeto.getInt("DNI"));
                            temp.setFoto(objeto.getString("FOTO"));
                            temp.setUbigeo(objeto.getString("UBIGEO"));

                            if(Info.GESTOR.getTipo_busqueda()==1){
                                lista_persona_captacion.add(temp);
                            }else if(Info.GESTOR.getTipo_busqueda()==2){
                                lista_persona_metodologia.add(temp);
                            }else if(Info.GESTOR.getTipo_busqueda()==3){
                                lista_persona_estadistico.add(temp);
                            }

                        }

                        if(Info.GESTOR.getTipo_busqueda()==1){
                            adapterBusquedaPersona1.notifyDataSetChanged();
                        }else if(Info.GESTOR.getTipo_busqueda()==2){
                            adapterBusquedaPersona2.notifyDataSetChanged();
                        }else if(Info.GESTOR.getTipo_busqueda()==3){
                            adapterBusquedaPersona3.notifyDataSetChanged();
                        }

                    } else {

                        if(Info.GESTOR.getTipo_busqueda()==1){
                            lista_persona_captacion.clear();
                            adapterBusquedaPersona1.notifyDataSetChanged();
                        }else if(Info.GESTOR.getTipo_busqueda()==2){
                            lista_persona_metodologia.clear();
                            adapterBusquedaPersona2.notifyDataSetChanged();
                        }else if(Info.GESTOR.getTipo_busqueda()==3){
                            lista_persona_estadistico.clear();
                            adapterBusquedaPersona3.notifyDataSetChanged();
                        }
                        Toast.makeText(context, "Sin Resultados!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarBusqueda1 xx = new RecuperarBusqueda1(inicio,fin,departamento,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);
    }
    private void Listar_Jugadores_DEP_PROV(Info gestor) {
        String inicio=gestor.getInicio().getFecha_base();
        String fin=gestor.getFin().getFecha_base();

        String departamento=String.valueOf(gestor.getDepartamento().getCodigo());
        String provincia=String.valueOf(gestor.getProvincia().getCodigo());

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        JSONArray departamentos=jsonResponse.getJSONArray("busqueda");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            temp.setDni(objeto.getInt("DNI"));
                            temp.setFoto(objeto.getString("FOTO"));
                            temp.setUbigeo(objeto.getString("UBIGEO"));

                            if(Info.GESTOR.getTipo_busqueda()==1){
                                lista_persona_captacion.add(temp);
                            }else if(Info.GESTOR.getTipo_busqueda()==2){
                                lista_persona_metodologia.add(temp);
                            }else if(Info.GESTOR.getTipo_busqueda()==3){
                                lista_persona_estadistico.add(temp);
                            }

                        }

                        if(Info.GESTOR.getTipo_busqueda()==1){
                            adapterBusquedaPersona1.notifyDataSetChanged();
                        }else if(Info.GESTOR.getTipo_busqueda()==2){
                            adapterBusquedaPersona2.notifyDataSetChanged();
                        }else if(Info.GESTOR.getTipo_busqueda()==3){
                            adapterBusquedaPersona3.notifyDataSetChanged();
                        }

                    } else {

                        if(Info.GESTOR.getTipo_busqueda()==1){
                            lista_persona_captacion.clear();
                            adapterBusquedaPersona1.notifyDataSetChanged();
                        }else if(Info.GESTOR.getTipo_busqueda()==2){
                            lista_persona_metodologia.clear();
                            adapterBusquedaPersona2.notifyDataSetChanged();
                        }else if(Info.GESTOR.getTipo_busqueda()==3){
                            lista_persona_estadistico.clear();
                            adapterBusquedaPersona3.notifyDataSetChanged();
                        }
                        Toast.makeText(context, "Sin Resultados!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarBusqueda2 xx = new RecuperarBusqueda2(inicio,fin,departamento,provincia,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    private void Listar_Jugadores_DEP_PROV_DIST(Info gestor) {
        String inicio=gestor.getInicio().getFecha_base();
        String fin=gestor.getFin().getFecha_base();

        String departamento=String.valueOf(gestor.getDepartamento().getCodigo());
        String provincia=String.valueOf(gestor.getProvincia().getCodigo());
        String distrito=String.valueOf(gestor.getDistrito().getCodigo());

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        JSONArray departamentos=jsonResponse.getJSONArray("busqueda");
                        for(int i=0;i<departamentos.length();i++){
                            JSONObject objeto= departamentos.getJSONObject(i);
                            Persona temp=new Persona();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Persona(objeto.getString("NOMBRES"));
                            temp.setApellidos_Persona(objeto.getString("APELLIDOS"));
                            temp.setDni(objeto.getInt("DNI"));
                            temp.setFoto(objeto.getString("FOTO"));
                            temp.setUbigeo(objeto.getString("UBIGEO"));

                            if(Info.GESTOR.getTipo_busqueda()==1){
                                lista_persona_captacion.add(temp);
                            }else if(Info.GESTOR.getTipo_busqueda()==2){
                                lista_persona_metodologia.add(temp);
                            }else if(Info.GESTOR.getTipo_busqueda()==3){
                                lista_persona_estadistico.add(temp);
                            }

                        }

                        if(Info.GESTOR.getTipo_busqueda()==1){
                            adapterBusquedaPersona1.notifyDataSetChanged();
                        }else if(Info.GESTOR.getTipo_busqueda()==2){
                            adapterBusquedaPersona2.notifyDataSetChanged();
                        }else if(Info.GESTOR.getTipo_busqueda()==3){
                            adapterBusquedaPersona3.notifyDataSetChanged();
                        }

                    } else {

                        if(Info.GESTOR.getTipo_busqueda()==1){
                            lista_persona_captacion.clear();
                            adapterBusquedaPersona1.notifyDataSetChanged();
                        }else if(Info.GESTOR.getTipo_busqueda()==2){
                            lista_persona_metodologia.clear();
                            adapterBusquedaPersona2.notifyDataSetChanged();
                        }else if(Info.GESTOR.getTipo_busqueda()==3){
                            lista_persona_estadistico.clear();
                            adapterBusquedaPersona3.notifyDataSetChanged();
                        }
                        Toast.makeText(context, "Sin Resultados!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar departamentos :"+e);
                }
            }
        };

        RecuperarBusqueda3 xx = new RecuperarBusqueda3(inicio,fin,departamento,provincia,distrito,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);
    }
    private void Seleccion_Fechas() {

        info_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog xx = onCreateDialog2();
                xx.show();
            }
        });
        info_fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog xx = onCreateDialog();
                xx.show();
            }
        });
    }
    private void Gestion_Ubigeo() {
        info_departamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if(String.valueOf(item).equalsIgnoreCase("-- SELECCIONE --")){

                    Limpiar_Distritos();
                    Limpiar_Provincias();
                    Info.GESTOR.setDepartamento(null);
                }else{

                    for(int x=0;x<Lista_Departamentos.size();x++){
                        if(Lista_Departamentos.get(x).getDescripcion().equalsIgnoreCase(String.valueOf(item))){
                            Info.GESTOR.setDepartamento(Lista_Departamentos.get(x));
                        }
                    }
                    Lista_Provincias.clear();
                    Lista_Distritos.clear();

                    Limpiar_Distritos();
                    Info.GESTOR.setProvincia(null);
                    Listar_Provincias(Info.GESTOR.getDepartamento().getCodigo(),context);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        info_provincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if(String.valueOf(item).equalsIgnoreCase("-- SELECCIONE --")){
                    Limpiar_Distritos();
                    Info.GESTOR.setProvincia(null);
                }else{

                    for(int x=0;x<Lista_Provincias.size();x++){
                        if(Lista_Provincias.get(x).getDescripcion().equalsIgnoreCase(String.valueOf(item))){
                            Info.GESTOR.setProvincia(Lista_Provincias.get(x));
                        }
                    }
                    Lista_Distritos.clear();
                    Info.GESTOR.setDistrito(null);
                    Listar_Distritos(Info.GESTOR.getProvincia().getCodigo(),context);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        info_distrito.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if(String.valueOf(item).equalsIgnoreCase("-- SELECCIONE --")){
                        Info.GESTOR.setDistrito(null);
                }else{

                    for(int x=0;x<Lista_Distritos.size();x++){
                        if(Lista_Distritos.get(x).getDescripcion().equalsIgnoreCase(String.valueOf(item))){
                            Info.GESTOR.setDistrito(Lista_Distritos.get(x));
                        }
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Listar_Departamentos(context);
    }
    private void Limpiar_Distritos() {
        Info.GESTOR.setDistrito(null);
        Distritos_Nombres=new String[1];
        Distritos_Nombres[0]="-- SELECCIONE --";
        adapter_spinner_distritos=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,Distritos_Nombres);
        info_distrito.setAdapter(adapter_spinner_distritos);
    }
    private void Limpiar_Provincias() {
         Info.GESTOR.setProvincia(null);
        Provincias_Nombres=new String[1];
        Provincias_Nombres[0]="-- SELECCIONE --";
        adapter_spinner_provincias=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,Provincias_Nombres);
        info_provincia.setAdapter(adapter_spinner_provincias);
    }
    private void Variables() {
        context=this;
        linea1=findViewById(R.id.area_captacion);
        linea2=findViewById(R.id.area_metodologia);
        linea3=findViewById(R.id.area_estadistico);
        recyclerView1=findViewById(R.id.lista_captacion);
        recyclerView2=findViewById(R.id.lista_metodologia);
        recyclerView3=findViewById(R.id.lista_estadistico);
        info_buscar=findViewById(R.id.info_buscar);
        lista_persona_captacion=new ArrayList<>();
        lista_persona_metodologia=new ArrayList<>();
        lista_persona_estadistico=new ArrayList<>();
        Lista_Departamentos =new ArrayList<>();
        Lista_Provincias =new ArrayList<>();
        Lista_Distritos =new ArrayList<>();
        info_departamento=findViewById(R.id.info_departamento);
        info_provincia=findViewById(R.id.info_provincia);
        info_distrito=findViewById(R.id.info_distrito);
        info_inicio=findViewById(R.id.info_icon_inicio);
        info_fin=findViewById(R.id.info_icon_fin);
        info_inicio_texto=findViewById(R.id.info_texto_inicio);
        info_fin_text=findViewById(R.id.info_texto_fin);
        info_fin.setEnabled(false);

        Lista_General_persona=new ArrayList<>();
    }
    private void Armado_Listas() {
        linearLayoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapterBusquedaPersona1 = new AdapterBusquedaPersona(this, lista_persona_captacion, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {
                       Info.GESTOR.setPersonal_Temporal(lista_persona_captacion.get(position));
                       Intent intent = new Intent(InfoBuscadorActivity.this,PerfilPersonaActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       InfoBuscadorActivity.this.startActivity(intent);
            }
        });
        recyclerView1.setAdapter(adapterBusquedaPersona1);
        recyclerView1.setLayoutManager(linearLayoutManager1);

        linearLayoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapterBusquedaPersona2 = new AdapterBusquedaPersona(this, lista_persona_metodologia, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
        });
        recyclerView2.setAdapter(adapterBusquedaPersona2);
        recyclerView2.setLayoutManager(linearLayoutManager2);

        linearLayoutManager3 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        adapterBusquedaPersona3 = new AdapterBusquedaPersona(this, lista_persona_estadistico, new RecyclerViewOnItemClickListener() {
            public void onClick(View v, int position) {

            }
        });
        recyclerView3.setAdapter(adapterBusquedaPersona3);
        recyclerView3.setLayoutManager(linearLayoutManager3);
    }

    private void Verificar_ventanas() {
        if(Info.GESTOR.getTipo_busqueda()==1){
            linea1.setVisibility(View.VISIBLE);
            linea2.setVisibility(View.GONE);
            linea3.setVisibility(View.GONE);
        }else if(Info.GESTOR.getTipo_busqueda()==2){
            linea1.setVisibility(View.GONE);
            linea2.setVisibility(View.VISIBLE);
            linea3.setVisibility(View.GONE);
        }else if(Info.GESTOR.getTipo_busqueda()==3){
            linea1.setVisibility(View.GONE);
            linea2.setVisibility(View.GONE);
            linea3.setVisibility(View.VISIBLE);

        }
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
                            Lista_Departamentos.add(temp);
                            System.out.println("id_Depa="+temp.getCodigo()+"  Descripcion_depa="+temp.getDescripcion());
                        }


                        Departamento_Nombres=new String[Lista_Departamentos.size()+1];
                        Departamento_Nombres[0]="-- SELECCIONE --";
                        for(int i = 0; i< Lista_Departamentos.size(); i++){
                            Departamento_Nombres[i+1]= Lista_Departamentos.get(i).getDescripcion();
                        }
                        adapter_spinner_departamentos=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,Departamento_Nombres);
                        info_departamento.setAdapter(adapter_spinner_departamentos);

                        System.out.println("DEPARTAMENTOS LISTADO");
                        select_depa=true;
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
                            Lista_Provincias.add(temp);
                        }
                        Provincias_Nombres=new String[Lista_Provincias.size()+1];
                        Provincias_Nombres[0]="-- SELECCIONE --";
                        for(int i = 0; i< Lista_Provincias.size(); i++){
                            Provincias_Nombres[i+1]= Lista_Provincias.get(i).getDescripcion();
                        }
                         adapter_spinner_provincias=null;
                         adapter_spinner_provincias=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,Provincias_Nombres);
                         info_provincia.setAdapter(adapter_spinner_provincias);

                        System.out.println("PROVINCIAS NUEVO");

                        select_prov=true;
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
                            Lista_Distritos.add(temp);
                        }
                        Distritos_Nombres=new String[Lista_Distritos.size()+1];
                        Distritos_Nombres[0]="-- SELECCIONE --";
                        for(int i = 0; i< Lista_Distritos.size(); i++){
                            Distritos_Nombres[i+1]= Lista_Distritos.get(i).getDescripcion();
                        }
                        adapter_spinner_distritos=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,Distritos_Nombres);
                        info_distrito.setAdapter(adapter_spinner_distritos);
                        System.out.println("DISTRITOS LISTADO");

                        select_dis=true;
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
    public Dialog onCreateDialog2() {

        DatePickerDialog.OnDateSetListener pDateSetListener = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                dateTime.set(Calendar.YEAR, year);
                dateTime.set(Calendar.MONTH, monthOfYear);
                dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateTextLabel1();

            }
        };

        DatePickerDialog dialog = new DatePickerDialog(this, pDateSetListener,dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH));
        //dialog.getDatePicker().setMinDate(minimo_inicio);

        return dialog;

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
        FechaEntidad inicio=new FechaEntidad();
        inicio.setAno(anoo);
        inicio.setMes(Integer.parseInt(meso));
        inicio.setDia(Integer.parseInt(diao));
        inicio.setFecha_base(fecha_inicio);
        Info.GESTOR.setInicio(inicio);
        info_inicio_texto.setText(diao+"/"+meso+"/"+anoo);

        info_fin.setEnabled(true);
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
         FechaEntidad fin=new FechaEntidad();
         fin.setAno(anoo);
         fin.setDia(Integer.parseInt(diao));
         fin.setMes(Integer.parseInt(meso));
         fin.setFecha_base(fecha_fin);
         Info.GESTOR.setFin(fin);
         info_fin_text.setText(diao+"/"+meso+"/"+anoo);
    }
    public long Recuperar_minimo(int year,int month,int day){
        long d=0;

        inicio.set(Calendar.YEAR,year);
        inicio.set(Calendar.MONTH,month);
        inicio.set(Calendar.DAY_OF_MONTH,day);

        d=inicio.getTimeInMillis();
        return d;
    }
    public void debug(String f){
        System.out.println(f);
    }

}
