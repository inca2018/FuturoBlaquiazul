package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class Actualizar_fecha_wo extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_ESTADISTICO;
    private Map<String, String> params;

    public Actualizar_fecha_wo(String id_fecha, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","actualizar_fecha_w_o");
        params.put("id_fecha",id_fecha);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
