package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class RecuperarBusqueda22 extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_INFORMACION;
    private Map<String, String> params;

    public RecuperarBusqueda22(String inicio, String fin, String depa, String prov, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","recuperar_busqueda22");
        params.put("departamento",depa);
        params.put("provincia",prov);
        params.put("inicio",inicio);
        params.put("fin",fin);
        System.out.println("------------------------------DATOS_ENVIADOS-----------------------");
        System.out.println("DEPA:"+depa);
        System.out.println("PROVINCIA:"+prov);
        System.out.println("INICIO:"+inicio);
        System.out.println("FIN:"+fin);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
