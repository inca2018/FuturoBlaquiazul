package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class RegistrarResultadosDiagnosticoSeguimiento extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_CAPTACION;
    private Map<String, String> params;

    public RegistrarResultadosDiagnosticoSeguimiento(String s1, String s2, String s3, String s4, String p1, String p2, String p3, String p4, String id_persona, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","registrar_diagnosticos_seguimiento");
        params.put("id_persona",id_persona);
        params.put("s1",s1);
        params.put("s2",s2);
        params.put("s3",s3);
        params.put("s4",s4);
        params.put("p1",p1);
        params.put("p2",p2);
        params.put("p3",p3);
        params.put("p4",p4);



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
