package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class RegistrarPruebaNutricion extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_METODOLOGIA;
    private Map<String, String> params;

    public RegistrarPruebaNutricion(String id_user, String id_persona, String imc, String graso, String aks,  String total_general, String estado, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","registrar_prueba_nutricional");
        params.put("id_user",id_user);
        params.put("id_persona",id_persona);

        params.put("imc",imc);
        params.put("graso",graso);
        params.put("aks",aks);


        params.put("total_general",total_general);
        params.put("estado",estado);







    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
