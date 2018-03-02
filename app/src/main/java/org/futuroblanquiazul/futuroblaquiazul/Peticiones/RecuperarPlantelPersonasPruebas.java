package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class RecuperarPlantelPersonasPruebas extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_METODOLOGIA;
    private Map<String, String> params;

    public RecuperarPlantelPersonasPruebas(String id_plantel, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","recuperar_personas_grupo_pruebas");
        params.put("id_plantel",id_plantel);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
