package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class AgregarJugadorEvento extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_MANTENIMIENTO;
    private Map<String, String> params;

    public AgregarJugadorEvento(String id_evento,String id_persona,String id_usuario, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","Agregar_Jugador_Evento");
        params.put("id_evento",id_evento);
        params.put("id_persona",id_persona);
        params.put("id_usuario",id_usuario);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
