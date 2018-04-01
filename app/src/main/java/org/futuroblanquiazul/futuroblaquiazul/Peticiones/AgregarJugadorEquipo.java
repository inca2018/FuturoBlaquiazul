package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class AgregarJugadorEquipo extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_MANTENIMIENTO;
    private Map<String, String> params;

    public AgregarJugadorEquipo(String id_pla, String id_persona, String estado, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","Agregar_Jugador_Equipo");
        params.put("id_equipo",id_pla);
        params.put("id_persona",id_persona);
        params.put("estado",estado);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
