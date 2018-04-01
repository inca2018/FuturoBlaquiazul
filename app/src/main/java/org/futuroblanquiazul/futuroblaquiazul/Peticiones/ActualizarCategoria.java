package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class ActualizarCategoria extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_MANTENIMIENTO;
    private Map<String, String> params;

    public ActualizarCategoria(String id_Rango, String nom, String estado ,String id_plantel, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","Actualizar_Categoria");
        params.put("id_rango",id_Rango);
        params.put("nombre_categoria",nom);
        params.put("estado",estado);
        params.put("id_plantel",id_plantel);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
