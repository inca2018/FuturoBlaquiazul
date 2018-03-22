package org.futuroblanquiazul.futuroblaquiazul.Activities.Mantenimientos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Area_Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Oponente;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Perfil;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarAreas;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarOponentes;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarPerfiles;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion_usuario);
        lista_perfiles=new ArrayList<>();
        lista_areas=new ArrayList<>();
        accion_foto=findViewById(R.id.u_accion_usuario);
        foto_usuario=findViewById(R.id.u_foto_usuario);

        areas=findViewById(R.id.u_spinner_areas);
        perfiles=findViewById(R.id.u_spinner_perfiles);
        estado=findViewById(R.id.u_spinner_estado);
        context=this;
        Listar_Perfiles(context);


        accion_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
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

            String path=Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGEN+File.separator+nombre_creado;

            File imagen=new File(path);

            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(imagen));

            startActivityForResult(intent,20);

    }

    private void Cargar_Galeria_Imagen() {

        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione Aplicación"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){

            switch (requestCode){
                case 10:
                    Uri Mipath=data.getData();
                    foto_usuario.setImageURI(Mipath);
                    break;
                case 20:
                    MediaScannerConnection.scanFile(context, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                                 System.out.println("Confirmado");
                        }
                    });
                    Bitmap bitmap= BitmapFactory.decodeFile(path);
                    foto_usuario.setImageBitmap(bitmap);
                    break;
            }


        }
    }

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



                        String[] lista_estado=new String[4];
                        lista_estado[0]="-- SELECCIONE --";
                        lista_estado[1]="ACTIVADO";
                        lista_estado[2]="DESACTIVADO";
                        lista_estado[3]="BLOQUEADO";

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

}
