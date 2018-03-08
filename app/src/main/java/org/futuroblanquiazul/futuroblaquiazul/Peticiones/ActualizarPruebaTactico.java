package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class ActualizarPruebaTactico extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_METODOLOGIA;
    private Map<String, String> params;

    public ActualizarPruebaTactico(String id_diagnostico, String id_grupo, String id_plantel, String id_persona, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","actualizar_tactico_metodologia");
        params.put("id_tactico",id_diagnostico);
        params.put("id_grupo",id_grupo);
        params.put("id_plantel",id_plantel);
        params.put("id_persona",id_persona);




    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
