package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class Actualizar_barrio extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_CAPTACION;
    private Map<String, String> params;

    public Actualizar_barrio(String id_barrio,String id_persona,String total , Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","Actualizar_barrio");
        params.put("id_barrio",id_barrio);
        params.put("id_persona",id_persona);
        params.put("total_diagnostico",total);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
