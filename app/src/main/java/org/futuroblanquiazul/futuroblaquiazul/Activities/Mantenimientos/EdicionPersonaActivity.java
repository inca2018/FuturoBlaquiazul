package org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarDepartamentos;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarDistritos;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarProvincias;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EdicionPersonaActivity extends AppCompatActivity {

    ImageView foto_persona;
    FloatingActionButton accion_foto;
    EditText nombre_persona,apellido_persona;
    ImageView calendario_nacimiento;
    EditText calendario_texto_Fecha;
    EditText dni_persona,correo_persona,nacionalidad_persona;
    CheckBox bautizo_persona,comunion_persona,confirmacion_persona;
    EditText residencia_persona;
    Spinner departamento_residencia_persona;
    Spinner provincia_residencia_persona;
    Spinner distrito_residencia_persona;
    EditText club_actual_persona,liga_actual_persona;
    EditText telefono_persona,fijo_persona;
    EditText apoderado_persona,telefono_apoderado_persona;
    Button grabar_persona;
    Context context;

    List<Unidad_Territorial> DepartamentosLista,ProvinciasLista,DistritoLista;
    String[] spinner_departamentos,spinner_provincias,spinner_distritos;
    boolean nuevo_caso1=false;
    boolean nuevo_caso2=false;

    boolean departamento_accion=false;
    boolean provincia_accion=false;
    boolean distrito_accion=false;

    int pos_depa;
    int pos_prov;
    int pos_dis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_persona);
        context=this;
        foto_persona=findViewById(R.id.p_foto_persona);
        accion_foto=findViewById(R.id.p_accion_persona);
        nombre_persona=findViewById(R.id.p_nombre);
        apellido_persona=findViewById(R.id.p_apellido);
        calendario_nacimiento=findViewById(R.id.p_fecha_naci_icon);
        calendario_texto_Fecha=findViewById(R.id.p_fecha_naci_text);
        dni_persona=findViewById(R.id.p_dni);
        correo_persona=findViewById(R.id.p_correo);
        nacionalidad_persona=findViewById(R.id.p_nacionalidad);
        bautizo_persona=findViewById(R.id.p_check_bautizo);
        comunion_persona=findViewById(R.id.p_check_comunion);
        confirmacion_persona=findViewById(R.id.p_check_confirmacion);
        residencia_persona=findViewById(R.id.p_residencia);
        departamento_residencia_persona=findViewById(R.id.p_departamentos);
        provincia_residencia_persona=findViewById(R.id.p_provincias);
        distrito_residencia_persona=findViewById(R.id.p_distritos);
        club_actual_persona=findViewById(R.id.p_club_actual);
        liga_actual_persona=findViewById(R.id.p_liga_Actual);
        telefono_persona=findViewById(R.id.p_telefono);
        fijo_persona=findViewById(R.id.p_fijo);
        apoderado_persona=findViewById(R.id.p_apoderado);
        telefono_apoderado_persona=findViewById(R.id.p_contacto_apoderado);
        grabar_persona=findViewById(R.id.p_boton_grabar);
        DepartamentosLista=new ArrayList<>();
        ProvinciasLista=new ArrayList<>();
        DistritoLista=new ArrayList<>();


        grabar_persona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if(Recursos_Mantenimientos.TEMP.getPersona_temporal()!=null){

        }else{
            Listar_DepartamentosUpdate(context);
        }

        departamento_residencia_persona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        debug("PASO DEPARTAMENTO NUEVO CASO FALSE");
                        ProvinciasLista.clear();
                        DistritoLista.clear();
                        Listar_Provincias(Recursos_Mantenimientos.TEMP.getDepartamento().getCodigo(),context);

                    }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        provincia_residencia_persona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                    }else{

                        DistritoLista.clear();
                        Listar_Distritos(Recursos_Mantenimientos.TEMP.getProvincia().getCodigo(),context);

                    }


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        distrito_residencia_persona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        distrito_accion=true;
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
                        distrito_residencia_persona.setAdapter(adapter_arr);
                        System.out.println("DISTRITOS NEW");


                         Mostrar_Resultados();


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
                        provincia_accion=true;
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
                        provincia_residencia_persona.setAdapter(adapter_arr);

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
                        departamento_residencia_persona.setAdapter(adapter_arr);

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

                    if (success) {
                        departamento_accion=true;
                        JSONArray xx=jsonResponse.getJSONArray("departamento");
                        for(int i=0;i<xx.length();i++){
                            JSONObject objeto= xx.getJSONObject(i);
                            Unidad_Territorial temp=new Unidad_Territorial();
                            temp.setCodigo(objeto.getInt("idDepa"));
                            temp.setDescripcion(objeto.getString("departamento"));
                            DepartamentosLista.add(temp);
                        }


                        spinner_departamentos=new String[DepartamentosLista.size()];
                        for(int i=0;i<DepartamentosLista.size();i++){
                            spinner_departamentos[i]=DepartamentosLista.get(i).getDescripcion();
                        }
                        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,spinner_departamentos);
                        departamento_residencia_persona.setAdapter(adapter_arr);



                        for(int i=0;i<spinner_departamentos.length;i++){
                            if(spinner_departamentos[i].equalsIgnoreCase("LIMA")){
                                pos_depa=i;
                            }
                        }
                        debug("----------------------------------------------------------------------------------");
                        debug("posicion_depa: "+pos_depa);
                        departamento_residencia_persona.setSelection(pos_depa);

                        Listar_ProvinciasUpdate(pos_depa,context);


                        System.out.println("DEPARTAMENTOS NEW");
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
                        provincia_residencia_persona.setAdapter(adapter_arr);


                        for(int i=0;i<spinner_provincias.length;i++){
                            if(spinner_provincias[i].equalsIgnoreCase("LIMA")){
                                pos_prov=i;
                            }
                        }
                        debug("----------------------------------------------------------------------------------");
                        debug("provincia encontrado: "+pos_prov);
                        provincia_residencia_persona.setSelection(pos_prov);

                        Listar_DistritosUpdate(pos_prov,context);

                        System.out.println("PROVINCIAS NEW");
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
                        distrito_residencia_persona.setAdapter(adapter_arr);


                        for(int i=0;i<spinner_distritos.length;i++){
                            if(spinner_distritos[i].equalsIgnoreCase("LOS OLIVOS")){
                                pos_dis=i;
                            }
                        }
                        debug("------------------------------------------------------------");
                        debug("COD DISTRITO ENCONTRADO: "+pos_dis);
                        distrito_residencia_persona.setSelection(pos_dis);

                        departamento_accion=false;
                        provincia_accion=false;
                        distrito_accion=false;

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

    private void Mostrar_Resultados() {

        if(Recursos_Mantenimientos.TEMP.getPersona_temporal()!=null){
            debug("TIENE DATOS ");
        }else{
            debug("NO TIENE DATOS ");
            nuevo_caso1=true;
            nuevo_caso2=true;



        }

    }
    public  int obtenerPosicionItem(Spinner spinner, String dato) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
        //que lo pasaremos posteriormente
        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda

            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(dato)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return posicion;
    }
    public void debug(String d){
        System.out.println(d);
    }


}
