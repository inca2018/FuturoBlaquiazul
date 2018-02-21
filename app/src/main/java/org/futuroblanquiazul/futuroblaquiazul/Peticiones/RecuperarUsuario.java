package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class RecuperarUsuario extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.VALIDAR_SESION;
    private Map<String, String> params;

    public RecuperarUsuario(String codigo, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","recuperarUsuario");
        params.put("codigo_usuario", codigo);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
