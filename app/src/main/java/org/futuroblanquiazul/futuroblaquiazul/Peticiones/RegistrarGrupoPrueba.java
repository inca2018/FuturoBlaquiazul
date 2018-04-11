package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class RegistrarGrupoPrueba extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_MANTENIMIENTO;
    private Map<String, String> params;

    public RegistrarGrupoPrueba(String user,String grupo,String obs,String id_plantel,String estado, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","RegistrarGrupo");
        params.put("id_user",user);
        params.put("grupo",grupo);
        params.put("obs",obs);
        params.put("id_plantel",id_plantel);
        params.put("estado",estado);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
