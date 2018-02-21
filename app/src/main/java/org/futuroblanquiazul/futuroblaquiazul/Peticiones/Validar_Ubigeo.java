package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class Validar_Ubigeo  extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_CAPTACION;
    private Map<String, String> params;

    public Validar_Ubigeo(String id_user, String id_modulo, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","verificar_ubigeo_modulo");
        params.put("id_user",id_user);
        params.put("id_modulo",id_modulo);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
