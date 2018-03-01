package org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio;

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
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Solicitudes;
import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.BuscarUsuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.CambiarContra;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarSolicitudes;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CambiarPassActivity extends AppCompatActivity {


    TextView nombre,dni,fecha;
    EditText contra_nueva;
    Button enviar;
    Context context;
    ProgressDialog progressDialog;
    int codigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_pass);
        context=this;

        nombre=findViewById(R.id.nombre_solicitante);
        dni=findViewById(R.id.dni_solicitante);
        fecha=findViewById(R.id.fecha_solicitante);
        contra_nueva=findViewById(R.id.pass_cambiado);
        enviar=findViewById(R.id.enviar_cambio);

        if(Usuario.SESION_ACTUAL.getSolicitud_temp()!=null){
            nombre.setText(Usuario.SESION_ACTUAL.getSolicitud_temp().getNombre_solicitud());
            dni.setText(String.valueOf(Usuario.SESION_ACTUAL.getSolicitud_temp().getDni_solicitud()));
            fecha.setText(Usuario.SESION_ACTUAL.getSolicitud_temp().getFecha_re());
        }else{
            nombre.setText("No disponible");
            dni.setText("No disponible");
            fecha.setText("No disponible");
        }


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Usuario.SESION_ACTUAL.getSolicitud_temp()!=null){

                    if(contra_nueva.getText().length()!=0){
                        String pass_nuevo=contra_nueva.getText().toString();
                        Buscar_Usuario(Usuario.SESION_ACTUAL.getSolicitud_temp(),pass_nuevo,context);
                    }else{
                        Toast.makeText(context, "Ingrese Contraseña Nueva", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(CambiarPassActivity.this, "Usuario no encontrado!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void Buscar_Usuario(final Solicitudes solicitud_temp,final String pass, final Context context) {

        String dni=String.valueOf(solicitud_temp.getDni_solicitud());

        debug("DNI  A BUSCA :"+dni);

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Usuario:");
        progressDialog.setMessage("Cambiando Contraseña...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        debug("PASO BUSCAR USUARIO");
                           codigo=jsonResponse.getInt("codigo_usuario");
                           debug("CODIGO ENCONTRADO:"+codigo+" PASS ENVIADO:"+pass+" DNI ENVIADO:"+Usuario.SESION_ACTUAL.getSolicitud_temp().getDni_solicitud());
                            Cambiar_Contraseña(codigo,pass,Usuario.SESION_ACTUAL.getSolicitud_temp().getDni_solicitud(),context);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "DNI de Solicitante no Encontrado", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al cambiar contraseña :"+e);
                }
            }
        };

        BuscarUsuario xx = new BuscarUsuario(dni,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);

    }

    private void Cambiar_Contraseña(int codigo, String pass,int dni,final Context context) {

        String codi=String.valueOf(codigo);
        String dnii=String.valueOf(dni);


        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                        debug("PASO CAMBIAR CONTRA");
                        progressDialog.dismiss();
                        Toast.makeText(context, "Contraseña Cambiada correctamente", Toast.LENGTH_SHORT).show();

                        Intent intent=new Intent(CambiarPassActivity.this,PrincipalActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra("o","o2");
                        CambiarPassActivity.this.startActivity(intent);

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error al cambiar contraseña de solicitante", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error de conexion al cambiar contraseña :"+e);
                }
            }
        };

        CambiarContra xx = new CambiarContra(codi,pass,dnii,responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(xx);




    }



    public void debug(String sm){
        System.out.println(sm);

    }
}
