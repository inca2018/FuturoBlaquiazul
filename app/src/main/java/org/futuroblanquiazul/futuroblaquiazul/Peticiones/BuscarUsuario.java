package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class BuscarUsuario extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.VALIDAR_SESION;
    private Map<String, String> params;

    public BuscarUsuario(String dni, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","buscar_usuario_dni");

        params.put("dni",dni);



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
