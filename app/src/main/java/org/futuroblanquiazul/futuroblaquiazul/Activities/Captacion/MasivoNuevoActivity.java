package org.futuroblanquiazul.futuroblaquiazul.Activities.Captacion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RegistrarNuevoMasivo;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Utils.GestionUbigeo;
import org.json.JSONException;
import org.json.JSONObject;

public class MasivoNuevoActivity extends AppCompatActivity {
     EditText nombre_masivo;
     TextView ubigeo_masivo;
     Button guardar_masivo;
     Context context;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_masivo);

        nombre_masivo=findViewById(R.id.nombre_nuevo_masivo);
        ubigeo_masivo=findViewById(R.id.ubigeo_nuevo_masivo);
        guardar_masivo=findViewById(R.id.crear_nuevo_masivo);
        context=this;


        String depa= GestionUbigeo.CAPTACION_UBIGEO_MASIVO.getDepartamento().getDescripcion();
        String prov=GestionUbigeo.CAPTACION_UBIGEO_MASIVO.getProvincia().getDescripcion();
        String dist=GestionUbigeo.CAPTACION_UBIGEO_MASIVO.getDistrito().getDescripcion();


        if(depa.length()!=0 && prov.length()!=0 && dist.length()!=0 ){
            String ubigeo=depa+"/"+prov+"/"+dist;
            ubigeo_masivo.setText(ubigeo);
        }else{
            ubigeo_masivo.setText("Ubigeo no encontrado!");
        }

        guardar_masivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre=nombre_masivo.getText().toString();
                if(nombre.trim().length()!=0){
                    Accion_Nuevo_Masivo(context,nombre,GestionUbigeo.CAPTACION_UBIGEO_MASIVO, Usuario.SESION_ACTUAL.getId());
                }

            }
        });


    }

    private Boolean Accion_Nuevo_Masivo(final Context context,final String nombre,final GestionUbigeo ubigeo,int id_usuario) {

        String nombre_masivo=nombre;
        String id_departamento=String.valueOf(ubigeo.getDepartamento().getCodigo());
        String id_provincia=String.valueOf(ubigeo.getProvincia().getCodigo());
        String id_distrito=String.valueOf(ubigeo.getDistrito().getCodigo());
        String id_user=String.valueOf(id_usuario);

        boolean rpta=false;

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Nuevo Masivo:");
        progressDialog.setMessage("Creando Registro Masivo...");
        progressDialog.show();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "REGISTRO MASIVO GUARDADO", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MasivoNuevoActivity.this, ListaMasivosActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        MasivoNuevoActivity.this.startActivity(intent);
                        Limpiar();
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error Validar sesion :"+e);
                }
            }
        };

        RegistrarNuevoMasivo xx = new RegistrarNuevoMasivo(nombre_masivo, id_departamento,id_provincia,id_distrito,id_user, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);


        return rpta;


    }

    public void Limpiar(){
        nombre_masivo.setText("");
    }


}
