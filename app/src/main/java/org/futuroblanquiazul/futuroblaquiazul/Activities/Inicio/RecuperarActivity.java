package org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Peticiones.CrearSolicitud;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ValidarSesion;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONException;
import org.json.JSONObject;

public class RecuperarActivity extends AppCompatActivity {


    Button enviar_solicitud;
    EditText dni_solicitud,nombres_solicitud;
    Context context;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);
        context=this;

        enviar_solicitud=findViewById(R.id.enviar_solicitud);
        dni_solicitud=findViewById(R.id.dni_solicitud);
        nombres_solicitud=findViewById(R.id.nombres_solicitud);


        enviar_solicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dni_solicitud.getText().toString().length()!=0 && nombres_solicitud.getText().toString().length()!=0){
                    String dni_enviado=dni_solicitud.getText().toString().trim().toUpperCase();
                    String nombres_enviado=nombres_solicitud.getText().toString().trim().toUpperCase();

                    Crear_Solicitud(nombres_enviado,dni_enviado,context);
                }else{
                    Toast.makeText(RecuperarActivity.this, "Debe ingresar datos para crear la solicitud", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void Crear_Solicitud(String nombre,String dni_enviado,final Context context) {


        String estado="1";
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Solicitud");
        progressDialog.setMessage("Creando solicitud...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        progressDialog.dismiss();

                        Toast.makeText(context, "Registro de Solicitud Creada, Contacte con un administrador para el cambio de contraseña", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(RecuperarActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        RecuperarActivity.this.startActivity(intent);


                    }else {
                        String error=jsonResponse.getString("error");
                        progressDialog.dismiss();
                        if(error.length()!=0){
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error crear solicitud :"+e);
                }
            }
        };

        CrearSolicitud xx = new CrearSolicitud(nombre,dni_enviado,estado, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }
}
