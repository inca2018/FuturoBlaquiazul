package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class RecuperarBusqueda3 extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_INFORMACION;
    private Map<String, String> params;

    public RecuperarBusqueda3(String inicio,String fin,String dep,String pro,String dis, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","recuperar_busqueda3");
        params.put("departamento",dep );
        params.put("provincia", pro);
        params.put("distrito",dis );

        params.put("inicio",inicio);
        params.put("fin",fin);

        System.out.println("------------------------------DATOS_ENVIADOS-----------------------");
        System.out.println("DEPARTAMENTO:"+dep);
        System.out.println("PROVINCIA:"+pro);
        System.out.println("DISTRITO:"+dis);
        System.out.println("INICIO:"+inicio);
        System.out.println("FIN:"+fin);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
