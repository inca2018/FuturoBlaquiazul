package org.futuroblanquiazul.futuroblaquiazul.Server;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.futuroblanquiazul.futuroblaquiazul.Entity.Usuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarCodigoUsuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.RecuperarUsuario;
import org.futuroblanquiazul.futuroblaquiazul.Peticiones.ValidarSesion;
import org.futuroblanquiazul.futuroblaquiazul.Servicios.ServicioLogin;
import org.json.JSONException;
import org.json.JSONObject;


public class ServerLogin implements ServicioLogin {

    String respuesta="";
    int codigo_usuario;
    Usuario UsuarioTemp=null;

    String codigo="";

    @Override
    public String Validar_Sesion(String usuario, String pass,Context context) {
        System.out.println("Ingreso validar sesion INCA");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                         respuesta=jsonResponse.getString("mensaje");
                        // int idusuario = jsonResponse.getInt("codigo");
                        //String nombre = jsonResponse.getString("nombre");

                       // progress.dismiss();
                    } else {

                         respuesta=jsonResponse.getString("mensaje");

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

        return respuesta;
    }

    @Override
    public int Recuperar_Codigo_Usuario(String usuario, String pass,Context context) {

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        codigo_usuario=jsonResponse.getInt("codigo_usuario");
                        // int idusuario = jsonResponse.getInt("codigo");
                        //String nombre = jsonResponse.getString("nombre");
                    } else {

                        codigo_usuario=0;
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

        return codigo_usuario;
    }

    @Override
    public Usuario Recuperar_Usuario(int codigo_usuario,Context context) {
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

                    } else {

                        UsuarioTemp=null;
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

        return UsuarioTemp;

    }
}
