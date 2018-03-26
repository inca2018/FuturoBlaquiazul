package org.futuroblanquiazul.futuroblaquiazul.Peticiones;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.futuroblanquiazul.futuroblaquiazul.Utils.Conexion;

import java.util.HashMap;
import java.util.Map;


public class RegistrarOponentes extends StringRequest {

    private static final String LOGIN_REQUEST_URL = Conexion.RUTA_SERVICIO_MANTENIMIENTO;
    private Map<String, String> params;

    public RegistrarOponentes( String nom,String apodo,String id_u,String foto,String estado,String nom_foto, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL,listener, null);
        params = new HashMap<>();
        params.put("operacion","Registrar_Oponente");
        params.put("nom_oponente",nom);
        params.put("apodo",apodo);
        params.put("id_usuario",id_u);
        params.put("foto",foto);
        params.put("estado",estado);
        params.put("nombre_foto",nom_foto);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
