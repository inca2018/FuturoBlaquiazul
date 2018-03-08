package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class RegistrarFasePrueba extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_METODOLOGIA;
    private Map<String, String> params;

    public RegistrarFasePrueba(String id_user, String id_persona, String t_prueba, String id_prueba, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","registrar_Fase_Pruebas");
        params.put("id_user",id_user);
        params.put("id_persona",id_persona);
        params.put("t_prueba",t_prueba);
        params.put("id_prueba",id_prueba);




    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
