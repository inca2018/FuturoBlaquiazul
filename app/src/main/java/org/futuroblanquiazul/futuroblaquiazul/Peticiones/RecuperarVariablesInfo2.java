package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class RecuperarVariablesInfo2 extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_ESTADISTICO;
    private Map<String, String> params;

    public RecuperarVariablesInfo2(String id_fecha, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","Recuperar_info_Cabecera");
        params.put("id_fecha",id_fecha );

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
