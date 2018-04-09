package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class Registrar_info_medidas extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_ESTADISTICO;
    private Map<String, String> params;

    public Registrar_info_medidas(String id_gestion,String op1,String op2,String op3,String op4,String op5,
                                  String op6,String op7,String op8,String op9,String op10,String op11,String op12,String op13,String op14,String op15,String op16,String estado, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","Registrar_info_medidas");
        params.put("id_gestion",id_gestion);
        params.put("op1",op1);
        params.put("op2",op2);
        params.put("op3",op3);
        params.put("op4",op4);
        params.put("op5",op5);
        params.put("op6",op6);
        params.put("op7",op7);
        params.put("op8",op8);
        params.put("op9",op9);
        params.put("op10",op10);
        params.put("op11",op11);
        params.put("op12",op12);
        params.put("op13",op13);
        params.put("op14",op14);
        params.put("op15",op15);
        params.put("op16",op16);
        params.put("estado",estado);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
