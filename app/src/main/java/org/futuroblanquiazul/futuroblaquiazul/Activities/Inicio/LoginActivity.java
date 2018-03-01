package org.futuroblanquiazul.futuroblaquiazul.Activities.Inicio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarCodigoUsuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarUsuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ValidarSesion;
import org.futuroblanquiazul.futuroblaquiazul.R;
import org.futuroblanquiazul.futuroblaquiazul.Server.ServerLogin;
import org.futuroblanquiazul.futuroblaquiazul.Utils.DialogosSalidas;
import org.json.JSONException;
import org.json.JSONObject;

import static org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario.SESION_ACTUAL;

public class LoginActivity extends AppCompatActivity {
    DialogosSalidas d;
    EditText usuario_login, pass_login;
    ProgressDialog progressDialog;
    //Variable de servicio
    ServerLogin Servicio_login;
    Button ingresar;

    Context context;

    String respuesta="";
    int codigo_usuario;
    Usuario UsuarioTemp=null;
    String codigo="";

    TextView  recuperar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
            context=this;
            usuario_login=findViewById(R.id.login_usuario);
            pass_login=findViewById(R.id.login_pass);
            ingresar=findViewById(R.id.ingresar_login);
            recuperar=findViewById(R.id.recuperar_pass);

            ingresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Ingresar_Sesion();
                }
            });


            recuperar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(LoginActivity.this, RecuperarActivity.class);
                    LoginActivity.this.startActivity(intent);
                }
            });
    }
    public void Ingresar_Sesion(){
         System.out.println("Ingreso click INCA");
        //Recuperar variables de sesion
         String usuario=usuario_login.getText().toString().trim();
         String pass=pass_login.getText().toString().trim();



         if(usuario.length()!=0){

             if(pass.length()!=0){
         //Inicio de progress de carga
                 progressDialog = new ProgressDialog(context);
                 progressDialog.setTitle("Login");
                 progressDialog.setMessage("Verificando Usuario...");
                 progressDialog.setCanceledOnTouchOutside(false);
                 progressDialog.show();

        //Recuperar mensaje de validacion , Vacio= Ingreso , LLeno = Mensaje de error (usuario no existe, password incorrecto)
         // String mensaje=Servicio_login.Validar_Sesion(usuario,pass,this);
          Validar_Sesion(usuario,pass,this);


             }else{
                 Toast.makeText(context, "Ingrese Contraseña!", Toast.LENGTH_SHORT).show();
             }
         }else{
             Toast.makeText(context, "Ingrese Usuario!", Toast.LENGTH_SHORT).show();
         }

    }
  // Salir de Login , salir del sistema
    public void onBackPressed() {
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle("SALIR")
                .setMessage("¿Desea Cerrar Aplicación?")
                .setPositiveButton("SI",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
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
    }
    public void limpiar(){
        usuario_login.setText("");
        pass_login.setText("");
    }
    public void Validar_Sesion(final String usuario,final String pass,final Context context) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        respuesta=jsonResponse.getString("mensaje");

                        if(respuesta.length()==0){
                            //Recupera codigo del usuario validado
                            Recuperar_Codigo_Usuario(usuario,pass,context);
                            //Recupera datos del usuario con el codigo
                        }else{
                            //Muestra Mensaje de error
                            limpiar();
                            progressDialog.dismiss();
                            Toast.makeText(context, respuesta, Toast.LENGTH_SHORT).show();
                            }
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

        ValidarSesion validarSesion = new ValidarSesion(usuario, pass, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(validarSesion);

    }
    public void Recuperar_Codigo_Usuario(final String usuario,final String pass, final Context context) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        codigo_usuario=jsonResponse.getInt("codigo_usuario");

                        if(codigo_usuario==0){
                            progressDialog.dismiss();
                            Toast.makeText(context, "Problema de Conexión al Recuperar Usuario", Toast.LENGTH_SHORT).show();
                        }else{
                            Recuperar_Usuario(codigo_usuario,context);
                        }

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error en Recupera codigo de usuario :"+e);
                }
            }
        };

        RecuperarCodigoUsuario recuperarCodigo = new RecuperarCodigoUsuario(usuario, pass, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(recuperarCodigo);


    }
    public void Recuperar_Usuario(final int codigo_usuario,final Context context) {
        codigo=String.valueOf(codigo_usuario);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        UsuarioTemp=new Usuario();
                        UsuarioTemp.setId(jsonResponse.getInt("ID"));
                        UsuarioTemp.setUsuario(jsonResponse.getString("USUARIO"));
                        UsuarioTemp.setPassword(jsonResponse.getString("PASSWORD"));
                        UsuarioTemp.setNombres(jsonResponse.getString("NOMBRES"));
                        UsuarioTemp.setApellidos(jsonResponse.getString("APELLIDOS"));
                        UsuarioTemp.setDni(jsonResponse.getInt("DNI"));
                        UsuarioTemp.setArea(jsonResponse.getString("AREA"));
                        UsuarioTemp.setCargo(jsonResponse.getString("CARGO"));
                        UsuarioTemp.setCorreo(jsonResponse.getString("CORREO"));
                        UsuarioTemp.setTipo_usuario(jsonResponse.getInt("TIPO"));
                        UsuarioTemp.setEstado(jsonResponse.getInt("ESTADO"));
                        UsuarioTemp.setFecha_creacion(jsonResponse.getString("FECHA_CREACION"));
                        UsuarioTemp.setFecha_conexion(jsonResponse.getString("FECHA_CONEXION"));



                        if(UsuarioTemp==null){
                            Toast.makeText(context, "Problemas de Conexión al Recuperar Datos del Usuario", Toast.LENGTH_SHORT).show();
                        }else{
                            SESION_ACTUAL.setId(UsuarioTemp.getId());
                            SESION_ACTUAL.setUsuario(UsuarioTemp.getUsuario());
                            SESION_ACTUAL.setNombres(UsuarioTemp.getNombres());
                            SESION_ACTUAL.setApellidos(UsuarioTemp.getApellidos());
                            SESION_ACTUAL.setDni(UsuarioTemp.getDni());
                            SESION_ACTUAL.setArea(UsuarioTemp.getArea());
                            SESION_ACTUAL.setEstado(UsuarioTemp.getEstado());
                            SESION_ACTUAL.setTipo_usuario(UsuarioTemp.getTipo_usuario());
                            SESION_ACTUAL.setCargo(UsuarioTemp.getCargo());
                            SESION_ACTUAL.setFoto(UsuarioTemp.getFoto());
                            SESION_ACTUAL.setPassword(UsuarioTemp.getPassword());
                            SESION_ACTUAL.setCorreo(UsuarioTemp.getCorreo());
                            SESION_ACTUAL.setFecha_creacion(UsuarioTemp.getFecha_creacion());
                            SESION_ACTUAL.setFecha_conexion(UsuarioTemp.getFecha_conexion());
                            //Cierra progress
                            progressDialog.dismiss();
                            //Cambia de activity al validar correctamente la sesion
                            Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                            LoginActivity.this.startActivity(intent);
                            //Muestra mensaje
                            Toast.makeText(context, "Ingreso Correctamente", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        UsuarioTemp=null;
                        progressDialog.dismiss();
                        Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Inca  : Error en Recuperar Informacion de usuario :"+e);
                }
            }
        };

        RecuperarUsuario recuperarUsuario = new RecuperarUsuario(codigo, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(recuperarUsuario);

    }

}
