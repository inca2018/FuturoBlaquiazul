package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class RegistrarEventoBarrio extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_MANTENIMIENTO;
    private Map<String, String> params;

    public RegistrarEventoBarrio(String id_user,String nom,String desc,String depa,String prov,String dit,String f_realizar,String estado, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","registrarBarrioIntimo");
        params.put("id_usuario",id_user);
        params.put("nombre_evento",nom);
        params.put("desc_evento",desc);
        params.put("departamento",depa);
        params.put("provincia",prov);
        params.put("distrito",dit);
        params.put("fecha_realizar",f_realizar);
        params.put("estado",estado);



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
