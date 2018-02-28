package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class RegistrarPruebaTecnica extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_CAPTACION;
    private Map<String, String> params;

    public RegistrarPruebaTecnica(String id_user,String id_barrio,String id_persona,String pase_Ras,String pase_al,String control_ras,String control_alto,String pase,String control,String remate,String conduccion,String cabeceo,String total_general,String estado, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","Registrar_prueba_tecnica");
        params.put("id_user",id_user);
        params.put("id_barrio",id_barrio);
        params.put("id_persona",id_persona);

        params.put("pase_ras",pase_Ras);
        params.put("pase_alto",pase_al);
        params.put("control_ras",control_ras);
        params.put("control_alto",control_alto);
        params.put("pase",pase);
        params.put("control",control);
        params.put("remate",remate);
        params.put("conduccion",conduccion);
        params.put("cabeceo",cabeceo);

        params.put("total_general",total_general);
        params.put("estado",estado);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
