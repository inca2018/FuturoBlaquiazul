package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class GuardarPosiciones extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_ESTADISTICO;
    private Map<String, String> params;

    public GuardarPosiciones(String id_u, String id_ev, String id_p, String id_pos, String num, String estado, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);

        params = new HashMap<>();
        params.put("operacion","Guardar_Posiciones");
        params.put("id_user",id_u);
        params.put("id_evento",id_ev);
        params.put("id_persona",id_p);
        params.put("id_posicion",id_pos);
        params.put("num_camiseta",num);
        params.put("estado",estado);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
