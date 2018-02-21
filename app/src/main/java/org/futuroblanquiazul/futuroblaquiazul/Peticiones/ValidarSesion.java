package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class ValidarSesion extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.VALIDAR_SESION;
    private Map<String, String> params;

    public ValidarSesion(String usuario, String password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","validar");
        params.put("usuario_login",usuario);
        params.put("pass_login",password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
