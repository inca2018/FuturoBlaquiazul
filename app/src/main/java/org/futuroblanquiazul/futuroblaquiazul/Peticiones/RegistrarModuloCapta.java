package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;


public class RegistrarModuloCapta extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_CAPTACION;
    private Map<String, String> params;

    public RegistrarModuloCapta(String id_persona,String departamento,String provincia,String distrito, String id_user,String id_fisico,String id_capacidad,String id_social,String id_tecnico,String id_psico,String sug1,String sug2,String sug3,String lateralidad,String total, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","registrar_modulo_captacion");
        params.put("id_persona", id_persona);
        params.put("departamento", departamento);
        params.put("provincia", provincia);
        params.put("distrito", distrito);
        params.put("id_user", id_user);
        params.put("id_fisico", id_fisico);
        params.put("id_capacidad", id_capacidad);
        params.put("id_social", id_social);
        params.put("id_tecnico", id_tecnico);
        params.put("id_psico", id_psico);
        params.put("sug1", sug1);
        params.put("sug2", sug2);
        params.put("sug3", sug3);
        params.put("lateralidad", lateralidad);
        params.put("total_general", total);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
