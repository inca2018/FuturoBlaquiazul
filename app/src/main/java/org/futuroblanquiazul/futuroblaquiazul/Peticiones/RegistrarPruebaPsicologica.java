package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class RegistrarPruebaPsicologica extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_METODOLOGIA;
    private Map<String, String> params;

    public RegistrarPruebaPsicologica(String id_user,String id_persona,String o1,String o2,String o3,String o4,String o5,String o6,String o7,String o8,String o9,String o10,String o11,String o12,String o13,String o14,String o15,String cognitivo,String motivacion,String inteligencia,String Liderazgo,String Autoconfianza,String total_general,String estado, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","registrar_prueba_psicologica");
        params.put("id_user",id_user);
        params.put("id_persona",id_persona);
        params.put("o1",o1);
        params.put("o2",o2);
        params.put("o3",o3);
        params.put("o4",o4);
        params.put("o5",o5);
        params.put("o6",o6);
        params.put("o7",o7);
        params.put("o8",o8);
        params.put("o9",o9);
        params.put("o10",o10);
        params.put("o11",o11);
        params.put("o12",o12);
        params.put("o13",o13);
        params.put("o14",o14);
        params.put("o15",o15);
        params.put("cognitivo",cognitivo);
        params.put("motivacion",motivacion);
        params.put("inteligencia",inteligencia);
        params.put("liderazgo",Liderazgo);
        params.put("autoconfianza",Autoconfianza);
        params.put("total_general",total_general);
        params.put("estado",estado);







    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
