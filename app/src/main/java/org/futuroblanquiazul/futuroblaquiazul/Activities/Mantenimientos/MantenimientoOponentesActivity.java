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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio.PrincipalActivity;
import org.futuroblanquiazul.futuroblaquiazul.Adapter.AdapterOponentes;
import org.futuroblanquiazul.futuroblaquiazul.Entity.BarrioIntimo;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Oponente;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Unidad_Territorial;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Interface_Alianza.RecyclerViewOnItemClickListener;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ActualizarOponentes;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarBarrios;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarOponentes2;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarOponentes;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarUsuario;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static org.futuroblanquiazul.futuroblaquiazul.Utils.Recursos_Mantenimientos.OPONENTES;

public class MantenimientoOponentesActivity extends AppCompatActivity {

    FloatingActionButton accion_foto;
    ImageView foto_oponente;
    EditText nombre_oponente;
    EditText apodo_oponente;
    Button agregar_oponente;
    RecyclerView recyclerView;
    Context context;
    LinearLayoutManager linearLayoutManager;
    ProgressDialog progressDialog;
    private final String CARPETA_RAIZ="FuturoBlanquiazul/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"MisFotos";
    String nombre_creado="";
    String path;
    Bitmap bitmap;
    public static MantenimientoOponentesActivity OPONENTE_TEMP=new MantenimientoOponentesActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_oponentes);
        context=this;
        accion_foto=findViewById(R.id.o_accion_oponente);
        foto_oponente=findViewById(R.id.o_foto_oponente);
        Recursos_Mantenimientos.TEMP.setFoto_oponente(foto_oponente);
        nombre_oponente=findViewById(R.id.o_nom_oponente);
        Recursos_Mantenimientos.TEMP.setNombre_oponente(nombre_oponente);
        apodo_oponente=findViewById(R.id.o_abreviado);
        Recursos_Mantenimientos.TEMP.setApodo(apodo_oponente);
        agregar_oponente=findViewById(R.id.o_accion_agregar);

        recyclerView=findViewById(R.id.recycler_oponente);

        Acciones_generales();

        linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

        Recursos_Mantenimientos.ADAPTER_OPONENTE = new AdapterOponentes(context,OPONENTES, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
            }
        });
        recyclerView.setAdapter(Recursos_Mantenimientos.ADAPTER_OPONENTE );
        recyclerView.setLayoutManager(linearLayoutManager);
        Recursos_Mantenimientos.OPONENTES.clear();
        Listar_Oponentes(context);
    }

    public void Mostrar_Oponente(Context context){
        if(Recursos_Mantenimientos.TEMP.getOponente_temporal()!=null){
            Recursos_Mantenimientos.TEMP.getNombre_oponente().setText(Recursos_Mantenimientos.TEMP.getOponente_temporal().getNombre_Oponente().toString());
            Recursos_Mantenimientos.TEMP.getApodo().setText(Recursos_Mantenimientos.TEMP.getOponente_temporal().getAbreviado().toString());
            Glide.with(context)
                    .load(Recursos_Mantenimientos.TEMP.getOponente_temporal().getFoto())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(Recursos_Mantenimientos.TEMP.getFoto_oponente());
            debug("Recuperando Resultados");

            Recursos_Mantenimientos.TEMP.setActualizar(true);
        }
    }
    private void Acciones_generales() {
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
        agregar_oponente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Recuperar_Resultados();
            }
        });
    }
    private void Recuperar_Resultados() {

        if(Recursos_Mantenimientos.TEMP.isActualizar()==true){
            debug("Entro a actualizar");
              if( Recursos_Mantenimientos.TEMP.getNombre_oponente().getText().length()!=0){
                  if( Recursos_Mantenimientos.TEMP.getApodo().getText().length()!=0){
                      Oponente temp=new Oponente();
                      temp.setNombre_Oponente(Recursos_Mantenimientos.TEMP.getNombre_oponente().getText().toString());
                      temp.setAbreviado(Recursos_Mantenimientos.TEMP.getApodo().getText().toString());
                      Usuario u=new Usuario();
                      u.setId(Usuario.SESION_ACTUAL.getId());
                      temp.setUsuario(u);
                      if(bitmap!=null){
                          debug("CON FOTO DE CAMARA  O GALERIA");
                          String nuevo=Minimizar(bitmap);
                          temp.setFoto(nuevo);
                      }else{
                              debug("CON FOTO DE SERVIDOR");
                              Recursos_Mantenimientos.TEMP.getFoto_oponente().buildDrawingCache();
                              Bitmap bmap = Recursos_Mantenimientos.TEMP.getFoto_oponente().getDrawingCache();
                              String no=convertirImgString(bmap);
                              temp.setFoto(no);
                              Recursos_Mantenimientos.TEMP.getFoto_oponente().destroyDrawingCache();

                      }
                          Actualizar_Oponente(Recursos_Mantenimientos.TEMP.getOponente_temporal(),temp,context);

                  }else{
                      Recursos_Mantenimientos.TEMP.getApodo().setError("Ingrese apodo de Oponente Ejm. 'Universidad Cesar Vallejo '(UCV)");
                  }

              }else{
                  Recursos_Mantenimientos.TEMP.getNombre_oponente().setError("Ingrese Nombre de Oponente");
              }
        }else{
            debug("Entro a registrar");
            if(nombre_oponente.getText().length()!=0){
                if(apodo_oponente.getText().length()!=0){
                    Oponente temp=new Oponente();
                    temp.setNombre_Oponente(nombre_oponente.getText().toString());
                    temp.setAbreviado(apodo_oponente.getText().toString());
                    Usuario u=new Usuario();
                    u.setId(Usuario.SESION_ACTUAL.getId());
                    temp.setUsuario(u);

                    if(bitmap!=null){
                        debug("BITMAP CON DATOS");
                        String nuevo=Minimizar(bitmap);
                        temp.setFoto(nuevo);

                        debug("CON FOTO ENVIO");
                        debug("---------------------------------------------------------------------------------");
                        debug(nuevo);
                    }else{
                        debug("BITMAP VACIO");

                            debug("OPONENTE VACIO");
                            Bitmap bitmap_actual = ((BitmapDrawable)foto_oponente.getDrawable()).getBitmap();
                            String nuevo2=Minimizar(bitmap_actual);
                            temp.setFoto(nuevo2);

                            debug("SIN FOTO EN DEFAULT");
                            debug("---------------------------------------------------------------------------------");
                            debug(nuevo2);

                    }
                      Registrar_Oponente_Nuevo(temp,context);
                }else{
                    apodo_oponente.setError("Ingrese apodo de Oponente Ejm. 'Universidad Cesar Vallejo '(UCV)");
                }
            }else{
                nombre_oponente.setError("Ingrese Nombre de Oponente");
            }
        }

    }
    private void Actualizar_Oponente(Oponente op_antiguo, final Oponente oponente, final Context context) {

        debug("ACTUALIZAR OPONENTE VOLLEY");

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Oponente:");
        progressDialog.setMessage("Actualizando Oponente...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String nom_oponente=oponente.getNombre_Oponente();
        String apodo=oponente.getAbreviado();
        String id_user=String.valueOf(oponente.getUsuario().getId());
        String foto=oponente.getFoto();
        String estado=String.valueOf(1);
        String id_oponente=String.valueOf(op_antiguo.getId()) ;

        String nombre_foto=apodo+".jpg";
        String nombre_foto_antiguo=op_antiguo.getAbreviado()+".jpg";

        debug("------------------------------------------------------------------");
        debug("NOMBRE ENVIADO:"+nom_oponente);
        debug("APODO: "+apodo);
        debug("ID USER:"+id_user);
        //debug("FOTO: "+foto);
        debug("ID_OPONENTE:"+id_oponente);

        debug("------------------------------------------------------------------");

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        progressDialog.dismiss();
                        nombre_oponente.setText("");
                        apodo_oponente.setText("");
                        foto_oponente.setImageResource(R.drawable.no_disponible);
                        Recursos_Mantenimientos.TEMP.setOponente_temporal(null);
                        Recursos_Mantenimientos.TEMP.setActualizar(false);
                        Recursos_Mantenimientos.TEMP.getApodo().setText(null);
                        Recursos_Mantenimientos.TEMP.getNombre_oponente().setText(null);
                        Recursos_Mantenimientos.TEMP.getFoto_oponente().setImageResource(R.drawable.no_disponible);
                        bitmap=null;

                        Actualizar_Oponentes(context);
                        // limpiar_Campos();
                        Toast.makeText(context, "Oponente Actualizado con exito", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error al Actualizar Oponente", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al actualizar Oponente :"+e);
                }
            }
        };

        ActualizarOponentes xx = new ActualizarOponentes(nom_oponente,apodo,id_user,foto,estado,id_oponente,nombre_foto,nombre_foto_antiguo ,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    private void Registrar_Oponente_Nuevo(final Oponente temp,final Context context) {

        debug("REGISTRAR OPONENTE");
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Oponente:");
        progressDialog.setMessage("Registrando Oponente...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String nom_oponente=temp.getNombre_Oponente();
        String apodo=temp.getAbreviado();
        String id_user=String.valueOf(temp.getUsuario().getId());
        String foto=temp.getFoto();
        String estado=String.valueOf(1);
        String nombre_foto=apodo+".jpg";

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        nombre_oponente.setText("");
                        apodo_oponente.setText("");
                        foto_oponente.setImageResource(R.drawable.no_disponible);

                        Recursos_Mantenimientos.TEMP.setActualizar(false);
                        Recursos_Mantenimientos.TEMP.getApodo().setText(null);
                        Recursos_Mantenimientos.TEMP.getNombre_oponente().setText(null);
                        Recursos_Mantenimientos.TEMP.getFoto_oponente().setImageResource(R.drawable.no_disponible);
                        bitmap=null;

                        progressDialog.dismiss();
                         Actualizar_Oponentes(context);
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

        RegistrarOponentes xx = new RegistrarOponentes(nom_oponente,apodo,id_user,foto,estado,nombre_foto ,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
    public void Actualizar_Oponentes(Context context) {
                Recursos_Mantenimientos.OPONENTES.clear();
                Listar_Oponentes(context);
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
                        foto_oponente.setImageBitmap(bitmap);
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
                    foto_oponente.setImageBitmap(bitmap);

                    break;
            }
        }
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
    //LISTAR OPONENTES
    private void Listar_Oponentes(final Context context) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Mantenimiento Oponentes:");
        progressDialog.setMessage("Listando...");
        progressDialog.show();
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
                            temp.setNum(i+1);
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_Oponente(objeto.getString("NOMBRE_OPONENTE"));
                            temp.setAbreviado(objeto.getString("APODO"));
                            Usuario u=new Usuario();
                            u.setId(objeto.getInt("ID_USER"));
                            temp.setUsuario(u);
                            temp.setFecha_Registro(objeto.getString("FECHA_REGISTRO"));
                            temp.setFoto(objeto.getString("FOTO"));
                            temp.setEstado(objeto.getInt("ESTADO"));
                            Recursos_Mantenimientos.OPONENTES.add(temp);

                        }

                        Recursos_Mantenimientos.ADAPTER_OPONENTE.notifyDataSetChanged();
                        progressDialog.dismiss();

                        System.out.println("LISTADO COMPLETO DE OPONENTES");
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Listado Vacio", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al LISTAR OPONENTES:"+e);
                }
            }
        };

        RecuperarOponentes2 xx = new RecuperarOponentes2(responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);
    }
    public void onBackPressed() {
        Recursos_Mantenimientos.OPONENTES.clear();
        Recursos_Mantenimientos.ADAPTER_OPONENTE=null;
        Recursos_Mantenimientos.TEMP.setOponente_temporal(null);
        Recursos_Mantenimientos.TEMP.setActualizar(false);

        Intent intent=new Intent(MantenimientoOponentesActivity.this,PrincipalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("o","o5");
        MantenimientoOponentesActivity.this.startActivity(intent);

    }
    public void debug(String sm){
        System.out.println(sm);
    }
}
