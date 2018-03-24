package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class ActualizarUsuario extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_MANTENIMIENTO;
    private Map<String, String> params;

    public ActualizarUsuario(String usuario, String pass, String nom, String ape, String dni, String area, String cargo, String correo, String tipo, String estado, String foto,String id_user,String ruta,String ca, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","actualizarUsuario");
        params.put("usuario",usuario);
        params.put("password",pass);
        params.put("nombre_usuario",nom);
        params.put("apellidos_usuario",ape);
        params.put("dni_usuario",dni);
        params.put("area_usuario",area);
        params.put("cargo_usuario",cargo);
        params.put("correo_usuario",correo);
        params.put("tipo_usuario",tipo);
        params.put("estado",estado);
        params.put("foto",foto);

        params.put("id_usuario",id_user);
        params.put("ruta_antigua",ruta);
        params.put("cambios",ca);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}