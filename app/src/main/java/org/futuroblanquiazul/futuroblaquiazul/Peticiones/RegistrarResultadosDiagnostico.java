package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;


public class RegistrarResultadosDiagnostico extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_CAPTACION;
    private Map<String, String> params;

    public RegistrarResultadosDiagnostico(String f1,String f2,String f3,String f4,String f5,String f6,String f7,String c1,String c2,String c3,String c4,String s1,String s2,String s3,String s4,String t1,String t2,String t3,String t4,String t5 ,String t6 ,String p1,String p2,String p3,String p4,String id_persona, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","registrar_modulos");
        params.put("f1", f1);
        params.put("f2", f2);
        params.put("f3", f3);
        params.put("f4", f4);
        params.put("f5", f5);
        params.put("f6", f6);
        params.put("f7", f7);
        params.put("c1", c1);
        params.put("c2", c2);
        params.put("c3", c3);
        params.put("c4", c4);
        params.put("s1", s1);
        params.put("s2", s2);
        params.put("s3", s3);
        params.put("s4", s4);
        params.put("t1", t1);
        params.put("t2", t2);
        params.put("t3", t3);
        params.put("t4", t4);
        params.put("t5", t5);
        params.put("t6", t6);
        params.put("p1", p1);
        params.put("p2", p2);
        params.put("p3", p3);
        params.put("p4", p4);
        params.put("id_persona", id_persona);



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
