package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class RecuperarBusqueda1 extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_INFORMACION;
    private Map<String, String> params;

    public RecuperarBusqueda1(String inicio,String fin,String departamento ,Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","recuperar_busqueda1");
        params.put("departamento",departamento);
        params.put("inicio",inicio);
        params.put("fin",fin);

        System.out.println("------------------------------DATOS_ENVIADOS-----------------------");
        System.out.println("DEPA:"+departamento);
        System.out.println("INICIO:"+inicio);
        System.out.println("FIN:"+fin);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
