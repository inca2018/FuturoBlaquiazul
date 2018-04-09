package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class Registrar_info_general extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_ESTADISTICO;
    private Map<String, String> params;

    public Registrar_info_general(String id_user,String id_evento,String id_fecha,String gol_local,String gol_oponente,String pos_1,String pos_2, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","Registrar_info_general");
        params.put("id_user",id_user);
        params.put("id_evento",id_evento);
        params.put("id_fecha",id_fecha);
        params.put("goles_local",gol_local);
        params.put("goles_oponente",gol_oponente);
        params.put("pos1",pos_1);
        params.put("pos2",pos_2);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
