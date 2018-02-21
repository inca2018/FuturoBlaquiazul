package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 14/02/2018.
 */

public class RegistrarNuevoMasivo extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_CAPTACION;
    private Map<String, String> params;

    public RegistrarNuevoMasivo(String nombre_masivo, String departamento, String provincia, String distrito, String id_usuario, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","crear_masivo");
        params.put("nombre_masivo",nombre_masivo);
        params.put("departamento",departamento);
        params.put("provincia",provincia);
        params.put("distrito",distrito);
        params.put("id_user",id_usuario);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
