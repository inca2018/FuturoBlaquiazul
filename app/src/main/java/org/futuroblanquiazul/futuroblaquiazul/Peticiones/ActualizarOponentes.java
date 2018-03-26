package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jesus Inca on 09/02/2018.
 */

public class ActualizarOponentes extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_MANTENIMIENTO;
    private Map<String, String> params;

    public ActualizarOponentes(String nom, String apodo, String id_u, String foto, String estado,String id_opo,String nom_foto,String nombre_foto_antiguo, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","actualizar_Oponente");
        params.put("nom_oponente",nom);
        params.put("apodo",apodo);
        params.put("id_usuario",id_u);
        params.put("foto",foto);
        params.put("estado",estado);
        params.put("id_oponente",id_opo);
        params.put("nombre_foto",nom_foto);
        params.put("nombre_foto_antigua",nombre_foto_antiguo);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
