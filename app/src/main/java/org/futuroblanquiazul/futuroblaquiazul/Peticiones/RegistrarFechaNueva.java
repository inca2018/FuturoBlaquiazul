package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class RegistrarFechaNueva extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_ESTADISTICO;
    private Map<String, String> params;

    public RegistrarFechaNueva(String id_evento,String id_user, String id_oponente, String fe, String ob, String es , Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","registrar_Fecha_Evento");
        params.put("id_evento",id_evento);
        params.put("id_user",id_user);
        params.put("id_oponente",id_oponente);
        params.put("fecha_realizar",fe);
        params.put("observaciones",ob);
        params.put("estado",es);



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
