package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class RegistrarSeguimientos extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_CAPTACION;
    private Map<String, String> params;

    public RegistrarSeguimientos(String id_persona,String id_user,String id_social,String id_psico,
                                 String titular,String capitan,String departamento,String provincia,
                                 String distrito,String nom_competencia,String rival,String minutos,String cant1,
                                 String cant2,String cant3,String cant4,String total,String estado,String goles, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","registrar_seguimiento_nuevo");
        params.put("id_persona",id_persona);
        params.put("id_user",id_user);
        params.put("id_social",id_social);
        params.put("id_psico",id_psico);
        params.put("titular",titular);
        params.put("capitan",capitan);
        params.put("departamento",departamento);
        params.put("provincia",provincia);
        params.put("distrito",distrito);
        params.put("nom_competencia",nom_competencia);
        params.put("nom_rival",rival);
        params.put("minutos",minutos);
        params.put("cant_1",cant1);
        params.put("cant_2",cant2);
        params.put("cant_3",cant3);
        params.put("cant_4",cant4);
        params.put("total_puntaje",total);
        params.put("estado",estado);
        params.put("goles",goles);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
