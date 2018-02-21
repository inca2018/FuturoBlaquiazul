package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class AgregarPersonaEnMasivo extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_CAPTACION;
    private Map<String, String> params;

    public AgregarPersonaEnMasivo( String id_masivo,String id_persona,String id_usuario, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","registrar_masivo_persona_nueva");
        params.put("id_masivo",id_masivo);
        params.put("id_persona",id_persona);
        params.put("id_user",id_usuario);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
