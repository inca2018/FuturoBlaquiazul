package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class CrearSolicitud extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_CAPTACION;
    private Map<String, String> params;

    public CrearSolicitud(String nombre,String dni,String esrta, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","crear_solicitud");
        params.put("nombre_buscado",nombre);
        params.put("dni_buscado",dni);
        params.put("estado",esrta);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
