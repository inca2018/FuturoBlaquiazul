package org.futuroblanquiazul.futuroblaquiazul.Activities.Estadistico;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
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

import org.futuroblanquiazul.futuroblaquiazul.Entity.Equipo;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class NuevoEventoEstadisticoActivity extends AppCompatActivity {

    EditText nombre_evento,detalle_evento;
    Spinner spinner_categorias;
    ImageView foto;
    Button crear;
    Context context;
    ProgressDialog progressDialog;
    String [] lista_categorias_nombres;
    List<Equipo> lista_plantel;

    ImageView fecha_inicio,fecha_fin;
    TextView texto_inicio,texto_fin;
    Calendar dateTime = Calendar.getInstance();
    Calendar dateTime2 = Calendar.getInstance();

    Calendar inicio = Calendar.getInstance();
    long minimo,minimo_inicio;


    private final String CARPETA_RAIZ="FuturoBlanquiazul/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"MisFotos";
    String nombre_creado="";
    String path;
    Bitmap bitmap;
    FloatingActionButton accion_foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_evento_estadistico);
        context=this;
        nombre_evento=findViewById(R.id.nuevo_evento_nombre);
        detalle_evento=findViewById(R.id.nuevo_evento_detalle);
        spinner_categorias=findViewById(R.id.nuevo_evento_spinner);
        crear=findViewById(R.id.nuevo_evento_crear);
        lista_plantel=new ArrayList<>();
        fecha_inicio=findViewById(R.id.nuevo_evento_fecha_inicio);
        fecha_fin=findViewById(R.id.nuevo_evento_fecha_fin);
        texto_inicio=findViewById(R.id.nuevo_evento_fecha_inicio_texto);
        texto_fin=findViewById(R.id.nuevo_evento_fecha_fin_texto);
        fecha_fin.setEnabled(false);

        accion_foto=findViewById(R.id.accion_foto_estad);
        foto=findViewById(R.id.nuevo_evento_foto);

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



        minimo_inicio=Recuperar_minimo(dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH));

        fecha_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog xx = onCreateDialog2();
                xx.show();
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

                                                            if(bitmap!=null){

                                                                String nuevo=Minimizar(bitmap);
                                                                EventoEstadistico.EVENTO_CREACION.setFoto(nuevo);

                                                            }else{

                                                                Bitmap bitmap_actual = ((BitmapDrawable)foto.getDrawable()).getBitmap();
                                                                String nuevo2=Minimizar(bitmap_actual);
                                                                EventoEstadistico.EVENTO_CREACION.setFoto(nuevo2);

                                                            }

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
                    if(lista_plantel.get(x).getNombre_equipo().equalsIgnoreCase(String.valueOf(item))){
                        EventoEstadistico.EVENTO_CREACION.setEquipo(lista_plantel.get(x));
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
        String id_plantel=String.valueOf(eventoCreacion.getEquipo().getId());
        String estado=String.valueOf(1);
        String estado2=String.valueOf(1);
        String fecha_inicio=String.valueOf(eventoCreacion.getFecha_Inicio());
        String fecha_fin=String.valueOf(eventoCreacion.getFecha_Fin());
        String dd=nombre.replace(" ","_");
        String foto_nom=dd+".jpg";
        String foto=eventoCreacion.getFoto();


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

        RegistrarEventoNuevo recuperarDepa = new RegistrarEventoNuevo(nombre,detalle,departamento,provincia,distrito,id_user,id_plantel,foto,estado,estado2,fecha_inicio,fecha_fin,foto_nom,responseListener);
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
                            Equipo temp=new Equipo();
                            temp.setId(objeto.getInt("ID"));
                            temp.setNombre_equipo(objeto.getString("NOMBRE_SUB"));
                            lista_plantel.add(temp);
                        }

                        //LLENAR SPINNER DEPARTAMENTOS
                        lista_categorias_nombres=new String[lista_plantel.size()];
                        for(int i=0;i<lista_plantel.size();i++){
                            lista_categorias_nombres[i]=lista_plantel.get(i).getNombre_equipo();
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
        dialog.getDatePicker().setMinDate(minimo_inicio);

        return dialog;

    }
    public long Recuperar_minimo(int year,int month,int day){
        long d=0;

        inicio.set(Calendar.YEAR,year);
        inicio.set(Calendar.MONTH,month);
        inicio.set(Calendar.DAY_OF_MONTH,day);

        d=inicio.getTimeInMillis();
        return d;
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
                        foto.setImageBitmap(bitmap);
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
                    foto.setImageBitmap(bitmap);

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
}
