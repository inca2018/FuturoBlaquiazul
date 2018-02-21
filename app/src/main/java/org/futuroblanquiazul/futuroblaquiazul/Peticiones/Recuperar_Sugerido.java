package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class Recuperar_Sugerido extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_CAPTACION;
    private Map<String, String> params;

    public Recuperar_Sugerido(String sug1,String sug2,String sug3,Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","recuperar_sugerido");

        params.put("sug1",sug1);
        params.put("sug2",sug2);
        params.put("sug3",sug3);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
