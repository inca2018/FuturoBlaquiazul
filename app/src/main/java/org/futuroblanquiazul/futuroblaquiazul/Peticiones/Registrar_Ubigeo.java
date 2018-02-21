package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class Registrar_Ubigeo extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_CAPTACION;
    private Map<String, String> params;

    public Registrar_Ubigeo(String id_user, String id_modulo, String id_depa, String id_prov, String id_dist, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","guardar_ubigeo_nuevo");
        params.put("id_user",id_user);
        params.put("id_modulo",id_modulo);
        params.put("departamento",id_depa);
        params.put("provincia",id_prov);
        params.put("distrito",id_dist);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
