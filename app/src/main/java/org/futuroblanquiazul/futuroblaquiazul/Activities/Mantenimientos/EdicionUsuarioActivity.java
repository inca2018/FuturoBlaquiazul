package org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico.ListaEventosEstadisticosActivity;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Area_Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Perfil;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarAreas;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPerfiles;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarEventoNuevo;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarUsuario;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class EdicionUsuarioActivity extends AppCompatActivity {
    Spinner areas,perfiles,estado;
    List<Perfil> lista_perfiles;
    List<Area_Usuario> lista_areas;
    Context context;
    ProgressDialog progressDialog;
    String [] lista_areas_desc,lista_perfiles_desc;
    FloatingActionButton accion_foto;
    ImageView foto_usuario;
    String nombre_creado="";
    String path;
    private final String CARPETA_RAIZ="FuturoBlanquiazul/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"MisFotos";

    Bitmap bitmap;

    Button u_boton_guardar;
    EditText usuario,password,nombres,apellidos,dni,correo,cargo;

    String area_seleccion;
    String perfil_seleccion;
    String estado_seleccion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_usuario);
        lista_perfiles=new ArrayList<>();
        lista_areas=new ArrayList<>();
        accion_foto=findViewById(R.id.u_accion_usuario);
        foto_usuario=findViewById(R.id.u_foto_usuario);
        u_boton_guardar=findViewById(R.id.u_boton_guardar);

        areas=findViewById(R.id.u_spinner_areas);
        perfiles=findViewById(R.id.u_spinner_perfiles);
        estado=findViewById(R.id.u_spinner_estado);
        usuario=findViewById(R.id.u_usuario_usuario);
        password=findViewById(R.id.u_usuario_pass);
        nombres=findViewById(R.id.u_usuario_nom);
        apellidos=findViewById(R.id.u_usuario_ape);
        dni=findViewById(R.id.u_usuario_dni);
        correo=findViewById(R.id.u_usuario_correo);
        cargo=findViewById(R.id.u_usuario_cargo);

        context=this;
        Listar_Perfiles(context);


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


        u_boton_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recupera_Valores();

            }
        });

        areas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    Object objeto=parent.getItemAtPosition(position);
                    area_seleccion=String.valueOf(objeto);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        perfiles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    Object objeto=parent.getItemAtPosition(position);
                    perfil_seleccion=String.valueOf(objeto);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    Object objeto=parent.getItemAtPosition(position);

                    estado_seleccion=String.valueOf(objeto);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    //GESTION DE ACTIVITY ACTUAL
    private void Recupera_Valores() {
        if(usuario.getText().toString().length()!=0){
            if(password.getText().toString().length()!=0){
                if(nombres.getText().toString().length()!=0){
                      if(apellidos.getText().toString().length()!=0){
                          if(dni.getText().toString().length()!=0){
                              if(correo.getText().toString().length()!=0){
                                  if(cargo.getText().toString().length()!=0){
                                      if(areas.getSelectedItemPosition()!=0){
                                          if(perfiles.getSelectedItemPosition()!=0){
                                              Usuario temp=new Usuario();
                                              temp.setUsuario(usuario.getText().toString());
                                              temp.setPassword(password.getText().toString());
                                              temp.setNombres(nombres.getText().toString());
                                              temp.setApellidos(apellidos.getText().toString());
                                              temp.setDni(Integer.parseInt(dni.getText().toString()));
                                              temp.setCorreo(correo.getText().toString());
                                              temp.setCargo(cargo.getText().toString());
                                              temp.setPerfil(RecuperarPerfil());
                                              temp.setArea_usuario(RecuperarArea());
                                              temp.setEstado((estado.getSelectedItemPosition()+1));

                                              if(bitmap!=null){
                                                  String info=convertirImgString(bitmap);
                                                   byte[] bytecode= Base64.decode(info,Base64.DEFAULT);
                                                  int alto=100;
                                                  int ancho=100;
                                                  Bitmap fo=BitmapFactory.decodeByteArray(bytecode,0,bytecode.length);
                                                  Bitmap d=Bitmap.createScaledBitmap(fo,alto,ancho,true);
                                                 temp.setFoto(convertirImgString(d));
                                                  temp.setFoto(convertirImgString(bitmap));

                                                  debug("CON FOTO ENVIO");
                                                  debug("---------------------------------------------------------------------------------");
                                                  // debug(String.valueOf(convertirImgString(bitmap)));
                                              }else{
                                                  Bitmap bitmap_actual = ((BitmapDrawable)foto_usuario.getDrawable()).getBitmap();
                                                  String info=convertirImgString(bitmap_actual);
                                                  byte[] bytecode= Base64.decode(info,Base64.DEFAULT);
                                                  int alto=100;
                                                  int ancho=100;
                                                  Bitmap fo=BitmapFactory.decodeByteArray(bytecode,0,bytecode.length);
                                                  Bitmap d=Bitmap.createScaledBitmap(fo,alto,ancho,true);
                                                  temp.setFoto(convertirImgString(d));
                                                  temp.setFoto(convertirImgString(bitmap_actual));

                                                  debug("SIN FOTO EN DEFAULT");
                                                  debug("---------------------------------------------------------------------------------");
                                                 // debug(String.valueOf(convertirImgString(bitmap_actual)));
                                              }


                                              Registrar_Usuario_Nuevo(temp,context);

                                          }else{
                                              Toast.makeText(context, "Seleccione Perfil", Toast.LENGTH_SHORT).show();
                                          }
                                      }else{
                                          Toast.makeText(context, "Seleccione Area", Toast.LENGTH_SHORT).show();
                                      }
                                  }else{
                                      cargo.setError("Campo Obligatorio");
                                  }
                              }else{
                                  correo.setError("Campo Obligatorio");
                              }
                          }else{
                              dni.setError("Campo Obligatorio");
                          }

                      }else{
                          apellidos.setError("Campo Obligatorio");
                      }
                }else{
                    nombres.setError("Campo Obligatorio");
                }
            }else{
                password.setError("Campo Obligatorio");
            }
        }else{
            usuario.setError("Campo Obligatorio");
        }

    }

    private void Registrar_Usuario_Nuevo(final Usuario temp,final Context context) {


        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Usuario:");
        progressDialog.setMessage("Registrando Usuario...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String usuario=temp.getUsuario();
        String pass=temp.getPassword();
        String nom=temp.getNombres().toUpperCase().toString();
        String ape=temp.getApellidos().toUpperCase().toString();
        String dni=String.valueOf(temp.getDni());
        String area=String.valueOf(temp.getArea_usuario().getId());
        String cargo=String.valueOf(temp.getCargo().toUpperCase());
        String correo=temp.getCorreo().toUpperCase();
        String tipo=String.valueOf(temp.getPerfil().getId());
        String foto=String.valueOf(temp.getFoto());
        String estado=String.valueOf(temp.getEstado());


        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        Intent intent=new Intent(context,MantenimientoUsuarioActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);

                       // limpiar_Campos();
                        Toast.makeText(context, "Usuario Nuevo Guardado Exitosamente", Toast.LENGTH_SHORT).show();


                    } else {

                        Toast.makeText(context, "Error de conexion al Recuperar Usuario", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al registrar Usuario :"+e);
                }
            }
        };

        RegistrarUsuario xx = new RegistrarUsuario( usuario, pass, nom, ape, dni, area, cargo, correo, tipo, estado, foto ,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);



    }

    private void limpiar_Campos() {
    }

    private Area_Usuario RecuperarArea() {
        Area_Usuario area=new Area_Usuario();
        for(int i=0;i<lista_areas.size();i++){
            if(area_seleccion.equalsIgnoreCase(lista_areas.get(i).getDescripcion())){
                area=lista_areas.get(i);
            }
        }
        return  area;
    }

    private Perfil RecuperarPerfil() {
        Perfil perfil=new Perfil();
        for(int i=0;i<lista_perfiles.size();i++){
            if(perfil_seleccion.equalsIgnoreCase(lista_perfiles.get(i).getNombre_Perfil())){
                perfil=lista_perfiles.get(i);
            }
        }
        return  perfil;
    }


    public String convertirImgString(Bitmap bitmap){

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte= array.toByteArray();
        String imagenString=Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }

    // LISTAS DE SPINNER
    private void Listar_Perfiles(final Context context) {
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Barrio Intimo:");
                progressDialog.setMessage("Listando...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        JSONArray xx=jsonResponse.getJSONArray("perfiles");
                        for(int i=0;i<xx.length();i++){
                            JSONObject objeto= xx.getJSONObject(i);
                            Perfil temp=new Perfil();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Perfil(objeto.getString("NOMBRE_PERFIL"));
                            lista_perfiles.add(temp);
                        }

                        //LLENAR SPINNER DEPARTAMENTOS
                        lista_perfiles_desc=new String[lista_perfiles.size()+1];
                        lista_perfiles_desc[0]="-- SELECCIONE --";
                        for(int i=0;i<lista_perfiles.size();i++){
                            lista_perfiles_desc[i+1]=lista_perfiles.get(i).getNombre_Perfil().toString();
                        }
                        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,lista_perfiles_desc);

                        perfiles.setAdapter(adapter_arr);


                        Listar_Areas(context);
                    } else {

                        Toast.makeText(context, "Error de conexion Recuperar Perfiles", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar Oponentes :"+e);
                }
            }
        };

        RecuperarPerfiles xx = new RecuperarPerfiles(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    private void Listar_Areas(final Context context) {


        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        JSONArray xx=jsonResponse.getJSONArray("areas");
                        for(int i=0;i<xx.length();i++){
                            JSONObject objeto= xx.getJSONObject(i);
                            Area_Usuario temp=new Area_Usuario();
                            temp.setId(objeto.getInt("ID"));
                            temp.setDescripcion(objeto.getString("DESCRIPCION"));
                            lista_areas.add(temp);
                        }

                        //LLENAR SPINNER DEPARTAMENTOS
                        lista_areas_desc=new String[lista_areas.size()+1];
                        lista_areas_desc[0]="-- SELECCIONE --";
                        for(int i=0;i<lista_areas.size();i++){
                            lista_areas_desc[i+1]=lista_areas.get(i).getDescripcion();
                        }
                        ArrayAdapter<String> adapter_arr=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,lista_areas_desc);

                        areas.setAdapter(adapter_arr);


                        String[] lista_estado=new String[3];
                        lista_estado[0]="ACTIVADO";
                        lista_estado[1]="DESACTIVADO";
                        lista_estado[2]="BLOQUEADO";

                        ArrayAdapter<String> adapter_arr2=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_dropdown_item,lista_estado);

                        estado.setAdapter(adapter_arr2);

                        progressDialog.dismiss();
                    } else {

                        Toast.makeText(context, "Error de conexion Recuperar Areas", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al recuperar Oponentes :"+e);
                }
            }
        };

        RecuperarAreas xx = new RecuperarAreas(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

   // OPCIONES PARA RECUPERAR IMAGEN
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
                    debug(" PATH ----------------------  :  "+String.valueOf(Mipath));
                    foto_usuario.setImageURI(Mipath);

                    try {
                        bitmap=MediaStore.Images.Media.getBitmap(context.getContentResolver(),Mipath);
                        foto_usuario.setImageBitmap(bitmap);
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
                    foto_usuario.setImageBitmap(bitmap);

                    break;
            }


        }
    }


    // VALIDACION DE PERMISOS  DE VERSIONES ANDROID
    private boolean Validar_Permisos() {
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return  true;
        }

        if((checkSelfPermission(CAMERA)==PackageManager.PERMISSION_GRANTED)
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

    public void debug(String sm){
        System.out.println(sm);
    }

}
