package org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Persona;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActualizarPersona;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActualizarUsuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarDepartamentos;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarDistritos;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarProvincias;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarPersona;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarUsuario;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Registro_Postulante;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

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
    EditText categoria_persona;
    Button grabar_persona;
    Context context;
    EditText edad_persona;

    int ano=1999,mes=0,dia=1;

    List<Unidad_Territorial> DepartamentosLista,ProvinciasLista,DistritoLista;
    String[] spinner_departamentos,spinner_provincias,spinner_distritos;
    boolean departamento_accion=false;
    boolean provincia_accion=false;
    boolean distrito_accion=false;
    int pos_depa;
    int pos_prov;
    int pos_dis;
    private final String CARPETA_RAIZ="FuturoBlanquiazul/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"MisFotos";
    String nombre_creado="";
    String path;
    Bitmap bitmap;
    Calendar dateTime = Calendar.getInstance();
    Calendar hoy=Calendar.getInstance();
    String fecha_nacimiento_temporal;
    ProgressDialog progressDialog;
    boolean actualizar_accion=false;

    int dni_antiguo;
    int edad_actual;

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
        categoria_persona=findViewById(R.id.p_categoria_actual);
        telefono_apoderado_persona=findViewById(R.id.p_contacto_apoderado);
        edad_persona=findViewById(R.id.p_edad);
        grabar_persona=findViewById(R.id.p_boton_grabar);
        DepartamentosLista=new ArrayList<>();
        ProvinciasLista=new ArrayList<>();
        DistritoLista=new ArrayList<>();

        if(Recursos_Mantenimientos.TEMP.getPersona_temporal()!=null){
            // INGRESO A ACTUALIZAR
            actualizar_accion=true;
        }else{
            //INGRESO NUEVO
            Persona p=new Persona();
            Recursos_Mantenimientos.TEMP.setPersona_temporal(p);
        }

        if(Validar_Permisos()){
            accion_foto.setEnabled(true);
        }else{
            accion_foto.setEnabled(false);
        }

        accion_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Opciones_de_Fotos();
            }
        });


        grabar_persona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validar_Datos();
            }
        });

        Listar_DepartamentosUpdate(context);
        Datos_Adicionales();

    }

    private void Datos_Adicionales() {

        departamento_residencia_persona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Object item = adapterView.getItemAtPosition(i);
                for(int x=0;x<DepartamentosLista.size();x++){
                    if(DepartamentosLista.get(x).getDescripcion().equalsIgnoreCase(String.valueOf(item))){
                        Recursos_Mantenimientos.TEMP.getPersona_temporal().setDepartamento(DepartamentosLista.get(x));
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
        provincia_residencia_persona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                debug("ENTRO A ONCHANGE PROVINCIA CON CLICK");
                Object item = adapterView.getItemAtPosition(i);
                for(int x=0;x<ProvinciasLista.size();x++){
                    if(ProvinciasLista.get(x).getDescripcion().equalsIgnoreCase(String.valueOf(item))){
                        Recursos_Mantenimientos.TEMP.getPersona_temporal().setProvincia(ProvinciasLista.get(x));
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
        distrito_residencia_persona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                debug("ENTRO A ONCHANGE DISTRITO");

                Object item = adapterView.getItemAtPosition(i);
                for(int x=0;x<DistritoLista.size();x++){
                    if(DistritoLista.get(x).getDescripcion().equalsIgnoreCase(String.valueOf(item))){
                        Recursos_Mantenimientos.TEMP.getPersona_temporal().setDistrito(DistritoLista.get(x));
                    }
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        calendario_nacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });
        bautizo_persona.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    Recursos_Mantenimientos.TEMP.getPersona_temporal().setBautizo(1);
                }else{
                    Recursos_Mantenimientos.TEMP.getPersona_temporal().setBautizo(0);
                }
            }
        });

        comunion_persona.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    Recursos_Mantenimientos.TEMP.getPersona_temporal().setComunion(1);
                }else{
                    Recursos_Mantenimientos.TEMP.getPersona_temporal().setComunion(0);
                }
            }
        });

        confirmacion_persona.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    Recursos_Mantenimientos.TEMP.getPersona_temporal().setConfirmacion(1);
                }else{
                    Recursos_Mantenimientos.TEMP.getPersona_temporal().setConfirmacion(0);
                }
            }
        });
    }
    private void Validar_Datos() {
        if(nombre_persona.getText().toString().length()!=0){

            if(apellido_persona.getText().toString().length()!=0){
                if(calendario_texto_Fecha.getText().toString().length()!=0){
                    if(dni_persona.getText().toString().length()!=0){
                        Recuperar_Informacion_Persona();
                    }else{
                        dni_persona.setError("Campo Obligatorio");
                    }

                }else{
                    Toast.makeText(context, "Ingrese Fecha de Nacimiento!", Toast.LENGTH_SHORT).show();
                }

            }else{
                apellido_persona.setError("Campo Obligatorio");
            }

        }else{
            nombre_persona.setError("Campo Obligatorio");
        }
    }

    private void Recuperar_Informacion_Persona() {
        debug("ENTRO A RECUPERAR INFO");

          Recursos_Mantenimientos.TEMP.getPersona_temporal().setNombre_Persona(nombre_persona.getText().toString());
          Recursos_Mantenimientos.TEMP.getPersona_temporal().setApellidos_Persona(apellido_persona.getText().toString());

          if(fecha_nacimiento_temporal!=null){
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setFecha_Nacimiento(fecha_nacimiento_temporal);
          }else{

          }

          if(actualizar_accion){
              dni_antiguo=Recursos_Mantenimientos.TEMP.getPersona_temporal().getDni();
          }
          if(dni_persona.getText().toString().length()!=0){
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setDni(Integer.parseInt(dni_persona.getText().toString()));
          }else{
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setDni(0);
          }

          if(correo_persona.getText().toString().length()!=0){
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setCorreo(correo_persona.getText().toString());
          }else{
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setCorreo("");
          }

          if(nacionalidad_persona.getText().toString().length()!=0){
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setNacionalidad(nacionalidad_persona.getText().toString());
          }else{
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setNacionalidad("");
          }

          if(bautizo_persona.isChecked()==true){
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setBautizo(1);
          }else{
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setBautizo(0);
          }

          if(comunion_persona.isChecked()==true){
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setComunion(1);
          }else{
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setComunion(0);
          }

          if(confirmacion_persona.isChecked()==true){
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setConfirmacion(1);
          }else{
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setConfirmacion(0);
          }

          if(residencia_persona.getText().toString().length()!=0){
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setLugar_Residencia(residencia_persona.getText().toString());
          }else{
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setLugar_Residencia("");
          }


          if(club_actual_persona.getText().toString().length()!=0){
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setClub_actual(club_actual_persona.getText().toString());
          }else{
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setClub_actual("");
          }

          if(liga_actual_persona.getText().toString().length()!=0){
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setLiga_actual(liga_actual_persona.getText().toString());
          }else{
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setLiga_actual("");
          }

          if(telefono_persona.getText().toString().length()!=0){
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setTelefono(Integer.parseInt(telefono_persona.getText().toString()));
          }else{
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setTelefono(0);
          }

          if(fijo_persona.getText().toString().length()!=0){
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setTelefono_fijo(Integer.parseInt(fijo_persona.getText().toString()));
          }else{
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setTelefono_fijo(0);
          }

          if(apoderado_persona.getText().toString().length()!=0){
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setNombre_Apoderado(apoderado_persona.getText().toString());
          }else{
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setNombre_Apoderado("");
          }
          if(categoria_persona.getText().toString().length()!=0){
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setCategoria_Actual(categoria_persona.getText().toString());
          }else{
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setCategoria_Actual("");
          }


          if(telefono_apoderado_persona.getText().toString().length()!=0){
              Recursos_Mantenimientos.TEMP.getPersona_temporal().setTelefono_apoderado(Integer.parseInt(telefono_apoderado_persona.getText().toString()));
          }else{

              Recursos_Mantenimientos.TEMP.getPersona_temporal().setTelefono_apoderado(0);
          }


          if(actualizar_accion){
              debug("PERSONA ACTUALIZAR ");

              if(bitmap!=null){
                  debug("PERSONA ACTUALIZAR DE CAMARA");
                  String nuevo=Minimizar(bitmap);
                  Recursos_Mantenimientos.TEMP.getPersona_temporal().setFoto(nuevo);

              }else{
                  debug("ACTUALIZAR PERSONA CON FOTO DE SERVIDOR");
                  foto_persona.buildDrawingCache();
                  Bitmap bmap = foto_persona.getDrawingCache();
                  String no=convertirImgString(bmap);
                  Recursos_Mantenimientos.TEMP.getPersona_temporal().setFoto(no);
                  foto_persona.destroyDrawingCache();

              }

              Actualizar_Persona(dni_antiguo,Recursos_Mantenimientos.TEMP.getPersona_temporal(),context);

          }else{
              debug("PERSONA NUEVA REGISTRO NUEVO");

              if(bitmap!=null){
                  debug("NUEVA PERSONA CON FOTO DE CAMARA");
                  String nuevo=Minimizar(bitmap);
                  Recursos_Mantenimientos.TEMP.getPersona_temporal().setFoto(nuevo);
              }else{
                  debug("NUEVA PERSONA CON FOTO DE GALERIA");
                  Bitmap bitmap_actual = ((BitmapDrawable)foto_persona.getDrawable()).getBitmap();
                  String nuevo2=Minimizar(bitmap_actual);
                  Recursos_Mantenimientos.TEMP.getPersona_temporal().setFoto(nuevo2);
              }
              Registrar_Persona_Nueva(Recursos_Mantenimientos.TEMP.getPersona_temporal(),context);
          }
    }

    private void Actualizar_Persona(final int dni_an , Persona persona_temporal, final Context context) {
        debug("ACTUALIZAR PERSONA SERVICE");
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Jugador:");
        progressDialog.setMessage("Actualizando Jugador...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        debug(" ------------------------------------------DATOS RECUPERADOS -----------------------------------------------");
        debug("Nombre_persona: "+persona_temporal.getNombre_Persona());
        debug("Apellidos: "+persona_temporal.getApellidos_Persona());
        debug("Fecha nacimiento: "+persona_temporal.getFecha_Nacimiento());
        debug("Nacionalidad: "+persona_temporal.getNacionalidad());
        debug("Residencia: "+persona_temporal.getLugar_Residencia());
        debug("Departamento: "+persona_temporal.getDepartamento().getDescripcion());
        debug("Provincia: "+persona_temporal.getProvincia().getDescripcion());
        debug("Distrito: "+persona_temporal.getDistrito().getDescripcion());
        debug("Club: "+persona_temporal.getClub_actual());
        debug("Liga: "+persona_temporal.getLiga_actual());
        debug("Telefono: "+persona_temporal.getTelefono());
        debug("Fijo: "+persona_temporal.getTelefono_fijo());
        debug("Bautizo: "+persona_temporal.getBautizo());
        debug("Confirmacion: "+persona_temporal.getConfirmacion());
        debug("Comunion: "+persona_temporal.getComunion());
        debug("Dni: "+persona_temporal.getDni());
        debug("Correo: "+persona_temporal.getCorreo());
        debug("Apoderado: "+persona_temporal.getNombre_Apoderado());
        debug("Telefono apoderado: "+persona_temporal.getTelefono_apoderado());
        debug("Categoria: "+persona_temporal.getCategoria_Actual());
        debug("ID persona: "+persona_temporal.getId());



        String nom=persona_temporal.getNombre_Persona();
        String ape=persona_temporal.getApellidos_Persona();
        String fech_naci=persona_temporal.getFecha_Nacimiento();
        String nacionalidad=persona_temporal.getNacionalidad();
        String lugar_residencia=persona_temporal.getLugar_Residencia();
        String id_depa=String.valueOf(persona_temporal.getDepartamento().getCodigo());
        String id_prov=String.valueOf(persona_temporal.getProvincia().getCodigo());
        String id_dis=String.valueOf(persona_temporal.getDistrito().getCodigo());
        String club=persona_temporal.getClub_actual();
        String liga=persona_temporal.getLiga_actual();
        String telefono=String.valueOf(persona_temporal.getTelefono());
        String telefono_fijo=String.valueOf(persona_temporal.getTelefono_fijo());
        String bautizo=String.valueOf(persona_temporal.getBautizo());
        String comunion=String.valueOf(persona_temporal.getComunion());
        String confirmacion=String.valueOf(persona_temporal.getConfirmacion());
        String dni=String.valueOf(persona_temporal.getDni());
        String correo=persona_temporal.getCorreo();
        String apoderado=persona_temporal.getNombre_Apoderado();
        String tele_apoderado=String.valueOf(persona_temporal.getTelefono_apoderado());
        String categoria=persona_temporal.getCategoria_Actual();
        String foto=persona_temporal.getFoto();
        String id_persona=String.valueOf(persona_temporal.getId());

        String foto_nom=persona_temporal.getDni()+".jpg";
        String foto_nom_antigua=dni_an+".jpg";

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        Intent intent=new Intent(context,MantenimientoPersonaActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                       Recursos_Mantenimientos.TEMP.setPersona_temporal(null);
                       dni_antiguo=0;
                       actualizar_accion=false;

                        Toast.makeText(context, "Jugador Actualizado Exitosamente", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    } else {

                        Toast.makeText(context, "Error de al Actualizar Usuario", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al Actualizar Usuario :"+e);
                }
            }
        };

        ActualizarPersona xx = new ActualizarPersona(nom,ape,fech_naci,nacionalidad,lugar_residencia,id_depa,id_prov,id_dis,club,liga,telefono,telefono_fijo,bautizo,comunion,confirmacion,dni,correo,apoderado,tele_apoderado,categoria,foto,foto_nom,foto_nom_antigua,id_persona,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


    }

    private void Registrar_Persona_Nueva(Persona persona_temporal,final Context context) {

        debug("ENTRO A REGISTRAR NUEVO USUARIO");
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Persona:");
        progressDialog.setMessage("Registrando Persona...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String estado=String.valueOf(6);
        String nom_persona=persona_temporal.getNombre_Persona().toUpperCase();
        String ape_persona=persona_temporal.getApellidos_Persona().toUpperCase();
        String fecha_nacimiento=persona_temporal.getFecha_Nacimiento();
        String nacionalidad=persona_temporal.getNacionalidad().toUpperCase();
        String lugar_residencia=persona_temporal.getLugar_Residencia().toUpperCase();
        String cod_depa=String.valueOf(persona_temporal.getDepartamento().getCodigo());
        String cod_prov=String.valueOf(persona_temporal.getProvincia().getCodigo());
        String cod_dist=String.valueOf(persona_temporal.getDistrito().getCodigo());
        String club_actual=persona_temporal.getClub_actual().toUpperCase();
        String liga_actual=persona_temporal.getLiga_actual().toUpperCase();
        String telefono=String.valueOf(persona_temporal.getTelefono());
        String telefono_fijo=String.valueOf(persona_temporal.getTelefono_fijo());
        String bautizo=String.valueOf(persona_temporal.getBautizo());
        String comunion=String.valueOf(persona_temporal.getComunion());
        String confirmacion=String.valueOf(persona_temporal.getConfirmacion());
        String dni=String.valueOf(persona_temporal.getDni());
        String correo=persona_temporal.getCorreo().toUpperCase();
        String apoderado=persona_temporal.getNombre_Apoderado().toUpperCase();
        String telefono_apoderado=String.valueOf(persona_temporal.getTelefono_apoderado());
        String categoria=String.valueOf(persona_temporal.getCategoria_Actual().toUpperCase());
        String foto=persona_temporal.getFoto();
        String foto_nom=dni+".jpg";

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Intent intent=new Intent(context,MantenimientoPersonaActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        Recursos_Mantenimientos.TEMP.setPersona_temporal(null);
                        fecha_nacimiento_temporal="";
                        Toast.makeText(context, "Jugador Nuevo Guardado Exitosamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error de conexion al Registrar Jugador", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al registrar Jugador :"+e);
                }
            }
        };

        RegistrarPersona xx = new RegistrarPersona(estado,nom_persona,ape_persona,fecha_nacimiento,nacionalidad,lugar_residencia,cod_depa,cod_prov,cod_dist,club_actual,liga_actual,telefono,telefono_fijo,bautizo,comunion,confirmacion,dni,correo,apoderado,telefono_apoderado,categoria,foto,foto_nom,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);
    }
    //GESTION DE PERMISOS
    private boolean Validar_Permisos() {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return  true;
        }

        if((checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)
                && (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return  true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA)) ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargar_Dialogo_Recomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }

        return  false;
    }
    private void cargar_Dialogo_Recomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(context);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe Aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }
    public String Minimizar(Bitmap bitmap){
        String nuevo="";
        String info=convertirImgString(bitmap);
        byte[] bytecode= Base64.decode(info,Base64.DEFAULT);
        int alto=400;
        int ancho=400;
        Bitmap fo= BitmapFactory.decodeByteArray(bytecode,0,bytecode.length);
        Bitmap d=Bitmap.createScaledBitmap(fo,alto,ancho,true);
        nuevo=convertirImgString(d);
        return nuevo;
    }
    public String convertirImgString(Bitmap bitmap){

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte= array.toByteArray();
        String imagenString=Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                accion_foto.setEnabled(true);
            }else{
                Solicitar_permisos_Manual();
            }
        }
    }
    private void Solicitar_permisos_Manual() {

        final  CharSequence[] opciones={"SI","NO"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(context);
        alertOpciones.setTitle("¿Desea Configurar los Permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(opciones[i].equals("SI")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);

                }else if(opciones[i].equals("NO")){
                    Toast.makeText(context, "Los Permisos No fueron aceptados", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    //RECUPERACION DE FOTO
    private void Opciones_de_Fotos() {
        final  CharSequence[] opciones={"Tomar Foto","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(context);
        alertOpciones.setTitle("Seleccione una Opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(opciones[i].equals("Tomar Foto")){
                    Tomar_Foto();
                }else if(opciones[i].equals("Cargar Imagen")){
                    Cargar_Galeria_Imagen();
                }else if(opciones[i].equals("Cancelar")){
                    dialog.dismiss();
                }
            }
        });
        alertOpciones.show();
    }
    private void Tomar_Foto() {
        File fileImagen=new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean isCreada=fileImagen.exists();

        if(isCreada==false){
            isCreada=fileImagen.mkdirs();
        }

        if(isCreada){
            nombre_creado=(System.currentTimeMillis()/1000)+".jpg";
        }


        path=Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGEN+File.separator+nombre_creado;

        File imagen=new File(path);

        Intent intent=null;
        intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
            String authorities="org.futuroblanquiazul.futuroblaquiazul.provider";
            Uri ImageUri= FileProvider.getUriForFile(context,authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,ImageUri);
        }else{
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(imagen));
        }

        startActivityForResult(intent,20);

    }
    private void Cargar_Galeria_Imagen() {

        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent,"Seleccione Aplicación"),10);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){

            switch (requestCode){
                case 10:
                    Uri Mipath=data.getData();
                    // debug(" PATH ----------------------  :  "+String.valueOf(Mipath));
                    //foto_usuario.setImageURI(Mipath);
                    try {
                        bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),Mipath);
                        foto_persona.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 20:
                    MediaScannerConnection.scanFile(context, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            System.out.println("Confirmado");
                        }
                    });
                    bitmap= BitmapFactory.decodeFile(path);
                    foto_persona.setImageBitmap(bitmap);

                    break;
            }
        }
    }

    //GESTION DE FECHA
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
                        distrito_residencia_persona.setAdapter(adapter_arr);
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
                        departamento_residencia_persona.setAdapter(adapter_arr);


                        if(actualizar_accion){
                            for(int i=0;i<spinner_departamentos.length;i++){
                                if(spinner_departamentos[i].equalsIgnoreCase(Recursos_Mantenimientos.TEMP.getPersona_temporal().getDepartamento().getDescripcion())){
                                    pos_depa=i;
                                }
                            }

                            departamento_residencia_persona.setSelection(pos_depa);
                            Listar_ProvinciasUpdate(Recursos_Mantenimientos.TEMP.getPersona_temporal().getDepartamento().getCodigo(),context);

                        }else{
                            for(int i=0;i<spinner_departamentos.length;i++){
                                if(spinner_departamentos[i].equalsIgnoreCase("LIMA")){
                                    pos_depa=i;
                                }
                            }


                            departamento_residencia_persona.setSelection(pos_depa);

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
                        provincia_residencia_persona.setAdapter(adapter_arr);



                        if(actualizar_accion){
                            for(int i=0;i<spinner_provincias.length;i++){
                                if(spinner_provincias[i].equalsIgnoreCase(Recursos_Mantenimientos.TEMP.getPersona_temporal().getProvincia().getDescripcion())){
                                    pos_prov=i;
                                }
                            }
                            provincia_residencia_persona.setSelection(pos_prov);
                            Listar_DistritosUpdate(Recursos_Mantenimientos.TEMP.getPersona_temporal().getProvincia().getCodigo(),context);

                        }else{
                            for(int i=0;i<spinner_provincias.length;i++){
                                if(spinner_provincias[i].equalsIgnoreCase("LIMA")){
                                    pos_prov=i;
                                }
                            }

                            provincia_residencia_persona.setSelection(pos_prov);
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
                        distrito_residencia_persona.setAdapter(adapter_arr);


                        if(actualizar_accion){
                            for(int i=0;i<spinner_distritos.length;i++){
                                if(spinner_distritos[i].equalsIgnoreCase(Recursos_Mantenimientos.TEMP.getPersona_temporal().getDistrito().getDescripcion())){
                                    pos_dis=i;
                                }
                            }

                            distrito_residencia_persona.setSelection(pos_dis);
                        }else{
                            for(int i=0;i<spinner_distritos.length;i++){
                                if(spinner_distritos[i].equalsIgnoreCase("LIMA")){
                                    pos_dis=i;
                                }
                            }

                            distrito_residencia_persona.setSelection(pos_dis);
                        }

                        departamento_accion=false;
                        provincia_accion=false;
                        distrito_accion=false;


                        if(actualizar_accion){
                            Mostrar_Informacion_Recuperada();
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

    private void Mostrar_Informacion_Recuperada() {

        nombre_persona.setText(Recursos_Mantenimientos.TEMP.getPersona_temporal().getNombre_Persona());
        apellido_persona.setText(Recursos_Mantenimientos.TEMP.getPersona_temporal().getApellidos_Persona());

        dni_persona.setText(String.valueOf(Recursos_Mantenimientos.TEMP.getPersona_temporal().getDni()));
        correo_persona.setText(Recursos_Mantenimientos.TEMP.getPersona_temporal().getCorreo());
        nacionalidad_persona.setText(Recursos_Mantenimientos.TEMP.getPersona_temporal().getNacionalidad());

        if(Recursos_Mantenimientos.TEMP.getPersona_temporal().getBautizo()==1){
            bautizo_persona.setChecked(true);
        }else{
            bautizo_persona.setChecked(false);
        }

        if(Recursos_Mantenimientos.TEMP.getPersona_temporal().getComunion()==1){
            comunion_persona.setChecked(true);
        }else{
            comunion_persona.setChecked(false);
        }

        if(Recursos_Mantenimientos.TEMP.getPersona_temporal().getConfirmacion()==1){
            confirmacion_persona.setChecked(true);
        }else{
            confirmacion_persona.setChecked(false);
        }

        residencia_persona.setText(Recursos_Mantenimientos.TEMP.getPersona_temporal().getLugar_Residencia());
        club_actual_persona.setText(Recursos_Mantenimientos.TEMP.getPersona_temporal().getClub_actual());
        categoria_persona.setText(Recursos_Mantenimientos.TEMP.getPersona_temporal().getCategoria_Actual());
        liga_actual_persona.setText(Recursos_Mantenimientos.TEMP.getPersona_temporal().getLiga_actual());
        telefono_persona.setText(String.valueOf(Recursos_Mantenimientos.TEMP.getPersona_temporal().getTelefono()));
        fijo_persona.setText(String.valueOf(Recursos_Mantenimientos.TEMP.getPersona_temporal().getTelefono_fijo()));
        apoderado_persona.setText(Recursos_Mantenimientos.TEMP.getPersona_temporal().getNombre_Apoderado());
        telefono_apoderado_persona.setText(String.valueOf(Recursos_Mantenimientos.TEMP.getPersona_temporal().getTelefono_apoderado()));

        ano=Recursos_Mantenimientos.TEMP.getPersona_temporal().getAno_nacimiento();
        mes= Recursos_Mantenimientos.TEMP.getPersona_temporal().getMes_nacimiento()-1;
        dia=Recursos_Mantenimientos.TEMP.getPersona_temporal().getDia_nacimiento();

        calendario_texto_Fecha.setText(Recursos_Mantenimientos.TEMP.getPersona_temporal().getFecha_Nacimiento());

        int año_actual=hoy.get(Calendar.YEAR);
        int mes_actual=hoy.get(Calendar.MONTH)+1;
        int dia_actual=hoy.get(Calendar.DAY_OF_MONTH);

        int e=año_actual-ano;
        boolean ee=false;
        int mes_c=mes+1;
        int dia_c=dia;

        if(mes_c==mes_actual){
            if (dia_c <= dia_actual) {
                ee=true;
                debug("DIA MENOR O IGUAL");
            }else{

            }

            debug("MES ==");
        }else if(mes_c<mes_actual){
            debug("MES MENOR");
        }else if(mes_c>dia_actual){
            if (dia_c == dia_actual) {
                debug("DIA IGUAL ");
            }else if(dia_c<dia_actual){
                  ee=true;
                debug("DIA MENOR ");
            }else if(dia_c>dia_actual){
                debug("DIA MAYOR");
            }
            debug("MES MAYOR");
        }

        if(ee){
            edad_persona.setText((e-1)+" Años");
        }else{
            edad_persona.setText(e+" Años");
        }

        Glide.with(context)
                .load(Recursos_Mantenimientos.TEMP.getPersona_temporal().getFoto())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(foto_persona);



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

    //GESTION FECHA NACIMIENTO
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


        fecha_nacimiento_temporal=anoo+"-"+meso+"-"+diao;
        calendario_texto_Fecha.setText(fecha_nacimiento_temporal);
        int año_actual=hoy.get(Calendar.YEAR);
        int mes_actual=hoy.get(Calendar.MONTH)+1;
        int dia_actual=hoy.get(Calendar.DAY_OF_MONTH);
         boolean d=false;
         edad_actual=año_actual-anoo;

        if(mes_actual<=mes){

        }else{
            if(dia_actual<=dia){

            }else{
                d=true;
            }
        }

        edad_persona.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        if(d){
            edad_persona.setText(String.valueOf((edad_actual-1))+" Años");
        }else{
            edad_persona.setText(String.valueOf(edad_actual)+" Años");
        }




    }
    public void debug(String d){
        System.out.println(d);
    }



}
