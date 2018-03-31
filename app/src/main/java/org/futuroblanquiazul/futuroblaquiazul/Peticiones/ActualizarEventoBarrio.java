package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class ActualizarEventoBarrio extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_MANTENIMIENTO;
    private Map<String, String> params;

    public ActualizarEventoBarrio(String id_evento, String nom, String desc, String depa, String prov, String dit, String f_realizar, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","actualizarBarrioIntimo");
        params.put("id_evento",id_evento);
        params.put("nombre_evento",nom);
        params.put("desc_evento",desc);
        params.put("departamento",depa);
        params.put("provincia",prov);
        params.put("distrito",dit);
        params.put("fecha_realizar",f_realizar);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
