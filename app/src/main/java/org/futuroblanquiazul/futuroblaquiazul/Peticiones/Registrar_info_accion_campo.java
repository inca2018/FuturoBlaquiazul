package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class Registrar_info_accion_campo extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_ESTADISTICO;
    private Map<String, String> params;

    public Registrar_info_accion_campo(String id_gestion, String op1, String op2, String op3, String op4, String op5,
                                       String op6, String op7, String op8, String op9, String op10, String op11, String op12,
                                       String op13, String op14, String op15, String op16,String op17,String op18,
                                       String op19,String op20,String op21,String op22,String op23,String op24,String op25,String op26,
                                       String op27,String op28,String op29,String op30,String estado, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","Registrar_info_accion_campo");
        params.put("id_gestion",id_gestion);
        params.put("v1",op1);
        params.put("v2",op2);
        params.put("v3",op3);
        params.put("v4",op4);
        params.put("v5",op5);
        params.put("v6",op6);
        params.put("v7",op7);
        params.put("v8",op8);
        params.put("v9",op9);
        params.put("v10",op10);
        params.put("v11",op11);
        params.put("v12",op12);
        params.put("v13",op13);
        params.put("v14",op14);
        params.put("v15",op15);
        params.put("v16",op16);
        params.put("v17",op17);
        params.put("v18",op18);
        params.put("v19",op19);
        params.put("v20",op20);
        params.put("v21",op21);
        params.put("v22",op22);
        params.put("v23",op23);
        params.put("v24",op24);
        params.put("v25",op25);
        params.put("v26",op26);
        params.put("v27",op27);
        params.put("v28",op28);
        params.put("v29",op29);
        params.put("v30",op30);

        params.put("estado",estado);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
