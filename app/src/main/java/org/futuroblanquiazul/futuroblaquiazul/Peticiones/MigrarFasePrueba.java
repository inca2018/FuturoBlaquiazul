package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class MigrarFasePrueba extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_CAPTACION;
    private Map<String, String> params;

    public MigrarFasePrueba(String id_user,String id_persona,String id_equipo,String estado, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","Migrar_Fase_Prueba");
        params.put("id_user",id_user);
        params.put("id_persona",id_persona);
        params.put("id_equipo",id_equipo);
        params.put("estado",estado);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
